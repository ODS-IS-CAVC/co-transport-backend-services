package jp.co.nlj.ix.dto.transportPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlansRequestDTO {

    @JsonProperty("trsp_instruction_id")
    private String trspInstructionId;

    @JsonProperty("registration_number")
    private String registrationNumber;

    @JsonProperty("registration_transport_office_name")
    private String registrationTransportOfficeName;

    @JsonProperty("registration_vehicle_type")
    private String registrationVehicleType;

    @JsonProperty("registration_vehicle_use")
    private String registrationVehicleUse;

    @JsonProperty("registration_vehicle_id")
    private String registrationVehicleId;

    @JsonProperty("chassis_number")
    private String chassisNumber;

    @JsonProperty("vehicle_id")
    private String vehicleId;

    @JsonProperty("operator_corporate_number")
    private String operatorCorporateNumber;

    @JsonProperty("operator_business_code")
    private String operatorBusinessCode;

    @JsonProperty("owner_corporate_number")
    private String ownerCorporateNumber;

    @JsonProperty("owner_business_code")
    private String ownerBusinessCode;

    @JsonProperty("vehicle_type")
    private String vehicleType;

    @JsonProperty("hazardous_material_vehicle_type")
    private String hazardousMaterialVehicleType;

    @JsonProperty("tractor")
    private String tractor;

    @JsonProperty("trailer")
    private String trailer;

    @JsonProperty("cid")
    private String cid;
}
