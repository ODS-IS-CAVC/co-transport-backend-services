package jp.co.nlj.gateway.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.gateway.aop.proxy.ValidateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * LogsSrvcPrvクラスは、ログサービスプロバイダーDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsSrvcPrv {

    @JsonProperty("logs_srvc_prv_prty_name_txt")
    @ValidateField(maxLength = 320, textFullWidthAndKanji = true)
    private String logsSrvcPrvPrtyNameTxt;
}
