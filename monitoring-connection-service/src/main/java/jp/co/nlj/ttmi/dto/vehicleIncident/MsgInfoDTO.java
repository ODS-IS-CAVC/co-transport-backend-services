package jp.co.nlj.ttmi.dto.vehicleIncident;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import jp.co.nlj.ttmi.constant.DataBaseConstant.DATE_TIME_FORMAT;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * メッセージ情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class MsgInfoDTO {

    @JsonProperty("msg_id")
    @ValidateField(minValue = 0, maxValue = 99999)
    private Integer msgId;

    @JsonProperty("msg_info_cls_typ_cd")
    private String msgInfoClsTypCd;

    @JsonProperty("msg_date_iss_dttm")
    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT)
    private String msgDateIssDttm;

    @JsonProperty("msg_time_iss_dttm")
    @ValidateField(timeFormat = DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS)
    private Integer msgTimeIssDttm;

    @JsonProperty("msg_fn_stas_cd")
    @ValidateField(msgFnStasCdType = true)
    private String msgFnStasCd;
}
