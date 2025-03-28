package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 委託貨物DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class CnsDTO {

    @JsonProperty("istd_totl_pcks_quan")
    @ValidateField(notNull = true, textHalfWidth = true, precision = 9, scale = 0, minValue = 1)
    private BigDecimal istdTotlPcksQuan; // 運送梱包総個数（依頼）

    @JsonProperty("num_unt_cd")
    @ValidateField(maxLength = 3, textHalfWidth = true)
    private String numUntCd; // 個数単位コード

    @ValidateField(precision = 14, scale = 3, textHalfWidth = true)
    @JsonProperty("istd_totl_weig_meas")
    private BigDecimal istdTotlWeigMeas; // 運送梱包総重量（依頼）

    @ValidateField(maxLength = 3, textHalfWidth = true)
    @JsonProperty("weig_unt_cd")
    private String weigUntCd; // 重量単位コード

    @ValidateField(precision = 11, scale = 4, textHalfWidth = true)
    @JsonProperty("istd_totl_vol_meas")
    private BigDecimal istdTotlVolMeas; // 運送梱包総容積（依頼）

    @ValidateField(maxLength = 3, textHalfWidth = true)
    @JsonProperty("vol_unt_cd")
    private String volUntCd; // 容積単位コード

    @JsonProperty("istd_totl_untl_quan")
    @ValidateField(textHalfWidth = true, precision = 9, scale = 0)
    private BigDecimal istdTotlUntlQuan; // ユニットロード総数（依頼）
}
