package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * Sipリクエストトラックエンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TracBySipReq.TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackBySipReq extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.TracBySipReq.ID)
    private Long id;

    @Column(name = DataBaseConstant.TracBySipReq.REQUEST, columnDefinition = "CLOB")
    private String request;

}
