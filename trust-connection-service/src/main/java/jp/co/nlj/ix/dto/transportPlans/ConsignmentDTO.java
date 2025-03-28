package jp.co.nlj.ix.dto.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <PRE>
 * 貨物DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ConsignmentDTO {

    @JsonProperty("istd_totl_pcks_quan")
    private Integer istdTotlPcksQuan;

    @JsonProperty("num_unt_cd")
    private String numUntCd;

    @JsonProperty("istd_totl_weig_meas")
    private BigDecimal istdTotlWeigMeas;

    @JsonProperty("weig_unt_cd")
    private String weigUntCd;

    @JsonProperty("istd_totl_vol_meas")
    private BigDecimal istdTotlVolMeas;

    @JsonProperty("vol_unt_cd")
    private String volUntCd;

    @JsonProperty("istd_totl_untl_quan")
    private Integer istdTotlUntlQuan;
} 