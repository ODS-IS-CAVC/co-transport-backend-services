package nlj.business.carrier.link.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.NextResponseEntity;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.link.constant.APIConstant;
import nlj.business.carrier.link.constant.APIConstant.VehicleInfo;
import nlj.business.carrier.link.constant.APIConstant.VehicleSearchParam;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.MessageConstant.APIResponses;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.commonBody.response.CommonResponseDTO;
import nlj.business.carrier.link.service.VehicleInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 車両情報コントローラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@RestController
@RequestMapping({VehicleInfo.PREFIX, VehicleInfo.DATA_CHANNEL_PREFIX})
@RequiredArgsConstructor
public class VehicleInfoController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleInfoService vehicleInfoService;

    @Operation(summary = "指定した車輌種別の車輛マスタを取得", description = "指定した車輌種別に基づいて、車輛マスタを取得します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.GET_VEHICLE_SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.SERVER_ERROR_MSG_JA)
    })
    @GetMapping
    public NextResponseEntity<CommonResponseDTO> searchVehicle(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        @RequestParam(name = VehicleSearchParam.VEHICLE_TYPE) String vehicleType,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) {
        log.info("GET /vehicle Request Body: vehicle_type {}", vehicleType);
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleInfoService.searchVehicleByVehicleType(vehicleType),
                httpServletRequest));
    }

    @Operation(summary = "車輛マスタを登録", description = "車輛マスタを登録します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.REGISTER_VEHICLE_SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.SERVER_ERROR_MSG_JA)
    })
    @PutMapping
    public NextResponseEntity<Void> registerOrUpdateVehicle(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        @Valid @RequestBody CommonRequestDTO commonRequestDTO,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) {
        log.info("PUT /vehicle Request Body: {}", commonRequestDTO);
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, String.valueOf(xTracking));
        vehicleInfoService.registerOrUpdateVehicleInfo(commonRequestDTO);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "車輛マスタを更新", description = "車輛マスタを削除します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.REQUEST_SUCCESS_CODE, description = APIResponses.DELETE_VEHICLE_SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.SERVER_ERROR_MSG_JA)
    })
    @DeleteMapping
    public NextResponseEntity<Void> deleteVehicle(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        @RequestParam(value = ParamConstant.GIAI) String giai,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) {
        log.info("DELETE /vehicle Request Body: {}", giai);
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        vehicleInfoService.deleteVehicle(giai);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }
}
