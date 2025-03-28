package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.Data;

/**
 * <PRE>
 * 注文明細エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@Entity
@IdClass(IOrderDetailId.class)
@Table(name = DataBaseConstant.IOrderDetail.TABLE)
public class IOrderDetail extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.IOrderDetail.MANAGEMENT_NUMBER, length = 20, nullable = false)
    private String managementNumber;

    @Id
    @Column(name = DataBaseConstant.IOrderDetail.DETAIL_NO, precision = 6, nullable = false)
    private Integer detailNo;

    @Column(name = DataBaseConstant.IOrderDetail.SHIPMENT_SHIPPER_ID, length = 5, nullable = false)
    private String shipmentShipperId;

    @Column(name = DataBaseConstant.IOrderDetail.ITEM_CODE, length = 15)
    private String itemCode;

    @Column(name = DataBaseConstant.IOrderDetail.ITEM_NAME, length = 75)
    private String itemName;

    @Column(name = DataBaseConstant.IOrderDetail.LENGTH_SIZE, precision = 6)
    private Integer lengthSize;

    @Column(name = DataBaseConstant.IOrderDetail.WIDTH_SIZE, precision = 6)
    private Integer widthSize;

    @Column(name = DataBaseConstant.IOrderDetail.HEIGHT_SIZE, precision = 6)
    private Integer heightSize;

    @Column(name = DataBaseConstant.IOrderDetail.WEIGHT, precision = 9, scale = 3)
    private BigDecimal weight;

    @Column(name = DataBaseConstant.IOrderDetail.UNIT, precision = 11, scale = 3)
    private BigDecimal unit;

    @Column(name = DataBaseConstant.IOrderDetail.FLAT_STACKING_FLG, length = 1)
    private String flatStackingFlg;

    @Column(name = DataBaseConstant.IOrderDetail.PACKAGE_STACKING_FLG, length = 1)
    private String packageStackingFlg;

    @Column(name = DataBaseConstant.IOrderDetail.UPPER_STAGE_USE_FLG, length = 1)
    private String upperStageUseFlg;

    @Column(name = DataBaseConstant.IOrderDetail.MEMO, length = 300)
    private String memo;

    @Column(name = DataBaseConstant.IOrderDetail.LOGISTICS_COMPANY_CODE, length = 12, nullable = false)
    private String logisticsCompanyCode;

    @Column(name = DataBaseConstant.IOrderDetail.DELETE_FLG, length = 1, nullable = false)
    private String deleteFlg;

    @Column(name = DataBaseConstant.IOrderDetail.PROCESS_ID, length = 15, nullable = false)
    private String processId;

    @Column(name = DataBaseConstant.IOrderDetail.ORDER_NUMBER, length = 20)
    private String orderNumber;

    @Column(name = DataBaseConstant.IOrderDetail.SHIP_FROM, length = 75)
    private String shipFrom;

    @Column(name = DataBaseConstant.IOrderDetail.SHIP_TO, length = 75)
    private String shipTo;

}
