package jp.co.nlj.ix.repository;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両予約リソース明細カスタムリポジトリインターフェース
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleAvailabilityResourceItemCustomRepository {

    /**
     * 指定された車両予約リソースIDに基づいて、車両予約リソース明細を取得します。
     *
     * @param vehicleAvbResourceId 車両予約リソースID
     * @return 車両予約リソース明細のリスト
     */
    List<VehicleAvbResourceItem> findByVehicleAvbResourceId(Long vehicleAvbResourceId);

    /**
     * 指定された条件に基づいて、車両予約リソース明細を取得します。
     *
     * @param vehicleAvbResourceId 車両予約リソースID
     * @param departureTimeMin     出発時間の最小値
     * @param departureTimeMax     出発時間の最大値
     * @param price                価格
     * @param statuses             ステータスのリスト
     * @return 条件に一致する車両予約リソース明細のリスト
     */
    List<VehicleAvbResourceItem> findByVehicleAvbResourceIdAndDepartureTimeAndPriceAndStatus(Long vehicleAvbResourceId,
        LocalTime departureTimeMin, LocalTime departureTimeMax, BigDecimal price, List<Integer> statuses);
}
