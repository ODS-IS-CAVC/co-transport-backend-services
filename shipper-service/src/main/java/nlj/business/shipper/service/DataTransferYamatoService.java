package nlj.business.shipper.service;

import java.util.List;
import nlj.business.shipper.domain.TransportPlan;
import nlj.business.shipper.domain.TransportPlanItem;

/**
 * <PRE>
 * ヤマトデータ転送サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface DataTransferYamatoService {

    void transferDataToYamato(TransportPlan transportPlan, List<TransportPlanItem> transportPlanItems);

    void transferDataToYamatoUpdate(TransportPlan transportPlan, List<TransportPlanItem> transportPlanItems);
}
