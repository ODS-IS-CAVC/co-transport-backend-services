package nlj.business.shipper.service;

import java.util.List;
import nlj.business.shipper.domain.TransportPlanItem;
import nlj.business.shipper.dto.request.TransportPlanRequestDTO;
import nlj.business.shipper.dto.response.TransportPlanPagingResponse;
import nlj.business.shipper.dto.response.TransportPlanResponseDTO;
import org.springframework.data.domain.Pageable;

/**
 * <PRE>
 * 輸送計画サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransportPlanService {

    void createTransportPlan(TransportPlanRequestDTO transportPlanRequestDTO);

    TransportPlanPagingResponse getAllTransportPlans(String transportName, List<Integer> statuses,
        Long outerPackageCode, Pageable pageable);

    TransportPlanResponseDTO getTransportPlanById(Long id);

    void updateTransportPlan(Long id, TransportPlanRequestDTO transportPlanRequestDTO);

    void deleteTransportPlan(Long id);

    void checkCnsLineItemByDateInTransOrder(List<TransportPlanItem> transportPlanItems);
} 