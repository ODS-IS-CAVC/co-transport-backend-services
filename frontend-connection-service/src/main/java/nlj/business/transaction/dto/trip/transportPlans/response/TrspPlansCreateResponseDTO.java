package nlj.business.transaction.dto.trip.transportPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.transaction.dto.trip.transportPlans.TrspPlansDTO;

/**
 * <PRE>
 * 運送業者プラン作成レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlansCreateResponseDTO {

    @JsonProperty("transport_plans")
    private TrspPlansDTO transportPlans;

    @JsonProperty("result")
    private Boolean result;

    @JsonProperty("error_msg")
    private String errorMsg;

    @JsonProperty("mh")
    private String mh;

    @JsonProperty("mh_space_list")
    private List<String> mhSpaceList;
}
