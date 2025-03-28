package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 納品情報通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class DelInfoNotifyDTO {

    @JsonProperty("del_note_id")
    private String delNoteId; // 納品番号

    @JsonProperty("shpm_num_id")
    private String shpmNumId; // 出荷番号

    @JsonProperty("rced_ord_num_id")
    private String rcedOrdNumId; // 受注番号
}
