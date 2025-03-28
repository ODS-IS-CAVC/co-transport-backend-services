package jp.co.nlj.gateway.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.gateway.constant.APIConstant;
import jp.co.nlj.gateway.constant.APIConstant.SemiDynamicInfo;
import jp.co.nlj.gateway.dto.semiDynamicInfo.request.SemiDynamicInfoRequestDTO;
import jp.co.nlj.gateway.dto.semiDynamicInfo.response.SemiDynamicInfoResponseDTO;
import jp.co.nlj.gateway.service.SemiDynamicInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SemiDynamicInfoControllerクラスは、セミダイナミックな情報を管理するためのコントローラークラスです。
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

    @Operation(summary = SemiDynamicInfo.GET_SEMI_DYNAMIC_INFO_SUMMARY, description =
        SemiDynamicInfo.GET_SEMI_DYNAMIC_INFO_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.STATUS_OK_DESCRIPTION)})
    @PostMapping
    public NextResponseEntity<SemiDynamicInfoResponseDTO> getSemiDynamicInfo(
        @RequestBody SemiDynamicInfoRequestDTO requestDTO,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(
            builderComponent.buildResponse(semiDynamicInfoService.getSemiDynamicInfo(requestDTO, httpServletRequest),
                httpServletRequest));
    }
}
