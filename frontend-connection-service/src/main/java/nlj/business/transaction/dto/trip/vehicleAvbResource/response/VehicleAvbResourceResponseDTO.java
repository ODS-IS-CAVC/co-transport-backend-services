package nlj.business.transaction.dto.trip.vehicleAvbResource.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

/**
 * <PRE>
 * 車両可用性リソースレスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleAvbResourceResponseDTO {

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    private String trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_strt_area_cty_jis_cd")
    private String trspOpStrtAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_strt_date")
    private String trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time")
    private String trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    private String trspOpEndAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_cty_jis_cd")
    private String trspOpEndAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_end_date")
    private String trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    private String trspOpPlanDateTrmEndTime;

    @JsonProperty("car_info_id")
    private Long carInfoId;

    public static VehicleAvbResourceResponseDTO fromResult(Object[] result) {
        VehicleAvbResourceResponseDTO dto = new VehicleAvbResourceResponseDTO();
        dto.setTrspOpStrtAreaLineOneTxt((String) result[0]);
        dto.setTrspOpStrtAreaCtyJisCd((String) result[1]);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dto.setTrspOpDateTrmStrtDate(dateFormat.format((Date) result[2]));

        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
        dto.setTrspOpPlanDateTrmStrtTime(timeFormat.format(((Time) result[3])));

        dto.setTrspOpEndAreaLineOneTxt((String) result[4]);
        dto.setTrspOpEndAreaCtyJisCd((String) result[5]);

        dto.setTrspOpDateTrmEndDate(dateFormat.format((Date) result[6]));
        dto.setTrspOpPlanDateTrmEndTime(timeFormat.format(((Time) result[7])));
        dto.setCarInfoId((Long) result[8]);
        return dto;
    }

}

