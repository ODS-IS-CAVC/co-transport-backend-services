package jp.co.nlj.ix.dto.shipperOperationPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.config.StrictBooleanDeserializer;
import lombok.Data;

/**
 * <PRE>
 * 荷主向け運行申し込み諾否回答通知リクエストDTO
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipperOperationApprovalRequestDTO {

    @JsonProperty("operation_id")
    @ValidateField(notNull = true, maxLength = 20, textHalfWidth = true)
    private String operationId;

    @JsonProperty("approval")
    @JsonDeserialize(using = StrictBooleanDeserializer.class)
    @ValidateField(notNull = true, inputIsArrOrBoolean = false)
    private Boolean approval;
}
