package nlj.business.carrier.dto.vehicleInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.constant.DataBaseConstant.VehicleInfo;

/**
 * <PRE>
 * 車両情報 DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoResponseDTO {

    @JsonProperty(DataBaseConstant.VehicleInfo.ID)
    private Long id;

    @JsonProperty(DataBaseConstant.VehicleInfo.REGISTRATION_AREA_CODE)
    private String registrationAreaCode;

    @JsonProperty(DataBaseConstant.VehicleInfo.REGISTRATION_GROUP_NUMBER)
    private String registrationGroupNumber;

    @JsonProperty(DataBaseConstant.VehicleInfo.REGISTRATION_CHARACTER)
    private String registrationCharacter;

    @JsonProperty(DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER_1)
    private String registrationNumber1;

    @JsonProperty(DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER_2)
    private String registrationNumber2;

    @JsonProperty(DataBaseConstant.VehicleInfo.VEHICLE_CODE)
    private String vehicleCode;

    @JsonProperty(DataBaseConstant.VehicleInfo.VEHICLE_NAME)
    private String vehicleName;

    @JsonProperty(DataBaseConstant.VehicleInfo.VEHICLE_TYPE)
    private Integer vehicleType;

    @JsonProperty(DataBaseConstant.VehicleInfo.VEHICLE_SIZE)
    private Integer vehicleSize;

    @JsonProperty(DataBaseConstant.VehicleInfo.TEMPERATURE_RANGE)
    //Integer[]
    private List<Integer> temperatureRange;

    @JsonProperty(DataBaseConstant.VehicleInfo.MAX_PAYLOAD)
    private BigDecimal maxPayload;

    @JsonProperty(DataBaseConstant.VehicleInfo.TOTAL_LENGTH)
    private BigDecimal totalLength;

    @JsonProperty(DataBaseConstant.VehicleInfo.TOTAL_WIDTH)
    private BigDecimal totalWidth;

    @JsonProperty(DataBaseConstant.VehicleInfo.TOTAL_HEIGHT)
    private BigDecimal totalHeight;

    @JsonProperty(DataBaseConstant.VehicleInfo.GROUND_CLEARANCE)
    private BigDecimal groundClearance;

    @JsonProperty(DataBaseConstant.VehicleInfo.DOOR_HEIGHT)
    private BigDecimal doorHeight;

    @JsonProperty(DataBaseConstant.VehicleInfo.BODY_SPECIFICATION)
    private String bodySpecification;

    @JsonProperty(DataBaseConstant.VehicleInfo.BODY_SHAPE)
    private String bodyShape;

    @JsonProperty(DataBaseConstant.VehicleInfo.BODY_CONSTRUCTION)
    private String bodyConstruction;
    @Column(name = DataBaseConstant.VehicleInfo.IMAGES)
    //String[]
    private List<String> images;

    @JsonProperty(DataBaseConstant.VehicleInfo.STATUS)
    private Integer status;

    @JsonProperty(DataBaseConstant.VehicleInfo.GIAI)
    private String giai;
    @JsonProperty(VehicleInfo.DELETE_FLAG)
    private Integer deleteFlag;
}
