package nlj.business.shipper.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import nlj.business.shipper.dto.TransportPlanCargoInfoDTO;
import nlj.business.shipper.dto.TransportPlanDTO;
import nlj.business.shipper.dto.TransportPlanItemDTO;

/**
 * <PRE>
 * 輸送計画リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanRequestDTO {

    @JsonProperty("transport_plan")
    private TransportPlanDTO transportPlanDTO;

    @JsonProperty("transport_plan_cargo_info")
    private List<TransportPlanCargoInfoDTO> transportPlanCargoInfoDTOList;

    @JsonProperty("transport_plan_item")
    private List<TransportPlanItemDTO> transportPlanItemDTOList;
}
