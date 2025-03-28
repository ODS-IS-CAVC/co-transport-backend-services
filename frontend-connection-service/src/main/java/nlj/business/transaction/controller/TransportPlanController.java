package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.trans.MarketTransportPlanItem;
import nlj.business.transaction.dto.PageResponseDTO;
import nlj.business.transaction.dto.TransportPlanItemDTO;
import nlj.business.transaction.dto.matching.TransMatchingDTOResponse;
import nlj.business.transaction.dto.matching.TrspPlanIdDTOResponse;
import nlj.business.transaction.dto.request.CommonPagingSearch;
import nlj.business.transaction.dto.request.TransactionOnSaleRequest;
import nlj.business.transaction.dto.request.TransportPlanMatchingRequest;
import nlj.business.transaction.dto.request.TransportPlanPublicSearch;
import nlj.business.transaction.service.CnsLineItemByDateService;
import nlj.business.transaction.service.MarketTransportPlanItemService;
import nlj.business.transaction.service.TransMatchingService;
import nlj.business.transaction.service.TransportPlanItemService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 輸送計画コントローラー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.TransportPlan.PREFIX)
@RequiredArgsConstructor
public class TransportPlanController {

    private final TransportPlanItemService transportPlanItemService;
    private final ResponseBuilderComponent builderComponent;
    private final TransMatchingService transMatchingService;
    private final MarketTransportPlanItemService marketTransportPlanItemService;
    private final CnsLineItemByDateService cnsLineItemByDateService;

    @Operation(summary = APIConstant.TransportPlan.LIST_PUBLIC_SUMMARY, description = APIConstant.TransportPlan.LIST_PUBLIC_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.LIST_PUBLIC_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(APIConstant.TransportPlan.LIST_PUBLIC)
    public NextResponseEntity<Object> searchTranportPlan(TransportPlanPublicSearch searchRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            new PageResponseDTO<>(cnsLineItemByDateService.getPagedTransportPlan(searchRequest)), httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportPlan.LIST_MATCHING_SUMMARY, description = APIConstant.TransportPlan.LIST_MATCHING_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.LIST_MATCHING_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.TransportPlan.LIST_MATCHING)
    public NextResponseEntity<PageResponseDTO<TransMatchingDTOResponse>> getAllTransportData(
        @RequestParam(name = "freeWord", required = false) String freeWord,
        @RequestParam(name = "temperatureRange", required = false) List<Integer> temperatureRange,
        @RequestParam(name = ParamConstant.TransportPlan.PAGE, defaultValue = "1") int offset,
        @RequestParam(name = ParamConstant.TransportPlan.LIMIT, defaultValue = "5") int limit,
        HttpServletRequest httpServletRequest) {
        Page<TransMatchingDTOResponse> list = transMatchingService.getTransPlanMatching(freeWord, temperatureRange,
            offset - 1, limit);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(new PageResponseDTO<>(list), httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportPlan.LIST_MATCHING_ID_SUMMARY, description = APIConstant.TransportPlan.LIST_MATCHING_ID_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.LIST_MATCHING_ID_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.TransportPlan.LIST_MATCHING_ID)
    public NextResponseEntity<List<TrspPlanIdDTOResponse>> getAllTransportDataById(
        @PathVariable(ParamConstant.TransportAbility.ID) Long cnsLineItemByDateId,
        HttpServletRequest httpServletRequest) {
        List<TrspPlanIdDTOResponse> list = transMatchingService.getTransPlanMatchingById(cnsLineItemByDateId);
        return new NextResponseEntity<>(builderComponent.buildResponse(list, httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportPlan.ON_SALE_SUMMARY, description = APIConstant.TransportPlan.ON_SALE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.ON_SALE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.TransportPlan.ON_SALE)
    public NextResponseEntity<Object> getTransportPlanOnSale(HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(marketTransportPlanItemService.findOnSale(), httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportPlan.ON_SALE_SUMMARY, description = APIConstant.TransportPlan.ON_SALE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.ON_SALE_UPDATE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportPlan.ON_SALE)
    public NextResponseEntity<Object> updateOnSale(@RequestBody TransactionOnSaleRequest request,
        HttpServletRequest httpServletRequest) {
        MarketTransportPlanItem marketTransportPlanItem = transportPlanItemService.updateStatusOnSale(request.getId());
        return new NextResponseEntity<>(builderComponent.buildResponse(marketTransportPlanItem, httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_SUMMARY, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN)
    public NextResponseEntity<List<TransMatching>> matchTransportPlan(
        @RequestBody TransportPlanMatchingRequest requestBody, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.matchByTransportPlanId(Long.parseLong(requestBody.getId()), httpServletRequest),
            httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_ITEM_SUMMARY, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_ITEM_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_ITEM_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_ITEM)
    public NextResponseEntity<List<TransMatching>> matchTransportPlanItem(
        @RequestBody TransportPlanMatchingRequest requestBody, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.matchByCnsLineItemId(Long.parseLong(requestBody.getId()), httpServletRequest),
            httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportPlan.TRANSPORT_PLAN_PROPOSAL_SUMMARY, description = APIConstant.TransportPlan.TRANSPORT_PLAN_PROPOSAL_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.TRANSPORT_PLAN_PROPOSAL_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.TransportPlan.TRANSPORT_PLAN_PROPOSAL)
    public NextResponseEntity<TransportPlanItemDTO> getTransportPlanProposal(HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transportPlanItemService.getTransportPlanProposal(), httpServletRequest));
    }


    @GetMapping(APIConstant.TransportPlan.SALE)
    public NextResponseEntity<Object> searchTranportPlanSale(
        @RequestParam(name = "temperatureRange", required = false) List<Integer> temperatureRange,
        CommonPagingSearch searchRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            new PageResponseDTO<>(cnsLineItemByDateService.getPagedTransportPlanSale(temperatureRange, searchRequest)),
            httpServletRequest));
    }
}
