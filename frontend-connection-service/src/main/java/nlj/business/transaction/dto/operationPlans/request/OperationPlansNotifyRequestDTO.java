package nlj.business.transaction.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 操作プラン通知リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationPlansNotifyRequestDTO {

    @JsonProperty("road_carr")
    @ValidateField(notNull = true)
    private RoadCarrNotifyDTO roadCarrDTO;

    @JsonProperty("logs_srvc_prv")
    private LogisticsServiceProviderNotifyDTO logisticsServiceProviderDTO;

    @JsonProperty("car_info")
    @ValidateField(notNull = true)
    private Set<CarInfoNotifyDTO> carInfoDTOS;

    @JsonProperty("drv_info")
    private Set<DriverInformationNotifyDTO> driverInformationDTOS;
}
