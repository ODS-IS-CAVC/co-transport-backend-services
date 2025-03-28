package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqTrspPlanMsgInfo;
import nlj.business.carrier.link.dto.trspPlanLineItem.MsgInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 要求からエンティティへのマップ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ReqTrspPlanMsgInfoMapper {

    @Mapping(source = "msgId", target = "msgId")
    @Mapping(source = "msgInfoClsTypCd", target = "msgInfoClsTypCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "msgDateIssDttm", target = "msgDateIssDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "msgTimeIssDttm", target = "msgTimeIssDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "msgFnStasCd", target = "msgFnStasCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "noteDcptTxt", target = "noteDcptTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    ReqTrspPlanMsgInfo toEntity(MsgInfoDTO msgInfo);

    @Mapping(source = "msgId", target = "msgId")
    @Mapping(source = "msgInfoClsTypCd", target = "msgInfoClsTypCd")
    @Mapping(source = "msgDateIssDttm", target = "msgDateIssDttm", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "msgTimeIssDttm", target = "msgTimeIssDttm", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(source = "msgFnStasCd", target = "msgFnStasCd")
    @Mapping(source = "noteDcptTxt", target = "noteDcptTxt")
    MsgInfoDTO toMsgInfoDTO(ReqTrspPlanMsgInfo trspPlanMsgInfo);
}
