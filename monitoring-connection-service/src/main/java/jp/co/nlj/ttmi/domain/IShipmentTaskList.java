package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * <PRE>
 * 運送計画明細エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Entity
@Table(name = DataBaseConstant.IShipmentTaskList.TABLE)
public class IShipmentTaskList extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.IShipmentTaskList.SHIPMENT_TASK_KEY, nullable = false, length = 20)
    private String shipmentTaskKey;

    @Column(name = DataBaseConstant.IShipmentTaskList.SHIPMENT_KBN, nullable = false, length = 1)
    private String shipmentKbn;

    @Column(name = DataBaseConstant.IShipmentTaskList.LOGISTICS_COMPANY_CODE, nullable = false, length = 12)
    private String logisticsCompanyCode;

    @Column(name = DataBaseConstant.IShipmentTaskList.MANAGEMENT_NUMBER, nullable = false, length = 20)
    private String managementNumber;

    @Column(name = DataBaseConstant.IShipmentTaskList.OWNER_COMPANY_CODE, length = 12)
    private String ownerCompanyCode;

    @Column(name = DataBaseConstant.IShipmentTaskList.OWNER_COMPANY_NAME_ABBREVIATION, length = 75)
    private String ownerCompanyNameAbbreviation;

    @Column(name = DataBaseConstant.IShipmentTaskList.OWNER_USER_NAME, length = 75)
    private String ownerUserName;

    @Column(name = DataBaseConstant.IShipmentTaskList.OWNER_PHONE, length = 20)
    private String ownerPhone;

    @Column(name = DataBaseConstant.IShipmentTaskList.SHIPMENT_SHIPPER_ID, length = 5)
    private String shipmentShipperId;

    @Column(name = DataBaseConstant.IShipmentTaskList.SHIPMENT_SHIPPER_NAME, length = 75)
    private String shipmentShipperName;

    @Column(name = DataBaseConstant.IShipmentTaskList.SHIP_FROM, length = 75)
    private String shipFrom;

    @Column(name = DataBaseConstant.IShipmentTaskList.SHIP_TO, length = 75)
    private String shipTo;

    @Column(name = DataBaseConstant.IShipmentTaskList.DESTINATION_DATE)
    private LocalDate destinationDate;

    @Column(name = DataBaseConstant.IShipmentTaskList.DESTINATION_TIME_FROM, length = 5)
    private String destinationTimeFrom;

    @Column(name = DataBaseConstant.IShipmentTaskList.DESTINATION_TIME_TO, length = 5)
    private String destinationTimeTo;

    @Column(name = DataBaseConstant.IShipmentTaskList.DESTINATION_CENTER_NAME_ABBREVIATION, length = 75)
    private String destinationCenterNameAbbreviation;

    @Column(name = DataBaseConstant.IShipmentTaskList.SHIPPING_COMPANY_NAME, length = 75)
    private String shippingCompanyName;

    @Column(name = DataBaseConstant.IShipmentTaskList.VEHICLE_SPEC, length = 75)
    private String vehicleSpec;

    @Column(name = DataBaseConstant.IShipmentTaskList.VEHICLE_NUMBER, length = 45)
    private String vehicleNumber;

    @Column(name = DataBaseConstant.IShipmentTaskList.DRIVER_NAME, length = 75)
    private String driverName;

    @Column(name = DataBaseConstant.IShipmentTaskList.MOBILE_PHONE, length = 20)
    private String mobilePhone;

    @Column(name = DataBaseConstant.IShipmentTaskList.MAIL, length = 200)
    private String mail;

    @Column(name = DataBaseConstant.IShipmentTaskList.DETAIL_NO, nullable = false)
    private Integer detailNo;

    @Column(name = DataBaseConstant.IShipmentTaskList.ITEM_CODE, length = 15)
    private String itemCode;

    @Column(name = DataBaseConstant.IShipmentTaskList.ITEM_NAME, length = 75)
    private String itemName;

    @Column(name = DataBaseConstant.IShipmentTaskList.LENGTH_SIZE)
    private Integer lengthSize;

    @Column(name = DataBaseConstant.IShipmentTaskList.WIDTH_SIZE)
    private Integer widthSize;

    @Column(name = DataBaseConstant.IShipmentTaskList.HEIGHT_SIZE)
    private Integer heightSize;

    @Column(name = DataBaseConstant.IShipmentTaskList.WEIGHT, precision = 38, scale = 2)
    private BigDecimal weight;

    @Column(name = DataBaseConstant.IShipmentTaskList.UNIT)
    private BigDecimal unit;

    @Column(name = DataBaseConstant.IShipmentTaskList.FLAT_STACKING_FLG, length = 1)
    private String flatStackingFlg;

    @Column(name = DataBaseConstant.IShipmentTaskList.PACKAGE_STACKING_FLG, length = 1)
    private String packageStackingFlg;

    @Column(name = DataBaseConstant.IShipmentTaskList.UPPER_STAGE_USE_FLG, length = 1)
    private String upperStageUseFlg;

    @Column(name = DataBaseConstant.IShipmentTaskList.MEMO, length = 300)
    private String memo;

    @Column(name = DataBaseConstant.IShipmentTaskList.TOTAL_UNIT, precision = 11, scale = 3)
    private BigDecimal totalUnit;

    @Column(name = DataBaseConstant.IShipmentTaskList.TRUNK_LINE_ALLOCATION_KEY, length = 20)
    private String trunkLineAllocationKey;

    @Column(name = DataBaseConstant.IShipmentTaskList.DELETE_FLG, nullable = false, length = 1)
    private String deleteFlg;

    @Column(name = DataBaseConstant.IShipmentTaskList.PROCESS_ID, nullable = false, length = 15)
    private String processId;

    @Column(name = DataBaseConstant.IShipmentTaskList.LOADING_ATTRIBUTE1)
    private Integer loadingAttribute1;

    @Column(name = DataBaseConstant.IShipmentTaskList.LOADING_ATTRIBUTE2)
    private Integer loadingAttribute2;

    @Column(name = DataBaseConstant.IShipmentTaskList.LOADING_ATTRIBUTE3)
    private Integer loadingAttribute3;

    @Column(name = DataBaseConstant.IShipmentTaskList.LOADING_ATTRIBUTE4, length = 2048)
    private String loadingAttribute4;

    @Column(name = DataBaseConstant.IShipmentTaskList.LOADING_ATTRIBUTE5, length = 2048)
    private String loadingAttribute5;

    @Column(name = DataBaseConstant.IShipmentTaskList.DESTINATION_DATETIME)
    private LocalDateTime destinationDatetime;

    @Column(name = DataBaseConstant.IShipmentTaskList.TRUNK_CENTER_NAME_ABBREVIATION, length = 75)
    private String trunkCenterNameAbbreviation;

    @Column(name = DataBaseConstant.IShipmentTaskList.TRUNK_SCHEDULE_DATE)
    private LocalDate trunkScheduleDate;

    @Column(name = DataBaseConstant.IShipmentTaskList.TRUNK_SCHEDULE_TIME, length = 5)
    private String trunkScheduleTime;

    @Column(name = DataBaseConstant.IShipmentTaskList.TRUNK_SCHEDULE_DATETIME)
    private LocalDateTime trunkScheduleDatetime;

    @Column(name = DataBaseConstant.IShipmentTaskList.TRUNK_SHIPPING_NAME, length = 75)
    private String trunkShippingName;

    @Column(name = DataBaseConstant.IShipmentTaskList.ORDER_DATE)
    private LocalDate orderDate;

    @Column(name = DataBaseConstant.IShipmentTaskList.ORDER_NUMBER, length = 20)
    private String orderNumber;

    @Column(name = DataBaseConstant.IShipmentTaskList.DELIVERY_DATE)
    private LocalDate deliveryDate;

    @Column(name = DataBaseConstant.IShipmentTaskList.DELIVERY_TIME_FROM, length = 5)
    private String deliveryTimeFrom;

    @Column(name = DataBaseConstant.IShipmentTaskList.DELIVERY_TIME_TO, length = 5)
    private String deliveryTimeTo;

    @Column(name = DataBaseConstant.IShipmentTaskList.TRACTOR_UNIT, precision = 11, scale = 3)
    private BigDecimal tractorUnit;

    @Column(name = DataBaseConstant.IShipmentTaskList.TRAILER_UNIT, precision = 11, scale = 3)
    private BigDecimal trailerUnit;
}
