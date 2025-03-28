package jp.co.nlj.verify.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.verify.constant.APIConstant;
import jp.co.nlj.verify.dto.auth.response.VerifyTokenResponseDTO;
import jp.co.nlj.verify.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 認証コントローラクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = "Verify Token", description = "Verify Token")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful Operation"),
        @ApiResponse(responseCode = "400", description = "Invalid Parameter/Bad Request")})
    @PostMapping(value = APIConstant.VERIFY_TOKEN)
    public NextResponseEntity<VerifyTokenResponseDTO> verifyToken(@Valid @RequestParam("idToken") String idToken,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(builderComponent.buildResponse(
            authService.verifyToken(idToken), httpServletRequest), HttpStatus.CREATED);
    }
}
