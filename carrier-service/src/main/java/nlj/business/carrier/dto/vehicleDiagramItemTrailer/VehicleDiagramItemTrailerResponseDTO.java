package nlj.business.carrier.dto.vehicleDiagramItemTrailer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import lombok.Data;

/**
 * <PRE>
 * 車両図面アイテムトレーラーレスポンスDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class VehicleDiagramItemTrailerResponseDTO {

    @Id
    private Long id;

    @JsonProperty("service_no")
    private Long serviceNo;

    @JsonProperty("service_strt_date")
    private Date serviceStrtDate;

    @JsonProperty("service_strt_time")
    private Time serviceStrtTime;

    @JsonProperty("service_end_date")
    private Date serviceEndDate;

    @JsonProperty("service_end_time")
    private Time serviceEndTime;

    @JsonProperty("freight_rate")
    private BigDecimal freightRate;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("car_lngh_meas")
    private BigDecimal carLnghMeas;

    @JsonProperty("car_wdth_meas")
    private BigDecimal carWdthMeas;

    @JsonProperty("car_hght_meas")
    private BigDecimal carHghtMeas;

    @JsonProperty("car_max_load_capacity1_meas")
    private BigDecimal carMaxLoadCapacity1Meas;

    @JsonProperty("trsp_op_strt_area_cty_jis_cd")
    private Long trspOpStrtAreaCtyJisCd;

    @JsonProperty("trsp_op_end_area_cty_jis_cd")
    private Long trspOpEndAreaCtyJisCd;

    @JsonProperty("trsp_op_date_trm_strt_date")
    private Date trspOpDateTrmStrtDate;

    @JsonProperty("trsp_op_plan_date_trm_strt_time ")
    private Time trspOpPlanDateTrmStrtTime;

    @JsonProperty("trsp_op_date_trm_end_date")
    private Date trspOpDateTrmEndDate;

    @JsonProperty("trsp_op_plan_date_trm_end_time")
    private Time trspOpPlanDateTrmEndTime;

    @JsonProperty("cut_off_price")
    private String cutOffPrice;

    @JsonProperty("tractor_idcr")
    private Integer tractorIdcr;

    @JsonProperty("registration_group_number")
    private String registrationGroupNumber;

    @JsonProperty("registration_character")
    private Character registrationCharacter;

    @JsonProperty("registration_number_1")
    private String registrationNumber1;


    @JsonProperty("registration_number_2")
    private String registrationNumber2;

    @JsonProperty("operator_code")
    private String operatorCode;

    @JsonProperty("trsp_op_strt_area_line_one_txt")
    private Long trspOpStrtAreaLineOneTxt;

    @JsonProperty("trsp_op_end_area_line_one_txt")
    private Long trspOpEndAreaLineOneTxt;

    @JsonProperty("vehicle_diagram_item_trailler_id")
    private Long vehicleDiagramItemTrailerId;

    @JsonProperty("vehicle_diagram_id")
    private Long vehicleDiagramId;

    @JsonProperty("giai")
    private String giai;

    @JsonProperty("temperature_range")
    private String temperatureRange;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("registration_area_code")
    private String registrationAreaCode;

    @JsonProperty("display_order")
    private Integer displayOrder;

    public static VehicleDiagramItemTrailerResponseDTO fromResult(Object[] result) {
        VehicleDiagramItemTrailerResponseDTO dto = new VehicleDiagramItemTrailerResponseDTO();
        dto.setServiceNo((Long) result[0]);
        dto.setServiceStrtDate((Date) result[1]);
        dto.setServiceStrtTime((Time) result[2]);
        dto.setServiceEndDate((Date) result[3]);
        dto.setServiceEndTime((Time) result[4]);
        dto.setFreightRate((BigDecimal) result[5]);
        dto.setServiceName((String) result[6]);
        dto.setCarLnghMeas((BigDecimal) result[7]);
        dto.setCarWdthMeas((BigDecimal) result[8]);
        dto.setCarHghtMeas((BigDecimal) result[9]);
        dto.setCarMaxLoadCapacity1Meas((BigDecimal) result[10]);
        dto.setTrspOpStrtAreaCtyJisCd((Long) result[11]);
        dto.setTrspOpEndAreaCtyJisCd((Long) result[12]);
        dto.setTrspOpDateTrmStrtDate((Date) result[13]);
        dto.setTrspOpPlanDateTrmStrtTime((Time) result[14]);
        dto.setTrspOpDateTrmEndDate((Date) result[15]);
        dto.setTrspOpPlanDateTrmEndTime((Time) result[16]);
        dto.setCutOffPrice((String) result[17]);
        dto.setTractorIdcr((Integer) result[18]);
        dto.setRegistrationGroupNumber((String) result[19]);
        dto.setRegistrationCharacter((Character) result[20]);
        dto.setRegistrationNumber1((String) result[21]);
        dto.setRegistrationNumber2((String) result[22]);
        dto.setOperatorCode((String) result[23]);
        dto.setTrspOpStrtAreaLineOneTxt((Long) result[24]);
        dto.setTrspOpEndAreaLineOneTxt((Long) result[25]);
        dto.setVehicleDiagramItemTrailerId((Long) result[26]);
        dto.setVehicleDiagramId((Long) result[27]);
        dto.setGiai((String) result[28]);
        dto.setTemperatureRange((String) result[29]);
        dto.setVehicleName((String) result[30]);
        dto.setRegistrationAreaCode((String) result[31]);
        dto.setDisplayOrder((Integer) result[32]);
        return dto;
    }

}
