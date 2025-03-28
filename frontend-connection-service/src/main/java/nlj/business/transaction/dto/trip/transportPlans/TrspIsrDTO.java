package nlj.business.transaction.dto.trip.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送業者指示DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspIsrDTO {

    @JsonProperty("trsp_instruction_id")
    private String trspInstructionId;

    @JsonProperty("trsp_instruction_date_subm_dttm")
    private String trspInstructionDateSubmDttm;

    @JsonProperty("inv_num_id")
    private String invNumId;

    @JsonProperty("cmn_inv_num_id")
    private String cmnInvNumId;
} 