package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.next.logistic.convert.IntegerArrayToStringConverter;
import com.next.logistic.util.BaseUtil;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * <PRE>
 * 文字列をIntegerのリストに変換するデシリアライザ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class StringToIntegerListDeserializer extends JsonDeserializer<List<Integer>> {

    /**
     * 文字列をIntegerのリストに変換する。
     *
     * @param p    JsonParser
     * @param ctxt DeserializationContext
     * @return List<Integer>
     * @throws IOException 入出力エラー
     */
    @Override
    public List<Integer> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p == null || BaseUtil.isNull(p.getValueAsString())) {
            return Collections.emptyList();
        }
        try {
            IntegerArrayToStringConverter integerArrayToStringConverter = new IntegerArrayToStringConverter();
            return integerArrayToStringConverter.convertToEntityAttribute(p.getValueAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
