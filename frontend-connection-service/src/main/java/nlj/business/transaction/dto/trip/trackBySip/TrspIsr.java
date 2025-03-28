package nlj.business.transaction.dto.trip.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送業者指示DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrspIsr {

    @JsonProperty("trsp_instruction_id")
    @ValidateField(notNull = true, maxLength = 20)
    private String trspInstructionId;
}
