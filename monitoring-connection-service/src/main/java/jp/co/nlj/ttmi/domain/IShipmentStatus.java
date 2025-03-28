package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.Data;

/**
 * <PRE>
 * 運送計画明細エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@Entity
@IdClass(IShipmentStatusId.class)
@Table(name = DataBaseConstant.IShipmentStatus.TABLE)
public class IShipmentStatus extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.IShipmentStatus.MANAGEMENT_NUMBER)
    private String managementNumber;

    @Id
    @Column(name = DataBaseConstant.IShipmentStatus.SHIPMENT_KBN)
    private String shipmentKbn;

    @Id
    @Column(name = DataBaseConstant.IShipmentStatus.DESTINATION_CENTER_KEY)
    private String destinationCenterKey;

    @Id
    @Column(name = DataBaseConstant.IShipmentStatus.DELIVERY_SERVICE_KEY)
    private String deliveryServiceKey;

    @Column(name = DataBaseConstant.IShipmentStatus.SHIPMENT_STATUS_KBN)
    private String shipmentStatusKbn;

    @Column(name = DataBaseConstant.IShipmentStatus.DELETE_FLG)
    private String deleteFlg;

    @Column(name = DataBaseConstant.IShipmentStatus.PROCESS_ID)
    private String processId;

}
