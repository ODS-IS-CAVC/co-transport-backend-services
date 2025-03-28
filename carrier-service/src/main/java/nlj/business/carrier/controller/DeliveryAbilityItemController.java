package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.DeliveryAbilityItem;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemTrackingResponseDTO;
import nlj.business.carrier.service.VehicleDiagramItemService;
import nlj.business.carrier.service.VehicleDiagramItemTrackingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 配達能力アイテムコントローラ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(DeliveryAbilityItem.PREFIX)
@RequiredArgsConstructor
public class DeliveryAbilityItemController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleDiagramItemService vehicleDiagramItemService;
    private final VehicleDiagramItemTrackingService vehicleDiagramItemTrackingService;

    @Operation(summary = "Get vehicle diagram items", description = "Get vehicle diagram items")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @GetMapping(value = DeliveryAbilityItem.PATH_VARIABLE_ID)
    public NextResponseEntity<List<VehicleDiagramItemResponseDTO>> getDeliveryAbilityItem(
        @Valid @PathVariable("id") Long id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                vehicleDiagramItemService.getVehicleDiagramItems(id),
                httpServletRequest)
        );
    }

    @Operation(summary = "Get vehicle diagram item tracking", description = "Get vehicle diagram item tracking")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @GetMapping(value = DeliveryAbilityItem.TRACKING)
    public NextResponseEntity<VehicleDiagramItemTrackingResponseDTO> getDeliveryAbilityItemTracking(
        @Valid @PathVariable("id") Long id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                vehicleDiagramItemTrackingService.getVehicleDiagramItemTracking(id, httpServletRequest),
                httpServletRequest)
        );
    }

    @GetMapping(value = DeliveryAbilityItem.OPERATION_TRACKING)
    public NextResponseEntity<VehicleDiagramItemTrackingResponseDTO> getOperationTracking(
        @Valid @PathVariable("id") Long id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                vehicleDiagramItemTrackingService.getOperationTracking(id, httpServletRequest),
                httpServletRequest)
        );
    }
}
