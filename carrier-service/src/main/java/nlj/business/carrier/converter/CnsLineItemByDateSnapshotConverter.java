package nlj.business.carrier.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.next.logistic.util.BaseUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import nlj.business.carrier.dto.CnsLineItemByDateSnapshot;

/**
 * <PRE>
 * CnsLineItemByDateSnapshotコンバーター.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Convert
public class CnsLineItemByDateSnapshotConverter implements AttributeConverter<CnsLineItemByDateSnapshot, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * <PRE>
     * データベースに保存するための文字列に変換する.<BR>
     * </PRE>
     *
     * @param attribute CnsLineItemByDateSnapshot
     * @return データベースに保存するための文字列
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
     * <PRE>
     * データベースから取得した文字列をCnsLineItemByDateSnapshotに変換する.<BR>
     * </PRE>
     *
     * @param dbData データベースから取得した文字列
     * @return CnsLineItemByDateSnapshot
     */
    @Override
    public CnsLineItemByDateSnapshot convertToEntityAttribute(String dbData) {
        if (BaseUtil.isNull(dbData)) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, CnsLineItemByDateSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
