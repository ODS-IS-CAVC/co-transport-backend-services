package nlj.business.transaction.dto.request;

import java.util.List;
import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.ParamConstant;

/**
 * <PRE>
 * 交通計画公開検索。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanPublicSearch {

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String depId;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String arrId;

    @ValidateField(dateFormat = "yyyyMMdd", endDateField = "arrDate")
    private String depDate;

    @ValidateField(dateFormat = "yyyyMMdd")
    private String arrDate;

    @ValidateField(timeFormat = "HH:mm")
    private String collectTimeFrom;

    @ValidateField(timeFormat = "HH:mm")
    private String collectTimeTo;

    private List<Integer> dayWeek;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String freightRateMax;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String freightRateMin;

    private String temperatureRange;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String page = ParamConstant.DEFAULT_PAGE_NO;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String limit = ParamConstant.DEFAULT_PAGE_LIMIT;

    private String keyword;
}
