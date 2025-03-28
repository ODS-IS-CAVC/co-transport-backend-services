package jp.co.nlj.ix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.UUID;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.ShipperTransportCapacity;
import jp.co.nlj.ix.constant.MessageConstant;
import jp.co.nlj.ix.constant.ParamConstant;
import jp.co.nlj.ix.dto.shipperTrspCapacity.AdvancedRequest;
import jp.co.nlj.ix.dto.shipperTrspCapacity.AttributeDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;
import jp.co.nlj.ix.service.TransportAbilityLineItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
@RestController
@RequestMapping(ShipperTransportCapacity.PREFIX)
@RequiredArgsConstructor
public class ShipperTransportCapacityController {

    private final TransportAbilityLineItemService transportAbilityLineItemService;

    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = ShipperTransportCapacity.SEARCH_SUMMARY, description = ShipperTransportCapacity.SEARCH_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping
    public NextResponseEntity<AttributeDTO> getAllTransportData(
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
        @RequestParam(value = ParamConstant.ADVANCED_CONDITIONS, required = false) String advanced,
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "limit", defaultValue = "20") int limit,

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
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AdvancedRequest advancedRequest = objectMapper.readValue(advanced, AdvancedRequest.class);
            params.setAdvanced(advancedRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        if (!BaseUtil.isNull(xTracking)) {
            try {
                UUID.fromString(xTracking);
            } catch (Exception e) {
                throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                    MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
            }
        }
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transportAbilityLineItemService.getAllTransportAbilityLineItems(params, page, limit), httpServletRequest));
    }
}
