package jp.co.nlj.ix.repository;

import jp.co.nlj.ix.domain.shipper.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画リポジトリ<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

}
