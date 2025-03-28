package jp.co.nlj.ix.dto.shipperOperationPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <PRE>
 * 荷主向け運行申し込みレスポンスDTO
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipperOperationUpdateResponseDTO {

    @JsonProperty("trsp_plan_id")
    private String trspPlanId; // 内部の運送能力管理ID

    @JsonProperty("operation_id")
    private String operationId; // システム内部の運送能力管理ID

    @JsonProperty("service_no")
    private String serviceNo; // 便・ダイヤ番号

    @JsonProperty("trsp_op_date_trm_strt_date")
    private String trspOpDateTrmStrtDate; // 運行開始日

    @JsonProperty("trsp_op_trailer_id")
    private String trspOpTrailerId; // システム内部のトレーラID

    @JsonProperty("giai_number")
    private String giaiNumber; // GIAI番号

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    private String trspOpPlanDateTrmStrtTime; // 運行開始予定時刻

    @JsonProperty("req_avb_from_time_of_cll_time")
    private String reqAvbFromTimeOfCllTime; // 申し込み集荷開始時間

    @JsonProperty("req_avb_to_time_of_cll_time")
    private String reqAvbToTimeOfCllTime; // 申し込み集荷終了時間

    @JsonProperty("req_freight_rate")
    private BigDecimal reqFreightRate;  // 申し込み希望運賃
}
