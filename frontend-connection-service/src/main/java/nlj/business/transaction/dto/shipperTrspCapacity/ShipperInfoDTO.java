package nlj.business.transaction.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.converter.StringToIntegerListDeserializer;

/**
 * <PRE>
 * 運送業者情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipperInfoDTO {

    private Long id;

    @JsonProperty("operator_name")
    private String operatorName;

    @JsonProperty("outer_package_code")
    private String outerPackageCode;

    @JsonProperty("collection_date")
    private LocalDate collectionDate;

    @JsonProperty("collection_time_from")
    private Time collectionTimeFrom;

    @JsonProperty("collection_time_to")
    private Time collectionTimeTo;

    @JsonProperty("special_instructions")
    private String specialInstructions;

    @JsonProperty("temperature_range")
    @JsonDeserialize(using = StringToIntegerListDeserializer.class)
    private List<Integer> temperatureRange;
}
