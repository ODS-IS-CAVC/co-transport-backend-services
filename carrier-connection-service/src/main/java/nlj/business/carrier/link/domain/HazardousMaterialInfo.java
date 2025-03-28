package nlj.business.carrier.link.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 危険物情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.HazardousMaterialInfo.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HazardousMaterialInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.HazardousMaterialInfo.ID)
    private Long id;

    @Column(name = DataBaseConstant.HazardousMaterialInfo.OPERATOR_ID)
    private String operatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DataBaseConstant.HazardousMaterialInfo.VEHICLE_INFO_ID)
    private VehicleInfo vehicleInfo;

    @Column(name = DataBaseConstant.HazardousMaterialInfo.HAZARDOUS_MATERIAL_ITEM_CODE, length = 2)
    private String hazardousMaterialItemCode;

    @Column(name = DataBaseConstant.HazardousMaterialInfo.HAZARDOUS_MATERIAL_TEXT_INFO, length = 1)
    private String hazardousMaterialTextInfo;

    @Override
    public Long getId() {
        return this.id;
    }

}
