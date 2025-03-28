package nlj.business.transaction.dto.trip.carInfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 車両情報レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarInfoResponseDTO {

    @JsonProperty("trsp_abillity_line_item_id")
    private Long trspAbillityLineItemId;

    public static CarInfoResponseDTO fromResult(Object[] result) {
        CarInfoResponseDTO dto = new CarInfoResponseDTO();
        dto.setTrspAbillityLineItemId((Long) result[0]);
        return dto;
    }
}
