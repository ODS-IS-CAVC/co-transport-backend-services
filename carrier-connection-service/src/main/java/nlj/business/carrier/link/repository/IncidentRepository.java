package nlj.business.carrier.link.repository;

import nlj.business.carrier.link.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 事故情報リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

}
