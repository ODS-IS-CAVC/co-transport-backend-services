package nlj.business.carrier.link.dto.vehicleInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * <PRE>
 * 車両詳細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDetailsDTO {

    @JsonProperty("bed_height")
    @ValidateField(notNull = true, precision = 15, scale = 0, textHalfWidth = true)
    private BigDecimal bedHeight;

    @JsonProperty("cargo_height")
    @ValidateField(notNull = true, precision = 15, scale = 0, textHalfWidth = true)
    private BigDecimal cargoHeight;

    @JsonProperty("cargo_width")
    @ValidateField(notNull = true, precision = 15, scale = 0, textHalfWidth = true)
    private BigDecimal cargoWidth;

    @JsonProperty("cargo_length")
    @ValidateField(notNull = true, precision = 15, scale = 0, textHalfWidth = true)
    private BigDecimal cargoLength;

    @JsonProperty("max_cargo_capacity")
    @ValidateField(precision = 11, scale = 4, textHalfWidth = true)
    private BigDecimal maxCargoCapacity;

    @JsonProperty("body_type")
    @ValidateField(maxLength = 1, vanBodyType = true, textHalfWidth = true)
    private String bodyType;

    @JsonProperty("power_gate")
    @ValidateField(maxLength = 1, powerGate = true, textHalfWidth = true)
    private String powerGate;

    @JsonProperty("wing_doors")
    @ValidateField(maxLength = 1, textHalfWidth = true, wingDoor = true)
    private String wingDoors;

    @JsonProperty("refrigeration_unit")
    @ValidateField(notNull = true, maxLength = 1, textHalfWidth = true, refrigerationUnit = true)
    private String refrigerationUnit;

    @JsonProperty("temperature_range_min")
    @ValidateField(precision = 5, scale = 3, positiveNumber = false, textHalfWidth = true)
    private BigDecimal temperatureRangeMin;

    @JsonProperty("temperature_range_max")
    @ValidateField(precision = 5, scale = 3, positiveNumber = false, textHalfWidth = true, minField = "temperatureRangeMin")
    private BigDecimal temperatureRangeMax;

    @JsonProperty("crane_equipped")
    @ValidateField(maxLength = 1, textHalfWidth = true, craneEquipped = true)
    private String craneEquipped;

    @JsonProperty("vehicle_equipment_notes")
    @ValidateField(maxLength = 100, textFullWidth = true)
    private String vehicleEquipmentNotes;

    @JsonProperty("master_data_start_date")
    @ValidateField(notNull = true, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, endDateField = "masterDataEndDate")
    private String masterDataStartDate;

    @JsonProperty("master_data_end_date")
    @ValidateField(notNull = true, dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String masterDataEndDate;
}
