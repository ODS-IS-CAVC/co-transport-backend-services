package nlj.business.carrier.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送注文カスタムリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransOrderCustomRepository {

    /**
     * 車両ダイアグラムアイテムIDで運送業者IDのリストを検索する。<BR>
     *
     * @param vehicleDiagramItemId 車両ダイアグラムアイテムID
     * @return 運送業者IDのリスト
     */
    List<String> findOperatorIdByVehicleDiagramItemId(Long vehicleDiagramItemId);

}
