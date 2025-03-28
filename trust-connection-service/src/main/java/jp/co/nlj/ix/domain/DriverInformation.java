package jp.co.nlj.ix.domain;

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
import java.util.Set;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * ドライバー情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.DriverInformation.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverInformation extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.DriverInformation.OPERATOR_ID, nullable = false)
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = DataBaseConstant.DriverInformation.TRSP_ABILLITY_LINE_ITEM_ID, nullable = false)
    @JsonIgnore
    private TransportAbilityLineItem transportAbilityLineItem;

    @OneToMany(mappedBy = DataBaseConstant.DriverInformation.MAPPED_BY, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private Set<DriverAvailabilityTime> driverAvailabilityTimes;

    @Column(name = DataBaseConstant.DriverInformation.DRV_CTRL_NUM_ID, length = 12)
    private String drvCtrlNumId;

    @Column(name = DataBaseConstant.DriverInformation.DRV_CLS_OF_DRVG_LICENSE_CD, length = 15)
    private String drvClsOfDrvgLicenseCd;

    @Column(name = DataBaseConstant.DriverInformation.DRV_CLS_OF_FKL_LICENSE_EXST_CD, length = 1)
    private String drvClsOfFklLicenseExstCd;

    @Column(name = DataBaseConstant.DriverInformation.DRV_RMK_ABOUT_DRV_TXT, length = 20)
    private String drvRmkAboutDrvTxt;

    @Column(name = DataBaseConstant.DriverInformation.DRV_CMPN_NAME_OF_GTP_CRTF_EXST_TXT, length = 100)
    private String drvCmpnNameOfGtpCrtfExstTxt;

    @Override
    public Long getId() {
        return this.id;
    }
}
