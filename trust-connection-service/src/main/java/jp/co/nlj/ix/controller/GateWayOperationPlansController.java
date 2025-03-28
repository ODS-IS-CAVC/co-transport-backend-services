package jp.co.nlj.ix.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.OperationPlans;
import jp.co.nlj.ix.constant.APIConstant.ShipperTransportCapacity;
import jp.co.nlj.ix.dto.operationPlans.request.OperationPlansNotifyRequestDTO;
import jp.co.nlj.ix.dto.operationPlans.request.OperationPlansRequestDTO;
import jp.co.nlj.ix.dto.operationPlans.response.OperationPlansInsertResponse;
import jp.co.nlj.ix.dto.operationPlans.response.OperationPlansNotifyResponse;
import jp.co.nlj.ix.service.GateWayOperationPlansService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * GateWayOperationPlansControllerクラスは、運行実施（確認）通知コントローラです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping(OperationPlans.PREFIX)
@RequiredArgsConstructor
public class GateWayOperationPlansController extends BaseController {

    private final GateWayOperationPlansService operationPlansService;

    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping()
    public NextResponseEntity<OperationPlansInsertResponse> insertPlan(HttpServletRequest httpServletRequest,
        @RequestBody OperationPlansRequestDTO requestDTO, @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "carrier_cid") String carrier,
        @RequestParam(value = "trsp_instruction_id") String trspInstructionId,
        @RequestParam(value = "departure_mh") String departureMh,
        @RequestParam(value = "departure_mh_space_list") String departureMhSpaceList,
        @RequestParam(value = "arrival_mh") String arrivalMh,
        @RequestParam(value = "arrival_mh_space_list") String arrivalMhSpaceList,
        @RequestParam(value = "tractor_giai") String tractorGiai,
        @RequestParam(value = "req_from_time") String reqFromTime,
        @RequestParam(value = "req_to_time") String reqToTime) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(
            builderComponent.buildResponse(operationPlansService.insertPlans(requestDTO, paramUrl),
                httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(OperationPlans.NOTIFY)
    public NextResponseEntity<OperationPlansNotifyResponse> pushNotify(HttpServletRequest httpServletRequest,
        @PathVariable("operation_id") String operationId,
        @RequestBody OperationPlansNotifyRequestDTO requestDTO,
        @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "carrier_cid") String carrier) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(
            builderComponent.buildResponse(operationPlansService.pushNotify(requestDTO, operationId, paramUrl),
                httpServletRequest));
    }
}
