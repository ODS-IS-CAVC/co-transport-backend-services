package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送車輌条件通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspVehicleTrmsNotifyDTO {

    @JsonProperty("car_cls_of_size_cd")
    private String carClsOfSizeCd; // 車輌種別

    @JsonProperty("car_cls_of_shp_cd")
    private String carClsOfShpCd; // 平ボディ/バンボディ

    @JsonProperty("car_cls_of_tlg_lftr_exst_cd")
    private String carClsOfTlgLftrExstCd; // パワーゲート有無

    @JsonProperty("car_cls_of_wing_body_exst_cd")
    private String carClsOfWingBodyExstCd; // ウィング有無

    @JsonProperty("car_cls_of_rfg_exst_cd")
    private String carClsOfRfgExstCd; // 冷凍・冷蔵設備

    @JsonProperty("trms_of_lwr_tmp_meas")
    private BigDecimal trmsOfLwrTmpMeas; // 温度範囲（下限）

    @JsonProperty("trms_of_upp_tmp_meas")
    private BigDecimal trmsOfUppTmpMeas; // 温度範囲（上限）

    @JsonProperty("car_cls_of_crn_exst_cd")
    private String carClsOfCrnExstCd; // クレーン付

    @JsonProperty("car_rmk_about_eqpm_txt")
    private String carRmkAboutEqpmTxt; // 車輌設備補足


}
