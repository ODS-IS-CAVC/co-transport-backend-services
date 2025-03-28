package jp.co.nlj.ix.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送計画DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspPlanDTO {

    @JsonProperty("trsp_plan_stas_cd")
    @ValidateField(maxLength = 2, textHalfWidth = true)
    private String trspPlanStasCd; // 運送計画種別コード
}
