package nlj.business.carrier.repository;

import java.time.LocalDate;
import java.util.List;
import nlj.business.carrier.domain.VehicleDiagramItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両ダイアグラム明細リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleDiagramItemCustomRepository {

    /**
     * 日付で車両ダイアグラム明細を検索する。
     *
     * @return 車両ダイアグラム明細のリスト
     */
    List<VehicleDiagramItem> findListByDate();

    /**
     * 車両ダイアグラムIDで車両ダイアグラム明細を検索する。
     *
     * @param headId       車両ダイアグラムID
     * @param operatorId   オペレータID
     * @param minStartDate 開始日付の最小値
     * @param maxStartDate 開始日付の最大値
     * @param tripName     トリップ名
     * @param statusList   ステータスのリスト
     * @param pageable     ページング情報
     * @return 車両ダイアグラム明細のページ
     */
    Page<VehicleDiagramItem> searchAllItemByDiagramId(Long headId, String operatorId, LocalDate minStartDate,
        LocalDate maxStartDate, String tripName, List<Integer> statusList, Pageable pageable);

    /**
     * ステータスで車両ダイアグラム明細を検索する。
     *
     * @param status ステータス
     * @return 車両ダイアグラム明細のリスト
     */
    List<VehicleDiagramItem> findAllVehicleDiagramItemByStatus(Integer status);
}

