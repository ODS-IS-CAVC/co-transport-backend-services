package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運行サービス計画マスタエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MOperatingServicePlanId.class)
@Table(name = DataBaseConstant.MOperatingServicePlan.TABLE)
public class MOperatingServicePlan extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.MOperatingServicePlan.OPE_SERVICE_KEY, nullable = false, length = 20)
    private String opeServiceKey;

    @Id
    @Column(name = DataBaseConstant.MOperatingServicePlan.OPE_SERVICE_ORDER, nullable = false)
    private Integer opeServiceOrder;

    @Column(name = DataBaseConstant.MOperatingServicePlan.LOCATION_KEY, nullable = false, length = 20)
    private String locationKey;

    @Column(name = DataBaseConstant.MOperatingServicePlan.OPERATION_KBN, length = 2)
    private String operationKbn;

    @Column(name = DataBaseConstant.MOperatingServicePlan.OPERATION_TIME, length = 5)
    private String operationTime;

    @Column(name = DataBaseConstant.MOperatingServicePlan.SHIPPING_END_POINT, length = 2)
    private String shippingEndPoint;

    @Column(name = DataBaseConstant.MOperatingServicePlan.DELETE_FLG, nullable = false, length = 1)
    private String deleteFlg;

    @Column(name = DataBaseConstant.MOperatingServicePlan.PROCESS_ID, nullable = false, length = 15)
    private String processId;
}
