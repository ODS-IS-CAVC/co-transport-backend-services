package nlj.business.transaction.dto.trip.vehicleAvbResource.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 車両可用性リソースリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleAvbResourceRequestDTO {

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    private String trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_strt_area_cty_jis_cd")
    private String trspOpStrtAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_strt_date")
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    private String trspOpEndAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_cty_jis_cd")
    private String trspOpEndAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_end_date")
    private String trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    private String trspOpPlanDateTrmEndTime;
}
