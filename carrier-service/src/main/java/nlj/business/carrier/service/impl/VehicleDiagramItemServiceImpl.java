package nlj.business.carrier.service.impl;

import static com.next.logistic.security.constant.AuthConstant.API_KEY;
import static com.next.logistic.security.constant.AuthConstant.AUTHORIZATION;
import static nlj.business.carrier.mapper.DateTimeMapper.localDateTimeToJapaneseFormat;
import static nlj.business.carrier.mapper.DateTimeMapper.localDateTimeToJapaneseFormatHHMM;
import static nlj.business.carrier.mapper.DateTimeMapper.localDateTimeToString;
import static nlj.business.carrier.mapper.DateTimeMapper.stringToLocalDate;
import static nlj.business.carrier.mapper.DateTimeMapper.stringToLocalTime;
import static nlj.business.carrier.mapper.DateTimeMapper.timeToStringHHMM;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.data.dto.response.ReservationResponseDTO;
import com.next.logistic.data.util.CreateTrackBySipUtil;
import com.next.logistic.data.util.MobilityHubUtil;
import com.next.logistic.data.util.ValidateUtil;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.mail.util.NextMailUtil;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.constant.APIConstant.TransportOrder;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.constant.MessageConstant;
import nlj.business.carrier.constant.MessageConstant.Email;
import nlj.business.carrier.constant.MessageConstant.SemiDymanicInfoMessage;
import nlj.business.carrier.constant.MessageConstant.System;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.constant.ParamConstant.VEHICLE_DIAGRAM_ITEM_STATUS;
import nlj.business.carrier.constant.ParamConstant.ZLWeb;
import nlj.business.carrier.domain.CarrierOperator;
import nlj.business.carrier.domain.LocationMaster;
import nlj.business.carrier.domain.TransOrder;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramAllocation;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.domain.VehicleDiagramItemOperationTracking;
import nlj.business.carrier.domain.VehicleDiagramItemTracking;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.domain.VehicleInfo;
import nlj.business.carrier.dto.CutOffInfoDTO;
import nlj.business.carrier.dto.semiDynamicInfo.response.ValidateSemiDynamicInfoResponseDTO;
import nlj.business.carrier.dto.trackBySip.LogsSrvcPrv;
import nlj.business.carrier.dto.trackBySip.ShipFromPrty;
import nlj.business.carrier.dto.trackBySip.ShipToPrty;
import nlj.business.carrier.dto.trackBySip.TrspIsr;
import nlj.business.carrier.dto.trackBySip.TrspSrvc;
import nlj.business.carrier.dto.trackBySip.request.TrackBySipRequestDTO;
import nlj.business.carrier.dto.trackBySip.response.TrackBySipResponse;
import nlj.business.carrier.dto.transferStatus.ItemTrailerUpdateStatusRequestDTO;
import nlj.business.carrier.dto.transferStatus.ItemTrailerUpdateStatusResponse;
import nlj.business.carrier.dto.vehicleDiagram.response.CarrierServiceTransMatchingResponse;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.request.DiagramItemUpdateStatusRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.OrderTimeUpdateRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.TransOrderSearchRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateDiagramItemTimeRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateEmergencyRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdatePrivateDiagramItemRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateSipTrackIdRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateTrackStatusRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.VehicleDiagramItemDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.TransOrderSearchResponse;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemByAbilityDetailsDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerDTO;
import nlj.business.carrier.mapper.CutOffInfoMapper;
import nlj.business.carrier.mapper.VehicleDiagramItemMapper;
import nlj.business.carrier.mapper.VehicleDiagramItemOperationTrackingMapper;
import nlj.business.carrier.mapper.VehicleDiagramItemTrailerMapper;
import nlj.business.carrier.mapper.VehicleInfoMapper;
import nlj.business.carrier.repository.CarrierRepository;
import nlj.business.carrier.repository.LocationMasterRepository;
import nlj.business.carrier.repository.TransMatchingCustomRepository;
import nlj.business.carrier.repository.TransOrderCustomRepository;
import nlj.business.carrier.repository.TransOrderRepository;
import nlj.business.carrier.repository.VehicleDiagramAllocationRepository;
import nlj.business.carrier.repository.VehicleDiagramItemCustomRepository;
import nlj.business.carrier.repository.VehicleDiagramItemOperationTrackingRepository;
import nlj.business.carrier.repository.VehicleDiagramItemRepository;
import nlj.business.carrier.repository.VehicleDiagramItemTrackingRepository;
import nlj.business.carrier.repository.VehicleDiagramItemTrailerRepository;
import nlj.business.carrier.repository.VehicleDiagramMobilityHubRepository;
import nlj.business.carrier.repository.VehicleDiagramRepository;
import nlj.business.carrier.repository.VehicleInfoRepository;
import nlj.business.carrier.service.DataTransferYamatoService;
import nlj.business.carrier.service.VehicleDiagramItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 車両ダイアグラム明細サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleDiagramItemServiceImpl implements VehicleDiagramItemService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleDiagramItemServiceImpl.class);

    @Resource(name = "userContext")
    private final UserContext userContext;

    private final EntityManager entityManager;

    private final NljUrlProperties urlProperties;
    private final RestTemplate restTemplate;
    private final VehicleDiagramRepository vehicleDiagramRepository;
    private final VehicleDiagramItemRepository vehicleDiagramItemRepository;
    private final VehicleDiagramItemMapper vehicleDiagramItemMapper;
    private final VehicleDiagramItemCustomRepository vehicleDiagramItemCustomRepository;
    private final VehicleDiagramItemTrackingRepository vehicleDiagramItemTrackingRepository;
    private final VehicleDiagramItemOperationTrackingRepository vehicleDiagramItemOperationTrackingRepository;
    private final CarrierRepository carrierRepository;
    private final ValidateUtil validateUtil;
    private final CreateTrackBySipUtil createTrackBySipUtil;
    private final NextMailUtil nextMailUtil;
    private final VehicleDiagramAllocationRepository vehicleDiagramAllocationRepository;
    private final VehicleDiagramItemTrailerRepository vehicleDiagramItemTrailerRepository;
    private final VehicleInfoRepository vehicleInfoRepository;
    private final VehicleInfoMapper vehicleInfoMapper;
    private final VehicleDiagramItemTrailerMapper vehicleDiagramItemTrailerMapper;
    private final NljUrlProperties nljUrlProperties;
    private final NljAuthProperties authProperties;
    private final LocationMasterRepository locationMasterRepository;
    private final TransMatchingCustomRepository transMatchingCustomRepsitory;
    private final TransOrderRepository transOrderRepository;
    private final TransOrderCustomRepository transOrderCustomRepository;
    private final VehicleDiagramMobilityHubRepository vehicleDiagramMobilityHubRepository;
    private final MobilityHubUtil mobilityHubUtil;
    private final CutOffInfoMapper cutOffInfoMapper;

    private final DataTransferYamatoService dataTransferYamatoService;

    private final VehicleDiagramItemOperationTrackingMapper vehicleDiagramItemOperationTrackingMapper;

    /**
     * 車両ダイアグラム明細を登録する。
     *
     * @param vehicleDiagram         車両ダイアグラム
     * @param vehicleDiagramItemDTOs 車両ダイアグラム明細DTOのリスト
     */
    @Override
    @Transactional
    public void registerVehicleDiagramItems(VehicleDiagram vehicleDiagram,
        List<VehicleDiagramItemDTO> vehicleDiagramItemDTOs) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
        }
        String operatorId = user.getCompanyId();
        // Convert DTOs to entities
        List<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemDTOs.stream()
            .map(dto -> {
                VehicleDiagramItem item = vehicleDiagramItemMapper.toVehicleDiagramItem(dto);
                item.setOperatorId(operatorId);
                item.setDepartureFrom(vehicleDiagram.getDepartureFrom());
                item.setArrivalTo(vehicleDiagram.getArrivalTo());
                return item;
            })
            .collect(Collectors.toList());

        // Save all items
        vehicleDiagramItemRepository.saveAll(vehicleDiagramItems);
    }

    /**
     * 車両ダイアグラム明細を更新する。
     *
     * @param vehicleDiagram     車両ダイアグラム
     * @param vehicleDiagramItem 車両ダイアグラム明細
     */
    @Override
    @Transactional
    public void updateVehicleDiagramItem(VehicleDiagram vehicleDiagram, VehicleDiagramItem vehicleDiagramItem) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
        }
        String operatorId = user.getCompanyId();
        vehicleDiagramItem.setOperatorId(operatorId);
        vehicleDiagramItem.setDepartureFrom(vehicleDiagram.getDepartureFrom());
        vehicleDiagramItem.setArrivalTo(vehicleDiagram.getArrivalTo());
        vehicleDiagramItemRepository.save(vehicleDiagramItem);
    }

    /**
     * 車両ダイアグラム明細を追加する。
     *
     * @param vehicleDiagram        車両ダイアグラム
     * @param vehicleDiagramItemDTO 車両ダイアグラム明細DTO
     */
    @Override
    @Transactional
    public void addVehicleDiagramItem(VehicleDiagram vehicleDiagram, VehicleDiagramItemDTO vehicleDiagramItemDTO) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
        }
        String operatorId = user.getCompanyId();
        // Convert DTO to entity
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemMapper.toVehicleDiagramItem(vehicleDiagramItemDTO);
        vehicleDiagramItem.setOperatorId(operatorId);
        vehicleDiagramItem.setDepartureFrom(vehicleDiagram.getDepartureFrom());
        vehicleDiagramItem.setArrivalTo(vehicleDiagram.getArrivalTo());
        vehicleDiagramItemRepository.save(vehicleDiagramItem);
    }

    /**
     * 車両ダイアグラム明細の時間を更新する。
     *
     * @param diagramItemId 車両ダイアグラム明細ID
     * @param request       更新リクエスト
     */
    @Override
    public void updateDiagramItemTime(Long diagramItemId, UpdateDiagramItemTimeRequest request) {
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(diagramItemId);
        if (Objects.isNull(vehicleDiagramItem)) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, System.NOT_FOUND, "Vehicle diagram item");
        }
        VehicleDiagramItemByAbilityDetailsDTO dto = new VehicleDiagramItemByAbilityDetailsDTO();
        dto.setDay(vehicleDiagramItem.getDay().toString());
        dto.setDepartureTime(request.getDepartureTime());
        dto.setArrivalTime(request.getArrivalTime());
        dto.setDepartureFrom(vehicleDiagramItem.getDepartureFrom());
        dto.setArrivalTo(vehicleDiagramItem.getArrivalTo());
        dto.setTripName(vehicleDiagramItem.getTripName());
        checkTimeChange(vehicleDiagramItem, dto);

        vehicleDiagramItem.setDepartureTime(stringToLocalTime(request.getDepartureTime()));
        vehicleDiagramItem.setArrivalTime(stringToLocalTime(request.getArrivalTime()));
        vehicleDiagramItemRepository.save(vehicleDiagramItem);
        dataTransferYamatoService.transferDataDateTime(vehicleDiagramItem);
    }

    /**
     * 車両ダイアグラム明細を取得する。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @return 車両ダイアグラム明細のリスト
     */
    @Override
    public List<VehicleDiagramItemResponseDTO> getVehicleDiagramItems(Long vehicleDiagramId) {
        VehicleDiagram vehicleDiagram = vehicleDiagramRepository.findVehicleDiagramById(vehicleDiagramId);
        if (Objects.isNull(vehicleDiagram)) {
            return List.of();
        }

        List<VehicleDiagramItem> vehicleDiagramItemList =
            vehicleDiagramItemRepository.findAllByVehicleDiagram(vehicleDiagram);
        return vehicleDiagramItemList.stream()
            .map(vehicleDiagramItemMapper::toVehicleDiagramItemDTO).toList();
    }

    /**
     * 車両ダイアグラム明細を取得する。
     *
     * @param diagramId 車両ダイアグラムID
     * @return 車両ダイアグラム明細のリスト
     */
    @Override
    public List<VehicleDiagramItemResponseDTO> getItemsByDiagramId(Long diagramId) {
        List<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemRepository.findVehicleDiagramItemsByVehicleDiagramId(
            diagramId);
        return vehicleDiagramItems.stream()
            .map(vehicleDiagramItemMapper::toVehicleDiagramItemDTO)
            .collect(Collectors.toList());
    }

    /**
     * 車両ダイアグラム明細を削除する。
     *
     * @param diagramIds 車両ダイアグラムIDのリスト
     */
    @Override
    @Transactional
    public void deleteByDiagramIds(List<Long> diagramIds) {
        vehicleDiagramItemRepository.deleteByDiagramIds(diagramIds);
    }

    /**
     * 車両ダイアグラム明細を検証する。
     */
    @Override
    @Transactional
    public void validateVehicleDiagramItems() {
        int countRequest = 0;
        int countSuccess = 0;
        int countError = 0;
        List<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemCustomRepository.findListByDate();
        User user = userContext.getUser();
        countRequest = vehicleDiagramItems.size();
        if (vehicleDiagramItems != null && !vehicleDiagramItems.isEmpty()) {
            for (VehicleDiagramItem vehicleDiagramItem : vehicleDiagramItems) {
                int result = 0;
                if (!user.isDebug()) {
                    result = validateUtil.validateCommon(vehicleDiagramItem.getDay(),
                        vehicleDiagramItem.getDepartureTime(), vehicleDiagramItem.getArrivalTime());
                }
                if (result == SemiDymanicInfoMessage.ERROR_CODE_1) {
                    countSuccess++;
                    createTrackBySipUtil.createTrackBySip(true);
                } else {
                    vehicleDiagramItem.setStatus(result);
                    vehicleDiagramItemRepository.save(vehicleDiagramItem);
                    CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(
                        vehicleDiagramItem.getOperatorId());
                    if (!BaseUtil.isNull(carrierOperator.getEmail())) {
                        nextMailUtil.sendMail(carrierOperator.getEmail(),
                            "Vehicle item: " + vehicleDiagramItem.getId() + "-" + validateUtil.getErrorMessage(result),
                            "", "mail-template");
                    }
                    countError++;
                }
                VehicleDiagramItemTracking vehicleDiagramItemTracking = new VehicleDiagramItemTracking();
                vehicleDiagramItemTracking.setVehicleDiagramItemId(vehicleDiagramItem.getId());
                vehicleDiagramItemTracking.setOperationDate(vehicleDiagramItem.getDay());
                vehicleDiagramItemTracking.setOperationTime(vehicleDiagramItem.getDepartureTime());
                if (result == SemiDymanicInfoMessage.ERROR_CODE_0 || result == SemiDymanicInfoMessage.ERROR_CODE_1) {
                    vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_0);
                } else {
                    vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_1);
                }
                vehicleDiagramItemTracking.setMessage(validateUtil.getErrorMessage(result));
                vehicleDiagramItemTrackingRepository.save(vehicleDiagramItemTracking);

                try {
                    VehicleDiagramItemOperationTracking vehicleDiagramItemOperationTracking =
                        vehicleDiagramItemOperationTrackingMapper.toVehicleDiagramItemOperationTracking(
                            vehicleDiagramItemTracking);
                    vehicleDiagramItemOperationTrackingRepository.save(vehicleDiagramItemOperationTracking);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            }
        }
        logger.info("Request: {}, Success: {}, Error: {}", countRequest, countSuccess, countError);
    }

    /**
     * 車両ダイアグラム明細を取得する。
     *
     * @param diagramId  車両ダイアグラムID
     * @param operatorId オペレータID
     * @param pageable   ページング
     * @return 車両ダイアグラム明細のページ
     */
    @Override
    public Page<VehicleDiagramItem> findAllByVehicleDiagramIdAndOperatorId(Long diagramId, String operatorId,
        Pageable pageable) {
        return vehicleDiagramItemRepository.findAllByVehicleDiagramIdAndOperatorId(diagramId, operatorId, pageable);
    }

    /**
     * 車両ダイアグラム明細を取得する。
     *
     * @param id 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細
     */
    @Override
    public VehicleDiagramItem findById(Long id) {
        return vehicleDiagramItemRepository.findById(id).orElse(null);
    }

    /**
     * 車両ダイアグラム明細を取得する。
     *
     * @param vehicleDiagramItemIdT 車両ダイアグラム明細ID
     * @param binName               ビン名
     * @param transportDate         運送日
     * @param httpServletRequest    HTTPリクエスト
     * @return 車両ダイアグラム明細のDTO
     */
    @Override
    public VehicleDiagramItemByAbilityDetailsDTO getDetail(Long vehicleDiagramItemIdT, String binName,
        String transportDate, HttpServletRequest httpServletRequest) {
        VehicleDiagramItem vehicleDiagramItem = findById(vehicleDiagramItemIdT);
        Long vehicleDiagramItemId = vehicleDiagramItemIdT;
        if (vehicleDiagramItem == null) {
            //No Id, try best as I can
            Optional<VehicleDiagramItem> item = vehicleDiagramItemRepository.findFirstByTripNameAndDay(binName,
                LocalDate.parse(transportDate));
            vehicleDiagramItemId = item.map(VehicleDiagramItem::getId).orElse(null);
            vehicleDiagramItem = item.orElse(null);
        }

        if (vehicleDiagramItemId == null) {
            return new VehicleDiagramItemByAbilityDetailsDTO();
        }

        List<VehicleDiagramItemTrailer> trailers = vehicleDiagramItemTrailerRepository.findAllByVehicleDiagramItemId(
            vehicleDiagramItemId);
        List<VehicleDiagramItemTrailerDTO> trailerDTOList = new ArrayList<>();
        VehicleDiagramItemByAbilityDetailsDTO detailsDTO = vehicleDiagramItemMapper.toVehicleDiagramItemByAbilityDetailsDto(
            vehicleDiagramItem);
        setVehicleDiagramAllocations(detailsDTO, vehicleDiagramItem);
        if (!trailers.isEmpty()) {
            List<Long> trailerIds = trailers.stream().map(VehicleDiagramItemTrailer::getId).toList();
            List<CarrierServiceTransMatchingResponse> carrierServiceTransMatchingResponses =
                transMatchingCustomRepsitory.findAllByVehicleDiagramItemTrailerIdIn(
                    trailerIds);
            for (VehicleDiagramItemTrailer trailer : trailers) {
                VehicleDiagramItemTrailerDTO dto = vehicleDiagramItemTrailerMapper.toVehicleDiagramItemTrailerDto(
                    trailer);
                for (CarrierServiceTransMatchingResponse responseItem : carrierServiceTransMatchingResponses) {
                    if (dto.getId().equals(responseItem.getVehicleDiagramItemTrailerId())) {
                        dto.setTotalCount(responseItem.getTotalCount());
                        dto.setMatchingStatus(responseItem.getMatchingStatus());
                        dto.setOrderStatus(responseItem.getOrderStatus());
                    }
                }
                if (!detailsDTO.getVehicleDiagramAllocations().isEmpty()) {
                    detailsDTO.getVehicleDiagramAllocations().stream()
                        .filter(item -> item.getId().equals(trailer.getVehicleDiagramAllocation().getId()))
                        .findFirst()
                        .ifPresent(item -> dto.setDisplayOrder(item.getDisplayOrder()));
                }
                setOrderStatusAndTotalCountForTrailer(trailer, dto);
                trailerDTOList.add(dto);
            }
        }
        setCommonDetails(detailsDTO, vehicleDiagramItem, trailerDTOList);
        return detailsDTO;
    }

    /**
     * 車両ダイアグラム明細の注文ステータスとトータルカウントを設定する。
     *
     * @param trailer 車両ダイアグラム明細のトレイラー
     * @param dto     車両ダイアグラム明細のDTO
     * @return 車両ダイアグラム明細のDTO
     */
    @Override
    public VehicleDiagramItemTrailerDTO setOrderStatusAndTotalCountForTrailer(VehicleDiagramItemTrailer trailer,
        VehicleDiagramItemTrailerDTO dto) {
        String sqlQuery = "SELECT cut_off_time, cut_off_fee, vehicle_avb_resource_id, operator_id, id  FROM vehicle_avb_resource_item WHERE vehicle_diagram_item_trailer_id = :idTrailer ORDER BY cut_off_time LIMIT 1";
        Object[] vehicleAvbResource = (Object[]) entityManager.createNativeQuery(sqlQuery)
            .setParameter("idTrailer", trailer.getId())
            .getSingleResult();

        Long vehicleAvbResourceItemTopId = null;
        Long vehicleAvbResourceId = null;
        if (vehicleAvbResource != null && vehicleAvbResource.length == 5) {
            BigDecimal cutOffTime = (BigDecimal) vehicleAvbResource[0];
            BigDecimal cutOffFee = (BigDecimal) vehicleAvbResource[1];
            dto.setCutOffTime(cutOffTime);
            dto.setCutOffFee(cutOffFee);
            vehicleAvbResourceId = (Long) vehicleAvbResource[2];
            dto.setVehicleAvbResourceId(vehicleAvbResourceId);
            dto.setCarrierOperatorId((String) vehicleAvbResource[3]);
            vehicleAvbResourceItemTopId = (Long) vehicleAvbResource[4];
        }

        try {
            String sqlQueryOrderStatus = "SELECT status, carrier_operator_id, trans_type, vehicle_avb_resource_item_id, id FROM t_trans_order WHERE vehicle_diagram_item_trailer_id = :idTrailer LIMIT 1";
            Object[] result = (Object[]) entityManager.createNativeQuery(sqlQueryOrderStatus)
                .setParameter("idTrailer", trailer.getId())
                .getSingleResult();

            if (result != null && result.length == 5) {
                Integer status = (Integer) result[0];
                String carrierOperatorId = (String) result[1];
                Integer transType = (Integer) result[2];
                Long vehicleAvbResourceItemId = (Long) result[3];
                Long transOrderId = (Long) result[4];

                dto.setTransOrderId(transOrderId);
                dto.setOrderStatus(status);
                dto.setCarrierOperatorId(carrierOperatorId);
                dto.setTransType(transType);
                if (!Objects.equals(vehicleAvbResourceItemTopId, vehicleAvbResourceItemId)) {
                    String sqlVehicleAvbResourceItem = "SELECT cut_off_time, cut_off_fee FROM vehicle_avb_resource_item WHERE id = :vehicleAvbResourceItemId";
                    Object[] resultItem = (Object[]) entityManager.createNativeQuery(sqlVehicleAvbResourceItem)
                        .setParameter("vehicleAvbResourceItemId", vehicleAvbResourceItemId)
                        .getSingleResult();
                    if (resultItem != null && resultItem.length == 2) {
                        BigDecimal cutOffTime = (BigDecimal) resultItem[0];
                        BigDecimal cutOffFee = (BigDecimal) resultItem[1];
                        dto.setCutOffTime(cutOffTime);
                        dto.setCutOffFee(cutOffFee);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        try {
            String sqlQueryTotalCount = "SELECT COUNT(*) FROM t_trans_matching WHERE vehicle_avb_resource_id = :vehicleAvbResourceId AND status = 1";
            Long totalCount = (Long) entityManager.createNativeQuery(sqlQueryTotalCount)
                .setParameter("vehicleAvbResourceId", vehicleAvbResourceId)
                .getSingleResult();
            dto.setTotalCount(totalCount);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return dto;
    }

    /**
     * ヘッダーを設定する。
     *
     * @param token トークン
     * @return ヘッダー
     */
    private HttpHeaders setHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY, authProperties.getApiKey());
        headers.set(AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 車両ダイアグラム割当を設定する。
     *
     * @param detailsDTO         車両ダイアグラム明細のDTO
     * @param vehicleDiagramItem 車両ダイアグラム明細
     */
    private void setVehicleDiagramAllocations(VehicleDiagramItemByAbilityDetailsDTO detailsDTO,
        VehicleDiagramItem vehicleDiagramItem) {
        List<VehicleDiagramAllocation> allocations = vehicleDiagramAllocationRepository.findAllByVehicleDiagramId(
            vehicleDiagramItem.getVehicleDiagram().getId());
        if (!allocations.isEmpty()) {
            List<VehicleDiagramAllocationResDTO> allocationDTOs = allocations.stream()
                .map(this::mapToAllocationResDTO)
                .toList();
            detailsDTO.setVehicleDiagramAllocations(allocationDTOs);
        }
    }

    /**
     * 車両ダイアグラム割当をDTOにマッピングする。
     *
     * @param allocation 車両ダイアグラム割当
     * @return 車両ダイアグラム割当のDTO
     */
    private VehicleDiagramAllocationResDTO mapToAllocationResDTO(VehicleDiagramAllocation allocation) {
        VehicleDiagramAllocationResDTO allocationResDTO = new VehicleDiagramAllocationResDTO();
        Optional<VehicleInfo> vehicleInfoOptional = vehicleInfoRepository.findById(allocation.getVehicleInfoId());
        vehicleInfoOptional.ifPresent(
            vehicleInfo -> allocationResDTO.setVehicleInfoResponseDTO(vehicleInfoMapper.toResponseDto(vehicleInfo)));
        allocationResDTO.setId(allocation.getId());
        allocationResDTO.setVehicleType(allocation.getVehicleType());
        allocationResDTO.setDisplayOrder(allocation.getDisplayOrder());
        allocationResDTO.setAssignType(allocation.getAssignType());
        return allocationResDTO;
    }

    /**
     * 共通詳細を設定する。
     *
     * @param detailsDTO         車両ダイアグラム明細のDTO
     * @param vehicleDiagramItem 車両ダイアグラム明細
     * @param trailerDTOList     車両ダイアグラム明細のトレイラーのDTOのリスト
     */
    private void setCommonDetails(VehicleDiagramItemByAbilityDetailsDTO detailsDTO,
        VehicleDiagramItem vehicleDiagramItem, List<VehicleDiagramItemTrailerDTO> trailerDTOList) {
        VehicleDiagram vehicleDiagram = vehicleDiagramItem.getVehicleDiagram();
        detailsDTO.setStartDate(
            vehicleDiagram.getVehicleDiagramHead().getStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        detailsDTO.setEndDate(
            vehicleDiagram.getVehicleDiagramHead().getEndDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        detailsDTO.setRepeatDay(vehicleDiagram.getVehicleDiagramHead().getRepeatDay());
        detailsDTO.setTrailerNumber(vehicleDiagram.getVehicleDiagramHead().getTrailerNumber());
        detailsDTO.setIsRoundTrip(vehicleDiagram.getVehicleDiagramHead().getIsRoundTrip());
        detailsDTO.setOriginType(vehicleDiagram.getVehicleDiagramHead().getOriginType());
        detailsDTO.setImportId(vehicleDiagram.getVehicleDiagramHead().getImportId());
        detailsDTO.setOneWayTime(timeToStringHHMM(vehicleDiagramItem.getOneWayTime()));
        detailsDTO.setCreatedDate(localDateTimeToString(vehicleDiagramItem.getCreatedDate()));
        detailsDTO.setVehicleDiagramItemTrailers(trailerDTOList);
        detailsDTO.setIsPrivate(vehicleDiagramItem.getIsPrivate());
        detailsDTO.setSipTrackId(vehicleDiagramItem.getSipTrackId());
    }

    /**
     * 車両ダイアグラム明細を更新する。
     *
     * @param id  車両ダイアグラム明細ID
     * @param dto 車両ダイアグラム明細のDTO
     * @return 検証結果のDTO
     */
    @Override
    @Transactional
    public ValidateSemiDynamicInfoResponseDTO updateVehicleItem(Long id, VehicleDiagramItemByAbilityDetailsDTO dto) {
        User user = userContext.getUser();
        ValidateSemiDynamicInfoResponseDTO responseDTO = new ValidateSemiDynamicInfoResponseDTO();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        VehicleDiagramItem vehicleDiagramItem = findById(id);
        if (vehicleDiagramItem == null) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }

        // if time change, call api trackbysip
        int result = checkTimeChange(vehicleDiagramItem, dto);

        responseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
        responseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_0);
        if (result == SemiDymanicInfoMessage.ERROR_CODE_1) {
            responseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
            responseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_1);
        } else if (result != SemiDymanicInfoMessage.ERROR_CODE_0) {
            responseDTO.setStatus(SemiDymanicInfoMessage.STATUS_ERROR);
            if (result == SemiDymanicInfoMessage.ERROR_CODE_2) {
                responseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_2);
            } else if (result == SemiDymanicInfoMessage.ERROR_CODE_3) {
                responseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_3);
            } else if (result == SemiDymanicInfoMessage.ERROR_CODE_4) {
                responseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_4);
            } else if (result == SemiDymanicInfoMessage.ERROR_CODE_5) {
                responseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_5);
            }
        }

        vehicleDiagramItem.setTripName(dto.getTripName());
        vehicleDiagramItem.setDay(stringToLocalDate(dto.getDay()));
        vehicleDiagramItem.setDepartureTime(stringToLocalTime(dto.getDepartureTime()));
        vehicleDiagramItem.setArrivalTime(stringToLocalTime(dto.getArrivalTime()));
        //vehicleDiagramItem.setStatus(dto.getStatus());
        vehicleDiagramItem.setDepartureFrom(dto.getDepartureFrom());
        vehicleDiagramItem.setArrivalTo(dto.getArrivalTo());
        vehicleDiagramItem.setOneWayTime(stringToLocalTime(dto.getOneWayTime()));
        vehicleDiagramItemRepository.save(vehicleDiagramItem);
        List<VehicleDiagramAllocationResDTO> vehicleDiagramAllocations = dto.getVehicleDiagramAllocations();
        Map<Long, VehicleDiagramAllocationResDTO> allocationMap = vehicleDiagramAllocations.stream()
            .collect(Collectors.toMap(VehicleDiagramAllocationResDTO::getId, Function.identity()));
        List<VehicleDiagramAllocation> vehicleDiagramAllocationsList = vehicleDiagramAllocationRepository.findAllByVehicleDiagramId(
            vehicleDiagramItem.getVehicleDiagram().getId());
        for (VehicleDiagramAllocation vehicleDiagramAllocation : vehicleDiagramAllocationsList) {
            if (allocationMap.get(vehicleDiagramAllocation.getId()) != null) {
                if (allocationMap.get(vehicleDiagramAllocation.getId()).getVehicleInfoResponseDTO() != null) {
                    Long vehicleInfoId = allocationMap.get(vehicleDiagramAllocation.getId()).getVehicleInfoResponseDTO()
                        .getId();
                    vehicleDiagramAllocation.setVehicleInfoId(vehicleInfoId);
                }
                vehicleDiagramAllocation.setVehicleType(
                    allocationMap.get(vehicleDiagramAllocation.getId()).getVehicleType());
                vehicleDiagramAllocation.setDisplayOrder(
                    allocationMap.get(vehicleDiagramAllocation.getId()).getDisplayOrder());
                vehicleDiagramAllocation.setAssignType(
                    allocationMap.get(vehicleDiagramAllocation.getId()).getAssignType());
            }
        }
        vehicleDiagramAllocationRepository.saveAll(vehicleDiagramAllocationsList);

        List<VehicleDiagramItemTrailerDTO> vehicleDiagramItemTrailers = dto.getVehicleDiagramItemTrailers();
        Map<Long, VehicleDiagramItemTrailerDTO> trailerDTOMap = vehicleDiagramItemTrailers.stream()
            .collect(Collectors.toMap(VehicleDiagramItemTrailerDTO::getId, Function.identity()));
        List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailerList =
            vehicleDiagramItemTrailerRepository.findAllByVehicleDiagramItemId(id);
        for (VehicleDiagramItemTrailer vehicleDiagramItemTrailer : vehicleDiagramItemTrailerList) {
            if (trailerDTOMap.get(vehicleDiagramItemTrailer.getId()) != null) {
                vehicleDiagramItemTrailer.setDay(
                    stringToLocalDate(trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getDay()));
                vehicleDiagramItemTrailer.setTripName(
                    trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getTripName());
                //vehicleDiagramItemTrailer.setDepartureTime(stringToLocalTime(trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getDepartureTime()));
                vehicleDiagramItemTrailer.setDepartureTime(stringToLocalTime(dto.getDepartureTime()));
                //vehicleDiagramItemTrailer.setArrivalTime(stringToLocalTime(trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getArrivalTime()));
                vehicleDiagramItemTrailer.setArrivalTime(stringToLocalTime(dto.getArrivalTime()));
                vehicleDiagramItemTrailer.setPrice(trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getPrice());
                //vehicleDiagramItemTrailer.setStatus(trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getStatus());
                vehicleDiagramItemTrailer.setCutOffPrice(
                    trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getCutOffPrice());
                //vehicleDiagramItemTrailer.setMobilityHubId(trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getMobilityHubId());
                //vehicleDiagramItemTrailer.setValidFrom(stringToLocalDateTime(trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getValidFrom())) ;
                //vehicleDiagramItemTrailer.setValidUntil(stringToLocalDateTime(trailerDTOMap.get(vehicleDiagramItemTrailer.getId()).getValidUntil()));
            }
        }
        vehicleDiagramItemTrailerRepository.saveAll(vehicleDiagramItemTrailerList);
        dataTransferYamatoService.transferDataDateTime(vehicleDiagramItem);
        return responseDTO;
    }

    /**
     * 時間変更をチェックする。
     *
     * @param vehicleDiagramItem 車両ダイアグラム明細
     * @param dto                車両ダイアグラム明細のDTO
     * @return 検証結果
     */
    private int checkTimeChange(VehicleDiagramItem vehicleDiagramItem, VehicleDiagramItemByAbilityDetailsDTO dto) {
        boolean isDepartureTimeChange = !Objects.equals(
            vehicleDiagramItem.getDepartureTime(),
            dto.getDepartureTime() != null ? stringToLocalTime(dto.getDepartureTime()) : null
        );

        boolean isArriveTimeChange = !Objects.equals(
            vehicleDiagramItem.getArrivalTime(),
            dto.getArrivalTime() != null ? stringToLocalTime(dto.getArrivalTime()) : null
        );
        if (isDepartureTimeChange || isArriveTimeChange) {
            int incidentType = vehicleDiagramItem.getIncidentType() != null ? vehicleDiagramItem.getIncidentType() : 0;
            // send mail to shipper
            List<String> operatorIds = transOrderCustomRepository.findOperatorIdByVehicleDiagramItemId(
                vehicleDiagramItem.getId());
            if (!operatorIds.isEmpty()) {
                // send mail to shipper
                if (operatorIds.get(0) != null) {
                    String sqlQuery = "SELECT email FROM s_shipper_operator WHERE id = :shipperOperatorId";
                    Object result = entityManager.createNativeQuery(sqlQuery, String.class)
                        .setParameter("shipperOperatorId", operatorIds.get(0))
                        .getSingleResult();
                    String emailShipper = result != null ? result.toString() : null;
                    if (emailShipper != null) {
                        sendMailByIncidentType(vehicleDiagramItem, dto, incidentType, emailShipper,
                            isDepartureTimeChange, isArriveTimeChange);
                    }
                }
                // send mail to carrier
                if (operatorIds.get(1) != null) {
                    CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(
                        operatorIds.get(1));
                    if (carrierOperator != null) {
                        String emailCarrier = carrierOperator.getEmail();
                        sendMailByIncidentType(vehicleDiagramItem, dto, incidentType, emailCarrier,
                            isDepartureTimeChange, isArriveTimeChange);
                    }
                }
                // send mail to carrier 2
                if (operatorIds.get(2) != null) {
                    CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(
                        operatorIds.get(2));
                    if (carrierOperator != null) {
                        String emailCarrier2 = carrierOperator.getEmail();
                        sendMailByIncidentType(vehicleDiagramItem, dto, incidentType, emailCarrier2,
                            isDepartureTimeChange, isArriveTimeChange);
                    }
                }
            }
            // update incident vehicle diagram item
            vehicleDiagramItem.setIncidentType(ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_0);
            vehicleDiagramItem.setIncidentMsg(null);
            vehicleDiagramItemRepository.save(vehicleDiagramItem);

            List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = vehicleDiagramItemTrailerRepository.
                findVehicleDiagramItemTrailersByVehicleDiagramItem(vehicleDiagramItem);

            if (!vehicleDiagramItemTrailers.isEmpty()) {
                vehicleDiagramItemTrailers.forEach(item -> {
                    if (isDepartureTimeChange && dto.getDepartureTime() != null) {
                        item.setDepartureTime(stringToLocalTime(dto.getDepartureTime()));
                    }
                    if (isArriveTimeChange && dto.getArrivalTime() != null) {
                        item.setArrivalTime(stringToLocalTime(dto.getArrivalTime()));
                    }
                });
                vehicleDiagramItemTrailerRepository.saveAll(vehicleDiagramItemTrailers);
            }

            //Update Mobility-Hub
            callMobilityHub(vehicleDiagramItem, dto);

            if (vehicleDiagramItem.getStatus() != null && (
                vehicleDiagramItem.getStatus() > ParamConstant.VehicleDiagramItemStatus.STATUS_0)) {

                // update time in trans-order
                List<TransOrder> transOrders = transOrderRepository.findAllByVehicleDiagramItemId(
                    vehicleDiagramItem.getId());
                if (!transOrders.isEmpty()) {
                    transOrders.forEach(transOrder -> {
                        if (isDepartureTimeChange && dto.getDepartureTime() != null) {
                            transOrder.setDepartureTime(stringToLocalTime(dto.getDepartureTime()));
                        }
                        if (isArriveTimeChange && dto.getArrivalTime() != null) {
                            transOrder.setArrivalTime(stringToLocalTime(dto.getArrivalTime()));
                        }
                    });
                    transOrderRepository.saveAll(transOrders);
                    try {
                        updateTime(vehicleDiagramItem.getId(), dto.getDepartureTime(), dto.getArrivalTime());
                    } catch (Exception e) {
                        log.error("Failed to update time to ZLWeb", e);
                    }
                }

                // Call API update Mobility-Hub
                TrackBySipRequestDTO trackBySipRequestDTO = new TrackBySipRequestDTO();
                HttpHeaders headers = new HttpHeaders();
                TrspIsr trspIsr = new TrspIsr();
                trspIsr.setTrspInstructionId(String.valueOf(vehicleDiagramItem.getId()));
                trackBySipRequestDTO.setTrspIsr(trspIsr);

                TrspSrvc trspSrvc = new TrspSrvc();
                trspSrvc.setApedArrToDate(BaseUtil.convertLocalDateToString(vehicleDiagramItem.getDay()));
                trspSrvc.setAvbDateCllDate(BaseUtil.convertLocalDateToString(vehicleDiagramItem.getDay()));
                trspSrvc.setAvbFromTimeOfCllTime(dto.getDepartureTime());
                trspSrvc.setApedArrToTimePrfmDttm(dto.getArrivalTime());

                LogsSrvcPrv logsSrvcPrv = new LogsSrvcPrv();
                logsSrvcPrv.setLogsSrvcPrvPrtyNameTxt(dto.getTripName());

                ShipFromPrty shipFromPrty = new ShipFromPrty();
                shipFromPrty.setShipFromPrtyBrncOffId(String.valueOf(dto.getDepartureFrom()));

                ShipToPrty shipToPrty = new ShipToPrty();
                shipToPrty.setShipToPrtyBrncOffId(String.valueOf(dto.getArrivalTo()));

                trackBySipRequestDTO.setTrspSrvc(trspSrvc);
                trackBySipRequestDTO.setTrspSrvc(trspSrvc);
                trackBySipRequestDTO.setLogsSrvcPrv(logsSrvcPrv);
                trackBySipRequestDTO.setShipFromPrty(shipFromPrty);
                trackBySipRequestDTO.setShipToPrty(shipToPrty);

                HttpEntity<TrackBySipRequestDTO> requestEntity = new HttpEntity<>(trackBySipRequestDTO, headers);
                String domainGatewayTTMITrackBySip = urlProperties.getDomainGateway() + "/api/v1/track/sip";
                try {
                    ResponseEntity<TrackBySipResponse> response = restTemplate.exchange(
                        domainGatewayTTMITrackBySip,
                        vehicleDiagramItem.getSipTrackId() != null ? HttpMethod.PUT : HttpMethod.POST,
                        requestEntity,
                        TrackBySipResponse.class
                    );
                    if (response.getBody() != null && response.getBody().getId() != null) {
                        vehicleDiagramItem.setSipTrackId(response.getBody().getId());
                    }
                    log.info("SUCCESS call Gateway-TTMI track by sip: {}", response);
                } catch (Exception e) {
                    log.error("ERROR call Gateway-TTMI track by sip : {}", e.getMessage());
                }
            }
            // validate semidynamic info
            if (vehicleDiagramItem.getStatus() != null && (
                vehicleDiagramItem.getStatus() == ParamConstant.VehicleDiagramItemStatus.STATUS_0
                    || vehicleDiagramItem.getStatus() == ParamConstant.VehicleDiagramItemStatus.STATUS_1)) {
                return validateUtil.validateCommon(vehicleDiagramItem.getDay(),
                    stringToLocalTime(dto.getDepartureTime()),
                    stringToLocalTime(dto.getArrivalTime()));
            } else {
                return SemiDymanicInfoMessage.ERROR_CODE_0;
            }
        } else {
            log.info("Not Updated because DepartureTime And ArrivalTime Not Change");
        }
        return SemiDymanicInfoMessage.ERROR_CODE_0;
    }

    /**
     * 車両ダイアグラム明細を削除する。
     *
     * @param id 車両ダイアグラム明細ID
     */
    @Override
    @Transactional
    public void deleteVehicleItem(Long id) {
        if (BaseUtil.isNull(String.valueOf(id))) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = vehicleDiagramItemTrailerRepository
            .findAllByVehicleDiagramItemId(id);
        List<Long> trailerIds = vehicleDiagramItemTrailers.stream()
            .map(VehicleDiagramItemTrailer::getId)
            .collect(Collectors.toList());
        if (trailerIds != null) {
            String sqlCheckTransOrder = "SELECT vehicle_diagram_item_trailer_id FROM t_trans_order WHERE vehicle_diagram_item_trailer_id IN :trailerIds";
            List<Object[]> results = entityManager.createNativeQuery(sqlCheckTransOrder)
                .setParameter("trailerIds", trailerIds)
                .getResultList();

            if (!results.isEmpty()) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                    System.VEHICLE_DIAGRAM_ITEM_TRAILER_HAVE_IN_TRANS_ORDER);
            }

            String sqlDeleteVehicleAvb = "DELETE FROM vehicle_avb_resource_item WHERE vehicle_diagram_item_trailer_id IN :trailerIds";
            entityManager.createNativeQuery(sqlDeleteVehicleAvb)
                .setParameter("trailerIds", trailerIds)
                .executeUpdate();

            String sqlDeleteTransMatching = "DELETE FROM t_trans_matching WHERE vehicle_diagram_item_trailer_id IN :trailerIds";
            entityManager.createNativeQuery(sqlDeleteTransMatching)
                .setParameter("trailerIds", trailerIds)
                .executeUpdate();
            vehicleDiagramItemTrailerRepository.deleteAllByVehicleDiagramItemId(id);
        }
        vehicleDiagramItemRepository.deleteByVehicleDiagramItemId(id);
    }

    /**
     * 車両ダイアグラム明細のステータスを更新する。
     *
     * @param dto 車両ダイアグラム明細のDTO
     * @return 更新結果
     */
    @Override
    @Transactional
    public ItemTrailerUpdateStatusResponse updateItemTrailerStatus(ItemTrailerUpdateStatusRequestDTO dto) {
        ItemTrailerUpdateStatusResponse response = new ItemTrailerUpdateStatusResponse();
        if (BaseUtil.isNull(String.valueOf(dto.getId())) || BaseUtil.isNull(String.valueOf(dto.getStatus()))) {
            response.setStatus(ParamConstant.Status.FAIL);
            return response;
        }
        VehicleDiagramItemTrailer vehicleDiagramItemTrailer = vehicleDiagramItemTrailerRepository.findById(dto.getId())
            .orElse(null);
        if (vehicleDiagramItemTrailer == null) {
            response.setStatus(ParamConstant.Status.FAIL);
            return response;
        }

        vehicleDiagramItemTrailer.setStatus(dto.getStatus());
        vehicleDiagramItemTrailerRepository.save(vehicleDiagramItemTrailer);
        response.setStatus(ParamConstant.Status.SUCCESS);
        return response;
    }

    /**
     * SipトラックIDを更新する。
     *
     * @param request 更新リクエスト
     * @return 更新結果
     */
    @Override
    @Transactional
    public String updateSipTrackId(UpdateSipTrackIdRequest request) {
        VehicleDiagramItemTrailer vehicleDiagramItemTrailer = vehicleDiagramItemTrailerRepository.findById(
            request.getVehicleDiagramItemTrailerId()).orElse(null);
        if (vehicleDiagramItemTrailer == null) {
            return ParamConstant.Status.FAIL;
        }
        if (vehicleDiagramItemTrailer.getVehicleDiagramItem() != null) {
            VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemTrailer.getVehicleDiagramItem();
            vehicleDiagramItem.setSipTrackId(request.getSipTrackId());
            vehicleDiagramItemRepository.save(vehicleDiagramItem);
        }
        return ParamConstant.Status.SUCCESS;
    }

    /**
     * 車両ダイアグラム明細の出発時間と到着時間を取得する。
     *
     * @param diagramItemTrailerId 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細の出発時間と到着時間のDTO
     */
    @Override
    public DiagramItemDepartureArrivalTimeDTO getDepartureArrivalTime(Long diagramItemTrailerId) {
        VehicleDiagramItemTrailer vehicleDiagramItemTrailer = vehicleDiagramItemTrailerRepository.findById(
            diagramItemTrailerId).orElse(null);
        if (vehicleDiagramItemTrailer == null) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemTrailer.getVehicleDiagramItem();
        DiagramItemDepartureArrivalTimeDTO response = new DiagramItemDepartureArrivalTimeDTO();
        response.setArrivalTime(vehicleDiagramItem.getArrivalTime());
        response.setDepartureTime(vehicleDiagramItem.getDepartureTime());
        return response;
    }

    /**
     * 車両ダイアグラム明細の出発時間と到着時間を取得する。
     *
     * @param diagramItemId 車両ダイアグラム明細ID
     * @return 車両ダイアグラム明細の出発時間と到着時間のDTO
     */
    @Override
    public DiagramItemDepartureArrivalTimeDTO getDepartureArrivalTimeByDiagramItemId(Long diagramItemId) {
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findById(diagramItemId).orElse(null);
        if (vehicleDiagramItem == null) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(
            vehicleDiagramItem.getOperatorId());
        DiagramItemDepartureArrivalTimeDTO response = new DiagramItemDepartureArrivalTimeDTO();
        response.setOperationDate(vehicleDiagramItem.getDay());
        response.setArrivalTime(vehicleDiagramItem.getArrivalTime());
        response.setDepartureTime(vehicleDiagramItem.getDepartureTime());
        response.setCarrierMail(carrierOperator.getEmail());
        return response;
    }

    /**
     * 車両ダイアグラム明細のステータスを更新する。
     *
     * @param diagramItemId 車両ダイアグラム明細ID
     * @param type          タイプ
     * @param request       更新リクエスト
     * @return 更新結果
     */
    @Override
    public String updateVehicleStatus(Long diagramItemId, Long type, UpdateTrackStatusRequest request) {
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(diagramItemId);
        if (vehicleDiagramItem == null) {
            return ParamConstant.Status.FAIL;
        }
        VehicleDiagramItemTracking vehicleDiagramItemTracking = new VehicleDiagramItemTracking();
        vehicleDiagramItemTracking.setVehicleDiagramItemId(vehicleDiagramItem.getId());
        vehicleDiagramItemTracking.setOperationDate(vehicleDiagramItem.getDay());

        LocalTime operationTime = LocalTime.now();
        vehicleDiagramItemTracking.setOperationTime(operationTime);
        if (ParamConstant._72H.equals(type)) {
            vehicleDiagramItemTracking.setOperationDate(vehicleDiagramItem.getDay().minusDays(3));
            vehicleDiagramItemTracking.setOperationTime(vehicleDiagramItem.getDepartureTime());
            //vehicleDiagramItemTracking.setOperationTime(vehicleDiagramItem.getDepartureTime().minusHours(ParamConstant._72H));
        }
        if (ParamConstant._3H.equals(type)) {
            if (vehicleDiagramItem.getDepartureTime().isBefore(LocalTime.of(3, 0))) {
                vehicleDiagramItemTracking.setOperationDate(vehicleDiagramItem.getDay().minusDays(1));
            }
            vehicleDiagramItemTracking.setOperationTime(
                vehicleDiagramItem.getDepartureTime().minusHours(ParamConstant._3H));
        }
        if (ParamConstant._2H.equals(type)) {
            if (vehicleDiagramItem.getDepartureTime().isBefore(LocalTime.of(2, 0))) {
                vehicleDiagramItemTracking.setOperationDate(vehicleDiagramItem.getDay().minusDays(1));
            }
            vehicleDiagramItemTracking.setOperationTime(
                vehicleDiagramItem.getDepartureTime().minusHours(ParamConstant._2H));
        }
        if (ParamConstant._1H.equals(type)) {
            if (vehicleDiagramItem.getDepartureTime().isBefore(LocalTime.of(1, 0))) {
                vehicleDiagramItemTracking.setOperationDate(vehicleDiagramItem.getDay().minusDays(1));
            }
            vehicleDiagramItemTracking.setOperationTime(
                vehicleDiagramItem.getDepartureTime().minusHours(ParamConstant._1H));
        }
        switch (request.getStatus()) {
            case ParamConstant.Status.START:
                vehicleDiagramItemTracking.setOperationTime(vehicleDiagramItem.getDepartureTime());
                vehicleDiagramItem.setStatus(ParamConstant.Status.DIAGRAM_START);
                vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_0);
                vehicleDiagramItemTracking.setMessage(validateUtil.getMessageInfo(
                    SemiDymanicInfoMessage.ERROR_CODE_25,
                    locationMasterRepository.findLocationMasterByCode(
                        String.valueOf(vehicleDiagramItem.getDepartureFrom())).getName()));
                break;
            case ParamConstant.Status.END:
                if (vehicleDiagramItem.getArrivalTime().isBefore(vehicleDiagramItem.getDepartureTime())) {
                    vehicleDiagramItemTracking.setOperationDate(vehicleDiagramItem.getDay().plusDays(1));
                }
                vehicleDiagramItem.setStatus(ParamConstant.Status.DIAGRAM_END);
                vehicleDiagramItemTracking.setOperationTime(vehicleDiagramItem.getArrivalTime());
                vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_2);
                vehicleDiagramItemTracking.setMessage(validateUtil.getMessageInfo(
                    SemiDymanicInfoMessage.ERROR_CODE_26,
                    locationMasterRepository.findLocationMasterByCode(
                        String.valueOf(vehicleDiagramItem.getArrivalTo())).getName()));
                break;
            case ParamConstant.Status.ERROR:
                //vehicleDiagramItemTracking.setOperationTime(operationTime);
                vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_1);
                vehicleDiagramItemTracking.setMessage(request.getMessage());
                break;
            default:
                //vehicleDiagramItemTracking.setOperationTime(operationTime);
                vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_0);
                vehicleDiagramItemTracking.setMessage(request.getMessage());
                break;
        }
        vehicleDiagramItemRepository.save(vehicleDiagramItem);
        vehicleDiagramItemTrackingRepository.save(vehicleDiagramItemTracking);

        try {
            VehicleDiagramItemOperationTracking vehicleDiagramItemOperationTracking = vehicleDiagramItemOperationTrackingMapper.toVehicleDiagramItemOperationTracking(
                vehicleDiagramItemTracking);
            vehicleDiagramItemOperationTrackingRepository.save(vehicleDiagramItemOperationTracking);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ParamConstant.Status.SUCCESS;
    }

    /**
     * 車両ダイアグラム明細をプライベートに設定する。
     *
     * @param id                 車両ダイアグラム明細ID
     * @param request            更新リクエスト
     * @param httpServletRequest HTTPリクエスト
     */
    @Override
    @Transactional
    public void updatePrivateDiagramItem(Long id, UpdatePrivateDiagramItemRequest request,
        HttpServletRequest httpServletRequest) {
        VehicleDiagramItem vehicleDiagramItem = findById(id);
        if (vehicleDiagramItem == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                MessageConstant.VehicleDiagramMessage.VEHICLE_DIAGRAM_ITEM_NOT_FOUND);
        }
        TransOrderSearchRequest searchRequest = new TransOrderSearchRequest();
        searchRequest.setVehicleDiagramItemTrailerId(request.getVehicleDiagramItemTrailerId());
        RestTemplate restTemplate = new RestTemplate();
        String urlTransaction = nljUrlProperties.getDomainTransaction() + TransportOrder.SEARCH_BY_TRAILER_ID;
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        HttpHeaders headers = setHeader(token);
        HttpEntity<TransOrderSearchRequest> requestEntity = new HttpEntity<>(searchRequest, headers);
        ResponseEntity<TransOrderSearchResponse> response = restTemplate.exchange(urlTransaction, HttpMethod.POST,
            requestEntity,
            TransOrderSearchResponse.class);
        if (response.getBody() != null && (!"Exist".equalsIgnoreCase(response.getBody().getData()))) {
            vehicleDiagramItem.setIsPrivate(request.getIsPrivate());
            vehicleDiagramItemRepository.save(vehicleDiagramItem);
        }
        if (response.getBody() != null && ("Exist".equalsIgnoreCase(response.getBody().getData()))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                MessageConstant.VehicleDiagramMessage.VEHICLE_DIAGRAM_ITEM_CAN_NOT_UPDATE_PRIVATE);
        }
    }

    /**
     * 車両ダイアグラム明細を緊急に設定する。
     *
     * @param id      車両ダイアグラム明細ID
     * @param request 更新リクエスト
     */
    @Override
    @Transactional
    public void updateEmergencyDiagramItem(Long id, UpdateEmergencyRequest request) {
        VehicleDiagramItem vehicleDiagramItem = findById(id);
        if (vehicleDiagramItem == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                MessageConstant.VehicleDiagramMessage.VEHICLE_DIAGRAM_ITEM_NOT_FOUND);
        }
        vehicleDiagramItem.setIsEmergency(request.getIsEmergency());
        vehicleDiagramItemRepository.save(vehicleDiagramItem);
        String transOrderSql = "SELECT cns_line_item_by_date_id FROM t_trans_order WHERE vehicle_diagram_item_id = :vehicleDiagramItemId";

        List<Long> cnsLineItemByDateIds = entityManager.createNativeQuery(transOrderSql)
            .setParameter("vehicleDiagramItemId", vehicleDiagramItem.getId())
            .getResultList();

        if (!cnsLineItemByDateIds.isEmpty()) {
            String cnsLineItemByDateSql = "UPDATE cns_line_item_by_date SET is_emergency = :isEmergency WHERE id IN :cnsLineItemByDateIds";
            entityManager.createNativeQuery(cnsLineItemByDateSql)
                .setParameter("cnsLineItemByDateIds", cnsLineItemByDateIds)
                .setParameter("isEmergency", request.getIsEmergency())
                .executeUpdate();
        }
    }

    /**
     * 車両ダイアグラム明細のステータスを更新する。
     */
    @Override
    @Transactional
    public void updateStatusVehicleDiagramItemsJob() {
        // Start
        updateStatusVehicleDiagramItem(ParamConstant.VehicleDiagramItemStatus.START);
        // End
        updateStatusVehicleDiagramItem(ParamConstant.VehicleDiagramItemStatus.END);
    }

    /**
     * 車両ダイアグラム明細のステータスを更新する
     *
     * @param status 車両ダイアグラム明細のステータス
     */
    private void updateStatusVehicleDiagramItem(Integer status) {
        try {
            List<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemCustomRepository.findAllVehicleDiagramItemByStatus(
                status);
            for (VehicleDiagramItem vehicleDiagramItem : vehicleDiagramItems) {
                if (status == ParamConstant.VehicleDiagramItemStatus.START) {
                    vehicleDiagramItem.setStatus(ParamConstant.VehicleDiagramItemStatus.STATUS_2);
                } else if (status == ParamConstant.VehicleDiagramItemStatus.END) {
                    vehicleDiagramItem.setStatus(ParamConstant.VehicleDiagramItemStatus.STATUS_3);
                }
                List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = vehicleDiagramItemTrailerRepository.findAllByVehicleDiagramItemId(
                    vehicleDiagramItem.getId());
                if (!vehicleDiagramItemTrailers.isEmpty()) {
                    List<TransOrder> transOrders = transOrderRepository.findAllByVehicleDiagramItemTrailerIdIn(
                        vehicleDiagramItemTrailers.stream().map(VehicleDiagramItemTrailer::getId).toList());

                    for (TransOrder transOrder : transOrders) {
                        if (status.equals(ParamConstant.VehicleDiagramItemStatus.START)) {
                            if (transOrder.getTransType() == ParamConstant.TransOrderStatus.TRANS_TYPE_0) {
                                transOrder.setStatus(ParamConstant.TransOrderStatus.SHIPPER_START_TRANSPORT);
                            } else if (transOrder.getTransType() == ParamConstant.TransOrderStatus.TRANS_TYPE_1) {
                                transOrder.setStatus(ParamConstant.TransOrderStatus.START_TRANSPORT);
                            }
                        } else if (status.equals(ParamConstant.VehicleDiagramItemStatus.END)) {
                            if (transOrder.getTransType() == ParamConstant.TransOrderStatus.TRANS_TYPE_0) {
                                transOrder.setStatus(ParamConstant.TransOrderStatus.SHIPPER_COMPLETE_TRANSPORT);
                            } else if (transOrder.getTransType() == ParamConstant.TransOrderStatus.TRANS_TYPE_1) {
                                transOrder.setStatus(ParamConstant.TransOrderStatus.COMPLETE_TRANSPORT);
                            }
                        }
                        transOrderRepository.save(transOrder);
                    }
                }
                vehicleDiagramItemRepository.save(vehicleDiagramItem);
            }
        } catch (Exception e) {
            logger.error("updateStatusVehicleDiagramItem Exception: {}", e.getMessage());
        }
    }

    /**
     * 車両ダイアグラム明細のステータスを更新する
     *
     * @param vehicleDiagramItem    車両ダイアグラム明細
     * @param dto                   車両ダイアグラム明細のDTO
     * @param incidentType          インシデントタイプ
     * @param emailTo               メールアドレス
     * @param isDepartureTimeChange 出発時間変更フラグ
     * @param isArrivalTimeChange   到着時間変更フラグ
     */
    private void sendMailByIncidentType(VehicleDiagramItem vehicleDiagramItem,
        VehicleDiagramItemByAbilityDetailsDTO dto, int incidentType, String emailTo, boolean isDepartureTimeChange,
        boolean isArrivalTimeChange) {
        try {
            LocationMaster locationMasterDeparture = locationMasterRepository.findLocationMasterByCode(
                String.valueOf(vehicleDiagramItem.getDepartureFrom()));
            LocationMaster locationMasterArrival = locationMasterRepository.findLocationMasterByCode(
                String.valueOf(vehicleDiagramItem.getArrivalTo()));
            List<String> data = new ArrayList<>();
            data.add(vehicleDiagramItem.getTripName());
            String conveyanceNumber = "";
            if (locationMasterDeparture != null) {
                conveyanceNumber = locationMasterDeparture.getName();
            } else {
                conveyanceNumber = vehicleDiagramItem.getDepartureFrom().toString();
            }
            if (locationMasterArrival != null) {
                conveyanceNumber = conveyanceNumber + " ～ " + locationMasterArrival.getName();
            } else {
                conveyanceNumber = conveyanceNumber + " ～ " + vehicleDiagramItem.getArrivalTo().toString();
            }
            data.add(conveyanceNumber);
            String lateTime = null;
            String arrivalTime = localDateTimeToJapaneseFormatHHMM(vehicleDiagramItem.getArrivalTime());
            if (isDepartureTimeChange) {
                data.add(localDateTimeToJapaneseFormat(
                    LocalDateTime.of(vehicleDiagramItem.getDay(),
                        stringToLocalTime(dto.getDepartureTime()))));
            } else {
                data.add(localDateTimeToJapaneseFormat(
                    LocalDateTime.of(vehicleDiagramItem.getDay(), vehicleDiagramItem.getDepartureTime())));
            }

            if (isArrivalTimeChange && incidentType != ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_0) {
                Duration duration = Duration.between(vehicleDiagramItem.getArrivalTime(),
                    stringToLocalTime(dto.getArrivalTime()));
                long minutes = duration.toMinutes();

                if (minutes <= 60) {
                    lateTime = minutes + "分";
                } else {
                    long hours = minutes / 60;
                    long remainingMinutes = minutes % 60;
                    lateTime = hours + "時間" + (remainingMinutes > 0 ? remainingMinutes + "分" : "");
                }
                arrivalTime = localDateTimeToJapaneseFormatHHMM(stringToLocalTime(dto.getArrivalTime()));
            }
            if (incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_2) {
                // send mail weather error
                data.add(lateTime);
                nextMailUtil.sendMailByType(emailTo, Email.MAIL_WEATHER_STOP, data);
            } else if (incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_3) {
                // send mail traffic stop
                data.add(lateTime);
                data.add(arrivalTime);
                nextMailUtil.sendMailByType(emailTo, Email.MAIL_TRAFFIC_STOP, data);
            } else if (incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_1 ||
                incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_4 ||
                incidentType == ParamConstant.VehicleDiagramItemStatus.INCIDENT_TYPE_5
            ) {
                // send mail incident stop
                data.add(lateTime);
                data.add(arrivalTime);
                nextMailUtil.sendMailByType(emailTo, Email.MAIL_INCIDENT_STOP, data);
            } else {
                // send mail change time
                nextMailUtil.sendMailByType(emailTo, Email.MAIL_TIME_CHANGE, data);
            }
        } catch (Exception e) {
            logger.error("SendMail ByIncidentType Exception: {}", e.getMessage());
        }
    }

    /**
     * 車両ダイアグラム明細のモビリティハブを更新する
     *
     * @param vehicleDiagramItem 車両ダイアグラム明細
     * @param dto                車両ダイアグラム明細のDTO
     */
    @Override
    public void callMobilityHub(VehicleDiagramItem vehicleDiagramItem, VehicleDiagramItemByAbilityDetailsDTO dto) {
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHub = getVehicleDiagramMobilityHub(vehicleDiagramItem);

        List<String> mobilityHubIds = new ArrayList<>();
        List<String> freightIds = new ArrayList<>();
        List<String> truckIds = new ArrayList<>();
        List<String> sizeClassIds = new ArrayList<>();
        List<LocalDateTime> validFroms = new ArrayList<>();
        List<LocalDateTime> validUntil = new ArrayList<>();

        if (vehicleDiagramMobilityHub != null && !vehicleDiagramMobilityHub.isEmpty()) {
            vehicleDiagramMobilityHub.forEach(vehicleDiagramMobility -> {
                processMobilityHub(vehicleDiagramMobility, dto, validFroms, validUntil);
                mobilityHubIds.add(vehicleDiagramMobility.getMobilityHubId());
                freightIds.add(vehicleDiagramMobility.getFreightId());
                truckIds.add(vehicleDiagramMobility.getTruckId());
                sizeClassIds.add(vehicleDiagramMobility.getSizeClass());
            });

            updateMobilityHub(mobilityHubIds, freightIds, truckIds, sizeClassIds, validFroms, validUntil,
                vehicleDiagramMobilityHub);
        } else {
            logger.info("Not Update MOBILITY-HUB: vehicleDiagramItem {}", vehicleDiagramItem);
        }
    }

    /**
     * 車両ダイアグラム明細のステータスを更新する
     *
     * @param request 更新リクエスト
     */
    @Override
    public void updateDiagramItemStatus(DiagramItemUpdateStatusRequest request) {
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(
            request.getId());
        if (vehicleDiagramItem == null) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, System.NOT_FOUND, "Vehicle diagram item");
        }
        vehicleDiagramItem.setStatus(request.getStatus());
        vehicleDiagramItemRepository.save(vehicleDiagramItem);
    }

    /**
     * 車両ダイアグラム明細のモビリティハブを取得する
     *
     * @param vehicleDiagramItem 車両ダイアグラム明細
     * @return 車両ダイアグラム明細のモビリティハブのリスト
     */
    private List<VehicleDiagramMobilityHub> getVehicleDiagramMobilityHub(VehicleDiagramItem vehicleDiagramItem) {
        if (VEHICLE_DIAGRAM_ITEM_STATUS.DIAGRAM_ITEM_COMPLETED.equals(vehicleDiagramItem.getStatus()) ||
            VEHICLE_DIAGRAM_ITEM_STATUS.DIAGRAM_ITEM_CANCEL.equals(vehicleDiagramItem.getStatus())) {
            return Collections.emptyList();
        }
        if (VEHICLE_DIAGRAM_ITEM_STATUS.DIAGRAM_ITEM_RUNNING.equals(vehicleDiagramItem.getStatus())) {
            return vehicleDiagramMobilityHubRepository.findAllByVehicleDiagramItemIdAndType(vehicleDiagramItem.getId(),
                ParamConstant.MobilityHub.TYPE_1);
        }
        return vehicleDiagramMobilityHubRepository.findAllByVehicleDiagramItemId(vehicleDiagramItem.getId());
    }

    /**
     * モビリティハブを処理する
     *
     * @param vehicleDiagramMobility 車両ダイアグラムモビリティハブ
     * @param dto                    車両ダイアグラム明細のDTO
     * @param validFroms             有効開始日時のリスト
     * @param validUntil             有効終了日時のリスト
     */
    private void processMobilityHub(VehicleDiagramMobilityHub vehicleDiagramMobility,
        VehicleDiagramItemByAbilityDetailsDTO dto, List<LocalDateTime> validFroms, List<LocalDateTime> validUntil) {
        mobilityHubUtil.deleteMobilityHub(vehicleDiagramMobility.getMhReservationId());

        LocalDateTime currentValidFrom = vehicleDiagramMobility.getValidFrom();
        LocalDateTime currentValidUntil = vehicleDiagramMobility.getValidUntil();

        if (vehicleDiagramMobility.getType() == ParamConstant.MobilityHub.TYPE_0) {
            Long cutOffTime = getCutOffTime(vehicleDiagramMobility);
            LocalDateTime newValidFrom = LocalDateTime.of(currentValidFrom.toLocalDate(),
                stringToLocalTime(dto.getDepartureTime()));
            validFroms.add(newValidFrom.minusHours(cutOffTime));
            validUntil.add(newValidFrom.plusMinutes(15));
            vehicleDiagramMobility.setValidFrom(newValidFrom.minusHours(cutOffTime));
            vehicleDiagramMobility.setValidUntil(newValidFrom.plusMinutes(15));
        } else {
            LocalDateTime newValidUntil = LocalDateTime.of(currentValidUntil.toLocalDate(),
                stringToLocalTime(dto.getArrivalTime()));
            validFroms.add(newValidUntil.minusMinutes(15));
            validUntil.add(newValidUntil.plusHours(3));
            vehicleDiagramMobility.setValidFrom(newValidUntil.minusMinutes(15));
            vehicleDiagramMobility.setValidUntil(newValidUntil.plusHours(3));
        }
    }

    /**
     * 切り捨て時間を取得する
     *
     * @param vehicleDiagramMobility 車両ダイアグラムモビリティハブ
     * @return 切り捨て時間
     */
    private Long getCutOffTime(VehicleDiagramMobilityHub vehicleDiagramMobility) {
        Long cutOffTime = 0L;
        try {
            VehicleDiagramItemTrailer vehicleDiagramItemTrailer =
                vehicleDiagramMobility.getVehicleDiagramItemTrailerId() != null
                    ? vehicleDiagramItemTrailerRepository.findById(
                    vehicleDiagramMobility.getVehicleDiagramItemTrailerId()).orElse(null)
                    : vehicleDiagramItemTrailerRepository.findFirstByVehicleDiagramItemId(
                        vehicleDiagramMobility.getVehicleDiagramItemId());

            Map<String, BigDecimal> cutOffInfo = vehicleDiagramItemTrailer.getCutOffPrice();
            List<CutOffInfoDTO> cutOffInfoDTOs = cutOffInfoMapper.toCutOffInfo(cutOffInfo);
            cutOffTime = Long.valueOf(cutOffInfoDTOs.stream()
                .map(CutOffInfoDTO::getCutOffTime)
                .max(Comparator.comparing(Long::parseLong))
                .orElse("0"));
        } catch (Exception e) {
            logger.error("Error getting cut off info: {}", e.getMessage());
        }
        return cutOffTime;
    }

    /**
     * モビリティハブを更新する
     *
     * @param mobilityHubIds                 モビリティハブIDのリスト
     * @param freightIds                     貨物IDのリスト
     * @param truckIds                       トラックIDのリスト
     * @param sizeClassIds                   サイズクラスIDのリスト
     * @param validFroms                     有効開始日時のリスト
     * @param validUntil                     有効終了日時のリスト
     * @param finalVehicleDiagramMobilityHub 最終的な車両ダイアグラムモビリティハブのリスト
     */
    private void updateMobilityHub(List<String> mobilityHubIds, List<String> freightIds, List<String> truckIds,
        List<String> sizeClassIds, List<LocalDateTime> validFroms, List<LocalDateTime> validUntil,
        List<VehicleDiagramMobilityHub> finalVehicleDiagramMobilityHub) {
        ReservationResponseDTO responseDTO = mobilityHubUtil.updateMobilityHub(mobilityHubIds, freightIds, truckIds,
            sizeClassIds, validFroms, validUntil);
        if (responseDTO != null) {
            updateMobilityHubKeys(responseDTO, finalVehicleDiagramMobilityHub);
        } else {
            finalVehicleDiagramMobilityHub.forEach(vehicleDiagramMobility -> vehicleDiagramMobility.setStatus(
                ParamConstant.MobilityHub.RESERVATION_STATUS_1));
        }
        vehicleDiagramMobilityHubRepository.saveAll(finalVehicleDiagramMobilityHub);
    }

    /**
     * モビリティハブのキーを更新する
     *
     * @param responseDTO                    モビリティハブのレスポンスDTO
     * @param finalVehicleDiagramMobilityHub 最終的な車両ダイアグラムモビリティハブのリスト
     */
    private void updateMobilityHubKeys(ReservationResponseDTO responseDTO,
        List<VehicleDiagramMobilityHub> finalVehicleDiagramMobilityHub) {
        String key;
        if (responseDTO.getAttribute().getStatuses().size() == 1) {
            key = responseDTO.getAttribute().getStatuses().get(0).getKey();
            if (key.length() > 20) {
                key = key.substring(0, 20);
            }
            String slotIdTractor = key.split(ParamConstant.MobilityHub.SLOT_ID_PREFIX)[1];
            String finalKey = key;
            finalVehicleDiagramMobilityHub.forEach(vehicleDiagramMobilityHub1 -> {
                vehicleDiagramMobilityHub1.setMhReservationId(finalKey);
                vehicleDiagramMobilityHub1.setSlotId(slotIdTractor);
            });
        } else {
            for (int i = 0; i < responseDTO.getAttribute().getStatuses().size(); i++) {
                key = responseDTO.getAttribute().getStatuses().get(i).getKey();
                if (key.length() > 20) {
                    key = key.substring(0, 20);
                }
                String slotIdTractor = key.split(ParamConstant.MobilityHub.SLOT_ID_PREFIX)[1];
                finalVehicleDiagramMobilityHub.get(i).setMhReservationId(key);
                finalVehicleDiagramMobilityHub.get(i).setSlotId(slotIdTractor);
            }
        }
    }

    public void updateTime(Long diagramItemId, String departureTime, String arrivalTime) {
        OrderTimeUpdateRequest orderTimeUpdateRequest = new OrderTimeUpdateRequest();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM);
        try {
            LocalTime parsedDepartureTime = LocalTime.parse(departureTime, formatter);
            LocalTime parsedArrivalTime = LocalTime.parse(arrivalTime, formatter);
            orderTimeUpdateRequest.setDepartureTime(parsedDepartureTime);
            orderTimeUpdateRequest.setArrivalTime(parsedArrivalTime);
        } catch (DateTimeParseException e) {
            log.error("Invalid time format: {}", e.getMessage());
        }
        if (orderTimeUpdateRequest.getDepartureTime() != null && orderTimeUpdateRequest.getArrivalTime() != null) {
            String managementNumber = ZLWeb.MANAGEMENT_NUMBER_PREFIX.concat(diagramItemId.toString());
            orderTimeUpdateRequest.setManagementNumber(managementNumber);
            HttpHeaders headers = new HttpHeaders();
            headers.set(API_KEY, authProperties.getApiKey());
            headers.setContentType(MediaType.APPLICATION_JSON);
            String urlPropertiesTtmi = urlProperties.getTtmi().concat(ZLWeb.UPDATE_TIME);
            HttpEntity<OrderTimeUpdateRequest> requestEntity =
                new HttpEntity<>(orderTimeUpdateRequest, headers);
            try {
                ResponseEntity<String> response = restTemplate.exchange(
                    urlPropertiesTtmi,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
                );
                log.info("SUCCESS call Gateway-TTMI : " + response);
            } catch (Exception e) {
                log.error("ERROR call Gateway-TTMI : {}", e.getMessage());
            }
        }
    }
}

