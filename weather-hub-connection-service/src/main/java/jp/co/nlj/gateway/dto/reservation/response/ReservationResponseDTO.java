package jp.co.nlj.gateway.dto.reservation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.gateway.dto.reservation.Attribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * ReservationResponseDTOクラスは、予約レスポンスDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponseDTO {

    @JsonProperty("dataModelType")
    private String dataModelType;

    @JsonProperty("attribute")
    private Attribute attribute;
}
