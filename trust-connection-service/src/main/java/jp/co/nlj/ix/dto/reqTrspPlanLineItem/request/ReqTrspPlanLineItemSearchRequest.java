package jp.co.nlj.ix.dto.reqTrspPlanLineItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.constant.DataBaseConstant.DATE_TIME_FORMAT;
import lombok.Data;


/**
 * <PRE>
 * 輸送計画明細検索リクエストのリクエスト。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ReqTrspPlanLineItemSearchRequest {

    @JsonProperty("from_plc_cd_prty_id")
    @ValidateField(maxLength = 4, textHalfWidth = true)
    private String fromPlcCdPrtyId; // 便・ダイヤ番号

    @JsonProperty("to_plc_cd_prty_id")
    @ValidateField(maxLength = 4, textHalfWidth = true)
    private String toPlcCdPrtyId; // 便・ダイヤ名称

    @JsonProperty("istd_totl_pcks_quan")
    @ValidateField(precision = 9, scale = 0, textHalfWidth = true)
    private BigDecimal istdTotlPcksQuan; // 最大積載量1

    @JsonProperty("dsed_cll_from_date")
    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true, endDateField = "dsedCllToDate")
    private String dsedCllFromDate; // 運行開始地域

    @JsonProperty("dsed_cll_to_date")
    @ValidateField(dateFormat = DATE_TIME_FORMAT.DATE_FORMAT, textHalfWidth = true)
    private String dsedCllToDate; // 運行終了地域

}
