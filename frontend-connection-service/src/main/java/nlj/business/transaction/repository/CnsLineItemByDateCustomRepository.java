package nlj.business.transaction.repository;

import java.util.List;
import java.util.Map;
import nlj.business.transaction.dto.CnsLineItemByDateByTrspAbility;
import nlj.business.transaction.dto.CnsLineItemByDateDTO;
import nlj.business.transaction.dto.CnsLineItemByDateResponseDTO;
import nlj.business.transaction.dto.request.CarrierListOrderSearch;
import nlj.business.transaction.dto.request.CarrierTransportPlanRequest;
import nlj.business.transaction.dto.request.TransportAbilityPublicResponseDTO;
import nlj.business.transaction.dto.request.TransportPlanPublicSearch;
import org.springframework.data.domain.Page;

/**
 * <PRE>
 * 運送計画の指示のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CnsLineItemByDateCustomRepository {

    Page<CnsLineItemByDateDTO> getPagedListCarrierOrderOnSale(CarrierListOrderSearch searchRequest);

    Page<CnsLineItemByDateResponseDTO> getPagedTransportPlan(TransportPlanPublicSearch request);

    Page<CnsLineItemByDateDTO> getPagedTransportPlanPublic(List<Map<String, String>> params, String pageNum,
        String size, CarrierTransportPlanRequest request);

    int updateStatusById(List<Long> ids, Integer updateStatus);

    List<CnsLineItemByDateByTrspAbility> findByTransportAbilityPublic(
        TransportAbilityPublicResponseDTO transportAbilityPublicResponseDTO);

}
