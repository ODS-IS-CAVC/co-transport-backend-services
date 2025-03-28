package jp.co.nlj.ix.service;

import jp.co.nlj.ix.domain.ShipperOperator;

/**
 * <PRE>
 * シッパーコーポレートサービスインターフェース。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ShipperOperatorService {

    /**
     * 荷主オペレータを作成する。
     *
     * @param operatorId オペレータID
     * @return 作成された荷主オペレータ
     */
    ShipperOperator createShipperOperator(String operatorId);
} 