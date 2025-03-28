package nlj.business.carrier.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 * <PRE>
 * 切り捨て情報DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CutOffInfoDTO {

    private String cutOffTime;

    private BigDecimal cutOffPrice;
}
