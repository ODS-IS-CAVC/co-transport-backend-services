package jp.co.nlj.ix.repository;

import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両予約リソース明細カスタムリポジトリインターフェース<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleAvailabilityResourceItemRepository extends JpaRepository<VehicleAvbResourceItem, Long> {

    /**
     * 指定されたオペレーターコードに基づいて最初の車両予約リソース明細を取得します。
     *
     * @param operatorCode オペレーターコード
     * @return 最初の車両予約リソース明細
     */
    VehicleAvbResourceItem findFirstByOperatorCode(String operatorCode);
}
