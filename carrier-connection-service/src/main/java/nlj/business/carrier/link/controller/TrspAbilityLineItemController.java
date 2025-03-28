package nlj.business.carrier.link.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.NextResponseEntity;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.link.constant.APIConstant;
import nlj.business.carrier.link.constant.APIConstant.TrspAbilityLineItem;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.MessageConstant.APIResponses;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.trspAbilityLineItem.response.TrspAbilityLineItemResponseDTO;
import nlj.business.carrier.link.service.TrspAbilityLineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 運送能力明細項目コントローラー<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping(TrspAbilityLineItem.PREFIX)
@RequiredArgsConstructor
public class TrspAbilityLineItemController {

    private final ResponseBuilderComponent builderComponent;
    private final TrspAbilityLineItemService trspAbilityLineItemService;

    @Operation(summary = "車輛マスタを登録", description = "車輛マスタを登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.REGISTER_VEHICLE_SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.SERVER_ERROR_MSG_JA)
    })
    @PutMapping
    public NextResponseEntity<TrspAbilityLineItemResponseDTO> registerTransportationCapacity(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        @Valid @RequestBody CommonRequestDTO commonRequestDTO,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) {
        log.info("PUT /trsp_ability_line_item Request Body: {}", commonRequestDTO);
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        TrspAbilityLineItemResponseDTO responseDTO = trspAbilityLineItemService.registerTrspAbilityLineItem(
            commonRequestDTO, httpServletRequest.getRemoteAddr());
        return new NextResponseEntity<>(
            builderComponent.buildResponse(responseDTO, httpServletRequest));
    }
}
