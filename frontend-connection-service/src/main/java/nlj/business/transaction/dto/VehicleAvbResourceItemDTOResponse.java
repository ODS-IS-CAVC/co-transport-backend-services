package nlj.business.transaction.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 車両可用性リソース品目応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAvbResourceItemDTOResponse {

    private List<VehicleAvbResourceItemDTO> data;
}
