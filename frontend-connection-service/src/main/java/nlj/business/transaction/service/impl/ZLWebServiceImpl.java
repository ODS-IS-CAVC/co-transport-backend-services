package nlj.business.transaction.service.impl;

import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.NextWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.constant.ParamConstant.ZLWeb;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.dto.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.transaction.dto.zlWeb.ZLWebDTO;
import nlj.business.transaction.dto.zlWeb.item.ZLWebItemDTO;
import nlj.business.transaction.repository.LocationMasterCustomRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.repository.VehicleDiagramMobilityHubCustomRepository;
import nlj.business.transaction.service.ZLWebService;
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
 * ZLWebサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ZLWebServiceImpl implements ZLWebService {

    private final TransOrderRepository transOrderRepository;
    private final NljAuthProperties authProperties;
    private final RestTemplate restTemplate;
    private final NljUrlProperties urlProperties;
    private final LocationMasterCustomRepository locationMasterCustomRepository;
    private final VehicleDiagramMobilityHubCustomRepository vehicleDiagramMobilityHubCustomRepository;

    /**
     * ZLWebにデータを送信する。
     *
     * @param orderId                 注文ID
     * @param departureArrivalTimeDTO 出発到着時間のDTO
     * @param request                 HTTPリクエスト
     */
    @Transactional
    @Override
    public void sendToZLWeb(Long orderId, DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO,
        HttpServletRequest request) {
        TransOrder order = transOrderRepository.findById(orderId).orElse(null);
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(formatter);
        if (order == null) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, MessageConstant.System.NOT_FOUND, "trans order");
        }
        Long unit = transOrderRepository.countAllByTransTypeAndVehicleDiagramItemId(0, order.getVehicleDiagramItemId());
        List<TransOrder> transOrders = transOrderRepository.findAllByVehicleDiagramItemId(
            order.getVehicleDiagramItemId());
        String vehiclePlateNumber = vehicleDiagramMobilityHubCustomRepository.getVehiclePlateNumberByDiagramItemId(
            order.getVehicleDiagramItemId());

        ZLWebDTO zlWebDTO = new ZLWebDTO();
        List<ZLWebItemDTO> zlWebItemDTOList = new ArrayList<>();
        zlWebDTO.setManagementNumber(ZLWeb.MANAGEMENT_NUMBER_PREFIX + order.getVehicleDiagramItemId().toString());
        zlWebDTO.setPlUnit(unit);
        zlWebDTO.setShipperOperatorCode(order.getShipperOperatorCode());
        zlWebDTO.setShipperOperatorName(order.getShipperOperatorName());
        zlWebDTO.setShipperManagerName(order.getShipperOperatorName());
        zlWebDTO.setCarrierOperatorCode(order.getCarrierOperatorCode());
        zlWebDTO.setCarrierOperatorName(order.getCarrierOperatorName());
        zlWebDTO.setDepartureFromCode(order.getDepartureFrom());
        zlWebDTO.setDepartureFromName(
            locationMasterCustomRepository.getLocationNameByCode(order.getDepartureFrom().toString()));
        zlWebDTO.setArrivalToCode(order.getArrivalTo());
        zlWebDTO.setArrivalToName(
            locationMasterCustomRepository.getLocationNameByCode(order.getArrivalTo().toString()));
        zlWebDTO.setStartDate(order.getTransportDate());
        zlWebDTO.setEndDate(order.getTransportDate());
        zlWebDTO.setStartTime(departureArrivalTimeDTO.getDepartureTime());
        zlWebDTO.setEndTime(departureArrivalTimeDTO.getArrivalTime());
        zlWebDTO.setTrailerNumber(order.getTrailerNumber());
        zlWebDTO.setTripName(order.getServiceName());
        zlWebDTO.setVehicleType(order.getProposeSnapshot().getVehicleType());
        zlWebDTO.setDropOffDate(order.getTransportDate().minusDays(1));
        zlWebDTO.setDropOffTimeFrom(order.getRequestCollectionTimeFrom() == null ? formattedTime
            : order.getRequestCollectionTimeFrom().toString());
        zlWebDTO.setDropOffTimeTo(order.getRequestCollectionTimeTo() == null ? formattedTime
            : order.getRequestCollectionTimeTo().toString());
        zlWebDTO.setVehicleNumber(vehiclePlateNumber);
        transOrders.forEach(transOrder -> {
            if (!Objects.isNull(transOrder.getRequestSnapshot())) {
                ZLWebItemDTO zlWebItemDTO = new ZLWebItemDTO();
                zlWebItemDTO.setItemName(transOrder.getItemNameTxt());
                zlWebItemDTO.setItemCode(transOrder.getRequestSnapshot().getOuterPackageCode().toString());
                zlWebItemDTO.setLengthSize(transOrder.getRequestSnapshot().getTotalLength().intValue());
                zlWebItemDTO.setWidthSize(transOrder.getRequestSnapshot().getTotalWidth().intValue());
                zlWebItemDTO.setHeightSize(transOrder.getRequestSnapshot().getTotalHeight().intValue());
                zlWebItemDTO.setWeight(transOrder.getRequestSnapshot().getWeight());
                zlWebItemDTO.setUnit(BigDecimal.ONE);
                zlWebItemDTOList.add(zlWebItemDTO);
            }
        });
        zlWebDTO.setZlWebItemDTOList(zlWebItemDTOList);
        String shipmentTimeFrom = null;
        String shipmentTimeTo = null;
        if (order.getRequestCollectionTimeFrom() != null) {
            shipmentTimeFrom = order.getRequestCollectionTimeFrom().toString();
        } else {
            if (order.getRequestSnapshot() != null && order.getRequestSnapshot().getCollectionTimeFrom() != null) {
                shipmentTimeFrom = order.getRequestSnapshot().getCollectionTimeFrom().toString();
            }
        }
        if (order.getRequestCollectionTimeTo() != null) {
            shipmentTimeTo = order.getRequestCollectionTimeTo().toString();
        } else {
            if (order.getRequestSnapshot() != null && order.getRequestSnapshot().getCollectionTimeTo() != null) {
                shipmentTimeTo = order.getRequestSnapshot().getCollectionTimeTo().toString();
            }
        }
        zlWebDTO.setShipmentDateFrom(shipmentTimeFrom);
        zlWebDTO.setShipmentDateTo(shipmentTimeTo);
        HttpHeaders headers = setHeader(request);
        String urlPropertiesTtmi = urlProperties.getTtmi().concat(ParamConstant.ZLWeb.ZLWEB_URL);
        HttpEntity<ZLWebDTO> requestEntity =
            new HttpEntity<>(zlWebDTO, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                urlPropertiesTtmi,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            log.info("SUCCESS call Gateway-TTMI : " + response);
        } catch (Exception e) {
            log.error("ERROR call Gateway-TTMI : {}", e.getMessage());
        }

    }

    /**
     * HTTPヘッダーを設定する。
     *
     * @param httpServletRequest HTTPリクエスト
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader(HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
