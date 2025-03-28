package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.dto.request.TransMarketSearchRequest;
import nlj.business.transaction.service.TransMarketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 市場価格コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.MarketPrice.PREFIX)
@RequiredArgsConstructor
public class MarketPriceController {

    private final TransMarketService transMarketService;

    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = APIConstant.MarketPrice.LIST_SHIPPER_SUMMARY, description = APIConstant.MarketPrice.LIST_SHIPPER_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.MarketPrice.LIST_SHIPPER_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @GetMapping(value = APIConstant.MarketPrice.LIST_SHIPPER)
    public NextResponseEntity<Object> getTransMarketByMonth(TransMarketSearchRequest searchRequest,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transMarketService.search(searchRequest.getMonth()), httpServletRequest));
    }
}
