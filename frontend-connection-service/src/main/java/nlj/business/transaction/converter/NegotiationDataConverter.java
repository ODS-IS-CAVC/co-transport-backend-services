package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.next.logistic.util.BaseUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import nlj.business.transaction.dto.NegotiationData;

/**
 * <PRE>
 * NegotiationDataコンバータ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Convert
public class NegotiationDataConverter implements AttributeConverter<NegotiationData, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * NegotiationDataをデータベースに保存するための文字列に変換する。
     *
     * @param attribute NegotiationData
     * @return 文字列
     */
    @Override
    public String convertToDatabaseColumn(NegotiationData attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * データベースから取得した文字列をNegotiationDataに変換する。
     *
     * @param dbData データベースから取得した文字列
     * @return NegotiationData
     */
    @Override
    public NegotiationData convertToEntityAttribute(String dbData) {
        if (BaseUtil.isNull(dbData)) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, NegotiationData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
