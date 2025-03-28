package jp.co.nlj.ix.dto.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 配送情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class DeliveryInfoDTO {

    @JsonProperty("del_note_id")
    private String delNoteId;

    @JsonProperty("shpm_num_id")
    private String shpmNumId;

    @JsonProperty("rced_ord_num_id")
    private String rcedOrdNumId;
} 