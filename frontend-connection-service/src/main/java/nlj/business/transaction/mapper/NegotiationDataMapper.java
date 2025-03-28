package nlj.business.transaction.mapper;

import nlj.business.transaction.dto.CarrierNegotiation;
import nlj.business.transaction.dto.NegotiationData;
import nlj.business.transaction.dto.ShipperNegotiation;
import nlj.business.transaction.dto.request.CarrierNegotiationDTO;
import nlj.business.transaction.dto.request.ShipperNegotiationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 交渉データマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = DateTimeMapper.class)
public interface NegotiationDataMapper {

    @Mapping(target = "departureDate", source = "departureDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "arrivalDate", source = "arrivalDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "collectionTimeFrom", source = "collectionTimeFrom", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "collectionTimeTo", source = "collectionTimeTo", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "comment", source = "comment")
    ShipperNegotiation map(ShipperNegotiationDTO dto);

    @Mapping(target = "shipper", source = "dto")
    NegotiationData mapNegotiationData(ShipperNegotiationDTO dto);


    @Mapping(target = "shipper", source = "dto")
    NegotiationData updateNegotiationDate(NegotiationData negotiationData, ShipperNegotiationDTO dto);

    @Mapping(target = "departureDate", source = "departureDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "arrivalDate", source = "arrivalDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "departureTime", source = "departureTime", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "arrivalTime", source = "arrivalTime", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "comment", source = "comment")
    CarrierNegotiation map(CarrierNegotiationDTO dto);

    @Mapping(target = "carrier", source = "dto")
    NegotiationData mapNegotiationDataCarrier(CarrierNegotiationDTO dto);
}
