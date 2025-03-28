package jp.co.nlj.ix.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jp.co.nlj.ix.dto.VehicleAvbResourceItemSnapshot;

/**
 * <PRE>
 * VehicleAvbResourceItemSnapshotConverterクラス データベースの列とエンティティ属性の変換を行います。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Convert
public class VehicleAvbResourceItemSnapshotConverter implements
    AttributeConverter<VehicleAvbResourceItemSnapshot, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public String convertToDatabaseColumn(VehicleAvbResourceItemSnapshot attribute) {
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
    public VehicleAvbResourceItemSnapshot convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, VehicleAvbResourceItemSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
