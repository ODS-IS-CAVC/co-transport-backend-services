package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 荷送人通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class CnsgPrtyNotifyDTO {

    @JsonProperty("cnsg_prty_head_off_id")
    private String cnsgPrtyHeadOffId; // 荷送人コード（本社）

    @JsonProperty("cnsg_prty_brnc_off_id")
    private String cnsgPrtyBrncOffId; // 荷送人コード（事業所）

    @JsonProperty("cnsg_prty_name_txt")
    private String cnsgPrtyNameTxt; // 荷送人名（漢字）

    @JsonProperty("cnsg_sct_sped_org_id")
    private String cnsgSctSpedOrgId; // 荷送人部門コード

    @JsonProperty("cnsg_sct_sped_org_name_txt")
    private String cnsgSctSpedOrgNameTxt; // 荷送人部門名（漢字）

    @JsonProperty("cnsg_tel_cmm_cmp_num_txt")
    private String cnsgTelCmmCmpNumTxt; // 荷送人電話番号

    @JsonProperty("cnsg_pstl_adrs_line_one_txt")
    private String cnsgPstlAdrsLineOneTxt; // 荷送人住所（漢字）

    @JsonProperty("cnsg_pstc_cd")
    private String cnsgPstcCd; // 荷送人郵便番号
}
