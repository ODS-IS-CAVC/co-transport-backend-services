package nlj.business.transaction.dto.request;

import com.next.logistic.util.BaseUtil;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.dto.shipperTrspCapacity.request.ShipperTransportCapacityAdvanceSearchDTO;
import nlj.business.transaction.dto.shipperTrspCapacity.request.ShipperTransportCapacitySearchDTO;

/**
 * <PRE>
 * 交通計画公開検索。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportAbilityPublicSearch extends CommonPagingSearch {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String depId;

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String arrId;

    @ValidateField(dateFormat = "yyyyMMdd", endDateField = "arrDate")
    private String depDate;

    @ValidateField(dateFormat = "yyyyMMdd")
    private String arrDate;

    @ValidateField(timeFormat = "HH:mm")
    private String depTime;

    @ValidateField(timeFormat = "HH:mm")
    private String arrTime;

    private List<Integer> dayWeek;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String freightRateMax;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String freightRateMin;

    private List<Integer> temperatureRange;

    private Boolean isNotIX;

    private String cid;

    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String collectionDate;

    private BigDecimal pricePerUnit;

    private String keyword;

    public ShipperTransportCapacitySearchDTO toShipperTransportCapacitySearchDTO() {
        String depTime = BaseUtil.isNull(this.depTime) ? null : this.depTime.replaceAll(":", "");
        String arrTime = BaseUtil.isNull(this.arrTime) ? null : this.arrTime.replaceAll(":", "");
        ShipperTransportCapacityAdvanceSearchDTO advanceSearchDTO = new ShipperTransportCapacityAdvanceSearchDTO();
        advanceSearchDTO.setDayWeek(this.dayWeek);
        advanceSearchDTO.setKeyword(this.keyword);
        advanceSearchDTO.setTemperatureRange(this.temperatureRange);
        if (this.pricePerUnit != null) {
            advanceSearchDTO.setFreightRateMax(this.pricePerUnit);
            advanceSearchDTO.setFreightRateMin(this.pricePerUnit);
        } else {
            if (!BaseUtil.isNull(this.freightRateMax)) {
                advanceSearchDTO.setFreightRateMax(new BigDecimal(this.freightRateMax));
            }
            if (!BaseUtil.isNull(this.freightRateMin)) {
                advanceSearchDTO.setFreightRateMin(new BigDecimal(this.freightRateMin));
            }
        }
        ShipperTransportCapacitySearchDTO shipperTrspSearchDTO = new ShipperTransportCapacitySearchDTO();
        shipperTrspSearchDTO.setTrspOpStrtAreaLineOneTxt(this.depId);
        shipperTrspSearchDTO.setTrspOpEndAreaLineOneTxt(this.arrId);
        if (!BaseUtil.isNull(this.collectionDate)) {
            shipperTrspSearchDTO.setMaxTrspOpDateTrmStrtDate(this.collectionDate);
            shipperTrspSearchDTO.setMinTrspOpDateTrmStrtDate(this.collectionDate);
        } else {
            shipperTrspSearchDTO.setMaxTrspOpDateTrmStrtDate(this.arrDate);
            shipperTrspSearchDTO.setMinTrspOpDateTrmStrtDate(this.depDate);
        }
        shipperTrspSearchDTO.setMaxTrspOpDateTrmEndDate(this.arrDate);
        shipperTrspSearchDTO.setMinTrspOpDateTrmEndDate(this.arrDate);
        shipperTrspSearchDTO.setMaxTrspOpPlanDateTrmStrtTime(arrTime);
        shipperTrspSearchDTO.setMinTrspOpPlanDateTrmStrtTime(depTime);
        shipperTrspSearchDTO.setMaxTrspOpPlanDateTrmEndTime(arrTime);
        shipperTrspSearchDTO.setMinTrspOpPlanDateTrmEndTime(arrTime);
        shipperTrspSearchDTO.setAdvancedSearch(advanceSearchDTO);
        return shipperTrspSearchDTO;
    }
}
