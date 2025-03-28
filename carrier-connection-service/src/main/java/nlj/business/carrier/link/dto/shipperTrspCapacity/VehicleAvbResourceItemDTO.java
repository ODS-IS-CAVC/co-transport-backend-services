package nlj.business.carrier.link.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Convert;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両可利用リソースアイテムDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class VehicleAvbResourceItemDTO {

    private Long id;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.OPERATOR_ID)
    private String operatorId;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.VEHICLE_AVB_RESOURCE_ID)
    private Long vehicleAvbResourceItemId;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.DEPARTURE_FROM)
    private Long departureFrom;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.ARRIVAL_TO)
    private Long arrivalTo;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.DAY)
    private LocalDate day;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.TRIP_NAME)
    private String tripName;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.DEPARTURE_TIME_MIN)
    private LocalTime departureTime;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.ARRIVAL_TIME)
    private LocalTime arrivalTime;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.ADJUSTMENT_PRICE)
    private Integer adjustmentPrice;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.VEHICLE_TYPE)
    private Integer vehicleType;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.DISPLAY_ORDER)
    private Integer displayOrder;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.ASSIGN_TYPE)
    private Integer assignType;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.MAX_PAYLOAD)
    private BigDecimal maxPayload;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.TOTAL_LENGTH)
    private BigDecimal totalLength;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.TOTAL_WIDTH)
    private BigDecimal totalWidth;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.TOTAL_HEIGHT)
    private BigDecimal totalHeight;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.VEHICLE_SIZE)
    private Integer vehicleSize;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.TEMPERATURE_RANGE)
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> temperatureRange;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.PRICE)
    private BigDecimal price;

    @JsonProperty(DataBaseConstant.VehicleAvbResourceItem.STATUS)
    private Integer status;
}
