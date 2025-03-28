package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IVehicleSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両スケジュールリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface IVehicleScheduleRepository extends JpaRepository<IVehicleSchedule, String> {

    IVehicleSchedule findByVehicleScheduleKey(String vehicleScheduleKey);
}
