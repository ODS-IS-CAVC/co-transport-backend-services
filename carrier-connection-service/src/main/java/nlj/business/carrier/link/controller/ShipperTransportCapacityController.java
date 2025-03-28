package nlj.business.carrier.link.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.NextResponseEntity;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.link.constant.APIConstant;
import nlj.business.carrier.link.constant.APIConstant.ShipperTransportCapacity;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacityRegisterDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;
import nlj.business.carrier.link.service.TransportAbilityLineItemService;
import nlj.business.carrier.link.service.TransportAbilityMessageInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 荷主輸送能力コントローラー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping({ShipperTransportCapacity.PREFIX, ShipperTransportCapacity.DATA_CHANNEL_PREFIX})
@RequiredArgsConstructor
public class ShipperTransportCapacityController {

    private final TransportAbilityLineItemService transportAbilityLineItemService;

    private final TransportAbilityMessageInfoService transportAbilityMessageInfoService;

    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = ShipperTransportCapacity.SEARCH_SUMMARY, description = ShipperTransportCapacity.SEARCH_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping
    public NextResponseEntity<ShipperTransportCapacityRegisterDTO> getAllTransportData(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        @RequestParam(value = ParamConstant.SERVICE_NO, required = false) String serviceNo,
        @RequestParam(value = ParamConstant.SERVICE_NAME, required = false) String serviceName,
        @RequestParam(value = ParamConstant.CAR_MAX_LOAD_CAPACITY1, required = false) String carMaxLoadCapacity1Meas,
        @RequestParam(value = ParamConstant.TRSP_STRT_TXT, required = false) String trspOpStrtAreaLineOneTxt,
        @RequestParam(value = ParamConstant.TRSP_END_TXT, required = false) String trspOpEndAreaLineOneTxt,
        @RequestParam(value = ParamConstant.MAX_TRSP_STRT_DATE, required = false) String maxTrspOpDateTrmStrtDate,
        @RequestParam(value = ParamConstant.MIN_TRSP_STRT_DATE, required = false) String minTrspOpDateTrmStrtDate,
        @RequestParam(value = ParamConstant.MAX_TRSP_END_DATE, required = false) String maxTrspOpDateTrmEndDate,
        @RequestParam(value = ParamConstant.MIN_TRSP_END_DATE, required = false) String minTrspOpDateTrmEndDate,
        @RequestParam(value = ParamConstant.MAX_TRSP_STRT_TIME, required = false) String maxTrspOpPlanDateTrmStrtTime,
        @RequestParam(value = ParamConstant.MIN_TRSP_STRT_TIME, required = false) String minTrspOpPlanDateTrmStrtTime,
        @RequestParam(value = ParamConstant.MAX_TRSP_END_TIME, required = false) String maxTrspOpPlanDateTrmEndTime,
        @RequestParam(value = ParamConstant.MIN_TRSP_END_TIME, required = false) String minTrspOpPlanDateTrmEndTime,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) {
        ShipperTransportCapacitySearchDTO params = new ShipperTransportCapacitySearchDTO();
        params.setServiceNo(BaseUtil.isNull(serviceNo) ? null : serviceNo.trim());
        params.setServiceName(BaseUtil.isNull(serviceName) ? null : serviceName.trim());
        params.setCarMaxLoadCapacity1Meas(
            BaseUtil.isNull(carMaxLoadCapacity1Meas) ? null : carMaxLoadCapacity1Meas.trim());
        params.setTrspOpStrtAreaLineOneTxt(
            BaseUtil.isNull(trspOpStrtAreaLineOneTxt) ? null : trspOpStrtAreaLineOneTxt.trim());
        params.setTrspOpEndAreaLineOneTxt(
            BaseUtil.isNull(trspOpEndAreaLineOneTxt) ? null : trspOpEndAreaLineOneTxt.trim());
        params.setMaxTrspOpDateTrmStrtDate(BaseUtil.isNull(maxTrspOpDateTrmStrtDate) ? null : maxTrspOpDateTrmStrtDate);
        params.setMinTrspOpDateTrmStrtDate(BaseUtil.isNull(minTrspOpDateTrmStrtDate) ? null : minTrspOpDateTrmStrtDate);
        params.setMaxTrspOpDateTrmEndDate(BaseUtil.isNull(maxTrspOpDateTrmEndDate) ? null : maxTrspOpDateTrmEndDate);
        params.setMinTrspOpDateTrmEndDate(BaseUtil.isNull(minTrspOpDateTrmEndDate) ? null : minTrspOpDateTrmEndDate);
        params.setMaxTrspOpPlanDateTrmStrtTime(
            BaseUtil.isNull(maxTrspOpPlanDateTrmStrtTime) ? null : maxTrspOpPlanDateTrmStrtTime);
        params.setMinTrspOpPlanDateTrmStrtTime(
            BaseUtil.isNull(minTrspOpPlanDateTrmStrtTime) ? null : minTrspOpPlanDateTrmStrtTime);
        params.setMaxTrspOpPlanDateTrmEndTime(
            BaseUtil.isNull(maxTrspOpPlanDateTrmEndTime) ? null : maxTrspOpPlanDateTrmEndTime);
        params.setMinTrspOpPlanDateTrmEndTime(
            BaseUtil.isNull(minTrspOpPlanDateTrmEndTime) ? null : minTrspOpPlanDateTrmEndTime);
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        log.info("GET /shipper_trans_capacity Request Body: {}", params);
        if (!BaseUtil.isNull(xTracking)) {
            try {
                UUID.fromString(xTracking);
            } catch (Exception e) {
                throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                    MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
            }
        }
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transportAbilityLineItemService.getAllTransportAbilityLineItems(params),
                httpServletRequest));
    }

    @Operation(summary = ShipperTransportCapacity.REGISTER_SUMMARY, description = ShipperTransportCapacity.REGISTER_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.REGISTER_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping
    public NextResponseEntity<Object> register(@Valid @RequestBody ShipperTransportCapacityRegisterDTO registerDTO,
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("PUT /shipper_trans_capacity Request Body: {}", registerDTO);
        transportAbilityMessageInfoService.registerOrUpdateTransportInfor(registerDTO, xTracking);
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(ShipperTransportCapacity.REGISTER_STATUS_OK_DESCRIPTION,
                httpServletRequest));
    }

    @Operation(summary = ShipperTransportCapacity.DELETE_SUMMARY, description = ShipperTransportCapacity.DELETE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.DELETE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @DeleteMapping
    public NextResponseEntity<Object> delete(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        @RequestParam(value = ParamConstant.TRSP_CLI_PRTY_HEAD_OFF_ID) String trspCliPrtyHeadOffId,
        @RequestParam(value = ParamConstant.TRSP_CLI_PRTY_BRNC_OFF_ID) String trspCliPrtyBrncOffId,
        @RequestParam(value = ParamConstant.SERVICE_NO) String serviceNo,
        @RequestParam(value = ParamConstant.SERVICE_STRT_DATE) String serviceStrtDate,
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        log.info(
            "DELETE /shipper_trans_capacity Request Params: trsp_cli_prty_head_off_id={}, trsp_cli_prty_brnc_off_id={}, service_no={}, service_strt_date={}",
            trspCliPrtyHeadOffId, trspCliPrtyBrncOffId, serviceNo, serviceStrtDate);
        if (!BaseUtil.isNull(xTracking)) {
            try {
                UUID.fromString(xTracking);
            } catch (Exception e) {
                throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
            }
        }
        transportAbilityMessageInfoService.deleteTransportInforYamato(trspCliPrtyHeadOffId, trspCliPrtyBrncOffId,
            serviceNo, serviceStrtDate);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(ShipperTransportCapacity.DELETE_STATUS_OK_DESCRIPTION, httpServletRequest));
    }
}
