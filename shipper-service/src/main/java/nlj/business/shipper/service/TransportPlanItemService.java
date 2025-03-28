package nlj.business.shipper.service;

import nlj.business.shipper.dto.TransportPlanItemDTO;
import nlj.business.shipper.dto.request.PlanItemUpdateStatusRequestDTO;
import nlj.business.shipper.dto.response.PlanItemUpdateStatusResponse;
import nlj.business.shipper.dto.transportPlanItem.response.TransportPlanItemPagingResponse;
import org.springframework.data.domain.Pageable;

/**
 * <PRE>
 * 輸送計画アイテムサービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransportPlanItemService {

    TransportPlanItemDTO getTransportPlanItemById(Long id);

    void updateTransportPlanItem(Long id, TransportPlanItemDTO transportPlanItemDTO);

    TransportPlanItemPagingResponse searchTrspPlanItem(String transportName, Pageable pageable);

    PlanItemUpdateStatusResponse updateStatus(PlanItemUpdateStatusRequestDTO dto);
}