package nlj.business.carrier.service.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.data.dto.response.ReservationResponseDTO;
import com.next.logistic.data.util.MobilityHubUtil;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.MessageConstant;
import nlj.business.carrier.constant.MessageConstant.VehicleDiagramMessage;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramAllocation;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.domain.VehicleInfo;
import nlj.business.carrier.dto.CutOffInfoDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.DayDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.request.VehicleDiagramItemTrailerRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramPrice.VehicleDiagramItemPriceDTO;
import nlj.business.carrier.dto.vehicleDiagramPrice.VehicleDiagramPriceDTO;
import nlj.business.carrier.dto.vehicleDiagramPrice.request.VehicleDiagramPriceRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramPrice.response.VehicleDiagramPriceResponseDTO;
import nlj.business.carrier.mapper.CutOffInfoMapper;
import nlj.business.carrier.mapper.VehicleDiagramPriceMapper;
import nlj.business.carrier.repository.VehicleDiagramAllocationRepository;
import nlj.business.carrier.repository.VehicleDiagramItemRepository;
import nlj.business.carrier.repository.VehicleDiagramItemTrailerRepository;
import nlj.business.carrier.repository.VehicleDiagramMobilityHubCustomRepository;
import nlj.business.carrier.repository.VehicleDiagramMobilityHubRepository;
import nlj.business.carrier.repository.VehicleDiagramRepository;
import nlj.business.carrier.repository.VehicleInfoRepository;
import nlj.business.carrier.service.DataTransferYamatoService;
import nlj.business.carrier.service.VehicleDiagramMobilityHubService;
import nlj.business.carrier.service.VehicleDiagramPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 車両図面価格サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleDiagramPriceServiceImpl implements VehicleDiagramPriceService {

    private final Logger logger = LoggerFactory.getLogger(VehicleDiagramPriceServiceImpl.class);

    private final VehicleDiagramPriceMapper vehicleDiagramPriceMapper;
    private final VehicleDiagramItemRepository vehicleDiagramItemRepository;
    private final VehicleDiagramRepository vehicleDiagramRepository;
    private final VehicleDiagramAllocationRepository vehicleDiagramAllocationRepository;
    private final VehicleDiagramItemTrailerRepository vehicleDiagramItemTrailerRepository;
    private final VehicleDiagramMobilityHubRepository vehicleDiagramMobilityHubRepository;
    private final VehicleInfoRepository vehicleInfoRepository;
    private final VehicleDiagramMobilityHubService vehicleDiagramMobilityHubService;
    private final VehicleDiagramMobilityHubCustomRepository vehicleDiagramMobilityHubCustomRepository;
    private final DataTransferYamatoService dataTransferYamatoService;
    private final MobilityHubUtil mobilityHubUtil;
    private final EntityManager entityManager;
    private final CutOffInfoMapper cutOffInfoMapper;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 車両図面の価格を取得する。
     *
     * @param headId 車両図面ヘッダーID
     * @return 車両図面価格レスポンスDTO
     */
    @Override
    public VehicleDiagramPriceResponseDTO getVehicleDiagramPrices(Long headId) {
        List<VehicleDiagram> vehicleDiagramList = vehicleDiagramRepository.findVehicleDiagramByVehicleDiagramHeadId(
            headId);
        VehicleDiagramPriceResponseDTO responseDTO = new VehicleDiagramPriceResponseDTO();
        List<VehicleDiagramPriceDTO> vehicleDiagramPriceDTOS = new ArrayList<>();
        for (VehicleDiagram diagram : vehicleDiagramList) {
            VehicleDiagramPriceDTO vehicleDiagramPriceDTO = vehicleDiagramPriceMapper.toVehicleDiagramPriceDTO(diagram);
            List<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemRepository.findAllByVehicleDiagram(
                diagram);
            List<VehicleDiagramItemPriceDTO> vehicleDiagramItemDTOS = vehicleDiagramItems.stream()
                .map(vehicleDiagramPriceMapper::toVehicleDiagramItemPriceDTO).toList();
            vehicleDiagramPriceDTO.setItemPrice(vehicleDiagramItemDTOS);
            vehicleDiagramPriceDTOS.add(vehicleDiagramPriceDTO);
        }
        responseDTO.setVehicleDiagramPrices(vehicleDiagramPriceDTOS);

        return responseDTO;
    }

    /**
     * 車両図面の価格を更新する。
     *
     * @param headId                        車両図面ヘッダーID
     * @param vehicleDiagramPriceRequestDTO 車両図面価格リクエストDTO
     */
    @Transactional
    @Override
    public void updateVehicleDiagramPrice(Long headId, VehicleDiagramPriceRequestDTO vehicleDiagramPriceRequestDTO) {
        List<VehicleDiagram> vehicleDiagrams = vehicleDiagramRepository.findVehicleDiagramByVehicleDiagramHeadId(
            headId);
        if (vehicleDiagrams.isEmpty()) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                MessageConstant.VehicleDiagramMessage.VEHICLE_DIAGRAM_HEAD_NOT_FOUND);
        }
        for (VehicleDiagram diagram : vehicleDiagrams) {
            VehicleDiagramPriceDTO vehicleDiagramPriceDTO = vehicleDiagramPriceRequestDTO.getVehicleDiagramPrices()
                .stream().filter(diagramPrice -> diagram.getRoundTripType().equals(diagramPrice.getRoundTripType()))
                .findAny().orElseThrow(() -> new IllegalStateException(
                    "No matching vehicle diagram price found for round trip type: " + diagram.getRoundTripType()));

            updateDiagramPrices(diagram, vehicleDiagramPriceDTO);

            List<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemRepository.findAllByVehicleDiagram(
                diagram);
            for (VehicleDiagramItem item : vehicleDiagramItems) {
                VehicleDiagramItemPriceDTO vehicleDiagramItemPriceDTO = vehicleDiagramPriceDTO.getItemPrice().stream()
                    .filter(itemPrice -> item.getTripName().equalsIgnoreCase(itemPrice.getTripName())).findAny()
                    .orElseThrow(() -> new IllegalStateException(
                        "No matching vehicle diagram price found for round trip name: " + item.getTripName()));

                vehicleDiagramItemRepository.save(item);
            }
            vehicleDiagramRepository.save(diagram);
            dataTransferYamatoService.transferDataToYamato(diagram.getId());
        }
    }

    /**
     * 車両図面アイテムトレーラーの日付に基づいて価格を追加する。
     *
     * @param vehicleDiagramId                    車両図面ID
     * @param vehicleDiagramItemTrailerRequestDTO 車両図面アイテムトレーラーリクエストDTO
     */
    @Transactional
    @Override
    public void addPriceForVehicleDiagramItemTrailerByDate(Long vehicleDiagramId,
        VehicleDiagramItemTrailerRequestDTO vehicleDiagramItemTrailerRequestDTO) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.SYSTEM_ERR_001);
        }
        VehicleDiagram vehicleDiagram = vehicleDiagramRepository.findVehicleDiagramById(vehicleDiagramId);
        if (vehicleDiagram == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, VehicleDiagramMessage.VEHICLE_DIAGRAM_NOT_FOUND);
        }
        List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailersExist = vehicleDiagramItemTrailerRepository.findVehicleDiagramItemTrailerByVehicleDiagram(
            vehicleDiagram);
        List<Long> vehicleDiagramItemTrailerIds = vehicleDiagramItemTrailersExist.stream()
            .map(VehicleDiagramItemTrailer::getId)
            .toList();
        checkVehicleDiagramItemTrailerInTransOrder(vehicleDiagramItemTrailerIds);
        int reservationStatus = ParamConstant.MobilityHub.RESERVATION_STATUS_0;
        if (!vehicleDiagramItemTrailersExist.isEmpty()) {
//            vehicleDiagramItemTrailerRepository.deleteByVehicleDiagramId(vehicleDiagramId);
            vehicleDiagramItemTrailersExist.forEach(item -> {
                // Delete VehicleAvbResourcesByTrailerId
                deleteVehicleAvbResourceItemByTrailer(item.getId());

                // Delete TransMatchingByTrailerId
                deleteTransMatchingByTrailer(item.getId());

                List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubs = vehicleDiagramMobilityHubCustomRepository.findByVehicleDiagramItemId(
                    item.getVehicleDiagramItem().getId());
                if (!vehicleDiagramMobilityHubs.isEmpty()) {
                    vehicleDiagramMobilityHubs.forEach(mobilityHub -> {
                        mobilityHubUtil.deleteMobilityHub(mobilityHub.getMhReservationId());
                    });
                    vehicleDiagramMobilityHubRepository.deleteAll(vehicleDiagramMobilityHubs);
                }
            });
            reservationStatus = ParamConstant.MobilityHub.RESERVATION_STATUS_2;
        }
        vehicleDiagram.setCutOffPrice(vehicleDiagramItemTrailerRequestDTO.getCutOffPrice());
        vehicleDiagram.setCommonPrice(vehicleDiagramItemTrailerRequestDTO.getCommonPrice());
        vehicleDiagramRepository.save(vehicleDiagram);
        List<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemRepository.findVehicleDiagramItemsByVehicleDiagramId(
            vehicleDiagramId);
        List<String> vehicleDiagramItemDate = vehicleDiagramItems.stream()
            .map(item -> item.getDay().format(DateTimeFormatter.ofPattern("yyyyMMdd"))).toList();

        if (!vehicleDiagramItemTrailerRequestDTO.getTrailers().isEmpty()) {
            vehicleDiagramItemTrailerRequestDTO.getTrailers().forEach(item -> {
                VehicleDiagramAllocation vehicleDiagramAllocation = vehicleDiagramAllocationRepository.findVehicleDiagramAllocationById(
                    item.getVehicleDiagramAllocationId());
                if (vehicleDiagramAllocation == null) {
                    NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                        VehicleDiagramMessage.VEHICLE_DIAGRAM_ALLOCATION_NOT_FOUND);
                }
                Integer freightRateType = item.getFreightRateType();
                if (!item.getDays().isEmpty()) {
                    item.getDays().forEach(day -> {
                        if (!vehicleDiagramItemDate.contains(day.getDay())) {
                            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                                VehicleDiagramMessage.VEHICLE_DIAGRAM_ALLOCATION_NOT_FOUND);
                        }
                        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(
                            day.getVehicleDiagramItemId());
                        if (vehicleDiagramItem == null) {
                            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                                VehicleDiagramMessage.VEHICLE_DIAGRAM_ITEM_NOT_FOUND);
                        }
                        VehicleDiagramItemTrailer vehicleDiagramItemTrailer = new VehicleDiagramItemTrailer();
                        if (vehicleDiagramItemTrailerIds.contains(day.getVehicleDiagramItemTrailerId())) {
                            vehicleDiagramItemTrailer.setId(day.getVehicleDiagramItemTrailerId());
                        }
                        vehicleDiagramItemTrailer = saveVehicleDiagramItemTrailer(vehicleDiagramItemTrailer, user,
                            vehicleDiagram,
                            vehicleDiagramItem, vehicleDiagramAllocation, vehicleDiagramItemTrailerRequestDTO, day,
                            freightRateType);
                        vehicleDiagramItemTrailerRepository.save(vehicleDiagramItemTrailer);
                    });
                }
            });
            List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailerNews = vehicleDiagramItemTrailerRepository.findVehicleDiagramItemTrailerByVehicleDiagram(
                vehicleDiagram);

            List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubTractors = new ArrayList<>();
            for (VehicleDiagramItemTrailer vehicleDiagramItemTrailer : vehicleDiagramItemTrailerNews) {
                VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItems.stream()
                    .filter(item -> item.getId().equals(vehicleDiagramItemTrailer.getVehicleDiagramItem().getId()))
                    .findFirst().orElse(null);
                if (vehicleDiagramItem == null) {
                    logger.error("Vehicle Diagram Item not found !!!");
                    continue;
                }
                // departure_from
                updateMobilityHub(vehicleDiagramItemTrailer, vehicleDiagramItem.getDepartureFrom(),
                    vehicleDiagramItem.getArrivalTo(), vehicleDiagramId, ParamConstant.MobilityHub.TYPE_0,
                    vehicleDiagramMobilityHubTractors, vehicleDiagramItem.getId());
                // arrival_to
                updateMobilityHub(vehicleDiagramItemTrailer, vehicleDiagramItem.getDepartureFrom(),
                    vehicleDiagramItem.getArrivalTo(), vehicleDiagramId, ParamConstant.MobilityHub.TYPE_1,
                    vehicleDiagramMobilityHubTractors, vehicleDiagramItem.getId());
            }
            callMobilityHub(vehicleDiagramItemTrailerNews, reservationStatus);
        }
        dataTransferYamatoService.transferDataToYamato(vehicleDiagramId);
    }

    /**
     * 車両図面アイテムトレーラーを保存する。
     *
     * @param vehicleDiagramItemTrailer           車両図面アイテムトレーラー
     * @param user                                ユーザー情報
     * @param vehicleDiagram                      車両図面
     * @param vehicleDiagramItem                  車両図面アイテム
     * @param vehicleDiagramAllocation            車両図面割り当て
     * @param vehicleDiagramItemTrailerRequestDTO 車両図面アイテムトレーラーリクエストDTO
     * @param day                                 日付情報
     * @param freightRateType                     運賃タイプ
     * @return 保存された車両図面アイテムトレーラー
     */
    private VehicleDiagramItemTrailer saveVehicleDiagramItemTrailer(VehicleDiagramItemTrailer vehicleDiagramItemTrailer,
        User user,
        VehicleDiagram vehicleDiagram, VehicleDiagramItem vehicleDiagramItem, VehicleDiagramAllocation
        vehicleDiagramAllocation, VehicleDiagramItemTrailerRequestDTO vehicleDiagramItemTrailerRequestDTO,
        DayDTO day, Integer freightRateType) {

        vehicleDiagramItemTrailer.setOperatorId(user.getCompanyId());
        vehicleDiagramItemTrailer.setVehicleDiagram(vehicleDiagram);
        vehicleDiagramItemTrailer.setVehicleDiagramItem(vehicleDiagramItem);
        vehicleDiagramItemTrailer.setVehicleDiagramAllocation(vehicleDiagramAllocation);
        if (vehicleDiagramItemTrailerRequestDTO.getDepartureTime() != null) {
            try {
                vehicleDiagramItemTrailer.setDepartureTime(
                    LocalTime.parse(vehicleDiagramItemTrailerRequestDTO.getDepartureTime(),
                        DateTimeFormatter.ofPattern("HHmm")));
            } catch (DateTimeParseException e) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                    "Invalid departure time format. Expected format: HHMM");
            }
        }
        if (vehicleDiagramItemTrailerRequestDTO.getArrivalTime() != null) {
            try {
                vehicleDiagramItemTrailer.setArrivalTime(
                    LocalTime.parse(vehicleDiagramItemTrailerRequestDTO.getArrivalTime(),
                        DateTimeFormatter.ofPattern("HHmm")));
            } catch (DateTimeParseException e) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                    "Invalid arrival time format. Expected format: HHMM");
            }
        }
        vehicleDiagramItemTrailer.setTripName(vehicleDiagramItemTrailerRequestDTO.getTripName());
        vehicleDiagramItemTrailer.setFreightRateType(freightRateType);
        vehicleDiagramItemTrailer.setCutOffPrice(vehicleDiagram.getCutOffPrice());
        if (vehicleDiagramItemTrailerRequestDTO.getCutOffPrice() != null) {
            vehicleDiagramItemTrailer.setCutOffPrice(
                vehicleDiagramItemTrailerRequestDTO.getCutOffPrice());
        } else {
            vehicleDiagramItemTrailer.setCutOffPrice(vehicleDiagram.getCutOffPrice());
        }
        if (day.getDay() != null) {
            LocalDate localDate = LocalDate.parse(day.getDay(),
                DateTimeFormatter.ofPattern("yyyyMMdd"));
            vehicleDiagramItemTrailer.setDay(localDate);
        }
        vehicleDiagramItemTrailer.setPrice(day.getPrice());
        vehicleDiagramItemTrailer.setStatus(day.getStatus());
        return vehicleDiagramItemTrailer;
    }

    /**
     * トレーラーIDに基づいて車両可用リソースアイテムを削除する。
     *
     * @param trailerId トレーラーID
     */
    private void deleteVehicleAvbResourceItemByTrailer(Long trailerId) {
        try {
            String sql = """
                    DELETE FROM vehicle_avb_resource_item 
                    WHERE vehicle_diagram_item_trailer_id = :trailerId
                """;

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("trailerId", trailerId);
            query.executeUpdate();
        } catch (Exception e) {
            logger.error("Error deleting vehicle_avb_resource_item for trailer_id {}: {}", trailerId, e.getMessage());
        }
    }

    /**
     * トレーラーIDに基づいてトランスマッチングを削除する。
     *
     * @param trailerId トレーラーID
     */
    private void deleteTransMatchingByTrailer(Long trailerId) {
        try {
            String sql = """
                    DELETE FROM t_trans_matching 
                    WHERE vehicle_diagram_item_trailer_id = :trailerId
                """;

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("trailerId", trailerId);
            query.executeUpdate();
        } catch (Exception e) {
            logger.error("Error deleting t_trans_matching for trailer_id {}: {}", trailerId, e.getMessage());
        }
    }

    /**
     * 車両図面の価格を更新する。
     *
     * @param diagram                車両図面
     * @param vehicleDiagramPriceDTO 車両図面価格DTO
     */
    private void updateDiagramPrices(VehicleDiagram diagram, VehicleDiagramPriceDTO vehicleDiagramPriceDTO) {
        diagram.setAdjustmentPrice(vehicleDiagramPriceDTO.getAdjustmentPrice());
        diagram.setCommonPrice(vehicleDiagramPriceDTO.getCommonPrice());
        diagram.setCutOffPrice(vehicleDiagramPriceDTO.getCutOffPrice());
    }

    /**
     * 車両図面アイテムトレーラーのモビリティハブを更新する。
     *
     * @param vehicleDiagramItemTrailer         車両図面アイテムトレーラー
     * @param departureFrom                     出発地点
     * @param arrivalTo                         到着地点
     * @param vehicleDiagramId                  車両図面ID
     * @param type                              モビリティハブのタイプ
     * @param vehicleDiagramMobilityHubTractors 車両図面モビリティハブトラクターリスト
     * @param vehicleDiagramItemId              車両図面アイテムID
     */
    private void updateMobilityHub(VehicleDiagramItemTrailer vehicleDiagramItemTrailer,
        Long departureFrom, Long arrivalTo, Long vehicleDiagramId, int type,
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubTractors, Long vehicleDiagramItemId) {

        VehicleDiagramAllocation vehicleDiagramAllocation = vehicleDiagramAllocationRepository.findVehicleDiagramAllocationById(
            vehicleDiagramItemTrailer.getVehicleDiagramAllocation().getId());
        List<VehicleDiagramAllocation> vehicleDiagramAllocations = vehicleDiagramAllocationRepository.findAllByVehicleDiagramIdAndVehicleType(
            vehicleDiagramItemTrailer.getVehicleDiagram().getId(), ParamConstant.MobilityHub.VEHICLE_TYPE_TRACTOR);
        VehicleDiagramAllocation vehicleDiagramAllocationTractor =
            vehicleDiagramAllocations.isEmpty() ? null : vehicleDiagramAllocations.get(0);
        if (vehicleDiagramAllocation == null || vehicleDiagramAllocationTractor == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                VehicleDiagramMessage.VEHICLE_DIAGRAM_ALLOCATION_NOT_FOUND);
        }
        String truckId = vehicleInfoRepository.getTruckId(vehicleDiagramAllocation.getVehicleInfoId());
        String truckIdTractor = vehicleInfoRepository.getTruckId(vehicleDiagramAllocationTractor.getVehicleInfoId());
        if (BaseUtil.isNull(truckId) || BaseUtil.isNull(truckIdTractor)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                VehicleDiagramMessage.VEHICLE_DIAGRAM_VEHICLE_INFO_NOT_FOUND);
        }
        VehicleInfo vehicleInfo = vehicleInfoRepository.findVehicleInfoById(
            vehicleDiagramAllocation.getVehicleInfoId());
        VehicleInfo vehicleInfoTractor = vehicleInfoRepository.findVehicleInfoById(
            vehicleDiagramAllocationTractor.getVehicleInfoId());
        if (vehicleInfo == null || vehicleInfoTractor == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                VehicleDiagramMessage.VEHICLE_DIAGRAM_VEHICLE_INFO_NOT_FOUND);
        }

        String mobilityHubId = null;
        if (type == ParamConstant.MobilityHub.TYPE_0) {
            mobilityHubId = BaseUtil.getGlnFrom(String.valueOf(departureFrom));
        } else {
            mobilityHubId = BaseUtil.getGlnTo(String.valueOf(arrivalTo));
        }

        VehicleDiagramMobilityHub vehicleDiagramMobilityHub;
        VehicleDiagramMobilityHub vehicleDiagramMobilityHubTractor = null;
        boolean isExist = false;
        if (!vehicleDiagramMobilityHubTractors.isEmpty()) {
            final String finalMobilityHubId = mobilityHubId;
            VehicleDiagramMobilityHub vehicleDiagramMobilityHubTractorExist = vehicleDiagramMobilityHubTractors.stream()
                .filter(item -> item.getMobilityHubId().equals(finalMobilityHubId) && item.getTruckId()
                    .equals(truckIdTractor) && item.getVehicleDiagramItemId().equals(vehicleDiagramItemId)
                    && item.getType().equals(type))
                .findFirst().orElse(null);
            if (vehicleDiagramMobilityHubTractorExist != null) {
                isExist = true;
            }
        }
        if (type == ParamConstant.MobilityHub.TYPE_0) {
            // get cut off info
            Long cutOffTime = 0L;
            try {
                Map<String, BigDecimal> cutOffInfo = vehicleDiagramItemTrailer.getCutOffPrice();
                List<CutOffInfoDTO> cutOffInfoDTOs = cutOffInfoMapper.toCutOffInfo(cutOffInfo);
                cutOffTime = Long.valueOf(cutOffInfoDTOs.stream()
                    .map(CutOffInfoDTO::getCutOffTime)
                    .max(Comparator.comparing(Long::parseLong))
                    .orElse("0"));
            } catch (Exception e) {
                logger.error("Error getting cut off info: {}", e.getMessage());
            }
            // insert or update tractor
            if (!isExist) {
                vehicleDiagramMobilityHubTractor = vehicleDiagramMobilityHubService.saveVehicleDiagramMobilityHub(
                    vehicleDiagramItemTrailer.getVehicleDiagramItem().getId(), null, mobilityHubId,
                    String.valueOf(vehicleInfoTractor.getVehicleSize()),
                    vehicleDiagramItemTrailer.getDay().atTime(vehicleDiagramItemTrailer.getDepartureTime())
                        .minusHours(cutOffTime),
                    vehicleDiagramItemTrailer.getDay().atTime(vehicleDiagramItemTrailer.getDepartureTime())
                        .plusMinutes(15), null,
                    String.valueOf(vehicleDiagramId),
                    truckIdTractor, type, ParamConstant.MobilityHub.VEHICLE_TYPE_TRACTOR,
                    vehicleDiagramAllocationTractor.getId());
                vehicleDiagramMobilityHubTractors.add(vehicleDiagramMobilityHubTractor);
            }
            // insert or update trailer
            vehicleDiagramMobilityHub = vehicleDiagramMobilityHubService.saveVehicleDiagramMobilityHub(
                vehicleDiagramItemTrailer.getVehicleDiagramItem().getId(), vehicleDiagramItemTrailer.getId(),
                mobilityHubId,
                String.valueOf(vehicleInfo.getVehicleSize()),
                vehicleDiagramItemTrailer.getDay().atTime(vehicleDiagramItemTrailer.getDepartureTime())
                    .minusHours(cutOffTime),
                vehicleDiagramItemTrailer.getDay().atTime(vehicleDiagramItemTrailer.getDepartureTime()).plusMinutes(15),
                null, String.valueOf(vehicleDiagramId),
                truckId, type, ParamConstant.MobilityHub.VEHICLE_TYPE_TRAILER, vehicleDiagramAllocation.getId());
        } else {
            // insert or update tractor
            if (!isExist) {
                vehicleDiagramMobilityHubTractor = vehicleDiagramMobilityHubService.saveVehicleDiagramMobilityHub(
                    vehicleDiagramItemTrailer.getVehicleDiagramItem().getId(), null, mobilityHubId,
                    String.valueOf(vehicleInfoTractor.getVehicleSize()),
                    vehicleDiagramItemTrailer.getDay().atTime(vehicleDiagramItemTrailer.getArrivalTime())
                        .minusMinutes(15),
                    vehicleDiagramItemTrailer.getDay().atTime(vehicleDiagramItemTrailer.getArrivalTime()).plusHours(3),
                    null,
                    String.valueOf(vehicleDiagramId),
                    truckIdTractor, type, ParamConstant.MobilityHub.VEHICLE_TYPE_TRACTOR,
                    vehicleDiagramAllocationTractor.getId());
                vehicleDiagramMobilityHubTractors.add(vehicleDiagramMobilityHubTractor);
            }
            // insert or update trailer
            vehicleDiagramMobilityHub = vehicleDiagramMobilityHubService.saveVehicleDiagramMobilityHub(
                vehicleDiagramItemTrailer.getVehicleDiagramItem().getId(), vehicleDiagramItemTrailer.getId(),
                mobilityHubId,
                String.valueOf(vehicleInfo.getVehicleSize()),
                vehicleDiagramItemTrailer.getDay().atTime(vehicleDiagramItemTrailer.getArrivalTime()).minusMinutes(15),
                vehicleDiagramItemTrailer.getDay().atTime(vehicleDiagramItemTrailer.getArrivalTime()).plusHours(3),
                null,
                String.valueOf(vehicleDiagramId), truckId, type, ParamConstant.MobilityHub.VEHICLE_TYPE_TRAILER,
                vehicleDiagramAllocation.getId());
        }
        if (vehicleDiagramMobilityHub == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                MessageConstant.VehicleDiagramMobilityHub.ERROR_INSERT_UPDATE_VEHICLE_DIAGRAM_MOBILITY_HUB);
        }
    }

    /**
     * モビリティハブを呼び出す。
     *
     * @param vehicleDiagramItemTrailers 車両図面アイテムトレーラーリスト
     * @param reservationStatus          予約ステータス
     */
    private void callMobilityHub(List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers, int reservationStatus) {
        List<String> mobilityHubIds = new ArrayList<>();
        List<String> freightIds = new ArrayList<>();
        List<String> truckIds = new ArrayList<>();
        List<String> sizeClassIds = new ArrayList<>();
        List<LocalDateTime> validFroms = new ArrayList<>();
        List<LocalDateTime> validUntil = new ArrayList<>();
        List<Long> vehicleDiagramItemIds = vehicleDiagramItemTrailers.stream()
            .map(VehicleDiagramItemTrailer::getVehicleDiagramItem)
            .map(VehicleDiagramItem::getId)
            .toList();
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubs = vehicleDiagramMobilityHubCustomRepository.findByVehicleDiagramItemIds(
            vehicleDiagramItemIds);
        vehicleDiagramMobilityHubs.forEach(vehicleDiagramMobilityHub -> {
            mobilityHubIds.add(vehicleDiagramMobilityHub.getMobilityHubId());
            freightIds.add(vehicleDiagramMobilityHub.getFreightId());
            truckIds.add(vehicleDiagramMobilityHub.getTruckId());
            sizeClassIds.add(vehicleDiagramMobilityHub.getSizeClass());
            validFroms.add(vehicleDiagramMobilityHub.getValidFrom());
            validUntil.add(vehicleDiagramMobilityHub.getValidUntil());
        });

        ReservationResponseDTO responseDTO = mobilityHubUtil.updateMobilityHub(mobilityHubIds, freightIds, truckIds,
            sizeClassIds, validFroms, validUntil);
        if (responseDTO != null) {
            updateMobilityHubKeys(responseDTO, vehicleDiagramMobilityHubs, reservationStatus);
        } else {
            vehicleDiagramMobilityHubs.forEach(vehicleDiagramMobility -> vehicleDiagramMobility.setReservationStatus(
                ParamConstant.MobilityHub.RESERVATION_STATUS_1));
        }
        vehicleDiagramMobilityHubRepository.saveAll(vehicleDiagramMobilityHubs);
    }

    /**
     * モビリティハブキーを更新する。
     *
     * @param responseDTO                    レスポンスDTO
     * @param finalVehicleDiagramMobilityHub 最終車両図面モビリティハブリスト
     * @param reservationStatus              予約ステータス
     */
    private void updateMobilityHubKeys(ReservationResponseDTO responseDTO,
        List<VehicleDiagramMobilityHub> finalVehicleDiagramMobilityHub, int reservationStatus) {
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
                vehicleDiagramMobilityHub1.setReservationStatus(reservationStatus);
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
                finalVehicleDiagramMobilityHub.get(i).setReservationStatus(reservationStatus);
            }
        }
    }

    /**
     * 車両図面アイテムトレーラーがトランスオーダーに存在するか確認する。
     *
     * @param vehicleDiagramItemTrailerIds 車両図面アイテムトレーラーIDリスト
     */
    private void checkVehicleDiagramItemTrailerInTransOrder(List<Long> vehicleDiagramItemTrailerIds) {
        if (vehicleDiagramItemTrailerIds.isEmpty()) {
            return;
        }
        String sqlTransOrder = "SELECT id FROM t_trans_order WHERE vehicle_diagram_item_trailer_id IN :trailerIds";
        List<Object[]> results = entityManager.createNativeQuery(sqlTransOrder)
            .setParameter("trailerIds", vehicleDiagramItemTrailerIds)
            .getResultList();
        if (!results.isEmpty()) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                MessageConstant.System.VEHICLE_DIAGRAM_ITEM_TRAILER_HAVE_IN_TRANS_ORDER);
        }
    }
}
