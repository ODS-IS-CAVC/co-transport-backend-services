package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.domain.CutOffInfo;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.dto.matching.request.MatchingIdRequest;
import nlj.business.transaction.dto.request.CarrierOperationApprovalRequestDTO;
import nlj.business.transaction.dto.request.CarrierOperatorPlansRequest;
import nlj.business.transaction.dto.request.CarrierTransactionShipperApproval;
import nlj.business.transaction.dto.request.CarrierTransactionShipperRequest;
import nlj.business.transaction.dto.request.CarrierVehicleDiagramGetRequest;
import nlj.business.transaction.dto.response.CarrierOperatorPlansInsertResponse;
import nlj.business.transaction.dto.response.CarrierVehicleDiagramGetResponse;
import nlj.business.transaction.service.BatchService;
import nlj.business.transaction.service.TransMatchingService;
import nlj.business.transaction.service.TransOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 転送マッチングコントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequiredArgsConstructor
public class TransMatchingController {

    private final TransMatchingService transMatchingService;
    private final ResponseBuilderComponent builderComponent;
    private final TransOrderService transOrderService;
    private final BatchService batchService;

    @PostMapping(value = APIConstant.TransMatching.REQ_CNS_LINE_ITEM_MATCHING)
    public NextResponseEntity<List<TransMatching>> reqCnsLineItemMatching(@RequestBody MatchingIdRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.reqCnsLineItemMatching(
                request, httpServletRequest), httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.VEHICLE_AVB_RESOURCE_MATCHING)
    public NextResponseEntity<List<TransMatching>> vehicleAvbResourceMatching(@RequestBody MatchingIdRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.vehicleAvbResourceMatching(
                request, httpServletRequest), httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.CNS_LINE_ITEM_MATCHING)
    public NextResponseEntity<List<TransMatching>> cnsLineItemMatching(@RequestBody MatchingIdRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.cnsLineItemMatching(
                request, httpServletRequest), httpServletRequest));
    }

    //ATH-3061
    @PostMapping(value = APIConstant.TransMatching.CARRIER_TRANSACTION_SHIPPER)
    NextResponseEntity<CarrierOperatorPlansInsertResponse> carrierTransactionShipper(
        @RequestBody CarrierTransactionShipperRequest request,
        HttpServletRequest httpServletRequest) {
        CarrierOperatorPlansRequest carrierOperatorPlansRequest = new CarrierOperatorPlansRequest();
        carrierOperatorPlansRequest.setTrspPlanId(request.getCnsLineItemByDateId());
        carrierOperatorPlansRequest.setOperationId(request.getVehicleAvbResourceItemId());
        carrierOperatorPlansRequest.setServiceNo(request.getServiceNo());
        carrierOperatorPlansRequest.setTrspOpDateTrmStrtDate(request.getDepartureDate());
        carrierOperatorPlansRequest.setTrspOpPlanDateTrmStrtTime(request.getDepartureTime());
        carrierOperatorPlansRequest.setTrspOpDateTrmEndDate(request.getArrivalDate());
        carrierOperatorPlansRequest.setTrspOpPlanDateTrmEndTime(request.getArrivalTime());
        carrierOperatorPlansRequest.setGiaiNumber(request.getGiai());
        carrierOperatorPlansRequest.setReqFreightRate(request.getPrice());
        carrierOperatorPlansRequest.setMatchingId(request.getMatchingId());
        carrierOperatorPlansRequest.setNegotiation(request.getNegotiation());
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.carrierTransactionShipper(request.isNotIX(), carrierOperatorPlansRequest,
                httpServletRequest), httpServletRequest));
    }

    //ATH-3062
    @PostMapping(value = APIConstant.TransMatching.CARRIER_TRANSACTION_SHIPPER_ID_APPROVAL)
    NextResponseEntity<CarrierOperatorPlansInsertResponse> carrierTransactionShipperIdApproval(
        @PathVariable("id") String id,
        @RequestBody CarrierTransactionShipperApproval request,
        HttpServletRequest httpServletRequest) {
        CarrierOperationApprovalRequestDTO carrierOperatorPlansRequest = new CarrierOperationApprovalRequestDTO();
        carrierOperatorPlansRequest.setTrspPlanId(request.getCnsLineItemId());
        carrierOperatorPlansRequest.setOperationId(request.getVehicleAvbResourceId());
        carrierOperatorPlansRequest.setApproval(request.isApproval());
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.carrierTransactionShipperIdApproval(request.isNotIX(), id, carrierOperatorPlansRequest,
                httpServletRequest), httpServletRequest));
    }

    //ATH-3063
    @PutMapping(value = APIConstant.TransMatching.CARRIER_TRANSACTION_SHIPPER_ID)
    NextResponseEntity<CarrierOperatorPlansInsertResponse> carrierTransactionShipperId(
        @PathVariable("id") String id,
        @RequestBody CarrierTransactionShipperRequest request,
        HttpServletRequest httpServletRequest) {
        CarrierOperatorPlansRequest carrierOperatorPlansRequest = new CarrierOperatorPlansRequest();
        carrierOperatorPlansRequest.setTrspPlanId(request.getCnsLineItemId());
        carrierOperatorPlansRequest.setOperationId(request.getVehicleAvbResourceItemId());
        carrierOperatorPlansRequest.setServiceNo(request.getServiceNo());
        carrierOperatorPlansRequest.setTrspOpDateTrmStrtDate(request.getDepartureDate());
        carrierOperatorPlansRequest.setTrspOpPlanDateTrmStrtTime(request.getDepartureTime());
        carrierOperatorPlansRequest.setTrspOpDateTrmEndDate(request.getArrivalDate());
        carrierOperatorPlansRequest.setTrspOpPlanDateTrmEndTime(request.getArrivalTime());
        carrierOperatorPlansRequest.setGiaiNumber(request.getGiai());
        carrierOperatorPlansRequest.setReqFreightRate(request.getPrice());
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.carrierTransactionShipperId(request.isNotIX(), id, carrierOperatorPlansRequest,
                httpServletRequest), httpServletRequest));
    }

    @GetMapping(value = APIConstant.Market.BATCH_MARKET)
    NextResponseEntity<Object> carrierTransactionShipper(HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(batchService.calculateMarketPriceStatistics(), httpServletRequest));
    }

    @PostMapping(value = APIConstant.TransMatching.CARRIER_GET_TRAILER_ID_MATCHING)
    public NextResponseEntity<CarrierVehicleDiagramGetResponse> getDiagramTrailerMatching(
        @RequestBody CarrierVehicleDiagramGetRequest request, HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transMatchingService.getTransportMatchingByTrailer(request), httpServletRequest));
    }

    @GetMapping(value = APIConstant.TransMatching.GET_CUT_OFF_INFO)
    NextResponseEntity<List<CutOffInfo>> getCufOffInfo(
        @PathVariable("id") Long vehicleAvbResoureId,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transMatchingService.getCufOffInfo(vehicleAvbResoureId),
                httpServletRequest));
    }
}
