package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import nlj.business.transaction.dto.CnsLineItemByDateSnapshot;

/**
 * <PRE>
 * CnsLineItemByDateSnapshotデシリアライザ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class CnsLineItemByDateSnapshotDeserializer extends JsonDeserializer<CnsLineItemByDateSnapshot> {

    private final CnsLineItemByDateSnapshotConverter converter = new CnsLineItemByDateSnapshotConverter();

    /**
     * CnsLineItemByDateSnapshotをデシリアライズする。
     *
     * @param p    JsonParser
     * @param ctxt DeserializationContext
     * @return CnsLineItemByDateSnapshot
     * @throws IOException      入出力エラー
     * @throws JacksonException デシリアライズエラー
     */
    @Override
    public CnsLineItemByDateSnapshot deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {
        return converter.convertToEntityAttribute(p.getText());
    }
}
