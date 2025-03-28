package nlj.business.transaction.dto.matching;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.dto.CnsLineItemByDateSnapshot;
import nlj.business.transaction.dto.VehicleAvbResourceItemSnapshot;

/**
 * <PRE>
 * トレーラプランIDDTOレスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrspPlanIdDTOResponse {

    @JsonProperty(DataBaseConstant.TransMatching.ID)
    private Long id;

    @JsonProperty(DataBaseConstant.TransMatching.TRANS_TYPE)
    private Integer transType;

    @JsonProperty(DataBaseConstant.AbstractAuditingEntity.CREATED_USER)
    private String createdUser;

    @JsonProperty(DataBaseConstant.AbstractAuditingEntity.CREATED_DATE)
    private String createdDate;

    @JsonProperty(DataBaseConstant.AbstractAuditingEntity.UPDATED_USER)
    private String updatedUser;

    @JsonProperty(DataBaseConstant.AbstractAuditingEntity.UPDATED_DATE)
    private String updatedDate;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER_OPERATOR_ID)
    private String carrierOperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_ID)
    private String shipperOperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER_OPERATOR_CODE)
    private String carrierOperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_CODE)
    private String shipperOperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER2_OPERATOR_ID)
    private String carrier2OperatorId;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER2_OPERATOR_CODE)
    private String carrier2OperatorCode;

    @JsonProperty(DataBaseConstant.TransMatching.BATCH_ID)
    private Long batchId;

    @JsonProperty(DataBaseConstant.TransMatching.CNS_LINE_ITEM_BY_DATE_ID)
    private Long cnsLineItemByDateId;

    @JsonProperty(DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ITEM_ID)
    private Long vehicleAvbResourceItemId;

    @JsonProperty(DataBaseConstant.TransMatching.TRAILER_NUMBER)
    private BigDecimal trailerNumber;

    @JsonProperty(DataBaseConstant.TransMatching.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.TransMatching.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.TransMatching.TRANSPORT_DATE)
    private String transportDate;

    @JsonProperty(DataBaseConstant.TransMatching.STATUS)
    private Integer status;

    @JsonProperty(DataBaseConstant.TransMatching.REQUEST_SNAPSHOT)
    private CnsLineItemByDateSnapshot requestSnapshot;

    @JsonProperty(DataBaseConstant.TransMatching.PROPOSE_SNAPSHOT)
    private VehicleAvbResourceItemSnapshot proposeSnapshot;

    @JsonProperty(DataBaseConstant.TransportAbilityLineItem.ROAD_CARR_DEPA_SPED_ORG_NAME_TXT)
    private String logsSrvcPrvPrtyNameTxt;

    @JsonProperty(DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_TEL_CMM_CMP_NUM_TXT)
    private String logsSrvcPrvSctSpedOrgNameTxt;

    @JsonProperty(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_NAME)
    private String shipperOperatorName;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER_OPERATOR_NAME)
    private String carrierOperatorName;

    @JsonProperty(DataBaseConstant.TransMatching.CARRIER2_OPERATOR_NAME)
    private String carrier2OperatorName;

    @JsonProperty("trailer")
    private TrspPlanTrailerResponse trailer;

    @JsonProperty("parent_order_propose_snapshot")
    private VehicleAvbResourceItemSnapshot parentOrderProposeSnapshot;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("trailer_license_plt_num_id")
    private String trailerLicensePltNumId;
}
