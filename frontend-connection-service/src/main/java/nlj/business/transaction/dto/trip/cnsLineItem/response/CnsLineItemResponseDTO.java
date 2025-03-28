package nlj.business.transaction.dto.trip.cnsLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送業者プラン行項目レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CnsLineItemResponseDTO {

    @JsonProperty("line_item_num_id")
    private String lineItemNumId;

    @JsonProperty("sev_ord_num_id")
    private String sevOrdNumId;

    @JsonProperty("cnsg_crg_item_num_id")
    private String cnsgCrgItemNumId;

    @JsonProperty("buy_assi_item_cd")
    private String buyAssiItemCd;

    @JsonProperty("sell_assi_item_cd")
    private String sellAssiItemCd;

    @JsonProperty("wrhs_assi_item_cd")
    private String wrhsAssiItemCd;

    @JsonProperty("item_name_txt")
    private String itemNameTxt;

    @JsonProperty("gods_idcs_in_ots_pcke_name_txt")
    private String godsIdcsInOtsPckeNameTxt;

    @JsonProperty("num_of_istd_untl_quan")
    private Integer numOfIstdUntlQuan;

    @JsonProperty("num_of_istd_quan")
    private Integer numOfIstdQuan;

    @JsonProperty("sev_num_unt_cd")
    private String sevNumUntCd;

    @JsonProperty("istd_pcke_weig_meas")
    private String istdPckeWeigMeas;

    @JsonProperty("sev_weig_unt_cd")
    private String sevWeigUntCd;

    @JsonProperty("istd_pcke_vol_meas")
    private String istdPckeVolMeas;

    @JsonProperty("sev_vol_unt_cd")
    private String sevVolUntCd;

    @JsonProperty("istd_quan_meas")
    private String istdQuanMeas;

    @JsonProperty("cnte_num_unt_cd")
    private String cnteNumUntCd;

    @JsonProperty("dcpv_trpn_pckg_txt")
    private String dcpvTrpnPckgTxt;

    @JsonProperty("pcke_frm_cd")
    private String pckeFrmCd;

    @JsonProperty("pcke_frm_name_cd")
    private String pckeFrmNameCd;

    @JsonProperty("crg_hnd_trms_spcl_isrs_txt")
    private String crgHndTrmsSpclIsrsTxt;

    @JsonProperty("glb_retb_asse_id")
    private String glbRetbAsseId;

    @JsonProperty("totl_rti_quan_quan")
    private Integer totlRtiQuanQuan;

    @JsonProperty("chrg_of_pcke_ctrl_num_unt_amnt")
    private Integer chrgOfPckeCtrlNumUntAmnt;

    @JsonProperty("trsp_plan_line_item_id")
    private Long trspPlanLineItemId;

    public static CnsLineItemResponseDTO fromResult(Object[] result) {
        CnsLineItemResponseDTO dto = new CnsLineItemResponseDTO();
        dto.setLineItemNumId((String) result[0]);
        dto.setSevOrdNumId((String) result[1]);
        dto.setCnsgCrgItemNumId((String) result[2]);
        dto.setBuyAssiItemCd((String) result[3]);
        dto.setSellAssiItemCd((String) result[4]);
        dto.setWrhsAssiItemCd((String) result[5]);
        dto.setItemNameTxt((String) result[6]);
        dto.setGodsIdcsInOtsPckeNameTxt((String) result[7]);
        dto.setNumOfIstdUntlQuan(result[8] != null ? ((Number) result[8]).intValue() : null);
        dto.setNumOfIstdQuan(result[9] != null ? ((Number) result[9]).intValue() : null);
        dto.setSevNumUntCd((String) result[10]);
        dto.setIstdPckeWeigMeas((String) result[11]);
        dto.setSevWeigUntCd((String) result[12]);
        dto.setIstdPckeVolMeas((String) result[13]);
        dto.setSevVolUntCd((String) result[14]);
        dto.setIstdQuanMeas((String) result[15]);
        dto.setCnteNumUntCd((String) result[16]);
        dto.setDcpvTrpnPckgTxt((String) result[17]);
        dto.setPckeFrmCd((String) result[18]);
        dto.setPckeFrmNameCd((String) result[19]);
        dto.setCrgHndTrmsSpclIsrsTxt((String) result[20]);
        dto.setGlbRetbAsseId((String) result[21]);
        dto.setTotlRtiQuanQuan(result[22] != null ? ((Number) result[22]).intValue() : null);
        dto.setChrgOfPckeCtrlNumUntAmnt(result[23] != null ? ((Number) result[23]).intValue() : null);
        dto.setTrspPlanLineItemId((Long) result[24]);
        return dto;
    }
}
