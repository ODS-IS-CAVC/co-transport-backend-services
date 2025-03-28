package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant.DATE_TIME_FORMAT;

/**
 * <PRE>
 * メッセージ情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class MsgInfoDTO {

    @JsonProperty("msg_id")
    @ValidateField(textHalfWidth = true, precision = 5, scale = 0)
    private BigDecimal msgId; // データ処理ＮＯ．

    @JsonProperty("msg_info_cls_typ_cd")
    @ValidateField(maxLength = 4, textHalfWidth = true)
    private String msgInfoClsTypCd; // 情報区分コード

    @JsonProperty("msg_date_iss_dttm")
    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT)
    private String msgDateIssDttm; // データ作成日

    @JsonProperty("msg_time_iss_dttm")
    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS)
    private String msgTimeIssDttm; // データ作成時刻

    @JsonProperty("msg_fn_stas_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true, correctionCode = true, correctionReasonField = "noteDcptTxt")
    private String msgFnStasCd; // 訂正コード

    @JsonProperty("note_dcpt_txt")
    @ValidateField(maxLength = 500, textFullWidth = true)
    private String noteDcptTxt; // 備考（漢字）
}
