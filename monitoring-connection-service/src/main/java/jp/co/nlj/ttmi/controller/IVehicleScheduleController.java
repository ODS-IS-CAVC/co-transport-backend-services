package jp.co.nlj.ttmi.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ttmi.constant.APIConstant;
import jp.co.nlj.ttmi.constant.APIConstant.OracleApi;
import jp.co.nlj.ttmi.constant.MessageConstant.APIResponses;
import jp.co.nlj.ttmi.dto.Request.OrderTimeUpdateRequest;
import jp.co.nlj.ttmi.dto.zlWeb.ZLWebDTO;
import jp.co.nlj.ttmi.service.ShipmentTaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <PRE>
 * 車両スケジュールコントローラクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(APIConstant.OracleApi.PREFIX)
@RequiredArgsConstructor
public class IVehicleScheduleController {

    private final ResponseBuilderComponent builderComponent;
    private final ShipmentTaskListService shipmentTaskListService;

    @Operation(summary = "Insert data into ZLWeb", description = "Insert data into ZLWeb")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PostMapping(value = APIConstant.OracleApi.SEND_DATA)
    public NextResponseEntity<Void> getDeliveryAbilityItem(
        HttpServletRequest httpServletRequest,
        @RequestBody ZLWebDTO vehicleScheduleDTO) {
        shipmentTaskListService.saveSchedule(vehicleScheduleDTO);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(
                null,
                httpServletRequest)
        );
    }

    @Operation(summary = "Update time data into ZLWeb", description = "Update time data into ZLWeb")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.SUCCESS_CODE, description = APIResponses.SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.ERROR_MSG),})
    @PutMapping(value = OracleApi.UPDATE_TIME)
    public NextResponseEntity<Void> updateTime(
        HttpServletRequest httpServletRequest,
        @RequestBody OrderTimeUpdateRequest orderTimeUpdateRequest) {
        shipmentTaskListService.updateScheduleTime(orderTimeUpdateRequest);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(null, httpServletRequest));
    }
}
