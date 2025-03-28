package nlj.business.transaction.dto.trspPlanLineItem.ix;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 荷届先DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipToPrtyDTO {

    @JsonProperty("ship_to_prty_head_off_id")
    //@ValidateField(maxLength = 13, textHalfWidth = true)
    private String shipToPrtyHeadOffId; // 荷届先コード（本社）

    @JsonProperty("ship_to_prty_brnc_off_id")
    //@ValidateField(maxLength = 17, textHalfWidth = true)
    private String shipToPrtyBrncOffId; // 荷届先コード（事業所）

    @JsonProperty("ship_to_prty_name_txt")
    //@ValidateField(maxLength = 320, textFullWidth = true)
    private String shipToPrtyNameTxt; // 荷届先名（漢字）

    @JsonProperty("ship_to_sct_id")
    //@ValidateField(maxLength = 12, textHalfWidth = true)
    private String shipToSctId; // 荷届先部門コード

    @JsonProperty("ship_to_sct_name_txt")
    //@ValidateField(maxLength = 100, textFullWidth = true)
    private String shipToSctNameTxt; // 荷届先部門名（漢字）

    @JsonProperty("ship_to_prim_cnt_id")
    //@ValidateField(maxLength = 12, textHalfWidth = true)
    private String shipToPrimCntId; // 荷届先担当者コード

    @JsonProperty("ship_to_prim_cnt_pers_name_txt")
    //@ValidateField(maxLength = 20, textFullWidth = true)
    private String shipToPrimCntPersNameTxt; // 荷届先担当者名（漢字）

    @JsonProperty("ship_to_tel_cmm_cmp_num_txt")
    //@ValidateField(maxLength = 20, numberContainSpecialChar = true, textHalfWidth = true)
    private String shipToTelCmmCmpNumTxt; // 荷届先電話番号

    @JsonProperty("ship_to_pstl_adrs_cty_id")
    //@ValidateField(length = "5", textHalfWidth = true, onlyNumber = true)
    private String shipToPstlAdrsCtyId; // 荷届先市区町村コード

    @JsonProperty("ship_to_pstl_adrs_id")
    //@ValidateField(maxLength = 20, textHalfWidth = true)
    private String shipToPstlAdrsId; // 荷届先住所コード

    @JsonProperty("ship_to_pstl_adrs_line_one_txt")
    //@ValidateField(maxLength = 500, textFullWidth = true)
    private String shipToPstlAdrsLineOneTxt; // 荷届先住所（漢字）

    @JsonProperty("ship_to_pstc_cd")
    //@ValidateField(length = "7", textHalfWidth = true, onlyNumber  = true)
    private String shipToPstcCd; // 荷届先郵便番号

    @JsonProperty("plc_cd_prty_id")
    //@ValidateField(maxLength = 4, textHalfWidth = true)
    private String plcCdPrtyId; // 場所コード

    @JsonProperty("gln_prty_id")
    //@ValidateField(maxLength = 13, textHalfWidth = true)
    private String glnPrtyId; // GLNコード

    @JsonProperty("jpn_uplc_cd")
    //@ValidateField(maxLength = 16, textHalfWidth = true)
    private String jpnUplcCd; // 位置情報コード

    @JsonProperty("jpn_van_srvc_cd")
    //@ValidateField(maxLength = 2, textHalfWidth = true)
    private String jpnVanSrvcCd; // サービス識別コード

    @JsonProperty("jpn_van_vans_cd")
    //@ValidateField(maxLength = 12, textHalfWidth = true)
    private String jpnVanVansCd; // 個別管理コード

    @JsonProperty("ship_to_prty_rqrm")
    private ShipToPrtyRqrmDTO shipToPrtyRqrm; // 荷届先要件
    @JsonProperty("free_time_info")
    private List<ShipFreeTimeInfoDTO> shipFreeTimeInfo;
}
