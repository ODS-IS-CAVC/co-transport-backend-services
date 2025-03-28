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
@Table(name = "mobility_hub_info")
public class MobilityHubInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobility_hub_id")
    private String mobilityHubId;

    @Column(name = "freight_id")
    private String freightId;

    @Column(name = "truck_id")
    private String truckId;

    @Column(name = "size_class")
    private String sizeClass;

    @Column(name = "reservation_id")
    private String reservationId;

    @Column(name = "operator_id")
    private String companyId;
}
