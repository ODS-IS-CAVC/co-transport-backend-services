package nlj.business.transaction.dto.trip.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 出発地プライマリDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipFromPartyDTO {

    @JsonProperty("ship_from_prty_rqrm")
    private List<PartyRequirementDTO> shipFromPrtyRqrm;

    @JsonProperty("ship_from_prty_head_off_id")
    private String shipFromPrtyHeadOffId;

    @JsonProperty("ship_from_prty_brnc_off_id")
    private String shipFromPrtyBrncOffId;

    @JsonProperty("ship_from_prty_name_txt")
    private String shipFromPrtyNameTxt;

    @JsonProperty("ship_from_sct_id")
    private String shipFromSctId;

    @JsonProperty("ship_from_sct_name_txt")
    private String shipFromSctNameTxt;

    @JsonProperty("ship_from_tel_cmm_cmp_num_txt")
    private String shipFromTelCmmCmpNumTxt;

    @JsonProperty("ship_from_pstl_adrs_cty_id")
    private String shipFromPstlAdrsCtyId;

    @JsonProperty("ship_from_pstl_adrs_id")
    private String shipFromPstlAdrsId;

    @JsonProperty("ship_from_pstl_adrs_line_one_txt")
    private String shipFromPstlAdrsLineOneTxt;

    @JsonProperty("ship_from_pstc_cd")
    private String shipFromPstcCd;

    @JsonProperty("plc_cd_prty_id")
    private String plcCdPrtyId;

    @JsonProperty("gln_prty_id")
    private String glnPrtyId;

    @JsonProperty("jpn_uplc_cd")
    private String jpnUplcCd;

    @JsonProperty("jpn_van_srvc_cd")
    private String jpnVanSrvcCd;

    @JsonProperty("jpn_van_vans_cd")
    private String jpnVanVansCd;
} 