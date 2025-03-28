package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.VehicleNoAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 利用可能な車両リポジトリがありません。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleNoAvailableRepository extends JpaRepository<VehicleNoAvailable, Long> {

    /**
     * 車両情報IDのリストで車両不可用情報を検索する。
     *
     * @param ids 車両情報IDのリスト
     * @return 車両不可用情報のリスト
     */
    List<VehicleNoAvailable> findAllByVehicleInfoIdIn(List<Long> ids);

    /**
     * 車両情報IDで車両不可用情報を削除する。
     *
     * @param id 車両情報ID
     */
    void deleteAllByVehicleInfoId(Long id);
}
