package jp.co.nlj.ix.dto.operationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RoadCarrNotifyDTO {

    @JsonProperty("trsp_cli_prty_head_off_id")
    private String trspCliPrtyHeadOffId;

    @JsonProperty("trsp_cli_prty_brnc_off_id")
    private String trspCliPrtyBrncOffId;

    @JsonProperty("trsp_cli_prty_name_txt")
    private String trspCliPrtyNameTxt;

    @JsonProperty("road_carr_depa_sped_org_id")
    private String roadCarrDepaSpedOrgId;

    @JsonProperty("road_carr_depa_sped_org_name_txt")
    private String roadCarrDepaSpedOrgNameTxt;

    @JsonProperty("trsp_cli_tel_cmm_cmp_num_txt")
    private String trspCliTelCmmCmpNumTxt;

    @JsonProperty("road_carr_arr_sped_org_id")
    private String roadCarrArrSpedOrgId;

    @JsonProperty("road_carr_arr_sped_org_name_txt")
    private String roadCarrArrSpedOrgNameTxt;
}
