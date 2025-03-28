package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.constant.MapperConstants;
import jp.co.nlj.ix.domain.TransportAbilityLineItem;
import jp.co.nlj.ix.domain.TransportAbilityMessageInfo;
import jp.co.nlj.ix.dto.shipperTrspCapacity.LogisticsServiceProviderDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.MessageInfoDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.RoadCarrDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * トランスポート機能メッセージ情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface TransportAbilityMessageInfoMapper {

    @Mapping(target = "msgDateIssDttm", source = "msgDateIssDttm", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "msgTimeIssDttm", source = "msgTimeIssDttm", qualifiedByName = MapperConstants.TIME_TO_STRING)
    MessageInfoDTO mapMessageInfoToTransportAbility(TransportAbilityMessageInfo transportAbilityMessageInfo);

    RoadCarrDTO mapToRoadCarrDTO(TransportAbilityLineItem transportAbilityLineItem);

    LogisticsServiceProviderDTO mapToLogisticsServiceProviderDTO(TransportAbilityLineItem transportAbilityLineItem);
}
