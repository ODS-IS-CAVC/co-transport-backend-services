package jp.co.nlj.ix.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import java.io.IOException;
import jp.co.nlj.ix.constant.MessageConstant.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <PRE>
 * StrictBooleanDeserializer.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StrictBooleanDeserializer extends JsonDeserializer<Boolean> {

    /**
     * Booleanをデシリアライズするメソッド。
     *
     * @param p    JsonParser
     * @param ctxt DeserializationContext
     * @return Boolean
     * @throws IOException
     */
    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        String fieldName = p.getCurrentName();

        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        }

        throw new NextWebException(
            new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(Validate.VALID_BOOLEAN), fieldName));
    }
} 
