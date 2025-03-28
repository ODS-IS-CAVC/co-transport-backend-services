package nlj.business.transaction.dto.matching;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * パートナートレーラレスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerTrailerResponse {

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("car_license_plt_num_id")
    private String carLicensePltNumId;

    @JsonProperty("vehicle_avb_resource_id")
    private Long vehicleAvbResourceId;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("trip_name")
    private String tripName;

    @JsonProperty("vehicle_name")
    private String vehicleName;
}
