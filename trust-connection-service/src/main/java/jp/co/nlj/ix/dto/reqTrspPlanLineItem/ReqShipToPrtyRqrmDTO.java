package jp.co.nlj.ix.dto.reqTrspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.constant.DataBaseConstant;
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

    @JsonProperty(DataBaseConstant.ReqShipToPrtyRqrm.TO_TRMS_OF_CAR_SIZE_CD)
    @ValidateField(maxLength = 1, textHalfWidth = true)
    private String toTrmsOfCarSizeCd; // 車輌種別制限

    @JsonProperty(DataBaseConstant.ReqShipToPrtyRqrm.TO_TRMS_OF_CAR_HGHT_MEAS)
    @ValidateField(maxLength = 5, textHalfWidth = true, onlyNumber = true)
    private String toTrmsOfCarHghtMeas; // 車輌高さ制限

    @JsonProperty(DataBaseConstant.ReqShipToPrtyRqrm.TO_TRMS_OF_GTP_CERT_TXT)
    @ValidateField(maxLength = 50, textFullWidth = true)
    private String toTrmsOfGtpCertTxt; // 入門条件（漢字）

    @JsonProperty(DataBaseConstant.ReqShipToPrtyRqrm.TRMS_OF_DEL_TXT)
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String trmsOfDelTxt; // 荷届条件（漢字）

    @JsonProperty(DataBaseConstant.ReqShipToPrtyRqrm.TO_TRMS_OF_GODS_HND_TXT)
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String toTrmsOfGodsHndTxt; // 荷扱指示（漢字）

    @JsonProperty(DataBaseConstant.ReqShipToPrtyRqrm.ANC_WRK_OF_DEL_TXT)
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String ancWrkOfDelTxt; // 配達附帯作業（漢字）

    @JsonProperty(DataBaseConstant.ReqShipToPrtyRqrm.TO_SPCL_WRK_TXT)
    @ValidateField(maxLength = 40, textFullWidth = true)
    private String toSpclWrkTxt; // 特別作業内容（漢字）
}
