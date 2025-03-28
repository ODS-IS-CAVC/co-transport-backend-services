package nlj.business.carrier.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.VehicleInfo;
import nlj.business.carrier.constant.MessageConstant.APIResponses;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.dto.vehicleInfo.request.VehicleInfoRegisterRequest;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoListResponse;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoResponse;
import nlj.business.carrier.service.VehicleInfoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 車両情報コントローラー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(VehicleInfo.PREFIX)
@RequiredArgsConstructor
public class VehicleInfoController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleInfoService vehicleInfoService;

    @Operation(summary = "車両情報を登録する", description = "車両情報を登録する")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PostMapping(value = VehicleInfo.REGISTER)
    public NextResponseEntity<VehicleInfoResponse> register(
        @Valid @RequestParam(value = "files", required = false) List<MultipartFile> files,
        @RequestParam("data") String data,
        HttpServletRequest httpServletRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        VehicleInfoRegisterRequest request = objectMapper.readValue(data, VehicleInfoRegisterRequest.class);

        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleInfoService.register(request, files), httpServletRequest));
    }

    @Operation(summary = "車両情報を更新", description = "車両情報を更新")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PutMapping(value = VehicleInfo.UPDATE)
    public NextResponseEntity<Void> update(
        @Valid @RequestParam(value = "files", required = false) List<MultipartFile> files, @PathVariable("id") Long id,
        @RequestParam("data") String data,
        HttpServletRequest httpServletRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        VehicleInfoRegisterRequest request = objectMapper.readValue(data, VehicleInfoRegisterRequest.class);
        vehicleInfoService.update(request, id, files);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "すべての車両情報を見つける", description = "すべての車両情報を見つける")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @GetMapping(value = VehicleInfo.FIND_ALL)
    public NextResponseEntity<VehicleInfoListResponse> findAllVehicle(
        @RequestParam(value = "vehicle_type", required = false) String vehicleType,
        @RequestParam(value = "temperature_range", required = false) String temperatureRange,
        @RequestParam(value = ParamConstant.VehicleInfo.LIMIT, defaultValue = ParamConstant.VehicleInfo.LIMIT_DEFAULT) Integer limit,
        @RequestParam(value = ParamConstant.VehicleInfo.PAGE_NO, defaultValue = ParamConstant.VehicleInfo.DEFAULT_PAGE_NO) Integer page,
        HttpServletRequest httpServletRequest) {

        Pageable pageable = PageRequest.of(page - 1, limit);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleInfoService.findAll(vehicleType, temperatureRange, pageable),
                httpServletRequest));
    }

    @Operation(summary = "すべての車両情報を見つける", description = "すべての車両情報を見つける")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @GetMapping(value = VehicleInfo.DETAIL)
    public NextResponseEntity<VehicleInfoResponse> getDetail(
        @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {

        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleInfoService.getDetail(id), httpServletRequest));
    }

    @Operation(summary = "車両情報を登録する", description = "車両情報を登録する")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @PostMapping(value = VehicleInfo.IMPORT)
    public NextResponseEntity<Void> importData(@Valid @RequestParam(value = "file") MultipartFile file,
        HttpServletRequest httpServletRequest) {
        vehicleInfoService.importData(file);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "すべての車両情報を見つける", description = "すべての車両情報を見つける")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),
    })
    @DeleteMapping(value = VehicleInfo.DETAIL)
    public NextResponseEntity<Void> deleteVehicleInfo(
        @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        vehicleInfoService.deleteVehicleInfo(id);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(null, httpServletRequest));
    }
}
