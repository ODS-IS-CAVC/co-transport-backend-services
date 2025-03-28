package nlj.business.carrier.link.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * ドライバーの可用性時間エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.DriverAvailabilityTime.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverAvailabilityTime extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.DriverAvailabilityTime.OPERATOR_ID, nullable = false)
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.DriverAvailabilityTime.DRV_INFO_ID, nullable = false)
    @JsonIgnore
    private DriverInformation driverInformation;

    @Column(name = DataBaseConstant.DriverAvailabilityTime.DRV_AVB_FROM_DATE)
    private LocalDate drvAvbFromDate;

    @Column(name = DataBaseConstant.DriverAvailabilityTime.DRV_AVB_FROM_TIME_OF_WRKG_TIME)
    private LocalTime drvAvbFromTimeOfWrkgTime;

    @Column(name = DataBaseConstant.DriverAvailabilityTime.DRV_AVB_TO_DATE)
    private LocalDate drvAvbToDate;

    @Column(name = DataBaseConstant.DriverAvailabilityTime.DRV_AVB_TO_TIME_OF_WRKG_TIME)
    private LocalTime drvAvbToTimeOfWrkgTime;

    @Column(name = DataBaseConstant.DriverAvailabilityTime.DRV_WRKG_TRMS_TXT, length = 20)
    private String drvWrkgTrmsTxt;

    @Column(name = DataBaseConstant.DriverAvailabilityTime.DRV_FRMR_OPTG_DATE)
    private LocalDate drvFrmrOptgDate;

    @Column(name = DataBaseConstant.DriverAvailabilityTime.DRV_FRMR_OP_END_TIME)
    private LocalTime drvFrmrOpEndTime;

    @Override
    public Long getId() {
        return this.id;
    }
}
