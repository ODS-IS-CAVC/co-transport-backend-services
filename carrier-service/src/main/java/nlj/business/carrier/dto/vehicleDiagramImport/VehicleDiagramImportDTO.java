package nlj.business.carrier.dto.vehicleDiagramImport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 車両図面インポートデータ転送オブジェクト。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramImportDTO {

    @JsonProperty(value = "number_success")
    private Integer numberSuccess;

    @JsonProperty(value = "number_failure")
    private Integer numberFailure;
}
