package nlj.business.carrier.link.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.APIConstant;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;
import nlj.business.carrier.link.dto.matching.TransMatchingDTO;
import nlj.business.carrier.link.dto.matching.request.CnsLineItemByDateRequest;
import nlj.business.carrier.link.dto.matching.request.MatchingIdRequest;
import nlj.business.carrier.link.dto.matching.request.ShipperOrderIdSaleRequest;
import nlj.business.carrier.link.dto.matching.request.VehicleAvailabilityResourceRequest;
import nlj.business.carrier.link.dto.matching.request.VehicleAvbResourceItemRequest;
import nlj.business.carrier.link.service.TransMatchingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <PRE>
 * 運送計画明細項目コントローラー<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Controller
@RequiredArgsConstructor
public class TransMatchingController {

    private final TransMatchingService transMatchingService;
    private final ResponseBuilderComponent builderComponent;

    @PostMapping(APIConstant.TransMatching.TRSP_MATCHING_PLAN_ITEM)
    public NextResponseEntity<List<TransMatchingDTO>> matchingWithCarrier(
        @Valid @RequestBody CnsLineItemByDateRequest cnsLineItemByDateRequest, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transMatchingService.matchingWithCarrier(cnsLineItemByDateRequest),
                httpServletRequest));
    }

    @PostMapping(APIConstant.TransMatching.TRSP_MATCHING_PLAN)
    public NextResponseEntity<Object> matchingWithCarrierByTransportPlan(
        @RequestBody CnsLineItemByDateRequest cnsLineItemByDateRequest, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.matchingWithCarrierByTransportPlan(cnsLineItemByDateRequest), httpServletRequest));
    }

    @PostMapping(APIConstant.TransMatching.TRSP_MATCHING_ABILITY_ITEM)
    public NextResponseEntity<List<TransMatchingDTO>> matchingWithShipper(
        @Valid @RequestBody VehicleAvbResourceItemRequest vehicleAvbResourceItemRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.matchingWithShipper(Long.valueOf(vehicleAvbResourceItemRequest.getId())),
            httpServletRequest));
    }


    @PostMapping(APIConstant.TransMatching.TRSP_MATCHING_ABILITY)
    public NextResponseEntity<List<TransMatchingDTO>> matchingWithShipperByVehicleDiagramId(
        @Valid @RequestBody VehicleAvailabilityResourceRequest vehicleAvailabilityResourceRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.matchingWithShipperByVehicleDiagramId(
                Long.valueOf(vehicleAvailabilityResourceRequest.getId())), httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.CARRIER_ORDER_ID_SALE)
    public NextResponseEntity<Object> carrierOrderIdSale(
        @Valid @RequestBody CnsLineItemByDateRequest cnsLineItemByDateRequest, HttpServletRequest httpServletRequest) {
        transMatchingService.insertCarrierOrderIdSale(cnsLineItemByDateRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.CARRIER_ORDER_ID_CANCEL)
    public NextResponseEntity<Object> carrierOrderIdCancel(
        @Valid @RequestBody CnsLineItemByDateRequest cnsLineItemByDateRequest, HttpServletRequest httpServletRequest) {
        transMatchingService.carrierOrderIdCancel(cnsLineItemByDateRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.CARRIER_ORDER_ID_MATCHING)
    public NextResponseEntity<List<TransMatchingDTO>> carrierOrderIdMatching(@RequestBody MatchingIdRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transMatchingService.carrierMatchingWithCarrier(request),
                httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.REQ_CNS_LINE_ITEM_MATCHING)
    public NextResponseEntity<List<TransMatchingDTO>> reqCnsLineItemMatching(
        @Valid @RequestBody MatchingIdRequest request, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transMatchingService.reqCnsLineItemMatching(Long.valueOf(request.getId())),
                httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.VEHICLE_AVB_RESOURCE_MATCHING)
    public NextResponseEntity<List<TransMatchingDTO>> vehicleAvbResourceMatching(
        @Valid @RequestBody MatchingIdRequest request, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.vehicleAvbResourceMatching(Long.valueOf(request.getId())), httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.CNS_LINE_ITEM_MATCHING)
    public NextResponseEntity<List<TransMatchingDTO>> cnsLineItemMatching(@Valid @RequestBody MatchingIdRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transMatchingService.cnsLineItemMatching(Long.valueOf(request.getId())),
                httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.SHIPPER_ORDER_ID_SALE)
    public NextResponseEntity<List<VehicleAvbResourceItem>> shipperOrderIdSale(
        @Valid @RequestBody ShipperOrderIdSaleRequest shipperOrderIdSaleRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transMatchingService.insertShipperOrderIdSale(shipperOrderIdSaleRequest),
                httpServletRequest));
    }

}
