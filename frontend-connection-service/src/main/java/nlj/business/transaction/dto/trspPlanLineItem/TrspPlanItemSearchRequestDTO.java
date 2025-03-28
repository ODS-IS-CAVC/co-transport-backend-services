package nlj.business.transaction.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.dto.request.CommonPagingSearch;
import nlj.business.transaction.dto.shipperTrspCapacity.request.ShipperTransportCapacityAdvanceSearchDTO;

/**
 * <PRE>
 * 輸送計画明細検索リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrspPlanItemSearchRequestDTO extends CommonPagingSearch {

    private String cid;

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    private String trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    private String trspOpEndAreaLineOneTxt;

    @JsonProperty("max_trsp_op_date_trm_strt_date")
    private String maxTrspOpDateTrmStrtDate;

    @JsonProperty("min_trsp_op_date_trm_strt_date")
    private String minTrspOpDateTrmStrtDate;

    @JsonProperty("max_trsp_op_plan_date_trm_strt_time")
    private String maxTrspOpPlanDateTrmStrtTime;

    @JsonProperty("min_trsp_op_plan_date_trm_strt_time")
    private String minTrspOpPlanDateTrmStrtTime;

    @JsonProperty("advanced_conditions")
    private ShipperTransportCapacityAdvanceSearchDTO advancedSearch;
}
