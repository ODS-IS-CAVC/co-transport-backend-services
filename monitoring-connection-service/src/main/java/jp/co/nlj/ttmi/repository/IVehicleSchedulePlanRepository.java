package jp.co.nlj.ttmi.repository;

import java.util.List;
import jp.co.nlj.ttmi.domain.IVehicleSchedulePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両スケジュール計画リポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Repository
public interface IVehicleSchedulePlanRepository extends JpaRepository<IVehicleSchedulePlan, String> {

    @Query(value = "SELECT VEHICLE_SCHEDULE_KEY FROM (SELECT VEHICLE_SCHEDULE_KEY FROM I_VEHICLE_SCHEDULE_PLAN WHERE VEHICLE_SCHEDULE_KEY LIKE '%LVS%' ORDER BY CREATE_DT DESC, VEHICLE_SCHEDULE_KEY DESC) WHERE ROWNUM = 1", nativeQuery = true)
    String getLastVehicleScheduleKey();

    List<IVehicleSchedulePlan> findAllByDeliveryServiceKey(String deliveryServiceKey);

}
