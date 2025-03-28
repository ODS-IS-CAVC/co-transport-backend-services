package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.dto.PageResponseDTO;
import nlj.business.transaction.dto.TransactionDetailResponseDTO;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.request.CarrierTransportProposalRequest;
import nlj.business.transaction.dto.request.CarrierTransportProposalSearch;
import nlj.business.transaction.dto.request.OrderEmergencyUpdateRequest;
import nlj.business.transaction.dto.request.TransMatchingRequest;
import nlj.business.transaction.dto.request.TransactionCarrier2Request;
import nlj.business.transaction.dto.request.TransactionCarrierFERequest;
import nlj.business.transaction.dto.request.TransactionCarrierRequest;
import nlj.business.transaction.dto.request.TransactionCarrierTransportRequest;
import nlj.business.transaction.dto.request.TransactionContractRequest;
import nlj.business.transaction.dto.request.TransactionIdRequest;
import nlj.business.transaction.dto.request.TransactionShipperApprovalRequest;
import nlj.business.transaction.dto.request.TransactionShipperSearch;
import nlj.business.transaction.dto.request.TransactionShipperUpdateRequest;
import nlj.business.transaction.dto.response.CarrierProposalItemResponseDTO;
import nlj.business.transaction.dto.response.TransactionResponseDataCommon;
import nlj.business.transaction.service.TransOrderService;
import nlj.business.transaction.service.TransPreOrderService;
import nlj.business.transaction.service.TransactionTimeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 転送コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.Transaction.PREFIX)
@RequiredArgsConstructor
public class TransactionController {

    private final TransPreOrderService transPreOrderService;
    private final ResponseBuilderComponent builderComponent;
    private final TransOrderService transOrderService;
    private final TransactionTimeService transactionTimeService;

    //ATH-008
    @Operation(summary = APIConstant.Transaction.SHIPPER_REQUEST_POST_SUMMARY, description = APIConstant.Transaction.SHIPPER_REQUEST_POST_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.SHIPPER_REQUEST_POST_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @PostMapping(value = APIConstant.Transaction.SHIPPER_REQUEST_POST)
    public NextResponseEntity<Object> insertTransPreOrderShipper(@RequestBody TransMatchingRequest transMatchingRequest,
        HttpServletRequest httpServletRequest) {
        BigInteger resultId = transOrderService.insertShipperTransaction(transMatchingRequest, httpServletRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID, resultId);
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    @Operation(summary = APIConstant.Transaction.SHIPPER_REQUEST_SUMMARY, description = APIConstant.Transaction.SHIPPER_REQUEST_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.SHIPPER_REQUEST_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @GetMapping(value = APIConstant.Transaction.SHIPPER_REQUEST)
    NextResponseEntity<Object> searchTransPreOrderShipper(HttpServletRequest httpServletRequest,
        @RequestParam(name = "status", required = false) String status,
        @RequestParam(name = "freeWord", required = false) String freeWord,
        @RequestParam(name = "temperatureRange", required = false) List<Integer> temperatureRange,
        @RequestParam(name = "advanceStatus", required = false) List<String> advanceStatus,
        @RequestParam(name = ParamConstant.TransportAbility.PAGE, defaultValue = ParamConstant.DEFAULT_PAGE_NO) String page,
        @RequestParam(name = ParamConstant.TransportAbility.LIMIT, defaultValue = ParamConstant.DEFAULT_PAGE_LIMIT) String limit) {
        TransactionShipperSearch searchRequest = new TransactionShipperSearch();
        searchRequest.setStatus(status);
        searchRequest.setFreeWord(freeWord);
        searchRequest.setTemperatureRange(temperatureRange);
        searchRequest.setPage(page);
        searchRequest.setLimit(limit);
        searchRequest.setAdvanceStatus(advanceStatus);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transOrderService.getPagedShipperTransaction(searchRequest),
                httpServletRequest));
    }

    @Operation(summary = APIConstant.Transaction.LIST_TRANSACTION_SUMMARY, description = APIConstant.Transaction.LIST_TRANSACTION_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.LIST_TRANSACTION_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @GetMapping(value = APIConstant.Transaction.LIST_TRANSACTION)
    NextResponseEntity<PageResponseDTO<TransMatchingHeadResponse>> searchTransPreOrderCarrier(
        @RequestParam(name = "status", required = false) String status,
        @RequestParam(name = "freeWord", required = false) String freeWord,
        @RequestParam(name = "temperatureRange", required = false) List<Integer> temperatureRange,
        @RequestParam(name = "advanceStatus", required = false) List<String> advanceStatus,
        @RequestParam(name = "transType", required = false) String transType,
        @RequestParam(name = ParamConstant.TransportAbility.PAGE, defaultValue = ParamConstant.DEFAULT_PAGE_NO) int page,
        @RequestParam(name = ParamConstant.TransportAbility.LIMIT, defaultValue = ParamConstant.DEFAULT_PAGE_LIMIT) int limit,
        @RequestParam(name = "isEmergency", required = false) Integer isEmergency,
        HttpServletRequest httpServletRequest) {
        Page<TransMatchingHeadResponse> list = transOrderService.getTransactionByTrailer(transType, advanceStatus,
            status, freeWord, temperatureRange, page, limit, isEmergency);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(new PageResponseDTO<>(list), httpServletRequest));
    }

    @Operation(summary = APIConstant.Transaction.CARRIER_REQUEST_CARRIER_SUMMARY, description = APIConstant.Transaction.CARRIER_REQUEST_CARRIER_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.CARRIER_REQUEST_CARRIER_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.Transaction.CARRIER_REQUEST_CARRIER)
    public NextResponseEntity<Object> createCarrierTransportProposal(
        @RequestBody CarrierTransportProposalRequest request, HttpServletRequest httpServletRequest) {
        Map<String, Object> data = new HashMap<>();
        data.put(DataBaseConstant.TransOrder.ID,
            transOrderService.createCarrierTransportProposal(request, httpServletRequest));
        return new NextResponseEntity<>(builderComponent.buildResponse(data, httpServletRequest));
    }

    @Operation(summary = APIConstant.Transaction.CARRIER_REQUEST_CARRIER_SUMMARY, description = APIConstant.Transaction.CARRIER_REQUEST_CARRIER_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.CARRIER_REQUEST_CARRIER_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.Transaction.CARRIER_REQUEST_CARRIER)
    public NextResponseEntity<Object> getCarrierTransportProposal(CarrierTransportProposalSearch searchRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transOrderService.getPagedCarrierProposal(searchRequest),
                httpServletRequest));
    }

    //ATH-308
    @Operation(summary = APIConstant.Transaction.CARRIER_GET_ITEM_CARRIER_SUMMARY, description = APIConstant.Transaction.CARRIER_GET_ITEM_CARRIER_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.CARRIER_GET_ITEM_CARRIER_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.Transaction.CARRIER_GET_ITEM_CARRIER)
    public TransactionResponseDataCommon<CarrierProposalItemResponseDTO> getCarrierTransportProposalItem(
        @PathVariable(ParamConstant.Transaction.ID) Long transOrderId, HttpServletRequest httpServletRequest) {
        CarrierProposalItemResponseDTO transactionDetails = transOrderService.getCarrierProposalItem(transOrderId);
        TransactionResponseDataCommon response = new TransactionResponseDataCommon<>();
        response.setData(transactionDetails);
        return response;
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER_CARRIER_REQUEST)
    NextResponseEntity<Object> updateStatusTransaction(@PathVariable(ParamConstant.Transaction.ID) Long id,
        HttpServletRequest httpServletRequest) {
        transOrderService.updateStatusTransaction(id, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER_CARRIER_APPROVE)
    NextResponseEntity<Object> updateStatusTransactionApprove(@PathVariable(ParamConstant.Transaction.ID) Long id,
        @RequestBody TransactionContractRequest request, HttpServletRequest httpServletRequest) {
        transOrderService.updateStatusTransactionCarrierApproval(id, request.isApproval(), httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER_CARRIER_CONTRACT)
    NextResponseEntity<Object> updateStatusTransactionContract(@PathVariable(ParamConstant.Transaction.ID) Long id,
        @RequestBody TransactionContractRequest request, HttpServletRequest httpServletRequest) {
        transOrderService.updateStatusTransactionCarrierContract(id, request.isApproval(), httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER_CARRIER_PAYMENT)
    NextResponseEntity<Object> updateStatusTransactionPayment(@PathVariable(ParamConstant.Transaction.ID) Long id,
        HttpServletRequest httpServletRequest) {
        transOrderService.updateStatusTransactionCarrierPayment(id, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER_CARRIER_CANCEL)
    NextResponseEntity<Object> updateStatusTransactionCancel(@PathVariable(ParamConstant.Transaction.ID) Long id,
        HttpServletRequest httpServletRequest) {
        transOrderService.updateStatusTransactionCarrierPayment(id, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER_CARRIER_TRANSPORT)
    NextResponseEntity<Object> updateStatusTransactionTransport(@PathVariable(ParamConstant.Transaction.ID) Long id,
        @RequestBody TransactionCarrierTransportRequest request, HttpServletRequest httpServletRequest) {
        transOrderService.updateStatusTransactionCarrierTransport(id, request.getStatus(), httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @GetMapping(value = APIConstant.Transaction.TRANSACTION_DETAIL)
    public TransactionResponseDataCommon<TransactionDetailResponseDTO> detailTransactionCarrier(
        @PathVariable(ParamConstant.Transaction.ID) String id,
        HttpServletRequest httpServletRequest) {

        TransactionDetailResponseDTO transactionDetails = transOrderService.getDetailTransaction(id);
        TransactionResponseDataCommon response = new TransactionResponseDataCommon<>();
        response.setData(transactionDetails);
        return response;

    }

    //ATH-0211
    @Operation(summary = APIConstant.Transaction.TRANSPORT_PROPOSAL_IX_SUMMARY, description = APIConstant.Transaction.TRANSPORT_PROPOSAL_IX_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.TRANSPORT_PROPOSAL_IX_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @PostMapping(value = APIConstant.Transaction.TRANSPORT_PROPOSAL_IX)
    public NextResponseEntity<Object> postTransactionCarrierIX(@RequestBody TransactionCarrierRequest requestBody,
        HttpServletRequest httpServletRequest) {
        BigInteger insertedTransOrderId = transOrderService.createCarrierTransportProposalIX(requestBody,
            httpServletRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID, insertedTransOrderId);
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    //ATH-013: POST
    @Operation(summary = APIConstant.Transaction.SHIPPER_APPROVAL_IX_SUMMARY, description = APIConstant.Transaction.SHIPPER_APPROVAL_IX_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.SHIPPER_APPROVAL_IX_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.Transaction.SHIPPER_APPROVAL_IX)
    public NextResponseEntity<Object> approvalShipperProposalIX(
        @PathVariable(ParamConstant.Transaction.ID) String id,
        @RequestBody TransactionShipperApprovalRequest requestBody,
        HttpServletRequest httpServletRequest) {
        requestBody.setPathId(id);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID,
            transOrderService.approveShipperProposalIX(requestBody, httpServletRequest));
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    //ATH-014
    @Operation(summary = APIConstant.Transaction.SHIPPER_UPDATE_IX_SUMMARY, description = APIConstant.Transaction.SHIPPER_UPDATE_IX_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.SHIPPER_UPDATE_IX_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(value = APIConstant.Transaction.SHIPPER_UPDATE_IX)
    public NextResponseEntity<Object> updateShipperProposalIX(
        @PathVariable(ParamConstant.Transaction.ID) String paramId,
        @RequestBody TransactionShipperUpdateRequest requestBody,
        HttpServletRequest httpServletRequest) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID,
            transOrderService.updateShipperProposalIX(requestBody, paramId, httpServletRequest));
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    @GetMapping(value = APIConstant.Transaction.TRANSACTION_TIME)
    public NextResponseEntity<Object> getTransactionTime(@PathVariable(ParamConstant.Transaction.TARGET) String target,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transactionTimeService.getTransactionTime(target), httpServletRequest));
    }

    @GetMapping(value = APIConstant.Transaction.TRANSACTION_ID)
    public NextResponseEntity<Object> getTransactionIds(@PathVariable(ParamConstant.Transaction.TARGET) String target,
        TransactionIdRequest request, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transactionTimeService.getUpdatedIds(target, request.getLast_update()),
                httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.TRANSACTION_CONTRACT)
    public NextResponseEntity<Object> transactionContract(@RequestBody TransactionContractRequest request,
        @PathVariable(ParamConstant.Transaction.ID) Long id, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.updateOrderContractStatus(id, request.isApproval(), httpServletRequest),
            httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.TRANSACTION_PAYMENT)
    public NextResponseEntity<Object> transactionPayment(@PathVariable(ParamConstant.Transaction.ID) Long id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.updateOrderStatus(id, DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT,
                httpServletRequest), httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.TRANSACTION_APPROVAL)
    public NextResponseEntity<Object> transactionApproval(@RequestBody TransactionContractRequest request,
        @PathVariable(ParamConstant.Transaction.ID) Long id, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.updateOrderApprovalStatus(id, request.isApproval(), httpServletRequest),
            httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.TRANSACTION_CANCEL)
    public NextResponseEntity<Object> transactionCancel(@PathVariable(ParamConstant.Transaction.ID) Long id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.updateOrderStatus(id, DataBaseConstant.TransOrderStatus.CANCEL, httpServletRequest),
            httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.TRANSACTION_SHIPPER_CONTRACT)
    public NextResponseEntity<Object> transactionShipperContract(@RequestBody TransactionContractRequest request,
        @PathVariable(ParamConstant.Transaction.ID) String id, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.updateShipperContractStatus(id, request.isApproval(), httpServletRequest),
            httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.TRANSACTION_SHIPPER_PAYMENT)
    public NextResponseEntity<Object> transactionShipperPayment(@PathVariable(ParamConstant.Transaction.ID) String id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.updateShipperOrderStatus(id, DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT,
                httpServletRequest), httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.TRANSACTION_SHIPPER_CANCEL)
    public NextResponseEntity<Object> transactionShipperCancel(@PathVariable(ParamConstant.Transaction.ID) String id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.updateShipperOrderStatus(id, DataBaseConstant.TransOrderStatus.SHIPPER_CANCEL,
                httpServletRequest), httpServletRequest));
    }

    //ATH-021
    @Operation(summary = APIConstant.Transaction.CARRIER_REQUEST_POST_SUMMARY, description = APIConstant.Transaction.CARRIER_REQUEST_POST_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.Transaction.CARRIER_REQUEST_POST_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @PostMapping(value = APIConstant.Transaction.CARRIER_REQUEST_POST)
    public NextResponseEntity<Object> insertTransOrderCarrier(@RequestBody TransMatchingRequest transMatchingRequest,
        HttpServletRequest httpServletRequest) {
        Long resultId = transOrderService.insertCarrierTransaction(transMatchingRequest, httpServletRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID, resultId);
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    //ATH-0212
    @PostMapping(value = APIConstant.Transaction.CARRIER_FE_POST)
    public NextResponseEntity<Object> carrierFeSendRequest(@RequestBody TransactionCarrierFERequest requestBody,
        HttpServletRequest httpServletRequest) {
        Long createdOrderId = transOrderService.createCarrierTransportProposalFE(requestBody, httpServletRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID, createdOrderId);
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    @PostMapping(value = APIConstant.Transaction.CARRIER_2_POST)
    public NextResponseEntity<Object> carrier2SendRequest(@RequestBody TransactionCarrier2Request requestBody,
        HttpServletRequest httpServletRequest) {
        Long createdOrderId = transOrderService.createCarrier2TransportProposal(requestBody);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID, createdOrderId);
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER_TRANSPORT_DECISION)
    public NextResponseEntity<Object> updateCarrierTransportDecision(HttpServletRequest httpServletRequest,
        @PathVariable(name = "id") String orderId) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID, transOrderService.updateCarrierTransportDecision(orderId,
            DataBaseConstant.TransOrderStatus.TRANSPORT_DECISION, httpServletRequest));
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER2_TRANSPORT_DECISION)
    public NextResponseEntity<Object> updateCarrier2TransportDecision(HttpServletRequest httpServletRequest,
        @PathVariable(name = "id") String orderId) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID,
            transOrderService.updateCarrierTransportDecision(orderId, 251, httpServletRequest));
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    @PutMapping(value = APIConstant.Transaction.CARRIER_RE_PROPOSE)
    public NextResponseEntity<Object> carrierRePropose(HttpServletRequest httpServletRequest,
        @PathVariable(name = "id") String orderId) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransOrder.ID,
            transOrderService.updateCarrierRePropose(orderId, httpServletRequest));
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }

    @PostMapping(value = APIConstant.Transaction.UPDATE_ORDER_EMERGENCY)
    public NextResponseEntity<Object> updateOrderEmergency(HttpServletRequest httpServletRequest,
        @RequestBody OrderEmergencyUpdateRequest requestBody) {
        transOrderService.updateOrderEmergency(requestBody);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }
}