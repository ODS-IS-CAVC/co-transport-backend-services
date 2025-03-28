package jp.co.nlj.ix.dto.trspInstruction;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 貨物明細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqCnsLineItemDTO {

    @ValidateField(maxLength = 10, textHalfWidth = true)
    @JsonProperty("line_item_num_id")
    private String lineItemNumId; // 明細番号

    @ValidateField(maxLength = 23, textHalfWidth = true)
    @JsonProperty("sev_ord_num_id")
    private String sevOrdNumId; // 個別注文番号

    @ValidateField(maxLength = 15, textHalfWidth = true)
    @JsonProperty("cnsg_crg_item_num_id")
    private String cnsgCrgItemNumId; // 運送品Ｎｏ．（荷送人）

    @JsonProperty("chrg_of_pcke_ctrl_num_unt_amnt")
    @ValidateField(precision = 8, scale = 0, textHalfWidth = true)
    private String chrgOfPckeCtrlNumUntAmnt; // 梱包管理番号単位運賃

    @ValidateField(maxLength = 25, textHalfWidth = true)
    @JsonProperty("buy_assi_item_cd")
    private String buyAssiItemCd; // 発注者品名コード

    @ValidateField(maxLength = 55, textHalfWidth = true)
    @JsonProperty("sell_assi_item_cd")
    private String sellAssiItemCd; // 受注者品名コード

    @ValidateField(maxLength = 25, textHalfWidth = true)
    @JsonProperty("wrhs_assi_item_cd")
    private String wrhsAssiItemCd; // 倉庫事業者品名コード

    @ValidateField(maxLength = 200, textFullWidth = true)
    @JsonProperty("item_name_txt")
    private String itemNameTxt; // 商品名（漢字）

    @ValidateField(maxLength = 50, textFullWidth = true)
    @JsonProperty("gods_idcs_in_ots_pcke_name_txt")
    private String godsIdcsInOtsPckeNameTxt; // 運送品標記用品名（漢字）

    @ValidateField(precision = 9, scale = 0, textHalfWidth = true)
    @JsonProperty("num_of_istd_untl_quan")
    private String clsOfIstdUntlQuan; // ユニットロード数（依頼）

    @ValidateField(precision = 9, scale = 0, textHalfWidth = true)
    @JsonProperty("num_of_istd_quan")
    private BigDecimal numOfIstdQuan; // 個数（依頼）

    @ValidateField(maxLength = 3, textHalfWidth = true)
    @JsonProperty("sev_num_unt_cd")
    private String sevNumUntCd; // 個別個数単位コード

    @ValidateField(precision = 12, scale = 3, textHalfWidth = true)
    @JsonProperty("istd_pcke_weig_meas")
    private BigDecimal istdPckeWeigMeas; // 運送梱包重量（依頼）

    @ValidateField(maxLength = 3, textHalfWidth = true)
    @JsonProperty("sev_weig_unt_cd")
    private String sevWeigUntCd; // 個別重量単位コード

    @ValidateField(precision = 11, scale = 4, textHalfWidth = true)
    @JsonProperty("istd_pcke_vol_meas")
    private BigDecimal istdPckeVolMeas; // 運送梱包容積（依頼）

    @ValidateField(maxLength = 3, textHalfWidth = true)
    @JsonProperty("sev_vol_unt_cd")
    private String sevVolUntCd; // 個別容積単位コード

    @ValidateField(precision = 15, scale = 4, textHalfWidth = true)
    @JsonProperty("istd_quan_meas")
    private BigDecimal istdQuanMeas; // 数量（依頼）

    @ValidateField(maxLength = 3, textHalfWidth = true)
    @JsonProperty("cnte_num_unt_cd")
    private String cnteNumUntCd; // 内容数量単位コード

    @ValidateField(maxLength = 20, textHalfWidth = true)
    @JsonProperty("dcpv_trpn_pckg_txt")
    private String dcpvTrpnPckgTxt; // 記述式運送梱包寸法

    @ValidateField(maxLength = 3, textHalfWidth = true)
    @JsonProperty("pcke_frm_cd")
    private String pckeFrmCd; // 荷姿コード

    @ValidateField(maxLength = 20, textFullWidth = true)
    @JsonProperty("pcke_frm_name_cd")
    private String pckeFrmNameCd; // 荷姿名（漢字）

    @ValidateField(maxLength = 30, textFullWidth = true)
    @JsonProperty("crg_hnd_trms_spcl_isrs_txt")
    private String crgHndTrmsSpclIsrsTxt; // 荷物取扱条件（漢字）

    @ValidateField(maxLength = 14, textHalfWidth = true)
    @JsonProperty("glb_retb_asse_id")
    private String glbRetbAsseId; // GRAI

    @ValidateField(precision = 5, scale = 0, textHalfWidth = true)
    @JsonProperty("totl_rti_quan_quan")
    private BigDecimal totlRtiQuanQuan; // RTI数量

}
