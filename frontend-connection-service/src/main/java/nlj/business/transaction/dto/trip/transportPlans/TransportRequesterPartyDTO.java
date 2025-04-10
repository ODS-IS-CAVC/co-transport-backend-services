package nlj.business.transaction.dto.trip.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送業者要求先プライマリDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportRequesterPartyDTO {

    @JsonProperty("trsp_rqr_prty_head_off_id")
    private String trspRqrPrtyHeadOffId;

    @JsonProperty("trsp_rqr_prty_brnc_off_id")
    private String trspRqrPrtyBrncOffId;

    @JsonProperty("trsp_rqr_prty_name_txt")
    private String trspRqrPrtyNameTxt;

    @JsonProperty("trsp_rqr_sct_sped_org_id")
    private String trspRqrSctSpedOrgId;

    @JsonProperty("trsp_rqr_sct_sped_org_name_txt")
    private String trspRqrSctSpedOrgNameTxt;

    @JsonProperty("trsp_rqr_sct_tel_cmm_cmp_num_txt")
    private String trspRqrSctTelCmmCmpNumTxt;

    @JsonProperty("trsp_rqr_pstl_adrs_line_one_txt")
    private String trspRqrPstlAdrsLineOneTxt;

    @JsonProperty("trsp_rqr_pstc_cd")
    private String trspRqrPstcCd;
} 