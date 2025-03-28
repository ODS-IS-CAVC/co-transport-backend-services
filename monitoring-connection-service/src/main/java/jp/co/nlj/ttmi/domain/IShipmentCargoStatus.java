package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 出荷貨物ステータスクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = DataBaseConstant.IShipmentCargoStatus.TABLE)
public class IShipmentCargoStatus extends AbstractEntity {

    @Id
    @Column(name = DataBaseConstant.IShipmentCargoStatus.DESTINATION_CENTER_KEY, nullable = false, length = 20)
    private String destinationCenterKey;

    @Column(name = DataBaseConstant.IShipmentCargoStatus.SHIPMENT_KBN, nullable = false, length = 1)
    private String shipmentKbn;

    @Column(name = DataBaseConstant.IShipmentCargoStatus.DELIVERY_SERVICE_KEY, nullable = false, length = 20)
    private String deliveryServiceKey;

    @Column(name = DataBaseConstant.IShipmentCargoStatus.MANAGEMENT_NUMBER, nullable = false, length = 20)
    private String managementNumber;

    @Column(name = DataBaseConstant.IShipmentCargoStatus.DETAIL_NO, nullable = false)
    private Integer detailNo;

    @Column(name = DataBaseConstant.IShipmentCargoStatus.CARGO_STATUS_KBN, nullable = false, length = 2)
    private String cargoStatusKbn;

    @Column(name = DataBaseConstant.IShipmentCargoStatus.DELETE_FLG, nullable = false, length = 1)
    private String deleteFlg;

    @Column(name = DataBaseConstant.IShipmentCargoStatus.PROCESS_ID, nullable = false, length = 15)
    private String processId;
}
