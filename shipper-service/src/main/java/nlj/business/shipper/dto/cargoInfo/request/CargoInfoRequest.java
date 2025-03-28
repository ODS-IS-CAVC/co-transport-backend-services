package nlj.business.shipper.dto.cargoInfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.shipper.dto.cargoInfo.CargoInfoDTO;

/**
 * <PRE>
 * 荷物情報リクエスト。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CargoInfoRequest {

    @JsonProperty("cargo_info")
    private CargoInfoDTO cargoInfoDTO;
}
