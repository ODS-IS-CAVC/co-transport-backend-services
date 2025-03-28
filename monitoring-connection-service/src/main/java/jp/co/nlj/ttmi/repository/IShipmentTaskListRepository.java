package jp.co.nlj.ttmi.repository;

import jp.co.nlj.ttmi.domain.IShipmentTaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 運送タスクリストリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface IShipmentTaskListRepository extends JpaRepository<IShipmentTaskList, String> {

    @Query(value = "SELECT SHIPMENT_TASK_KEY FROM (SELECT SHIPMENT_TASK_KEY FROM I_SHIPMENT_TASK_LIST WHERE SHIPMENT_TASK_KEY LIKE '%KHT%' ORDER BY CREATE_DT DESC, SHIPMENT_TASK_KEY DESC) WHERE ROWNUM = 1", nativeQuery = true)
    String lastShipmentTaskKey();

    IShipmentTaskList findByManagementNumberAndDetailNo(String managementNumber, Integer detailNo);
}