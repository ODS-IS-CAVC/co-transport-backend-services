package nlj.business.transaction.dto.trip.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 運送業者プランDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlansDTO {

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

    @JsonProperty("is_shipper")
    private Boolean isShipper;

    @JsonProperty("shipper_cid")
    private String shipperCid;

    @JsonProperty("recipient_cid")
    private String recipientCid;

    @JsonProperty("carrier_cid")
    private String carrierCid;

    @JsonProperty("tractor_giai")
    private String tractorGiai;

    @JsonProperty("trailer_giai_list")
    private List<String> trailerGiaiList;

    @JsonProperty("req_from_time")
    private String reqFromTime;

    @JsonProperty("req_to_time")
    private String reqToTime;
}
