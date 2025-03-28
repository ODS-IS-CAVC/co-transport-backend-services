package nlj.business.carrier.link.dto.reqTrspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;
import nlj.business.carrier.link.constant.DataBaseConstant;

/**
 * 出荷場所要件DTO.<BR>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqShipFromPrtyRqrmDTO {

    @JsonProperty(DataBaseConstant.ReqShipFromPrtyRqrm.FROM_TRMS_OF_CAR_SIZE_CD)
    @ValidateField(maxLength = 1, textHalfWidth = true)
    private String fromTrmsOfCarSizeCd; // 車輌種別制限

    @JsonProperty(DataBaseConstant.ReqShipFromPrtyRqrm.FROM_TRMS_OF_CAR_HGHT_MEAS)
    @ValidateField(maxLength = 5, textHalfWidth = true, onlyNumber = true)
    private String fromTrmsOfCarHghtMeas; // 車輌高さ制限

    @JsonProperty(DataBaseConstant.ReqShipFromPrtyRqrm.FROM_TRMS_OF_GTP_CERT_TXT)
    @ValidateField(maxLength = 50, textFullWidth = true)
    private String fromTrmsOfGtpCertTxt; // 入門条件（漢字）

    @JsonProperty(DataBaseConstant.ReqShipFromPrtyRqrm.TRMS_OF_CLL_TXT)
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String trmsOfCllTxt; // 集荷条件（漢字）

    @JsonProperty(DataBaseConstant.ReqShipFromPrtyRqrm.FROM_TRMS_OF_GODS_HND_TXT)
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String fromTrmsOfGodsHndTxt; // 荷扱指示（漢字）

    @JsonProperty(DataBaseConstant.ReqShipFromPrtyRqrm.ANC_WRK_OF_CLL_TXT)
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String ancWrkOfCllTxt; // 集荷附帯作業（漢字）

    @JsonProperty(DataBaseConstant.ReqShipFromPrtyRqrm.FROM_SPCL_WRK_TXT)
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String fromSpclWrkTxt; // 特別作業内容（漢字）
}
