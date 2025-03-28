package nlj.business.carrier.link.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * モタス情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class MotasInfoDTO {

    @JsonProperty("max_payload_1")
    @ValidateField(notNull = true, maxLength = 6, integerFormat = true, minValue = 1, textHalfWidth = true)
    private Number maxPayload1;

    @JsonProperty("max_payload_2")
    @ValidateField(notNull = true, maxLength = 5, integerFormat = true, minValue = 1, textHalfWidth = true)
    private Number maxPayload2;

    @JsonProperty("vehicle_weight")
    @ValidateField(notNull = true, maxLength = 5, integerFormat = true, minValue = 1, textHalfWidth = true)
    private Number vehicleWeight;

    @JsonProperty("vehicle_length")
    @ValidateField(notNull = true, maxLength = 4, integerFormat = true, minValue = 1, textHalfWidth = true)
    private Number vehicleLength;

    @JsonProperty("vehicle_width")
    @ValidateField(notNull = true, maxLength = 3, integerFormat = true, minValue = 1, textHalfWidth = true)
    private Number vehicleWidth;

    @JsonProperty("vehicle_height")
    @ValidateField(notNull = true, maxLength = 3, integerFormat = true, minValue = 1, textHalfWidth = true)
    private Number vehicleHeight;

    @JsonProperty("hazardous_material_volume")
    @ValidateField(maxLength = 5, integerFormat = true, textHalfWidth = true)
    private Number hazardousMaterialVolume;

    @JsonProperty("hazardous_material_specific_gravity")
    @ValidateField(precision = 5, scale = 3, textHalfWidth = true)
    private BigDecimal hazardousMaterialSpecificGravity;

    @JsonProperty("expiry_date")
    @ValidateField(notNull = true, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String expiryDate;

    @JsonProperty("deregistration_status")
    @ValidateField(maxLength = 1, textHalfWidth = true, textContainSpecialChar = true)
    private String deregistrationStatus;
}
