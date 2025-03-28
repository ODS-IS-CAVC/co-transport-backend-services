package jp.co.nlj.gateway.dto.trackBySip.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * DataChannelRequestDTOクラスは、データチャンネルリクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataChannelRequestDTO {

    private String dataModelType;

    private AttributeRequestDTO attribute;
}
