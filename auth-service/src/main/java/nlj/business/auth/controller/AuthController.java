package nlj.business.auth.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import com.next.logistic.model.SystemLogin;
import com.next.logistic.model.UserLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.auth.constant.APIConstant;
import nlj.business.auth.dto.auth.response.TokenResponseDTO;
import nlj.business.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 認証コントローラー<BR>
 * </PRE>
 * 
 * @author Next Logistics Japan
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = "Login User", description = "Login User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful Operation"),
        @ApiResponse(responseCode = "400", description = "Invalid Parameter/Bad Request")})
    @PostMapping(value = APIConstant.AUTH.LOGIN)
//    @PreAuthorize("@preXAuthorization.hasAnyRole({'NEXTR-ADMIN'})")
    public NextResponseEntity<TokenResponseDTO> loginUser(@Valid @RequestBody UserLogin dto,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(builderComponent.buildResponse(
            authService.loginUser(dto), httpServletRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Login System", description = "Login System")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful Operation"),
        @ApiResponse(responseCode = "400", description = "Invalid Parameter/Bad Request")})
    @PostMapping(value = APIConstant.AUTH.CLIENT)
//    @PreAuthorize("@preXAuthorization.hasAnyRole({'NEXTR-ADMIN'})")
    public NextResponseEntity<TokenResponseDTO> loginSystem(@Valid @RequestBody SystemLogin dto,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(builderComponent.buildResponse(
            authService.loginSystem(dto), httpServletRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Refresh Token", description = "Refresh Token")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful Operation"),
        @ApiResponse(responseCode = "400", description = "Invalid Parameter/Bad Request")})
    @PostMapping(value = APIConstant.AUTH.REFRESH)
//    @PreAuthorize("@preXAuthorization.hasAnyRole({'NEXTR-ADMIN'})")
    public NextResponseEntity<TokenResponseDTO> refreshToken(@Valid @RequestParam("refreshToken") String refreshToken,
        HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(builderComponent.buildResponse(
            authService.refreshToken(refreshToken), httpServletRequest), HttpStatus.CREATED);
    }
}
