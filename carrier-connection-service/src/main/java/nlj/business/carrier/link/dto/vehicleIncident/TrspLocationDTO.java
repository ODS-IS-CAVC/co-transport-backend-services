package nlj.business.carrier.link.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送場所情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspLocationDTO {

    @ValidateField(minValue = -90, maxValue = 90)
    @JsonProperty("lat")
    private Double lat;

    @ValidateField(minValue = -180, maxValue = 180)
    @JsonProperty("lon")
    private Double lon;
}
