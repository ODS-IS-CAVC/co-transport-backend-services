package jp.co.nlj.ttmi.repository;

import java.util.List;
import jp.co.nlj.ttmi.domain.MOperatingServicePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運行サービス計画リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Repository
public interface MOperatingServicePlanRepository extends JpaRepository<MOperatingServicePlan, Integer> {

    List<MOperatingServicePlan> findAllByOpeServiceKey(String opeServiceKey);
}
