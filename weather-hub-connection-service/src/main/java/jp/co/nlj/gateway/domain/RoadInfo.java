package jp.co.nlj.gateway.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MobilityHubInfoクラスは、モビリティハブの情報を保持するためのエンティティクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "road_info")
public class RoadInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_data")
    private Integer targetData;

    @Column(name = "response_data", columnDefinition = "TEXT")
    private String responseData;

    @Column(name = "operator_id")
    private String companyId;
}
