package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送能力別の輸送計画明細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnsLineItemByDateByTrspAbility {

    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "service_no")
    private String serviceNo;

    @JsonProperty(value = "service_strt_date")
    private Date serviceStrtDate;

    @JsonProperty(value = "service_end_date")
    private Date serviceEndDate;

    @JsonProperty("service_strt_time")
    private Time serviceStrTime;

    @JsonProperty("service_end_time")
    private Time serviceEndTime;

    @JsonProperty("departure_from")
    private Long departureFrom;

    @JsonProperty("arrival_to")
    private Long arrivalTo;

    @JsonProperty("transport_name")
    private String transportName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "trsp_plan_line_item_id")
    private Long trspPlanLineItemId;

    @JsonProperty("created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DataBaseConstant.DATE_TIME_FORMAT.YYYYMMDDHHMMSS)
    private Timestamp createdDate;

    @JsonProperty("shipper_info")
    private Object shipperInfo;

    @JsonProperty("trsp_plan_line_item")
    private Object trspPlanLineItem;
}
