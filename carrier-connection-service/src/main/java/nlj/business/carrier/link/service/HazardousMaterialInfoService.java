package nlj.business.carrier.link.service;

import java.util.List;
import nlj.business.carrier.link.domain.HazardousMaterialInfo;

/**
 * <PRE>
 * 危険物情報サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface HazardousMaterialInfoService {

    /**
     * 車両情報IDから危険物情報を取得.<BR>
     *
     * @param vehicleInfoId 車両情報ID
     * @return 危険物情報
     */
    public List<HazardousMaterialInfo> getHazardousMaterialInfoByVehicleInfoId(Long vehicleInfoId);

}
