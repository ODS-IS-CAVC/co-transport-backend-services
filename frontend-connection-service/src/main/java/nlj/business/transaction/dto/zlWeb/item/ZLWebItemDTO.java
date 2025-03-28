package nlj.business.transaction.dto.zlWeb.item;

import java.math.BigDecimal;
import lombok.Data;

/**
 * <PRE>
 * ZLWebItemDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ZLWebItemDTO {

    private String itemCode;
    private String itemName;
    private Integer lengthSize;
    private Integer widthSize;
    private Integer heightSize;
    private BigDecimal weight;
    private BigDecimal unit;
}
