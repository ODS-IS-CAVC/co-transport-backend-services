package nlj.business.shipper.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.constant.APIConstant.TransportPlanItem;
import nlj.business.shipper.constant.MessageConstant.APIResponses;
import nlj.business.shipper.constant.ParamConstant;
import nlj.business.shipper.dto.TransportPlanItemDTO;
import nlj.business.shipper.dto.request.PlanItemUpdateStatusRequestDTO;
import nlj.business.shipper.dto.response.PlanItemUpdateStatusResponse;
import nlj.business.shipper.dto.transportPlanItem.response.TransportPlanItemPagingResponse;
import nlj.business.shipper.service.TransportPlanItemService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 輸送計画アイテムコントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(TransportPlanItem.PREFIX)
@RequiredArgsConstructor
public class TransportPlanItemController {

    private final ResponseBuilderComponent builderComponent;
    private final TransportPlanItemService transportPlanItemService;

    @Operation(summary = "輸送計画アイテムを取得", description = "指定されたIDの輸送計画アイテムを取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @GetMapping(TransportPlanItem.ID)
    public NextResponseEntity<TransportPlanItemDTO> getTransportPlanItemById(
        @PathVariable("id") Long id,
        HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                transportPlanItemService.getTransportPlanItemById(id),
                request
            )
        );
    }

    @Operation(summary = "輸送計画アイテムを更新", description = "指定されたIDの輸送計画アイテムを更新します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PutMapping(TransportPlanItem.ID)
    public NextResponseEntity<Void> updateTransportPlanItem(
        @PathVariable("id") Long id,
        @Valid @RequestBody TransportPlanItemDTO transportPlanItemDTO,
        HttpServletRequest request) {
        transportPlanItemService.updateTransportPlanItem(id, transportPlanItemDTO);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(null, request)
        );
    }

    @GetMapping
    public NextResponseEntity<TransportPlanItemPagingResponse> getAllTransportPlanItems(
        @RequestParam(name = ParamConstant.PAGE_NO, defaultValue = ParamConstant.DEFAULT_PAGE_NO) Integer page,
        @RequestParam(name = ParamConstant.LIMIT, defaultValue = ParamConstant.LIMIT_DEFAULT) Integer limit,
        @RequestParam(value = ParamConstant.TransportPlan.TRANSPORT_NAME, required = false) String transportName,
        @RequestParam(value = ParamConstant.SORT, required = false, defaultValue = ParamConstant.CREATED_DATE) String sortField,
        @RequestParam(value = ParamConstant.ORDER, required = false, defaultValue = ParamConstant.DESC) String sortOrder,
        HttpServletRequest request) {
        Sort sort = sortOrder.equalsIgnoreCase(ParamConstant.DESC) ?
            Sort.by(sortField).descending() :
            Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                transportPlanItemService.searchTrspPlanItem(transportName, pageable),
                request
            )
        );
    }

    @Operation(summary = "輸送計画アイテムを更新", description = "指定されたIDの輸送計画アイテムを更新します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PutMapping(TransportPlanItem.STATUS)
    public NextResponseEntity<PlanItemUpdateStatusResponse> updateStatus(
        @Valid @RequestBody PlanItemUpdateStatusRequestDTO requestDTO,
        HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(transportPlanItemService.updateStatus(requestDTO), request)
        );
    }
}