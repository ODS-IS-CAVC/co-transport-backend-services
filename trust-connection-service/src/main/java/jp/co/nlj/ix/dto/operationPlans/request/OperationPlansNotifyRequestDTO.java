package jp.co.nlj.ix.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Data;

/**
 * <PRE>
 * 運送計画通知リクエストDTO。<BR>
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
