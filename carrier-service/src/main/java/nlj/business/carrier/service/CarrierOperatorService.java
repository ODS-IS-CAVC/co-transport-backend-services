package nlj.business.carrier.service;

import nlj.business.carrier.domain.CarrierOperator;

/**
 * <PRE>
 * シッパーコーポレートサービスインターフェース。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CarrierOperatorService {

    /**
     * キャリアオペレーターを作成する。
     *
     * @param operatorId オペレーターID
     * @return キャリアオペレーター
     */
    CarrierOperator createCarrierOperator(String operatorId);
}
