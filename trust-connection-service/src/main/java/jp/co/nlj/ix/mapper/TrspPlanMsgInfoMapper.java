package jp.co.nlj.ix.mapper;

import java.math.BigDecimal;
import jp.co.nlj.ix.constant.MapperConstants;
import jp.co.nlj.ix.domain.TrspPlanMsgInfo;
import jp.co.nlj.ix.dto.trspPlanLineItem.MsgInfoDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspPlanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 輸送計画メッセージ情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface TrspPlanMsgInfoMapper {

    @Mapping(source = "msgInfo.msgId", target = "msgId")
    @Mapping(source = "msgInfo.msgInfoClsTypCd", target = "msgInfoClsTypCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "msgInfo.msgDateIssDttm", target = "msgDateIssDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "msgInfo.msgTimeIssDttm", target = "msgTimeIssDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "msgInfo.msgFnStasCd", target = "msgFnStasCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "msgInfo.noteDcptTxt", target = "noteDcptTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspPlan.trspPlanStasCd", target = "trspPlanStasCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    TrspPlanMsgInfo toEntity(MsgInfoDTO msgInfo, TrspPlanDTO trspPlan);

    @Mapping(source = "msgId", target = "msgId")
    @Mapping(source = "msgInfoClsTypCd", target = "msgInfoClsTypCd")
    @Mapping(source = "msgDateIssDttm", target = "msgDateIssDttm", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "msgTimeIssDttm", target = "msgTimeIssDttm", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(source = "msgFnStasCd", target = "msgFnStasCd")
    @Mapping(source = "noteDcptTxt", target = "noteDcptTxt")
    MsgInfoDTO toMsgInfoDTO(TrspPlanMsgInfo trspPlanMsgInfo);

    @Mapping(source = "trspPlanStasCd", target = "trspPlanStasCd")
    TrspPlanDTO toTrspPlanDTO(TrspPlanMsgInfo trspPlanMsgInfo);

    @Named(MapperConstants.INTEGER_TO_BIGDECIMAL)
    default BigDecimal integerToBigDecimal(Integer value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }
}
