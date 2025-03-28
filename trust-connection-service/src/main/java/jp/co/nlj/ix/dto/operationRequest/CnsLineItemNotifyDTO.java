package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 貨物明細通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class CnsLineItemNotifyDTO {

    @JsonProperty("line_item_num_id")
    private String lineItemNumId; // 明細番号

    @JsonProperty("sev_ord_num_id")
    private String sevOrdNumId; // 個別注文番号

    @JsonProperty("cnsg_crg_item_num_id")
    private String cnsgCrgItemNumId; // 運送品Ｎｏ．（荷送人）

    @JsonProperty("buy_assi_item_cd")
    private String buyAssiItemCd; // 発注者品名コード

    @JsonProperty("sell_assi_item_cd")
    private String sellAssiItemCd; // 受注者品名コード

    @JsonProperty("wrhs_assi_item_cd")
    private String wrhsAssiItemCd; // 倉庫事業者品名コード

    @JsonProperty("item_name_txt")
    private String itemNameTxt; // 商品名（漢字）

    @JsonProperty("gods_idcs_in_ots_pcke_name_txt")
    private String godsIdcsInOtsPckeNameTxt; // 運送品標記用品名（漢字）

    @JsonProperty("num_of_istd_untl_quan")
    private BigDecimal numOfIstdUntlQuan; // ユニットロード数（依頼）

    @JsonProperty("num_of_istd_quan")
    private BigDecimal numOfIstdQuan; // 個数（依頼）

    @JsonProperty("sev_num_unt_cd")
    private String sevNumUntCd; // 個別個数単位コード

    @JsonProperty("istd_pcke_weig_meas")
    private BigDecimal istdPckeWeigMeas; // 運送梱包重量（依頼）

    @JsonProperty("sev_weig_unt_cd")
    private String sevWeigUntCd; // 個別重量単位コード

    @JsonProperty("istd_pcke_vol_meas")
    private BigDecimal istdPckeVolMeas; // 運送梱包容積（依頼）

    @JsonProperty("sev_vol_unt_cd")
    private String sevVolUntCd; // 個別容積単位コード

    @JsonProperty("istd_quan_meas")
    private BigDecimal istdQuanMeas; // 数量（依頼）

    @JsonProperty("cnte_num_unt_cd")
    private String cnteNumUntCd; // 内容数量単位コード

    @JsonProperty("dcpv_trpn_pckg_txt")
    private String dcpvTrpnPckgTxt; // 記述式運送梱包寸法

    @JsonProperty("pcke_frm_cd")
    private String pckeFrmCd; // 荷姿コード

    @JsonProperty("pcke_frm_name_cd")
    private String pckeFrmNameCd; // 荷姿名（漢字）

    @JsonProperty("crg_hnd_trms_spcl_isrs_txt")
    private String crgHndTrmsSpclIsrsTxt; // 荷物取扱条件（漢字）

    @JsonProperty("glb_retb_asse_id")
    private String glbRetbAsseId; // GRAI

    @JsonProperty("totl_rti_quan_quan")
    private BigDecimal totlRtiQuanQuan; // RTI数量

    @JsonProperty("chrg_of_pcke_ctrl_num_unt_amnt")
    private BigDecimal chrgOfPckeCtrlNumUntAmnt; // 梱包管理番号単位運賃
}
