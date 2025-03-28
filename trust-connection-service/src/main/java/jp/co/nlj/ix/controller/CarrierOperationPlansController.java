package jp.co.nlj.ix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.ShipperOperationPlans;
import jp.co.nlj.ix.constant.APIConstant.TrspPlanLineItem;
import jp.co.nlj.ix.constant.MessageConstant.APIResponses;
import jp.co.nlj.ix.constant.ParamConstant;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.CarrierOperationApprovalRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.CarrierOperationApprovalResponseDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.AdvancedRequest;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.CarrierOperatorPlansRequest;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.CarrierOperatorPlansInsertResponse;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.CarrierOperatorPlansUpdateResponse;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.TrspPlanLineItemSearchResponse;
import jp.co.nlj.ix.service.CarrierOperationPlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <PRE>
 * 輸送計画明細項目コントローラ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(TrspPlanLineItem.PREFIX)
public class CarrierOperationPlansController extends BaseController {

    private final ResponseBuilderComponent builderComponent;
    private final CarrierOperationPlansService carrierOperationPlansService;

    @Operation(summary = "指定した条件の運送能力情報（明細型）を取得", description = "指定した条件に基づいて、運送能力情報（明細型）を取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TrspPlanLineItem.STATUS_OK_MSG),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping()
    public NextResponseEntity<TrspPlanLineItemSearchResponse> searchTrspPlan(@Valid
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
        @RequestParam(value = ParamConstant.ADVANCED_CONDITIONS, required = false) String advanced,
        HttpServletRequest httpServletRequest) {
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
        if (advanced != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                AdvancedRequest advancedRequest = objectMapper.readValue(advanced, AdvancedRequest.class);
                request.setAdvancedConditions(advancedRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new NextResponseEntity<>(
            builderComponent.buildResponse(carrierOperationPlansService.searchTransportPlan(request),
                httpServletRequest));
    }

    @Operation(summary = "指定した条件の運送能力情報（明細型）を取得", description = "指定した条件に基づいて、運送能力情報（明細型）を取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TrspPlanLineItem.STATUS_OK_MSG),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(TrspPlanLineItem.PROPOSE)
    public NextResponseEntity<CarrierOperatorPlansInsertResponse> insertByPropose(@Valid @RequestBody
    CarrierOperatorPlansRequest request, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(carrierOperationPlansService.insertItem(request), httpServletRequest));
    }

    @Operation(summary = "指定した条件の運送能力情報（明細型）を取得", description = "指定した条件に基づいて、運送能力情報（明細型）を取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TrspPlanLineItem.STATUS_OK_MSG),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(TrspPlanLineItem.PROPOSE_OPERATION)
    public NextResponseEntity<CarrierOperatorPlansUpdateResponse> updateByPropose(
        @Valid @PathVariable("operation_id") String operationId,
        @PathVariable("propose_id") String proposeId,
        @RequestBody CarrierOperatorPlansRequest request, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(carrierOperationPlansService.updateItem(request, proposeId, operationId),
                httpServletRequest));
    }

    @Operation(summary = ShipperOperationPlans.SUMMARY, description = ShipperOperationPlans.DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG_JA),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PostMapping(TrspPlanLineItem.APPROVAL_PREFIX)
    public NextResponseEntity<CarrierOperationApprovalResponseDTO> carrierOperationApproval(
        @Valid @PathVariable("operation_id") String operationIdParam,
        @PathVariable("propose_id") String proposeId,
        @Valid @RequestBody CarrierOperationApprovalRequestDTO carrierOperationApprovalRequestDTO,
        HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                carrierOperationPlansService.carrierOperationApproval(carrierOperationApprovalRequestDTO,
                    operationIdParam, proposeId),
                request
            )
        );
    }
}
