package jp.co.nlj.gateway.dto.trackBySip.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * RestemplateApiResponseクラスは、RESTテンプレートAPIレスポンスDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestTemplateApiResponse {

    private int code;
    private String message;
    private String id;
}
