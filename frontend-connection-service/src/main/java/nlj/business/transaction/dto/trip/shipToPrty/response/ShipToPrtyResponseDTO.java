package nlj.business.transaction.dto.trip.shipToPrty.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 到着地プライマリ情報レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipToPrtyResponseDTO {

    @JsonProperty("ship_to_prty_head_off_id")
    private String shipToPrtyHeadOffId;

    @JsonProperty("ship_to_prty_brnc_off_id")
    private String shipToPrtyBrncOffId;

    @JsonProperty("ship_to_prty_name_txt")
    private String shipToPrtyNameTxt;

    @JsonProperty("ship_to_sct_id")
    private String shipToSctId;

    @JsonProperty("ship_to_sct_name_txt")
    private String shipToSctNameTxt;

    @JsonProperty("ship_to_prim_cnt_id")
    private String shipToPrimCntId;

    @JsonProperty("ship_to_prim_cnt_pers_name_txt")
    private String shipToPrimCntPersNameTxt;

    @JsonProperty("ship_to_tel_cmm_cmp_num_txt")
    private String shipToTelCmmCmpNumTxt;

    @JsonProperty("ship_to_pstl_adrs_cty_id")
    private String shipToPstlAdrsCtyId;

    @JsonProperty("ship_to_pstl_adrs_id")
    private String shipToPstlAdrsId;

    @JsonProperty("ship_to_pstl_adrs_line_one_txt")
    private String shipToPstlAdrsLineOneTxt;

    @JsonProperty("ship_to_pstc_cd")
    private String shipToPstcCd;

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

    public static ShipToPrtyResponseDTO fromResult(Object[] result) {
        ShipToPrtyResponseDTO dto = new ShipToPrtyResponseDTO();

        if (result != null) {
            dto.setShipToPrtyHeadOffId((String) result[0]);
            dto.setShipToPrtyBrncOffId((String) result[1]);
            dto.setShipToPrtyNameTxt((String) result[2]);
            dto.setShipToSctId((String) result[3]);
            dto.setShipToSctNameTxt((String) result[4]);
            dto.setShipToPrimCntId((String) result[5]);
            dto.setShipToPrimCntPersNameTxt((String) result[6]);
            dto.setShipToTelCmmCmpNumTxt((String) result[7]);
            dto.setShipToPstlAdrsCtyId((String) result[8]);
            dto.setShipToPstlAdrsId((String) result[9]);
            dto.setShipToPstlAdrsLineOneTxt((String) result[10]);
            dto.setShipToPstcCd((String) result[11]);
            dto.setPlcCdPrtyId((String) result[12]);
            dto.setGlnPrtyId((String) result[13]);
            dto.setJpnUplcCd((String) result[14]);
            dto.setJpnVanSrvcCd((String) result[15]);
            dto.setJpnVanVansCd((String) result[16]);
        }

        return dto;
    }
}
