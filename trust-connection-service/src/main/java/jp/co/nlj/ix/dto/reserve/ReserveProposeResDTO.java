package jp.co.nlj.ix.dto.reserve;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 予約提案応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReserveProposeResDTO {

    @JsonProperty("trsp_plan_id")
    private String trspPlanId;

    @JsonProperty("operation_id")
    private String operationId;

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("trsp_op_date_trm_strt_date")
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_trailer_id")
    private String trspOpTrailerId;

    @JsonProperty("giai_number")
    private String giaiNumber;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("req_avb_from_time_of_cll_time")
    private String reqAvbFromTimeOfCllTime;

    @JsonProperty("req_avb_to_time_of_cll_time")
    private String reqAvbToTimeOfCllTime;

    @JsonProperty("req_freight_rate")
    private String reqFreightRate;

    @JsonProperty("shipper_cid")
    private String shipperCid;

    @JsonProperty("carrier_cid")
    private String carrierCid;
}
