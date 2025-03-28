package jp.co.nlj.gateway.dto.reservation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * ReservationRequestDTOクラスは、予約リクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    private String dataModelType;
    private AttributeRequestDTO attribute;

    public ReservationRequestDTO(ReservationRequestDTO request) {
        this.dataModelType = request.dataModelType;
        this.attribute = request.attribute;
    }
}
