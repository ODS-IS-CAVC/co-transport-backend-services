package jp.co.nlj.ix.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.APIConstant.OperationNotify;
import jp.co.nlj.ix.constant.APIConstant.ShipperTransportCapacity;
import jp.co.nlj.ix.dto.sendMail.SendMailRequest;
import jp.co.nlj.ix.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * OperationNotifyControllerクラスは、運行実施（確認）通知コントローラです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping(OperationNotify.PREFIX)
@RequiredArgsConstructor
public class OperationNotifyController extends BaseController {

    private final MailService mailService;

    private final ResponseBuilderComponent builderComponent;

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(value = OperationNotify.NOTIFY)
    public NextResponseEntity<Void> sendOperationNotify(HttpServletRequest httpServletRequest,
        @PathVariable(name = "operation_id") Long operationId) {
        log.info("API145- operation_id={}", operationId);
        SendMailRequest request = new SendMailRequest();
        request.setVehicleAvbResourceItemId(operationId);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "運行実施（確認）通知", description = "運行～輸送前に運行実施（確認）通知を共同輸送システムに送るAPI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.SEARCH_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PostMapping(value = OperationNotify.HANDOVER_INFO)
    public NextResponseEntity<Void> handoverInfo(HttpServletRequest httpServletRequest,
        @RequestBody Object request,
        @PathVariable(name = "operation_id") String operationId,
        @PathVariable(name = "propose_id") String proposeId) {
        log.info("API134- operation_id={} propose_id {}", operationId, proposeId);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }
}
