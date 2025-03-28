package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送業者トランザクションシッパーリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierTransactionShipperRequest {

    @JsonProperty("cns_line_item_id")
    @ValidateField(notNull = false)
    private String cnsLineItemId;

    @JsonProperty("vehicle_avb_resource_id")
    @ValidateField(notNull = false)
    private String vehicleAvbResourceId;

    @JsonProperty("cns_line_item_by_date_id")
    @ValidateField(notNull = false)
    private String cnsLineItemByDateId;

    @JsonProperty("vehicle_avb_resource_item_id")
    @ValidateField(notNull = false)
    private String vehicleAvbResourceItemId;

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("departure_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String departureDate;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("arrival_date")
    private String arrivalDate;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("giai")
    private String giai;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("isNotIX")
    private boolean isNotIX;

    @JsonProperty("matching_id")
    private String matchingId;

    @JsonProperty("negotiation")
    private CarrierNegotiationDTO negotiation;
}
