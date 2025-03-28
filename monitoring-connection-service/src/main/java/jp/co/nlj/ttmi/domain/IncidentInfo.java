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
 * 運送計画明細エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@Table(name = DataBaseConstant.Incident.TABLE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class IncidentInfo extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 215734124506464337L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.Incident.INSTRUCTION_ID)
    private String instructionId;

    @Column(name = DataBaseConstant.Incident.VEHICLE_ID)
    private String vehicleId;

    @Column(name = DataBaseConstant.Incident.INCIDENT_ID)
    private String incidentId;

    @Column(name = DataBaseConstant.Incident.INCIDENT_JSON, columnDefinition = "CLOB")
    private String incidentJson;

}
