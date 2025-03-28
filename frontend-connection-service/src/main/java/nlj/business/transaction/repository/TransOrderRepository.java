package nlj.business.transaction.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import nlj.business.transaction.domain.trans.TransOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * トランザクション注文のリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransOrderRepository extends JpaRepository<TransOrder, Long>, TransOrderCustomRepository {

    @Query(value = "SELECT COUNT(p.id) FROM TransOrder p WHERE FUNCTION('DATE', p.createdDate) = :localDateTime AND p.status IN :listStatus")
    Integer countByDate(@Param("localDateTime") LocalDateTime localDateTime, @Param("listStatus") List<Integer> status);

    @Query("SELECT SUM(t.trailerNumber) FROM TransOrder t WHERE FUNCTION('DATE', t.createdDate) = :localDateTime AND t.status IN :listStatus")
    Integer sumTrailerNumberByDate(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("listStatus") List<Integer> status);

    // MIN(price)
    @Query("SELECT MIN(t.price) FROM TransOrder t WHERE FUNCTION('DATE', t.createdDate) = :localDateTime AND t.status IN :listStatus")
    BigDecimal minPriceByDate(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("listStatus") List<Integer> status);

    // MAX(price)
    @Query("SELECT MAX(t.price) FROM TransOrder t WHERE FUNCTION('DATE', t.createdDate) = :localDateTime  AND t.status IN :listStatus")
    BigDecimal maxPriceByDate(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("listStatus") List<Integer> status);

    // MEDIAN (price)
    @Query(value = "SELECT PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY t.price) FROM TransOrder t WHERE FUNCTION('DATE', t.createdDate) = :localDateTime  AND t.status IN :listStatus")
    BigDecimal medianPrice(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("listStatus") List<Integer> status);

    @Query(value = "SELECT t FROM TransOrder t WHERE t.status IN :statuses")
    Page<TransOrder> getPagedByStatus(@Param("statuses") List<Integer> statuses, Pageable pageable);

    @Query(value = "SELECT COUNT(p.id) FROM TransOrder p WHERE FUNCTION('DATE', p.createdDate) = :localDateTime AND p.status IN :status")
    Integer countByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("status") List<Integer> status);

    @Query("SELECT SUM(t.trailerNumber) FROM TransOrder t WHERE FUNCTION('DATE', t.createdDate) = :localDateTime AND t.status IN :status")
    Integer sumTrailerNumberByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("status") List<Integer> status);

    // MIN(price)
    @Query("SELECT MIN(t.price) FROM TransOrder t WHERE FUNCTION('DATE', t.createdDate) = :localDateTime AND t.status IN :status")
    BigDecimal minPriceByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("status") List<Integer> status);

    // MAX(price)
    @Query("SELECT MAX(t.price) FROM TransOrder t WHERE FUNCTION('DATE', t.createdDate) = :localDateTime AND t.status IN :status")
    BigDecimal maxPriceByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("status") List<Integer> status);

    // AVG(price)
    @Query("SELECT AVG(t.price) FROM TransOrder t WHERE FUNCTION('DATE', t.createdDate) = :localDateTime AND t.status IN :status")
    BigDecimal avgPriceByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("status") List<Integer> status);

    @Query(value =
        "SELECT (SELECT COUNT(c.id) FROM car_info c WHERE DATE(c.service_strt_date) = :localDateTime AND c.tractor_idcr <> '1') - "
            +
            "(SELECT COUNT(t.id) FROM t_trans_order t WHERE t.status IN :listStatus AND DATE(t.created_date) = :localDateTime) AS total_count",
        nativeQuery = true)
    Integer countTotalAvailableTrailerNumber(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("listStatus") List<Integer> status);

    @Query(value = "SELECT t FROM TransOrder t WHERE t.status IN :statuses AND transType = :transType")
    Page<TransOrder> getPagedByStatusAndTransType(@Param("statuses") List<Integer> statuses,
        @Param("transType") Integer transType, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE TransOrder p SET p.status = :status, p.updatedDate = CURRENT_TIMESTAMP WHERE p.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") Integer status);

    List<TransOrder> findAllByVehicleDiagramItemTrailerIdIn(List<Long> ids);

    List<TransOrder> findByVehicleDiagramItemIdAndStatusIn(Long vehicleDiagramItemId, List<String> statuses);

    @Query(value = "SELECT t.id FROM t_trans_order t " +
        "LEFT JOIN cns_line_item_by_date c ON t.id = c.trans_order_id " +
        "WHERE t.is_emergency = :isEmergency " +
        "AND c.trans_order_id IS null ", nativeQuery = true)
    List<Long> findIdByEmergencyOnSale(@Param("isEmergency") Integer isEmergency);

    List<TransOrder> findByVehicleDiagramItemIdAndIdIsNot(Long vehicleDiagramItemId, Long id);

    List<TransOrder> findDistinctByCnsLineItemByDateId(Long cnsLineItemByDateId);

    List<TransOrder> findDistinctByVehicleAvbResourceItemId(Long vehicleAvbResourceItemId);

    TransOrder findFirstByParentOrderId(Long parentOrderId);

    Long countAllByTransTypeAndVehicleDiagramItemId(Integer transType, Long vehicleDiagramItemId);

    List<TransOrder> findAllByVehicleDiagramItemId(Long vehicleDiagramItemId);
}
