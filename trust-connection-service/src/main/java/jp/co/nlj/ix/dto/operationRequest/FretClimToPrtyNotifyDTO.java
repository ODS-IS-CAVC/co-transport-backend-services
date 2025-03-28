package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運賃請求先通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class FretClimToPrtyNotifyDTO {

    @JsonProperty("fret_clim_to_prty_head_off_id")
    private String fretClimToPrtyHeadOffId; // 運賃請求先コード（本社）

    @JsonProperty("fret_clim_to_prty_brnc_off_id")
    private String fretClimToPrtyBrncOffId; // 運賃請求先コード（事業所）

    @JsonProperty("fret_clim_to_prty_name_txt")
    private String fretClimToPrtyNameTxt; // 運賃請求先名（漢字）

    @JsonProperty("fret_clim_to_sct_sped_org_id")
    private String fretClimToSctSpedOrgId; // 運賃請求先部門コード

    @JsonProperty("fret_clim_to_sct_sped_org_name_txt")
    private String fretClimToSctSpedOrgNameTxt; // 運賃請求先部門名（漢字）
}
