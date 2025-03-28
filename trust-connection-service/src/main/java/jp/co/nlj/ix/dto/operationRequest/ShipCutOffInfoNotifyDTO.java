package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jp.co.nlj.ix.constant.DataBaseConstant.ShipCutOffInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 出荷停止情報通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipCutOffInfoNotifyDTO {

    @JsonProperty(ShipCutOffInfo.CUT_OFF_TIME)
    private BigDecimal cutOffTime;
    @JsonProperty(ShipCutOffInfo.CUT_OFF_FEE)
    private BigDecimal cutOffFee;
}
