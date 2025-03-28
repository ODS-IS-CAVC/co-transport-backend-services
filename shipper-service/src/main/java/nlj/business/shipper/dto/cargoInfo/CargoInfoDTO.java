package nlj.business.shipper.dto.cargoInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import nlj.business.shipper.aop.proxy.ValidateField;
import nlj.business.shipper.constant.DataBaseConstant;

/**
 * <PRE>
 * 荷物情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CargoInfoDTO {

    @JsonProperty(DataBaseConstant.CargoInfo.CARGO_NAME)
    @ValidateField(notNull = true, maxLength = 20)
    private String cargoName;

    @JsonProperty(DataBaseConstant.CargoInfo.OUTER_PACKAGE_CODE)
    @ValidateField(notNull = true)
    private Integer outerPackageCode;

    @JsonProperty(DataBaseConstant.CargoInfo.TOTAL_LENGTH)
    @ValidateField(precision = 9, scale = 3)
    private BigDecimal totalLength;

    @JsonProperty(DataBaseConstant.CargoInfo.TOTAL_WIDTH)
    @ValidateField(precision = 9, scale = 3)
    private BigDecimal totalWidth;

    @JsonProperty(DataBaseConstant.CargoInfo.TOTAL_HEIGHT)
    @ValidateField(precision = 9, scale = 3)
    private BigDecimal totalHeight;

    @JsonProperty(DataBaseConstant.CargoInfo.WEIGHT)
    @ValidateField(precision = 9, scale = 3)
    private BigDecimal weight;

    @JsonProperty(DataBaseConstant.CargoInfo.TEMP_RANGE)
    private List<Integer> tempRange;

    @JsonProperty(DataBaseConstant.CargoInfo.SPECIAL_INSTRUCTIONS)
    @ValidateField(maxLength = 100)
    private String specialInstructions;

    @JsonProperty(DataBaseConstant.CargoInfo.IMPORT_ID)
    private Long importId;

    @JsonProperty(DataBaseConstant.CargoInfo.STATUS)
    @ValidateField(cargoStatus = true)
    private Integer status = 0;
}
