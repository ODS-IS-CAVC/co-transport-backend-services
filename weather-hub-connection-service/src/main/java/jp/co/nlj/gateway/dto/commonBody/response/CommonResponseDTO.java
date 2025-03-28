package jp.co.nlj.gateway.dto.commonBody.response;

import lombok.Data;

/**
 * <PRE>
 * CommonResponseDTOクラスは、共通レスポンスDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CommonResponseDTO {

    private String dataModelType;
    private Object attribute;
}
