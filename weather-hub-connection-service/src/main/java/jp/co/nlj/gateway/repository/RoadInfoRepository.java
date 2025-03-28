package jp.co.nlj.gateway.repository;

import jp.co.nlj.gateway.domain.RoadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * RoadInfoRepositoryクラスは、道路情報リポジトリを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface RoadInfoRepository extends JpaRepository<RoadInfo, Long> {

}
