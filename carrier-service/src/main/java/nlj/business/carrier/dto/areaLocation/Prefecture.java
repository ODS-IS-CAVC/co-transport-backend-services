package nlj.business.carrier.dto.areaLocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 都道府県.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class Prefecture {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;
}
