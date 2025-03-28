package jp.co.nlj.gateway.service;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.gateway.dto.vehicleIncidentInfo.request.VehicleIncidentInfoRequestDTO;

/**
 * <PRE>
 * VehicleIncidentInfoServiceインターフェースは、車両事故情報サービスを定義するためのインターフェースです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleIncidentInfoService {

    /**
     * 車両事故情報を登録するメソッド。
     *
     * @param vehicleIncidentInfo 車両事故情報リクエストDTO
     * @param httpServletRequest  HTTPリクエスト
     */
    void registerVehicleIncidentInfo(VehicleIncidentInfoRequestDTO vehicleIncidentInfo,
        HttpServletRequest httpServletRequest);
}
