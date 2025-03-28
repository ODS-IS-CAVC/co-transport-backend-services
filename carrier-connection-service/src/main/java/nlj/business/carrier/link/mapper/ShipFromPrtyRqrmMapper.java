package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqShipFromPrtyRqrm;
import nlj.business.carrier.link.domain.ShipFromPrtyRqrm;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipFromPrtyRqrmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 出発地要求マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ShipFromPrtyRqrmMapper {

    @Mapping(source = "trmsOfCarSizeCd", target = "trmsOfCarSizeCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfCarHghtMeas", target = "trmsOfCarHghtMeas", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfGtpCertTxt", target = "trmsOfGtpCertTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfCllTxt", target = "trmsOfCllTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfGodsHndTxt", target = "trmsOfGodsHndTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "ancWrkOfCllTxt", target = "ancWrkOfCllTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "spclWrkTxt", target = "spclWrkTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    ShipFromPrtyRqrm toEntity(ShipFromPrtyRqrmDTO shipFromPrtyRqrmDTO);

    @Mapping(source = "trmsOfCarSizeCd", target = "trmsOfCarSizeCd")
    @Mapping(source = "trmsOfCarHghtMeas", target = "trmsOfCarHghtMeas")
    @Mapping(source = "trmsOfGtpCertTxt", target = "trmsOfGtpCertTxt")
    @Mapping(source = "trmsOfCllTxt", target = "trmsOfCllTxt")
    @Mapping(source = "trmsOfGodsHndTxt", target = "trmsOfGodsHndTxt")
    @Mapping(source = "ancWrkOfCllTxt", target = "ancWrkOfCllTxt")
    @Mapping(source = "spclWrkTxt", target = "spclWrkTxt")
    ShipFromPrtyRqrmDTO toDTO(ShipFromPrtyRqrm shipFromPrtyRqrm);

    ShipFromPrtyRqrm toShipFromPrtyRqrm(ReqShipFromPrtyRqrm reqShipFromPrtyRqrm);
}
