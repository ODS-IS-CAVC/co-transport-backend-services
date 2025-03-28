package nlj.business.transaction.mapper;

import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.dto.TransOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 運送業者取引マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface TransOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    TransOrder toTransOrder(TransMatching transMatching);

    TransOrderDTO toDTO(TransOrder transOrder);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "transType", ignore = true)
    @Mapping(target = "shipperOperatorId", source = "cnsLineItemByDate.operatorId")
    @Mapping(target = "shipperOperatorCode", source = "cnsLineItemByDate.operatorCode")
    @Mapping(target = "shipperOperatorName", source = "cnsLineItemByDate.operatorName")
    @Mapping(target = "carrierOperatorId", source = "vehicleAvbResourceItem.operatorId")
    @Mapping(target = "carrierOperatorCode", source = "vehicleAvbResourceItem.operatorCode")
    @Mapping(target = "carrierOperatorName", source = "vehicleAvbResourceItem.operatorName")
    @Mapping(target = "cnsLineItemByDateId", source = "cnsLineItemByDate.id")
    @Mapping(target = "reqCnsLineItemId", source = "cnsLineItemByDate.reqCnsLineItemId")
    @Mapping(target = "vehicleAvbResourceId", source = "vehicleAvbResourceItem.vehicleAvbResourceId")
    @Mapping(target = "vehicleAvbResourceItemId", source = "vehicleAvbResourceItem.id")
    @Mapping(target = "departureFrom", source = "vehicleAvbResourceItem.departureFrom")
    @Mapping(target = "arrivalTo", source = "vehicleAvbResourceItem.arrivalTo")
    @Mapping(target = "transportDate", source = "vehicleAvbResourceItem.day")
    @Mapping(target = "proposePrice", source = "vehicleAvbResourceItem.price")
    @Mapping(target = "proposeDepartureTime", source = "vehicleAvbResourceItem.departureTimeMin")
    @Mapping(target = "proposeArrivalTime", source = "vehicleAvbResourceItem.arrivalTime")
    @Mapping(target = "requestPrice", source = "cnsLineItemByDate.pricePerUnit")
    @Mapping(target = "requestCollectionTimeFrom", source = "cnsLineItemByDate.collectionTimeFrom")
    @Mapping(target = "requestCollectionTimeTo", source = "cnsLineItemByDate.collectionTimeTo")
    @Mapping(target = "requestSnapshot", source = "cnsLineItemByDate")
    @Mapping(target = "proposeSnapshot", source = "vehicleAvbResourceItem")
    @Mapping(target = "serviceName", source = "vehicleAvbResourceItem.tripName")
    @Mapping(target = "itemNameTxt", source = "cnsLineItemByDate.transportName")
    TransOrder toTransOrder(CnsLineItemByDate cnsLineItemByDate, VehicleAvbResourceItem vehicleAvbResourceItem);
}
