package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IncidentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 事故情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface IncidentRepository extends JpaRepository<IncidentInfo, Long> {

}
