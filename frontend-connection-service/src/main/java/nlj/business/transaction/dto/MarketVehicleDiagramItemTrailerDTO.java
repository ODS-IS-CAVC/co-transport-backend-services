package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.carrier.VehicleDiagram;
import nlj.business.transaction.domain.carrier.VehicleDiagramAllocation;
import nlj.business.transaction.domain.carrier.VehicleDiagramItem;

/**
 * <PRE>
 * 市場輸送計画品目トレイラーDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class MarketVehicleDiagramItemTrailerDTO {

    private Long id;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.OPERATOR_ID)
    private String operatorId;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ID)
    private VehicleDiagram vehicleDiagram;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ITEM_ID)
    private VehicleDiagramItem vehicleDiagramItem;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_DIAGRAM_ALLOCATION_ID)
    private VehicleDiagramAllocation vehicleDiagramAllocation;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_INFO_ID)
    private Integer vehicleInfoId;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.TRIP_NAME)
    private String tripName;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.DAY)
    private LocalDate day;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.DEPARTURE_TIME)
    private LocalTime departureTime;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.PRICE)
    private Integer price;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_CODE)
    private String vehicleCode;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_NAME)
    private String vehicleName;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_TYPE)
    private Integer vehicleType;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.VEHICLE_SIZE)
    private Integer vehicleSize;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.TEMPERATURE_RANGE)
    private List<Integer> temperatureRange;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.MAX_PAYLOAD)
    private BigDecimal maxPayload;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.TOTAL_LENGTH)
    private BigDecimal totalLength;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.TOTAL_WIDTH)
    private BigDecimal totalWidth;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.TOTAL_HEIGHT)
    private BigDecimal totalHeight;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.GROUND_CLEARANCE)
    private BigDecimal groundClearance;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.DOOR_HEIGHT)
    private BigDecimal doorHeight;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.BODY_SPECIFICATION)
    private String bodySpecification;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.BODY_SHAPE)
    private String bodyShape;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.BODY_CONSTRUCTION)
    private String bodyConstruction;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.IMAGES)
    private String[] images;

    @JsonProperty(DataBaseConstant.MarketVehicleDiagramItemTrailer.STATUS)
    private Integer status;
}
