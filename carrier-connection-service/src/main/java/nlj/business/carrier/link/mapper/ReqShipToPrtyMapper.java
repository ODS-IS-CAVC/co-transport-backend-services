package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqShipToPrty;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqShipToPrtyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 要求からエンティティへのマップ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ReqShipToPrtyMapper {

    @Mapping(source = "shipToPrtyHeadOffId", target = "shipToPrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToPrtyBrncOffId", target = "shipToPrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToPrtyNameTxt", target = "shipToPrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToSctId", target = "shipToSctId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToSctNameTxt", target = "shipToSctNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToPrimCntId", target = "shipToPrimCntId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToPrimCntPersNameTxt", target = "shipToPrimCntPersNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToTelCmmCmpNumTxt", target = "shipToTelCmmCmpNumTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToPstlAdrsCtyId", target = "shipToPstlAdrsCtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToPstlAdrsId", target = "shipToPstlAdrsId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToPstlAdrsLineOneTxt", target = "shipToPstlAdrsLineOneTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipToPstcCd", target = "shipToPstcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toPlcCdPrtyId", target = "toPlcCdPrtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toGlnPrtyId", target = "toGlnPrtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toJpnUplcCd", target = "toJpnUplcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toJpnVanSrvcCd", target = "toJpnVanSrvcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "toJpnVanVansCd", target = "toJpnVanVansCd", qualifiedByName = MapperConstants.TRIM_VALUE)
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
