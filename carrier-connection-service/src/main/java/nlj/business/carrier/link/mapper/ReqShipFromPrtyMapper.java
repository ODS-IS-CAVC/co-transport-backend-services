package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqShipFromPrty;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqShipFromPrtyDTO;
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
public interface ReqShipFromPrtyMapper {

    @Mapping(source = "shipFromPrtyHeadOffId", target = "shipFromPrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPrtyBrncOffId", target = "shipFromPrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPrtyNameTxt", target = "shipFromPrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromSctId", target = "shipFromSctId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromSctNameTxt", target = "shipFromSctNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromTelCmmCmpNumTxt", target = "shipFromTelCmmCmpNumTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPstlAdrsCtyId", target = "shipFromPstlAdrsCtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPstlAdrsLineOneTxt", target = "shipFromPstlAdrsLineOneTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "shipFromPstcCd", target = "shipFromPstcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromPlcCdPrtyId", target = "fromPlcCdPrtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromGlnPrtyId", target = "fromGlnPrtyId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromJpnUplcCd", target = "fromJpnUplcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromJpnVanSrvcCd", target = "fromJpnVanSrvcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fromJpnVanVansCd", target = "fromJpnVanVansCd", qualifiedByName = MapperConstants.TRIM_VALUE)
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
