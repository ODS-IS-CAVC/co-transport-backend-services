package nlj.business.carrier.link.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.link.constant.APIConstant.Incident;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.service.VehicleIncidentInfoService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 車両事故情報コントローラー<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping({Incident.PREFIX, Incident.DATA_CHANNEL_PREFIX})
@RequiredArgsConstructor
public class VehicleIncidentInfoController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleIncidentInfoService vehicleIncidentInfoService;

    @Operation(summary = "車輛マスタを更新", description = "車輛マスタを更新します。")
    @PutMapping(value = "")
    public NextResponseEntity<Void> registerIncident(@Valid @RequestBody CommonRequestDTO commonRequestDTO,
        HttpServletRequest httpServletRequest) throws JsonProcessingException {
        log.info("START registerIncident");
        vehicleIncidentInfoService.registerIncident(httpServletRequest, commonRequestDTO);
        log.info("END registerIncident");
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "車輛マスタを更新", description = "車輛マスタを更新します。")
    @PutMapping(value = "/tracking")
    public NextResponseEntity<Void> registerIncidentOperation(@Valid @RequestBody CommonRequestDTO commonRequestDTO,
        HttpServletRequest httpServletRequest) throws JsonProcessingException {
        log.info("START registerIncident");
        vehicleIncidentInfoService.registerIncidentOperation(httpServletRequest, commonRequestDTO);
        log.info("END registerIncident");
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

}
