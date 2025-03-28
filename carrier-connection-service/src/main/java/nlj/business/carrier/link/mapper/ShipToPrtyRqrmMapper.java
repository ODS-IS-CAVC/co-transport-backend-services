package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqShipToPrtyRqrm;
import nlj.business.carrier.link.domain.ShipToPrtyRqrm;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipToPrtyRqrmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 目的地要求マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ShipToPrtyRqrmMapper {

    @Mapping(source = "trmsOfCarSizeCd", target = "trmsOfCarSizeCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfCarHghtMeas", target = "trmsOfCarHghtMeas", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfGtpCertTxt", target = "trmsOfGtpCertTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfDelTxt", target = "trmsOfDelTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfGodsHndTxt", target = "trmsOfGodsHndTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "ancWrkOfDelTxt", target = "ancWrkOfDelTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "spclWrkTxt", target = "spclWrkTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    ShipToPrtyRqrm toEntity(ShipToPrtyRqrmDTO shipToPrtyRqrmDTO);

    @Mapping(source = "trmsOfCarSizeCd", target = "trmsOfCarSizeCd")
    @Mapping(source = "trmsOfCarHghtMeas", target = "trmsOfCarHghtMeas")
    @Mapping(source = "trmsOfGtpCertTxt", target = "trmsOfGtpCertTxt")
    @Mapping(source = "trmsOfDelTxt", target = "trmsOfDelTxt")
    @Mapping(source = "trmsOfGodsHndTxt", target = "trmsOfGodsHndTxt")
    @Mapping(source = "ancWrkOfDelTxt", target = "ancWrkOfDelTxt")
    @Mapping(source = "spclWrkTxt", target = "spclWrkTxt")
    ShipToPrtyRqrmDTO toDTO(ShipToPrtyRqrm shipToPrtyRqrm);

    ShipToPrtyRqrm toShipToPrtyRqrm(ReqShipToPrtyRqrm reqShipToPrtyRqrm);
}
