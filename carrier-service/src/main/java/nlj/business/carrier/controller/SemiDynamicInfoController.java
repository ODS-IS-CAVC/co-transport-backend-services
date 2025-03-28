package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.SemiDynamicInfo;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.dto.semiDynamicInfo.request.SemiDynamicInfoRequestDTO;
import nlj.business.carrier.dto.semiDynamicInfo.response.SemiDynamicInfoResponseDTO;
import nlj.business.carrier.service.SemiDynamicInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * セミダイナミック情報コントローラ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(SemiDynamicInfo.PREFIX)
@RequiredArgsConstructor
public class SemiDynamicInfoController {

    private final ResponseBuilderComponent builderComponent;
    private final SemiDynamicInfoService semiDynamicInfoService;

    @Operation(summary = SemiDynamicInfo.GET_CURRENT_SEMI_DYNAMIC_INFO_SUMMARY, description =
        SemiDynamicInfo.GET_CURRENT_SEMI_DYNAMIC_INFO_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PostMapping(SemiDynamicInfo.GET_CURRENT)
    public NextResponseEntity<SemiDynamicInfoResponseDTO> getCurrentSemiDynamicInfo(
        @Valid @RequestBody SemiDynamicInfoRequestDTO semiDynamicInfoRequestDTO,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(
            builderComponent.buildResponse(semiDynamicInfoService.getCurrentSemiDynamicInfo(semiDynamicInfoRequestDTO),
                httpServletRequest));
    }

    @Operation(summary = SemiDynamicInfo.GET_BEFORE_START_SEMI_DYNAMIC_INFO_SUMMARY, description =
        SemiDynamicInfo.GET_BEFORE_START_SEMI_DYNAMIC_INFO_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PostMapping(SemiDynamicInfo.GET_BEFORE_START)
    public NextResponseEntity<SemiDynamicInfoResponseDTO> getBeforeStartSemiDynamicInfo(
        @Valid @RequestBody SemiDynamicInfoRequestDTO semiDynamicInfoRequestDTO,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                semiDynamicInfoService.getBeforeStartSemiDynamicInfo(semiDynamicInfoRequestDTO),
                httpServletRequest));
    }
}
