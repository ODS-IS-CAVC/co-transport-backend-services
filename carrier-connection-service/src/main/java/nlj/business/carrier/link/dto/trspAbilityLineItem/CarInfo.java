package nlj.business.carrier.link.dto.trspAbilityLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 車両情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarInfo {

    @JsonProperty("pcke_frm_cd")
    @ValidateField(length = "3")
    private String pckeFrmCd;

    @JsonProperty("ar_max_untl_cp_quan")
    @ValidateField(length = "2", minValue = 1)
    private Integer arMaxUntlCpQuan;
}
