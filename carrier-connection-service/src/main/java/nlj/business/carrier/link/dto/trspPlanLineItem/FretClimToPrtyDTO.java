package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運賃請求先DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class FretClimToPrtyDTO {

    @ValidateField(maxLength = 13, textHalfWidth = true)
    @JsonProperty("fret_clim_to_prty_head_off_id")
    private String fretClimToPrtyHeadOffId; // 運賃請求先コード（本社）

    @ValidateField(maxLength = 17, textHalfWidth = true)
    @JsonProperty("fret_clim_to_prty_brnc_off_id")
    private String fretClimToPrtyBrncOffId; // 運賃請求先コード（事業所）

    @ValidateField(maxLength = 320, textFullWidth = true)
    @JsonProperty("fret_clim_to_prty_name_txt")
    private String fretClimToPrtyNameTxt; // 運賃請求先名（漢字）

    @ValidateField(maxLength = 12, textHalfWidth = true)
    @JsonProperty("fret_clim_to_sct_sped_org_id")
    private String fretClimToSctSpedOrgId; // 運賃請求先部門コード

    @ValidateField(maxLength = 100, textFullWidth = true)
    @JsonProperty("fret_clim_to_sct_sped_org_name_txt")
    private String fretClimToSctSpedOrgNameTxt; // 運賃請求先部門名（漢字）
}
