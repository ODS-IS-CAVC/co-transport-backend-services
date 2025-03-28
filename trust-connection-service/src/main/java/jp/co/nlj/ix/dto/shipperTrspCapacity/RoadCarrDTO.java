package jp.co.nlj.ix.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * ロードカー dto。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoadCarrDTO {

    @JsonProperty("trsp_cli_prty_head_off_id")
    @ValidateField(notNull = true, maxLength = 13, textHalfWidth = true)
    private String trspCliPrtyHeadOffId;

    @JsonProperty("trsp_cli_prty_brnc_off_id")
    @ValidateField(notNull = true, maxLength = 17, textHalfWidth = true)
    private String trspCliPrtyBrncOffId;

    @JsonProperty("trsp_cli_prty_name_txt")
    @ValidateField(maxLength = 320, textFullWidth = true)
    private String trspCliPrtyNameTxt;

    @JsonProperty("road_carr_depa_sped_org_id")
    @ValidateField(maxLength = 12, textHalfWidth = true)
    private String roadCarrDepaSpedOrgId;

    @JsonProperty("road_carr_depa_sped_org_name_txt")
    @ValidateField(maxLength = 320, textFullWidth = true)
    private String roadCarrDepaSpedOrgNameTxt;

    @JsonProperty("trsp_cli_tel_cmm_cmp_num_txt")
    @ValidateField(maxLength = 20, textHalfWidth = true)
    private String trspCliTelCmmCmpNumTxt;

    @JsonProperty("road_carr_arr_sped_org_id")
    @ValidateField(maxLength = 12, textHalfWidth = true)
    private String roadCarrArrSpedOrgId;

    @JsonProperty("road_carr_arr_sped_org_name_txt")
    @ValidateField(maxLength = 320, textFullWidth = true)
    private String roadCarrArrSpedOrgNameTxt;
}
