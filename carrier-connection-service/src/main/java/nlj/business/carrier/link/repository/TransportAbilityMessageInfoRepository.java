package nlj.business.carrier.link.repository;

import nlj.business.carrier.link.domain.TransportAbilityMessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * トランスポート能力メッセージ情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportAbilityMessageInfoRepository extends JpaRepository<TransportAbilityMessageInfo, Long> {

}