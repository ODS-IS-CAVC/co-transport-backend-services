package jp.co.nlj.ix.mapper;


import jp.co.nlj.ix.constant.MapperConstants;
import jp.co.nlj.ix.domain.ShipFromPrty;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipFromPrtyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 船の出発地マップパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ShipFromPrtyMapper {

    @Mapping(source = "shipFromPrtyHeadOffId", target = "shipFromPrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPrtyBrncOffId", target = "shipFromPrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPrtyNameTxt", target = "shipFromPrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromSctId", target = "shipFromSctId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromSctNameTxt", target = "shipFromSctNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromTelCmmCmpNumTxt", target = "shipFromTelCmmCmpNumTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPstlAdrsCtyId", target = "shipFromPstlAdrsCtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPstlAdrsId", target = "shipFromPstlAdrsId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPstlAdrsLineOneTxt", target = "shipFromPstlAdrsLineOneTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPstcCd", target = "shipFromPstcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "plcCdPrtyId", target = "plcCdPrtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "glnPrtyId", target = "glnPrtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "jpnUplcCd", target = "jpnUplcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "jpnVanSrvcCd", target = "jpnVanSrvcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "jpnVanVansCd", target = "jpnVanVansCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    ShipFromPrty toEntity(ShipFromPrtyDTO shipFromPrtyDTO);

    @Mapping(source = "shipFromPrtyHeadOffId", target = "shipFromPrtyHeadOffId")
    @Mapping(source = "shipFromPrtyBrncOffId", target = "shipFromPrtyBrncOffId")
    @Mapping(source = "shipFromPrtyNameTxt", target = "shipFromPrtyNameTxt")
    @Mapping(source = "shipFromSctId", target = "shipFromSctId")
    @Mapping(source = "shipFromSctNameTxt", target = "shipFromSctNameTxt")
    @Mapping(source = "shipFromTelCmmCmpNumTxt", target = "shipFromTelCmmCmpNumTxt")
    @Mapping(source = "shipFromPstlAdrsCtyId", target = "shipFromPstlAdrsCtyId")
    @Mapping(source = "shipFromPstlAdrsId", target = "shipFromPstlAdrsId")
    @Mapping(source = "shipFromPstlAdrsLineOneTxt", target = "shipFromPstlAdrsLineOneTxt")
    @Mapping(source = "shipFromPstcCd", target = "shipFromPstcCd")
    @Mapping(source = "plcCdPrtyId", target = "plcCdPrtyId")
    @Mapping(source = "glnPrtyId", target = "glnPrtyId")
    @Mapping(source = "jpnUplcCd", target = "jpnUplcCd")
    @Mapping(source = "jpnVanSrvcCd", target = "jpnVanSrvcCd")
    @Mapping(source = "jpnVanVansCd", target = "jpnVanVansCd")
    ShipFromPrtyDTO toDTO(ShipFromPrty shipFromPrty);
}
