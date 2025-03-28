package jp.co.nlj.ix.dto.operationPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.dto.operationPlans.request.CarInfoNotifyDTO;
import jp.co.nlj.ix.dto.operationPlans.request.DriverInformationNotifyDTO;
import jp.co.nlj.ix.dto.operationPlans.request.LogisticsServiceProviderNotifyDTO;
import jp.co.nlj.ix.dto.operationPlans.request.RoadCarrNotifyDTO;
import lombok.Data;

/**
 * <PRE>
 * 運送計画通知DTO。<BR>
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
