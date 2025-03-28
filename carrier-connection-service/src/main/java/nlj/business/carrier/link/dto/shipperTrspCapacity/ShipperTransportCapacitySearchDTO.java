package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 荷送人輸送能力検索 dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipperTransportCapacitySearchDTO {

    @JsonProperty("service_no")
    @ValidateField(maxLength = 20, textHalfWidth = true)
    private String serviceNo;

    @JsonProperty("service_name")
    @ValidateField(maxLength = 24, textFullWidth = true)
    private String serviceName;

    @JsonProperty("car_max_load_capacity1_meas")
    @ValidateField(precision = 6, scale = 0, textHalfWidth = true)
    private String carMaxLoadCapacity1Meas;

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String trspOpEndAreaLineOneTxt;

    @JsonProperty("max_trsp_op_date_trm_strt_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String maxTrspOpDateTrmStrtDate;

    @JsonProperty("min_trsp_op_date_trm_strt_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String minTrspOpDateTrmStrtDate;

    @JsonProperty("max_trsp_op_date_trm_end_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String maxTrspOpDateTrmEndDate;

    @JsonProperty("min_trsp_op_date_trm_end_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String minTrspOpDateTrmEndDate;

    @JsonProperty("max_trsp_op_plan_date_trm_strt_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String maxTrspOpPlanDateTrmStrtTime;

    @JsonProperty("min_trsp_op_plan_date_trm_strt_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String minTrspOpPlanDateTrmStrtTime;

    @JsonProperty("max_trsp_op_plan_date_trm_end_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String maxTrspOpPlanDateTrmEndTime;

    @JsonProperty("min_trsp_op_plan_date_trm_end_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, textHalfWidth = true)
    private String minTrspOpPlanDateTrmEndTime;
}
