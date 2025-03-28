package nlj.business.transaction.domain.trans;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 市場輸送計画項目エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.MarketTransportPlanItem.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketTransportPlanItem extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.SHIPPER_CORPERATE_ID, nullable = false)
    private Long shipperCorperateId;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.TRANSPORT_PLAN_ID, nullable = false)
    private Long transportPlanId;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.TRANSPORT_PLAN_ITEM_ID, nullable = false)
    private Long transportPlanItemId;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.TRANSPORT_CODE, nullable = false)
    private String transportCode;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.TRANSPORT_NAME, nullable = false)
    private String transportName;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.DEPARTURE_FROM)
    private String departureFrom;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.ARRIVAL_TO)
    private String arrivalTo;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.COLLECTION_DATE)
    private LocalDate collectionDate;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.PRICE_PER_UNIT)
    private Double pricePerUnit;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.COLLECTION_TIME_FROM)
    private String collectionTimeFrom;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.COLLECTION_TIME_TO)
    private String collectionTimeTo;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.VEHICLE_CONDITION)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> vehicleCondition;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.OUTER_PACKAGE_CODE)
    private String outerPackageCode;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.TOTAL_LENGTH)
    private Double totalLength;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.TOTAL_WIDTH)
    private Double totalWidth;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.TOTAL_HEIGHT)
    private Double totalHeight;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.WEIGHT)
    private Double weight;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.SPECIAL_INSTRUCTIONS)
    private String specialInstructions;

    @Column(name = DataBaseConstant.MarketTransportPlanItem.STATUS)
    private Integer status;
}
