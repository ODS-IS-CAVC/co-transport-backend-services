package jp.co.nlj.gateway.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.gateway.aop.proxy.ValidateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * ShipToPrtyクラスは、出荷先プロパティDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipToPrty {

    @JsonProperty("ship_to_prty_brnc_off_id")
    @ValidateField(maxLength = 17, textHalfWidth = true)
    private String shipToPrtyBrncOffId;
}
