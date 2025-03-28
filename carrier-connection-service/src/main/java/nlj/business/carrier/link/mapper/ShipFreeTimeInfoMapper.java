package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.domain.ShipFreeTimeInfo;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipFreeTimeInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 船の空き時間情報マップ.<BR>
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
