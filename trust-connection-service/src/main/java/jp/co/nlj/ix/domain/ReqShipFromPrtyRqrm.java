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
 * 要求出荷場所要件エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ReqShipFromPrtyRqrm.TABLE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReqShipFromPrtyRqrm extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 2725671498022904886L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.ID)
    private Long id;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.SHIP_FROM_PRTY_ID)
    private Long reqShipFromPrtyId;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.FROM_TRMS_OF_CAR_SIZE_CD, length = 1)
    private String fromTrmsOfCarSizeCd;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.FROM_TRMS_OF_CAR_HGHT_MEAS, length = 5)
    private String fromTrmsOfCarHghtMeas;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.FROM_TRMS_OF_GTP_CERT_TXT, length = 50)
    private String fromTrmsOfGtpCertTxt;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.TRMS_OF_CLL_TXT, length = 40)
    private String trmsOfCllTxt;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.FROM_TRMS_OF_GODS_HND_TXT, length = 40)
    private String fromTrmsOfGodsHndTxt;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.ANC_WRK_OF_CLL_TXT, length = 40)
    private String ancWrkOfCllTxt;

    @Column(name = DataBaseConstant.ReqShipFromPrtyRqrm.FROM_SPCL_WRK_TXT, length = 40)
    private String fromSpclWrkTxt;

    @Override
    public Long getId() {
        return this.id;
    }
}
