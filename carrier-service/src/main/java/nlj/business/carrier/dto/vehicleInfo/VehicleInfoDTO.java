package nlj.business.carrier.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.aop.proxy.ValidateField;
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
public class VehicleInfoDTO {

    @JsonProperty(VehicleInfo.ID)
    private Long id;
    @JsonProperty(VehicleInfo.REGISTRATION_AREA_CODE)
    @ValidateField(maxLength = 10)
    private String registrationAreaCode;

    @JsonProperty(VehicleInfo.REGISTRATION_GROUP_NUMBER)
    @ValidateField(maxLength = 10)
    private String registrationGroupNumber;

    @JsonProperty(VehicleInfo.REGISTRATION_CHARACTER)
    @ValidateField(maxLength = 1)
    private String registrationCharacter;

    @JsonProperty(VehicleInfo.REGISTRATION_NUMBER_1)
    @ValidateField(maxLength = 10)
    private String registrationNumber1;

    @JsonProperty(VehicleInfo.REGISTRATION_NUMBER_2)
    @ValidateField(maxLength = 10)
    private String registrationNumber2;

    @JsonProperty(VehicleInfo.VEHICLE_CODE)
    @ValidateField(maxLength = 5)
    private String vehicleCode;

    @JsonProperty(VehicleInfo.VEHICLE_NAME)
    @ValidateField(maxLength = 250)
    private String vehicleName;

    @JsonProperty(VehicleInfo.VEHICLE_TYPE)
    @ValidateField(carrierVehicleType = true)
    private Integer vehicleType;

    @JsonProperty(VehicleInfo.VEHICLE_SIZE)
    private Integer vehicleSize;

    @JsonProperty(VehicleInfo.TEMPERATURE_RANGE)
    @ValidateField(arrayInput = true)
    private List<Integer> temperatureRange = new ArrayList<>();

    @JsonProperty(VehicleInfo.MAX_PAYLOAD)
    @ValidateField(textHalfWidth = true, precision = 9, scale = 3)
    private BigDecimal maxPayload;

    @JsonProperty(VehicleInfo.TOTAL_LENGTH)
    @ValidateField(textHalfWidth = true, precision = 9, scale = 3)
    private BigDecimal totalLength;

    @JsonProperty(VehicleInfo.TOTAL_WIDTH)
    @ValidateField(textHalfWidth = true, precision = 9, scale = 3)
    private BigDecimal totalWidth;

    @JsonProperty(VehicleInfo.TOTAL_HEIGHT)
    @ValidateField(textHalfWidth = true, precision = 9, scale = 3)
    private BigDecimal totalHeight;

    @JsonProperty(VehicleInfo.GROUND_CLEARANCE)
    @ValidateField(textHalfWidth = true, precision = 9, scale = 3)
    private BigDecimal groundClearance;

    @JsonProperty(VehicleInfo.DOOR_HEIGHT)
    @ValidateField(textHalfWidth = true, precision = 9, scale = 3)
    private BigDecimal doorHeight;

    @JsonProperty(VehicleInfo.BODY_SPECIFICATION)
    @ValidateField(maxLength = 250)
    private String bodySpecification;

    @JsonProperty(VehicleInfo.BODY_SHAPE)
    @ValidateField(maxLength = 250)
    private String bodyShape;

    @JsonProperty(VehicleInfo.BODY_CONSTRUCTION)
    @ValidateField(maxLength = 250)
    private String bodyConstruction;

    @JsonProperty(VehicleInfo.STATUS)
    @ValidateField(carrierStatus = true)
    private Integer status;
    @JsonProperty(VehicleInfo.DELETE_FLAG)
    @ValidateField(carrierDeleteFlag = true)
    private Integer deleteFlag;
    @JsonProperty(VehicleInfo.NO_AVAIL_START_DATE)
    private String startDate;
    @JsonProperty(VehicleInfo.NO_AVAIL_END_DATE)
    private String endDate;
    @JsonProperty(VehicleInfo.NO_AVAIL_STATUS)
    private String noAvailStatus;
    @JsonProperty(VehicleInfo.GIAI)
    private String giai;
}
