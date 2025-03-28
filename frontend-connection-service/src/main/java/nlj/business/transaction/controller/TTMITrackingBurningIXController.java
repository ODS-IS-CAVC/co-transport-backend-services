package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.APIConstant.IXBurningTTMITracking;
import nlj.business.transaction.constant.MessageConstant.WeatherInformation;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.dto.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.transaction.dto.request.TimeRangeRequest;
import nlj.business.transaction.dto.request.WeatherRequest;
import nlj.business.transaction.dto.response.VehicleDiagramStatusResponseDTO;
import nlj.business.transaction.dto.response.WeatherResponse;
import nlj.business.transaction.service.IXBurningService;
import nlj.business.transaction.service.TTMITrackingBySipService;
import nlj.business.transaction.service.VehicleDiagramItemService;
import nlj.business.transaction.service.WeatherInformationService;
import nlj.business.transaction.service.ZLWebService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 輸送計画コントローラー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(IXBurningTTMITracking.PREFIX)
@RequiredArgsConstructor
@Slf4j
public class TTMITrackingBurningIXController {

    private final ResponseBuilderComponent builderComponent;
    private final IXBurningService ixBurningService;
    private final TTMITrackingBySipService ttmiTrackingBySipService;
    private final ZLWebService zlWebService;
    private final WeatherInformationService weatherInformationService;
    private final VehicleDiagramItemService vehicleDiagramItemService;

    @Operation(summary = "Register IX Burning and TTMI Tracking", description = "Register IX Burning and TTMI Tracking information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping
    public NextResponseEntity<WeatherResponse> registerIXBurningAndTTMITracking(
        @PathVariable("id") Long transOrderId,
        @Valid @RequestBody WeatherRequest weatherRequest,
        HttpServletRequest httpServletRequest) {
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO = vehicleDiagramItemService.getDepartureArrivalTime(
            transOrderId, httpServletRequest);
        WeatherResponse errorWeather = weatherInformationService.validateWeatherInformation(transOrderId,
            departureArrivalTimeDTO, weatherRequest);
        if ((weatherRequest != null && weatherRequest.isIgnore()) || !errorWeather.getStatus().equals(
            WeatherInformation.STATUS_ERROR)) {
            try {
                ttmiTrackingBySipService.registerTTMITrackingBySip(transOrderId,
                    HttpMethod.POST, httpServletRequest);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            try {
                zlWebService.sendToZLWeb(transOrderId, departureArrivalTimeDTO, httpServletRequest);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            try {
                ixBurningService.registerIXBurning(transOrderId, httpServletRequest);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return new NextResponseEntity<>(builderComponent.buildResponse(errorWeather, httpServletRequest),
            HttpStatus.CREATED);
    }

    @Operation(summary = "Update IX Burning and TTMI Tracking status", description = "Update IX Burning and TTMI Tracking status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(IXBurningTTMITracking.START)
    public NextResponseEntity<String> startIXBurningAndTTMITracking(
        @PathVariable("id") Long diagramItemId,
        HttpServletRequest httpServletRequest) {
        weatherInformationService.updateWeatherInformationStatus(diagramItemId, ParamConstant.Status.START, 0L, null);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest), HttpStatus.OK);
    }

    @Operation(summary = "Update IX Burning and TTMI Tracking status", description = "Update IX Burning and TTMI Tracking status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(IXBurningTTMITracking.END)
    public NextResponseEntity<String> endIXBurningAndTTMITracking(
        @PathVariable("id") Long diagramItemId,
        HttpServletRequest httpServletRequest) {
        weatherInformationService.updateWeatherInformationStatus(diagramItemId, ParamConstant.Status.END, 0L, null);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest), HttpStatus.OK);
    }

    @Operation(summary = "Validate semiDynamicInfo", description = "Validate semiDynamicInfo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(IXBurningTTMITracking.VALIDATE)
    public NextResponseEntity<VehicleDiagramStatusResponseDTO> validateIXBurningAndTTMITracking(
        @PathVariable("id") Long diagramItemId,
        @RequestBody TimeRangeRequest timeRange,
        HttpServletRequest httpServletRequest) {
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO = vehicleDiagramItemService.getDepartureArrivalTimeByDiagramItemId(
            diagramItemId, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse(
            weatherInformationService.validateWeatherInformationByDiagramItemId(diagramItemId, departureArrivalTimeDTO,
                timeRange.getRange()), httpServletRequest), HttpStatus.OK);
    }

}
