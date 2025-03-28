package nlj.business.transaction.dto.matching;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * トレーラプランレスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrspPlanTrailerResponse {

    @JsonProperty("service_strt_time")
    private String serviceStrtTime;

    @JsonProperty("service_end_time")
    private String serviceEndTime;

    @JsonProperty("temperature_range")
    private Integer[] temperatureRange;

    @JsonProperty("trans_type")
    private Integer transType;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("matching_id")
    private Long matchingId;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;

    @JsonProperty("price")
    private String price;

    @JsonProperty("cut_off_time")
    private String cutOffTime;

    @JsonProperty("matching_type")
    private String matchingType;

    @JsonProperty("matching_status")
    private String matchingStatus;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("total_count")
    private String totalCount;

    @JsonProperty("vehicle_avb_resource_id")
    private String vehicleAvbResourceId;

    @JsonProperty("created_at")
    private String createdAt;
}
