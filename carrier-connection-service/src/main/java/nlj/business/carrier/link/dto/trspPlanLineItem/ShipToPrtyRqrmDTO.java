package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 荷届先要件DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipToPrtyRqrmDTO {

    @JsonProperty("trms_of_car_size_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true)
    private String trmsOfCarSizeCd; // 車輌種別制限

    @JsonProperty("trms_of_car_hght_meas")
    @ValidateField(maxLength = 5, textHalfWidth = true, onlyNumber = true)
    private String trmsOfCarHghtMeas; // 車輌高さ制限

    @JsonProperty("trms_of_gtp_cert_txt")
    @ValidateField(maxLength = 50, textFullWidth = true)
    private String trmsOfGtpCertTxt; // 入門条件（漢字）

    @JsonProperty("trms_of_del_txt")
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String trmsOfDelTxt; // 荷届条件（漢字）

    @JsonProperty("trms_of_gods_hnd_txt")
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String trmsOfGodsHndTxt; // 荷扱指示（漢字）

    @JsonProperty("anc_wrk_of_del_txt")
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String ancWrkOfDelTxt; // 配達附帯作業（漢字）

    @JsonProperty("spcl_wrk_txt")
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String spclWrkTxt; // 特別作業内容（漢字）
}
