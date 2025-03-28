package jp.co.nlj.ttmi.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.ttmi.constant.APIConstant;
import jp.co.nlj.ttmi.constant.MessageConstant;
import jp.co.nlj.ttmi.dto.vehicleIncident.request.TTMIVehicleIncidentInfoRequestDTO;
import jp.co.nlj.ttmi.service.VehicleIncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 車両情報コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.VehicleIncident.PREFIX)
@RequiredArgsConstructor
public class VehicleIncidentController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleIncidentService vehicleIncidentService;

    @Operation(
        summary = "車両図面情報の登録",
        description = "車両図面の新規情報をシステムに登録するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstant.APIResponses.SUCCESS_CODE, description = MessageConstant.APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = MessageConstant.APIResponses.BAD_REQUEST_CODE, description = MessageConstant.APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = MessageConstant.APIResponses.ERROR_CODE, description = MessageConstant.APIResponses.ERROR_MSG),})
    @PostMapping
    public NextResponseEntity<Void> register(
        @Valid @RequestBody TTMIVehicleIncidentInfoRequestDTO ttmiVehicleIncidentInfoRequestDTO,
        HttpServletRequest httpServletRequest) {
        vehicleIncidentService.registerVehicleIncident(httpServletRequest, ttmiVehicleIncidentInfoRequestDTO);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest), HttpStatus.CREATED);
    }
}
