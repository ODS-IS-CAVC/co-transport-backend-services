package nlj.business.carrier.scheduler;

import nlj.business.carrier.service.VehicleDiagramItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * タスクスケジューラ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
public class CarrierTaskScheduler {

    private final VehicleDiagramItemService vehicleDiagramItemService;

    @Autowired
    public CarrierTaskScheduler(VehicleDiagramItemService vehicleDiagramItemService) {
        this.vehicleDiagramItemService = vehicleDiagramItemService;
    }
}
