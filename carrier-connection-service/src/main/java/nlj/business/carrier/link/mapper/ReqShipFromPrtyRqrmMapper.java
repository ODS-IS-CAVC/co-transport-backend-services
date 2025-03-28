package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqShipFromPrtyRqrm;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqShipFromPrtyRqrmDTO;
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
public interface ReqShipFromPrtyRqrmMapper {

    @Mapping(source = "fromTrmsOfCarSizeCd", target = "fromTrmsOfCarSizeCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromTrmsOfCarHghtMeas", target = "fromTrmsOfCarHghtMeas", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromTrmsOfGtpCertTxt", target = "fromTrmsOfGtpCertTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trmsOfCllTxt", target = "trmsOfCllTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromTrmsOfGodsHndTxt", target = "fromTrmsOfGodsHndTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "ancWrkOfCllTxt", target = "ancWrkOfCllTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromSpclWrkTxt", target = "fromSpclWrkTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    ReqShipFromPrtyRqrm toEntity(ReqShipFromPrtyRqrmDTO shipFromPrtyRqrmDTO);

    @Mapping(source = "fromTrmsOfCarSizeCd", target = "fromTrmsOfCarSizeCd")
    @Mapping(source = "fromTrmsOfCarHghtMeas", target = "fromTrmsOfCarHghtMeas")
    @Mapping(source = "fromTrmsOfGtpCertTxt", target = "fromTrmsOfGtpCertTxt")
    @Mapping(source = "trmsOfCllTxt", target = "trmsOfCllTxt")
    @Mapping(source = "fromTrmsOfGodsHndTxt", target = "fromTrmsOfGodsHndTxt")
    @Mapping(source = "ancWrkOfCllTxt", target = "ancWrkOfCllTxt")
    @Mapping(source = "fromSpclWrkTxt", target = "fromSpclWrkTxt")
    ReqShipFromPrtyRqrmDTO toDTO(ReqShipFromPrtyRqrm shipFromPrtyRqrm);

}
