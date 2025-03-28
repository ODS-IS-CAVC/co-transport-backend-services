package nlj.business.carrier.link.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.NextResponseEntity;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.link.constant.APIConstant;
import nlj.business.carrier.link.constant.APIConstant.CarrierTransportCapacity;
import nlj.business.carrier.link.constant.APIConstant.ShipperTransportCapacity;
import nlj.business.carrier.link.constant.APIConstant.TrspPlanLineItem;
import nlj.business.carrier.link.constant.APIConstant.VehicleInfo;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.MessageConstant.APIResponses;
import nlj.business.carrier.link.constant.MessageConstant.System;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacityRegisterDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemDeleteRequest;
import nlj.business.carrier.link.service.TransportAbilityMessageInfoService;
import nlj.business.carrier.link.service.TrspPlanLineItemService;
import nlj.business.carrier.link.service.VehicleInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequiredArgsConstructor
public class DeleteDataChannelController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleInfoService vehicleInfoService;

    private final TransportAbilityMessageInfoService transportAbilityMessageInfoService;

    private final TrspPlanLineItemService trspPlanLineItemService;

    @Operation(summary = "車輛マスタを更新", description = "車輛マスタを更新します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIResponses.REQUEST_SUCCESS_CODE, description = APIResponses.DELETE_VEHICLE_SUCCESS_MSG),
        @ApiResponse(responseCode = APIResponses.BAD_REQUEST_CODE, description = APIResponses.BAD_REQUEST_MSG_JA),
        @ApiResponse(responseCode = APIResponses.ERROR_CODE, description = APIResponses.SERVER_ERROR_MSG_JA)
    })
    @PutMapping(VehicleInfo.DATA_CHANNEL_DELETE)
    public NextResponseEntity<Void> deleteVehicle(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        @Valid @RequestBody CommonRequestDTO commonRequestDTO,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) {
        log.info("PUT /vehicle_del Request Body: {}", commonRequestDTO);
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, String.valueOf(xTracking));
        vehicleInfoService.delete(commonRequestDTO);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest), HttpStatus.OK);
    }

    @Operation(summary = ShipperTransportCapacity.DELETE_SUMMARY, description = ShipperTransportCapacity.DELETE_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = APIConstant.STATUS_OK_CODE, description = ShipperTransportCapacity.DELETE_STATUS_OK_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_BAD_REQUEST_CODE, description = APIConstant.STATUS_BAD_REQUEST_DESCRIPTION),
        @ApiResponse(responseCode = APIConstant.STATUS_SERVER_ERROR_CODE, description = APIConstant.STATUS_SERVER_ERROR_DESCRIPTION)
    })
    @PutMapping(ShipperTransportCapacity.DATA_CHANNEL_DELETE)
    public NextResponseEntity<Object> delete(
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        @Valid @RequestBody ShipperTransportCapacityRegisterDTO shipperTransportCapacityRegisterDTO,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        log.info("PUT /shipper_trans_capacity_del Request Body: {}", shipperTransportCapacityRegisterDTO);
        if (!BaseUtil.isNull(xTracking)) {
            try {
                UUID.fromString(xTracking);
            } catch (Exception e) {
                throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                    MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
            }
        }
        transportAbilityMessageInfoService.deleteTransportInfor(shipperTransportCapacityRegisterDTO);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(ShipperTransportCapacity.DELETE_STATUS_OK_DESCRIPTION, httpServletRequest));
    }

    @Operation(summary = "運送計画情報（明細型）を削除", description = "運送計画情報（明細型）を削除します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "運送能力情報（明細型）を正常に削除しました。"),
        @ApiResponse(responseCode = "400", description = "リクエスト自体に問題がある場合"),
        @ApiResponse(responseCode = "500", description = "システムの内部にてエラーが発生している場合")
    })
    @PutMapping(CarrierTransportCapacity.DATA_CHANNEL_DELETE)
    public NextResponseEntity<Void> delete(
        @Valid @RequestBody CommonRequestDTO commonRequestDTO,
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse,
        HttpServletRequest httpServletRequest) {
        log.info("PUT /carrier_trans_capacity_del Request Body: {}", commonRequestDTO);
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TrspPlanLineItemDeleteRequest trspPlanLineItemDeleteRequest = new TrspPlanLineItemDeleteRequest();
        try {
            trspPlanLineItemDeleteRequest = objectMapper.convertValue(commonRequestDTO.getAttribute(),
                TrspPlanLineItemDeleteRequest.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        trspPlanLineItemService.deleteTrspPlan(trspPlanLineItemDeleteRequest, xTracking);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }

    @Operation(summary = "運送計画情報（明細型）を削除", description = "運送計画情報（明細型）を更新します。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "運送能力情報（明細型）を正常に削除しました。"),
        @ApiResponse(responseCode = "400", description = "運送能力情報（明細型）が見つからない場合のレスポンス"),
        @ApiResponse(responseCode = "401", description = "認証エラーに対するレスポンス"),
        @ApiResponse(responseCode = "500", description = "サーバーエラーに対するレスポンス")
    })
    @PutMapping(TrspPlanLineItem.DATA_CHANNEL_DELETE)
    public NextResponseEntity<Void> deleteTrspPlan(
        @Valid @RequestBody CommonRequestDTO commonRequestDTO,
        @RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse,
        HttpServletRequest httpServletRequest) {
        log.info("PUT /carrier_trans_request_del Request Body: {}", commonRequestDTO);
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                MessageConstant.Validate.VALID_XTRACKING_FORMAT)));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TrspPlanLineItemDeleteRequest trspPlanLineItemRegisterRequest = new TrspPlanLineItemDeleteRequest();
        try {
            trspPlanLineItemRegisterRequest = objectMapper.convertValue(commonRequestDTO.getAttribute(),
                TrspPlanLineItemDeleteRequest.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
        }
        httpServletResponse.addHeader(APIConstant.X_TRACKING, xTracking);
        trspPlanLineItemService.deleteTrspPlan(trspPlanLineItemRegisterRequest, xTracking);
        return new NextResponseEntity<>(builderComponent.buildResponse(null, httpServletRequest));
    }
}

