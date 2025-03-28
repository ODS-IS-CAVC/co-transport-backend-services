package nlj.business.shipper.service.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.shipper.constant.MessageConstant.SystemMessage;
import nlj.business.shipper.constant.MessageConstant.TransportPlanMessage;
import nlj.business.shipper.constant.ParamConstant;
import nlj.business.shipper.domain.CargoInfo;
import nlj.business.shipper.domain.ShipperOperator;
import nlj.business.shipper.domain.TransportPlan;
import nlj.business.shipper.domain.TransportPlanCargoInfo;
import nlj.business.shipper.domain.TransportPlanItem;
import nlj.business.shipper.dto.TransportPlanCargoInfoDTO;
import nlj.business.shipper.dto.TransportPlanDTO;
import nlj.business.shipper.dto.TransportPlanItemDTO;
import nlj.business.shipper.dto.request.TransportPlanRequestDTO;
import nlj.business.shipper.dto.response.TransportPlanPagingResponse;
import nlj.business.shipper.dto.response.TransportPlanResponseDTO;
import nlj.business.shipper.mapper.TransportPlanCargoInfoMapper;
import nlj.business.shipper.mapper.TransportPlanMapper;
import nlj.business.shipper.repository.CargoInfoRepository;
import nlj.business.shipper.repository.ShipperOperatorRepository;
import nlj.business.shipper.repository.TransportPlanCargoInfoRepository;
import nlj.business.shipper.repository.TransportPlanCustomRepository;
import nlj.business.shipper.repository.TransportPlanItemRepository;
import nlj.business.shipper.repository.TransportPlanRepository;
import nlj.business.shipper.service.DataTransferYamatoService;
import nlj.business.shipper.service.ShipperOperatorService;
import nlj.business.shipper.service.TransportPlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 輸送計画サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransportPlanServiceImpl implements TransportPlanService {

    private final TransportPlanRepository transportPlanRepository;
    private final TransportPlanCustomRepository transportPlanCustomRepository;
    private final TransportPlanItemRepository transportPlanItemRepository;
    private final ShipperOperatorRepository shipperOperatorRepository;
    private final TransportPlanMapper transportPlanMapper;
    private final TransportPlanCargoInfoMapper transportPlanCargoInfoMapper;
    private final DataTransferYamatoService dataTransferYamatoService;
    private final ShipperOperatorService shipperOperatorService;
    private final CargoInfoRepository cargoInfoRepository;
    private final TransportPlanCargoInfoRepository transportPlanCargoInfoRepository;
    private final EntityManager entityManager;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 輸送計画を作成する。
     *
     * @param transportPlanRequestDTO 輸送計画リクエストDTO
     */
    @Override
    @Transactional
    public void createTransportPlan(TransportPlanRequestDTO transportPlanRequestDTO) {
        TransportPlan transportPlan = transportPlanMapper.toTransportPlanEntity(
            transportPlanRequestDTO.getTransportPlanDTO());
        String operatorId = userContext.getUser().getCompanyId();
        ShipperOperator shipperOperator = shipperOperatorRepository.getShipperOperatorById(operatorId);
        if (shipperOperator == null) {
            shipperOperator = shipperOperatorService.createShipperOperator(operatorId);
        }
        transportPlan.setShipperOperator(shipperOperator);
        transportPlan = transportPlanRepository.save(transportPlan);

        for (TransportPlanCargoInfoDTO transportPlanCargoInfoDTO : transportPlanRequestDTO.getTransportPlanCargoInfoDTOList()) {
            TransportPlanCargoInfo transportPlanCargoInfo = transportPlanCargoInfoMapper.toTransportPlanCargoInfoEntity(
                transportPlanCargoInfoDTO);
            transportPlanCargoInfo.setTransportPlanId(transportPlan.getId());
            transportPlanCargoInfo.setShipperOperator(shipperOperator);
            transportPlanCargoInfoRepository.save(transportPlanCargoInfo);
            updateStatusCargoInfo(transportPlanCargoInfoDTO.getCargoInfoId());
        }

        List<TransportPlanItem> transportPlanItems = new ArrayList<>();
        for (TransportPlanItemDTO transportPlanItemDTO : transportPlanRequestDTO.getTransportPlanItemDTOList()) {
            TransportPlanItem transportPlanItem = transportPlanMapper.toTransportPlanItemEntity(transportPlanItemDTO);
            transportPlanItem.setTransportPlan(transportPlan);
            transportPlanItem.setShipperOperator(shipperOperator);
            transportPlanItems.add(transportPlanItem);
            transportPlanItemRepository.save(transportPlanItem);
        }
        dataTransferYamatoService.transferDataToYamato(transportPlan, transportPlanItems);
    }

    /**
     * すべての輸送計画を取得する。
     *
     * @return 輸送計画DTOのリスト
     */
    @Override
    public TransportPlanPagingResponse getAllTransportPlans(String transportName, List<Integer> statuses,
        Long outerPackageCode, Pageable pageable) {
        User user = userContext.getUser();
        TransportPlanPagingResponse transportPlanPagingResponse = new TransportPlanPagingResponse();
        ShipperOperator shipperOperator = shipperOperatorRepository.findById(user.getCompanyId()).orElse(null);
        if (shipperOperator == null) {
            transportPlanPagingResponse.setCurrentPage(Integer.valueOf(ParamConstant.DEFAULT_PAGE_NO));
            transportPlanPagingResponse.setTotalItem(0L);
            transportPlanPagingResponse.setTotalPage(0);
            transportPlanPagingResponse.setItemPerPage(Integer.valueOf(ParamConstant.LIMIT_DEFAULT));
            return transportPlanPagingResponse;
        }

        Page<TransportPlan> transportPlanPage = transportPlanCustomRepository.searchTrspPlanByTrspName(
            user.getCompanyId(), transportName, statuses, outerPackageCode, pageable);
        if (transportPlanPage.isEmpty()) {
            transportPlanPagingResponse.setCurrentPage(Integer.valueOf(ParamConstant.DEFAULT_PAGE_NO));
            transportPlanPagingResponse.setTotalItem(0L);
            transportPlanPagingResponse.setTotalPage(0);
            transportPlanPagingResponse.setItemPerPage(Integer.valueOf(ParamConstant.LIMIT_DEFAULT));
            return transportPlanPagingResponse;
        }
        transportPlanPagingResponse.setTotalItem(transportPlanPage.getTotalElements());
        transportPlanPagingResponse.setCurrentPage(pageable.getPageNumber() + 1);
        transportPlanPagingResponse.setTotalPage(transportPlanPage.getTotalPages());
        transportPlanPagingResponse.setItemPerPage(pageable.getPageSize());

        List<TransportPlan> transportPlanList = transportPlanPage.getContent();
        List<TransportPlanResponseDTO> responseDTOs = new ArrayList<>();

        for (TransportPlan transportPlan : transportPlanList) {
            TransportPlanResponseDTO responseDTO = new TransportPlanResponseDTO();

            TransportPlanDTO transportPlanDTO = transportPlanMapper.toTransportPlanDTO(transportPlan);
            transportPlanDTO.setCreatedDate(
                transportPlan.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            transportPlanDTO.setCompanyName(shipperOperator.getOperatorName());
            transportPlanDTO.setId(transportPlan.getId());
            responseDTO.setTransportPlanDTO(transportPlanDTO);

            List<TransportPlanItem> items = transportPlanItemRepository.findByTransportPlanId(transportPlan.getId());
            List<TransportPlanItemDTO> itemDTOs = items.stream()
                .map(transportPlanMapper::toTransportPlanItemDTO)
                .collect(Collectors.toList());
            responseDTO.setTransportPlanItemDTOList(itemDTOs);

            List<TransportPlanCargoInfo> transportPlanCargoInfoList = transportPlanCargoInfoRepository.findByTransportPlanId(
                transportPlan.getId());
            responseDTO.setTransportPlanCargoInfoDTOList(transportPlanCargoInfoList.stream()
                .map(transportPlanCargoInfoMapper::toTransportPlanCargoInfoDTO)
                .collect(Collectors.toList()));

            responseDTOs.add(responseDTO);
        }
        transportPlanPagingResponse.setDataList(responseDTOs);
        if (shipperOperator != null) {
            transportPlanPagingResponse.setCompanyName(shipperOperator.getOperatorName());
        }

        return transportPlanPagingResponse;
    }

    /**
     * IDによって輸送計画を取得する。
     *
     * @param id 輸送計画ID
     * @return 輸送計画レスポンスDTO
     */
    @Override
    public TransportPlanResponseDTO getTransportPlanById(Long id) {
        TransportPlanResponseDTO transportPlanResponseDTO = new TransportPlanResponseDTO();

        TransportPlan transportPlan = transportPlanRepository.getTransportPlanById(id);
        if (transportPlan == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, SystemMessage.SYSTEM_ERR_001);
        }

        List<TransportPlanItem> transportPlanItemList = transportPlanItemRepository.findByTransportPlanId(
            transportPlan.getId());

        List<TransportPlanItemDTO> transportPlanItemDTOList = new ArrayList<>();

        transportPlanItemList.forEach(transportPlanItem -> {
            String sqlGetShipperOperatorId = "SELECT id FROM cns_line_item_by_date WHERE transport_plan_item_id = :transport_plan_item_id";
            Object result = entityManager.createNativeQuery(sqlGetShipperOperatorId)
                .setParameter("transport_plan_item_id", transportPlanItem.getId())
                .getResultList().stream().findFirst().orElse(null);
            String cnsLineItemId = result != null ? result.toString() : null;
            TransportPlanItemDTO transportPlanItemDTO = transportPlanMapper.toTransportPlanItemDTO(transportPlanItem);
            transportPlanItemDTO.setCnsLineItemByDateId(cnsLineItemId);
            transportPlanItemDTOList.add(transportPlanItemDTO);
        });

        transportPlanResponseDTO.setTransportPlanDTO(transportPlanMapper.toTransportPlanDTO(transportPlan));

        List<TransportPlanCargoInfo> transportPlanCargoInfoList = transportPlanCargoInfoRepository.findByTransportPlanId(
            transportPlan.getId());
        transportPlanResponseDTO.setTransportPlanCargoInfoDTOList(transportPlanCargoInfoList.stream()
            .map(transportPlanCargoInfoMapper::toTransportPlanCargoInfoDTO)
            .collect(Collectors.toList()));

        transportPlanResponseDTO.setTransportPlanItemDTOList(transportPlanItemDTOList);
        return transportPlanResponseDTO;
    }

    /**
     * IDによって輸送計画を更新する。
     *
     * @param transportPlanId         輸送計画ID
     * @param transportPlanRequestDTO 輸送計画リクエストDTO
     */
    @Override
    @Transactional
    public void updateTransportPlan(Long transportPlanId, TransportPlanRequestDTO transportPlanRequestDTO) {
        User user = userContext.getUser();
        TransportPlan existingTransportPlan = transportPlanRepository.getTransportPlanById(transportPlanId);
        List<TransportPlanItem> transportPlanItemsAdd = new ArrayList<>();
        List<Long> existingTransportPlanItemIds = new ArrayList<>();
        List<Long> transportPlanItemDTOIds = new ArrayList<>();
        if (existingTransportPlan == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, SystemMessage.SYSTEM_ERR_001);
        }
        String operatorId = userContext.getUser().getCompanyId();
        ShipperOperator shipperOperator = shipperOperatorRepository.getShipperOperatorById(operatorId);
        if (shipperOperator == null) {
            shipperOperator = shipperOperatorService.createShipperOperator(operatorId);
        }
        List<TransportPlanItem> existingTransportPlanItems = existingTransportPlan.getTransportPlanItems();
        if (!existingTransportPlanItems.isEmpty()) {
            existingTransportPlanItemIds = existingTransportPlanItems.stream()
                .map(TransportPlanItem::getId)
                .collect(Collectors.toList());
        }
        if (!transportPlanRequestDTO.getTransportPlanItemDTOList().isEmpty()) {
            transportPlanItemDTOIds = transportPlanRequestDTO.getTransportPlanItemDTOList().stream()
                .map(TransportPlanItemDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }
        List<TransportPlanItemDTO> transportPlanItemDTOs = transportPlanRequestDTO.getTransportPlanItemDTOList();
        TransportPlan updateTransportPlan = transportPlanMapper.toTransportPlanEntity(
            transportPlanRequestDTO.getTransportPlanDTO());
        updateTransportPlan.setShipperOperator(shipperOperator);
        updateTransportPlan.setId(transportPlanId);
        transportPlanRepository.save(updateTransportPlan);
        if (!transportPlanItemDTOs.isEmpty()) {
            ShipperOperator finalShipperOperator = shipperOperator;
            transportPlanItemDTOs.forEach(transportPlanItemDTO -> {
                boolean isExistingTransportPlanItem = transportPlanItemRepository.getTransportPlanItemById(
                    transportPlanItemDTO.getId()) != null;
                boolean isExistingTransOrder = false;

                if (isExistingTransportPlanItem) {
                    isExistingTransOrder = checkExistInTransOrder(transportPlanItemDTO.getId());
                }
                // if transportPlanItem exist in s_transport_plan_item and not exist in t_trans_order
                if (isExistingTransportPlanItem && !isExistingTransOrder) {
                    // update s_tranpoport_plan_item
                    TransportPlanItem transportPlanItem = transportPlanMapper.toTransportPlanItemEntity(
                        transportPlanItemDTO);
                    transportPlanItem.setTransportPlan(updateTransportPlan);
                    transportPlanItem.setShipperOperator(finalShipperOperator);
                    transportPlanItemRepository.save(transportPlanItem);

                    // update cns_line_item_by_date
                    updateCnsLineItemByDate(user, updateTransportPlan, transportPlanItem, finalShipperOperator);

                    // delete in t_trans_matching
                    try {
                        String sqlMatching = "DELETE FROM t_trans_matching WHERE transport_plan_item_id = :itemId";
                        entityManager.createNativeQuery(sqlMatching)
                            .setParameter("itemId", transportPlanItem.getId())
                            .executeUpdate();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }

                // if transportPlanItem not exist in s_transport_plan_item
                if (!isExistingTransportPlanItem) {
                    TransportPlanItem transportPlanItem = transportPlanMapper.toTransportPlanItemEntity(
                        transportPlanItemDTO);
                    transportPlanItem.setTransportPlan(updateTransportPlan);
                    transportPlanItem.setShipperOperator(finalShipperOperator);
                    transportPlanItem.setTotalHeight(updateTransportPlan.getTotalHeight());
                    transportPlanItem.setTotalLength(updateTransportPlan.getTotalLength());
                    transportPlanItem.setTotalWidth(updateTransportPlan.getTotalWidth());
                    transportPlanItem.setWeight(updateTransportPlan.getTotalWeight());
                    transportPlanItemRepository.save(transportPlanItem);
                    transportPlanItemsAdd.add(transportPlanItem);
                }
            });
            if (!transportPlanItemsAdd.isEmpty()) {
                dataTransferYamatoService.transferDataToYamatoUpdate(existingTransportPlan, transportPlanItemsAdd);
            }

            // if not exist in DTO and exit s_transport_plan_item
            if (!existingTransportPlanItemIds.isEmpty()) {
                List<Long> finalTransportPlanItemDTOIds = transportPlanItemDTOIds;
                existingTransportPlanItemIds.forEach(existingTransportPlanItemId -> {
                    if (!finalTransportPlanItemDTOIds.contains(existingTransportPlanItemId)) {
                        if (!checkExistInTransOrder(existingTransportPlanItemId)) {
                            transportPlanItemRepository.deleteById(existingTransportPlanItemId);
                            try {
                                String sqlCns = "DELETE FROM cns_line_item_by_date WHERE transport_plan_item_id = :itemId";
                                entityManager.createNativeQuery(sqlCns)
                                    .setParameter("itemId", existingTransportPlanItemId)
                                    .executeUpdate();
                                String sqlMatching = "DELETE FROM t_trans_matching WHERE transport_plan_item_id = :itemId";
                                entityManager.createNativeQuery(sqlMatching)
                                    .setParameter("itemId", existingTransportPlanItemId)
                                    .executeUpdate();
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }
                    }
                });
            }
        }

        List<TransportPlanCargoInfo> transportPlanCargoInfos = transportPlanCargoInfoRepository.findByTransportPlanId(
            transportPlanId);
        List<Long> transportPlanCargoInfoIds = new ArrayList<>();
        List<Long> transportPlanCargoInfoDTOIds = new ArrayList<>();
        if (!transportPlanCargoInfos.isEmpty()) {
            transportPlanCargoInfoIds = transportPlanCargoInfos.stream().map(TransportPlanCargoInfo::getId).collect(
                Collectors.toList());
        }
        List<TransportPlanCargoInfoDTO> transportPlanCargoInfoDTOs = new ArrayList<>();
        if (!transportPlanRequestDTO.getTransportPlanCargoInfoDTOList().isEmpty()) {
            transportPlanCargoInfoDTOs = transportPlanRequestDTO.getTransportPlanCargoInfoDTOList();
        }
        if (!transportPlanCargoInfoDTOs.isEmpty()) {
            transportPlanCargoInfoDTOIds = transportPlanCargoInfoDTOs.stream()
                .map(TransportPlanCargoInfoDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }

        for (TransportPlanCargoInfoDTO transportPlanCargoInfoDTO : transportPlanRequestDTO.getTransportPlanCargoInfoDTOList()) {
            TransportPlanCargoInfo transportPlanCargoInfo = transportPlanCargoInfoMapper.toTransportPlanCargoInfoEntity(
                transportPlanCargoInfoDTO);
            transportPlanCargoInfo.setTransportPlanId(existingTransportPlan.getId());
            transportPlanCargoInfo.setShipperOperator(shipperOperator);
            transportPlanCargoInfoRepository.save(transportPlanCargoInfo);
            updateStatusCargoInfo(transportPlanCargoInfoDTO.getCargoInfoId());
        }
        List<Long> idsNotInDTO = new ArrayList<>(transportPlanCargoInfoIds);
        idsNotInDTO.removeAll(transportPlanCargoInfoDTOIds);
        if (!idsNotInDTO.isEmpty()) {
            transportPlanCargoInfoRepository.deleteAllByIdInBatch(idsNotInDTO);
        }
    }

    private void updateCnsLineItemByDate(User user, TransportPlan transportPlan, TransportPlanItem transportPlanItem,
        ShipperOperator shipperOperator) {
        String sqlCnsLineItemByDate =
            "UPDATE cns_line_item_by_date SET updated_user = :updatedUser, updated_date = :updatedDate, "
                + "arrival_to = :arrivalTo, collection_date = :collectionDate, collection_time_from = :collectionTimeFrom, collection_time_to = :collectionTimeTo, departure_from = :departureFrom, operator_id = :operatorId, "
                + "outer_package_code = :outerPackageCode, price_per_unit = :pricePerUnit, trailer_number = :trailerNumber, trailer_number_rest = :trailerNumberRest, transport_code = :transportCode, "
                + "transport_name = :transportName, temperature_range = :temperatureRange, trans_plan_id = :transPlanId, operator_code = :operatorCode, trans_type = :transType, status = :status, total_height = :totalHeight, "
                + "total_length = :totalLength, total_width = :totalWidth, weight = :weight WHERE transport_plan_item_id = :transportPlanItemId RETURNING id";
        entityManager.createNativeQuery(sqlCnsLineItemByDate)
            .setParameter("updatedUser", user.getUsername())
            .setParameter("updatedDate", LocalDateTime.now())
            .setParameter("arrivalTo", transportPlan.getArrivalTo())
            .setParameter("collectionDate", transportPlanItem.getCollectionDate())
            .setParameter("collectionTimeFrom", transportPlan.getCollectionTimeFrom())
            .setParameter("collectionTimeTo", transportPlan.getCollectionTimeTo())
            .setParameter("departureFrom", transportPlan.getDepartureFrom())
            .setParameter("operatorId", user.getCompanyId())
            .setParameter("outerPackageCode", transportPlanItem.getOuterPackageCode())
            .setParameter("pricePerUnit", transportPlanItem.getPricePerUnit())
            .setParameter("trailerNumber", BigDecimal.ONE)
            .setParameter("trailerNumberRest", BigDecimal.ZERO)
            .setParameter("transportCode", transportPlanItem.getTransportCode())
            .setParameter("transportName", transportPlanItem.getTransportName())
            .setParameter("temperatureRange", String.join(", ", transportPlanItem.getTempRange()
                .stream()
                .map(String::valueOf)
                .toArray(String[]::new)))
            .setParameter("transPlanId", transportPlan.getId())
            .setParameter("operatorCode", shipperOperator.getOperatorCode())
            .setParameter("transType", 0)
            .setParameter("status", 0)
            .setParameter("totalHeight",
                transportPlanItem.getTotalHeight() != null ? transportPlanItem.getTotalHeight() : BigDecimal.ZERO)
            .setParameter("totalLength",
                transportPlanItem.getTotalLength() != null ? transportPlanItem.getTotalLength() : BigDecimal.ZERO)
            .setParameter("totalWidth",
                transportPlanItem.getTotalWidth() != null ? transportPlanItem.getTotalWidth() : BigDecimal.ZERO)
            .setParameter("weight",
                transportPlanItem.getWeight() != null ? transportPlanItem.getWeight() : BigDecimal.ZERO)
            .setParameter("transportPlanItemId", transportPlanItem.getId())
            .getSingleResult();
    }

    private boolean checkExistInTransOrder(Long transportPlanItemId) {
        String sqlTransOrder = "SELECT id FROM t_trans_order WHERE transport_plan_item_id = :transportPlanItemId";
        List<Object[]> results = entityManager.createNativeQuery(sqlTransOrder)
            .setParameter("transportPlanItemId", transportPlanItemId)
            .getResultList();
        return !results.isEmpty();
    }

    /**
     * 荷物情報のステータスを更新する。
     *
     * @param idCargo 荷物情報ID
     */

    private void updateStatusCargoInfo(Long idCargo) {
        if (!BaseUtil.isNull(String.valueOf(idCargo))) {
            CargoInfo cargoInfo = cargoInfoRepository.findById(idCargo).orElse(null);
            if (cargoInfo != null) {
                cargoInfo.setStatus(1);
                cargoInfoRepository.save(cargoInfo);
            }
        }
    }

    /**
     * 輸送計画アイテムを削除する。
     *
     * @param transportPlanId 輸送計画ID
     */
    private void deleteCnsLineItemByDateAndTransMatching(Long transportPlanId) {
        List<TransportPlanItem> transportPlanItemsExist = transportPlanItemRepository.findByTransportPlanId(
            transportPlanId);
        if (transportPlanItemsExist.isEmpty()) {
            return;
        }
        checkCnsLineItemByDateInTransOrder(transportPlanItemsExist);
        transportPlanItemsExist.forEach(transportPlanItem -> {
            try {
                String sqlCns = "DELETE FROM cns_line_item_by_date WHERE transport_plan_item_id = :itemId";
                entityManager.createNativeQuery(sqlCns)
                    .setParameter("itemId", transportPlanItem.getId())
                    .executeUpdate();
                String sqlMatching = "DELETE FROM t_trans_matching WHERE transport_plan_item_id = :itemId";
                entityManager.createNativeQuery(sqlMatching)
                    .setParameter("itemId", transportPlanItem.getId())
                    .executeUpdate();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

    /**
     * 輸送計画アイテムが輸送注文に存在するかチェックする。
     *
     * @param transportPlanItems 輸送計画アイテムリスト
     */
    @Override
    public void checkCnsLineItemByDateInTransOrder(List<TransportPlanItem> transportPlanItems) {
        List<Long> transportPlanItemIds = transportPlanItems.stream().map(transportPlanItem ->
            transportPlanItem.getId()).collect(Collectors.toList());
        String sqlTransOrder = "SELECT id FROM t_trans_order WHERE transport_plan_item_id IN :transportPlanItemIds";
        List<Object[]> results = entityManager.createNativeQuery(sqlTransOrder)
            .setParameter("transportPlanItemIds", transportPlanItemIds)
            .getResultList();

        if (!results.isEmpty()) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                TransportPlanMessage.CNS_LINE_ITEM_BY_DATE_HAVE_IN_TRANS_ORDER);
        }
    }

    /**
     * 輸送計画を削除する。
     *
     * @param id 輸送計画ID
     */
    @Override
    @Transactional
    public void deleteTransportPlan(Long id) {

        TransportPlan transportPlan = transportPlanRepository.getTransportPlanById(id);
        if (transportPlan == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, TransportPlanMessage.TRANSPORT_PLAN_NOT_FOUND);
        }

        List<TransportPlanItem> transportPlanItemsExist = transportPlanItemRepository.findByTransportPlanId(id);

        if (transportPlanItemsExist.isEmpty()) {
            transportPlanRepository.deleteById(id);
            return;
        }

        String sqlCheckMatching = "SELECT COUNT(*) FROM t_trans_order WHERE trsp_instruction_id = :transport_plan_id";
        Long matchingCount = ((Number) entityManager.createNativeQuery(sqlCheckMatching)
            .setParameter("transport_plan_id", id.toString()).getSingleResult()).longValue();

        if (matchingCount > 0) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                TransportPlanMessage.TRANSPORT_PLAN_DELETE_FAILED_MATCHING_EXISTS,
                id.toString());
        }

        deleteCnsLineItemByDateAndTransMatching(id);

        transportPlanRepository.deleteById(id);
    }
} 