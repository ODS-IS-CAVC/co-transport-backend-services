package nlj.business.carrier.dto.areaLocation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.dto.areaLocation.Prefecture;

/**
 * <PRE>
 * セミダイナミック情報 DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaLocationResponseDTO {

    @JsonProperty("prefectures")
    List<Prefecture> prefectures;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}
