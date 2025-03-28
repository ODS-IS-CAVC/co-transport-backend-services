package nlj.business.carrier.service;

import java.util.List;
import nlj.business.carrier.dto.areaLocation.response.AreaLocationResponseDTO;

/**
 * <PRE>
 * 車両図面割り当てサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface AreaService {

    /**
     * すべてのエリア位置を取得する。
     *
     * @return エリア位置リスト
     */
    List<AreaLocationResponseDTO> getAllAreaLocation();
}
