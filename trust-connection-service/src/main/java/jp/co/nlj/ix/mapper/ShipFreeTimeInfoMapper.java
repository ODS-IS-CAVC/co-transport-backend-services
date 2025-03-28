package jp.co.nlj.ix.mapper;


import jp.co.nlj.ix.domain.ShipFreeTimeInfo;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipFreeTimeInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 船の空き時間情報マップパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring")
public interface ShipFreeTimeInfoMapper {

    @Mapping(source = "freeTime", target = "freeTime")
    @Mapping(source = "freeTimeFee", target = "freeTimeFee")
    ShipFreeTimeInfo toEntity(ShipFreeTimeInfoDTO shipFreeTimeInfoDTO);

    @Mapping(source = "freeTime", target = "freeTime")
    @Mapping(source = "freeTimeFee", target = "freeTimeFee")
    ShipFreeTimeInfoDTO toDTO(ShipFreeTimeInfo shipFreeTimeInfo);
}
