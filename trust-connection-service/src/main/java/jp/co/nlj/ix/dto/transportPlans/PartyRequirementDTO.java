package jp.co.nlj.ix.dto.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * パーティ要件DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class PartyRequirementDTO {

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
} 