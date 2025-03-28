package nlj.business.shipper.dto.cargoInfoImport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.shipper.aop.proxy.ValidateField;
import nlj.business.shipper.constant.DataBaseConstant;

/**
 * <PRE>
 * 荷物情報インポートDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CargoInfoImportDTO {

    @JsonProperty(value = DataBaseConstant.CargoInfoImport.FILE_PATH)
    @ValidateField(maxLength = 500)
    private String filePath;

    @JsonProperty(value = DataBaseConstant.CargoInfoImport.NUMBER_SUCCESS)
    private Integer numberSuccess;

    @JsonProperty(value = DataBaseConstant.CargoInfoImport.NUMBER_FAILURE)
    private Integer numberFailure;

    @JsonProperty(value = DataBaseConstant.CargoInfoImport.STATUS)
    private Integer status;
}
