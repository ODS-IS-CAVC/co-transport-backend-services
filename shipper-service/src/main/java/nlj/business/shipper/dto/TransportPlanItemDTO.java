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
 * トランスポートプランのアイテムDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanItemDTO {

    @JsonProperty("transport_code")
    @ValidateField(notNull = true, maxLength = 20)
    private String transportCode;

    @JsonProperty("transport_name")
    @ValidateField(notNull = true, maxLength = 24)
    private String transportName;

    @JsonProperty("collection_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String collectionDate;

    @JsonProperty("collection_time_from")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM, endTimeField = "collectionTimeTo")
    private String collectionTimeFrom;

    @JsonProperty("collection_time_to")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String collectionTimeTo;

    @JsonProperty("departure_from")
    @ValidateField(positiveNumber = true)
    private Long departureFrom;

    @JsonProperty("arrival_to")
    @ValidateField(positiveNumber = true)
    private Long arrivalTo;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    @JsonProperty("trailer_number_rest")
    private Integer trailerNumberRest;

    @JsonProperty("price_per_unit")
    @ValidateField(precision = 9, scale = 0)
    private BigDecimal pricePerUnit;

    @JsonProperty("temp_range")
    @Convert(converter = IntegerArrayToStringConverter.class)
    private List<Integer> tempRange;

    @JsonProperty("outer_package_code")
    private Integer outerPackageCode;

    @JsonProperty("total_length")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalLength;

    @JsonProperty("total_width")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalWidth;

    @JsonProperty("total_height")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal totalHeight;

    @JsonProperty("weight")
    @ValidateField(precision = 12, scale = 3)
    private BigDecimal weight;

    @JsonProperty("special_instructions")
    private String specialInstructions;

    @JsonProperty("status")
    @ValidateField(allowedEnumValues = {
        ParamConstant.TransportPlanStatus.INIT,
        ParamConstant.TransportPlanStatus.MARKET,
        ParamConstant.TransportPlanStatus.AUTO_MATCHING,
        ParamConstant.TransportPlanStatus.PENDING,
        ParamConstant.TransportPlanStatus.CONTRACT,
        ParamConstant.TransportPlanStatus.PAYMENT,
        ParamConstant.TransportPlanStatus.SHIPPING,
        ParamConstant.TransportPlanStatus.COMPLETED
    })
    private Integer status;

    @JsonProperty("created_at")
    private String createdDate;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("cargo_info_id")
    private Long cargoInfoId;

    @JsonProperty("is_private")
    private Boolean isPrivate;

    @JsonProperty("trsp_plan_id")
    private Long trspPlanId;

    @JsonProperty("cns_line_item_by_date_id")
    private String cnsLineItemByDateId;
}