package jp.co.nlj.gateway.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.gateway.constant.APIConstant;
import jp.co.nlj.gateway.dto.trackBySip.request.TrackBySipRequestDTO;
import jp.co.nlj.gateway.dto.trackBySip.response.RestTemplateApiResponse;
import jp.co.nlj.gateway.service.TrackBySipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TrackBySipControllerクラスは、トラックバイSIPの情報を管理するためのコントローラークラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.TrackBySip.PREFIX)
@RequiredArgsConstructor
public class TrackBySipController {

    private final TrackBySipService trackBySipService;
    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = APIConstant.TrackBySip.CREATE_NEW_TRACK_BY_SIP_SUMMARY, description =
        APIConstant.TrackBySip.CREATE_NEW_TRACK_BY_SIP_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_CREATED, description =
            APIConstant.TrackBySip.STATUS_CODE_CREATED_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_BAD_REQUEST, description =
            APIConstant.TrackBySip.STATUS_CODE_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_UNAUTHORIZED, description =
            APIConstant.TrackBySip.STATUS_CODE_UNAUTHORIZED_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_FORBIDDEN, description =
            APIConstant.TrackBySip.STATUS_CODE_FORBIDDEN_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_NOT_FOUND, description =
            APIConstant.TrackBySip.STATUS_CODE_NOT_FOUND_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_INTERNAL_SERVER_ERROR, description =
            APIConstant.TrackBySip.STATUS_CODE_INTERNAL_SERVER_ERROR_DESCRIPTION)})
    @PostMapping
    public NextResponseEntity<RestTemplateApiResponse> createTrackBySIP(
        @Valid @RequestBody TrackBySipRequestDTO trackBySipRequestDTO, HttpServletRequest httpServletRequest) {
        trackBySipRequestDTO.setPost(true);
        RestTemplateApiResponse apiResponse = trackBySipService.createTrackBySIP(httpServletRequest,
            trackBySipRequestDTO);
        return new NextResponseEntity<>(builderComponent.buildResponse(apiResponse, httpServletRequest));
    }

    @Operation(summary = APIConstant.TrackBySip.UPDATE_TRACK_BY_SIP_SUMMARY, description =
        APIConstant.TrackBySip.UPDATE_TRACK_BY_SIP_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_UPDATE_SUCCESS, description =
            APIConstant.TrackBySip.STATUS_CODE_UPDATE_SUCCESS_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_BAD_REQUEST, description =
            APIConstant.TrackBySip.STATUS_CODE_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_UNAUTHORIZED, description =
            APIConstant.TrackBySip.STATUS_CODE_UNAUTHORIZED_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_FORBIDDEN, description =
            APIConstant.TrackBySip.STATUS_CODE_FORBIDDEN_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_NOT_FOUND, description =
            APIConstant.TrackBySip.STATUS_CODE_NOT_FOUND_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.TrackBySip.STATUS_CODE_INTERNAL_SERVER_ERROR, description =
            APIConstant.TrackBySip.STATUS_CODE_INTERNAL_SERVER_ERROR_DESCRIPTION)})
    @PutMapping
    public NextResponseEntity<RestTemplateApiResponse> updateTrackBySIP(
        @Valid @RequestBody TrackBySipRequestDTO trackBySipRequestDTO, HttpServletRequest httpServletRequest) {
        trackBySipRequestDTO.setPost(false);
        RestTemplateApiResponse apiResponse = trackBySipService.updateTrackBySIP(httpServletRequest,
            trackBySipRequestDTO);
        return new NextResponseEntity<>(builderComponent.buildResponse(apiResponse, httpServletRequest));
    }
}
