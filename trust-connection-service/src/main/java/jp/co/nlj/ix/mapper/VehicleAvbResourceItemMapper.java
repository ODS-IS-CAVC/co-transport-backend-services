package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import jp.co.nlj.ix.dto.VehicleAvbResourceItemSnapshot;
import org.mapstruct.Mapper;

/**
 * <PRE>
 * 車両可用性リソースアイテムマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface VehicleAvbResourceItemMapper {

    VehicleAvbResourceItemSnapshot mapToSnapshot(VehicleAvbResourceItem item);
}
