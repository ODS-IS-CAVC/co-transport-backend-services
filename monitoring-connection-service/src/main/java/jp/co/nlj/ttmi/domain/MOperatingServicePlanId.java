package jp.co.nlj.ttmi.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * <PRE>
 * 運用サービス計画IDクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

public class MOperatingServicePlanId implements Serializable {

    private String opeServiceKey;
    private Integer opeServiceOrder;

    // Default constructor
    public MOperatingServicePlanId() {
    }

    // Parameterized constructor
    public MOperatingServicePlanId(String opeServiceKey, Integer opeServiceOrder) {
        this.opeServiceKey = opeServiceKey;
        this.opeServiceOrder = opeServiceOrder;
    }

    // Getters and Setters
    public String getOpeServiceKey() {
        return opeServiceKey;
    }

    public void setOpeServiceKey(String opeServiceKey) {
        this.opeServiceKey = opeServiceKey;
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
        MOperatingServicePlanId that = (MOperatingServicePlanId) o;
        return Objects.equals(opeServiceKey, that.opeServiceKey) &&
            Objects.equals(opeServiceOrder, that.opeServiceOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opeServiceKey, opeServiceOrder);
    }
}