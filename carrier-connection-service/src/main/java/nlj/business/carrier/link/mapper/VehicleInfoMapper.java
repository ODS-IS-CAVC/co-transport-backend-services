package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.DataBaseConstant;
import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.HazardousMaterialInfo;
import nlj.business.carrier.link.domain.MaxCarryingCapacity;
import nlj.business.carrier.link.domain.VehicleInfo;
import nlj.business.carrier.link.dto.vehicleInfo.HazardousMaterialInfoDTO;
import nlj.business.carrier.link.dto.vehicleInfo.MaxCarryingCapacityDTO;
import nlj.business.carrier.link.dto.vehicleInfo.MotasInfoDTO;
import nlj.business.carrier.link.dto.vehicleInfo.VehicleDetailsDTO;
import nlj.business.carrier.link.dto.vehicleInfo.VehicleInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * <PRE>
 * 車両情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface VehicleInfoMapper {

    @Mapping(source = "vehicleInfoDTO.registrationNumber", target = "registrationNumber", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.giai", target = "giai", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.registrationTransportOfficeName", target = "registrationTransportOfficeName", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.registrationVehicleType", target = "registrationVehicleType", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.registrationVehicleUse", target = "registrationVehicleUse", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.registrationVehicleId", target = "registrationVehicleId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.chassisNumber", target = "chassisNumber", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.vehicleId", target = "vehicleId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.operatorCorporateNumber", target = "operatorCorporateNumber", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.operatorBusinessCode", target = "operatorBusinessCode", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.ownerCorporateNumber", target = "ownerCorporateNumber", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.ownerBusinessCode", target = "ownerBusinessCode", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.vehicleType", target = "vehicleType", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.hazardousMaterialVehicleType", target = "hazardousMaterialVehicleType", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.tractor", target = "tractor", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleInfoDTO.trailer", target = "trailer", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "motasInfoDTO.maxPayload1", target = "maxPayload1", qualifiedByName = MapperConstants.NUMBER_TO_INTEGER)
    @Mapping(source = "motasInfoDTO.maxPayload2", target = "maxPayload2", qualifiedByName = MapperConstants.NUMBER_TO_INTEGER)
    @Mapping(source = "motasInfoDTO.vehicleWeight", target = "vehicleWeight", qualifiedByName = MapperConstants.NUMBER_TO_INTEGER)
    @Mapping(source = "motasInfoDTO.vehicleLength", target = "vehicleLength", qualifiedByName = MapperConstants.NUMBER_TO_INTEGER)
    @Mapping(source = "motasInfoDTO.vehicleWidth", target = "vehicleWidth", qualifiedByName = MapperConstants.NUMBER_TO_INTEGER)
    @Mapping(source = "motasInfoDTO.vehicleHeight", target = "vehicleHeight", qualifiedByName = MapperConstants.NUMBER_TO_INTEGER)
    @Mapping(source = "motasInfoDTO.hazardousMaterialVolume", target = "hazardousMaterialVolume", qualifiedByName = MapperConstants.NUMBER_TO_INTEGER)
    @Mapping(source = "motasInfoDTO.hazardousMaterialSpecificGravity", target = "hazardousMaterialSpecificGravity")
    @Mapping(source = "motasInfoDTO.expiryDate", target = "expiryDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "motasInfoDTO.deregistrationStatus", target = "deregistrationStatus", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleDetailsDTO.bedHeight", target = "bedHeight")
    @Mapping(source = "vehicleDetailsDTO.cargoHeight", target = "cargoHeight")
    @Mapping(source = "vehicleDetailsDTO.cargoWidth", target = "cargoWidth")
    @Mapping(source = "vehicleDetailsDTO.cargoLength", target = "cargoLength")
    @Mapping(source = "vehicleDetailsDTO.maxCargoCapacity", target = "maxCargoCapacity")
    @Mapping(source = "vehicleDetailsDTO.bodyType", target = "bodyType", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleDetailsDTO.powerGate", target = "powerGate", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleDetailsDTO.wingDoors", target = "wingDoors", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleDetailsDTO.refrigerationUnit", target = "refrigerationUnit", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleDetailsDTO.temperatureRangeMin", target = "temperatureRangeMin")
    @Mapping(source = "vehicleDetailsDTO.temperatureRangeMax", target = "temperatureRangeMax")
    @Mapping(source = "vehicleDetailsDTO.craneEquipped", target = "craneEquipped", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleDetailsDTO.vehicleEquipmentNotes", target = "vehicleEquipmentNotes", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "vehicleDetailsDTO.masterDataStartDate", target = "masterDataStartDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "vehicleDetailsDTO.masterDataEndDate", target = "masterDataEndDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Named(MapperConstants.TO_VEHICLE_INFO)
    VehicleInfo toVehicleInfo(VehicleInfoDTO vehicleInfoDTO, MotasInfoDTO motasInfoDTO,
        VehicleDetailsDTO vehicleDetailsDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "operatorId", ignore = true)
    @Mapping(target = "vehicleInfo", ignore = true)
    @Mapping(target = "maxLoadQuantity", qualifiedByName = MapperConstants.NUMBER_TO_INTEGER)
    @Mapping(target = "packageCode", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(target = "packageNameKanji", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(target = "dimensionUnitCode", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Named(MapperConstants.TO_MAX_CARRYING_CAPACITY)
    MaxCarryingCapacity toMaxCarryingCapacity(MaxCarryingCapacityDTO maxCarryingCapacityDTO);

    @Mapping(target = "hazardousMaterialItemCode", source = "hazardousMaterialItemCode", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(target = "hazardousMaterialTextInfo", source = "hazardousMaterialTextInfo", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Named(MapperConstants.TO_HAZARDOUS_MATERIAL_INFO)
    HazardousMaterialInfo toHazardousMaterialInfo(HazardousMaterialInfoDTO hazardousMaterialInfoDTO);

    @Mapping(source = "registrationNumber", target = "registrationNumber")
    @Mapping(source = "giai", target = "giai")
    @Mapping(source = "registrationTransportOfficeName", target = "registrationTransportOfficeName")
    @Mapping(source = "registrationVehicleType", target = "registrationVehicleType")
    @Mapping(source = "registrationVehicleUse", target = "registrationVehicleUse")
    @Mapping(source = "registrationVehicleId", target = "registrationVehicleId")
    @Mapping(source = "chassisNumber", target = "chassisNumber")
    @Mapping(source = "vehicleId", target = "vehicleId")
    @Mapping(source = "operatorCorporateNumber", target = "operatorCorporateNumber")
    @Mapping(source = "operatorBusinessCode", target = "operatorBusinessCode")
    @Mapping(source = "ownerCorporateNumber", target = "ownerCorporateNumber")
    @Mapping(source = "ownerBusinessCode", target = "ownerBusinessCode")
    @Mapping(source = "vehicleType", target = "vehicleType")
    @Mapping(source = "hazardousMaterialVehicleType", target = "hazardousMaterialVehicleType")
    @Mapping(source = "tractor", target = "tractor")
    @Mapping(source = "trailer", target = "trailer")
    @Named(MapperConstants.TO_VEHICLE_INFO_DTO)
    VehicleInfoDTO toVehicleInfoDTO(VehicleInfo vehicleInfo);

    @Mapping(source = "maxPayload1", target = "maxPayload1")
    @Mapping(source = "maxPayload2", target = "maxPayload2")
    @Mapping(source = "vehicleWeight", target = "vehicleWeight")
    @Mapping(source = "vehicleLength", target = "vehicleLength")
    @Mapping(source = "vehicleWidth", target = "vehicleWidth")
    @Mapping(source = "vehicleHeight", target = "vehicleHeight")
    @Mapping(source = "hazardousMaterialVolume", target = "hazardousMaterialVolume")
    @Mapping(source = "hazardousMaterialSpecificGravity", target = "hazardousMaterialSpecificGravity")
    @Mapping(source = "expiryDate", target = "expiryDate", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    @Mapping(source = "deregistrationStatus", target = "deregistrationStatus")
    @Named(MapperConstants.TO_MOTAS_INFO_DTO)
    MotasInfoDTO toMotasInfoDTO(VehicleInfo vehicleInfo);

    @Mapping(source = "bedHeight", target = "bedHeight")
    @Mapping(source = "cargoHeight", target = "cargoHeight")
    @Mapping(source = "cargoWidth", target = "cargoWidth")
    @Mapping(source = "cargoLength", target = "cargoLength")
    @Mapping(source = "maxCargoCapacity", target = "maxCargoCapacity")
    @Mapping(source = "bodyType", target = "bodyType")
    @Mapping(source = "powerGate", target = "powerGate")
    @Mapping(source = "wingDoors", target = "wingDoors")
    @Mapping(source = "refrigerationUnit", target = "refrigerationUnit")
    @Mapping(source = "temperatureRangeMin", target = "temperatureRangeMin")
    @Mapping(source = "temperatureRangeMax", target = "temperatureRangeMax")
    @Mapping(source = "craneEquipped", target = "craneEquipped")
    @Mapping(source = "vehicleEquipmentNotes", target = "vehicleEquipmentNotes")
    @Mapping(source = "masterDataStartDate", target = "masterDataStartDate", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    @Mapping(source = "masterDataEndDate", target = "masterDataEndDate", dateFormat = DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
    @Named(MapperConstants.TO_VEHICLE_DETAILS_DTO)
    VehicleDetailsDTO toVehicleDetailsDTO(VehicleInfo vehicleInfo);

    @Mapping(source = "packageCode", target = "packageCode")
    @Mapping(source = "packageNameKanji", target = "packageNameKanji")
    @Mapping(source = "width", target = "width")
    @Mapping(source = "height", target = "height")
    @Mapping(source = "depth", target = "depth")
    @Mapping(source = "dimensionUnitCode", target = "dimensionUnitCode")
    @Mapping(source = "maxLoadQuantity", target = "maxLoadQuantity")
    @Named(MapperConstants.TO_MAX_CARRYING_CAPACITY_DTO)
    MaxCarryingCapacityDTO toMaxCarryingCapacityDTO(MaxCarryingCapacity maxCarryingCapacity);

    @Mapping(source = "hazardousMaterialItemCode", target = "hazardousMaterialItemCode")
    @Mapping(source = "hazardousMaterialTextInfo", target = "hazardousMaterialTextInfo")
    @Named(MapperConstants.TO_HAZARDOUS_MATERIAL_INFO_DTO)
    HazardousMaterialInfoDTO toHazardousMaterialInfoDTO(HazardousMaterialInfo hazardousMaterialInfo);
}
