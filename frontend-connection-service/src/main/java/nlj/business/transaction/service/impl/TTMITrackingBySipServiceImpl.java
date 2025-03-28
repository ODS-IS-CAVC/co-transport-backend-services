package nlj.business.transaction.service.impl;

import static nlj.business.transaction.mapper.DateTimeMapper.dateToString;
import static nlj.business.transaction.mapper.DateTimeMapper.timeToStringHHMM;

import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.DateUtils;
import com.next.logistic.util.NextWebUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.domain.yamato.CarInfo;
import nlj.business.transaction.domain.yamato.VehicleAvailabilityResource;
import nlj.business.transaction.dto.TrackBySipResponse;
import nlj.business.transaction.dto.request.UpdateSipTrackIdRequestDTO;
import nlj.business.transaction.dto.trip.trackBySip.LogsSrvcPrv;
import nlj.business.transaction.dto.trip.trackBySip.ShipFromPrty;
import nlj.business.transaction.dto.trip.trackBySip.ShipToPrty;
import nlj.business.transaction.dto.trip.trackBySip.TrspIsr;
import nlj.business.transaction.dto.trip.trackBySip.TrspSrvc;
import nlj.business.transaction.dto.trip.trackBySip.request.TrackBySipRequestDTO;
import nlj.business.transaction.dto.trip.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerResponseDTO;
import nlj.business.transaction.repository.CarInfoRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.repository.VehicleAvbResourceRepository;
import nlj.business.transaction.repository.VehicleDiagramItemRepository;
import nlj.business.transaction.repository.VehicleDiagramItemTrailerRepository;
import nlj.business.transaction.service.TTMITrackingBySipService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 配送マッチングを実行するクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TTMITrackingBySipServiceImpl implements TTMITrackingBySipService {

    private final NljUrlProperties urlProperties;
    private final RestTemplate restTemplate;
    private final NljAuthProperties authProperties;
    private final TransOrderRepository transOrderRepository;
    private final VehicleAvbResourceRepository vehicleAvbResourceRepository;
    private final CarInfoRepository carInfoRepository;
    private final VehicleDiagramItemRepository vehicleDiagramItemRepository;
    private final VehicleDiagramItemTrailerRepository vehicleDiagramItemTrailerRepository;
    private final EntityManager entityManager;

    /**
     * TTMIトラッキングを登録する。
     *
     * @param transOrderId   トランスオーダーID
     * @param method         HTTPメソッド
     * @param servletRequest HTTPサーブレットリクエスト
     */
    @Override
    public void registerTTMITrackingBySip(Long transOrderId, HttpMethod method, HttpServletRequest servletRequest) {
        HttpHeaders headers = setHeader(servletRequest);
        TransOrder transOrder = transOrderRepository.findById(transOrderId).orElse(null);
        VehicleDiagramItemTrailerResponseDTO vehicleDiagramItemTrailerResponseDTO = new VehicleDiagramItemTrailerResponseDTO();
        if (transOrder == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.TRANS_ORDER_NOT_EXIST);
        }
        if (transOrder.getVehicleDiagramItemId() != null) {
            vehicleDiagramItemTrailerResponseDTO = getVehicleDiagramItemResponseDTO(
                transOrder.getVehicleDiagramItemId());
        }
        if (vehicleDiagramItemTrailerResponseDTO == null) {
            vehicleDiagramItemTrailerResponseDTO = getVehicleDiagramItemTrailerResponseDTO(
                transOrder.getVehicleDiagramItemTrailerId());
        }
        TrspSrvc trspSrvc = new TrspSrvc();
        TrackBySipRequestDTO trackBySipRequestDTO = new TrackBySipRequestDTO();
        //Make data for request
        TrspIsr trspIsr = new TrspIsr();

        VehicleAvailabilityResource vehicleAvailabilityResource = new VehicleAvailabilityResource();
        if (transOrder.getVehicleAvbResourceId() != null) {
            vehicleAvailabilityResource = vehicleAvbResourceRepository.findById(transOrder.getVehicleAvbResourceId())
                .orElse(null);
        }
        if (vehicleAvailabilityResource != null && vehicleAvailabilityResource.getCarInfo() != null) {
            CarInfo carInfo = vehicleAvailabilityResource.getCarInfo();
            if (carInfo.getCarLicensePltNumId() != null) {
                trspSrvc.setCarLicensePltNumId(carInfo.getCarLicensePltNumId());
            } else {
                trspSrvc.setCarLicensePltNumId(carInfo.getTrailerLicensePltNumId());
            }
        }

        LocalDateTime[] dateTimes = null;
        dateTimes = DateUtils.getStartAndEndDateTime(vehicleDiagramItemTrailerResponseDTO.getDay(),
            vehicleDiagramItemTrailerResponseDTO.getDepartureTime(),
            vehicleDiagramItemTrailerResponseDTO.getArrivalTime());
        trspIsr.setTrspInstructionId(String.valueOf(vehicleDiagramItemTrailerResponseDTO.getVehicleDiagramItemId()));

        trspSrvc.setAvbDateCllDate(dateToString(dateTimes[0].toLocalDate()));
        trspSrvc.setApedArrToDate(dateToString(dateTimes[1].toLocalDate()));
        trspSrvc.setAvbFromTimeOfCllTime(timeToStringHHMM(dateTimes[0].toLocalTime()));
        trspSrvc.setApedArrToTimePrfmDttm(timeToStringHHMM(dateTimes[1].toLocalTime()));

        LogsSrvcPrv logsSrvcPrv = new LogsSrvcPrv();
        logsSrvcPrv.setLogsSrvcPrvPrtyNameTxt(vehicleDiagramItemTrailerResponseDTO.getTripName());
        ShipFromPrty shipFromPrty = new ShipFromPrty();
        ShipToPrty shipToPrty = new ShipToPrty();
        shipFromPrty.setShipFromPrtyBrncOffId(String.valueOf(transOrder.getDepartureFrom()));
        shipToPrty.setShipToPrtyBrncOffId(String.valueOf(transOrder.getArrivalTo()));

        trackBySipRequestDTO.setTrspIsr(trspIsr);
        trackBySipRequestDTO.setTrspSrvc(trspSrvc);
        trackBySipRequestDTO.setLogsSrvcPrv(logsSrvcPrv);
        trackBySipRequestDTO.setShipFromPrty(shipFromPrty);
        trackBySipRequestDTO.setShipToPrty(shipToPrty);

        String domainGatewayTTMITrackBySip = urlProperties.getDomainGateway() + "/api/v1/track/sip";
        // Call gateWay service and update track sip id in carrier service
        callGateWayService(trackBySipRequestDTO, method, domainGatewayTTMITrackBySip, headers, transOrder);
    }

    /**
     * 車両ダイアグラムアイテムトレーラーのレスポンスDTOを取得する。
     *
     * @param vehicleDiagramItemTrailerId 車両ダイアグラムアイテムトレーラーID
     * @return 車両ダイアグラムアイテムトレーラーのレスポンスDTO
     */
    private VehicleDiagramItemTrailerResponseDTO getVehicleDiagramItemTrailerResponseDTO(
        Long vehicleDiagramItemTrailerId) {
        String vehicleDiagramItemTrailerSql = "SELECT " +
            "vehicle_diagram_item_id, "
            + "vehicle_diagram_id, "
            + "departure_time, "
            + "arrival_time, "
            + "day, "
            + "trip_name "
            + "FROM c_vehicle_diagram_item_trailer WHERE id = ?";

        List<Object[]> resultList = entityManager.createNativeQuery(vehicleDiagramItemTrailerSql)
            .setParameter(1, vehicleDiagramItemTrailerId)
            .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.stream()
            .map(VehicleDiagramItemTrailerResponseDTO::fromResult)
            .findFirst()
            .orElse(null);
    }

    /**
     * 車両ダイアグラムアイテムのレスポンスDTOを取得する。
     *
     * @param vehicleDiagramItemId 車両ダイアグラムアイテムID
     * @return 車両ダイアグラムアイテムのレスポンスDTO
     */
    private VehicleDiagramItemTrailerResponseDTO getVehicleDiagramItemResponseDTO(Long vehicleDiagramItemId) {
        String vehicleDiagramItemTrailerSql = "SELECT " +
            "id, "
            + "vehicle_diagram_id, "
            + "departure_time, "
            + "arrival_time, "
            + "day, "
            + "trip_name "
            + "FROM c_vehicle_diagram_item WHERE id = ?";

        List<Object[]> resultList = entityManager.createNativeQuery(vehicleDiagramItemTrailerSql)
            .setParameter(1, vehicleDiagramItemId)
            .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.stream()
            .map(VehicleDiagramItemTrailerResponseDTO::fromResult)
            .findFirst()
            .orElse(null);
    }

    /**
     * HTTPヘッダーを設定する。
     *
     * @param httpServletRequest HTTPサーブレットリクエスト
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader(HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * ゲートウェイサービスを呼び出す。
     *
     * @param trackBySipRequestDTO トラッキングリクエストDTO
     * @param method               HTTPメソッド
     * @param domainGateway        ドメインゲートウェイ
     * @param headers              HTTPヘッダー
     * @param transOrder           トランスオーダー
     */
    private void callGateWayService(TrackBySipRequestDTO trackBySipRequestDTO, HttpMethod method,
        String domainGateway, HttpHeaders headers, TransOrder transOrder) {
        HttpEntity<TrackBySipRequestDTO> requestEntity = new HttpEntity<>(trackBySipRequestDTO, headers);
        try {
            ResponseEntity<TrackBySipResponse> response = restTemplate.exchange(
                domainGateway,
                method,
                requestEntity,
                TrackBySipResponse.class
            );
            if (response.getBody() != null && response.getBody().getId() != null && HttpMethod.POST.equals(method)) {
                String domainCarrier = urlProperties.getDomainCarrier() + "/api/v1/item/sip-track";
                UpdateSipTrackIdRequestDTO request = new UpdateSipTrackIdRequestDTO();
                request.setSipTrackId(response.getBody().getId());
                request.setVehicleDiagramItemTrailerId(transOrder.getVehicleDiagramItemTrailerId());
                HttpEntity<UpdateSipTrackIdRequestDTO> requestUpdateCarrierEntity = new HttpEntity<>(request, headers);
                if (transOrder.getVehicleDiagramItemTrailerId() != null) {
                    ResponseEntity<String> responseEntity = restTemplate.exchange(
                        domainCarrier,
                        HttpMethod.PUT,
                        requestUpdateCarrierEntity,
                        String.class
                    );
                    if (responseEntity.getBody() == null || !responseEntity.getBody().contains("Success")) {
                        log.error("ERROR update sip track id : {}", responseEntity.getStatusCode());
                    }
                }
            }
            log.info("SUCCESS call Gateway-TTMI 052: " + response);
        } catch (Exception e) {
            log.error("ERROR call Gateway-TTMI : {}", e.getMessage());
        }
    }
}
