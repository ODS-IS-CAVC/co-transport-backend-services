package nlj.business.carrier.link.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 車両情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleInfoDTO {

    @JsonProperty("registration_number")
    @ValidateField(notNull = true, maxLength = 24, textFullWidth = true, registrationNumber = true)
    private String registrationNumber;

    @JsonProperty("giai")
    @ValidateField(notNull = true, maxLength = 50, textHalfWidth = true)
    private String giai;

    @JsonProperty("registration_transport_office_name")
    @ValidateField(notNull = true, maxLength = 8, textFullWidth = true)
    private String registrationTransportOfficeName;

    @JsonProperty("registration_vehicle_type")
    @ValidateField(notNull = true, maxLength = 6, textFullWidth = true)
    private String registrationVehicleType;

    @JsonProperty("registration_vehicle_use")
    @ValidateField(notNull = true, maxLength = 2, textFullWidth = true)
    private String registrationVehicleUse;

    @JsonProperty("registration_vehicle_id")
    @ValidateField(notNull = true, maxLength = 8, textFullWidth = true)
    private String registrationVehicleId;

    @JsonProperty("chassis_number")
    @ValidateField(maxLength = 42, textHalfWidth = true)
    private String chassisNumber;

    @JsonProperty("vehicle_id")
    @ValidateField(maxLength = 20, textHalfWidth = true)
    private String vehicleId;

    @JsonProperty("operator_corporate_number")
    @ValidateField(notNull = true, maxLength = 13, textHalfWidth = true)
    private String operatorCorporateNumber;

    @JsonProperty("operator_business_code")
    @ValidateField(maxLength = 17, textHalfWidth = true)
    private String operatorBusinessCode;

    @JsonProperty("owner_corporate_number")
    @ValidateField(maxLength = 13, textHalfWidth = true)
    private String ownerCorporateNumber;

    @JsonProperty("owner_business_code")
    @ValidateField(maxLength = 17, textHalfWidth = true)
    private String ownerBusinessCode;

    @JsonProperty("vehicle_type")
    @ValidateField(notNull = true, maxLength = 1, vehicleType = true, textHalfWidth = true)
    private String vehicleType;

    @JsonProperty("hazardous_material_vehicle_type")
    @ValidateField(maxLength = 1, onlyNumber = true, hazardousMaterialVehicleType = true, textHalfWidth = true)
    private String hazardousMaterialVehicleType;

    @JsonProperty("tractor")
    @ValidateField(maxLength = 1, tractorType = true, textHalfWidth = true)
    private String tractor;

    @JsonProperty("trailer")
    @ValidateField(maxLength = 24, textFullWidth = true)
    private String trailer;
}
