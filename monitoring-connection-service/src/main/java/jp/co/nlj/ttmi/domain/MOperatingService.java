package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運行サービスマスタエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = DataBaseConstant.MOperatingService.TABLE)
public class MOperatingService extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.MOperatingService.OPE_SERVICE_KEY, nullable = false, length = 20)
    private String opeServiceKey;

    @Column(name = DataBaseConstant.MOperatingService.OPE_SERVICE_NAME, nullable = false, length = 75)
    private String opeServiceName;

    @Column(name = DataBaseConstant.MOperatingService.OPE_SERVICE_NAME_ABBREVIATION, nullable = false, length = 75)
    private String opeServiceNameAbbreviation;

    @Column(name = DataBaseConstant.MOperatingService.ROUTE_KEY, nullable = false, length = 20)
    private String routeKey;

    @Column(name = DataBaseConstant.MOperatingService.SAME_VEHICLE_KEYWORD, nullable = false, length = 10)
    private String sameVehicleKeyword;

    @Column(name = DataBaseConstant.MOperatingService.CLOCK_IN_TIME, nullable = false, length = 5)
    private String clockInTime;

    @Column(name = DataBaseConstant.MOperatingService.CLOCK_OUT_TIME, nullable = false, length = 5)
    private String clockOutTime;

    @Column(name = DataBaseConstant.MOperatingService.MEAL_ALLOWANCE_KBN, length = 2)
    private String mealAllowanceKbn;

    @Column(name = DataBaseConstant.MOperatingService.TRIP_KBN, length = 2)
    private String tripKbn;

    @Column(name = DataBaseConstant.MOperatingService.WORKING_KBN, length = 2)
    private String workingKbn;

    @Column(name = DataBaseConstant.MOperatingService.WORK_ON_TIME)
    private Integer workOnTime;

    @Column(name = DataBaseConstant.MOperatingService.POINT_CALL_TIME)
    private Integer pointCallTime;

    @Column(name = DataBaseConstant.MOperatingService.OWNED_COMPANY_CODE, length = 12)
    private String ownedCompanyCode;

    @Column(name = DataBaseConstant.MOperatingService.LOGISTICS_COMPANY_CODE, length = 12)
    private String logisticsCompanyCode;

    @Column(name = DataBaseConstant.MOperatingService.SHOW_DATE_FROM, nullable = false)
    private LocalDate showDateFrom;

    @Column(name = DataBaseConstant.MOperatingService.SHOW_DATE_TO, nullable = false)
    private LocalDate showDateTo;

    @Column(name = DataBaseConstant.MOperatingService.DELETE_FLG, nullable = false, length = 1)
    private String deleteFlg;

    @Column(name = DataBaseConstant.MOperatingService.PROCESS_ID, nullable = false, length = 15)
    private String processId;

}
