package nlj.business.carrier.link.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送事故情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspIsrDTO {

    @JsonProperty("trsp_instruction_id")
    @ValidateField(notNull = true, minLength = 1, maxLength = 20)
    private String trspInstructionId;
}
