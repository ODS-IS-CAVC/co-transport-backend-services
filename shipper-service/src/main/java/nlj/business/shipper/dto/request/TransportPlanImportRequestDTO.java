package nlj.business.shipper.dto.request;

import java.util.List;
import lombok.Data;
import nlj.business.shipper.dto.TransportPlanImportDTO;

/**
 * <PRE>
 * 輸送計画インポートリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanImportRequestDTO {

    private TransportPlanImportDTO transportPlanImportDTO;
    private List<TransportPlanRequestDTO> transportPlanRequestDTOList;
}
