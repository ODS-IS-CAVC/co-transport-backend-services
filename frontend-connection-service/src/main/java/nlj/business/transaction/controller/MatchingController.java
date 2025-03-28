package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.dto.request.MatchingIntervalRequest;
import nlj.business.transaction.dto.response.MatchingIntervalResponse;
import nlj.business.transaction.dto.response.TransactionResponseDataCommon;
import nlj.business.transaction.service.MatchingIntervalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * マッチングコントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.Matching.PREFIX)
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingIntervalService matchingIntervalService;

    private final ResponseBuilderComponent builderComponent;

    @GetMapping(APIConstant.Matching.MATCHING_INTERVAL)
    public NextResponseEntity<Object> matchingGet(HttpServletRequest httpServletRequest,
        @PathVariable(name = "target") String target,
        @PathVariable(name = "interval") String interval) {
        MatchingIntervalResponse result = matchingIntervalService.matchingByInterval(
            new MatchingIntervalRequest(target, interval), httpServletRequest);
        TransactionResponseDataCommon<MatchingIntervalResponse> responseData = new TransactionResponseDataCommon<>();
        responseData.setData(result);
        return new NextResponseEntity<>(builderComponent.buildResponse(responseData, httpServletRequest));
    }
}
