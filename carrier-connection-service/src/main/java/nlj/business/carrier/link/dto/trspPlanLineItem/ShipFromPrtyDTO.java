package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 出荷場所DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipFromPrtyDTO {

    @JsonProperty("ship_from_prty_head_off_id")
    @ValidateField(maxLength = 13, textHalfWidth = true)
    private String shipFromPrtyHeadOffId; // 出荷場所コード（本社）

    @JsonProperty("ship_from_prty_brnc_off_id")
    @ValidateField(maxLength = 17, textHalfWidth = true)
    private String shipFromPrtyBrncOffId; // 出荷場所コード（事業所）

    @JsonProperty("ship_from_prty_name_txt")
    @ValidateField(maxLength = 320, textFullWidth = true)
    private String shipFromPrtyNameTxt; // 出荷場所名（漢字）

    @JsonProperty("ship_from_sct_id")
    @ValidateField(maxLength = 12, textHalfWidth = true)
    private String shipFromSctId; // 出荷場所部門コード

    @JsonProperty("ship_from_sct_name_txt")
    @ValidateField(maxLength = 100, textFullWidth = true)
    private String shipFromSctNameTxt; // 出荷場所部門名（漢字）

    @JsonProperty("ship_from_tel_cmm_cmp_num_txt")
    @ValidateField(maxLength = 20, textHalfWidth = true, numberContainSpecialChar = true)
    private String shipFromTelCmmCmpNumTxt; // 出荷場所電話番号

    @JsonProperty("ship_from_pstl_adrs_cty_id")
    @ValidateField(length = "5", textHalfWidth = true, onlyNumber = true)
    private String shipFromPstlAdrsCtyId; // 出荷場所市区町村コード

    @JsonProperty("ship_from_pstl_adrs_id")
    @ValidateField(maxLength = 20, textHalfWidth = true)
    private String shipFromPstlAdrsId; // 出荷場所住所コード

    @JsonProperty("ship_from_pstl_adrs_line_one_txt")
    @ValidateField(maxLength = 500, textFullWidth = true)
    private String shipFromPstlAdrsLineOneTxt; // 出荷場所住所（漢字）

    @JsonProperty("ship_from_pstc_cd")
    @ValidateField(length = "7", textHalfWidth = true, onlyNumber = true)
    private String shipFromPstcCd; // 出荷場所郵便番号

    @JsonProperty("plc_cd_prty_id")
    @ValidateField(maxLength = 4, textHalfWidth = true)
    private String plcCdPrtyId; // 場所コード

    @JsonProperty("gln_prty_id")
    @ValidateField(maxLength = 13, textHalfWidth = true)
    private String glnPrtyId; // GLNコード

    @JsonProperty("jpn_uplc_cd")
    @ValidateField(maxLength = 16, textHalfWidth = true)
    private String jpnUplcCd; // 位置情報コード

    @JsonProperty("jpn_van_srvc_cd")
    @ValidateField(maxLength = 2, textHalfWidth = true)
    private String jpnVanSrvcCd; // サービス識別コード

    @JsonProperty("jpn_van_vans_cd")
    @ValidateField(maxLength = 12, textHalfWidth = true)
    private String jpnVanVansCd; // 個別管理コード

    @JsonProperty("ship_from_prty_rqrm")
    private ShipFromPrtyRqrmDTO shipFromPrtyRqrm; // 出荷場所要件
    @JsonProperty("cut_off_info")
    private List<ShipCutOffInfoDTO> shipCutOffInfo;
}
