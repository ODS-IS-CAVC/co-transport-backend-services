package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.dto.TransOrderDTO;

/**
 * <PRE>
 * 運送業者提案項目レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierProposalItemResponseDTO extends TransOrderDTO {

    @JsonProperty(DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_PRTY_NAME_TXT)
    private String trspCliPrtyNameTxt;

    @JsonProperty(DataBaseConstant.TrspPlanLineItem.CNSG_PRTY_NAME_TXT)
    private String cnsgPrtyNameTxt;

    @JsonProperty("shipper_info")
    private Object shipperInfo;

    @JsonProperty("is_request_carrier")
    private Boolean isRequestCarrier = false;

    @JsonProperty("car_info")
    private List<Map<String, Object>> carInfo;

    @JsonIgnore
    private String serviceNo;

    @JsonIgnore
    private String serviceStrtDate;

    @JsonProperty("sibling_order_status")
    private Integer siblingOrderStatus;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;

    @JsonProperty(DataBaseConstant.TransOrder.DEPARTURE_TIME)
    private Time departureTime;

    @JsonProperty(DataBaseConstant.TransOrder.ARRIVAL_TIME)
    private Time arrivalTime;
}
