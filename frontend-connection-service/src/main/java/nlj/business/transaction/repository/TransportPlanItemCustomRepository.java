package nlj.business.transaction.repository;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import nlj.business.transaction.domain.shipper.TransportPlanItem;
import nlj.business.transaction.dto.request.TransportPlanPublicSearch;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画項目カスタムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportPlanItemCustomRepository {

    Page<TransportPlanItem> search(TransportPlanPublicSearch searchRequest);

    TransportPlanItem getTransportPlanItemProposal();

    List<TransportPlanItem> searchMatching(Long departureFrom, Long arrivalTo, BigDecimal maxPayload,
        BigDecimal totalLength, BigDecimal totalWidth, BigDecimal totalHeight, LocalTime departureTime,
        BigDecimal price);
}
