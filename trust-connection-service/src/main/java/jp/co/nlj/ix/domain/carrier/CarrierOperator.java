package jp.co.nlj.ix.domain.carrier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import jp.co.nlj.ix.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * <PRE>
 * CarrierOperatorクラスは、運送事業者エンティティです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DataBaseConstant.CarrierOperator.TABLE)
public class CarrierOperator implements Serializable {

    @Serial
    private static final long serialVersionUID = -478987275984930802L;
    @Id
    @Column(name = DataBaseConstant.CarrierOperator.ID, nullable = false)
    private String id;

    @Column(name = DataBaseConstant.CarrierOperator.OPERATOR_CODE, length = 13)
    private String operatorCode;

    @Column(name = DataBaseConstant.CarrierOperator.OPERATOR_NAME, length = 320)
    private String operatorName;

    @Column(name = DataBaseConstant.CarrierOperator.POSTAL_CODE, length = 7)
    private String postalCode;

    @Column(name = DataBaseConstant.CarrierOperator.ADDRESS_1, length = 320)
    private String address1;

    @Column(name = DataBaseConstant.CarrierOperator.ADDRESS_2, length = 320)
    private String address2;

    @Column(name = DataBaseConstant.CarrierOperator.PHONE_NUMBER, length = 20)
    private String phoneNumber;

    @Column(name = DataBaseConstant.CarrierOperator.MANAGER_NAME, length = 100)
    private String managerName;

    @Column(name = DataBaseConstant.CarrierOperator.IMAGE_LOGO, length = 500)
    private String imageLogo;

    @Column(name = DataBaseConstant.CarrierOperator.NOTES, length = 500)
    private String notes;

    @Column(name = DataBaseConstant.CarrierOperator.STATUS)
    private Integer status;
    @CreatedBy
    @Column(name = DataBaseConstant.CarrierOperator.CREATED_USER, nullable = false, length = 50, updatable = false)
    private String createdUser = "Admin";

    @CreatedDate
    @Column(name = DataBaseConstant.CarrierOperator.CREATED_DATE, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = DataBaseConstant.CarrierOperator.UPDATED_USER, length = 50)
    private String updatedUser = "Admin";

    @LastModifiedDate
    @Column(name = DataBaseConstant.CarrierOperator.UPDATED_DATE)
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Column(name = DataBaseConstant.CarrierOperator.MAIL, length = 320)
    private String email;
}
