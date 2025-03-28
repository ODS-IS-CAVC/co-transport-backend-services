package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqShipToPrtyRqrm;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqShipToPrtyRqrmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 要求からエンティティへのマップ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ReqShipToPrtyRqrmMapper {

    @Mapping(source = "toTrmsOfCarSizeCd", target = "toTrmsOfCarSizeCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toTrmsOfCarHghtMeas", target = "toTrmsOfCarHghtMeas", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toTrmsOfGtpCertTxt", target = "toTrmsOfGtpCertTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfDelTxt", target = "trmsOfDelTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toTrmsOfGodsHndTxt", target = "toTrmsOfGodsHndTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "ancWrkOfDelTxt", target = "ancWrkOfDelTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toSpclWrkTxt", target = "toSpclWrkTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    ReqShipToPrtyRqrm toEntity(ReqShipToPrtyRqrmDTO shipToPrtyRqrmDTO);

    @Mapping(source = "toTrmsOfCarSizeCd", target = "toTrmsOfCarSizeCd")
    @Mapping(source = "toTrmsOfCarHghtMeas", target = "toTrmsOfCarHghtMeas")
    @Mapping(source = "toTrmsOfGtpCertTxt", target = "toTrmsOfGtpCertTxt")
    @Mapping(source = "trmsOfDelTxt", target = "trmsOfDelTxt")
    @Mapping(source = "toTrmsOfGodsHndTxt", target = "toTrmsOfGodsHndTxt")
    @Mapping(source = "ancWrkOfDelTxt", target = "ancWrkOfDelTxt")
    @Mapping(source = "toSpclWrkTxt", target = "toSpclWrkTxt")
    ReqShipToPrtyRqrmDTO toDTO(ReqShipToPrtyRqrm shipToPrtyRqrm);
}
