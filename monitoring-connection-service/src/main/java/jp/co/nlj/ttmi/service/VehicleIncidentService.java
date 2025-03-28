package jp.co.nlj.ttmi.service;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.ttmi.dto.vehicleIncident.request.TTMIVehicleIncidentInfoRequestDTO;

/**
 * <PRE>
 * 車両事故サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleIncidentService {

    void registerVehicleIncident(HttpServletRequest servletRequest,
        TTMIVehicleIncidentInfoRequestDTO vehicleIncidentInfoRequestDTO);
}
