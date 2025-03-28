package jp.co.nlj.ix.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 物流サービスプロバイダー dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsServiceProviderDTO {

    @JsonProperty("logs_srvc_prv_prty_head_off_id")
    @ValidateField(maxLength = 13, textHalfWidth = true)
    private String logsSrvcPrvPrtyHeadOffId;

    @JsonProperty("logs_srvc_prv_prty_brnc_off_id")
    @ValidateField(maxLength = 17, textHalfWidth = true)
    private String logsSrvcPrvPrtyBrncOffId;

    @JsonProperty("logs_srvc_prv_prty_name_txt")
    @ValidateField(maxLength = 320, textFullWidth = true)
    private String logsSrvcPrvPrtyNameTxt;

    @JsonProperty("logs_srvc_prv_sct_sped_org_id")
    @ValidateField(maxLength = 12, textHalfWidth = true)
    private String logsSrvcPrvSctSpedOrgId;

    @JsonProperty("logs_srvc_prv_sct_sped_org_name_txt")
    @ValidateField(maxLength = 100, textFullWidth = true)
    private String logsSrvcPrvSctSpedOrgNameTxt;

    @JsonProperty("logs_srvc_prv_sct_prim_cnt_pers_name_txt")
    @ValidateField(maxLength = 20, textFullWidth = true)
    private String logsSrvcPrvSctPrimCntPersNameTxt;

    @JsonProperty("logs_srvc_prv_sct_tel_cmm_cmp_num_txt")
    @ValidateField(maxLength = 20, textHalfWidth = true)
    private String logsSrvcPrvSctTelCmmCmpNumTxt;
}
