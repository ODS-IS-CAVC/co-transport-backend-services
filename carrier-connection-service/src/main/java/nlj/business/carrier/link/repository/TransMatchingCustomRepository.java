package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.CnsLineItemByDate;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;

/**
 * <PRE>
 * 輸送計画マッチングカスタムリポジトリインターフェース.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransMatchingCustomRepository {

    /**
     * @param carInfo
     */
    List<CarInfo> findCarInfoByGiai(CarInfo carInfo);

    /**
     * 荷主を検索.<BR>
     *
     * @param vehicleAvbResourceItem 車両情報
     * @param transType              輸送タイプ
     * @param statuses               ステータス
     * @return 荷主
     */
    List<CnsLineItemByDate> shippers(VehicleAvbResourceItem vehicleAvbResourceItem, Integer transType,
        List<Integer> statuses);

    /**
     * 運送業者を検索.<BR>
     *
     * @param cnsLineItemByDate 輸送計画明細
     * @param transType         輸送タイプ
     * @param statuses          ステータス
     * @return 運送業者
     */
    List<VehicleAvbResourceItem> carriers(CnsLineItemByDate cnsLineItemByDate, Integer transType,
        List<Integer> statuses);

    /**
     * パッケージマッチングを検索.<BR>
     *
     * @param vehicleAvbResourceItem 車両情報
     * @param transType              輸送タイプ
     * @param statuses               ステータス
     * @return パッケージマッチング
     */
    List<CnsLineItemByDate> matchingCarrierPackage(VehicleAvbResourceItem vehicleAvbResourceItem, Integer transType,
        List<Integer> statuses);

    /**
     * トレーラーマッチングを検索.<BR>
     *
     * @param cnsLineItemByDate 輸送計画明細
     * @param transType         輸送タイプ
     * @param statuses          ステータス
     * @return トレーラーマッチング
     */
    List<VehicleAvbResourceItem> matchingCarrierTrailer(CnsLineItemByDate cnsLineItemByDate, Integer transType,
        List<Integer> statuses);
}
