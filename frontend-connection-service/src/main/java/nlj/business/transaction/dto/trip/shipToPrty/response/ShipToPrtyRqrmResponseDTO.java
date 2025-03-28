package nlj.business.transaction.dto.trip.shipToPrty.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 到着地プライマリ要求レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipToPrtyRqrmResponseDTO {

    @JsonProperty("trms_of_car_size_cd")
    private String trmsOfCarSizeCd;

    @JsonProperty("trms_of_car_hght_meas")
    private String trmsOfCarHghtMeas;

    @JsonProperty("trms_of_gtp_cert_txt")
    private String trmsOfGtpCertTxt;

    @JsonProperty("trms_of_del_txt")
    private String trmsOfDelTxt;

    @JsonProperty("trms_of_gods_hnd_txt")
    private String trmsOfGodsHndTxt;

    @JsonProperty("anc_wrk_of_del_txt")
    private String ancWrkOfDelTxt;

    @JsonProperty("spcl_wrk_txt")
    private String spclWrkTxt;

    public static ShipToPrtyRqrmResponseDTO fromResult(Object[] result) {
        ShipToPrtyRqrmResponseDTO dto = new ShipToPrtyRqrmResponseDTO();

        if (result != null) {
            dto.setTrmsOfCarSizeCd((String) result[0]);
            dto.setTrmsOfCarHghtMeas((String) result[1]);
            dto.setTrmsOfGtpCertTxt((String) result[2]);
            dto.setTrmsOfDelTxt((String) result[3]);
            dto.setTrmsOfGodsHndTxt((String) result[4]);
            dto.setAncWrkOfDelTxt((String) result[5]);
            dto.setSpclWrkTxt((String) result[6]);
        }

        return dto;
    }
}
