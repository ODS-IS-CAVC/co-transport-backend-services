package jp.co.nlj.ix.dto.trspInstruction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 納品情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqDelInfoDTO {

    @ValidateField(maxLength = 23, textHalfWidth = true)
    @JsonProperty("del_note_id")
    private String delNoteId; // 納品番号

    @ValidateField(maxLength = 20, textHalfWidth = true)
    @JsonProperty("del_ctrl_num_id")
    private String delCtrlNumId;

    @ValidateField(maxLength = 20, textHalfWidth = true)
    @JsonProperty("shpm_num_id")
    private String shpmNumId;
}
