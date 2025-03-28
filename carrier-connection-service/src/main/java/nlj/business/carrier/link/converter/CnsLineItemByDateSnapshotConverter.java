package nlj.business.carrier.link.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import nlj.business.carrier.link.dto.matching.CnsLineItemByDateSnapshot;

/**
 * <PRE>
 * データベース変換クラス
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Convert
public class CnsLineItemByDateSnapshotConverter implements AttributeConverter<CnsLineItemByDateSnapshot, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * データベースに保存するための変換.
     *
     * @param attribute 変換するオブジェクト
     * @return 変換後の文字列
     * @author Next Logistics Japan
     */
    @Override
    public String convertToDatabaseColumn(CnsLineItemByDateSnapshot attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * データベースから取得するための変換.
     *
     * @param dbData 変換する文字列
     * @return 変換後のオブジェクト
     * @author Next Logistics Japan
     */
    @Override
    public CnsLineItemByDateSnapshot convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, CnsLineItemByDateSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
