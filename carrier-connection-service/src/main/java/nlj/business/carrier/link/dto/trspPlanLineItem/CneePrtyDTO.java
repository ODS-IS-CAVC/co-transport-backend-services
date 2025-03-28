package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 荷受人DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class CneePrtyDTO {

    @ValidateField(maxLength = 13, textHalfWidth = true)
    @JsonProperty("cnee_prty_head_off_id")
    private String cneePrtyHeadOffId; // 荷受人コード（本社）

    @ValidateField(maxLength = 17, textHalfWidth = true)
    @JsonProperty("cnee_prty_brnc_off_id")
    private String cneePrtyBrncOffId; // 荷受人コード（事業所）

    @ValidateField(maxLength = 320, textFullWidth = true)
    @JsonProperty("cnee_prty_name_txt")
    private String cneePrtyNameTxt; // 荷受人名（漢字）

    @ValidateField(maxLength = 12, textHalfWidth = true)
    @JsonProperty("cnee_sct_id")
    private String cneeSctId; // 荷受人部門コード

    @ValidateField(maxLength = 100, textFullWidth = true)
    @JsonProperty("cnee_sct_name_txt")
    private String cneeSctNameTxt; // 荷受人部門名（漢字）

    @ValidateField(maxLength = 20, textFullWidth = true)
    @JsonProperty("cnee_prim_cnt_pers_name_txt")
    private String cneePrimCntPersNameTxt; // 荷受人担当者名（漢字）

    @ValidateField(maxLength = 20, textHalfWidth = true, numberContainSpecialChar = true)
    @JsonProperty("cnee_tel_cmm_cmp_num_txt")
    private String cneeTelCmmCmpNumTxt; // 荷受人電話番号

    @ValidateField(maxLength = 500, textFullWidth = true)
    @JsonProperty("cnee_pstl_adrs_line_one_txt")
    private String cneePstlAdrsLineOneTxt; // 荷受人住所（漢字）

    @ValidateField(length = "7", textHalfWidth = true, onlyNumber = true)
    @JsonProperty("cnee_pstc_cd")
    private String cneePstcCd; // 荷受人郵便番号
}
