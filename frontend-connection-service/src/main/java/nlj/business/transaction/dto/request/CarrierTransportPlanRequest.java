package nlj.business.transaction.dto.request;

import com.next.logistic.util.BaseUtil;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.Prefectures;
import nlj.business.transaction.dto.shipperTrspCapacity.request.ShipperTransportCapacityAdvanceSearchDTO;
import nlj.business.transaction.dto.trspPlanLineItem.TrspPlanItemSearchRequestDTO;

/**
 * <PRE>
 * 運送業者トランザクションプランリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierTransportPlanRequest extends CommonPagingSearch {

    private String cid;

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0, notEqualField = "arrId")
    private String depId;

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
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

    private List<Integer> temperatureRange;

    private Boolean isNotIX;

    private String keyword;

    public TrspPlanItemSearchRequestDTO toTrspPlanLineItemRequestDTO() {
        String depTime = BaseUtil.isNull(this.collectTimeFrom) ? null : this.collectTimeFrom.replaceAll(":", "");
        String arrTime = BaseUtil.isNull(this.collectTimeTo) ? null : this.collectTimeTo.replaceAll(":", "");
        ShipperTransportCapacityAdvanceSearchDTO advanceSearchDTO = new ShipperTransportCapacityAdvanceSearchDTO();
        advanceSearchDTO.setDayWeek(this.dayWeek);
        advanceSearchDTO.setKeyword(this.keyword);
        if (!BaseUtil.isNull(this.freightRateMax)) {
            advanceSearchDTO.setFreightRateMax(new BigDecimal(this.freightRateMax));
        }
        if (!BaseUtil.isNull(this.freightRateMin)) {
            advanceSearchDTO.setFreightRateMin(new BigDecimal(this.freightRateMin));
        }
        TrspPlanItemSearchRequestDTO trspPlanItemSearchRequestDTO = new TrspPlanItemSearchRequestDTO();
        trspPlanItemSearchRequestDTO.setTrspOpStrtAreaLineOneTxt(Prefectures.data.get(Long.parseLong(this.depId)));
        trspPlanItemSearchRequestDTO.setTrspOpEndAreaLineOneTxt(Prefectures.data.get(Long.parseLong(this.arrId)));
        trspPlanItemSearchRequestDTO.setMaxTrspOpDateTrmStrtDate(this.arrDate);
        trspPlanItemSearchRequestDTO.setMinTrspOpDateTrmStrtDate(this.depDate);
        trspPlanItemSearchRequestDTO.setMaxTrspOpPlanDateTrmStrtTime(arrTime);
        trspPlanItemSearchRequestDTO.setMinTrspOpPlanDateTrmStrtTime(depTime);
        trspPlanItemSearchRequestDTO.setAdvancedSearch(advanceSearchDTO);
        trspPlanItemSearchRequestDTO.setCid(this.getCid());
        trspPlanItemSearchRequestDTO.setLimit(this.getLimit());
        trspPlanItemSearchRequestDTO.setPage(this.getPage());
        return trspPlanItemSearchRequestDTO;
    }
}
