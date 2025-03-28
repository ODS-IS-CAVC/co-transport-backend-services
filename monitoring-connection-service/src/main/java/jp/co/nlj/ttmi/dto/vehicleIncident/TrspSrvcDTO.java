package jp.co.nlj.ttmi.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import jp.co.nlj.ttmi.constant.DataBaseConstant.DATE_TIME_FORMAT;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送サービスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrspSrvcDTO {

    @ValidateField(trspNormalAbnormal = true)
    @JsonProperty("trsp_normal_abnm_cls_srvc_rqrm_cd")
    private String trspNormalAbnmClsSrvcRqrmCd;

    @JsonProperty("trsp_incident")
    private TrspIncidentDTO trspIncident;

    @JsonProperty("trsp_delay")
    private TrspDelayDTO trspDelay;

    @JsonProperty("trsp_vehicle_stop")
    @ValidateField(inputIsArrOrBoolean = false)
    private TrspIsVehicleStopSucceededDTO trspVehicleStop;

    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    @JsonProperty("trsp_normal_abnm_date")
    private String trspNormalAbnmDate;

    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    @JsonProperty("trsp_normal_abnm_time")
    private String trspNormalAbnmTime;

    @JsonProperty("trsp_location")
    private TrspLocationDTO trspLocation;
}
