package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import nlj.business.transaction.dto.TransportPlanCarInfoDTO;

public class CarInfoDeserializer extends JsonDeserializer<List<TransportPlanCarInfoDTO>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<TransportPlanCarInfoDTO> deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException {
        String json = p.getValueAsString();
        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(json, new TypeReference<List<TransportPlanCarInfoDTO>>() {
        });
    }
}
