package jp.co.nlj.ttmi.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import lombok.Data;

/**
 * <PRE>
 * 運送指示DTO。<BR>
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
