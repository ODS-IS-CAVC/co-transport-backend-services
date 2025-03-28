package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant.TransportOrder;
import nlj.business.transaction.dto.request.SendMailRequest;
import nlj.business.transaction.dto.request.TransportOrderSearchRequest;
import nlj.business.transaction.service.TransOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 転送注文コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(TransportOrder.PREFIX)
public class TransportOrderController {

    private final ResponseBuilderComponent builderComponent;
    private final TransOrderService transOrderService;

    @PostMapping
    public NextResponseEntity<String> findByTrailerId(@RequestBody TransportOrderSearchRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(builderComponent.buildResponse(
            transOrderService.findAllByItemTrailerIds(request.getVehicleDiagramItemTrailerId()), httpServletRequest));
    }

    @PostMapping(value = TransportOrder.SENDMAIL)
    public NextResponseEntity<String> sendMail(@RequestBody SendMailRequest request,
        HttpServletRequest httpServletRequest) {
        transOrderService.sendIXMail(request);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }
}
