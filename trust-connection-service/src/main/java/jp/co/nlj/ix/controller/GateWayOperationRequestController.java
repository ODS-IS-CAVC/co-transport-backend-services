package jp.co.nlj.ix.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.OperationRequest;
import jp.co.nlj.ix.constant.APIConstant.ShipperTransportCapacity;
import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestInsertReqDTO;
import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestNotifyRequest;
import jp.co.nlj.ix.dto.operationRequest.request.OperationRequestReplyRequest;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestInsertResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestNotifyResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestReplyResponse;
import jp.co.nlj.ix.dto.operationRequest.response.OperationRequestUpdateResponse;
import jp.co.nlj.ix.service.GateWayOperationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * GateWayOperationRequestControllerクラスは、運行実施（確認）通知コントローラです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping(OperationRequest.PREFIX)
@RequiredArgsConstructor
public class GateWayOperationRequestController extends BaseController {

    private final GateWayOperationRequestService gateWayOperationRequestService;

    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(OperationRequest.INSERT)
    public NextResponseEntity<OperationRequestInsertResponse> insertPlan(HttpServletRequest httpServletRequest,
        @RequestBody OperationRequestInsertReqDTO requestDTO, @RequestParam(value = "from_cid") String fromCid,
        @RequestParam(value = "to_cid") String toCid) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(
            builderComponent.buildResponse(gateWayOperationRequestService.insertPlans(requestDTO, paramUrl),
                httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(OperationRequest.UPDATE)
    public NextResponseEntity<OperationRequestUpdateResponse> updatePlan(HttpServletRequest httpServletRequest,
        @RequestBody OperationRequestInsertReqDTO requestDTO, @PathVariable("operation_id") String operationId,
        @PathVariable("propose_id") String proposeId, @RequestParam(value = "from_cid") String fromCid,
        @RequestParam(value = "to_cid") String toCid) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                gateWayOperationRequestService.updatePlan(requestDTO, operationId, proposeId, paramUrl),
                httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(OperationRequest.NOTIFY)
    public NextResponseEntity<OperationRequestNotifyResponse> planNotify(HttpServletRequest httpServletRequest,
        @RequestBody Object requestDTO, @PathVariable("operation_id") String operationId,
        @PathVariable("propose_id") String proposeId, @RequestParam(value = "from_cid") String fromCid,
        @RequestParam(value = "to_cid") String toCid, @RequestParam(value = "shipper_cid") String shipperCid) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        OperationRequestNotifyRequest requestDTOT = new OperationRequestNotifyRequest();
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                gateWayOperationRequestService.planNotify(requestDTOT, operationId, proposeId, paramUrl),
                httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(OperationRequest.REPLY)
    public NextResponseEntity<OperationRequestReplyResponse> planReply(HttpServletRequest httpServletRequest,
        @RequestBody OperationRequestReplyRequest requestDTO, @PathVariable("operation_id") String operationId,
        @PathVariable("propose_id") String proposeId, @RequestParam(value = "from_cid") String fromCid,
        @RequestParam(value = "to_cid") String toCid, @RequestParam(value = "replay") String replay) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                gateWayOperationRequestService.planReply(requestDTO, operationId, proposeId, paramUrl),
                httpServletRequest));
    }
}
