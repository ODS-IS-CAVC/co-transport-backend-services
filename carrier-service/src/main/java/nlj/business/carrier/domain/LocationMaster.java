package nlj.business.carrier.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * <PRE>
 * 場所マスタ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DataBaseConstant.LocationMaster.TABLE)
public class LocationMaster implements Serializable {

    @Serial
    private static final long serialVersionUID = -4548593659588476432L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = DataBaseConstant.LocationMaster.AREA_ID)
    private Long areaId;
    @Column(name = DataBaseConstant.LocationMaster.CODE, length = 16)
    private String code;

    @Column(name = DataBaseConstant.LocationMaster.NAME, length = 20)
    private String name;

    @Column(name = DataBaseConstant.LocationMaster.POSTAL_CODE, length = 7)
    private String postalCode;

    @Column(name = DataBaseConstant.LocationMaster.MOBILITY_HUB_ID, length = 50)
    private String mobilityHubId;

    @Column(name = DataBaseConstant.LocationMaster.LATITUDE, precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(name = DataBaseConstant.LocationMaster.LONGITUDE, precision = 10, scale = 6)
    private BigDecimal longitude;

    @Column(name = DataBaseConstant.LocationMaster.DISPLAY_ORDER)
    private Integer displayOrder;

    @Column(name = DataBaseConstant.LocationMaster.STATUS)
    private Integer status;
    @Column(name = "created_user", nullable = false, length = 50, updatable = false)
    private String createdUser = "Admin";

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = "updated_user", length = 50)
    private String updatedUser = "Admin";

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();
}
