package nlj.business.transaction.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import nlj.business.transaction.domain.CnsLineItemByDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 運送計画の指示のリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CnsLineItemByDateRepository extends JpaRepository<CnsLineItemByDate, Long> {

    List<CnsLineItemByDate> findByTransPlanId(String transPlanId);

    List<CnsLineItemByDate> findByReqCnsLineItemId(Long reqCnsLineItemId);

    List<CnsLineItemByDate> findByCnsLineItemId(Long cnsLineItemId);

    List<CnsLineItemByDate> findByTransTypeAndTransOrderIdAndStatusIn(Integer transType, Long transOrderId,
        List<Integer> status);

    @Query(value = "SELECT * FROM cns_line_item_by_date c " +
        "WHERE c.trans_type = 0 " +
        "AND c.status IN (1, 8, 9) " +
        "AND c.operator_id = :companyId " +
        "AND (:temperatureRange IS NULL OR EXISTS ( " +
        "     SELECT 1 FROM unnest(string_to_array(replace(c.temperature_range, ' ', ''), ',')) AS temp " +
        "     WHERE temp = ANY(CAST(:temperatureRange AS text[])))) " +
        "ORDER BY COALESCE(c.updated_date, c.created_date) DESC ",
        nativeQuery = true)
    Page<CnsLineItemByDate> findByStatusInAndTransTypeAndOperatorId(
        @Param("companyId") String companyId,
        @Param("temperatureRange") String temperatureRange,
        Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE CnsLineItemByDate cns SET cns.status = :status, cns.updatedDate = CURRENT_TIMESTAMP WHERE cns.id = :id")
    int updateStatusById(@Param("id") Long id, @Param("status") Integer status);

    @Query(value = "SELECT *FROM cns_line_item_by_date c WHERE c.trans_type = 1 AND c.trans_order_id = :transOrderId ORDER BY c.id ASC LIMIT 1", nativeQuery = true)
    CnsLineItemByDate checkCarrieOnSale(@Param("transOrderId") Long transOrderId);

    @Query(value = "SELECT c FROM CnsLineItemByDate c WHERE c.transType = :transType AND c.status IN :statuses " +
        "AND (c.createdDate >= :lastInserted OR c.updatedDate >= :lastInserted) ")
    List<CnsLineItemByDate> findAllByTransTypeAndStatusAndLastCreated(@Param("transType") Integer transType,
        @Param("statuses") List<Integer> statuses, @Param("lastInserted") LocalDateTime lastInserted);

    Optional<CnsLineItemByDate> findByTransOrderId(Long transOrderId);

    @Query(value = "SELECT req_clibd.is_emergency " +
        "FROM cns_line_item_by_date clibd " +
        "LEFT JOIN t_trans_order tto ON tto.id = clibd.trans_order_id " +
        "LEFT JOIN cns_line_item_by_date req_clibd ON tto.cns_line_item_by_date_id = req_clibd.id " +
        "WHERE clibd.id = :id LIMIT 1", nativeQuery = true)
    Integer findParentOrderEmergencyStatus(@Param("id") Long id);
}
