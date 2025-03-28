package nlj.business.transaction.dto.trspPlanLineItem.ix;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class DelInfoDTO {

    //@ValidateField(maxLength = 23, textHalfWidth = true)
    @JsonProperty("del_note_id")
    private String delNoteId; // 納品番号

    //@ValidateField(maxLength = 20, textHalfWidth = true)
    @JsonProperty("shpm_num_id")
    private String shpmNumId; // 出荷番号

    //@ValidateField(maxLength = 23, textHalfWidth = true)
    @JsonProperty("rced_ord_num_id")
    private String rcedOrdNumId; // 受注番号
}
