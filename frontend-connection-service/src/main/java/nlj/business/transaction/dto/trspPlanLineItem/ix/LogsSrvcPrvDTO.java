package nlj.business.transaction.dto.trspPlanLineItem.ix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 物流サービス提供者DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class LogsSrvcPrvDTO {

    //@ValidateField(maxLength = 13, textHalfWidth = true)
    @JsonProperty("logs_srvc_prv_prty_head_off_id")
    private String logsSrvcPrvPrtyHeadOffId; // 物流サービス提供者コード（本社）

    //@ValidateField(maxLength = 17, textHalfWidth = true)
    @JsonProperty("logs_srvc_prv_prty_brnc_off_id")
    private String logsSrvcPrvPrtyBrncOffId; // 物流サービス提供者コード（事業所）

    //@ValidateField(maxLength = 320, textFullWidth = true)
    @JsonProperty("logs_srvc_prv_prty_name_txt")
    private String logsSrvcPrvPrtyNameTxt; // 物流サービス提供者名（漢字）

    //@ValidateField(maxLength = 12, textHalfWidth = true)
    @JsonProperty("logs_srvc_prv_sct_sped_org_id")
    private String logsSrvcPrvSctSpedOrgId; // 物流サービス提供者部門コード

    //@ValidateField(maxLength = 100, textFullWidth = true)
    @JsonProperty("logs_srvc_prv_sct_sped_org_name_txt")
    private String logsSrvcPrvSctSpedOrgNameTxt; // 物流サービス提供者部門名（漢字）

    //@ValidateField(maxLength = 20, textFullWidth = true)
    @JsonProperty("logs_srvc_prv_sct_prim_cnt_pers_name_txt")
    private String logsSrvcPrvSctPrimCntPersNameTxt; // 物流サービス提供者担当者名（漢字）

    //@ValidateField(maxLength = 20, textHalfWidth = true, numberContainSpecialChar = true)
    @JsonProperty("logs_srvc_prv_sct_tel_cmm_cmp_num_txt")
    private String logsSrvcPrvSctTelCmmCmpNumTxt; // 物流サービス提供者電話番号
}
