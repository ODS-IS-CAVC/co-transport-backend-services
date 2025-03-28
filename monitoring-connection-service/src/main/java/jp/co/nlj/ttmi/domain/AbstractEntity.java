package jp.co.nlj.ttmi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <PRE>
 * エンティティの基底クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"}, allowGetters = true)
public class AbstractEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CREATE_USER", length = 20)
    private String createUser = "nljadmin01";

    @Column(name = "CREATE_DT")
    private LocalDateTime createDt = LocalDateTime.now();

    @Column(name = "UPD_USER", length = 20)
    private String updUser = "nljadmin01";

    @Column(name = "UPD_DT")
    private LocalDateTime updDt = LocalDateTime.now();

}
