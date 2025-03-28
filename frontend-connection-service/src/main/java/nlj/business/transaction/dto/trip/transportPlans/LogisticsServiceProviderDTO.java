package nlj.business.transaction.dto.trip.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 物流サービスプロバイダDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class LogisticsServiceProviderDTO {

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