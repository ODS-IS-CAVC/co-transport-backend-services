package jp.co.nlj.ttmi.dto.vehicleIncident;


import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import jp.co.nlj.ttmi.constant.DataBaseConstant.DATE_TIME_FORMAT;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送遅延DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspDelayDTO {

    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    @JsonProperty("trsp_eta_date")
    private String trspEtaDate;

    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    @JsonProperty("trsp_eta_time")
    private String trspEtaTime;
}
