package nlj.business.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import nlj.business.transaction.constant.DataBaseConstant;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <PRE>
 * 抽象監査エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {DataBaseConstant.AbstractAuditingEntity.CREATED_USER,
    DataBaseConstant.AbstractAuditingEntity.CREATED_DATE, DataBaseConstant.AbstractAuditingEntity.UPDATED_USER,
    DataBaseConstant.AbstractAuditingEntity.UPDATED_DATE}, allowGetters = true)
public abstract class AbstractAuditingEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract T getId();

    @JsonProperty(DataBaseConstant.AbstractAuditingEntity.CREATED_USER)
    @CreatedBy
    @Column(name = DataBaseConstant.AbstractAuditingEntity.CREATED_USER, nullable = false, length = 50, updatable = false)
    private String createdBy = "Admin";

    @JsonProperty(DataBaseConstant.AbstractAuditingEntity.CREATED_DATE)
    @CreatedDate
    @Column(name = DataBaseConstant.AbstractAuditingEntity.CREATED_DATE, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @JsonProperty(DataBaseConstant.AbstractAuditingEntity.UPDATED_USER)
    @LastModifiedBy
    @Column(name = DataBaseConstant.AbstractAuditingEntity.UPDATED_USER, length = 50)
    private String updatedBy = "Admin";

    @JsonProperty(DataBaseConstant.AbstractAuditingEntity.UPDATED_DATE)
    @LastModifiedDate
    @Column(name = DataBaseConstant.AbstractAuditingEntity.UPDATED_DATE)
    private LocalDateTime updatedDate = LocalDateTime.now();

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String lastModifiedBy) {
        this.updatedBy = lastModifiedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime lastModifiedDate) {
        this.updatedDate = lastModifiedDate;
    }
}
