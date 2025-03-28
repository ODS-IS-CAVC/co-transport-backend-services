package jp.co.nlj.ix.domain.carrier;

import com.next.logistic.convert.IntegerArrayToStringConverter;
import com.next.logistic.convert.StringArrayToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.domain.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * VehicleInfoクラスは、車両情報エンティティです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleInfo.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.VehicleInfo.OPERATOR_ID)
    private String operatorId;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_AREA_CODE)
    private String registrationAreaCode;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_GROUP_NUMBER)
    private String registrationGroupNumber;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_CHARACTER)
    private String registrationCharacter;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER_1)
    private String registrationNumber1;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER_2)
    private String registrationNumber2;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_CODE)
    private String vehicleCode;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_NAME)
    private String vehicleName;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_TYPE)
    private Integer vehicleType;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_SIZE)
    private Integer vehicleSize;

    @Column(name = DataBaseConstant.VehicleInfo.TEMPERATURE_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> temperatureRange;

    @Column(name = DataBaseConstant.VehicleInfo.MAX_PAYLOAD)
    private BigDecimal maxPayload;

    @Column(name = DataBaseConstant.VehicleInfo.TOTAL_LENGTH)
    private BigDecimal totalLength;

    @Column(name = DataBaseConstant.VehicleInfo.TOTAL_WIDTH)
    private BigDecimal totalWidth;

    @Column(name = DataBaseConstant.VehicleInfo.TOTAL_HEIGHT)
    private BigDecimal totalHeight;

    @Column(name = DataBaseConstant.VehicleInfo.GROUND_CLEARANCE)
    private BigDecimal groundClearance;

    @Column(name = DataBaseConstant.VehicleInfo.DOOR_HEIGHT)
    private BigDecimal doorHeight;

    @Column(name = DataBaseConstant.VehicleInfo.BODY_SPECIFICATION)
    private String bodySpecification;

    @Column(name = DataBaseConstant.VehicleInfo.BODY_SHAPE)
    private String bodyShape;

    @Column(name = DataBaseConstant.VehicleInfo.BODY_CONSTRUCTION)
    private String bodyConstruction;

    @Column(name = DataBaseConstant.VehicleInfo.IMAGES)
    @Convert(converter = StringArrayToStringConverter.class)
    private List<String> images;

    @Column(name = DataBaseConstant.VehicleInfo.STATUS)
    private Integer status;
}
