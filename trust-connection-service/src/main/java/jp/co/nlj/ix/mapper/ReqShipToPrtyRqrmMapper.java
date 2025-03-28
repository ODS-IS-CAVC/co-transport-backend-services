package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.ReqShipToPrtyRqrm;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqShipToPrtyRqrmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 要求からエンティティへのマップパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring")
public interface ReqShipToPrtyRqrmMapper {

    @Mapping(source = "toTrmsOfCarSizeCd", target = "toTrmsOfCarSizeCd")
    @Mapping(source = "toTrmsOfCarHghtMeas", target = "toTrmsOfCarHghtMeas")
    @Mapping(source = "toTrmsOfGtpCertTxt", target = "toTrmsOfGtpCertTxt")
    @Mapping(source = "trmsOfDelTxt", target = "trmsOfDelTxt")
    @Mapping(source = "toTrmsOfGodsHndTxt", target = "toTrmsOfGodsHndTxt")
    @Mapping(source = "ancWrkOfDelTxt", target = "ancWrkOfDelTxt")
    @Mapping(source = "toSpclWrkTxt", target = "toSpclWrkTxt")
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
