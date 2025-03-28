package nlj.business.shipper.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.constant.APIConstant.TransportPlanBulk;
import nlj.business.shipper.constant.MessageConstant.APIResponses;
import nlj.business.shipper.dto.TransportPlanImportDTO;
import nlj.business.shipper.dto.request.TransportPlanFileRequestDTO;
import nlj.business.shipper.service.TransportPlanImportService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 輸送計画一括登録コントローラー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(TransportPlanBulk.PREFIX)
@RequiredArgsConstructor
public class TransportPlanBulkController {

    private final ResponseBuilderComponent builderComponent;
    private final TransportPlanImportService transportPlanImportService;

    @Operation(summary = "輸送計画を一括登録", description = "輸送計画を複数件一括登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public NextResponseEntity<TransportPlanImportDTO> createTransportPlanBulk(
        @Valid @ModelAttribute TransportPlanFileRequestDTO transportPlanFileRequestDTO,
        HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                transportPlanImportService.createTransportPlanBulk(transportPlanFileRequestDTO),
                request
            )
        );
    }
}
