package nlj.business.transaction.dto.operationPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 操作プランDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationPlansDTO {

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

    @JsonProperty("trsp_instruction_id")
    private String instructionId;

    @JsonProperty("shipper_cid")
    private String shipperCid;

    @JsonProperty("carrier_cid")
    private String carrierCid;

    @JsonProperty("departure_mh")
    private String departureMh;

    @JsonProperty("departure_mh_space_list")
    private String departureMhSpaceList;

    @JsonProperty("arrival_mh")
    private String arrivalMh;

    @JsonProperty("arrival_mh_space_list")
    private String arrivalMhSpaceList;

    @JsonProperty("tractor_giai")
    private String tractorGiai;

    @JsonProperty("req_from_time")
    private String reqFromTime;

    @JsonProperty("req_to_time")
    private String reqToTime;
}
