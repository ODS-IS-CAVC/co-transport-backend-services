package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.ValidateSemiDynamicInfo;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.dto.semiDynamicInfo.request.ValidateSemiDynamicInfoRequestDTO;
import nlj.business.carrier.dto.semiDynamicInfo.response.ValidateSemiDynamicInfoResponseDTO;
import nlj.business.carrier.service.SemiDynamicInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * セミダイナミック情報の検証コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(ValidateSemiDynamicInfo.PREFIX)
@RequiredArgsConstructor
public class ValidateSemiDynamicInfoController {

    private final ResponseBuilderComponent builderComponent;
    private final SemiDynamicInfoService semiDynamicInfoService;

    @Operation(summary = ValidateSemiDynamicInfo.VALIDATE_SEMI_DYNAMIC_INFO_SUMMARY, description =
        ValidateSemiDynamicInfo.VALIDATE_SEMI_DYNAMIC_INFO_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PostMapping
    public NextResponseEntity<ValidateSemiDynamicInfoResponseDTO> validateSemiDynamicInfo(
        @Valid @RequestBody ValidateSemiDynamicInfoRequestDTO validateSemiDynamicInfoRequestDTO,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                semiDynamicInfoService.validateSemiDynamicInfo(validateSemiDynamicInfoRequestDTO),
                httpServletRequest));
    }

}
