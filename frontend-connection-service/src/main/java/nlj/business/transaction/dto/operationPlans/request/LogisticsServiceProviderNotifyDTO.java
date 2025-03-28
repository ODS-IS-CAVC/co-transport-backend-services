package nlj.business.transaction.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 物流サービスプロバイダー通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsServiceProviderNotifyDTO {

    @JsonProperty("logs_srvc_prv_prty_head_off_id")
    private String logsSrvcPrvPrtyHeadOffId;

    @JsonProperty("logs_srvc_prv_prty_brnc_off_id")
    private String logsSrvcPrvPrtyBrncOffId;

    @JsonProperty("logs_srvc_prv_prty_name_txt")
    private String logsSrvcPrvPrtyNameTxt;

    @JsonProperty("logs_srvc_prv_sct_sped_org_id")
    private String logsSrvcPrvSctSpedOrgId;

    @JsonProperty("logs_srvc_prv_sct_sped_org_name_txt")
    private String logsSrvcPrvSctSpedOrgNameTxt;

    @JsonProperty("logs_srvc_prv_sct_prim_cnt_pers_name_txt")
    private String logsSrvcPrvSctPrimCntPersNameTxt;

    @JsonProperty("logs_srvc_prv_sct_tel_cmm_cmp_num_txt")
    private String logsSrvcPrvSctTelCmmCmpNumTxt;
}
