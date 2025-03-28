package jp.co.nlj.ix.dto.operationNotify.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.dto.shipperTrspCapacity.CarInfoDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.DriverInformationDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.LogisticsServiceProviderDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.RoadCarrDTO;
import lombok.Data;

/**
 * <PRE>
 * 運送計画通知リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Data
public class OperationNotifyRequestDTO {

    @JsonProperty("road_carr")
    @ValidateField(notNull = true)
    private RoadCarrDTO roadCarrDTO;

    @JsonProperty("logs_srvc_prv")
    private LogisticsServiceProviderDTO logisticsServiceProviderDTO;

    @JsonProperty("car_info")
    @ValidateField(notNull = true)
    private Set<CarInfoDTO> carInfoDTOS;

    @JsonProperty("drv_info")
    private Set<DriverInformationDTO> driverInformationDTOS;
}
