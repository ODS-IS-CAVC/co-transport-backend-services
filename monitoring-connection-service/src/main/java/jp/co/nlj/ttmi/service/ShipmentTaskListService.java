package jp.co.nlj.ttmi.service;

import jp.co.nlj.ttmi.dto.Request.OrderTimeUpdateRequest;
import jp.co.nlj.ttmi.dto.zlWeb.ZLWebDTO;

/**
 * <PRE>
 * 運送タスクリストサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ShipmentTaskListService {

    void saveSchedule(ZLWebDTO vehicleScheduleDTO);

    void updateScheduleTime(OrderTimeUpdateRequest orderTimeUpdateRequest);

}
