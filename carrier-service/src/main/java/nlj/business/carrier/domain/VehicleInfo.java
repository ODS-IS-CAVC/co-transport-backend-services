package nlj.business.carrier.domain;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import com.next.logistic.convert.StringArrayToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DataBaseConstant.VehicleInfo.TABLE)
@Entity
public class VehicleInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -4279578875633879789L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleInfo.ID)
    private Long id;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_AREA_CODE, length = 10)
    private String registrationAreaCode;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_GROUP_NUMBER, length = 10)
    private String registrationGroupNumber;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_CHARACTER, length = 1)
    private String registrationCharacter;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER_1, length = 10)
    private String registrationNumber1;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER_2, length = 10)
    private String registrationNumber2;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_CODE, length = 5)
    private String vehicleCode;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_NAME, length = 250)
    private String vehicleName;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_TYPE)
    private Integer vehicleType;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_SIZE)
    private Integer vehicleSize;

    @Column(name = DataBaseConstant.VehicleInfo.TEMPERATURE_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> temperatureRange;

    @Column(name = DataBaseConstant.VehicleInfo.MAX_PAYLOAD, precision = 9, scale = 3)
    private BigDecimal maxPayload;

    @Column(name = DataBaseConstant.VehicleInfo.TOTAL_LENGTH, precision = 9, scale = 3)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.VehicleInfo.TOTAL_WIDTH, precision = 9, scale = 3)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.VehicleInfo.TOTAL_HEIGHT, precision = 9, scale = 3)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.VehicleInfo.GROUND_CLEARANCE, precision = 9, scale = 3)
    private BigDecimal groundClearance;

    @Column(name = DataBaseConstant.VehicleInfo.DOOR_HEIGHT, precision = 9, scale = 3)
    private BigDecimal doorHeight;

    @Column(name = DataBaseConstant.VehicleInfo.BODY_SPECIFICATION, length = 250)
    private String bodySpecification;

    @Column(name = DataBaseConstant.VehicleInfo.BODY_SHAPE, length = 250)
    private String bodyShape;

    @Column(name = DataBaseConstant.VehicleInfo.BODY_CONSTRUCTION, length = 250)
    private String bodyConstruction;

    @Column(name = DataBaseConstant.VehicleInfo.IMAGES)
    @Convert(converter = StringArrayToStringConverter.class)
    private List<String> images;

    @Column(name = DataBaseConstant.VehicleInfo.STATUS)
    private Integer status;
    @Column(name = DataBaseConstant.VehicleInfo.DELETE_FLAG)
    private Integer deleteFlag;

    @Column(name = DataBaseConstant.VehicleInfo.GIAI)
    private String giai;

    @Override
    public Long getId() {
        return this.id;
    }
}
