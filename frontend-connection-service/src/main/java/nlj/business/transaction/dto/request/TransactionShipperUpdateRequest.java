package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送業者トランザクション更新リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionShipperUpdateRequest {

    @JsonProperty("cns_line_item_by_date_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String cnsLineItemByDateId;

    @JsonProperty("vehicle_avb_resource_item_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String vehicleAvbResourceItemId;

    @JsonProperty("service_no")
    @ValidateField(notNull = false)
    private String serviceNo;

    @JsonProperty("departure_date")
    @ValidateField(notNull = true, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String departureDate;

    @ValidateField(notNull = false)
    private String giai;

    @JsonProperty("departure_time")
    @ValidateField(notNull = true, timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String departureTime;

    @JsonProperty("collection_time_from")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String collectionTimeFrom;

    @JsonProperty("collection_time_to")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String collectionTimeTo;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String price;

    private Boolean isNotIX;
}
