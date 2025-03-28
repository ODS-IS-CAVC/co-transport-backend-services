package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.sql.Time;
import lombok.Data;

/**
 * <PRE>
 * 運送業者トランザクション計画行アイテムレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlanLineItemResponseDTO {

    @JsonProperty("dsed_cll_from_date")
    private Date dsedCllFromDate;

    @JsonProperty("dsed_cll_to_date")
    private Date dsedCllToDate;

    @JsonProperty("dsed_cll_from_time")
    private Time dsedCllFromTime;

    @JsonProperty("dsed_cll_to_time")
    private Time dsedCllToTime;

    @JsonProperty("dsed_cll_time_trms_srvc_rqrm_cd")
    private String dsedCllTimeTrmsSrvcRqrmCd;

    @JsonProperty("aped_arr_from_date")
    private Date apedArrFromDate;

    @JsonProperty("aped_arr_to_date")
    private Date apedArrToDate;

    @JsonProperty("cnsg_prty_head_off_id")
    private String cnsgPrtyHeadOffId;

    public static TrspPlanLineItemResponseDTO fromResult(Object[] result) {
        TrspPlanLineItemResponseDTO dto = new TrspPlanLineItemResponseDTO();
        dto.setDsedCllFromDate((Date) result[0]);
        dto.setDsedCllToDate((Date) result[1]);
        dto.setDsedCllFromTime((Time) result[2]);
        dto.setDsedCllToTime((Time) result[3]);
        dto.setDsedCllTimeTrmsSrvcRqrmCd((String) result[4]);
        dto.setApedArrFromDate((Date) result[5]);
        dto.setApedArrToDate((Date) result[6]);
        dto.setCnsgPrtyHeadOffId((String) result[7]);
        return dto;
    }
}
