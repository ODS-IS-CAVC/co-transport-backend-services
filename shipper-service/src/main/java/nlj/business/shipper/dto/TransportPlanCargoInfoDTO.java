package nlj.business.shipper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import nlj.business.shipper.aop.proxy.ValidateField;

/**
 * <PRE>
 * 輸送計画DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanCargoInfoDTO {

    @JsonProperty("price_per_unit")
    @ValidateField(precision = 9, scale = 0)
    private Long pricePerUnit;

    @JsonProperty("outer_package_code")
    private Long outerPackageCode;

    @JsonProperty("cargo_info_id")
    private Long cargoInfoId;

    @JsonProperty("cargo_name")
    private String cargoName;

    @JsonProperty("total_length")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalWidth;

    @JsonProperty("total_height")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalHeight;

    @JsonProperty("total_weight")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalWeight;

    @JsonProperty("temp_range")
    private List<Integer> tempRange;

    @JsonProperty("special_instructions")
    @ValidateField(maxLength = 500)
    private String specialInstructions;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("id")
    private Long id;
}