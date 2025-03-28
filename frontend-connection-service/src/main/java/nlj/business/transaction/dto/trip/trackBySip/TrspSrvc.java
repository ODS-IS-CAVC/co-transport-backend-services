package nlj.business.transaction.dto.trip.trackBySip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 運送業者サービスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrspSrvc {

    @JsonProperty("car_license_plt_num_id")
    private String carLicensePltNumId;

    @JsonProperty("avb_date_cll_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String avbDateCllDate;

    @JsonProperty("avb_from_time_of_cll_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String avbFromTimeOfCllTime;

    @JsonProperty("aped_arr_to_date")
    @ValidateField(dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    private String apedArrToDate;

    @JsonProperty("aped_arr_to_time_prfm_dttm")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String apedArrToTimePrfmDttm;
}
