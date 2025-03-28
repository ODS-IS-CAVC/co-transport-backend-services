package jp.co.nlj.ix.dto.transportPlans.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import jp.co.nlj.ix.dto.transportPlans.TrspPlansDTO;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画作成応答DTO。<BR>
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
