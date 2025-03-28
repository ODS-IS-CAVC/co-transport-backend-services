package nlj.business.transaction.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送計画明細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrspPlanLineItemDTO {

    @JsonProperty("trsp_srvc")
    private TrspSrvcDTO trspSrvc = new TrspSrvcDTO();


}
