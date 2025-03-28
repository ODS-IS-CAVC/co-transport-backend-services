package nlj.business.shipper.mapper;

import nlj.business.shipper.domain.CargoInfoImport;
import nlj.business.shipper.dto.cargoInfoImport.CargoInfoImportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 貨物情報輸入マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface CargoInfoImportMapper {


    @Mapping(source = "numberSuccess", target = "numberSuccess")
    @Mapping(source = "numberFailure", target = "numberFailure")
    CargoInfoImport toEntity(CargoInfoImportDTO dto);

    @Mapping(source = "numberSuccess", target = "numberSuccess")
    @Mapping(source = "numberFailure", target = "numberFailure")
    @Mapping(source = "filePath", target = "filePath")
    @Mapping(source = "status", target = "status")
    CargoInfoImportDTO toDTO(CargoInfoImport entity);


}
