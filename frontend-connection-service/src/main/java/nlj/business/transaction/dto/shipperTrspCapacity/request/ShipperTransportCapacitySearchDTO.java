package nlj.business.transaction.dto.shipperTrspCapacity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 運送業者運送能力検索DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipperTransportCapacitySearchDTO {

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    private String trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    private String trspOpEndAreaLineOneTxt;

    @JsonProperty("max_trsp_op_date_trm_strt_date")
    private String maxTrspOpDateTrmStrtDate;

    @JsonProperty("min_trsp_op_date_trm_strt_date")
    private String minTrspOpDateTrmStrtDate;

    @JsonProperty("max_trsp_op_date_trm_end_date")
    private String maxTrspOpDateTrmEndDate;

    @JsonProperty("min_trsp_op_date_trm_end_date")
    private String minTrspOpDateTrmEndDate;

    @JsonProperty("max_trsp_op_plan_date_trm_strt_time")
    private String maxTrspOpPlanDateTrmStrtTime;

    @JsonProperty("min_trsp_op_plan_date_trm_strt_time")
    private String minTrspOpPlanDateTrmStrtTime;

    @JsonProperty("max_trsp_op_plan_date_trm_end_time")
    private String maxTrspOpPlanDateTrmEndTime;

    @JsonProperty("min_trsp_op_plan_date_trm_end_time")
    private String minTrspOpPlanDateTrmEndTime;

    @JsonProperty("advanced")
    private ShipperTransportCapacityAdvanceSearchDTO advancedSearch;
}
