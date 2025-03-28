package jp.co.nlj.ix.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.ShipperTransportCapacity;
import jp.co.nlj.ix.constant.APIConstant.TrspPlansRequest;
import jp.co.nlj.ix.dto.transportPlans.request.TrspPlansNotifyRequestDTO;
import jp.co.nlj.ix.dto.transportPlans.request.TrspPlansRequestDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlanUpdateResponseDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlansCreateResponseDTO;
import jp.co.nlj.ix.dto.transportPlans.response.TrspPlansNotifyResponseDTO;
import jp.co.nlj.ix.service.GateWayTrspPlansService;
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
 * GateWayTrspPlansControllerクラスは、運行実施（確認）通知コントローラです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping(TrspPlansRequest.PREFIX)
@RequiredArgsConstructor
public class GateWayTrspPlansController extends BaseController {

    private final GateWayTrspPlansService gateWayTrspPlansService;
    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(TrspPlansRequest.TRSP_PLANS)
    public NextResponseEntity<TrspPlanUpdateResponseDTO> updateTrspPlans(
        @PathVariable("trsp_instruction_id") String trspInstructionId,
        HttpServletRequest httpServletRequest,
        @RequestBody TrspPlansRequestDTO requestDTO,
        @RequestParam(value = "is_shipper") String isShipper,
        @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "recipient_cid") String recipientCid,
        @RequestParam(value = "carrier_cid") String carrierCid,
        @RequestParam(value = "tractor_giai") String tractorGiai,
        @RequestParam(value = "trailer_giai_list") String trailerGiaiList,
        @RequestParam(value = "req_from_time") String reqFromTime,
        @RequestParam(value = "req_to_time") String reqToTime) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(builderComponent.buildResponse(
            gateWayTrspPlansService.updateTrspPlans(trspInstructionId, requestDTO, paramUrl),
            httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(TrspPlansRequest.TRSP_PLANS)
    public NextResponseEntity<TrspPlansCreateResponseDTO> insertTrspPlans(
        @PathVariable("trsp_instruction_id") String trspInstructionId,
        HttpServletRequest httpServletRequest,
        @RequestBody TrspPlansRequestDTO requestDTO,
        @RequestParam(value = "is_shipper") String isShipper,
        @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "recipient_cid") String recipientCid,
        @RequestParam(value = "carrier_cid") String carrierCid,
        @RequestParam(value = "tractor_giai") String tractorGiai,
        @RequestParam(value = "trailer_giai_list") String trailerGiaiList,
        @RequestParam(value = "req_from_time") String reqFromTime,
        @RequestParam(value = "req_to_time") String reqToTime) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(builderComponent.buildResponse(
            gateWayTrspPlansService.insertTrspPlans(trspInstructionId, requestDTO, paramUrl),
            httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(TrspPlansRequest.NOTIFY)
    public NextResponseEntity<TrspPlansNotifyResponseDTO> insertTrspPlansNotify(
        @PathVariable("trsp_instruction_id") String trspInstructionId,
        HttpServletRequest httpServletRequest,
        @RequestBody TrspPlansNotifyRequestDTO requestDTO,
        @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "carrier_cid") String carrierCid) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(builderComponent.buildResponse(
            gateWayTrspPlansService.insertTrspPlansNotify(trspInstructionId, requestDTO, paramUrl),
            httpServletRequest));
    }
}
