package nlj.business.shipper.mapper;

import nlj.business.shipper.domain.CargoInfo;
import nlj.business.shipper.dto.cargoInfo.CargoInfoDTO;
import nlj.business.shipper.dto.cargoInfo.CargoInfoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 貨物情報マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface CargoInfoMapper {


    @Mapping(source = "cargoName", target = "cargoName")
    @Mapping(source = "outerPackageCode", target = "outerPackageCode")
    @Mapping(source = "totalLength", target = "totalLength")
    @Mapping(source = "totalWidth", target = "totalWidth")
    @Mapping(source = "totalHeight", target = "totalHeight")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "tempRange", target = "tempRange")
    @Mapping(source = "specialInstructions", target = "specialInstructions")
    @Mapping(source = "importId", target = "importId")
    @Mapping(source = "status", target = "status")
    CargoInfo toEntity(CargoInfoDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cargoName", target = "cargoName")
    @Mapping(source = "outerPackageCode", target = "outerPackageCode")
    @Mapping(source = "totalLength", target = "totalLength")
    @Mapping(source = "totalWidth", target = "totalWidth")
    @Mapping(source = "totalHeight", target = "totalHeight")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "tempRange", target = "tempRange")
    @Mapping(source = "specialInstructions", target = "specialInstructions")
    @Mapping(source = "importId", target = "importId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdDate", target = "createdDate", qualifiedByName = "localDateTimeToLocalDate")
    CargoInfoResponseDTO toDTO(CargoInfo entity);


}
