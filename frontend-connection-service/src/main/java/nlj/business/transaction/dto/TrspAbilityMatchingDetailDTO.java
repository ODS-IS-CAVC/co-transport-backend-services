package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 運送能力マッチング詳細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrspAbilityMatchingDetailDTO extends CnsLineItemByDateSnapshot {

    @JsonProperty("vehicle_avb_resource_item_id")
    private Long vehicleAvbResourceItemId;

    @JsonProperty("parent_order_propose_snapshot")
    private VehicleAvbResourceItemSnapshot parentOrderProposeSnapshot;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("carrier_operator_id")
    private String carrierOperatorId;

    @JsonProperty("carrier_operator_name")
    private String carrierOperatorName;

    @JsonProperty("carrier2_operator_id")
    private String carrier2OperatorId;

    @JsonProperty("carrier2_operator_name")
    private String carrier2OperatorName;

    @JsonProperty("shipper_operator_id")
    private String shipperOperatorId;
}
