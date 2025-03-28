package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 出荷場所要件通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipFromPrtyRqrmNotifyDTO {

    @JsonProperty("trms_of_car_size_cd")
    private String trmsOfCarSizeCd; // 車輌種別制限

    @JsonProperty("trms_of_car_hght_meas")
    private String trmsOfCarHghtMeas; // 車輌高さ制限

    @JsonProperty("trms_of_gtp_cert_txt")
    private String trmsOfGtpCertTxt; // 入門条件（漢字）

    @JsonProperty("trms_of_cll_txt")
    private String trmsOfCllTxt; // 集荷条件（漢字）

    @JsonProperty("trms_of_gods_hnd_txt")
    private String trmsOfGodsHndTxt; // 荷扱指示（漢字）

    @JsonProperty("anc_wrk_of_cll_txt")
    private String ancWrkOfCllTxt; // 集荷附帯作業（漢字）

    @JsonProperty("spcl_wrk_txt")
    private String spclWrkTxt; // 特別作業内容（漢字）
}
