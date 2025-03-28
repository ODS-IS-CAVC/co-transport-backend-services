package nlj.business.transaction.domain.yamato;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 車両情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.CarInfo.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.CarInfo.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.CarInfo.TRSP_ABILLITY_LINE_ITEM_ID, nullable = false)
    private Long transportAbilityLineItemId;

    @OneToMany(mappedBy = DataBaseConstant.CarInfo.MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private Set<VehicleAvailabilityResource> vehicleAvailabilityResources;

    @Column(name = DataBaseConstant.CarInfo.SERVICE_STRT_DATE, nullable = false)
    private LocalDate serviceStrtDate;

    @Column(name = DataBaseConstant.CarInfo.SERVICE_STRT_TIME, nullable = false)
    private LocalTime serviceStrtTime;

    @Column(name = DataBaseConstant.CarInfo.SERVICE_END_DATE)
    private LocalDate serviceEndDate;

    @Column(name = DataBaseConstant.CarInfo.SERVICE_END_TIME)
    private LocalTime serviceEndTime;

    @Column(name = DataBaseConstant.CarInfo.FREIGHT_RATE, nullable = false, precision = 10, scale = 0)
    private BigDecimal freightRate;

    @Column(name = DataBaseConstant.CarInfo.GIAI, nullable = false, length = 50)
    private String giai;

    @Column(name = DataBaseConstant.CarInfo.SERVICE_NO, nullable = false, length = 20)
    private String serviceNo;

    @Column(name = DataBaseConstant.CarInfo.SERVICE_NAME, nullable = false, length = 24)
    private String serviceName;

    @Column(name = DataBaseConstant.CarInfo.CAR_CTRL_NUM_ID, nullable = true, length = 20)
    private String carCtrlNumId;

    @Column(name = DataBaseConstant.CarInfo.CAR_LICENSE_PLT_NUM_ID, nullable = true, length = 24)
    private String carLicensePltNumId;

    @Column(name = DataBaseConstant.CarInfo.CAR_BODY_NUM_CD, nullable = true, length = 42)
    private String carBodyNumCd;

    @Column(name = DataBaseConstant.CarInfo.CAR_CLS_OF_SIZE_CD, nullable = true, length = 1)
    private String carClsOfSizeCd;

    @Column(name = DataBaseConstant.CarInfo.TRACTOR_IDCR, nullable = true, length = 1)
    private String tractorIdcr;

    @Column(name = DataBaseConstant.CarInfo.TRAILER_LICENSE_PLT_NUM_ID, nullable = true, length = 24)
    private String trailerLicensePltNumId;

    @Column(name = DataBaseConstant.CarInfo.CAR_WEIG_MEAS, nullable = true, precision = 10, scale = 0)
    private BigDecimal carWeigMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_LNGH_MEAS, nullable = true, precision = 10, scale = 0)
    private BigDecimal carLnghMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_WID_MEAS, nullable = true, precision = 10, scale = 0)
    private BigDecimal carWidMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_HGHT_MEAS, nullable = true, precision = 10, scale = 0)
    private BigDecimal carHghtMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_MAX_LOAD_CAPACITY1_MEAS, nullable = false, precision = 10, scale = 0)
    private BigDecimal carMaxLoadCapacity1Meas;

    @Column(name = DataBaseConstant.CarInfo.CAR_MAX_LOAD_CAPACITY2_MEAS, nullable = true, precision = 10, scale = 0)
    private BigDecimal carMaxLoadCapacity2Meas;

    @Column(name = DataBaseConstant.CarInfo.CAR_VOL_OF_HZD_ITEM_MEAS, nullable = true, precision = 10, scale = 0)
    private BigDecimal carVolOfHzdItemMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_SPC_GRV_OF_HZD_ITEM_MEAS, nullable = true, precision = 10, scale = 3)
    private BigDecimal carSpcGrvOfHzdItemMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_TRK_BED_HGHT_MEAS, nullable = true, precision = 15, scale = 0)
    private BigDecimal carTrkBedHghtMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_TRK_BED_WID_MEAS, nullable = true, precision = 15, scale = 0)
    private BigDecimal carTrkBedWidMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_TRK_BED_LNGH_MEAS, nullable = true, precision = 15, scale = 0)
    private BigDecimal carTrkBedLnghMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_TRK_BED_GRND_HGHT_MEAS, nullable = true, precision = 15, scale = 0)
    private BigDecimal carTrkBedGrndHghtMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_MAX_LOAD_VOL_MEAS, nullable = true, precision = 11, scale = 4)
    private BigDecimal carMaxLoadVolMeas;

    @Column(name = DataBaseConstant.CarInfo.PCKE_FRM_CD, nullable = true, length = 3)
    private String pckeFrmCd;

    @Column(name = DataBaseConstant.CarInfo.PCKE_FRM_NAME_CD, nullable = true, length = 20)
    private String pckeFrmNameCd;

    @Column(name = DataBaseConstant.CarInfo.CAR_MAX_UNTL_CP_QUAN, nullable = true, precision = 10, scale = 0)
    private BigDecimal carMaxUntlCpQuan;

    @Column(name = DataBaseConstant.CarInfo.CAR_CLS_OF_SHP_CD, nullable = true, length = 1)
    private String carClsOfShpCd;

    @Column(name = DataBaseConstant.CarInfo.CAR_CLS_OF_TLG_LFTR_EXST_CD, nullable = true, length = 1)
    private String carClsOfTlgLftrExstCd;

    @Column(name = DataBaseConstant.CarInfo.CAR_CLS_OF_WING_BODY_EXST_CD, nullable = true, length = 1)
    private String carClsOfWingBodyExstCd;

    @Column(name = DataBaseConstant.CarInfo.CAR_CLS_OF_RFG_EXST_CD, nullable = true, length = 1)
    private String carClsOfRfgExstCd;

    @Column(name = DataBaseConstant.CarInfo.TRMS_OF_LWR_TMP_MEAS, nullable = true, precision = 10, scale = 2)
    private BigDecimal trmsOfLwrTmpMeas;

    @Column(name = DataBaseConstant.CarInfo.TRMS_OF_UPP_TMP_MEAS, nullable = true, precision = 10, scale = 2)
    private BigDecimal trmsOfUppTmpMeas;

    @Column(name = DataBaseConstant.CarInfo.CAR_CLS_OF_CRN_EXST_CD, nullable = true, length = 1)
    private String carClsOfCrnExstCd;

    @Column(name = DataBaseConstant.CarInfo.CAR_RMK_ABOUT_EQPM_TXT, nullable = true, length = 100)
    private String carRmkAboutEqpmTxt;

    @Column(name = DataBaseConstant.CarInfo.CAR_CMPN_NAME_OF_GTP_CRTF_EXST_TXT, nullable = true, length = 100)
    private String carCmpnNameOfGtpCrfExstTxt;

    @Override
    public Long getId() {
        return this.id;
    }
}
