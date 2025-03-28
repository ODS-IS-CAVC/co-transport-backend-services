package nlj.business.transaction.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 操作プランリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationPlansRequestDTO {

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    private String startAreaLineOneText;

    @JsonProperty("trsp_op_strt_area_cty_jis_cd")
    private String startAreaCityJisCode;

    @JsonProperty("trsp_op_date_trm_strt_date")
    private String startDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    private String startTime;


    @JsonProperty("trsp_op_end_area_line_one_txt")
    private String endAreaLineOneText;

    @JsonProperty("trsp_op_end_area_cty_jis_cd")
    private String endAreaCityJisCode;

    @JsonProperty("trsp_op_date_trm_end_date")
    private String endDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    private String endTime;
}
