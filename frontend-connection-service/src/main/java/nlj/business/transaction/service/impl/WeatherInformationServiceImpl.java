package nlj.business.transaction.service.impl;

import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.data.util.ValidateUtil;
import com.next.logistic.mail.util.NextMailUtil;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.NextWebUtil;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.constant.MessageConstant.WeatherInformation;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.carrier.VehicleDiagramItemCarrier;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.dto.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.transaction.dto.request.UpdateTrackStatusRequest;
import nlj.business.transaction.dto.request.WeatherRequest;
import nlj.business.transaction.dto.response.VehicleDiagramStatusResponseDTO;
import nlj.business.transaction.dto.response.WeatherResponse;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.repository.VehicleDiagramItemCarrierRepository;
import nlj.business.transaction.service.WeatherInformationService;
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
 * 天気情報サービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherInformationServiceImpl implements WeatherInformationService {

    private final ValidateUtil validateUtil;
    private final TransOrderRepository transOrderRepository;
    private final NljAuthProperties authProperties;
    private final NljUrlProperties urlProperties;
    private final RestTemplate restTemplate;
    private final NextMailUtil nextMailUtil;
    private final VehicleDiagramItemCarrierRepository vehicleDiagramItemCarrierRepository;
    private final EntityManager entityManager;

    /**
     * 天気情報を検証する。
     *
     * @param transOrderId            トランスオーダーID
     * @param departureArrivalTimeDTO 出発到着時間DTO
     * @param weatherRequest          天気リクエスト
     * @return 天気レスポンス
     */
    @Override
    public WeatherResponse validateWeatherInformation(Long transOrderId,
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO, WeatherRequest weatherRequest) {
        TransOrder transOrder = getTransOrder(transOrderId);
        int checkErrorCode = MessageConstant.WeatherInformation.ERROR_CODE_0;
        WeatherResponse response = new WeatherResponse();
        response.setStatus(WeatherInformation.STATUS_SUCCESS);
        response.setMessage(WeatherInformation.MESSAGE_ERROR_0);
        if (!Objects.isNull(weatherRequest) && !weatherRequest.isIgnore()) {
            checkErrorCode = validateUtil.validateCommon(transOrder.getTransportDate(),
                departureArrivalTimeDTO.getDepartureTime(),
                departureArrivalTimeDTO.getArrivalTime());
            if (checkErrorCode == WeatherInformation.ERROR_CODE_1 && response.getStatus()
                .equals(WeatherInformation.STATUS_SUCCESS)) {
                response.setStatus(WeatherInformation.STATUS_SUCCESS);
                response.setMessage(WeatherInformation.MESSAGE_ERROR_1);
            } else if (checkErrorCode != WeatherInformation.ERROR_CODE_0) {
                response.setStatus(WeatherInformation.STATUS_ERROR);
                if (checkErrorCode == WeatherInformation.ERROR_CODE_2) {
                    response.setMessage(WeatherInformation.MESSAGE_ERROR_2);
                } else if (checkErrorCode == WeatherInformation.ERROR_CODE_3) {
                    response.setMessage(WeatherInformation.MESSAGE_ERROR_3);
                } else if (checkErrorCode == WeatherInformation.ERROR_CODE_4) {
                    response.setMessage(WeatherInformation.MESSAGE_ERROR_4);
                } else if (checkErrorCode == WeatherInformation.ERROR_CODE_5) {
                    response.setMessage(WeatherInformation.MESSAGE_ERROR_5);
                }
            }
        }

        return response;
    }

    /**
     * ダイアグラムアイテムIDによる天気情報を検証する。
     *
     * @param diagramItemId           ダイアグラムアイテムID
     * @param departureArrivalTimeDTO 出発到着時間DTO
     * @param timeRange               時間範囲
     * @return 車両ダイアグラムステータスレスポンスDTO
     */
    @Override
    public VehicleDiagramStatusResponseDTO validateWeatherInformationByDiagramItemId(Long diagramItemId,
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO, Long timeRange) {
        if (timeRange == null || !isValidTimeRange(timeRange)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.Validate.VALID_PARAM,
                "time range");
        }

        VehicleDiagramStatusResponseDTO responseDTO = new VehicleDiagramStatusResponseDTO();
        int checkErrorCode = validateUtil.validateCommon(departureArrivalTimeDTO.getOperationDate(),
            departureArrivalTimeDTO.getDepartureTime(),
            departureArrivalTimeDTO.getArrivalTime());

        setResponseStatusAndMessage(responseDTO, checkErrorCode);
        VehicleDiagramItemCarrier vehicleDiagramItemCarrier = vehicleDiagramItemCarrierRepository.findVehicleDiagramItemById(
            diagramItemId);
        if (vehicleDiagramItemCarrier != null && (checkErrorCode == ParamConstant.WeatherInformation.ERROR_CODE_2
            || checkErrorCode == ParamConstant.WeatherInformation.ERROR_CODE_3)) {
            vehicleDiagramItemCarrier.setIncidentType(ParamConstant.WeatherInformation.INCIDENT_TYPE_WEATHER_STOP);
            vehicleDiagramItemCarrier.setIncidentMsg(responseDTO.getMessage());
            vehicleDiagramItemCarrierRepository.save(vehicleDiagramItemCarrier);
        } else if (vehicleDiagramItemCarrier != null && (checkErrorCode == ParamConstant.WeatherInformation.ERROR_CODE_4
            || checkErrorCode == ParamConstant.WeatherInformation.ERROR_CODE_5)) {
            vehicleDiagramItemCarrier.setIncidentType(ParamConstant.WeatherInformation.INCIDENT_TYPE_TRAFFIC_STOP);
            vehicleDiagramItemCarrier.setIncidentMsg(responseDTO.getMessage());
            vehicleDiagramItemCarrierRepository.save(vehicleDiagramItemCarrier);
        }

        String status = determineStatus(checkErrorCode);
        updateWeatherInformationStatus(diagramItemId, status, timeRange, responseDTO.getMessage());
        return responseDTO;
    }

    /**
     * 有効な時間範囲を確認する。
     *
     * @param timeRange 時間範囲
     * @return 有効な場合はtrue、無効な場合はfalse
     */
    private boolean isValidTimeRange(Long timeRange) {
        return timeRange.equals(ParamConstant._72H) || timeRange.equals(ParamConstant._3H)
            || timeRange.equals(ParamConstant._2H) || timeRange.equals(ParamConstant._1H);
    }

    /**
     * レスポンスのステータスとメッセージを設定する。
     *
     * @param responseDTO    車両ダイアグラムステータスレスポンスDTO
     * @param checkErrorCode エラーコード
     */
    private void setResponseStatusAndMessage(VehicleDiagramStatusResponseDTO responseDTO, int checkErrorCode) {
        if (checkErrorCode == ParamConstant.WeatherInformation.ERROR_CODE_1) {
            responseDTO.setStatus(ParamConstant.WeatherInformation.STATUS_SUCCESS);
            responseDTO.setMessage(ParamConstant.WeatherInformation.MESSAGE_ERROR_1);
        } else if (checkErrorCode != ParamConstant.WeatherInformation.ERROR_CODE_0) {
            responseDTO.setStatus(ParamConstant.WeatherInformation.STATUS_ERROR);
            responseDTO.setMessage(getErrorMessageForCode(checkErrorCode));
        } else {
            responseDTO.setStatus(ParamConstant.WeatherInformation.STATUS_SUCCESS);
            responseDTO.setMessage(ParamConstant.WeatherInformation.MESSAGE_ERROR_0);
        }
    }

    /**
     * エラーコードに基づいてエラーメッセージを取得する。
     *
     * @param checkErrorCode エラーコード
     * @return エラーメッセージ
     */
    private String getErrorMessageForCode(int checkErrorCode) {
        return switch (checkErrorCode) {
            case ParamConstant.WeatherInformation.ERROR_CODE_2 -> ParamConstant.WeatherInformation.MESSAGE_ERROR_2;
            case ParamConstant.WeatherInformation.ERROR_CODE_3 -> ParamConstant.WeatherInformation.MESSAGE_ERROR_3;
            case ParamConstant.WeatherInformation.ERROR_CODE_4 -> ParamConstant.WeatherInformation.MESSAGE_ERROR_4;
            case ParamConstant.WeatherInformation.ERROR_CODE_5 -> ParamConstant.WeatherInformation.MESSAGE_ERROR_5;
            default -> ParamConstant.WeatherInformation.MESSAGE_ERROR_0;
        };
    }

    /**
     * メールを送信する必要があるかどうかを確認する。
     *
     * @param checkErrorCode エラーコード
     * @return 送信する必要がある場合はtrue、そうでない場合はfalse
     */
    private boolean shouldSendEmail(int checkErrorCode) {
        return checkErrorCode != ParamConstant.WeatherInformation.ERROR_CODE_0
            && checkErrorCode != ParamConstant.WeatherInformation.ERROR_CODE_1;
    }

    /**
     * メール通知を送信する。
     *
     * @param diagramItemId ダイアグラムアイテムID
     * @param mail          メールアドレス
     * @param message       メッセージ
     */
    private void sendEmailNotification(Long diagramItemId, String mail, String message) {
        boolean emailSent = nextMailUtil.sendMail(mail,
            "Vehicle item: " + diagramItemId + "-" + message,
            "Vehicle item: " + diagramItemId + "-" + message,
            "mail-template");
        log.info("{} to send mail to : {}", emailSent ? "Success" : "Failed", mail);
    }

    /**
     * ステータスを決定する。
     *
     * @param checkErrorCode エラーコード
     * @return ステータス
     */
    private String determineStatus(int checkErrorCode) {
        return checkErrorCode == ParamConstant.WeatherInformation.ERROR_CODE_0
            ? ParamConstant.WeatherInformation.STATUS_SUCCESS
            : ParamConstant.WeatherInformation.STATUS_ERROR;
    }

    /**
     * 天気情報のステータスを更新する。
     *
     * @param diagramItemId ダイアグラムアイテムID
     * @param status        ステータス
     * @param type          タイプ
     * @param message       メッセージ
     */
    @Override
    @Transactional
    public void updateWeatherInformationStatus(Long diagramItemId, String status, Long type, String message) {
        try {
            String sql = "SELECT id FROM c_vehicle_diagram_item_trailer WHERE vehicle_diagram_item_id = ?";
            List<Long> resultList = entityManager.createNativeQuery(sql)
                .setParameter(1, diagramItemId).getResultList();
            if (!resultList.isEmpty()) {
                List<TransOrder> transOrders = transOrderRepository.findAllByVehicleDiagramItemTrailerIdIn(resultList);

                for (TransOrder transOrder : transOrders) {
                    if (status.equals(ParamConstant.Status.START)) {
                        if (transOrder.getTransType() == 0) {
                            transOrder.setStatus(160);
                        } else if (transOrder.getTransType() == 1) {
                            transOrder.setStatus(260);
                        }
                    } else if (status.equals(ParamConstant.Status.END)) {
                        if (transOrder.getTransType() == 0) {
                            transOrder.setStatus(161);
                        } else if (transOrder.getTransType() == 1) {
                            transOrder.setStatus(261);
                        }
                    }
                    transOrderRepository.save(transOrder);
                }
            }
        } catch (Exception ex) {
            log.error("Validate Weather: Error updated order:" + Arrays.toString(ex.getStackTrace()));
        }

        HttpHeaders headers = setHeader();
        String urlPropertiesTime = urlProperties.getDomainCarrier()
            .concat(APIConstant.CarrierDiagramItem.UPDATE_DIAGRAM_ITEM_STATUS).concat("/")
            .concat(diagramItemId.toString()).concat("/").concat(String.valueOf(type));
        UpdateTrackStatusRequest request = new UpdateTrackStatusRequest();
        request.setStatus(status);
        request.setMessage(message);
        HttpEntity<UpdateTrackStatusRequest> requestEntity =
            new HttpEntity<>(request, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                urlPropertiesTime,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            log.info("SUCCESS call Carrier Service : " + response);
        } catch (Exception e) {
            log.error("ERROR call Carrier Service : {}", e.getMessage());
            NextWebUtil.throwCustomException(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstant.System.SYSTEM_ERR_001);
        }
    }

    /**
     * トランスオーダーを取得する。
     *
     * @param transOrderId トランスオーダーID
     * @return トランスオーダー
     */
    private TransOrder getTransOrder(Long transOrderId) {
        TransOrder transOrder = transOrderRepository.findById(transOrderId).orElse(null);
        if (transOrder == null) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, MessageConstant.System.NOT_FOUND, "trans order");
        }
        return transOrder;
    }

    /**
     * HTTPヘッダーを設定する。
     *
     * @return HTTPヘッダー
     */
    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
