package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.VehicleDiagramItemTrailer;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.dto.transferStatus.ItemTrailerUpdateStatusRequestDTO;
import nlj.business.carrier.dto.transferStatus.ItemTrailerUpdateStatusResponse;
import nlj.business.carrier.service.VehicleDiagramItemService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 車両ダイアグラムアイテムトレイラコントローラ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(VehicleDiagramItemTrailer.PREFIX)
@RequiredArgsConstructor
public class VehicleDiagramItemTrailerController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleDiagramItemService vehicleDiagramItemService;

    @Operation(
        summary = "車両図面情報の登録",
        description = "車両図面の新規情報をシステムに登録するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping
    public NextResponseEntity<ItemTrailerUpdateStatusResponse> updateStatus(
        @Valid @RequestBody ItemTrailerUpdateStatusRequestDTO requestDTO,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramItemService.updateItemTrailerStatus(requestDTO),
                httpServletRequest));
    }

}
