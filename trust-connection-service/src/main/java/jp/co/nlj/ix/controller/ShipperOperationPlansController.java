package jp.co.nlj.ix.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.ix.constant.APIConstant.ShipperOperationPlans;
import jp.co.nlj.ix.constant.MessageConstant.APIResponses;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationApprovalRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationReserveRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.request.ShipperOperationUpdateRequestDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationApprovalResponseDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationReserveResponseDTO;
import jp.co.nlj.ix.dto.shipperOperationPlans.response.ShipperOperationUpdateResponseDTO;
import jp.co.nlj.ix.service.ShipperOperationPlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 荷主向け運行申し込みコントローラー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(ShipperOperationPlans.PREFIX)
@RequiredArgsConstructor
public class ShipperOperationPlansController extends BaseController {

    private final ResponseBuilderComponent builderComponent;
    private final ShipperOperationPlansService shipperOperationPlansService;

    @Operation(summary = ShipperOperationPlans.SUMMARY, description = ShipperOperationPlans.DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG_JA),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PostMapping(ShipperOperationPlans.APPROVAL_PREFIX)
    public NextResponseEntity<ShipperOperationApprovalResponseDTO> shipperOperationApproval(
        @PathVariable("operation_id") String operationIdParam,
        @PathVariable("propose_id") String proposeId,
        @Valid @RequestBody ShipperOperationApprovalRequestDTO shipperOperationApprovalRequestDTO,
        HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                shipperOperationPlansService.shipperOperationApproval(shipperOperationApprovalRequestDTO,
                    operationIdParam, proposeId),
                request
            )
        );
    }

    @Operation(summary = ShipperOperationPlans.UPDATE_SUMMARY, description = ShipperOperationPlans.UPDATE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG_JA),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PutMapping(ShipperOperationPlans.UPDATE_PREFIX)
    public NextResponseEntity<ShipperOperationUpdateResponseDTO> shipperOperationUpdate(
        @PathVariable("operation_id") String operationIdParam,
        @PathVariable("propose_id") String proposeId,
        @Valid @RequestBody ShipperOperationUpdateRequestDTO shipperOperationUpdateRequestDTO,
        HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                shipperOperationPlansService.shipperOperationUpdate(shipperOperationUpdateRequestDTO,
                    operationIdParam, proposeId),
                request
            )
        );
    }

    @Operation(summary = ShipperOperationPlans.RESERVE_SUMMARY, description = ShipperOperationPlans.RESERVE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG_JA),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PostMapping(ShipperOperationPlans.PROPOSE_PREFIX)
    public NextResponseEntity<ShipperOperationReserveResponseDTO> shipperOperationReserve(
        @Valid @RequestBody ShipperOperationReserveRequestDTO shipperOperationReserveRequestDTO,
        HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                shipperOperationPlansService.shipperOperationReserve(shipperOperationReserveRequestDTO),
                request
            )
        );
    }
}
