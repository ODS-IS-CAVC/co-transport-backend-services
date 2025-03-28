package nlj.business.shipper.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.constant.APIConstant.TransportPlan;
import nlj.business.shipper.constant.MessageConstant.APIResponses;
import nlj.business.shipper.constant.ParamConstant;
import nlj.business.shipper.dto.request.TransportPlanRequestDTO;
import nlj.business.shipper.dto.response.TransportPlanPagingResponse;
import nlj.business.shipper.dto.response.TransportPlanResponseDTO;
import nlj.business.shipper.service.TransportPlanService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 輸送計画コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(TransportPlan.PREFIX)
@RequiredArgsConstructor
public class TransportPlanController {

    private final ResponseBuilderComponent builderComponent;
    private final TransportPlanService transportPlanService;

    @Operation(summary = "輸送計画を登録", description = "輸送計画を1件登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PostMapping
    public NextResponseEntity<Void> createTransportPlan(
        @Valid @RequestBody TransportPlanRequestDTO transportPlanRequestDTO,
        HttpServletRequest request) {
        transportPlanService.createTransportPlan(transportPlanRequestDTO);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                null,
                request
            )
        );
    }

    @Operation(summary = "輸送計画一覧を取得", description = "輸送計画の一覧を取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @GetMapping
    public NextResponseEntity<TransportPlanPagingResponse> getAllTransportPlans(
        @RequestParam(name = ParamConstant.PAGE_NO, defaultValue = ParamConstant.DEFAULT_PAGE_NO) Integer page,
        @RequestParam(name = ParamConstant.LIMIT, defaultValue = ParamConstant.LIMIT_DEFAULT) Integer limit,
        @RequestParam(value = ParamConstant.TransportPlan.TRANSPORT_NAME, required = false) String transportName,
        @RequestParam(value = ParamConstant.TransportPlan.STATUSES, required = false) List<Integer> statuses,
        @RequestParam(value = ParamConstant.TransportPlan.OUTER_PACKAGE_CODE, required = false) Long outerPackageCode,
        @RequestParam(value = ParamConstant.SORT, required = false, defaultValue = ParamConstant.CREATED_DATE) String sortField,
        @RequestParam(value = ParamConstant.ORDER, required = false, defaultValue = ParamConstant.DESC) String sortOrder,
        HttpServletRequest request) {
        Sort sort = sortOrder.equalsIgnoreCase(ParamConstant.DESC) ?
            Sort.by(sortField).descending() :
            Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                transportPlanService.getAllTransportPlans(transportName, statuses, outerPackageCode, pageable),
                request
            )
        );
    }

    @Operation(summary = "輸送計画を取得", description = "指定されたIDの輸送計画を取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @GetMapping(TransportPlan.ID)
    public NextResponseEntity<TransportPlanResponseDTO> getTransportPlanById(
        @PathVariable("id") Long id,
        HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                transportPlanService.getTransportPlanById(id),
                request
            )
        );
    }

    @Operation(summary = "輸送計画を更新", description = "指定されたIDの輸送計画を更新します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PutMapping(TransportPlan.ID)
    public NextResponseEntity<Void> updateTransportPlan(
        @PathVariable("id") Long id,
        @Valid @RequestBody TransportPlanRequestDTO transportPlanRequestDTO,
        HttpServletRequest request) {
        transportPlanService.updateTransportPlan(id, transportPlanRequestDTO);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(null, request)
        );
    }

    @Operation(summary = "輸送計画を削除", description = "指定されたIDの輸送計画を削除します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @DeleteMapping(TransportPlan.ID)
    public NextResponseEntity<Void> deleteTransportPlan(
        @PathVariable("id") Long id,
        HttpServletRequest request) {
        transportPlanService.deleteTransportPlan(id);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, request));
    }
}