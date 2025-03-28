package nlj.business.carrier.link.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
 * 出荷業者オペレーターエンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.ShipperOperator.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipperOperator extends AbstractAuditingEntity<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = DataBaseConstant.ShipperOperator.ID)
    private String id;

    @Column(name = DataBaseConstant.ShipperOperator.OPERATOR_CODE, length = 13)
    private String operatorCode;

    @Column(name = DataBaseConstant.ShipperOperator.OPERATOR_NAME, length = 320)
    private String operatorName;

    @Column(name = DataBaseConstant.ShipperOperator.POSTAL_CODE, length = 7)
    private String postalCode;

    @Column(name = DataBaseConstant.ShipperOperator.ADDRESS_1, length = 320)
    private String address1;

    @Column(name = DataBaseConstant.ShipperOperator.ADDRESS_2, length = 320)
    private String address2;

    @Column(name = DataBaseConstant.ShipperOperator.PHONE_NUMBER, length = 20)
    private String phoneNumber;

    @Column(name = DataBaseConstant.ShipperOperator.MANAGER_NAME, length = 100)
    private String managerName;

    @Column(name = DataBaseConstant.ShipperOperator.IMAGE_LOGO, length = 500)
    private String imageLogo;

    @Column(name = DataBaseConstant.ShipperOperator.OTHERS, length = 500)
    private String others;

    @Column(name = DataBaseConstant.ShipperOperator.STATUS)
    private Integer status;

    @Column(name = DataBaseConstant.ShipperOperator.EMAIL)
    private String email;

    @Override
    public String getId() {
        return this.id;
    }
}