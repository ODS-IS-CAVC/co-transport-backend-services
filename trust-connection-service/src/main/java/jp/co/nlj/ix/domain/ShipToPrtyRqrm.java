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
 * 荷届先要件エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.ShipToPrtyRqrm.TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShipToPrtyRqrm extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 4298281723521070579L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.ShipToPrtyRqrm.ID)
    private Long id;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.OPERATOR_ID, nullable = false)
    private String operatorId;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.SHIP_TO_PRTY_ID)
    private Long shipToPrtyId;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.TRMS_OF_CAR_SIZE_CD, length = 1)
    private String trmsOfCarSizeCd;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.TRMS_OF_CAR_HGHT_MEAS, length = 5)
    private String trmsOfCarHghtMeas;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.TRMS_OF_GTP_CERT_TXT, length = 50)
    private String trmsOfGtpCertTxt;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.TRMS_OF_DEL_TXT, length = 40)
    private String trmsOfDelTxt;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.TRMS_OF_GODS_HND_TXT, length = 40)
    private String trmsOfGodsHndTxt;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.ANC_WRK_OF_DEL_TXT, length = 40)
    private String ancWrkOfDelTxt;

    @Column(name = DataBaseConstant.ShipToPrtyRqrm.SPCL_WRK_TXT, length = 40)
    private String spclWrkTxt;

    @Override
    public Long getId() {
        return this.id;
    }
}
