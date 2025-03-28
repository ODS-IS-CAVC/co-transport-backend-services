package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.constant.DataBaseConstant.ShipFreeTimeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 船のフリータイム情報通知DTO。<BR>
 * </PRE>
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipFreeTimeInfoNotifyDTO {

    @JsonProperty(ShipFreeTimeInfo.FREE_TIME)
    private BigDecimal freeTime;
    @JsonProperty(ShipFreeTimeInfo.FREE_TIME_FEE)
    private BigDecimal freeTimeFee;
}
