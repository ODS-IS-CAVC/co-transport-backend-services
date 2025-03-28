package nlj.business.carrier.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.carrier.aop.proxy.ValidateField;

/**
 * <PRE>
 * 輸送指示.<BR>
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
