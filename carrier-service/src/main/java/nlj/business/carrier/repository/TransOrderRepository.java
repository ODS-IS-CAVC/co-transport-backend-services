package nlj.business.carrier.repository;

import java.util.List;
import nlj.business.carrier.domain.TransOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送注文リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransOrderRepository extends JpaRepository<TransOrder, Long> {

    /**
     * 車両ダイアグラムアイテムトレーラIDのリストで輸送注文を検索する。<BR>
     *
     * @param ids 車両ダイアグラムアイテムトレーラIDのリスト
     * @return 輸送注文のリスト
     */
    List<TransOrder> findAllByVehicleDiagramItemTrailerIdIn(List<Long> ids);

    /**
     * 車両ダイアグラムアイテムIDで輸送注文を検索する。<BR>
     *
     * @param id 車両ダイアグラムアイテムID
     * @return 輸送注文
     */
    List<TransOrder> findAllByVehicleDiagramItemId(Long id);
}
