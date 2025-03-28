package jp.co.nlj.ttmi.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * データチャンネルSipDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class DataChannelTrspSrvcDTO {

    @JsonProperty("dsed_cll_from_date")
    private String dsedCllFromDate;

    @JsonProperty("dsed_cll_from_time")
    private String dsedCllFromTime;

    @JsonProperty("aped_arr_from_date")
    private String apedArrFromDate;

    @JsonProperty("aped_arr_from_time_prfm_dttm")
    private String apedArrFromTimePrfmDttm;
}
