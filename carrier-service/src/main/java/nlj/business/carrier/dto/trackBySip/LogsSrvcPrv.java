package nlj.business.carrier.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.carrier.aop.proxy.ValidateField;

/**
 * <PRE>
 * ログサービスプロバイダー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsSrvcPrv {

    @JsonProperty("logs_srvc_prv_prty_name_txt")
    @ValidateField(maxLength = 320)
    private String logsSrvcPrvPrtyNameTxt;
}
