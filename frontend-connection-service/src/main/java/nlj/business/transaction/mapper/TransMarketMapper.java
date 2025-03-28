package nlj.business.transaction.mapper;


import java.util.List;
import nlj.business.transaction.domain.trans.TransMarket;
import nlj.business.transaction.dto.TransMarketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 取引市場マッピング。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface TransMarketMapper {

    List<TransMarketDTO> mapToTransMarketDTO(List<TransMarket> transMarkets);

    @Mapping(target = "id", ignore = true)
    TransMarket mapToTransMarket(TransMarketDTO transMarketDTO);
}
