package nlj.business.shipper.mapper;

import nlj.business.shipper.constant.MapperConstants;
import nlj.business.shipper.domain.TransportPlanCargoInfo;
import nlj.business.shipper.dto.TransportPlanCargoInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * 輸送計画マッパーインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface TransportPlanCargoInfoMapper {

    @Named(MapperConstants.TO_TRANSPORT_PLAN_CARGO_INFO_DTO)
    TransportPlanCargoInfoDTO toTransportPlanCargoInfoDTO(TransportPlanCargoInfo entity);

    @Named(MapperConstants.TO_TRANSPORT_PLAN_CARGO_INFO_ENTITY)
    TransportPlanCargoInfo toTransportPlanCargoInfoEntity(TransportPlanCargoInfoDTO dto);

}
