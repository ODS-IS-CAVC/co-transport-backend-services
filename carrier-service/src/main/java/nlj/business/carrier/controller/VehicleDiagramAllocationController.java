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
import nlj.business.carrier.constant.APIConstant.VehicleDiagramAllocation;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.dto.vehicleDiagramAllocation.request.VehicleDiagramAllocationRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResponseDTO;
import nlj.business.carrier.service.VehicleDiagramAllocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 車両ダイアグラム割り当てコントローラ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(VehicleDiagramAllocation.PREFIX)
@RequiredArgsConstructor
public class VehicleDiagramAllocationController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleDiagramAllocationService vehicleDiagramAllocationService;

    @Operation(summary = "Register vehicle diagram allocation", description = "Register vehicle diagram allocation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PostMapping(value = VehicleDiagramAllocation.PATH_VARIABLE_ALLOCATION)
    public NextResponseEntity<Void> registerVehicleDiagramAllocation(
        @Valid @PathVariable("id") Long id,
        @Valid @RequestBody List<VehicleDiagramAllocationRequestDTO> vehicleDiagramAllocationRequestDTO,
        HttpServletRequest httpServletRequest) {
        vehicleDiagramAllocationService.saveVehicleDiagramAllocation(id, vehicleDiagramAllocationRequestDTO);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "Get vehicle diagram allocation", description = "Get vehicle diagram allocation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @GetMapping(value = VehicleDiagramAllocation.PATH_VARIABLE_ALLOCATION)
    public NextResponseEntity<List<VehicleDiagramAllocationResponseDTO>> getVehicleDiagramAllocation(
        @Valid @PathVariable("id") Long id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                vehicleDiagramAllocationService.getVehicleDiagramAllocation(id),
                httpServletRequest));
    }
}
