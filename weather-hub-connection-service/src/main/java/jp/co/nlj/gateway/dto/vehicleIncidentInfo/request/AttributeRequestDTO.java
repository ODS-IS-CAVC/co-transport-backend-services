package jp.co.nlj.gateway.dto.vehicleIncidentInfo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <PRE>
 * AttributeRequestDTOクラスは、属性リクエストDTOを定義するためのクラスです。<PRE>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class AttributeRequestDTO {

    private String deliveryName;

    private String dateTime;

    private String spatialID;

    private Double lat;

    private Double lon;

    private String vehicleName;

    private String vehicleID;

    private String operatorID;

    private String incidentID;

    private IncidentRequestDTO incidents;
}
