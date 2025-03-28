package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant.DATE_TIME_FORMAT;

/**
 * <PRE>
 * 運送依頼削除DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspIsrDeleteDTO {

    @JsonProperty("trsp_instruction_id")
    @ValidateField(notNull = true, maxLength = 20, textHalfWidth = true)
    private String trspInstructionId; // 運送依頼番号

    @JsonProperty("trsp_instruction_date_subm_dttm")
    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT)
    private String trspInstructionDateSubmDttm; // 運送依頼年月日

    @ValidateField(maxLength = 20, textHalfWidth = true)
    @JsonProperty("inv_num_id")
    private String invNumId; // 運送送り状番号

    @ValidateField(maxLength = 20, textHalfWidth = true)
    @JsonProperty("cmn_inv_num_id")
    private String cmnInvNumId; // 共用送り状番号

    @ValidateField(maxLength = 20, textHalfWidth = true)
    @JsonProperty("mix_load_num_id")
    private String mixLoadNumId; // 積合せ番号
}
