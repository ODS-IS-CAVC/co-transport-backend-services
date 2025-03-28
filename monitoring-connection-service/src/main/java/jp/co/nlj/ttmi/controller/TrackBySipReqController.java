package jp.co.nlj.ttmi.controller;


import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.ttmi.constant.APIConstant.TrackBySipReq;
import jp.co.nlj.ttmi.constant.MessageConstant.APIResponses;
import jp.co.nlj.ttmi.dto.response.ResponseAPI;
import jp.co.nlj.ttmi.dto.trackBySip.request.TrackBySipReqDTO;
import jp.co.nlj.ttmi.service.TrackBySipReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping(TrackBySipReq.PREFIX)
@RequiredArgsConstructor
public class TrackBySipReqController {

    private final ResponseBuilderComponent builderComponent;
    private final TrackBySipReqService trackBySipReqService;

    @Operation(
        summary = "車両図面情報の登録",
        description = "車両図面の新規情報をシステムに登録するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping()
    public NextResponseEntity<ResponseAPI> update(@Valid @RequestBody TrackBySipReqDTO trackBySipReqDTO,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                trackBySipReqService.updateTrackByShipReq(httpServletRequest, trackBySipReqDTO),
                httpServletRequest));
    }


}
