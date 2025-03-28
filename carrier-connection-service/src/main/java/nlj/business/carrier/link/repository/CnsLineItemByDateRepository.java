package nlj.business.carrier.link.repository;

import java.util.List;
import nlj.business.carrier.link.domain.CnsLineItemByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * コンティネンツ行項目リポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CnsLineItemByDateRepository extends JpaRepository<CnsLineItemByDate, Long> {

    /**
     * 輸送計画IDで検索.<BR>
     *
     * @param transPlanId 輸送計画ID
     * @return コンティネンツ行項目
     */
    List<CnsLineItemByDate> findByTransPlanId(String transPlanId);

    /**
     * 輸送計画タイプで検索.<BR>
     *
     * @return コンティネンツ行項目
     */
    @Query(value = "SELECT * FROM cns_line_item_by_date WHERE trans_type = 1", nativeQuery = true)
    List<CnsLineItemByDate> findByTransType();

    /**
     * 要求コンティネンツ行項目IDで検索.<BR>
     *
     * @param reqCnsLineItemId 要求コンティネンツ行項目ID
     * @return コンティネンツ行項目
     */
    List<CnsLineItemByDate> findByReqCnsLineItemId(Long reqCnsLineItemId);

    /**
     * コンティネンツ行項目IDで検索.<BR>
     *
     * @param cnsLineItemId コンティネンツ行項目ID
     * @return コンティネンツ行項目
     */
    List<CnsLineItemByDate> findByCnsLineItemId(Long cnsLineItemId);

    /**
     * 輸送計画タイプ、輸送計画ID、ステータスで検索.<BR>
     *
     * @param transType    輸送計画タイプ
     * @param transOrderId 輸送計画ID
     * @param status       ステータス
     * @return コンティネンツ行項目
     */
    List<CnsLineItemByDate> findByTransTypeAndTransOrderIdAndStatusIn(Integer transType, Long transOrderId,
        List<Integer> status);
}
