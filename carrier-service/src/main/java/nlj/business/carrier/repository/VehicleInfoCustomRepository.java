package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.VehicleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <PRE>
 * 車両情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

public interface VehicleInfoCustomRepository {

    /**
     * 車両情報を検索する。
     *
     * @param temperatureRange 温度範囲のリスト
     * @param vehicleType      車両タイプのリスト
     * @param pageable         ページング情報
     * @return 車両情報のページ
     */
    Page<VehicleInfo> search(List<Integer> temperatureRange, List<Integer> vehicleType, Pageable pageable);

    /**
     * 車両IDからトラックIDを取得する。
     *
     * @param vehicleId 車両ID
     * @return トラックID
     */
    String getTruckId(Long vehicleId);
}
