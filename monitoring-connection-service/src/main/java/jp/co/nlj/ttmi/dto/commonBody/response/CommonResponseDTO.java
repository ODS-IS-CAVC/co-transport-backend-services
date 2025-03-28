package jp.co.nlj.ttmi.dto.commonBody.response;

import lombok.Data;

/**
 * <PRE>
 * 共通レスポンスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CommonResponseDTO {

    private String dataModelType;
    private Object attribute;
}
