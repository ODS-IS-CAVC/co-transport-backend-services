package nlj.business.transaction.dto.matching;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <PRE>
 * ヘッダレスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
public class HeadResponse {

    @JsonProperty("car_license_plt_num_id")
    private String carCtrlNumId;

    @JsonProperty("vehicle_name")
    private String vehicleName;
}
