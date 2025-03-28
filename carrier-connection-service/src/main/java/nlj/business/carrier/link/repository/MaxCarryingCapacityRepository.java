package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.MaxCarryingCapacity;
import nlj.business.carrier.link.domain.VehicleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 最大積載量情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface MaxCarryingCapacityRepository extends JpaRepository<MaxCarryingCapacity, Long> {

    /**
     * 車両ID（vehicleInfoId）から最大積載量情報を検索
     *
     * @param vehicleInfoId 検索する車両IDを入力します
     * @return 一致する最大積載量情報のリスト
     */
    List<MaxCarryingCapacity> findByVehicleInfoId(Long vehicleInfoId);

    /**
     * 車両情報に基づいて最大積載量情報を削除
     *
     * @param vehicleInfo 削除対象の車両情報
     */
    void deleteAllByVehicleInfo(VehicleInfo vehicleInfo);
}
