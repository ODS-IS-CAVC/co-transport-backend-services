package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import nlj.business.transaction.dto.VehicleDiagramItemSnapshot;

/**
 * <PRE>
 * 車両図アイテムスナップショットコンバーター。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Convert
public class VehicleDiagramItemSnapshotConverter implements AttributeConverter<VehicleDiagramItemSnapshot, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * VehicleDiagramItemSnapshotをデータベースに保存するための文字列に変換する。
     *
     * @param attribute VehicleDiagramItemSnapshot
     * @return 文字列
     */
    @Override
    public String convertToDatabaseColumn(VehicleDiagramItemSnapshot attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * データベースから取得した文字列をVehicleDiagramItemSnapshotに変換する。
     *
     * @param dbData データベースから取得した文字列
     * @return VehicleDiagramItemSnapshot
     */
    @Override
    public VehicleDiagramItemSnapshot convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VehicleDiagramItemSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
