package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IVehicleScheduleRest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両スケジュールリストリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface IVehicleScheduleRestRepository extends JpaRepository<IVehicleScheduleRest, String> {

}
