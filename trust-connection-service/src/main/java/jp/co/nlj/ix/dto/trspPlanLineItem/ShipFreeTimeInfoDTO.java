package jp.co.nlj.ix.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.constant.DataBaseConstant.ShipFreeTimeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 船のフリータイム情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipFreeTimeInfoDTO {

    @JsonProperty(ShipFreeTimeInfo.FREE_TIME)
    @ValidateField(precision = 5, scale = 1, textHalfWidth = true)
    private BigDecimal freeTime;
    @JsonProperty(ShipFreeTimeInfo.FREE_TIME_FEE)
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private BigDecimal freeTimeFee;
}
