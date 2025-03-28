package jp.co.nlj.gateway.dto.semiDynamicInfo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * RequestInfoDTOクラスは、リクエスト情報DTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RequestInfoDTO {

    private Integer targetData;

    private Integer requestFormat;

    private RequestAreaDTO requestArea;
}
