package nlj.business.carrier.link.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.APIConstant;
import nlj.business.carrier.link.dto.transaction.request.UpdateTransactionRequest;
import nlj.business.carrier.link.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <PRE>
 * トランザクションコントローラー<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final ResponseBuilderComponent builderComponent;

    @PostMapping(APIConstant.Transaction.TRANSACTION_CARRIER_CARRIER)
    public NextResponseEntity<Object> transactionCarrierCarrier(@Valid @RequestBody UpdateTransactionRequest request,
        HttpServletRequest httpServletRequest) {
        transactionService.updateStatusTransaction(Long.valueOf(request.getShipperId()),
            Long.valueOf(request.getCarrierId()), Integer.valueOf(request.getStatus()));
        return new NextResponseEntity<>(builderComponent.buildResponse("OK", httpServletRequest));
    }
}
