package jp.co.nlj.ix.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 輸送能力ラインアイテム dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportAbilityLineItemDTO {

    @JsonProperty("cid")
    private String cid;

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
