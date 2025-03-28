package nlj.business.shipper.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.constant.APIConstant.CargoInfo;
import nlj.business.shipper.constant.MessageConstant.APIResponses;
import nlj.business.shipper.constant.ParamConstant;
import nlj.business.shipper.dto.cargoInfo.CargoInfoResponseDTO;
import nlj.business.shipper.dto.cargoInfo.request.CargoInfoRequest;
import nlj.business.shipper.dto.cargoInfo.response.CargoInfoSearchResponse;
import nlj.business.shipper.service.CargoInfoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * 輸送計画コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(CargoInfo.PREFIX)
@RequiredArgsConstructor
public class CargoInfoController {

    private final ResponseBuilderComponent builderComponent;
    private final CargoInfoService cargoInfoService;

    @Operation(summary = "輸送計画を登録", description = "輸送計画を1件登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PostMapping
    public NextResponseEntity<CargoInfoResponseDTO> createCargoInfo(
        @Valid @RequestBody CargoInfoRequest cargoInfoRequest, HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(cargoInfoService.createCargoInfo(cargoInfoRequest, null), request));
    }

    @Operation(summary = "輸送計画を登録", description = "輸送計画を1件登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @PutMapping(value = CargoInfo.ID)
    public NextResponseEntity<Void> updateCargoInfo(@Valid @RequestBody CargoInfoRequest cargoInfoRequest,
        @PathVariable("id") Long id, HttpServletRequest request) {
        cargoInfoService.updateCargoInfo(cargoInfoRequest, id);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, request));
    }

    @Operation(summary = "輸送計画を登録", description = "輸送計画を1件登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @GetMapping(value = CargoInfo.ID)
    public NextResponseEntity<CargoInfoResponseDTO> getDetail(@PathVariable("id") Long id, HttpServletRequest request) {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(cargoInfoService.getDetailCargoInfo(id), request));
    }

    @Operation(summary = "輸送計画を登録", description = "輸送計画を1件登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG)
    })
    @GetMapping
    public NextResponseEntity<CargoInfoSearchResponse> searchCargoInfo(
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = ParamConstant.CargoInfo.LIMIT, defaultValue = ParamConstant.CargoInfo.LIMIT_DEFAULT) Integer limit,
        @RequestParam(value = ParamConstant.CargoInfo.PAGE_NO, defaultValue = ParamConstant.CargoInfo.DEFAULT_PAGE_NO) Integer page,
        HttpServletRequest httpServletRequest) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(cargoInfoService.search(status, pageable), httpServletRequest));
    }

    @Operation(summary = "車両情報を登録する", description = "車両情報を登録する")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PostMapping(value = CargoInfo.IMPORT)
    public NextResponseEntity<Void> importData(@Valid @RequestParam("file") MultipartFile file,
        HttpServletRequest httpServletRequest) {
        cargoInfoService.importData(file);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @DeleteMapping(value = CargoInfo.ID)
    public NextResponseEntity<Void> deleteCargoInfo(@Valid @PathVariable("id") Long id,
        HttpServletRequest httpServletRequest) {
        cargoInfoService.deleteCargoInfo(id);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

}