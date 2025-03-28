package jp.co.nlj.gateway.dto.reservation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * RequestDescriptionDTOクラスは、リクエスト説明DTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDescriptionDTO {

    private String mobilityHubId;
    private String freightId;
    private String truckId;
    private String sizeClass;
}
