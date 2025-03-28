package nlj.business.carrier.link.dto.trspAbilityLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 車両可利用リソースDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleAvbResource {

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    @ValidateField(length = "20")
    private String trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    @ValidateField(length = "20")
    private String trspOpEndAreaLineOneTxt;

    @JsonProperty("trsp_op_date_trm_strt_date")
    @ValidateField(dateFormat = "yyyy-MM-dd", endDateField = "trspOpDateTrmEndDate", startTimeField = "trspOpPlanDateTrmStrtTime", endTimeField = "trspOpPlanDateTrmEndTime")
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    @ValidateField(timeFormat = "hh:mm")
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_date_trm_end_date")
    @ValidateField(dateFormat = "yyyy-MM-dd")
    private String trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    @ValidateField(timeFormat = "hh:mm")
    private String trspOpPlanDateTrmEndTime;
}
