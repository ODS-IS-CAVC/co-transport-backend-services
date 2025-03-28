package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送計画DTO.<BR>
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
