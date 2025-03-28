package nlj.business.transaction.repository;

import nlj.business.transaction.dto.ShipperTransactionDTO;
import nlj.business.transaction.dto.TransactionCarrierDTO;
import nlj.business.transaction.dto.request.TransactionShipperSearch;
import nlj.business.transaction.dto.response.CarrierProposalItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * トランザクション注文のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransOrderCustomRepository {

    Page<ShipperTransactionDTO> getPagedShipperTransaction(TransactionShipperSearch searchRequest);

    CarrierProposalItemResponseDTO getCarrierProposalItem(Long transOrderId);

    TransactionCarrierDTO getDetailTransaction(Long id);

    Integer findSiblingTrailerOrderStatus(Long vehicleDiagramItemTrailerId);
}
