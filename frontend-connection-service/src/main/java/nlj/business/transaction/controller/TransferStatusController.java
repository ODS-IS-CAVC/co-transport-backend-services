package nlj.business.transaction.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.dto.transferStatus.request.TransferStatusRequest;
import nlj.business.transaction.dto.transferStatus.response.TransferStatusResponse;
import nlj.business.transaction.service.TransferStatusService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 転送ステータスコントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.TransferStatus.PREFIX)
@RequiredArgsConstructor
public class TransferStatusController {

    private final ResponseBuilderComponent builderComponent;
    private final TransferStatusService transferStatusService;

    @PutMapping
    public NextResponseEntity<TransferStatusResponse> transferStatus(@Valid @RequestBody TransferStatusRequest request,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transferStatusService.updateStatus(request, httpServletRequest),
                httpServletRequest));
    }
}
