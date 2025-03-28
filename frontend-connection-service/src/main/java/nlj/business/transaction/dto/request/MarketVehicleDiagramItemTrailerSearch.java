package nlj.business.transaction.dto.request;

import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.ParamConstant;

/**
 * <PRE>
 * マーケット車両図項目トレーラー検索。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class MarketVehicleDiagramItemTrailerSearch {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0, notEqualField = "arrId")
    private String depId;

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String arrId;

    @ValidateField(dateFormat = "yyyyMMdd", endDateField = "arrDate", notNull = true)
    private String depDate;

    @ValidateField(dateFormat = "yyyyMMdd", notNull = true)
    private String arrDate;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private Integer vehicleUnit;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String page = ParamConstant.DEFAULT_PAGE_NO;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String limit = ParamConstant.DEFAULT_PAGE_LIMIT;
}
