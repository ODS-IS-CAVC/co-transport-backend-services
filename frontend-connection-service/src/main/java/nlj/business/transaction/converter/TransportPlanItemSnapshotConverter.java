package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import nlj.business.transaction.dto.TransportPlanItemSnapshot;

/**
 * <PRE>
 * 輸送計画項目スナップショットコンバータ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Convert
public class TransportPlanItemSnapshotConverter implements AttributeConverter<TransportPlanItemSnapshot, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * TransportPlanItemSnapshotをデータベースに保存するための文字列に変換する。
     *
     * @param attribute TransportPlanItemSnapshot
     * @return 文字列
     */
    @Override
    public String convertToDatabaseColumn(TransportPlanItemSnapshot attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * データベースから取得した文字列をTransportPlanItemSnapshotに変換する。
     *
     * @param dbData データベースから取得した文字列
     * @return TransportPlanItemSnapshot
     */
    @Override
    public TransportPlanItemSnapshot convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TransportPlanItemSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
