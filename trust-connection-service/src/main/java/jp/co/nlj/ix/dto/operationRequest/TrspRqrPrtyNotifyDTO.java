package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送依頼者通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspRqrPrtyNotifyDTO {

    @JsonProperty("trsp_rqr_prty_head_off_id")
    private String trspRqrPrtyHeadOffId; // 運送依頼者コード（本社）

    @JsonProperty("trsp_rqr_prty_brnc_off_id")
    private String trspRqrPrtyBrncOffId; // 運送依頼者コード（事業所）

    @JsonProperty("trsp_rqr_prty_name_txt")
    private String trspRqrPrtyNameTxt; // 運送依頼者名（漢字）

    @JsonProperty("trsp_rqr_sct_sped_org_id")
    private String trspRqrSctSpedOrgId; // 運送依頼者部門コード

    @JsonProperty("trsp_rqr_sct_sped_org_name_txt")
    private String trspRqrSctSpedOrgNameTxt; // 運送依頼者部門名（漢字）

    @JsonProperty("trsp_rqr_sct_tel_cmm_cmp_num_txt")
    private String trspRqrSctTelCmmCmpNumTxt; // 運送依頼者電話番号

    @JsonProperty("trsp_rqr_pstl_adrs_line_one_txt")
    private String trspRqrPstlAdrsLineOneTxt; // 運送依頼者住所（漢字）

    @JsonProperty("trsp_rqr_pstc_cd")
    private String trspRqrPstcCd; // 運送依頼者郵便番号
}
