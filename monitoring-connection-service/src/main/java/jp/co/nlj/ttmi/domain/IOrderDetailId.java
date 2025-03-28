package jp.co.nlj.ttmi.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * <PRE>
 * 注文明細IDクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class IOrderDetailId implements Serializable {

    private String managementNumber;
    private Integer detailNo;

    // Default constructor
    public IOrderDetailId() {
    }

    public IOrderDetailId(String managementNumber, Integer detailNo) {
        this.managementNumber = managementNumber;
        this.detailNo = detailNo;
    }

    // Getters and Setters
    public String getManagementNumber() {
        return managementNumber;
    }

    public void setManagementNumber(String managementNumber) {
        this.managementNumber = managementNumber;
    }

    public Integer getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(Integer detailNo) {
        this.detailNo = detailNo;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IOrderDetailId that)) {
            return false;
        }
        return Objects.equals(managementNumber, that.managementNumber) &&
            Objects.equals(detailNo, that.detailNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managementNumber, detailNo);
    }
}