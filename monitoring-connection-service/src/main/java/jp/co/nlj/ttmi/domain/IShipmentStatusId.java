package jp.co.nlj.ttmi.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * <PRE>
 * 運送計画明細IDクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

public class IShipmentStatusId implements Serializable {

    private String managementNumber;
    private String shipmentKbn;
    private String destinationCenterKey;
    private String deliveryServiceKey;

    // Default constructor
    public IShipmentStatusId() {
    }

    // Parameterized constructor
    public IShipmentStatusId(String managementNumber, String shipmentKbn, String destinationCenterKey,
        String deliveryServiceKey) {
        this.managementNumber = managementNumber;
        this.shipmentKbn = shipmentKbn;
        this.destinationCenterKey = destinationCenterKey;
        this.deliveryServiceKey = deliveryServiceKey;
    }

    // Getters and Setters
    // ...

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IShipmentStatusId that = (IShipmentStatusId) o;
        return Objects.equals(managementNumber, that.managementNumber) &&
            Objects.equals(shipmentKbn, that.shipmentKbn) &&
            Objects.equals(destinationCenterKey, that.destinationCenterKey) &&
            Objects.equals(deliveryServiceKey, that.deliveryServiceKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managementNumber, shipmentKbn, destinationCenterKey, deliveryServiceKey);
    }
}