package nlj.business.transaction.dto.trip.trspAbillityLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送業者能力行項目レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspAbillityLineItemResponseDTO {

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

    public static TrspAbillityLineItemResponseDTO fromResult(Object[] result) {
        TrspAbillityLineItemResponseDTO dto = new TrspAbillityLineItemResponseDTO();
        dto.setTrspCliPrtyHeadOffId((String) result[0]);
        dto.setTrspCliPrtyBrncOffId((String) result[1]);
        dto.setTrspCliPrtyNameTxt((String) result[2]);
        dto.setRoadCarrDepaSpedOrgId((String) result[3]);
        dto.setRoadCarrDepaSpedOrgNameTxt((String) result[4]);
        dto.setTrspCliTelCmmCmpNumTxt((String) result[5]);
        dto.setRoadCarrArrSpedOrgId((String) result[6]);
        dto.setRoadCarrArrSpedOrgNameTxt((String) result[7]);
        dto.setLogsSrvcPrvPrtyHeadOffId((String) result[8]);
        dto.setLogsSrvcPrvPrtyBrncOffId((String) result[9]);
        dto.setLogsSrvcPrvPrtyNameTxt((String) result[10]);
        dto.setLogsSrvcPrvSctSpedOrgId((String) result[11]);
        dto.setLogsSrvcPrvSctSpedOrgNameTxt((String) result[12]);
        dto.setLogsSrvcPrvSctPrimCntPersNameTxt((String) result[13]);
        dto.setLogsSrvcPrvSctTelCmmCmpNumTxt((String) result[14]);
        return dto;
    }
}