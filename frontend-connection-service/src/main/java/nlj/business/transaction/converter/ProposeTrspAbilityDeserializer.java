package nlj.business.transaction.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import nlj.business.transaction.dto.ProposeTrspAbilityDTO;

/**
 * <PRE>
 * ProposeTrspAbilityDTOのリストをデシリアライズするデシリアライザ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class ProposeTrspAbilityDeserializer extends JsonDeserializer<List<ProposeTrspAbilityDTO>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * ProposeTrspAbilityDTOのリストをデシリアライズする。
     *
     * @param p    JsonParser
     * @param ctxt DeserializationContext
     * @return List<ProposeTrspAbilityDTO>
     * @throws IOException 入出力エラー
     */
    @Override
    public List<ProposeTrspAbilityDTO> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String json = p.getValueAsString();
        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(json, new TypeReference<List<ProposeTrspAbilityDTO>>() {
        });
    }
}
