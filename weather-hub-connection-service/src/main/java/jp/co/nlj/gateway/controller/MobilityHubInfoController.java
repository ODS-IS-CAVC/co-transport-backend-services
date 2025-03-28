package jp.co.nlj.gateway.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.gateway.constant.APIConstant;
import jp.co.nlj.gateway.constant.APIConstant.MobilityHub;
import jp.co.nlj.gateway.dto.reservation.request.ReservationRequestDTO;
import jp.co.nlj.gateway.dto.reservation.response.ReservationResponseDTO;
import jp.co.nlj.gateway.service.MobilityHubInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * MobilityHubInfoControllerクラスは、モビリティハブの情報を管理するためのコントローラークラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(MobilityHub.PREFIX)
@RequiredArgsConstructor
public class MobilityHubInfoController {

    private final ResponseBuilderComponent builderComponent;
    private final MobilityHubInfoService mobilityHubInfoService;

    @Operation(summary = MobilityHub.RESERVE_MOBILITY_HUB_SUMMARY, description =
        MobilityHub.RESERVE_MOBILITY_HUB_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description =
            APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @PutMapping
    public NextResponseEntity<ReservationResponseDTO> reserveMobilityHub(@RequestBody ReservationRequestDTO request,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(
            builderComponent.buildResponse(mobilityHubInfoService.reserveMobilityHub(httpServletRequest, request),
                httpServletRequest));

    }

    @Operation(summary = MobilityHub.CANCEL_RESERVE_MOBILITY_HUB_SUMMARY, description =
        MobilityHub.CANCEL_RESERVE_MOBILITY_HUB_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description =
            APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @DeleteMapping
    public NextResponseEntity<Void> cancelMobilityHubReservation(
        @RequestParam(MobilityHub.RESERVATION_ID) String reservationId, HttpServletRequest httpServletRequest) {
        mobilityHubInfoService.cancelMobilityHubReservation(httpServletRequest, reservationId);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }
}
