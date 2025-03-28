package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.domain.ShipCutOffInfo;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipCutOffInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 船舶遮断情報マップ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring")
public interface ShipCutOffInfoMapper {

    @Mapping(source = "cutOffTime", target = "cutOffTime")
    @Mapping(source = "cutOffFee", target = "cutOffFee")
    ShipCutOffInfo toEntity(ShipCutOffInfoDTO shipCutOffInfoDTO);


    @Mapping(source = "cutOffTime", target = "cutOffTime")
    @Mapping(source = "cutOffFee", target = "cutOffFee")
    ShipCutOffInfoDTO toDTO(ShipCutOffInfo shipCutOffInfo);
}
