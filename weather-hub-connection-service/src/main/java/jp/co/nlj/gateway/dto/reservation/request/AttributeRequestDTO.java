package jp.co.nlj.gateway.dto.reservation.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <PRE>
 * AttributeRequestDTOクラスは、属性リクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class AttributeRequestDTO {

    private String category;
    private List<StatusesRequestDTO> statuses;
}
