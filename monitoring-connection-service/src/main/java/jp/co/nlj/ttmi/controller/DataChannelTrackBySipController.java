package jp.co.nlj.ttmi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.ttmi.constant.APIConstant.TrackBySipReq;
import jp.co.nlj.ttmi.constant.MessageConstant.APIResponses;
import jp.co.nlj.ttmi.dto.commonBody.request.CommonRequestDTO;
import jp.co.nlj.ttmi.dto.response.ResponseAPI;
import jp.co.nlj.ttmi.service.TrackBySipReqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping(TrackBySipReq.DATA_CHANNEL_PREFIX)
@RequiredArgsConstructor
public class DataChannelTrackBySipController {

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
    public NextResponseEntity<ResponseAPI> update(@Valid @RequestBody CommonRequestDTO commonRequestDTO,
        HttpServletRequest httpServletRequest) throws JsonProcessingException {
        log.info(commonRequestDTO.toString());
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                trackBySipReqService.updateTrackByShipDataChannel(httpServletRequest, commonRequestDTO),
                httpServletRequest));
    }
}
