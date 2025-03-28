package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.domain.CnsLineItemByDate;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;
import nlj.business.carrier.link.dto.matching.TransMatchingDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * <PRE>
 * 輸送計画行マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface TransMatchingMapper {

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
    @Mapping(target = "vehicleAvbResourceId", source = "vehicleAvbResourceItem.vehicleAvailabilityResource.id")
    TransMatchingDTO match(CnsLineItemByDate cnsLineItemByDate, VehicleAvbResourceItem vehicleAvbResourceItem);

    @Mapping(target = "carrierOperatorId", source = "cnsLineItemByDate.operatorId")
    @Mapping(target = "carrierOperatorCode", source = "cnsLineItemByDate.operatorCode")
    @Mapping(target = "carrier2OperatorId", source = "vehicleAvbResourceItem.operatorId")
    @Mapping(target = "carrier2OperatorCode", source = "vehicleAvbResourceItem.operatorCode")
    @Mapping(target = "departureFrom", source = "cnsLineItemByDate.departureFrom")
    @Mapping(target = "arrivalTo", source = "cnsLineItemByDate.arrivalTo")
    @Mapping(target = "transportDate", source = "cnsLineItemByDate.collectionDate")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestSnapshot", source = "cnsLineItemByDate")
    @Mapping(target = "proposeSnapshot", source = "vehicleAvbResourceItem")
    @Mapping(target = "cnsLineItemByDateId", source = "cnsLineItemByDate.id")
    @Mapping(target = "cnsLineItemId", source = "cnsLineItemByDate.cnsLineItemId")
    @Mapping(target = "vehicleAvbResourceItemId", source = "vehicleAvbResourceItem.id")
    @Mapping(target = "reqCnsLineItemId", source = "cnsLineItemByDate.reqCnsLineItemId")
    @Mapping(target = "vehicleAvbResourceId", source = "vehicleAvbResourceItem.vehicleAvailabilityResource.id")
    @Mapping(target = "transType", source = "cnsLineItemByDate.transType")
    TransMatchingDTO matchCarrierWithCarrier(CnsLineItemByDate cnsLineItemByDate,
        VehicleAvbResourceItem vehicleAvbResourceItem);

    @AfterMapping
    default void addCarrier2OperatorInfo(@MappingTarget TransMatchingDTO dto, CnsLineItemByDate cnsLineItemByDate,
        VehicleAvbResourceItem vehicleAvbResourceItem) {
        dto.setStatus(1);
        if (cnsLineItemByDate != null && cnsLineItemByDate.getTransType() != null
            && cnsLineItemByDate.getTransType() == 1) {
            dto.setCnsLineItemId(cnsLineItemByDate.getCnsLineItemId());
            if (vehicleAvbResourceItem != null) {
                dto.setCarrier2OperatorId(vehicleAvbResourceItem.getOperatorId());
                dto.setCarrier2OperatorCode(vehicleAvbResourceItem.getOperatorCode());
            }
        }
    }
}