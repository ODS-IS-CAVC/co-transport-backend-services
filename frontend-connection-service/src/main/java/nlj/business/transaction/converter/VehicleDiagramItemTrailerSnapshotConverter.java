package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import nlj.business.transaction.dto.VehicleDiagramItemTrailerSnapshot;

/**
 * <PRE>
 * 車両図アイテムスナップショットコンバーター。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Convert
public class VehicleDiagramItemTrailerSnapshotConverter implements
    AttributeConverter<VehicleDiagramItemTrailerSnapshot, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * VehicleDiagramItemTrailerSnapshotをデータベースに保存するための文字列に変換する。
     *
     * @param attribute VehicleDiagramItemTrailerSnapshot
     * @return 文字列
     */
    @Override
    public String convertToDatabaseColumn(VehicleDiagramItemTrailerSnapshot attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * データベースから取得した文字列をVehicleDiagramItemTrailerSnapshotに変換する。
     *
     * @param dbData データベースから取得した文字列
     * @return VehicleDiagramItemTrailerSnapshot
     */
    @Override
    public VehicleDiagramItemTrailerSnapshot convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VehicleDiagramItemTrailerSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
