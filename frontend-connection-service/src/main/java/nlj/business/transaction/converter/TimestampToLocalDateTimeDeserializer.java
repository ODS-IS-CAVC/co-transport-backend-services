package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <PRE>
 * タイムスタンプをLocalDateTimeに変換するデシリアライザ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class TimestampToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    /**
     * タイムスタンプをLocalDateTimeに変換する。
     *
     * @param p    JsonParser
     * @param ctxt DeserializationContext
     * @return LocalDateTime
     * @throws IOException 入出力エラー
     */
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        long timestamp = p.getLongValue();
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }
}