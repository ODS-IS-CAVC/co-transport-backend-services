package jp.co.nlj.gateway.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.gateway.aop.proxy.ValidateField;
import jp.co.nlj.gateway.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * MsgInfoクラスは、メッセージ情報DTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgInfo {

    @JsonProperty("msg_id")
    @ValidateField(precision = 5, textHalfWidth = true)
    private String msgId;

    @JsonProperty("msg_info_cls_typ_cd")
    @ValidateField(maxLength = 4, textHalfWidth = true)
    private String msgInfoClsTypCd;

    @JsonProperty("msg_date_iss_dttm")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String msgDateIssDttm;

    @JsonProperty("msg_time_iss_dttm")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS, textHalfWidth = true)
    private String msgTimeIssDttm;

    @JsonProperty("msg_fn_stas_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true, correctionCode = true, correctionReasonField = "noteDcptTxt")
    private String msgFnStasCd;
}
