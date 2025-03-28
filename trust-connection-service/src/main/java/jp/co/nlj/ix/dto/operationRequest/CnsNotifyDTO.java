package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 委託貨物通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class CnsNotifyDTO {

    @JsonProperty("istd_totl_pcks_quan")
    private BigDecimal istdTotlPcksQuan; // 運送梱包総個数（依頼）

    @JsonProperty("num_unt_cd")
    private String numUntCd; // 個数単位コード

    @JsonProperty("istd_totl_weig_meas")
    private BigDecimal istdTotlWeigMeas; // 運送梱包総重量（依頼）

    @JsonProperty("weig_unt_cd")
    private String weigUntCd; // 重量単位コード

    @JsonProperty("istd_totl_vol_meas")
    private BigDecimal istdTotlVolMeas; // 運送梱包総容積（依頼）

    @JsonProperty("vol_unt_cd")
    private String volUntCd; // 容積単位コード

    @JsonProperty("istd_totl_untl_quan")
    private BigDecimal istdTotlUntlQuan; // ユニットロード総数（依頼）
}
