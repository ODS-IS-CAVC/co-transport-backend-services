package nlj.business.carrier.link.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 最大積載量DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class MaxCarryingCapacityDTO {

    @JsonProperty("package_code")
    @ValidateField(maxLength = 3, textHalfWidth = true, packageCode = true)
    private String packageCode;

    @JsonProperty("package_name_kanji")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String packageNameKanji;

    @JsonProperty("width")
    @ValidateField(precision = 15, scale = 6, textHalfWidth = true)
    private BigDecimal width;

    @JsonProperty("height")
    @ValidateField(precision = 15, scale = 6, textHalfWidth = true)
    private BigDecimal height;

    @JsonProperty("depth")
    @ValidateField(precision = 15, scale = 6, textHalfWidth = true)
    private BigDecimal depth;

    @JsonProperty("dimension_unit_code")
    @ValidateField(textHalfWidth = true, dimensionCode = true)
    private String dimensionUnitCode;

    @JsonProperty("max_load_quantity")
    @ValidateField(maxLength = 2, integerFormat = true, textHalfWidth = true)
    private Number maxLoadQuantity;
}
