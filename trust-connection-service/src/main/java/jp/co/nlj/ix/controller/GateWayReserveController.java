package jp.co.nlj.ix.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.ReserveRequest;
import jp.co.nlj.ix.constant.APIConstant.ShipperTransportCapacity;
import jp.co.nlj.ix.dto.reserve.request.ReserveRequestDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveProposeNotifyDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveProposeResponseDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveResponseDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserverProposeReplyDTO;
import jp.co.nlj.ix.service.GateWayReserveService;
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
 * GateWayReserveControllerクラスは、運行実施（確認）通知コントローラです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping(ReserveRequest.PREFIX)
@RequiredArgsConstructor
public class GateWayReserveController extends BaseController {

    private final GateWayReserveService gateWayReserveService;
    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping()
    public NextResponseEntity<ReserveResponseDTO> insertReserve(HttpServletRequest httpServletRequest,
        @RequestBody ReserveRequestDTO requestDTO,
        @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "carrier_cid") String carrierCid,
        @RequestParam(value = "cid") String cid) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(builderComponent.buildResponse(
            gateWayReserveService.insertReserve(requestDTO, paramUrl), httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(ReserveRequest.UPDATE)
    public NextResponseEntity<ReserveProposeResponseDTO> updateReservePropose(
        @PathVariable("operation_id") String operationId,
        @PathVariable("propose_id") String proposeId,
        HttpServletRequest httpServletRequest,
        @RequestBody ReserveRequestDTO requestDTO,
        @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "carrier_cid") String carrierCid) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(builderComponent.buildResponse(
            gateWayReserveService.updateReservePropose(operationId, proposeId, requestDTO, paramUrl),
            httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(ReserveRequest.NOTIFY)
    public NextResponseEntity<ReserveProposeNotifyDTO> insertReserveProposeNotify(
        @PathVariable("operation_id") String operationId,
        @PathVariable("propose_id") String proposeId,
        HttpServletRequest httpServletRequest,
        @RequestBody Object requestDTO,
        @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "carrier_cid") String carrierCid) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        ReserveRequestDTO requestDTOT = new ReserveRequestDTO();
        return new NextResponseEntity<>(builderComponent.buildResponse(
            gateWayReserveService.insertReserveProposeNotify(operationId, proposeId, requestDTOT, paramUrl),
            httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(ReserveRequest.REPLY)
    public NextResponseEntity<ReserverProposeReplyDTO> insertReserveProposeReply(
        @PathVariable("operation_id") String operationId,
        @PathVariable("propose_id") String proposeId,
        HttpServletRequest httpServletRequest,
        @RequestBody ReserveRequestDTO requestDTO,
        @RequestParam(value = "shipper_cid") String shipperCid,
        @RequestParam(value = "carrier_cid") String carrierCid) {
        String queryString = httpServletRequest.getQueryString();
        String paramUrl = "?" + queryString;
        return new NextResponseEntity<>(builderComponent.buildResponse(
            gateWayReserveService.insertReserveProposeReply(operationId, proposeId, requestDTO, paramUrl),
            httpServletRequest));
    }

}
