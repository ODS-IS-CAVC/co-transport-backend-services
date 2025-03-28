package nlj.business.carrier.dto.vehicleInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 利用可能な車両なし DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleNoAvailableResponseDTO {

    @JsonProperty(DataBaseConstant.VehicleNoAvailable.ID)
    private Long id;

    @JsonProperty(DataBaseConstant.VehicleNoAvailable.VEHICLE_INFO_ID)
    private Long vehicleInfoId;

    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("day_week")
    private List<Integer> dayWeek;
}
