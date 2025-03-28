package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = DataBaseConstant.IOrderInfo.TABLE)
public class IOrderInfo {

    @Id
    @Column(name = DataBaseConstant.IOrderInfo.MANAGEMENT_NUMBER, length = 20, nullable = false)
    private String managementNumber;

    @Column(name = DataBaseConstant.IOrderInfo.ORDER_DATE, nullable = false)
    private LocalDate orderDate;

    @Column(name = DataBaseConstant.IOrderInfo.OWNER_COMPANY_CODE, length = 12, nullable = false)
    private String ownerCompanyCode;

    @Column(name = DataBaseConstant.IOrderInfo.OWNER_USER_ID, length = 20, nullable = false)
    private String ownerUserId;

    @Column(name = DataBaseConstant.IOrderInfo.OWNER_COMPANY_NAME_ABBREVIATION, length = 75, nullable = false)
    private String ownerCompanyNameAbbreviation;

    @Column(name = DataBaseConstant.IOrderInfo.OWNER_USER_NAME, length = 75, nullable = false)
    private String ownerUserName;

    @Column(name = DataBaseConstant.IOrderInfo.OWNER_PHONE, length = 20)
    private String ownerPhone;

    @Column(name = DataBaseConstant.IOrderInfo.ORDER_NUMBER, length = 20)
    private String orderNumber;

    @Column(name = DataBaseConstant.IOrderInfo.SHIPMENT_SHIPPER_ID, length = 5)
    private String shipmentShipperId;

    @Column(name = DataBaseConstant.IOrderInfo.SHIPMENT_SHIPPER_NAME, length = 75, nullable = false)
    private String shipmentShipperName;

    @Column(name = DataBaseConstant.IOrderInfo.SHIP_FROM, length = 75)
    private String shipFrom;

    @Column(name = DataBaseConstant.IOrderInfo.SHIP_TO, length = 75)
    private String shipTo;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_DATE)
    private LocalDate dropOffDate;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_TIME_FROM, length = 5)
    private String dropOffTimeFrom;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_TIME_TO, length = 5)
    private String dropOffTimeTo;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_LOCATION_KEY, length = 20)
    private String dropOffLocationKey;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_DATE)
    private LocalDate deliveryDate;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_TIME_FROM, length = 5)
    private String deliveryTimeFrom;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_TIME_TO, length = 5)
    private String deliveryTimeTo;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_LOCATION_KEY, length = 20)
    private String deliveryLocationKey;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_COMPANY_NAME, length = 75)
    private String dropOffCompanyName;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_VEHICLE_SPEC, length = 75)
    private String dropOffVehicleSpec;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_VEHICLE_NUMBER, length = 45)
    private String dropOffVehicleNumber;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_DRIVER_NAME, length = 75)
    private String dropOffDriverName;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_MOBILE_PHONE, length = 20)
    private String dropOffMobilePhone;

    @Column(name = DataBaseConstant.IOrderInfo.DROP_OFF_MAIL, length = 200)
    private String dropOffMail;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_COMPANY_NAME, length = 75)
    private String deliveryCompanyName;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_VEHICLE_SPEC, length = 75)
    private String deliveryVehicleSpec;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_VEHICLE_NUMBER, length = 45)
    private String deliveryVehicleNumber;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_DRIVER_NAME, length = 75)
    private String deliveryDriverName;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_MOBILE_PHONE, length = 20)
    private String deliveryMobilePhone;

    @Column(name = DataBaseConstant.IOrderInfo.DELIVERY_MAIL, length = 200)
    private String deliveryMail;

    @Column(name = DataBaseConstant.IOrderInfo.LOGISTICS_COMPANY_CODE, length = 12, nullable = false)
    private String logisticsCompanyCode;

    @Column(name = DataBaseConstant.IOrderInfo.STATUS_KBN, length = 2)
    private String statusKbn;

    @Column(name = DataBaseConstant.IOrderInfo.TRUNK_LINE_LOADING_KBN, length = 2)
    private String trunkLineLoadingKbn;

    @Column(name = DataBaseConstant.IOrderInfo.DELETE_FLG, length = 1, nullable = false)
    private String deleteFlg;

    @Column(name = DataBaseConstant.IOrderInfo.PROCESS_ID, length = 15, nullable = false)
    private String processId;

    @Column(name = DataBaseConstant.IOrderInfo.CREATE_USER, length = 20, nullable = false)
    private String createUser;

    @Column(name = DataBaseConstant.IOrderInfo.CREATE_DT, nullable = false)
    private LocalDateTime createDt;

    @Column(name = DataBaseConstant.IOrderInfo.UPD_USER, length = 20, nullable = false)
    private String updUser;

    @Column(name = DataBaseConstant.IOrderInfo.UPD_DT, nullable = false)
    private LocalDateTime updDt;
}
