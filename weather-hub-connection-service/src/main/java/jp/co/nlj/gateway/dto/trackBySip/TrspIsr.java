package jp.co.nlj.gateway.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.gateway.aop.proxy.ValidateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * TrspIsrクラスは、トランスポート指示DTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrspIsr {

    @JsonProperty("trsp_instruction_id")
    @ValidateField(notNull = true, maxLength = 20, textHalfWidth = true)
    private String trspInstructionId;
}
