package nlj.business.carrier.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.next.logistic.util.BaseUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import nlj.business.carrier.dto.VehicleAvbResourceItemSnapshot;

/**
 * <PRE>
 * 車両可動資源アイテムスナップショットコンバーター.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Convert
public class VehicleAvbResourceItemSnapshotConverter implements
    AttributeConverter<VehicleAvbResourceItemSnapshot, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * <PRE>
     * データベースに保存するための文字列に変換する.<BR>
     * </PRE>
     *
     * @param attribute VehicleAvbResourceItemSnapshot
     * @return データベースに保存するための文字列
     */
    @Override
    public String convertToDatabaseColumn(VehicleAvbResourceItemSnapshot attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <PRE>
     * データベースから取得した文字列をVehicleAvbResourceItemSnapshotに変換する.<BR>
     * </PRE>
     *
     * @param dbData データベースから取得した文字列
     * @return VehicleAvbResourceItemSnapshot
     */
    @Override
    public VehicleAvbResourceItemSnapshot convertToEntityAttribute(String dbData) {
        try {
            if (BaseUtil.isNull(dbData)) {
                return null;
            }
            return objectMapper.readValue(dbData, VehicleAvbResourceItemSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
