package nlj.business.carrier.link.dto.incident.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <PRE>
 * 属性リクエストDMPDTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class AttributeRequestDmpDTO {

    private String deliveryName;

    private String dateTime;

    private String spatialID;

    private Double lat;

    private Double lon;

    private String vehicleName;

    private String vehicleID;

    private String operatorID;

    private String incidentID;

    private IncidentRequestDmpDTO incidents;
}
