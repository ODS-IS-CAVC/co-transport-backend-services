package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = DataBaseConstant.ILoadingInfo.TABLE)
public class ILoadingInfo extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.ILoadingInfo.TRUNK_LINE_ALLOCATION_KEY, nullable = false, length = 20)
    private String trunkLineAllocationKey;

    @Column(name = DataBaseConstant.ILoadingInfo.VEHICLE_SCHEDULE_KEY, nullable = false, length = 20)
    private String vehicleScheduleKey;

    @Column(name = DataBaseConstant.ILoadingInfo.MANAGEMENT_NUMBER, nullable = false, length = 20)
    private String managementNumber;

    @Column(name = DataBaseConstant.ILoadingInfo.DETAIL_NO, nullable = false)
    private Integer detailNo;

    @Column(name = DataBaseConstant.ILoadingInfo.UNIT, nullable = false, precision = 11, scale = 3)
    private BigDecimal unit;

    @Column(name = DataBaseConstant.ILoadingInfo.DELETE_FLG, nullable = false, length = 1)
    private String deleteFlg;

    @Column(name = DataBaseConstant.ILoadingInfo.PROCESS_ID, nullable = false, length = 15)
    private String processId;
}
