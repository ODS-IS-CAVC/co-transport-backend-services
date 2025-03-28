package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.AreaLocation;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.dto.areaLocation.response.AreaLocationResponseDTO;
import nlj.business.carrier.service.AreaService;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(AreaLocation.PREFIX)
@RequiredArgsConstructor
public class AreaLocationController {

    private final ResponseBuilderComponent builderComponent;
    private final AreaService areaService;

    @Operation(summary = "Get locations", description = "Get locations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @GetMapping()
    public NextResponseEntity<List<AreaLocationResponseDTO>> getLocations(HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                areaService.getAllAreaLocation(),
                httpServletRequest)
        );
    }
}
