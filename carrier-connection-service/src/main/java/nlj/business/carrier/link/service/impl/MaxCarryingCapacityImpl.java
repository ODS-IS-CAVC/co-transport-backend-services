package nlj.business.carrier.link.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.domain.MaxCarryingCapacity;
import nlj.business.carrier.link.repository.MaxCarryingCapacityRepository;
import nlj.business.carrier.link.service.MaxCarryingCapacityService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 最大積載量情報サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class MaxCarryingCapacityImpl implements MaxCarryingCapacityService {

    private final MaxCarryingCapacityRepository maxCarryingCapacityRepository;

    /**
     * 車両情報IDから最大積載量情報を取得.<BR>
     *
     * @param vehicleInfoId 車両情報ID
     * @return 最大積載量情報
     */
    @Override
    public List<MaxCarryingCapacity> getMaxCarryingCapacityByVehicleInfoId(Long vehicleInfoId) {
        return maxCarryingCapacityRepository.findByVehicleInfoId(vehicleInfoId);
    }
}
