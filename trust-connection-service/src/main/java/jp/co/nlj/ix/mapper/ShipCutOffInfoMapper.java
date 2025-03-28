package jp.co.nlj.ix.mapper;


import jp.co.nlj.ix.domain.ShipCutOffInfo;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipCutOffInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 船舶遮断情報マップパー。<BR>
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
