package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqShipToPrty;
import nlj.business.carrier.link.domain.ShipToPrty;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipToPrtyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 目的地マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ShipToPrtyMapper {

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
    @Mapping(source = "plcCdPrtyId", target = "plcCdPrtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "glnPrtyId", target = "glnPrtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "jpnUplcCd", target = "jpnUplcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "jpnVanSrvcCd", target = "jpnVanSrvcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "jpnVanVansCd", target = "jpnVanVansCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    ShipToPrty toEntity(ShipToPrtyDTO shipToPrtyDTO);

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
    @Mapping(source = "plcCdPrtyId", target = "plcCdPrtyId")
    @Mapping(source = "glnPrtyId", target = "glnPrtyId")
    @Mapping(source = "jpnUplcCd", target = "jpnUplcCd")
    @Mapping(source = "jpnVanSrvcCd", target = "jpnVanSrvcCd")
    @Mapping(source = "jpnVanVansCd", target = "jpnVanVansCd")
    ShipToPrtyDTO toDTO(ShipToPrty shipToPrty);

    ShipToPrty toShipToPrty(ReqShipToPrty reqShipToPrty);
}
