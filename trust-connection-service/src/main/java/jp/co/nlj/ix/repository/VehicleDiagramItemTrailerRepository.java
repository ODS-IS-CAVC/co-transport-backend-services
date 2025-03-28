package jp.co.nlj.ix.repository;

import jp.co.nlj.ix.domain.carrier.VehicleDiagramItemTrailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画アイテムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemTrailerRepository extends JpaRepository<VehicleDiagramItemTrailer, Long> {

}
