package jp.co.nlj.ix.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.Transports;
import jp.co.nlj.ix.dto.sendMail.SendMailRequest;
import jp.co.nlj.ix.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <PRE>
 * TransportsControllerクラスは、輸送実施（確認）通知コントローラです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(Transports.PREFIX)
public class TransportsController extends BaseController {

    private final ResponseBuilderComponent builderComponent;
    private final MailService mailService;

    @Operation(summary = "輸送実施（確認）通知", description = "運送事業者、車輌情報、車輌稼働可能リソース")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = APIConstant.TrspPlanLineItem.STATUS_OK_MSG),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(Transports.NOTIFY)
    public NextResponseEntity<Void> searchTrspPlan(@Valid @PathVariable("trsp_instruction_id") Long trsp_instruction_id,
        HttpServletRequest httpServletRequest) {
        SendMailRequest request = new SendMailRequest();
        request.setCnsLineItemByDateId(trsp_instruction_id);
        log.info("API152- trsp_instruction_id={}", trsp_instruction_id);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }
}
