package nlj.business.transaction.mapper;

import java.util.List;
import java.util.Objects;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.domain.carrier.VehicleDiagramItemTrailer;
import nlj.business.transaction.domain.shipper.TransportPlanItem;
import nlj.business.transaction.dto.TransMatchingDTO;
import nlj.business.transaction.dto.VehicleDiagramItemTrailerSnapshot;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * <PRE>
 * トランスポートマッチングマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {TransportPlanItemMapper.class, VehicleDiagramItemTrailerMapper.class})
public interface TransMatchingMapper {

    @Mapping(target = "shipperOperatorId", source = "trspPlanItem.transportPlan.operatorId")
    @Mapping(target = "carrierOperatorId", source = "vehicleDiagramItemTrailer.operatorId")
    @Mapping(target = "departureFrom", source = "trspPlanItem.departureFrom")
    @Mapping(target = "arrivalTo", source = "trspPlanItem.arrivalTo")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestSnapshot", source = "trspPlanItem")
    @Mapping(target = "proposeSnapshot", source = "vehicleDiagramItemTrailer")
    TransMatching match(TransportPlanItem trspPlanItem, VehicleDiagramItemTrailer vehicleDiagramItemTrailer);

    @Mapping(target = "shipperOperatorId", source = "trspPlanItem.transportPlan.operatorId")
    @Mapping(target = "carrierOperatorId", source = "vehicleTrailerSnapshot.operatorId")
    @Mapping(target = "departureFrom", source = "trspPlanItem.departureFrom")
    @Mapping(target = "arrivalTo", source = "trspPlanItem.arrivalTo")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestSnapshot", source = "trspPlanItem")
    @Mapping(target = "proposeSnapshot", source = "vehicleTrailerSnapshot")
    TransMatching match(TransportPlanItem trspPlanItem, VehicleDiagramItemTrailerSnapshot vehicleTrailerSnapshot);

    List<TransMatching> toMapperTransMatching(List<TransMatchingDTO> transMatchingDTO);

    @Mapping(target = "id", ignore = true)
    TransMatching toMapperTransMatching(TransMatchingDTO transMatchingDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    void updateTransMatching(@MappingTarget TransMatching transMatching, TransMatchingDTO transMatchingDTO);

    @Mapping(target = "shipperOperatorId", source = "cnsLineItemByDate.operatorId")
    @Mapping(target = "shipperOperatorCode", source = "cnsLineItemByDate.operatorCode")
    @Mapping(target = "carrierOperatorId", source = "vehicleAvbResourceItem.operatorId")
    @Mapping(target = "carrierOperatorCode", source = "vehicleAvbResourceItem.operatorCode")
    @Mapping(target = "departureFrom", source = "cnsLineItemByDate.departureFrom")
    @Mapping(target = "arrivalTo", source = "cnsLineItemByDate.arrivalTo")
    @Mapping(target = "transportDate", source = "cnsLineItemByDate.collectionDate")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestSnapshot", source = "cnsLineItemByDate")
    @Mapping(target = "proposeSnapshot", source = "vehicleAvbResourceItem")
    @Mapping(target = "transType", source = "cnsLineItemByDate.transType")
    @Mapping(target = "cnsLineItemByDateId", source = "cnsLineItemByDate.id")
    @Mapping(target = "vehicleAvbResourceItemId", source = "vehicleAvbResourceItem.id")
    @Mapping(target = "reqCnsLineItemId", source = "cnsLineItemByDate.reqCnsLineItemId")
    @Mapping(target = "vehicleAvbResourceId", source = "vehicleAvbResourceItem.vehicleAvbResourceId")
    @Mapping(target = "carrierOperatorName", source = "vehicleAvbResourceItem.operatorName")
    @Mapping(target = "shipperOperatorName", source = "cnsLineItemByDate.operatorName")
    @Mapping(target = "vehicleDiagramItemTrailerId", source = "vehicleAvbResourceItem.vehicleDiagramItemTrailerId")
    @Mapping(target = "transportPlanItemId", source = "cnsLineItemByDate.transportPlanItemId")
    TransMatchingDTO match(CnsLineItemByDate cnsLineItemByDate, VehicleAvbResourceItem vehicleAvbResourceItem);

    @AfterMapping
    default void addCarrier2OperatorInfo(@MappingTarget TransMatchingDTO dto, CnsLineItemByDate cnsLineItemByDate,
        VehicleAvbResourceItem vehicleAvbResourceItem) {
        if (cnsLineItemByDate != null && cnsLineItemByDate.getTransType() != null
            && Objects.equals(cnsLineItemByDate.getTransType(),
            DataBaseConstant.CnsLineItemByDate.TransType.CARRIER_REQUEST)) {
            dto.setCnsLineItemId(cnsLineItemByDate.getCnsLineItemId());
            dto.setCarrierOperatorId(cnsLineItemByDate.getOperatorId());
            dto.setCarrierOperatorCode(cnsLineItemByDate.getOperatorCode());
            dto.setCarrierOperatorName(cnsLineItemByDate.getOperatorName());
            if (vehicleAvbResourceItem != null) {
                dto.setCarrier2OperatorId(vehicleAvbResourceItem.getOperatorId());
                dto.setCarrier2OperatorCode(vehicleAvbResourceItem.getOperatorCode());
                dto.setCarrier2OperatorName(vehicleAvbResourceItem.getOperatorName());
            }
        }
    }
}
