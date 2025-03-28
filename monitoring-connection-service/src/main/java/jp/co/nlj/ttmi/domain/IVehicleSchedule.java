package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 車両スケジュールエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = DataBaseConstant.IVehicleSchedule.TABLE)
public class IVehicleSchedule extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.IVehicleSchedule.VEHICLE_SCHEDULE_KEY, length = 20)
    private String vehicleScheduleKey;

    @Column(name = DataBaseConstant.IVehicleSchedule.OPE_SERVICE_KEY, length = 20)
    private String opeServiceKey;

    @Column(name = DataBaseConstant.IVehicleSchedule.CLOCK_IN_DATETIME)
    private LocalDateTime clockInDatetime;

    @Column(name = DataBaseConstant.IVehicleSchedule.CLOCK_OUT_DATETIME)
    private LocalDateTime clockOutDatetime;

    @Column(name = DataBaseConstant.IVehicleSchedule.DEPARTURE_DATE)
    private LocalDate departureDate;

    @Column(name = DataBaseConstant.IVehicleSchedule.DEPARTURE_DATETIME)
    private LocalDateTime departureDatetime;

    @Column(name = DataBaseConstant.IVehicleSchedule.ARRIVAL_DATETIME)
    private LocalDateTime arrivalDatetime;

    @Column(name = DataBaseConstant.IVehicleSchedule.MEAL_ALLOWANCE_KBN, length = 2)
    private String mealAllowanceKbn;

    @Column(name = DataBaseConstant.IVehicleSchedule.TRIP_KBN, length = 2)
    private String tripKbn;

    @Column(name = DataBaseConstant.IVehicleSchedule.WORKING_KBN, length = 2)
    private String workingKbn;

    @Column(name = DataBaseConstant.IVehicleSchedule.WORK_ON_TIME)
    private Integer workOnTime;

    @Column(name = DataBaseConstant.IVehicleSchedule.POINT_CALL_TIME)
    private Integer pointCallTime;

    @Column(name = DataBaseConstant.IVehicleSchedule.COMPANY_CODE, length = 12)
    private String companyCode;

    @Column(name = DataBaseConstant.IVehicleSchedule.LOGISTICS_COMPANY_CODE, length = 12)
    private String logisticsCompanyCode;

    @Column(name = DataBaseConstant.IVehicleSchedule.DELETE_FLG, length = 1)
    private String deleteFlg;

    @Column(name = DataBaseConstant.IVehicleSchedule.PROCESS_ID, length = 15)
    private String processId;

}
