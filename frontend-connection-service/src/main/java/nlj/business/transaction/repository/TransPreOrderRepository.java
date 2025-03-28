package nlj.business.transaction.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import nlj.business.transaction.domain.TransPreOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * トランザクション予約のリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransPreOrderRepository extends JpaRepository<TransPreOrder, Long> {

    Optional<TransPreOrder> findByTransMatchingId(Long transMatchingId);

    boolean existsByTransMatchingId(Long transMatchingId);

    @Query(value = "SELECT p FROM TransPreOrder p WHERE p.status IN :statuses")
    Page<TransPreOrder> getPaged(@Param("statuses") List<Integer> statuses, Pageable pageable);

    @Query(value = "SELECT COUNT(p.id) FROM TransPreOrder p WHERE FUNCTION('DATE', p.createdDate) = :localDateTime AND p.status IN :statuses")
    Integer countByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("statuses") List<Integer> statuses);

    @Query(value = "SELECT SUM(p.trailerNumber) FROM TransPreOrder p WHERE FUNCTION('DATE', p.createdDate) = :localDateTime AND p.status IN :statuses")
    Integer sumTrailerNumberByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("statuses") List<Integer> statuses);

    @Query(value = "SELECT MIN(p.price) FROM TransPreOrder p WHERE FUNCTION('DATE', p.createdDate) = :localDateTime AND p.status IN :statuses")
    BigDecimal minPriceByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("statuses") List<Integer> statuses);

    @Query(value = "SELECT MAX(p.price) FROM TransPreOrder p WHERE FUNCTION('DATE', p.createdDate) = :localDateTime AND p.status IN :statuses")
    BigDecimal maxPriceByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("statuses") List<Integer> statuses);

    @Query(value = "SELECT AVG(p.price) FROM TransPreOrder p WHERE FUNCTION('DATE', p.createdDate) = :localDateTime AND p.status IN :statuses")
    BigDecimal avgPriceByDateAndStatus(@Param("localDateTime") LocalDateTime localDateTime,
        @Param("statuses") List<Integer> statuses);
}