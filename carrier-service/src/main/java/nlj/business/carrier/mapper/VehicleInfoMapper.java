package nlj.business.carrier.mapper;

import nlj.business.carrier.domain.VehicleInfo;
import nlj.business.carrier.dto.vehicleInfo.VehicleInfoDTO;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 車両情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface VehicleInfoMapper {

    @Mapping(source = "registrationAreaCode", target = "registrationAreaCode")
    @Mapping(source = "registrationGroupNumber", target = "registrationGroupNumber")
    @Mapping(source = "registrationCharacter", target = "registrationCharacter")
    @Mapping(source = "registrationNumber1", target = "registrationNumber1")
    @Mapping(source = "registrationNumber2", target = "registrationNumber2")
    @Mapping(source = "vehicleCode", target = "vehicleCode")
    @Mapping(source = "vehicleName", target = "vehicleName")
    @Mapping(source = "vehicleType", target = "vehicleType")
    @Mapping(source = "vehicleSize", target = "vehicleSize")
    @Mapping(source = "temperatureRange", target = "temperatureRange")
    @Mapping(source = "maxPayload", target = "maxPayload")
    @Mapping(source = "totalLength", target = "totalLength")
    @Mapping(source = "totalWidth", target = "totalWidth")
    @Mapping(source = "totalHeight", target = "totalHeight")
    @Mapping(source = "groundClearance", target = "groundClearance")
    @Mapping(source = "doorHeight", target = "doorHeight")
    @Mapping(source = "bodySpecification", target = "bodySpecification")
    @Mapping(source = "bodyShape", target = "bodyShape")
    @Mapping(source = "bodyConstruction", target = "bodyConstruction")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "deleteFlag", target = "deleteFlag")
    VehicleInfo toEntity(VehicleInfoDTO vehicleInfoDTO);

    @Mapping(source = "registrationAreaCode", target = "registrationAreaCode")
    @Mapping(source = "registrationGroupNumber", target = "registrationGroupNumber")
    @Mapping(source = "registrationCharacter", target = "registrationCharacter")
    @Mapping(source = "registrationNumber1", target = "registrationNumber1")
    @Mapping(source = "registrationNumber2", target = "registrationNumber2")
    @Mapping(source = "vehicleCode", target = "vehicleCode")
    @Mapping(source = "vehicleName", target = "vehicleName")
    @Mapping(source = "vehicleType", target = "vehicleType")
    @Mapping(source = "vehicleSize", target = "vehicleSize")
    @Mapping(source = "temperatureRange", target = "temperatureRange")
    @Mapping(source = "maxPayload", target = "maxPayload")
    @Mapping(source = "totalLength", target = "totalLength")
    @Mapping(source = "totalWidth", target = "totalWidth")
    @Mapping(source = "totalHeight", target = "totalHeight")
    @Mapping(source = "groundClearance", target = "groundClearance")
    @Mapping(source = "doorHeight", target = "doorHeight")
    @Mapping(source = "bodySpecification", target = "bodySpecification")
    @Mapping(source = "bodyShape", target = "bodyShape")
    @Mapping(source = "bodyConstruction", target = "bodyConstruction")
    @Mapping(source = "status", target = "status")
    VehicleInfoDTO toDto(VehicleInfo vehicleInfo);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "registrationAreaCode", target = "registrationAreaCode")
    @Mapping(source = "registrationGroupNumber", target = "registrationGroupNumber")
    @Mapping(source = "registrationCharacter", target = "registrationCharacter")
    @Mapping(source = "registrationNumber1", target = "registrationNumber1")
    @Mapping(source = "registrationNumber2", target = "registrationNumber2")
    @Mapping(source = "vehicleCode", target = "vehicleCode")
    @Mapping(source = "vehicleName", target = "vehicleName")
    @Mapping(source = "vehicleType", target = "vehicleType")
    @Mapping(source = "vehicleSize", target = "vehicleSize")
    @Mapping(source = "temperatureRange", target = "temperatureRange")
    @Mapping(source = "maxPayload", target = "maxPayload")
    @Mapping(source = "totalLength", target = "totalLength")
    @Mapping(source = "totalWidth", target = "totalWidth")
    @Mapping(source = "totalHeight", target = "totalHeight")
    @Mapping(source = "groundClearance", target = "groundClearance")
    @Mapping(source = "doorHeight", target = "doorHeight")
    @Mapping(source = "bodySpecification", target = "bodySpecification")
    @Mapping(source = "bodyShape", target = "bodyShape")
    @Mapping(source = "bodyConstruction", target = "bodyConstruction")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "deleteFlag", target = "deleteFlag")
    VehicleInfoResponseDTO toResponseDto(VehicleInfo vehicleInfo);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "registrationAreaCode", target = "registrationAreaCode")
    @Mapping(source = "registrationGroupNumber", target = "registrationGroupNumber")
    @Mapping(source = "registrationCharacter", target = "registrationCharacter")
    @Mapping(source = "registrationNumber1", target = "registrationNumber1")
    @Mapping(source = "registrationNumber2", target = "registrationNumber2")
    @Mapping(source = "vehicleCode", target = "vehicleCode")
    @Mapping(source = "vehicleName", target = "vehicleName")
    @Mapping(source = "vehicleType", target = "vehicleType")
    @Mapping(source = "vehicleSize", target = "vehicleSize")
    @Mapping(source = "temperatureRange", target = "temperatureRange")
    @Mapping(source = "maxPayload", target = "maxPayload")
    @Mapping(source = "totalLength", target = "totalLength")
    @Mapping(source = "totalWidth", target = "totalWidth")
    @Mapping(source = "totalHeight", target = "totalHeight")
    @Mapping(source = "groundClearance", target = "groundClearance")
    @Mapping(source = "doorHeight", target = "doorHeight")
    @Mapping(source = "bodySpecification", target = "bodySpecification")
    @Mapping(source = "bodyShape", target = "bodyShape")
    @Mapping(source = "bodyConstruction", target = "bodyConstruction")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "status", target = "status")
    VehicleInfo toEntityFromResponseDto(VehicleInfoResponseDTO responseDTO);
}
