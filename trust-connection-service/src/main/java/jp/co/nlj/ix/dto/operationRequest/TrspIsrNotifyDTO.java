package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.constant.DataBaseConstant.DATE_TIME_FORMAT;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * <PRE>
 * 運送依頼通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspIsrNotifyDTO {

    @JsonProperty("trsp_instruction_id")
    private String trspInstructionId; // 運送依頼番号

    @JsonProperty("trsp_instruction_date_subm_dttm")
    private String trspInstructionDateSubmDttm; // 運送依頼年月日

    @JsonProperty("inv_num_id")
    private String invNumId; // 運送送り状番号

    @JsonProperty("cmn_inv_num_id")
    private String cmnInvNumId; // 共用送り状番号

    @JsonProperty("mix_load_num_id")
    private String mixLoadNumId; // 積合せ番号
}
