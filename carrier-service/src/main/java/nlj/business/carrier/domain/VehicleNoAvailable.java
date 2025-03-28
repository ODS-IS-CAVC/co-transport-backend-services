package nlj.business.carrier.domain;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 利用可能な車両番号。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DataBaseConstant.VehicleNoAvailable.TABLE)
@Entity
public class VehicleNoAvailable extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 6590241111836644194L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleNoAvailable.ID)
    private Long id;

    @Column(name = DataBaseConstant.VehicleNoAvailable.VEHICLE_INFO_ID, nullable = false)
    private Long vehicleInfoId;

    @Column(name = DataBaseConstant.VehicleNoAvailable.START_DATE)
    private LocalDate startDate;

    @Column(name = DataBaseConstant.VehicleNoAvailable.END_DATE)
    private LocalDate endDate;
    @Column(name = DataBaseConstant.VehicleNoAvailable.STATUS)
    private Integer status;
    @Column(name = DataBaseConstant.VehicleNoAvailable.DAY_WEEK)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> dayWeek;

    @Override
    public Long getId() {
        return this.id;
    }
}
