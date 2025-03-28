package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.ReqShipFromPrty;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqShipFromPrtyDTO;
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
public interface ReqShipFromPrtyMapper {

    @Mapping(source = "shipFromPrtyHeadOffId", target = "shipFromPrtyHeadOffId")
    @Mapping(source = "shipFromPrtyBrncOffId", target = "shipFromPrtyBrncOffId")
    @Mapping(source = "shipFromPrtyNameTxt", target = "shipFromPrtyNameTxt")
    @Mapping(source = "shipFromSctId", target = "shipFromSctId")
    @Mapping(source = "shipFromSctNameTxt", target = "shipFromSctNameTxt")
    @Mapping(source = "shipFromTelCmmCmpNumTxt", target = "shipFromTelCmmCmpNumTxt")
    @Mapping(source = "shipFromPstlAdrsCtyId", target = "shipFromPstlAdrsCtyId")
    @Mapping(source = "shipFromPstlAdrsLineOneTxt", target = "shipFromPstlAdrsLineOneTxt")
    @Mapping(source = "shipFromPstcCd", target = "shipFromPstcCd")
    @Mapping(source = "fromPlcCdPrtyId", target = "fromPlcCdPrtyId")
    @Mapping(source = "fromGlnPrtyId", target = "fromGlnPrtyId")
    @Mapping(source = "fromJpnUplcCd", target = "fromJpnUplcCd")
    @Mapping(source = "fromJpnVanSrvcCd", target = "fromJpnVanSrvcCd")
    @Mapping(source = "fromJpnVanVansCd", target = "fromJpnVanVansCd")
    ReqShipFromPrty toEntity(ReqShipFromPrtyDTO shipFromPrtyDTO);

    @Mapping(source = "shipFromPrtyHeadOffId", target = "shipFromPrtyHeadOffId")
    @Mapping(source = "shipFromPrtyBrncOffId", target = "shipFromPrtyBrncOffId")
    @Mapping(source = "shipFromPrtyNameTxt", target = "shipFromPrtyNameTxt")
    @Mapping(source = "shipFromSctId", target = "shipFromSctId")
    @Mapping(source = "shipFromSctNameTxt", target = "shipFromSctNameTxt")
    @Mapping(source = "shipFromTelCmmCmpNumTxt", target = "shipFromTelCmmCmpNumTxt")
    @Mapping(source = "shipFromPstlAdrsCtyId", target = "shipFromPstlAdrsCtyId")
    @Mapping(source = "shipFromPstlAdrsLineOneTxt", target = "shipFromPstlAdrsLineOneTxt")
    @Mapping(source = "shipFromPstcCd", target = "shipFromPstcCd")
    @Mapping(source = "fromPlcCdPrtyId", target = "fromPlcCdPrtyId")
    @Mapping(source = "fromGlnPrtyId", target = "fromGlnPrtyId")
    @Mapping(source = "fromJpnUplcCd", target = "fromJpnUplcCd")
    @Mapping(source = "fromJpnVanSrvcCd", target = "fromJpnVanSrvcCd")
    @Mapping(source = "fromJpnVanVansCd", target = "fromJpnVanVansCd")
    ReqShipFromPrtyDTO toDTO(ReqShipFromPrty shipFromPrty);
}
