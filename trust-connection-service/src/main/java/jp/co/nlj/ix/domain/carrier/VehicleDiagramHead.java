package jp.co.nlj.ix.domain.carrier;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.domain.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * VehicleDiagramHeadクラスは、車両図ヘッダエンティティです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleDiagramHead.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDiagramHead extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToMany(mappedBy = DataBaseConstant.VehicleDiagramHead.MAPPED_BY, cascade = CascadeType.ALL)
    private List<VehicleDiagram> vehicleDiagrams;

    @Column(name = DataBaseConstant.VehicleDiagramHead.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.VehicleDiagramHead.DEPARTURE_FROM)
    private Long departureFrom;

    @Column(name = DataBaseConstant.VehicleDiagramHead.ARRIVAL_TO)
    private Long arrivalTo;

    @Column(name = DataBaseConstant.VehicleDiagramHead.ONE_WAY_TIME)
    private String oneWayTime;

    @Column(name = DataBaseConstant.VehicleDiagramHead.START_DATE)
    private LocalDate startDate;

    @Column(name = DataBaseConstant.VehicleDiagramHead.END_DATE)
    private LocalDate endDate;

    @Column(name = DataBaseConstant.VehicleDiagramHead.REPEAT_DAY)
    private Integer repeatDay;

    @Column(name = DataBaseConstant.VehicleDiagramHead.TRAILER_NUMBER)
    private Integer trailerNumber;

    @Column(name = DataBaseConstant.VehicleDiagramHead.IS_ROUND_TRIP)
    private Boolean isRoundTrip;

    @Column(name = DataBaseConstant.VehicleDiagramHead.ORIGIN_TYPE)
    private Integer originType;

    @Column(name = DataBaseConstant.VehicleDiagramHead.IMPORT_ID)
    private Long importId;

    @Column(name = DataBaseConstant.VehicleDiagramHead.STATUS)
    private Integer status;

}
