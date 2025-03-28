package nlj.business.transaction.mapper;

import nlj.business.transaction.dto.request.TransportAbilityPublicResponseDTO;
import nlj.business.transaction.dto.response.TransportAbilityPublicIXResponseDTO;
import org.mapstruct.Mapper;

/**
 * <PRE>
 * 車両可用性リソース品目マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface VehicleAvbResourceItemMapper {

    TransportAbilityPublicIXResponseDTO map(TransportAbilityPublicResponseDTO dto);
}
