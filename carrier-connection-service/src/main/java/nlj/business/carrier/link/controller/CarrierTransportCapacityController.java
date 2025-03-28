package nlj.business.carrier.link.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.NextResponseEntity;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.link.constant.APIConstant;
import nlj.business.carrier.link.constant.APIConstant.CarrierTransportCapacity;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.MessageConstant.System;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.commonBody.response.CommonResponseDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemRegisterRequest;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import nlj.business.carrier.link.service.TrspPlanLineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <PRE>
 * キャリア輸送能力コントローラー<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@Controller
@RequestMapping({CarrierTransportCapacity.PREFIX, CarrierTransportCapacity.DATA_CHANNEL_PREFIX})
@RequiredArgsConstructor
public class CarrierTransportCapacityController {

    private final ResponseBuilderComponent builderComponent;
    private final TrspPlanLineItemService trspPlanLineItemService;

    @Operation(summary = "運送計画情報（明細型）を登録", description = "運送計画情報（明細型）を登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "指定した条件に基づいて、運送能力情報（明細型）を正常に取得しました。"),
        @ApiResponse(responseCode = "400", description = "リクエスト自体に問題がある場合"),
        @ApiResponse(responseCode = "500", description = "システムの内部にてエラーが発生している場合")
    })
    @PutMapping()
    public NextResponseEntity<Void> register(@Valid @RequestBody CommonRequestDTO commonRequestDTO,
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse,
        HttpServletRequest httpServletRequest) {
        log.info("PUT /carrier_trans_capacity Request Body: {}", commonRequestDTO);
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TrspPlanLineItemRegisterRequest trspPlanLineItemRegisterRequest = new TrspPlanLineItemRegisterRequest();
        try {
            trspPlanLineItemRegisterRequest = objectMapper.convertValue(commonRequestDTO.getAttribute(),
                TrspPlanLineItemRegisterRequest.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
        }

        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        trspPlanLineItemService.registerTrspPlan(trspPlanLineItemRegisterRequest, xTracking);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "運送計画情報（明細型）を削除", description = "運送計画情報（明細型）を削除します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "運送能力情報（明細型）を正常に削除しました。"),
        @ApiResponse(responseCode = "400", description = "リクエスト自体に問題がある場合"),
        @ApiResponse(responseCode = "500", description = "システムの内部にてエラーが発生している場合")
    })
    @DeleteMapping()
    public NextResponseEntity<Void> delete(
        @RequestParam(value = ParamConstant.TRSP_INSTRUCTION_ID) String trspInstructionId,
        @RequestParam(value = ParamConstant.SERVICE_NO) String serviceNo,
        @RequestParam(value = ParamConstant.SERVICE_STRT_DATE) String serviceStrtDate,
        @RequestParam(value = ParamConstant.CNSG_PRTY_HEAD_OFF_ID) String cnsgPrtyHeadOffId,
        @RequestParam(value = ParamConstant.CNSG_PRTY_BRNC_OFF_ID) String cnsgPrtyBrncOffId,
        @RequestParam(value = ParamConstant.TRSP_RQR_PRTY_HEAD_OFF_ID) String trspRqrPrtyHeadOffId,
        @RequestParam(value = ParamConstant.TRSP_RQR_PRTY_BRNC_OFF_ID) String trspRqrPrtyBrncOffId,
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse,
        HttpServletRequest httpServletRequest) {
        log.info(
            "DELETE /carrier_trans_capacity Request Params: trsp_instruction_id={}, service_no={}, service_strt_date={}, cnsg_prty_head_off_id={}, cnsg_prty_brnc_off_id={}, trsp_rqr_prty_head_off_id={}, trsp_rqr_prty_brnc_off_id={}",
            trspInstructionId, serviceNo, serviceStrtDate, cnsgPrtyHeadOffId, cnsgPrtyBrncOffId, trspRqrPrtyHeadOffId,
            trspRqrPrtyBrncOffId);

        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        trspPlanLineItemService.deleteTrspPlanYamato(trspInstructionId, serviceNo, serviceStrtDate, cnsgPrtyHeadOffId,
            cnsgPrtyBrncOffId, trspRqrPrtyHeadOffId, trspRqrPrtyBrncOffId);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "指定した条件の運送能力情報（明細型）を取得", description = "指定した条件に基づいて、運送能力情報（明細型）を取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "指定した条件に基づいて、運送能力情報（明細型）を正常に取得しました。"),
        @ApiResponse(responseCode = "400", description = "リクエスト自体に問題がある場合"),
        @ApiResponse(responseCode = "500", description = "システムの内部にてエラーが発生している場合")
    })
    @GetMapping()
    public NextResponseEntity<CommonResponseDTO> searchTrspPlan(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse,
        @RequestParam(value = "service_no", required = false) String serviceNo,
        @RequestParam(value = "service_name", required = false) String serviceName,
        @RequestParam(value = "car_max_load_capacity1_meas", required = false) BigDecimal carMaxLoadCapacity1Meas,
        @RequestParam(value = "trsp_op_strt_area_line_one_txt", required = false) String trspOpStrtAreaLineOneTxt,
        @RequestParam(value = "trsp_op_end_area_line_one_txt", required = false) String trspOpEndAreaLineOneTxt,
        @RequestParam(value = "max_trsp_op_date_trm_strt_date", required = false) String maxTrspOpDateTrmStrtDate,
        @RequestParam(value = "min_trsp_op_date_trm_strt_date", required = false) String minTrspOpDateTrmStrtDate,
        @RequestParam(value = "max_trsp_op_date_trm_end_date", required = false) String maxTrspOpDateTrmEndDate,
        @RequestParam(value = "min_trsp_op_date_trm_end_date", required = false) String minTrspOpDateTrmEndDate,
        @RequestParam(value = "max_trsp_op_plan_date_trm_strt_time", required = false) String maxTrspOpPlanDateTrmStrtTime,
        @RequestParam(value = "min_trsp_op_plan_date_trm_strt_time", required = false) String minTrspOpPlanDateTrmStrtTime,
        @RequestParam(value = "max_trsp_op_plan_date_trm_end_time", required = false) String maxTrspOpPlanDateTrmEndTime,
        @RequestParam(value = "min_trsp_op_plan_date_trm_end_time", required = false) String minTrspOpPlanDateTrmEndTime,
        HttpServletRequest httpServletRequest) {
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        TrspPlanLineItemSearchRequest request = new TrspPlanLineItemSearchRequest();
        request.setServiceNo(serviceNo);
        request.setServiceName(serviceName);
        request.setCarMaxLoadCapacity1Meas(carMaxLoadCapacity1Meas);
        request.setTrspOpStrtAreaLineOneTxt(trspOpStrtAreaLineOneTxt);
        request.setTrspOpEndAreaLineOneTxt(trspOpEndAreaLineOneTxt);
        request.setMaxTrspOpDateTrmStrtDate(maxTrspOpDateTrmStrtDate);
        request.setMinTrspOpDateTrmStrtDate(minTrspOpDateTrmStrtDate);
        request.setMaxTrspOpDateTrmEndDate(maxTrspOpDateTrmEndDate);
        request.setMinTrspOpDateTrmEndDate(minTrspOpDateTrmEndDate);
        request.setMaxTrspOpPlanDateTrmStrtTime(maxTrspOpPlanDateTrmStrtTime);
        request.setMinTrspOpPlanDateTrmStrtTime(minTrspOpPlanDateTrmStrtTime);
        request.setMaxTrspOpPlanDateTrmEndTime(maxTrspOpPlanDateTrmEndTime);
        request.setMinTrspOpPlanDateTrmEndTime(minTrspOpPlanDateTrmEndTime);
        log.info("GET /carrier_trans_capacity Request Body: {}", request);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(trspPlanLineItemService.searchTrspPlan(request), httpServletRequest));
    }
}
