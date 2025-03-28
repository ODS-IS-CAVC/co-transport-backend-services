package jp.co.nlj.ttmi.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 運送指示DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrspIsrDTO {

    @JsonProperty("trsp_instruction_id")
    private String trspInstructionId;
}
