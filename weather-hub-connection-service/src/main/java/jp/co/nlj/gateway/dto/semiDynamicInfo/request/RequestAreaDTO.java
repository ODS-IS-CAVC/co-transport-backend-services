package jp.co.nlj.gateway.dto.semiDynamicInfo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * RequestAreaDTOクラスは、リクエストエリアDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RequestAreaDTO {

    private String spatialID;
    private Double[][] bbox;
    private String areaID;
}
