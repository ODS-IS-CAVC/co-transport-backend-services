package jp.co.nlj.ttmi.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * サービスプロバイダーDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class LogsSrvcPrvDTO {

    @JsonProperty("logs_srvc_prv_prty_name_txt")
    private String logsSrvcPrvPrtyNameTxt;
}
