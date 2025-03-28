package nlj.business.carrier.link.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import nlj.business.carrier.link.domain.Incident;
import nlj.business.carrier.link.dto.incident.request.IncidentRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 事故マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring")
public interface IncidentMapper {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mapping(source = "instructionId", target = "instructionId")
    @Mapping(source = "vehicleId", target = "vehicleId")
    @Mapping(target = "incidentJson", expression = "java(mapRequestObjectToString(dto.getIncidentJson()))")
    Incident toEntity(IncidentRequestDTO dto);

    @Mapping(source = "instructionId", target = "instructionId")
    @Mapping(source = "vehicleId", target = "vehicleId")
    @Mapping(target = "incidentJson", expression = "java(mapRequestStringToObject(entity.getIncidentJson()))")
    IncidentRequestDTO toDTO(Incident entity);

    default String mapRequestObjectToString(Map<String, Object> request) {
        if (request == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing request object to JSON string", e);
        }
    }

    default Map<String, Object> mapRequestStringToObject(String requestString) {
        if (requestString == null || requestString.isEmpty()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(requestString, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing request JSON string to Object", e);
        }
    }
}
