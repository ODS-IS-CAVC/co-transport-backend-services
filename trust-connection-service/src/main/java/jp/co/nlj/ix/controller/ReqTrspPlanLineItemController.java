package jp.co.nlj.ix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.math.BigDecimal;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.ReqTrspPlanLineItem;
import jp.co.nlj.ix.constant.MessageConstant;
import jp.co.nlj.ix.constant.MessageConstant.APIResponses;
import jp.co.nlj.ix.dto.commonBody.request.CommonRequestDTO;
import jp.co.nlj.ix.dto.commonBody.response.CommonResponseDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemDeleteRequest;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemRegisterRequest;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemSearchRequest;
import jp.co.nlj.ix.service.ReqTrspPlanLineItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <PRE>
 * ReqTrspPlanLineItemControllerクラスは、リクエスト輸送計画明細項目コントローラです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(ReqTrspPlanLineItem.PREFIX)
public class ReqTrspPlanLineItemController {

    private final ResponseBuilderComponent builderComponent;
    private final ReqTrspPlanLineItemService reqTrspPlanLineItemService;

    @Operation(summary = "運送計画情報（明細型）を登録", description = "運送計画情報（明細型）を登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = "運送能力情報（明細型）を正常に登録しました。"),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.SERVER_ERROR_MSG_JA)
    })
    @PutMapping()
    public NextResponseEntity<Void> registerTrspPlan(
        @Valid @RequestBody CommonRequestDTO commonRequestDTO,
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse,
        HttpServletRequest httpServletRequest) {
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ReqTrspPlanLineItemRegisterRequest request = new ReqTrspPlanLineItemRegisterRequest();
        try {
            request = objectMapper.convertValue(commonRequestDTO.getAttribute(),
                ReqTrspPlanLineItemRegisterRequest.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        reqTrspPlanLineItemService.registerTrspPlan(request, xTracking);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "運送計画情報（明細型）を削除", description = "運送計画情報（明細型）を更新します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "運送能力情報（明細型）を正常に削除しました。"),
        @ApiResponse(responseCode = "400", description = "運送能力情報（明細型）が見つからない場合のレスポンス"),
        @ApiResponse(responseCode = "401", description = "認証エラーに対するレスポンス"),
        @ApiResponse(responseCode = "500", description = "サーバーエラーに対するレスポンス")
    })
    @DeleteMapping()
    public NextResponseEntity<Void> deleteTrspPlan(
        @Valid @RequestBody CommonRequestDTO commonRequestDTO,
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse,
        HttpServletRequest httpServletRequest) {
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ReqTrspPlanLineItemDeleteRequest request = new ReqTrspPlanLineItemDeleteRequest();
        try {
            request = objectMapper.convertValue(commonRequestDTO.getAttribute(),
                ReqTrspPlanLineItemDeleteRequest.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        reqTrspPlanLineItemService.deleteTrspPlan(request, xTracking);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "指定した条件の運送能力情報（明細型）を取得", description = "指定した条件に基づいて、運送能力情報（明細型）を取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "指定した条件に基づいて、運送能力情報（明細型）を正常に取得しました。"),
        @ApiResponse(responseCode = "400", description = "リクエスト自体に問題がある場合"),
        @ApiResponse(responseCode = "401", description = "アクセストークン発行APIで発行したアクセストークン"),
        @ApiResponse(responseCode = "500", description = "システムの内部にてエラーが発生している場合")
    })
    @GetMapping()
    public NextResponseEntity<CommonResponseDTO> searchTrspPlan(@Valid
    @RequestParam(value = "from_plc_cd_prty_id", required = false) String fromPlcCdPrtyId,
        @RequestParam(value = "to_plc_cd_prty_id", required = false) String toPlcCdPrtyId,
        @RequestParam(value = "istd_totl_pcks_quan", required = false) BigDecimal istdTotlPcksQuan,
        @RequestParam(value = "dsed_cll_from_date", required = false) String dsedCllFromDate,
        @RequestParam(value = "dsed_cll_to_date", required = false) String dsedCllToDate,
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse,
        HttpServletRequest httpServletRequest) {
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        ReqTrspPlanLineItemSearchRequest request = new ReqTrspPlanLineItemSearchRequest();
        request.setFromPlcCdPrtyId(fromPlcCdPrtyId);
        request.setToPlcCdPrtyId(toPlcCdPrtyId);
        request.setIstdTotlPcksQuan(istdTotlPcksQuan);
        request.setDsedCllFromDate(dsedCllFromDate);
        request.setDsedCllToDate(dsedCllToDate);

        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(reqTrspPlanLineItemService.searchTrspPlan(request), httpServletRequest));
    }
}
