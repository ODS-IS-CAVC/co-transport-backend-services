package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.VehicleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleInfoRepository extends JpaRepository<VehicleInfo, Long> {

    /**
     * ※車種（vehicleType）から車両情報を検索
     *
     * @param vehicleType 検索する車両のタイプを入力します
     * @return 一致する車両情報のリスト
     */
    List<VehicleInfo> findByVehicleTypeAndOperatorId(String vehicleType, String operatorId);

    /**
     * ※車両情報を検索
     *
     * @param giai 検索する車両のGIAIを入力します
     * @return 一致する車両情報
     */
    VehicleInfo findByGiaiAndOperatorId(String giai, String operatorId);

}
