package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.domain.FreeTimeInfo;
import nlj.business.carrier.link.dto.shipperTrspCapacity.FreeTimeInfoDTO;
import org.mapstruct.Mapper;

/**
 * <PRE>
 * フリータイム情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = BaseMapper.class)
public interface FreeTimeInfoMapper {

    FreeTimeInfo mapToFreeTimeInfo(FreeTimeInfoDTO freeTimeInfoDTO);
}
