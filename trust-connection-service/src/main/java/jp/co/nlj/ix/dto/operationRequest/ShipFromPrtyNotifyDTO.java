package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 出荷場所通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipFromPrtyNotifyDTO {

    @JsonProperty("ship_from_prty_head_off_id")
    private String shipFromPrtyHeadOffId; // 出荷場所コード（本社）

    @JsonProperty("ship_from_prty_brnc_off_id")
    private String shipFromPrtyBrncOffId; // 出荷場所コード（事業所）

    @JsonProperty("ship_from_prty_name_txt")
    private String shipFromPrtyNameTxt; // 出荷場所名（漢字）

    @JsonProperty("ship_from_sct_id")
    private String shipFromSctId; // 出荷場所部門コード

    @JsonProperty("ship_from_sct_name_txt")
    private String shipFromSctNameTxt; // 出荷場所部門名（漢字）

    @JsonProperty("ship_from_tel_cmm_cmp_num_txt")
    private String shipFromTelCmmCmpNumTxt; // 出荷場所電話番号

    @JsonProperty("ship_from_pstl_adrs_cty_id")
    private String shipFromPstlAdrsCtyId; // 出荷場所市区町村コード

    @JsonProperty("ship_from_pstl_adrs_id")
    private String shipFromPstlAdrsId; // 出荷場所住所コード

    @JsonProperty("ship_from_pstl_adrs_line_one_txt")
    private String shipFromPstlAdrsLineOneTxt; // 出荷場所住所（漢字）

    @JsonProperty("ship_from_pstc_cd")
    private String shipFromPstcCd; // 出荷場所郵便番号

    @JsonProperty("plc_cd_prty_id")
    private String plcCdPrtyId; // 場所コード

    @JsonProperty("gln_prty_id")
    private String glnPrtyId; // GLNコード

    @JsonProperty("jpn_uplc_cd")
    private String jpnUplcCd; // 位置情報コード

    @JsonProperty("jpn_van_srvc_cd")
    private String jpnVanSrvcCd; // サービス識別コード

    @JsonProperty("jpn_van_vans_cd")
    private String jpnVanVansCd; // 個別管理コード

    @JsonProperty("ship_from_prty_rqrm")
    private List<ShipFromPrtyRqrmNotifyDTO> shipFromPrtyRqrm; // 出荷場所要件
    @JsonProperty("cut_off_info")
    private List<ShipCutOffInfoNotifyDTO> shipCutOffInfo;
}
