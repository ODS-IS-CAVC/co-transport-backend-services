package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.dto.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.transaction.service.VehicleDiagramItemService;
import nlj.business.transaction.service.ZLWebService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * ZLWebコントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.ZLWeb.PREFIX)
@RequiredArgsConstructor
public class ZLWebController {

    private final ResponseBuilderComponent builderComponent;
    private final ZLWebService zlWebService;
    private final VehicleDiagramItemService vehicleDiagramItemService;

    @Operation(summary = "Send data to ZLWeb", description = "Send data to ZLWeb")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TransportPlan.POST_MATCHED_TRANSPORT_PLAN_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping
    public NextResponseEntity<Void> sendDataToZLWeb(
        @PathVariable("id") Long id,
        HttpServletRequest httpServletRequest) {
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO = vehicleDiagramItemService.getDepartureArrivalTime(
            id, httpServletRequest);
        zlWebService.sendToZLWeb(id, departureArrivalTimeDTO, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest), HttpStatus.OK);
    }
}
