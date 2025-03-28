package jp.co.nlj.ttmi.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * <PRE>
 * 車両スケジュール計画IDクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

public class IVehicleSchedulePlanId implements Serializable {

    private String vehicleScheduleKey;
    private Integer opeServiceOrder;

    // Default constructor
    public IVehicleSchedulePlanId() {
    }

    // Parameterized constructor
    public IVehicleSchedulePlanId(String vehicleScheduleKey, Integer opeServiceOrder) {
        this.vehicleScheduleKey = vehicleScheduleKey;
        this.opeServiceOrder = opeServiceOrder;
    }

    // Getters and Setters
    public String getVehicleScheduleKey() {
        return vehicleScheduleKey;
    }

    public void setVehicleScheduleKey(String vehicleScheduleKey) {
        this.vehicleScheduleKey = vehicleScheduleKey;
    }

    public Integer getOpeServiceOrder() {
        return opeServiceOrder;
    }

    public void setOpeServiceOrder(Integer opeServiceOrder) {
        this.opeServiceOrder = opeServiceOrder;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IVehicleSchedulePlanId that = (IVehicleSchedulePlanId) o;
        return Objects.equals(vehicleScheduleKey, that.vehicleScheduleKey) &&
            Objects.equals(opeServiceOrder, that.opeServiceOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleScheduleKey, opeServiceOrder);
    }
}