package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 車両スケジュール休憩エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = DataBaseConstant.IVehicleScheduleRest.TABLE)
public class IVehicleScheduleRest extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.IVehicleScheduleRest.VEHICLE_SCHEDULE_KEY, length = 20)
    private String vehicleScheduleKey;

    @Column(name = DataBaseConstant.IVehicleScheduleRest.SEQ_NO)
    private Integer seqNo;

    @Column(name = DataBaseConstant.IVehicleScheduleRest.REST_STOP_POINT, length = 90)
    private String restStopPoint;

    @Column(name = DataBaseConstant.IVehicleScheduleRest.REST_DATETIME_FROM)
    private LocalDateTime restDatetimeFrom;

    @Column(name = DataBaseConstant.IVehicleScheduleRest.REST_DATETIME_TO)
    private LocalDateTime restDatetimeTo;

    @Column(name = DataBaseConstant.IVehicleScheduleRest.DELETE_FLG, length = 1)
    private String deleteFlg;

    @Column(name = DataBaseConstant.IVehicleScheduleRest.PROCESS_ID, length = 15)
    private String processId;

}
