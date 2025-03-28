package jp.co.nlj.ix.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 要求荷届先要件エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ReqShipToPrtyRqrm.TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReqShipToPrtyRqrm extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 4298281723521070579L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.ID)
    private Long id;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.SHIP_TO_PRTY_ID)
    private Long reqShipToPrtyId;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.TO_TRMS_OF_CAR_SIZE_CD, length = 1)
    private String toTrmsOfCarSizeCd;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.TO_TRMS_OF_CAR_HGHT_MEAS, length = 5)
    private String toTrmsOfCarHghtMeas;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.TO_TRMS_OF_GTP_CERT_TXT, length = 50)
    private String toTrmsOfGtpCertTxt;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.TRMS_OF_DEL_TXT, length = 40)
    private String trmsOfDelTxt;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.TO_TRMS_OF_GODS_HND_TXT, length = 40)
    private String toTrmsOfGodsHndTxt;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.ANC_WRK_OF_DEL_TXT, length = 40)
    private String ancWrkOfDelTxt;

    @Column(name = DataBaseConstant.ReqShipToPrtyRqrm.TO_SPCL_WRK_TXT, length = 40)
    private String toSpclWrkTxt;

    @Override
    public Long getId() {
        return this.id;
    }
}
