package jp.co.nlj.ix.dto.trspInstruction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 荷届先要件DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqShipToPrtyRqrmDTO {

    @JsonProperty("trms_of_car_size_cd")
    @ValidateField(maxLength = 1, textHalfWidth = true)
    private String toTrmsOfCarSizeCd; // 車輌種別制限

    @JsonProperty("trms_of_car_hght_meas")
    @ValidateField(maxLength = 5, textHalfWidth = true, onlyNumber = true)
    private String toTrmsOfCarHghtMeas; // 車輌高さ制限

    @JsonProperty("trms_of_gtp_cert_txt")
    @ValidateField(maxLength = 50, textFullWidth = true)
    private String toTrmsOfGtpCertTxt; // 入門条件（漢字）

    @JsonProperty("trms_of_del_txt")
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String trmsOfDelTxt; // 荷届条件（漢字）

    @JsonProperty("trms_of_gods_hnd_txt")
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String toTrmsOfGodsHndTxt; // 荷扱指示（漢字）

    @JsonProperty("anc_wrk_of_del_txt")
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String ancWrkOfDelTxt; // 配達附帯作業（漢字）

    @JsonProperty("spcl_wrk_txt")
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String toSpclWrkTxt; // 特別作業内容（漢字）
}
