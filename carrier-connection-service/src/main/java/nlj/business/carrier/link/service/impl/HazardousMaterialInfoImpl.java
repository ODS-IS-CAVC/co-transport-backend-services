package nlj.business.carrier.link.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.domain.HazardousMaterialInfo;
import nlj.business.carrier.link.repository.HazardousMaterialInfoRepository;
import nlj.business.carrier.link.service.HazardousMaterialInfoService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 危険物情報サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class HazardousMaterialInfoImpl implements HazardousMaterialInfoService {

    private final HazardousMaterialInfoRepository hazardousMaterialInfoRepository;

    /**
     * 車両情報IDから危険物情報を取得.<BR>
     *
     * @param vehicleInfoId 車両情報ID
     * @return 危険物情報
     */
    @Override
    public List<HazardousMaterialInfo> getHazardousMaterialInfoByVehicleInfoId(Long vehicleInfoId) {
        return hazardousMaterialInfoRepository.findByVehicleInfoId(vehicleInfoId);
    }
}
