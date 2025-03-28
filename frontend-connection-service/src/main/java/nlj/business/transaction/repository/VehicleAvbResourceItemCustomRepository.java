package nlj.business.transaction.repository;

import java.util.List;
import java.util.Map;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.dto.ProposeAbilityDTO;
import nlj.business.transaction.dto.request.TransportAbilityProposalRequest;
import nlj.business.transaction.dto.request.TransportAbilityPublicResponseDTO;
import nlj.business.transaction.dto.request.TransportAbilityPublicSearch;
import org.springframework.data.domain.Page;

/**
 * <PRE>
 * 車両利用可能リソース品目のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleAvbResourceItemCustomRepository {

    Page<TransportAbilityPublicResponseDTO> getPagedTransportAbility(TransportAbilityPublicSearch searchRequest);

    Page<TransportAbilityPublicResponseDTO> getTransportAbility(TransportAbilityPublicSearch searchRequest,
        List<Map<String, String>> listParam);

    int updateStatusById(List<Long> ids, Integer updateStatus);

    List<VehicleAvbResourceItem> matchingCarrierTrailer(CnsLineItemByDate cnsLineItemByDate);

    Page<ProposeAbilityDTO> getProposalItem(TransportAbilityProposalRequest request);
}
