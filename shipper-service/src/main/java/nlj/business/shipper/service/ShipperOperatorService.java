package nlj.business.shipper.service;

import nlj.business.shipper.domain.ShipperOperator;

/**
 * <PRE>
 * シッパーコーポレートサービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ShipperOperatorService {

    ShipperOperator createShipperOperator(String operatorId);
} 