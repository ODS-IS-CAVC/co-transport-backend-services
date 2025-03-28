package nlj.business.transaction.repository;

import java.util.List;
import java.util.Optional;
import nlj.business.transaction.domain.trans.MarketVehicleDiagramItemTrailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 市場輸送計画品目リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface MarketVehicleDiagramItemTrailerRepository extends
    JpaRepository<MarketVehicleDiagramItemTrailer, Long> {

    List<MarketVehicleDiagramItemTrailer> findByStatus(Integer status);

    Optional<MarketVehicleDiagramItemTrailer> findFirstByVehicleDiagramItemTrailerId(Long id);
}
