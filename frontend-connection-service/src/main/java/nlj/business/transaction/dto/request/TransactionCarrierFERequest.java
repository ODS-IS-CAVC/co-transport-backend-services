package nlj.business.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.constant.ParamConstant;
import org.springframework.http.HttpStatus;

/**
 * <PRE>
 * 運送業者トランザクションF&EリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCarrierFERequest {

    @JsonProperty("cns_line_item_id")
    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String cnsLineItemId;

    @JsonProperty("req_cns_line_item_id")
    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String reqCnsLineItemId;

    @JsonProperty("cns_line_item_by_date_id")
    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String cnsLineItemByDateId;

    @JsonProperty("vehicle_avb_resource_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String vehicleAvbResourceId;

    @JsonProperty("vehicle_avb_resource_item_id")
    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String vehicleAvbResourceItemId;

    @JsonProperty("trans_type")
    @ValidateField(notNull = true, transType = true)
    private String transType;

    @JsonProperty("departure_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String departureTime;

    @JsonProperty("arrival_time")
    @ValidateField(timeFormat = DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM)
    private String arrivalTime;

    @ValidateField(precision = Integer.MAX_VALUE, scale = 0)
    private String price;

    @JsonProperty("negotiation")
    @ValidateField(notNull = true)
    private CarrierNegotiationDTO negotiationDTO;

    public void validateTransTypeField() {
        if (this.transType.equals(ParamConstant.TransType.CARRIER_CARRIER.toString())
            && BaseUtil.isNull(this.cnsLineItemId)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(MessageConstant.Validate.VALID_NOT_NULL),
                    "cns_line_item_id"));
        }
        if (this.transType.equals(ParamConstant.TransType.CARRIER_SHIPPER.toString())
            && BaseUtil.isNull(this.reqCnsLineItemId)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(MessageConstant.Validate.VALID_NOT_NULL),
                    "req_cns_line_item_id"));
        }
    }

}
