package nlj.business.transaction.dto.operationPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.dto.operationPlans.request.CarInfoNotifyDTO;
import nlj.business.transaction.dto.operationPlans.request.DriverInformationNotifyDTO;
import nlj.business.transaction.dto.operationPlans.request.LogisticsServiceProviderNotifyDTO;
import nlj.business.transaction.dto.operationPlans.request.RoadCarrNotifyDTO;

/**
 * <PRE>
 * 操作プラン通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationPlansNotifyDTO {

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
    @JsonProperty("shipper_cid")
    private String shipperCid;
    @JsonProperty("carrier_cid")
    private String carrierCid;
}
