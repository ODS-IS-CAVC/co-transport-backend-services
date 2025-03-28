package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.ReqShipToPrty;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqShipToPrtyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 要求からエンティティへのマップパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface ReqShipToPrtyMapper {

    @Mapping(source = "shipToPrtyHeadOffId", target = "shipToPrtyHeadOffId")
    @Mapping(source = "shipToPrtyBrncOffId", target = "shipToPrtyBrncOffId")
    @Mapping(source = "shipToPrtyNameTxt", target = "shipToPrtyNameTxt")
    @Mapping(source = "shipToSctId", target = "shipToSctId")
    @Mapping(source = "shipToSctNameTxt", target = "shipToSctNameTxt")
    @Mapping(source = "shipToPrimCntId", target = "shipToPrimCntId")
    @Mapping(source = "shipToPrimCntPersNameTxt", target = "shipToPrimCntPersNameTxt")
    @Mapping(source = "shipToTelCmmCmpNumTxt", target = "shipToTelCmmCmpNumTxt")
    @Mapping(source = "shipToPstlAdrsCtyId", target = "shipToPstlAdrsCtyId")
    @Mapping(source = "shipToPstlAdrsId", target = "shipToPstlAdrsId")
    @Mapping(source = "shipToPstlAdrsLineOneTxt", target = "shipToPstlAdrsLineOneTxt")
    @Mapping(source = "shipToPstcCd", target = "shipToPstcCd")
    @Mapping(source = "toPlcCdPrtyId", target = "toPlcCdPrtyId")
    @Mapping(source = "toGlnPrtyId", target = "toGlnPrtyId")
    @Mapping(source = "toJpnUplcCd", target = "toJpnUplcCd")
    @Mapping(source = "toJpnVanSrvcCd", target = "toJpnVanSrvcCd")
    @Mapping(source = "toJpnVanVansCd", target = "toJpnVanVansCd")
    ReqShipToPrty toEntity(ReqShipToPrtyDTO shipToPrtyDTO);

    @Mapping(source = "shipToPrtyHeadOffId", target = "shipToPrtyHeadOffId")
    @Mapping(source = "shipToPrtyBrncOffId", target = "shipToPrtyBrncOffId")
    @Mapping(source = "shipToPrtyNameTxt", target = "shipToPrtyNameTxt")
    @Mapping(source = "shipToSctId", target = "shipToSctId")
    @Mapping(source = "shipToSctNameTxt", target = "shipToSctNameTxt")
    @Mapping(source = "shipToPrimCntId", target = "shipToPrimCntId")
    @Mapping(source = "shipToPrimCntPersNameTxt", target = "shipToPrimCntPersNameTxt")
    @Mapping(source = "shipToTelCmmCmpNumTxt", target = "shipToTelCmmCmpNumTxt")
    @Mapping(source = "shipToPstlAdrsCtyId", target = "shipToPstlAdrsCtyId")
    @Mapping(source = "shipToPstlAdrsId", target = "shipToPstlAdrsId")
    @Mapping(source = "shipToPstlAdrsLineOneTxt", target = "shipToPstlAdrsLineOneTxt")
    @Mapping(source = "shipToPstcCd", target = "shipToPstcCd")
    @Mapping(source = "toPlcCdPrtyId", target = "toPlcCdPrtyId")
    @Mapping(source = "toGlnPrtyId", target = "toGlnPrtyId")
    @Mapping(source = "toJpnUplcCd", target = "toJpnUplcCd")
    @Mapping(source = "toJpnVanSrvcCd", target = "toJpnVanSrvcCd")
    @Mapping(source = "toJpnVanVansCd", target = "toJpnVanVansCd")
    ReqShipToPrtyDTO toDTO(ReqShipToPrty shipToPrty);
}
