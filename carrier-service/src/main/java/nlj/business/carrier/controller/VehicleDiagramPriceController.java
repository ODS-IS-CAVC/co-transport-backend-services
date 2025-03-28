package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.VehicleDiagramPrice;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.request.VehicleDiagramItemTrailerRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramPrice.response.VehicleDiagramPriceResponseDTO;
import nlj.business.carrier.service.VehicleDiagramPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 車両図面価格コントローラークラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(VehicleDiagramPrice.PREFIX)
@RequiredArgsConstructor
public class VehicleDiagramPriceController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleDiagramPriceService vehicleDiagramPriceService;

    @Operation(summary = "Get vehicle diagram prices", description = "Get vehicle diagram prices")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @GetMapping(value = VehicleDiagramPrice.PATH_VARIABLE_ID)
    public NextResponseEntity<VehicleDiagramPriceResponseDTO> getVehicleDiagramPrices(
        @Valid @PathVariable("id") Long id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                vehicleDiagramPriceService.getVehicleDiagramPrices(id),
                httpServletRequest));
    }

    @Operation(summary = "Update vehicle diagram prices", description = "Update vehicle diagram prices")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PostMapping(value = VehicleDiagramPrice.PATH_VARIABLE_ID)
    public NextResponseEntity<VehicleDiagramPriceResponseDTO> updateVehicleDiagramPrices(
        @Valid @PathVariable("id") Long id,
        @Valid @RequestBody VehicleDiagramItemTrailerRequestDTO vehicleDiagramItemTrailerRequestDTO,
        HttpServletRequest httpServletRequest) {
        vehicleDiagramPriceService.addPriceForVehicleDiagramItemTrailerByDate(id, vehicleDiagramItemTrailerRequestDTO);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                null,
                httpServletRequest));
    }
}
