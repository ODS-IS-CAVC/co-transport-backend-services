package jp.co.nlj.ttmi.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送場所DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrspLocationDTO {

    @ValidateField(minValue = -90, maxValue = 90)
    @JsonProperty("lat")
    private Double lat;

    @ValidateField(minValue = -180, maxValue = 180)
    @JsonProperty("lon")
    private Double lon;
}
