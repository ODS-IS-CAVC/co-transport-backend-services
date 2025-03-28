package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 荷届先通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipToPrtyNotifyDTO {

    @JsonProperty("ship_to_prty_head_off_id")
    private String shipToPrtyHeadOffId; // 荷届先コード（本社）

    @JsonProperty("ship_to_prty_brnc_off_id")
    private String shipToPrtyBrncOffId; // 荷届先コード（事業所）

    @JsonProperty("ship_to_prty_name_txt")
    private String shipToPrtyNameTxt; // 荷届先名（漢字）

    @JsonProperty("ship_to_sct_id")
    private String shipToSctId; // 荷届先部門コード

    @JsonProperty("ship_to_sct_name_txt")
    private String shipToSctNameTxt; // 荷届先部門名（漢字）

    @JsonProperty("ship_to_prim_cnt_id")
    private String shipToPrimCntId; // 荷届先担当者コード

    @JsonProperty("ship_to_prim_cnt_pers_name_txt")
    private String shipToPrimCntPersNameTxt; // 荷届先担当者名（漢字）

    @JsonProperty("ship_to_tel_cmm_cmp_num_txt")
    private String shipToTelCmmCmpNumTxt; // 荷届先電話番号

    @JsonProperty("ship_to_pstl_adrs_cty_id")
    private String shipToPstlAdrsCtyId; // 荷届先市区町村コード

    @JsonProperty("ship_to_pstl_adrs_id")
    private String shipToPstlAdrsId; // 荷届先住所コード

    @JsonProperty("ship_to_pstl_adrs_line_one_txt")
    private String shipToPstlAdrsLineOneTxt; // 荷届先住所（漢字）

    @JsonProperty("ship_to_pstc_cd")
    private String shipToPstcCd; // 荷届先郵便番号

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

    @JsonProperty("ship_to_prty_rqrm")
    private List<ShipToPrtyRqrmNotifyDTO> shipToPrtyRqrm; // 荷届先要件
    @JsonProperty("free_time_info")
    private List<ShipFreeTimeInfoNotifyDTO> shipFreeTimeInfo;
}
