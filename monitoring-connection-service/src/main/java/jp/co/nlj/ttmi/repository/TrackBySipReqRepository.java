package jp.co.nlj.ttmi.repository;


import jp.co.nlj.ttmi.domain.TrackBySipReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送SIPリクエストリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TrackBySipReqRepository extends JpaRepository<TrackBySipReq, Long> {

}
