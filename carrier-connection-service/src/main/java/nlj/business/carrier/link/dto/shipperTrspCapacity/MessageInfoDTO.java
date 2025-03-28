package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * メッセージ情報 dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class MessageInfoDTO {

    @JsonProperty("msg_id")
    @ValidateField(precision = 5, scale = 0, textHalfWidth = true)
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

    @JsonProperty("note_dcpt_txt")
    @ValidateField(maxLength = 500, textFullWidth = true)
    private String noteDcptTxt;
}
