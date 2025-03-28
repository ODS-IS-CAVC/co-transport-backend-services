package nlj.business.shipper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.convert.IntegerArrayToStringConverter;
import jakarta.persistence.Convert;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import nlj.business.shipper.aop.proxy.ValidateField;
import nlj.business.shipper.constant.DataBaseConstant;
import nlj.business.shipper.constant.ParamConstant;

/**
 * <PRE>
 * 輸送計画DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanDTO {

    @JsonProperty("transport_code")
    @ValidateField(maxLength = 20)
    private String transportCode;

    @JsonProperty("transport_name")
    @ValidateField(maxLength = 24)
    private String transportName;

    @JsonProperty("departure_from")
    @ValidateField(positiveNumber = true)
    private Long departureFrom;

    @JsonProperty("arrival_to")
    @ValidateField(positiveNumber = true)
    private Long arrivalTo;

    @JsonProperty("collection_date_from")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT, endDateField = "collectionDateTo")
    private String collectionDateFrom;

    @JsonProperty("collection_date_to")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String collectionDateTo;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    @JsonProperty("repeat_day")
    private Integer repeatDay;

    @JsonProperty("day_week")
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> dayWeek;

    @JsonProperty("collection_time_from")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, endTimeField = "collectionTimeTo")
    private String collectionTimeFrom;

    @JsonProperty("collection_time_to")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String collectionTimeTo;

    @JsonProperty("price_per_unit")
    @ValidateField(precision = 9, scale = 0)
    private Long pricePerUnit;

    @JsonProperty("vehicle_condition")
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> vehicleCondition;

    @JsonProperty("outer_package_code")
    private Long outerPackageCode;

    @JsonProperty("cargo_info_id")
    private Long cargoInfoId;

    @JsonProperty("cargo_name")
    private String cargoName;

    @JsonProperty("total_length")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalWidth;

    @JsonProperty("total_height")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalHeight;

    @JsonProperty("total_weight")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalWeight;

    @JsonProperty("special_instructions")
    @ValidateField(maxLength = 500)
    private String specialInstructions;

    @JsonProperty("origin_type")
    @ValidateField(allowedEnumValues = {
        ParamConstant.OriginType.ORIGIN_TYPE_1,
        ParamConstant.OriginType.ORIGIN_TYPE_2,
        ParamConstant.OriginType.ORIGIN_TYPE_3
    })
    private Integer originType;

    @JsonProperty("import_id")
    private Long importId;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("created_at")
    private String createdDate;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("is_private")
    private Boolean isPrivate;
}