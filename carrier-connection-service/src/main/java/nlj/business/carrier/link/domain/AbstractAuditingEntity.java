package nlj.business.carrier.link.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <PRE>
 * エンティティの基底クラス。<BR> 作成日時、更新日時、作成者、更新者を保持する。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdUser", "createdDate", "updatedUser", "updatedDate"}, allowGetters = true)
public abstract class AbstractAuditingEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    @CreatedBy
    @Column(name = "created_user", nullable = false, length = 50, updatable = false)
    private String createdUser = "Admin";
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    @LastModifiedBy
    @Column(name = "updated_user", length = 50)
    private String updatedUser = "Admin";
    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();

    public abstract T getId();

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
