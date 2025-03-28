package nlj.business.carrier.mapper;

import nlj.business.carrier.domain.VehicleDiagramImport;
import nlj.business.carrier.dto.vehicleDiagramImport.VehicleDiagramImportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 車両図面インポートマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface VehicleDiagramImportMapper {

    @Mapping(source = "numberSuccess", target = "numberSuccess")
    @Mapping(source = "numberFailure", target = "numberFailure")
    VehicleDiagramImport toEntity(VehicleDiagramImportDTO vehicleDiagramImportDTO);

}
