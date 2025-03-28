package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送事業者DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class RoadCarrDTO {

    @ValidateField(maxLength = 13, textHalfWidth = true)
    @JsonProperty("trsp_cli_prty_head_off_id")
    private String trspCliPrtyHeadOffId; // 運送事業者コード（本社）

    @ValidateField(maxLength = 17, textHalfWidth = true)
    @JsonProperty("trsp_cli_prty_brnc_off_id")
    private String trspCliPrtyBrncOffId; // 運送事業者コード（事業所）

    @ValidateField(maxLength = 320, textFullWidth = true)
    @JsonProperty("trsp_cli_prty_name_txt")
    private String trspCliPrtyNameTxt; // 運送事業者名（漢字）

    @ValidateField(maxLength = 12, textHalfWidth = true)
    @JsonProperty("road_carr_depa_sped_org_id")
    private String roadCarrDepaSpedOrgId; // 運送事業者発店コード

    @ValidateField(maxLength = 320, textFullWidth = true)
    @JsonProperty("road_carr_depa_sped_org_name_txt")
    private String roadCarrDepaSpedOrgNameTxt; // 運送事業者発店名（漢字）

    @ValidateField(maxLength = 20, textHalfWidth = true, numberContainSpecialChar = true)
    @JsonProperty("trsp_cli_tel_cmm_cmp_num_txt")
    private String trspCliTelCmmCmpNumTxt; // 運送事業者電話番号

    @ValidateField(maxLength = 12, textHalfWidth = true)
    @JsonProperty("road_carr_arr_sped_org_id")
    private String roadCarrArrSpedOrgId; // 運送事業者着店コード

    @ValidateField(maxLength = 320, textFullWidth = true)
    @JsonProperty("road_carr_arr_sped_org_name_txt")
    private String roadCarrArrSpedOrgNameTxt; // 運送事業者着店名（漢字）
}
