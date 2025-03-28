package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送業者取引予約DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransPreOrderDTO {

    private Long id;

    @JsonProperty(DataBaseConstant.TransPreOrder.CARRIER_OPERATOR_ID)
    private String carrierOperatorId;

    @JsonProperty(DataBaseConstant.TransPreOrder.SHIPPER_OPERATOR_ID)
    private String shipperOperatorId;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ID)
    private Long transportPlanId;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ITEM_ID)
    private Long transportPlanItemId;

    @JsonProperty(DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_ID)
    private Long vehicleDiagramItemId;

    @JsonProperty(DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_TRAILER_ID)
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRAILER_NUMBER)
    private BigDecimal trailerNumber;

    @JsonProperty(DataBaseConstant.TransPreOrder.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.TransPreOrder.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANSPORT_DATE)
    private LocalDate transportDate;

    @JsonProperty(DataBaseConstant.TransPreOrder.PRICE)
    private BigDecimal price;

    @JsonProperty(DataBaseConstant.TransPreOrder.COLLECTION_TIME_FROM)
    private LocalTime collectionTimeFrom;

    @JsonProperty(DataBaseConstant.TransPreOrder.COLLECTION_TIME_TO)
    private LocalTime collectionTimeTo;

    @JsonProperty(DataBaseConstant.TransPreOrder.STATUS)
    private Integer status;

    @JsonProperty(DataBaseConstant.TransPreOrder.TRANSPORT_PLAN_ITEM_SNAPSHOT)
    private TransportPlanItemSnapshot transportPlanItemSnapshot;

    @JsonProperty(DataBaseConstant.TransPreOrder.VEHICLE_DIAGRAM_ITEM_SNAPSHOT)
    private VehicleDiagramItemSnapshot vehicleDiagramItemSnapshot;

    @JsonManagedReference
    private TransMatchingDTO transMatchingDTO;
}
