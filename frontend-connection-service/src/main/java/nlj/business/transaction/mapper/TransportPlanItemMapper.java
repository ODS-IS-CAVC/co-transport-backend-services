package nlj.business.transaction.mapper;

import java.util.List;
import nlj.business.transaction.domain.shipper.TransportPlanItem;
import nlj.business.transaction.dto.TransportPlanItemDTO;
import nlj.business.transaction.dto.TransportPlanItemSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 輸送計画項目マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface TransportPlanItemMapper {

    //@Mapping(target = "transportPlanTrailerDTOS", source = "transportPlanItem.transportPlanTrailers")
    @Mapping(target = "transportPlanDTO", source = "transportPlanItem.transportPlan")
    TransportPlanItemDTO mapToTransportPlanItemDTO(TransportPlanItem transportPlanItem);

    List<TransportPlanItemDTO> mapToTransportPlanItemDTO(List<TransportPlanItem> transportPlanItems);

    @Mapping(target = "transportPlanId", source = "transportPlanItem.transportPlan.id")
    @Mapping(target = "operatorId", source = "transportPlanItem.transportPlan.operatorId")
    TransportPlanItemSnapshot mapToSnapshot(TransportPlanItem transportPlanItem);

    List<TransportPlanItemSnapshot> mapToListShipperMatching(List<TransportPlanItem> transportPlanItems);
}
