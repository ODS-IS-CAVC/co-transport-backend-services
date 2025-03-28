package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.dto.PageResponseDTO;
import nlj.business.transaction.dto.request.CarrierTransportPlanRequest;
import nlj.business.transaction.service.CnsLineItemByDateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * キャリア転送計画コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.CarrierTransportPlan.PREFIX)
@RequiredArgsConstructor
public class CarrierTransportPlanController {

    private final CnsLineItemByDateService cnsLineItemByDateService;

    private final ResponseBuilderComponent builderComponent;

    @GetMapping(value = APIConstant.CarrierTransportPlan.SEARCH)
    public NextResponseEntity<Object> CarrierTransportPlanPublic(CarrierTransportPlanRequest request,
        HttpServletRequest httpServletRequest) {
        if (request.getIsNotIX() == null) {
            request.setIsNotIX(false);
        }
        return new NextResponseEntity<>(builderComponent.buildResponse(
            new PageResponseDTO<>(cnsLineItemByDateService.getPagedTransportPlanPublic(request, httpServletRequest)),
            httpServletRequest));
    }
}
