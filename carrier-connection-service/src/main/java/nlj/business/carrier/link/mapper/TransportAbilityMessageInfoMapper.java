package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.DriverAvailabilityTime;
import nlj.business.carrier.link.domain.DriverInformation;
import nlj.business.carrier.link.domain.TransportAbilityLineItem;
import nlj.business.carrier.link.domain.TransportAbilityMessageInfo;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import nlj.business.carrier.link.dto.shipperTrspCapacity.CarInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.DriverAvailabilityTimeDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.DriverInformationDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.LogisticsServiceProviderDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.MessageInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.RoadCarrDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.TransportAbilityLineItemDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.VehicleAvailabilityResourceDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * <PRE>
 * トランスポート機能メッセージ情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface TransportAbilityMessageInfoMapper {

    @Mapping(target = "msgDateIssDttm", source = "msgDateIssDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "msgTimeIssDttm", source = "msgTimeIssDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    TransportAbilityMessageInfo mapToTransportAbilityMessageInfo(MessageInfoDTO messageInfoDTO);

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
    TransportAbilityLineItem mapToTransportAbilityLineItem(TransportAbilityLineItemDTO dto, RoadCarrDTO roadCarrDTO,
        LogisticsServiceProviderDTO logisticsServiceProviderDTO);

    @InheritConfiguration
    @Mapping(target = "carInfos", ignore = true)
    @Mapping(target = "driverInformations", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateTransportAbilityLineItem(@MappingTarget TransportAbilityLineItem target, TransportAbilityLineItemDTO dto,
        RoadCarrDTO roadCarrDTO, LogisticsServiceProviderDTO logisticsServiceProviderDTO);

    @Mapping(target = "serviceStrtDate", source = "serviceStrtDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "serviceStrtTime", source = "serviceStrtTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "serviceEndDate", source = "serviceEndDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "serviceEndTime", source = "serviceEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    CarInfo mapToCarInfo(CarInfoDTO carInfoDTO);

    @Mapping(target = "vehicleAvailabilityResources", ignore = true)
    @Mapping(target = "serviceStrtDate", source = "serviceStrtDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "serviceStrtTime", source = "serviceStrtTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "serviceEndDate", source = "serviceEndDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "serviceEndTime", source = "serviceEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "id", ignore = true)
    void updateCarInfo(@MappingTarget CarInfo target, CarInfoDTO carInfoDTO);

    @Mapping(target = "trspOpDateTrmStrtDate", source = "trspOpDateTrmStrtDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "trspOpPlanDateTrmStrtTime", source = "trspOpPlanDateTrmStrtTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "trspOpDateTrmEndDate", source = "trspOpDateTrmEndDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "trspOpPlanDateTrmEndTime", source = "trspOpPlanDateTrmEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "avbDateCllDate", source = "avbDateCllDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "avbFromTimeOfCllTime", source = "avbFromTimeOfCllTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "avbToTimeOfCllTime", source = "avbToTimeOfCllTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "estiDelDatePrfmDttm", source = "estiDelDatePrfmDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "avbFromTimeOfDelTime", source = "avbFromTimeOfDelTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "avbToTimeOfDelTime", source = "avbToTimeOfDelTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    VehicleAvailabilityResource mapToVehicleAvailabilityResource(VehicleAvailabilityResourceDTO dto);

    DriverInformation mapToDriverInformation(DriverInformationDTO driverInformationDTO);

    @Mapping(target = "drvAvbFromDate", source = "drvAvbFromDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "drvAvbFromTimeOfWrkgTime", source = "drvAvbFromTimeOfWrkgTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "drvAvbToDate", source = "drvAvbToDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "drvAvbToTimeOfWrkgTime", source = "drvAvbToTimeOfWrkgTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(target = "drvFrmrOptgDate", source = "drvFrmrOptgDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(target = "drvFrmrOpEndTime", source = "drvFrmrOpEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    DriverAvailabilityTime mapToDriverAvailabilityTime(DriverAvailabilityTimeDTO driverAvailabilityTimeDTO);

    @Mapping(target = "msgDateIssDttm", source = "msgDateIssDttm", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(target = "msgTimeIssDttm", source = "msgTimeIssDttm", qualifiedByName = MapperConstants.TIME_TO_STRING)
    MessageInfoDTO mapMessageInfoToTransportAbility(TransportAbilityMessageInfo transportAbilityMessageInfo);

    RoadCarrDTO mapToRoadCarrDTO(TransportAbilityLineItem transportAbilityLineItem);

    LogisticsServiceProviderDTO mapToLogisticsServiceProviderDTO(TransportAbilityLineItem transportAbilityLineItem);
}
