package nlj.business.carrier.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nlj.business.carrier.constant.MapperConstants;
import nlj.business.carrier.dto.CutOffInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * 切り捨て情報マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface CutOffInfoMapper {

    @Named(MapperConstants.TO_CUT_OFF_INFO)
    default List<CutOffInfoDTO> toCutOffInfo(Map<String, BigDecimal> cutOffInfo) {
        if (cutOffInfo == null) {
            return new ArrayList<>();
        }

        List<CutOffInfoDTO> result = new ArrayList<>();
        cutOffInfo.forEach((key, value) -> {
            CutOffInfoDTO dto = new CutOffInfoDTO();
            dto.setCutOffTime(key);
            dto.setCutOffPrice(value);
            result.add(dto);
        });

        return result;
    }
}
