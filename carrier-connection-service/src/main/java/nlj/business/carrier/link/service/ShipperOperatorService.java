package nlj.business.carrier.link.service;

import nlj.business.carrier.link.domain.ShipperOperator;

/**
 * <PRE>
 * シッパーコーポレートサービスインターフェース.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ShipperOperatorService {

    /**
     * シッパーコーポレートを作成.<BR>
     *
     * @param operatorId オペレータID
     * @return シッパーコーポレート
     */
    ShipperOperator createShipperOperator(String operatorId);
} 