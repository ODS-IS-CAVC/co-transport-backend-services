package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import nlj.business.transaction.dto.VehicleAvbResourceItemSnapshot;

/**
 * <PRE>
 * VehicleAvbResourceItemSnapshotデシリアライザ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class VehicleAvbResourceItemSnapshotDeserializer extends JsonDeserializer<VehicleAvbResourceItemSnapshot> {

    private final VehicleAvbResourceItemSnapshotConverter converter = new VehicleAvbResourceItemSnapshotConverter();

    /**
     * VehicleAvbResourceItemSnapshotをデシリアライズする。
     *
     * @param p    JsonParser
     * @param ctxt DeserializationContext
     * @return VehicleAvbResourceItemSnapshot
     * @throws IOException      入出力エラー
     * @throws JacksonException デシリアライズエラー
     */
    @Override
    public VehicleAvbResourceItemSnapshot deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {
        String json = p.getText();
        return converter.convertToEntityAttribute(json);
    }
}
