package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.HazardousMaterialInfo;
import nlj.business.carrier.link.domain.VehicleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 危険物情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface HazardousMaterialInfoRepository extends JpaRepository<HazardousMaterialInfo, Long> {

    /**
     * 車両ID（vehicleInfoId）から危険物情報を検索
     *
     * @param vehicleInfoId 検索する車両IDを入力します
     * @return 一致する危険物情報のリスト
     */
    List<HazardousMaterialInfo> findByVehicleInfoId(Long vehicleInfoId);

    /**
     * 車両情報に基づいて危険物情報を削除
     *
     * @param vehicleInfo 削除対象の車両情報
     */
    void deleteAllByVehicleInfo(VehicleInfo vehicleInfo);
}
