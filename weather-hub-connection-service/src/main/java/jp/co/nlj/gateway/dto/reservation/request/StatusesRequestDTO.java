package jp.co.nlj.gateway.dto.reservation.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * StatusesRequestDTOクラスは、ステータスリクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusesRequestDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RequestDescriptionDTO requestObject;
    private String value;
    private String validFrom;
    private String validUntil;
}
