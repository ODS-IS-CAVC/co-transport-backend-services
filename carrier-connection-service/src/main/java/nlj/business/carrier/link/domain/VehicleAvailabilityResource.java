package nlj.business.carrier.link.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両可用性リソースエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleAvailabilityResource.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleAvailabilityResource extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.OPERATOR_ID, nullable = false)
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.VehicleAvailabilityResource.CAR_INFO_ID, nullable = false)
    @JsonIgnore
    private CarInfo carInfo;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_STRT_AREA_LINE_ONE_TXT, length = 20, nullable = false)
    private String trspOpStrtAreaLineOneTxt;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_STRT_AREA_CTY_JIS_CD, length = 5)
    private String trspOpStrtAreaCtyJisCd;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_DATE_TRM_STRT_DATE, nullable = false)
    private LocalDate trspOpDateTrmStrtDate;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_PLAN_DATE_TRM_STRT_TIME, nullable = false)
    private LocalTime trspOpPlanDateTrmStrtTime;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_END_AREA_LINE_ONE_TXT, length = 20, nullable = false)
    private String trspOpEndAreaLineOneTxt;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_END_AREA_CTY_JIS_CD, length = 5)
    private String trspOpEndAreaCtyJisCd;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_DATE_TRM_END_DATE, nullable = false)
    private LocalDate trspOpDateTrmEndDate;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_PLAN_DATE_TRM_END_TIME, nullable = false)
    private LocalTime trspOpPlanDateTrmEndTime;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.CLB_AREA_TXT, length = 20)
    private String clbAreaTxt;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRMS_OF_CLB_AREA_CD, length = 5)
    private String trmsOfClbAreaCd;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.AVB_DATE_CLL_DATE)
    private LocalDate avbDateCllDate;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.AVB_FROM_TIME_OF_CLL_TIME)
    private LocalTime avbFromTimeOfCllTime;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.AVB_TO_TIME_OF_CLL_TIME)
    private LocalTime avbToTimeOfCllTime;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.DELB_AREA_TXT, length = 20)
    private String delbAreaTxt;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRMS_OF_DELB_AREA_CD, length = 5)
    private String trmsOfDelbAreaCd;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.ESTI_DEL_DATE_PRFM_DTTM)
    private LocalDate estiDelDatePrfmDttm;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.AVB_FROM_TIME_OF_DEL_TIME)
    private LocalTime avbFromTimeOfDelTime;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.AVB_TO_TIME_OF_DEL_TIME)
    private LocalTime avbToTimeOfDelTime;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.AVB_LOAD_CP_OF_CAR_MEAS, precision = 14, scale = 3)
    private BigDecimal avbLoadCpOfCarMeas;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.AVB_LOAD_VOL_OF_CAR_MEAS, precision = 11, scale = 4)
    private BigDecimal avbLoadVolOfCarMeas;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.PCKE_FRM_CD, length = 3)
    private String pckeFrmCd;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.AVB_NUM_OF_RETB_CNTN_OF_CAR_QUAN, precision = 2, scale = 0)
    private BigDecimal avbNumOfRetbCntnOfCarQuan;

    @Column(name = DataBaseConstant.VehicleAvailabilityResource.TRK_BED_STAS_TXT, length = 100)
    private String trkBedStasTxt;
    @OneToMany(mappedBy = DataBaseConstant.VehicleAvailabilityResource.MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private Set<CutOffInfo> cutOffInfos;
    @OneToMany(mappedBy = DataBaseConstant.VehicleAvailabilityResource.MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private Set<FreeTimeInfo> freeTimeInfos;
    @OneToMany(mappedBy = DataBaseConstant.VehicleAvailabilityResource.MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private Set<VehicleAvbResourceItem> vehicleAvbResourceItems;

    @Override
    public Long getId() {
        return this.id;
    }
}
