package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.ReqShipFromPrtyRqrm;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqShipFromPrtyRqrmDTO;
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
public interface ReqShipFromPrtyRqrmMapper {

    @Mapping(source = "fromTrmsOfCarSizeCd", target = "fromTrmsOfCarSizeCd")
    @Mapping(source = "fromTrmsOfCarHghtMeas", target = "fromTrmsOfCarHghtMeas")
    @Mapping(source = "fromTrmsOfGtpCertTxt", target = "fromTrmsOfGtpCertTxt")
    @Mapping(source = "trmsOfCllTxt", target = "trmsOfCllTxt")
    @Mapping(source = "fromTrmsOfGodsHndTxt", target = "fromTrmsOfGodsHndTxt")
    @Mapping(source = "ancWrkOfCllTxt", target = "ancWrkOfCllTxt")
    @Mapping(source = "fromSpclWrkTxt", target = "fromSpclWrkTxt")
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
