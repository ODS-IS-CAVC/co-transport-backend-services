package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.VehicleDiagramItem;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.dto.vehicleDiagramItem.request.DiagramItemUpdateStatusRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateDiagramItemTimeRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateEmergencyRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdatePrivateDiagramItemRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateSipTrackIdRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateTrackStatusRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.response.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.carrier.service.VehicleDiagramItemService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 車両ダイアグラムアイテムコントローラ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(VehicleDiagramItem.PREFIX)
@RequiredArgsConstructor
public class VehicleDiagramItemController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleDiagramItemService vehicleDiagramItemService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping(value = VehicleDiagramItem.SIP_TRACK)
    public NextResponseEntity<String> updateSipTrack(
        @Valid @RequestBody UpdateSipTrackIdRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramItemService.updateSipTrackId(request),
                httpServletRequest));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PostMapping(VehicleDiagramItem.DEPARTURE_ARRIVAL_TIME)
    public NextResponseEntity<DiagramItemDepartureArrivalTimeDTO> getDepartureArrivalTime(
        @PathVariable("id") Long diagramItemTrailerId,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramItemService.getDepartureArrivalTime(diagramItemTrailerId),
                httpServletRequest));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PostMapping(VehicleDiagramItem.DEPARTURE_ARRIVAL_TIME_BY_DIAGRAM_ITEM_ID)
    public NextResponseEntity<DiagramItemDepartureArrivalTimeDTO> getDepartureArrivalTimeByDiagramItemId(
        @PathVariable("id") Long diagramItemId,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                vehicleDiagramItemService.getDepartureArrivalTimeByDiagramItemId(diagramItemId),
                httpServletRequest));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PostMapping(VehicleDiagramItem.UPDATE_STATUS)
    public NextResponseEntity<String> updateVehicleStatus(
        @PathVariable("id") Long diagramItemId,
        @PathVariable("type") Long type,
        @RequestBody UpdateTrackStatusRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramItemService.updateVehicleStatus(diagramItemId, type, request),
                httpServletRequest));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping(value = VehicleDiagramItem.IS_PRIVATE)
    public NextResponseEntity<Void> updatePrivateDiagramItem(
        @PathVariable("id") Long id, @Valid @RequestBody UpdatePrivateDiagramItemRequest request,
        HttpServletRequest httpServletRequest) {
        vehicleDiagramItemService.updatePrivateDiagramItem(id, request, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping(value = VehicleDiagramItem.UPDATE_TIME)
    public NextResponseEntity<Void> updateDiagramItemTime(
        @PathVariable("id") Long id, @Valid @RequestBody UpdateDiagramItemTimeRequest request,
        HttpServletRequest httpServletRequest) {
        vehicleDiagramItemService.updateDiagramItemTime(id, request);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping(value = VehicleDiagramItem.IS_EMERGENCY)
    public NextResponseEntity<Void> updateEmergencyDiagramItem(
        @PathVariable("id") Long id, @Valid @RequestBody UpdateEmergencyRequest request,
        HttpServletRequest httpServletRequest) {
        vehicleDiagramItemService.updateEmergencyDiagramItem(id, request);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping(value = VehicleDiagramItem.UPDATE_ITEM_STATUS)
    public NextResponseEntity<Void> updateDiagramItemStatus(
        @RequestBody DiagramItemUpdateStatusRequest request,
        HttpServletRequest httpServletRequest) {
        vehicleDiagramItemService.updateDiagramItemStatus(request);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

}
