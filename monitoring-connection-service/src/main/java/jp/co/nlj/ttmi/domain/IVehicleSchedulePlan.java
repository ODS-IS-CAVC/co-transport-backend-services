package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 車両スケジュール計画エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(IVehicleSchedulePlanId.class)
@Table(name = DataBaseConstant.IVehicleSchedulePlan.TABLE)
public class IVehicleSchedulePlan extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.IVehicleSchedulePlan.VEHICLE_SCHEDULE_KEY, nullable = false, length = 20)
    private String vehicleScheduleKey;

    @Id
    @Column(name = DataBaseConstant.IVehicleSchedulePlan.OPE_SERVICE_ORDER, nullable = false)
    private Integer opeServiceOrder;

    @Column(name = DataBaseConstant.IVehicleSchedulePlan.OPE_SERVICE_KEY, nullable = false, length = 20)
    private String opeServiceKey;

    @Column(name = DataBaseConstant.IVehicleSchedulePlan.OPERATION_DATETIME)
    private LocalDateTime operationDatetime;

    @Column(name = DataBaseConstant.IVehicleSchedulePlan.DELIVERY_SERVICE_KEY, length = 20)
    private String deliveryServiceKey;

    @Column(name = DataBaseConstant.IVehicleSchedulePlan.DELETE_FLG, nullable = false, length = 1)
    private String deleteFlg = "0";

    @Column(name = DataBaseConstant.IVehicleSchedulePlan.PROCESS_ID, nullable = false, length = 15)
    private String processId;

}
