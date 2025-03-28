package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.FreeTimeInfo;
import jp.co.nlj.ix.dto.shipperTrspCapacity.FreeTimeInfoDTO;
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
