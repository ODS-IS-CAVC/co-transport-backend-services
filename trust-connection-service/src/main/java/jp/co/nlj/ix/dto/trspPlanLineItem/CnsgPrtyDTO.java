package jp.co.nlj.ix.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 荷送人DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class CnsgPrtyDTO {

    @ValidateField(maxLength = 13, textHalfWidth = true, notNull = true)
    @JsonProperty("cnsg_prty_head_off_id")
    private String cnsgPrtyHeadOffId; // 荷送人コード（本社）

    @ValidateField(maxLength = 17, textHalfWidth = true, notNull = true)
    @JsonProperty("cnsg_prty_brnc_off_id")
    private String cnsgPrtyBrncOffId; // 荷送人コード（事業所）

    @ValidateField(maxLength = 320, textFullWidth = true)
    @JsonProperty("cnsg_prty_name_txt")
    private String cnsgPrtyNameTxt; // 荷送人名（漢字）

    @ValidateField(maxLength = 12, textHalfWidth = true)
    @JsonProperty("cnsg_sct_sped_org_id")
    private String cnsgSctSpedOrgId; // 荷送人部門コード

    @ValidateField(maxLength = 100, textFullWidth = true)
    @JsonProperty("cnsg_sct_sped_org_name_txt")
    private String cnsgSctSpedOrgNameTxt; // 荷送人部門名（漢字）

    @ValidateField(maxLength = 20, textHalfWidth = true, numberContainSpecialChar = true)
    @JsonProperty("cnsg_tel_cmm_cmp_num_txt")
    private String cnsgTelCmmCmpNumTxt; // 荷送人電話番号

    @ValidateField(maxLength = 500, textFullWidth = true)
    @JsonProperty("cnsg_pstl_adrs_line_one_txt")
    private String cnsgPstlAdrsLineOneTxt; // 荷送人住所（漢字）

    @ValidateField(length = "7", textHalfWidth = true, onlyNumber = true)
    @JsonProperty("cnsg_pstc_cd")
    private String cnsgPstcCd; // 荷送人郵便番号
}
