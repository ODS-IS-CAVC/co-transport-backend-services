package nlj.business.carrier.link.service;

import java.util.List;
import nlj.business.carrier.link.domain.MaxCarryingCapacity;

/**
 * <PRE>
 * 最大積載量サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface MaxCarryingCapacityService {

    /**
     * 車両情報IDから最大積載量を取得.<BR>
     *
     * @param vehicleInfoId 車両情報ID
     * @return 最大積載量
     */
    public List<MaxCarryingCapacity> getMaxCarryingCapacityByVehicleInfoId(Long vehicleInfoId);

}
