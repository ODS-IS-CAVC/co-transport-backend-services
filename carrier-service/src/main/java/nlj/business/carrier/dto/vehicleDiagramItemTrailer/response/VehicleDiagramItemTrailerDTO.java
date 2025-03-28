package nlj.business.carrier.dto.vehicleDiagramItemTrailer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Convert;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import nlj.business.carrier.converter.CutOffPriceConverter;

/**
 * <PRE>
 * 車両図面アイテムトレーラーDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemTrailerDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("vehicle_diagram_allocation_id")
    private Long vehicleDiagramAllocationId;

    @JsonProperty("freight_rate_type")
    private Long freightRateType;

    @JsonProperty("day")
    private String day;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("cut_off_price")
    @Convert(converter = CutOffPriceConverter.class)
    private Map<String, BigDecimal> cutOffPrice;
    @JsonProperty("mobility_hub_id")
    private String mobilityHubId;
    @JsonProperty("valid_from")
    private String validFrom;
    @JsonProperty("valid_until")
    private String validUntil;
    @JsonProperty("matching_count")
    private Long matchingCount;

    @JsonProperty("total_count")
    private Long totalCount;

    @JsonProperty("matching_status")
    private Integer matchingStatus;

    @JsonProperty("order_status")
    private Integer orderStatus;

    @JsonProperty("vehicle_avb_resource_id")
    private Long vehicleAvbResourceId;

    @JsonProperty("carrier_operator_id")
    private String carrierOperatorId;

    @JsonProperty("trans_type")
    private Integer transType;

    @JsonProperty("cut_off_time")
    private BigDecimal cutOffTime;

    @JsonProperty("cut_off_fee")
    private BigDecimal cutOffFee;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("trans_order_id")
    private Long transOrderId;
}
