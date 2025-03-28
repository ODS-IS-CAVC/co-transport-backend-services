package nlj.business.carrier.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両情報インポートDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleInfoImportDTO {

    @JsonProperty(value = DataBaseConstant.VehicleInfoImport.NUMBER_SUCCESS)
    private Integer numberSuccess;

    @JsonProperty(value = DataBaseConstant.VehicleInfoImport.NUMBER_FAILURE)
    private Integer numberFailure;
}
