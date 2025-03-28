package nlj.business.carrier.link.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両情報エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.VehicleInfo.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.VehicleInfo.ID)
    private Long id;

    @Column(name = DataBaseConstant.VehicleInfo.OPERATOR_ID)
    private String operatorId;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER, length = 24)
    private String registrationNumber;

    @Column(name = DataBaseConstant.VehicleInfo.GIAI, length = 50)
    private String giai;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_TRANSPORT_OFFICE_NAME, length = 8)
    private String registrationTransportOfficeName;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_VEHICLE_TYPE, length = 6)
    private String registrationVehicleType;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_VEHICLE_USE, length = 2)
    private String registrationVehicleUse;

    @Column(name = DataBaseConstant.VehicleInfo.REGISTRATION_VEHICLE_ID, length = 8)
    private String registrationVehicleId;

    @Column(name = DataBaseConstant.VehicleInfo.CHASSIS_NUMBER, length = 42)
    private String chassisNumber;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_ID, length = 20)
    private String vehicleId;

    @Column(name = DataBaseConstant.VehicleInfo.OPERATOR_CORPORATE_NUMBER, length = 13)
    private String operatorCorporateNumber;

    @Column(name = DataBaseConstant.VehicleInfo.OPERATOR_BUSINESS_CODE, length = 17)
    private String operatorBusinessCode;

    @Column(name = DataBaseConstant.VehicleInfo.OWNER_CORPORATE_NUMBER, length = 13)
    private String ownerCorporateNumber;

    @Column(name = DataBaseConstant.VehicleInfo.OWNER_BUSINESS_CODE, length = 17)
    private String ownerBusinessCode;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_TYPE, length = 1)
    private String vehicleType;

    @Column(name = DataBaseConstant.VehicleInfo.HAZARDOUS_MATERIAL_VEHICLE_TYPE, length = 1)
    private String hazardousMaterialVehicleType;

    @Column(name = DataBaseConstant.VehicleInfo.TRACTOR, length = 1)
    private String tractor;

    @Column(name = DataBaseConstant.VehicleInfo.TRAILER, length = 24)
    private String trailer;

    @Column(name = DataBaseConstant.VehicleInfo.MAX_PAYLOAD1)
    private Integer maxPayload1;

    @Column(name = DataBaseConstant.VehicleInfo.MAX_PAYLOAD2)
    private Integer maxPayload2;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_WEIGHT)
    private Integer vehicleWeight;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_LENGTH)
    private Integer vehicleLength;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_WIDTH)
    private Integer vehicleWidth;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_HEIGHT)
    private Integer vehicleHeight;

    @Column(name = DataBaseConstant.VehicleInfo.HAZARDOUS_MATERIAL_VOLUME)
    private Integer hazardousMaterialVolume;

    @Column(name = DataBaseConstant.VehicleInfo.HAZARDOUS_MATERIAL_SPECIFIC_GRAVITY, precision = 8, scale = 3)
    private BigDecimal hazardousMaterialSpecificGravity;

    @Column(name = DataBaseConstant.VehicleInfo.EXPIRY_DATE)
    private LocalDate expiryDate;

    @Column(name = DataBaseConstant.VehicleInfo.DEREGISTRATION_STATUS, length = 1)
    private String deregistrationStatus;

    @Column(name = DataBaseConstant.VehicleInfo.BED_HEIGHT, precision = 15, scale = 0)
    private BigDecimal bedHeight;

    @Column(name = DataBaseConstant.VehicleInfo.CARGO_HEIGHT, precision = 15, scale = 0)
    private BigDecimal cargoHeight;

    @Column(name = DataBaseConstant.VehicleInfo.CARGO_WIDTH, precision = 15, scale = 0)
    private BigDecimal cargoWidth;

    @Column(name = DataBaseConstant.VehicleInfo.CARGO_LENGTH, precision = 15, scale = 0)
    private BigDecimal cargoLength;

    @Column(name = DataBaseConstant.VehicleInfo.MAX_CARGO_CAPACITY, precision = 11, scale = 4)
    private BigDecimal maxCargoCapacity;

    @Column(name = DataBaseConstant.VehicleInfo.BODY_TYPE, length = 1)
    private String bodyType;

    @Column(name = DataBaseConstant.VehicleInfo.POWER_GATE, length = 1)
    private String powerGate;

    @Column(name = DataBaseConstant.VehicleInfo.WING_DOORS, length = 1)
    private String wingDoors;

    @Column(name = DataBaseConstant.VehicleInfo.REFRIGERATION_UNIT, length = 1)
    private String refrigerationUnit;

    @Column(name = DataBaseConstant.VehicleInfo.TEMPERATURE_RANGE_MIN, precision = 5, scale = 2)
    private BigDecimal temperatureRangeMin;

    @Column(name = DataBaseConstant.VehicleInfo.TEMPERATURE_RANGE_MAX, precision = 5, scale = 2)
    private BigDecimal temperatureRangeMax;

    @Column(name = DataBaseConstant.VehicleInfo.CRANE_EQUIPPED, length = 1)
    private String craneEquipped;

    @Column(name = DataBaseConstant.VehicleInfo.VEHICLE_EQUIPMENT_NOTES, length = 100)
    private String vehicleEquipmentNotes;

    @Column(name = DataBaseConstant.VehicleInfo.MASTER_DATA_START_DATE)
    private LocalDate masterDataStartDate;

    @Column(name = DataBaseConstant.VehicleInfo.MASTER_DATA_END_DATE)
    private LocalDate masterDataEndDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleInfo")
    private List<MaxCarryingCapacity> maxCarryingCapacities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleInfo")
    private List<HazardousMaterialInfo> hazardousMaterialInfos = new ArrayList<>();

    @Override
    public Long getId() {
        return this.id;
    }
}
