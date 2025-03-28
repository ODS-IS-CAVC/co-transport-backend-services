package jp.co.nlj.ix.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jp.co.nlj.ix.dto.CnsLineItemByDateSnapshot;

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

    @Override
    public String convertToDatabaseColumn(CnsLineItemByDateSnapshot attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CnsLineItemByDateSnapshot convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, CnsLineItemByDateSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
