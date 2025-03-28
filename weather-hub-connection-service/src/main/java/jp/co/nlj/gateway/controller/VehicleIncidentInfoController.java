package jp.co.nlj.gateway.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.gateway.constant.APIConstant;
import jp.co.nlj.gateway.dto.vehicleIncidentInfo.request.VehicleIncidentInfoRequestDTO;
import jp.co.nlj.gateway.service.VehicleIncidentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * VehicleIncidentInfoControllerクラスは、車両事故情報を管理するためのコントローラークラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.VehicleIncidentInfo.PREFIX)
@RequiredArgsConstructor
public class VehicleIncidentInfoController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleIncidentInfoService vehicleIncidentInfoService;

    @Operation(summary = APIConstant.VehicleIncidentInfo.REGISTER_VIHICLE_INCIDENT_INFO_SUMMARY, description =
        APIConstant.VehicleIncidentInfo.REGISTER_VIHICLE_INCIDENT_INFO_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description =
            APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)})
    @PutMapping
    public NextResponseEntity<Void> registerVehicleIncidentInfo(
        @Valid @RequestBody VehicleIncidentInfoRequestDTO vehicleIncidentInfo, HttpServletRequest httpServletRequest) {
        vehicleIncidentInfoService.registerVehicleIncidentInfo(vehicleIncidentInfo, httpServletRequest);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }
}
