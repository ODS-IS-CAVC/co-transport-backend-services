package nlj.business.carrier.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Convert;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant.DATE_TIME_FORMAT;

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
public class VehicleNoAvailableDTO {

    @JsonProperty("start_date")
    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT)
    private String startDate;
    @JsonProperty("end_date")
    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT)
    private String endDate;

    @JsonProperty("status")
    @ValidateField(statusVehicleDiagram = true)
    private Integer status;
    @JsonProperty("day_week")
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> dayWeek;
}
