package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.domain.CutOffInfo;
import nlj.business.carrier.link.dto.shipperTrspCapacity.CutOffInfoDTO;
import org.mapstruct.Mapper;

/**
 * <PRE>
 * カットオフ情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = BaseMapper.class)
public interface CutOffInfoMapper {

    CutOffInfo mapToCutOffInfo(CutOffInfoDTO cutOffInfoDTO);
}
