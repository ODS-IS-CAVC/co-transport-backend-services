package jp.co.nlj.gateway.repository;

import jp.co.nlj.gateway.domain.MobilityHubInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * MobilityHubInfoRepositoryクラスは、モビリティハブ情報リポジトリを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface MobilityHubInfoRepository extends JpaRepository<MobilityHubInfo, String> {

}
