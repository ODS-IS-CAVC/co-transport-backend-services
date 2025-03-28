package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * シッパー交渉リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipperNegotiationDTO {

    @JsonProperty("departure_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String departureDate;

    @JsonProperty("arrival_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String arrivalDate;

    @JsonProperty("cut_off_time")
    @ValidateField(precision = Integer.MAX_VALUE, scale = 1, positiveNumber = false, greaterThanOrEqualZero = true)
    private String cutOffTime;

    @JsonProperty("cut_off_fee")
    @ValidateField(precision = Integer.MAX_VALUE, scale = 0, positiveNumber = false)
    private String cutOffFee;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String price;

    @JsonProperty("collection_time_from")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String collectionTimeFrom;

    @JsonProperty("collection_time_to")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String collectionTimeTo;

    private String comment;
}
