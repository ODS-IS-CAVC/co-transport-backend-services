package nlj.business.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <PRE>
 * トランザクションレスポンスデータ共通DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@JsonInclude(Include.NON_NULL)
public class TransactionResponseDataCommon<T> {

    private T data;

    public TransactionResponseDataCommon() {
    }


    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
