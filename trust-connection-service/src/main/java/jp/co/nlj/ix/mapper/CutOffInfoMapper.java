package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.CutOffInfo;
import jp.co.nlj.ix.dto.shipperTrspCapacity.CutOffInfoDTO;
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
