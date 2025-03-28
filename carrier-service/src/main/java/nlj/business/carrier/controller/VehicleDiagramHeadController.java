package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.VehicleDiagram;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.constant.ParamConstant.VehicleInfo;
import nlj.business.carrier.dto.semiDynamicInfo.response.ValidateSemiDynamicInfoResponseDTO;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramListResponse;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.request.VehicleDiagramHeadDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.VehicleDiagramStatusResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemByAbilityDetailsDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemByAbilityResponseDTO;
import nlj.business.carrier.service.VehicleDiagramHeadService;
import nlj.business.carrier.service.VehicleDiagramItemService;
import nlj.business.carrier.service.VehicleDiagramService;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 車両情報コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(VehicleDiagram.PREFIX)
@RequiredArgsConstructor
public class VehicleDiagramHeadController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleDiagramHeadService vehicleDiagramHeadService;
    private final VehicleDiagramService vehicleDiagramService;
    private final VehicleDiagramItemService vehicleDiagramItemService;

    @Operation(
        summary = "車両図面情報の登録",
        description = "車両図面の新規情報をシステムに登録するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PostMapping
    public NextResponseEntity<VehicleDiagramStatusResponseDTO> register(
        @Valid @RequestBody VehicleDiagramHeadDTO vehicleDiagramHeadDTO,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramHeadService.registerVehicleDiagramHead(vehicleDiagramHeadDTO),
                httpServletRequest));
    }

    @Operation(
        summary = "車両図面情報一覧の取得",
        description = "登録済みの車両図面情報の一覧を取得するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @GetMapping(value = "")
    public NextResponseEntity<VehicleDiagramListResponse> getRegisterVehicleDiagrams(
        @RequestParam(value = ParamConstant.VehicleInfo.PAGE_NO, defaultValue = VehicleInfo.DEFAULT_PAGE_NO) Integer page,
        @RequestParam(value = ParamConstant.VehicleInfo.LIMIT, defaultValue = ParamConstant.VehicleInfo.LIMIT_DEFAULT) Integer limit,
        @RequestParam(value = ParamConstant.SORT, required = false, defaultValue = ParamConstant.CREATED_DATE) String sortField,
        @RequestParam(value = ParamConstant.TRAILER, required = false, defaultValue = "true") String trailer,
        @RequestParam(value = ParamConstant.ORDER, required = false, defaultValue = ParamConstant.DESC) String sortOrder,
        @RequestParam(value = ParamConstant.TRIP_NAME, required = false) String tripName,
        @RequestParam(value = ParamConstant.START_DATE, required = false) String startDate,
        @RequestParam(value = ParamConstant.END_DATE, required = false) String endDate,
        @RequestParam(value = ParamConstant.DEPARTURE_FROM, required = false) String departureFrom,
        @RequestParam(value = ParamConstant.ARRIVAL_TO, required = false) String arrivalTo,
        HttpServletRequest httpServletRequest) {
        Sort sort = sortOrder.equalsIgnoreCase(ParamConstant.DESC) ?
            Sort.by(sortField).descending() :
            Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramService.getVehicleDiagrams(pageable,
                    Boolean.parseBoolean(trailer), tripName, startDate, endDate, departureFrom, arrivalTo),
                httpServletRequest));
    }

    @Operation(
        summary = "車両図面情報の詳細取得",
        description = "指定されたIDに基づいて車両図面情報の詳細を取得するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @GetMapping(value = "/{id}")
    public NextResponseEntity<VehicleDiagramResponseDTO> getVehicleDiagramById(
        @PathVariable("id") Long id,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramService.getVehicleDiagramById(id, true),
                httpServletRequest));
    }

    @Operation(
        summary = "車両図面情報の更新",
        description = "指定されたIDの車両図面情報を更新するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping(value = "/{id}")
    public NextResponseEntity<VehicleDiagramStatusResponseDTO> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody VehicleDiagramHeadDTO vehicleDiagramHeadDTO,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramService.updateVehicleDiagram(id, vehicleDiagramHeadDTO)
                , httpServletRequest));
    }

    @Operation(
        summary = "車両図面情報の登録",
        description = "車両図面の新規情報をシステムに登録するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PostMapping(value = "/delivery-ability-bulk")
    public NextResponseEntity<Void> importData(@Valid @RequestParam("file") MultipartFile file,
        HttpServletRequest httpServletRequest) {
        vehicleDiagramHeadService.importData(file);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "Get items by delivery ability", description = "Get vehicle diagram items")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @GetMapping(value = "/{abilityId}/items")
    public NextResponseEntity<VehicleDiagramItemByAbilityResponseDTO> getItemsByAbility(
        @PathVariable("abilityId") Long abilityId,
        @RequestParam(value = ParamConstant.VehicleInfo.LIMIT, defaultValue = ParamConstant.VehicleInfo.LIMIT_DEFAULT) Integer limit,
        @RequestParam(value = ParamConstant.VehicleInfo.PAGE_NO, defaultValue = ParamConstant.VehicleInfo.DEFAULT_PAGE_NO) Integer page,
        @RequestParam(value = "sortField", required = false, defaultValue = "departureTime") String sortField,
        @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
        @RequestParam(value = "start_date", required = false) String minStartDate,
        @RequestParam(value = "end_date", required = false) String maxStartDate,
        @RequestParam(value = "trip_name", required = false) String tripName,
        @RequestParam(value = "status", required = false) String statusList,
        @RequestParam(value = "temperature_range", required = false) Integer temperatureRange,
        HttpServletRequest httpServletRequest) {
        Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() :
            Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return new NextResponseEntity<>(builderComponent.buildResponse(
            vehicleDiagramService.getVehicleDiagramByAbility(abilityId, minStartDate, maxStartDate, tripName,
                statusList, temperatureRange, pageable, httpServletRequest), httpServletRequest));
    }

    @Operation(
        summary = "車両図面情報の更新",
        description = "指定されたIDの車両図面情報を更新するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @GetMapping(value = "/item/{id}")
    public NextResponseEntity<VehicleDiagramItemByAbilityDetailsDTO> getItemDetail(
        @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        String binName = httpServletRequest.getParameter("binName");
        String transportDate = httpServletRequest.getParameter("transportDate");
        return new NextResponseEntity<>(builderComponent.buildResponse(
            vehicleDiagramItemService.getDetail(id, binName, transportDate, httpServletRequest), httpServletRequest));
    }

    @Operation(
        summary = "車両図面情報の更新",
        description = "指定されたIDの車両図面情報を更新するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping(value = "/item/{id}")
    public NextResponseEntity<ValidateSemiDynamicInfoResponseDTO> updateVehicleItem(
        @PathVariable("id") Long id, @Valid @RequestBody VehicleDiagramItemByAbilityDetailsDTO dto,
        HttpServletRequest httpServletRequest) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleDiagramItemService.updateVehicleItem(id, dto), httpServletRequest));
    }

    @Operation(
        summary = "車両図面情報の更新",
        description = "指定されたIDの車両図面情報を更新するAPI"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @DeleteMapping(value = "/item/{id}")
    public NextResponseEntity<Void> deleteVehicleItem(
        @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        vehicleDiagramItemService.deleteVehicleItem(id);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

}
