package jp.co.nlj.ttmi.dto.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送サービスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspSrvcDTO {

    @JsonProperty("car_license_plt_num_id")
    private String carLicensePltNumId;

    @JsonProperty("avb_date_cll_date")
    private String avbDateCllDate;

    @JsonProperty("avb_from_time_of_cll_time")
    private String avbFromTimeOfCllTime;

    @JsonProperty("aped_arr_to_date")
    private String apedArrToDate;

    @JsonProperty("aped_arr_to_time_prfm_dttm")
    private String apedArrToTimePrfmDttm;
}
