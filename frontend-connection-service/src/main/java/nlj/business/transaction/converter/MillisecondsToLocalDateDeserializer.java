package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * <PRE>
 * ミリ秒をLocalDateに変換するデシリアライザ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class MillisecondsToLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    /**
     * ミリ秒をLocalDateに変換する。
     *
     * @param jsonParser             JsonParser
     * @param deserializationContext DeserializationContext
     * @return LocalDate
     * @throws IOException      入出力エラー
     * @throws JacksonException デシリアライズエラー
     */
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JacksonException {
        if (jsonParser == null || jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        long timestamp = jsonParser.getLongValue();
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
