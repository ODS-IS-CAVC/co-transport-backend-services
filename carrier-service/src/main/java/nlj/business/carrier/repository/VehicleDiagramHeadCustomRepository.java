package nlj.business.carrier.repository;

import java.time.LocalDate;
import java.util.List;
import nlj.business.carrier.domain.VehicleDiagramHead;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラム明細リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramHeadCustomRepository {

    /**
     * 日付で車両ダイアグラム明細を検索する。<BR>
     *
     * @param startDate 開始日付
     * @param endDate   終了日付
     * @return 車両ダイアグラム明細のリスト
     */
    List<VehicleDiagramHead> searchByDate(LocalDate startDate, LocalDate endDate);
}
