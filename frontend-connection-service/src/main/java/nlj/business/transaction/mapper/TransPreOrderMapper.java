package nlj.business.transaction.mapper;

import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.TransPreOrder;
import nlj.business.transaction.dto.TransPreOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 運送業者取引予約マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface TransPreOrderMapper {

    TransPreOrder map(TransMatching transMatching);

    @Mapping(target = "transMatchingDTO", source = "transMatching")
    TransPreOrderDTO toDTO(TransPreOrder transPreOrder);

    @Mapping(source = "transMatching", target = "transMatching")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    TransPreOrder match(TransMatching transMatching);
}