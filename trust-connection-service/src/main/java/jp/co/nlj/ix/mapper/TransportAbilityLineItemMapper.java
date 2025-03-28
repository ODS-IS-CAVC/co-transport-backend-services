package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.TransportAbilityLineItem;
import jp.co.nlj.ix.dto.shipperTrspCapacity.LogisticsServiceProviderDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.RoadCarrDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * <PRE>
 * 輸送能力項目マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface TransportAbilityLineItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trspCliPrtyHeadOffId", source = "roadCarrDTO.trspCliPrtyHeadOffId")
    @Mapping(target = "trspCliPrtyBrncOffId", source = "roadCarrDTO.trspCliPrtyBrncOffId")
    @Mapping(target = "trspCliPrtyNameTxt", source = "roadCarrDTO.trspCliPrtyNameTxt")
    @Mapping(target = "roadCarrDepaSpedOrgId", source = "roadCarrDTO.roadCarrDepaSpedOrgId")
    @Mapping(target = "roadCarrDepaSpedOrgNameTxt", source = "roadCarrDTO.roadCarrDepaSpedOrgNameTxt")
    @Mapping(target = "trspCliTelCmmCmpNumTxt", source = "roadCarrDTO.trspCliTelCmmCmpNumTxt")
    @Mapping(target = "roadCarrArrSpedOrgId", source = "roadCarrDTO.roadCarrArrSpedOrgId")
    @Mapping(target = "roadCarrArrSpedOrgNameTxt", source = "roadCarrDTO.roadCarrArrSpedOrgNameTxt")
    @Mapping(target = "logsSrvcPrvPrtyHeadOffId", source = "logisticsServiceProviderDTO.logsSrvcPrvPrtyHeadOffId")
    @Mapping(target = "logsSrvcPrvPrtyBrncOffId", source = "logisticsServiceProviderDTO.logsSrvcPrvPrtyBrncOffId")
    @Mapping(target = "logsSrvcPrvPrtyNameTxt", source = "logisticsServiceProviderDTO.logsSrvcPrvPrtyNameTxt")
    @Mapping(target = "logsSrvcPrvSctSpedOrgId", source = "logisticsServiceProviderDTO.logsSrvcPrvSctSpedOrgId")
    @Mapping(target = "logsSrvcPrvSctSpedOrgNameTxt", source = "logisticsServiceProviderDTO.logsSrvcPrvSctSpedOrgNameTxt")
    @Mapping(target = "logsSrvcPrvSctPrimCntPersNameTxt", source = "logisticsServiceProviderDTO.logsSrvcPrvSctPrimCntPersNameTxt")
    @Mapping(target = "logsSrvcPrvSctTelCmmCmpNumTxt", source = "logisticsServiceProviderDTO.logsSrvcPrvSctTelCmmCmpNumTxt")
    TransportAbilityLineItem mapToTransportAbilityLineItem(RoadCarrDTO roadCarrDTO,
        LogisticsServiceProviderDTO logisticsServiceProviderDTO);

    @Mapping(target = "carInfos", ignore = true)
    @Mapping(target = "driverInformations", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateTransportAbilityLineItem(@MappingTarget TransportAbilityLineItem target, RoadCarrDTO roadCarrDTO,
        LogisticsServiceProviderDTO logisticsServiceProviderDTO);
}
