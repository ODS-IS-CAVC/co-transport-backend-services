package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import nlj.business.transaction.dto.NegotiationData;

/**
 * <PRE>
 * NegotiationDataデシリアライザ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class NegotiationDataDeserializer extends JsonDeserializer<NegotiationData> {

    private final NegotiationDataConverter converter = new NegotiationDataConverter();

    /**
     * NegotiationDataをデシリアライズする。
     *
     * @param jsonParser             JsonParser
     * @param deserializationContext DeserializationContext
     * @return NegotiationData
     * @throws IOException      入出力エラー
     * @throws JacksonException デシリアライズエラー
     */
    @Override
    public NegotiationData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JacksonException {
        return converter.convertToEntityAttribute(jsonParser.getText());
    }
}
