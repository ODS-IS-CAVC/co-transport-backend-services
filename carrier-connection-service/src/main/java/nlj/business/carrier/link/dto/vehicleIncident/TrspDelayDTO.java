package nlj.business.carrier.link.dto.vehicleIncident;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant.DATE_TIME_FORMAT;

/**
 * <PRE>
 * 運送遅延情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspDelayDTO {

    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT)
    @JsonProperty("trsp_eta_date")
    private String trspEtaDate;

    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS)
    @JsonProperty("trsp_eta_time")
    private String trspEtaTime;
}
