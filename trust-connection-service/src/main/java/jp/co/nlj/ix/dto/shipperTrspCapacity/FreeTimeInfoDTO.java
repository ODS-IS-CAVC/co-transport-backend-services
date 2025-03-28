package jp.co.nlj.ix.dto.shipperTrspCapacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Data;

/**
 * <PRE>
 * 空き時間情報DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class FreeTimeInfoDTO {

    @JsonProperty("free_time")
    @ValidateField(precision = 5, scale = 1, textHalfWidth = true)
    private String freeTime;

    @JsonProperty("free_time_fee")
    @ValidateField(precision = 10, scale = 0, textHalfWidth = true)
    private String freeTimeFee;
}
