package nlj.business.transaction.dto.matching;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nlj.business.transaction.domain.CutOffInfo;

/**
 * <PRE>
 * 配送マッチングトレーラレスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
public class TransMatchingTrailerResponse {

    @JsonProperty("temperature_range")
    private Integer[] temperatureRange;

    @JsonProperty("vehicle_diagram_item_id")
    private Long vehicleDiagramItemId;

    @JsonProperty("trans_type")
    private Long transType;

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

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("shipper_operator_id")
    private String shipperOperatorId;

    @JsonProperty("carrier_operator_id")
    private String carrierOperatorId;

    @JsonProperty("carrier2_operator_id")
    private String carrier2OperatorId;

    @JsonProperty("carrier2_operator_name")
    private String carrier2OperatorName;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("service_strt_date")
    private Date serviceStrtDate;

    @JsonProperty("service_end_date")
    private Date serviceEndDate;

    @JsonProperty("service_strt_time")
    private Time serviceStrtTime;

    @JsonProperty("service_end_time")
    private Time serviceEndTime;

    @JsonProperty("departure_time_max")
    private Time departureTimeMax;

    @JsonProperty("departure_time_min")
    private Time departureTimeMin;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;

    @JsonProperty("cut_off_info")
    private List<CutOffInfo> cutOffInfos;

    @JsonProperty("is_emergency")
    private Integer isEmergency;

    @JsonProperty("vehicle_avb_resource_item_id")
    private Long vehicleAvbResourceItemId;

    @JsonProperty("cns_line_item_by_date_id")
    private Long cnsLineItemByDateId;
}
