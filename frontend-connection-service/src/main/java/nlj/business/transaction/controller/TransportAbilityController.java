package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.trans.MarketVehicleDiagramItemTrailer;
import nlj.business.transaction.dto.CnsLineItemByDateDTO;
import nlj.business.transaction.dto.PageResponseDTO;
import nlj.business.transaction.dto.VehicleAvbResourceItemDTO;
import nlj.business.transaction.dto.matching.TransMatchingAbilityDetailResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilitySaleHeadResponse;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.request.CarrierListOrderSearch;
import nlj.business.transaction.dto.request.TransactionOnSaleRequest;
import nlj.business.transaction.dto.request.TransportAbilityProposalRequest;
import nlj.business.transaction.dto.request.TransportAbilityPublicSearch;
import nlj.business.transaction.dto.request.VehicleDiagramItemMatchingRequest;
import nlj.business.transaction.dto.request.VehicleDiagramMatchingRequest;
import nlj.business.transaction.service.CnsLineItemByDateService;
import nlj.business.transaction.service.MarketVehicleDiagramItemTrailerService;
import nlj.business.transaction.service.TransMatchingService;
import nlj.business.transaction.service.VehicleAvbResourceItemService;
import nlj.business.transaction.service.VehicleDiagramItemService;
import nlj.business.transaction.service.VehicleDiagramItemTrailerService;
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
 * 転送能力コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.TransportAbility.PREFIX)
@RequiredArgsConstructor
public class TransportAbilityController {

    private final TransMatchingService transMatchingService;
    private final VehicleDiagramItemService vehicleDiagramItemService;
    private final MarketVehicleDiagramItemTrailerService marketVehicleDiagramItemTrailerService;
    private final VehicleDiagramItemTrailerService vehicleDiagramItemTrailerService;
    private final VehicleAvbResourceItemService vehicleAvbResourceItemService;
    private final CnsLineItemByDateService cnsLineItemByDateService;

    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = APIConstant.TransportAbility.LIST_MATCHING_SUMMARY, description = APIConstant.TransportAbility.LIST_MATCHING_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.LIST_MATCHING_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.TransportAbility.LIST_MATCHING)
    public NextResponseEntity<PageResponseDTO<TransMatchingHeadResponse>> getAllTransportData(
        @RequestParam(name = "freeWord", required = false) String freeWord,
        @RequestParam(name = "temperatureRange", required = false) List<Integer> temperatureRange,
        @RequestParam(name = "transType", required = false) String transType,
        @RequestParam(name = ParamConstant.TransportAbility.PAGE, defaultValue = "1") int page,
        @RequestParam(name = ParamConstant.TransportAbility.LIMIT, defaultValue = "5") int limit,
        @RequestParam(name = "isEmergency", required = false) Integer isEmergency,
        HttpServletRequest httpServletRequest) {
        Page<TransMatchingHeadResponse> list = transMatchingService.getMatchingByTrailer(transType, freeWord,
            temperatureRange, page, limit, isEmergency);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(new PageResponseDTO<>(list), httpServletRequest));
    }

    //ATH-013
    @Operation(summary = APIConstant.TransportAbility.LIST_MATCHING_ID_SUMMARY, description = APIConstant.TransportAbility.LIST_MATCHING_ID_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.LIST_MATCHING_ID_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.TransportAbility.LIST_MATCHING_ID)
    public NextResponseEntity<TransMatchingAbilityDetailResponse> getAllTransportDataById(
        @PathVariable(ParamConstant.TransportAbility.ID) Long vehicleAvbResourceId,
        @RequestParam(value = "trans_type", required = false) String transType,
        HttpServletRequest httpServletRequest) {
        TransMatchingAbilityDetailResponse list = transMatchingService.getTransMatchingByTrailerId(vehicleAvbResourceId,
            transType);
        return new NextResponseEntity<>(builderComponent.buildResponse(list, httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.LIST_PUBLIC_SUMMARY, description = APIConstant.TransportAbility.LIST_PUBLIC_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.LIST_PUBLIC_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(APIConstant.TransportAbility.LIST_PUBLIC)
    public NextResponseEntity<Object> searchTransportAbility(TransportAbilityPublicSearch searchRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            new PageResponseDTO<>(vehicleAvbResourceItemService.getPagedTransportAbility(searchRequest)),
            httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.ON_SALE_SUMMARY, description = APIConstant.TransportAbility.ON_SALE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.ON_SALE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.TransportPlan.ON_SALE)
    public NextResponseEntity<Object> getTransportAbilityOnSale(HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(marketVehicleDiagramItemTrailerService.findOnSale(), httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.ON_SALE_SUMMARY, description = APIConstant.TransportAbility.ON_SALE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.ON_SALE_UPDATE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportPlan.ON_SALE)
    public NextResponseEntity<Object> updateOnSale(@RequestBody TransactionOnSaleRequest request,
        HttpServletRequest httpServletRequest) {
        MarketVehicleDiagramItemTrailer marketVehicleDiagramItemTrailer = vehicleDiagramItemService.updateStatusOnSale(
            request.getId());
        return new NextResponseEntity<>(
            builderComponent.buildResponse(marketVehicleDiagramItemTrailer, httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.MATCHED_DETAIL_SUMMARY, description = APIConstant.TransportAbility.MATCHED_DETAIL_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.MATCHED_DETAIL_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportAbility.MATCHING_DETAILS)
    public NextResponseEntity<List<TransMatching>> postTransportAbilityMatching(
        @RequestBody VehicleDiagramMatchingRequest vehicleDiagramMatchingRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.executeMatching(Long.valueOf(vehicleDiagramMatchingRequest.getId()),
                httpServletRequest), httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.MATCHED_DETAIL_ITEM_SUMMARY, description = APIConstant.TransportAbility.MATCHED_DETAIL_ITEM_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.MATCHED_DETAIL_ITEM_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportAbility.MATCHING_DETAIL_ITEM)
    public NextResponseEntity<List<TransMatching>> postMatchingForItem(
        @RequestBody VehicleDiagramItemMatchingRequest vehicleDiagramItemMatchingRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.matchByVehicleAvbResourceItemId(
                Long.parseLong(vehicleDiagramItemMatchingRequest.getId()), httpServletRequest), httpServletRequest));
    }

//    @Operation(summary = APIConstant.TransportAbility.TRANSPORT_ABILITY_PROPOSAL_SUMMARY, description = APIConstant.TransportAbility.TRANSPORT_ABILITY_PROPOSAL_STATUS_OK_DESCRIPTION)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.TRANSPORT_ABILITY_PROPOSAL_STATUS_OK_DESCRIPTION),
//            @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
//            @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
//    })
//    @GetMapping(value = APIConstant.TransportAbility.TRANSPORT_ABILITY_PROPOSAL)
//    public NextResponseEntity<VehicleDiagramItemTrailerDTO> getTransportAbilityProposal(HttpServletRequest httpServletRequest) {
//        return new NextResponseEntity<>(builderComponent.buildResponse(vehicleDiagramItemTrailerService.getTransportAbilityProposal(), httpServletRequest));
//    }

    @Operation(summary = APIConstant.TransportAbility.CARRIER_ORDER_ID_SALE_SUMMARY, description = APIConstant.TransportAbility.CARRIER_ORDER_ID_SALE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.CARRIER_ORDER_ID_SALE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportAbility.CARRIER_ORDER_ID_SALE)
    public NextResponseEntity<Object> carrierOrderIdSale(
        @PathVariable(ParamConstant.TransportAbility.ORDER_ID) Long orderId,
        HttpServletRequest httpServletRequest) {
        transMatchingService.insertCarrierOrderIdSale(orderId, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransportAbility.CARRIER_ORDER_ID_CANCEL)
    public NextResponseEntity<Object> carrierOrderIdCancel(
        @PathVariable(ParamConstant.TransportAbility.ORDER_ID) Long orderId,
        HttpServletRequest httpServletRequest) {
        transMatchingService.carrierOrderIdCancel(orderId, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.CARRIER_ORDER_ID_MATCHING_SUMMARY, description = APIConstant.TransportAbility.CARRIER_ORDER_ID_MATCHING_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.CARRIER_ORDER_ID_MATCHING_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportAbility.CARRIER_ORDER_ID_MATCHING)
    public NextResponseEntity<Object> carrierOrderIdMatching(
        @PathVariable(ParamConstant.TransportAbility.ORDER_ID) Long orderId, HttpServletRequest httpServletRequest) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(DataBaseConstant.TransMatching.ID,
            transMatchingService.insertCarrierOrderIdMatching(orderId, httpServletRequest));
        return new NextResponseEntity<>(builderComponent.buildResponse(
            responseData, httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.CARRIER_ORDER_ID_LIST_ON_SALE_SUMMARY, description = APIConstant.TransportAbility.CARRIER_ORDER_ID_LIST_ON_SALE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.CARRIER_ORDER_ID_LIST_ON_SALE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.TransportAbility.CARRIER_ORDER_ID_LIST_ON_SALE)
    public NextResponseEntity<PageResponseDTO<CnsLineItemByDateDTO>> getListCarrierOrderOnSale(
        @PathVariable(ParamConstant.TransportAbility.ORDER_ID) Long orderId,
        CarrierListOrderSearch searchRequest, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            new PageResponseDTO<>(cnsLineItemByDateService.getPagedListCarrierOrderOnSale(searchRequest))
            , httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.LIST_PUBLIC_IX_SUMMARY, description = APIConstant.TransportAbility.LIST_PUBLIC_IX_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.LIST_PUBLIC_IX_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(APIConstant.TransportAbility.LIST_PUBLIC_IX)
    public NextResponseEntity<Object> searchTransportAbilityIX(TransportAbilityPublicSearch searchRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(new PageResponseDTO<>(
                vehicleAvbResourceItemService.getPagedTransportAbilityIX(searchRequest, httpServletRequest)),
            httpServletRequest));
    }

    @Operation(summary = APIConstant.TransportAbility.SHIPPER_ORDER_ID_SALE_SUMMARY, description = APIConstant.TransportAbility.SHIPPER_ORDER_ID_SALE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportAbility.SHIPPER_ORDER_ID_SALE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = APIConstant.TransportAbility.SHIPPER_ORDER_ID_SALE)
    public NextResponseEntity<List<VehicleAvbResourceItemDTO>> insertShipperOrderIdSale(
        @PathVariable(ParamConstant.TransportAbility.ORDER_ID) Long orderId,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.insertShipperOrderIdSale(orderId, httpServletRequest), httpServletRequest));
    }

    @GetMapping(value = APIConstant.TransportAbility.CARRIER_ORDER_ID_MATCHING)
    public NextResponseEntity<PageResponseDTO<TransMatchingHeadResponse>> getMatchingCarrierByTrailer(
        @PathVariable(ParamConstant.TransportAbility.ORDER_ID) Long orderId,
        @RequestParam(name = ParamConstant.TransportAbility.PAGE, defaultValue = "1") int page,
        @RequestParam(name = ParamConstant.TransportAbility.LIMIT, defaultValue = "5") int limit,
        HttpServletRequest httpServletRequest) {
        Page<TransMatchingHeadResponse> list = transMatchingService.getMatchingCarrierByTrailer(orderId, page, limit);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(new PageResponseDTO<>(list), httpServletRequest));
    }

    @GetMapping(value = APIConstant.TransportAbility.SALE)
    public NextResponseEntity<PageResponseDTO<TransMatchingAbilitySaleHeadResponse>> getTransportAbilitySale(
        @RequestParam(name = "temperatureRange", required = false) List<Integer> temperatureRange,
        @RequestParam(name = "transType", required = false) String transType,
        @RequestParam(name = ParamConstant.TransportAbility.PAGE, defaultValue = "1") int page,
        @RequestParam(name = ParamConstant.TransportAbility.LIMIT, defaultValue = "5") int limit,
        HttpServletRequest httpServletRequest) {
        Page<TransMatchingAbilitySaleHeadResponse> list = transMatchingService.getTransportAbilitySale(transType,
            temperatureRange, page, limit);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(new PageResponseDTO<>(list), httpServletRequest));
    }

    @GetMapping(value = APIConstant.TransportAbility.TRANSPORT_ABILITY_PROPOSAL)
    public NextResponseEntity<Object> getTransportAbilityProposal(TransportAbilityProposalRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            new PageResponseDTO<>(vehicleAvbResourceItemService.getProposalItem(request)), httpServletRequest));
    }
}