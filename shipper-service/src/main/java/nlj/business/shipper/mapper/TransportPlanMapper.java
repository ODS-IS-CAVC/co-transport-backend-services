package nlj.business.shipper.mapper;

import nlj.business.shipper.constant.MapperConstants;
import nlj.business.shipper.domain.TransportPlan;
import nlj.business.shipper.domain.TransportPlanImport;
import nlj.business.shipper.domain.TransportPlanItem;
import nlj.business.shipper.dto.TransportPlanDTO;
import nlj.business.shipper.dto.TransportPlanImportDTO;
import nlj.business.shipper.dto.TransportPlanItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * <PRE>
 * 輸送計画マッパーインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface TransportPlanMapper {


    @Mapping(source = "collectionDateFrom", target = "collectionDateFrom", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "collectionDateTo", target = "collectionDateTo", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "collectionTimeFrom", target = "collectionTimeFrom", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "collectionTimeTo", target = "collectionTimeTo", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Named(MapperConstants.TO_TRANSPORT_PLAN_ENTITY)
    TransportPlan toTransportPlanEntity(TransportPlanDTO dto);

    @Mapping(source = "collectionDateFrom", target = "collectionDateFrom", qualifiedByName = MapperConstants.LOCAL_DATE_TO_STRING)
    @Mapping(source = "collectionDateTo", target = "collectionDateTo", qualifiedByName = MapperConstants.LOCAL_DATE_TO_STRING)
    @Mapping(source = "collectionTimeFrom", target = "collectionTimeFrom", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "collectionTimeTo", target = "collectionTimeTo", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Named(MapperConstants.TO_TRANSPORT_PLAN_DTO)
    TransportPlanDTO toTransportPlanDTO(TransportPlan entity);

    @Mapping(source = "collectionDate", target = "collectionDate", qualifiedByName = MapperConstants.LOCAL_DATE_TO_STRING)
    @Mapping(source = "collectionTimeFrom", target = "collectionTimeFrom", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Mapping(source = "collectionTimeTo", target = "collectionTimeTo", qualifiedByName = MapperConstants.TIME_TO_STRING_HH_MM)
    @Named(MapperConstants.TO_TRANSPORT_PLAN_ITEM_DTO)
    TransportPlanItemDTO toTransportPlanItemDTO(TransportPlanItem transportPlanItem);

    @Mapping(source = "collectionDate", target = "collectionDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "collectionTimeFrom", target = "collectionTimeFrom", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "collectionTimeTo", target = "collectionTimeTo", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Named(MapperConstants.TO_TRANSPORT_PLAN_ITEM_ENTITY)
    TransportPlanItem toTransportPlanItemEntity(TransportPlanItemDTO transportPlanItemDTO);

    @Named(MapperConstants.TO_TRANSPORT_PLAN_IMPORT_ENTITY)
    TransportPlanImport toTransportPlanImportEntity(TransportPlanImportDTO transportPlanImportDTO);

}
