package nlj.business.transaction.repository;

import java.util.List;
import java.util.Optional;
import nlj.business.transaction.domain.TransMatching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 配送マッチングリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransMatchingRepository extends JpaRepository<TransMatching, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TransMatching WHERE vehicleAvbResourceItemId IN :vehicleItemIds AND status = :status")
    int deleteByListVehicleAvbResourceItemIdAndStatus(@Param("vehicleItemIds") List<Long> vehicleItemIds,
        @Param("status") Integer status);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TransMatching WHERE vehicleAvbResourceItemId = :vehicleItemId")
    int deleteByVehicleAvbResourceItemId(@Param("vehicleItemId") Long vehicleItemId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TransMatching WHERE vehicleAvbResourceId = :vehicleItemId AND status = :status")
    int deleteByVehicleAvbResourceIdAndStatus(@Param("vehicleItemId") Long vehicleItemId,
        @Param("status") Integer status);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TransMatching WHERE cnsLineItemByDateId IN :cnsItemByDateIds AND status = :status")
    int deleteByListCnsLineItemByDateIdAndStatus(@Param("cnsItemByDateIds") List<Long> cnsItemByDateIds,
        @Param("status") Integer status);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TransMatching WHERE cnsLineItemByDateId = :cnsItemByDateId")
    int deleteByCnsLineItemByDateId(@Param("cnsItemByDateId") Long cnsItemByDateId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TransMatching WHERE cnsLineItemByDateId = :cnsItemByDateId AND status = :status ")
    int deleteByCnsLineItemByDateIdAndStatus(@Param("cnsItemByDateId") Long cnsItemByDateId,
        @Param("status") Integer status);

    Optional<TransMatching> findFirstByVehicleAvbResourceItemIdAndCnsLineItemByDateIdAndStatus(
        Long vehicleAvbResourceItemId, Long cnsLineItemByDateId, Integer status);

    @Query(value = "SELECT trsn FROM TransMatching trsn WHERE vehicleAvbResourceId = :vehicleAvbResourceId AND vehicleAvbResourceItemId = :vehicleAvbResourceItemId AND cnsLineItemByDateId = :cnsLineItemByDateId")
    Optional<TransMatching> findFirstByMatchingKey(@Param("vehicleAvbResourceId") Long vehicleAvbResourceId,
        @Param("vehicleAvbResourceItemId") Long vehicleAvbResourceItemId,
        @Param("cnsLineItemByDateId") Long cnsLineItemByDateId);

    @Modifying
    @Transactional
    @Query("update TransMatching t set t.status = :status where t.id = :id")
    void updateStatusById(@Param("status") Integer status, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE TransMatching t SET t.status = :status, t.updatedDate = CURRENT_TIMESTAMP WHERE t.vehicleAvbResourceId = :vehicleAvbResourceId AND t.id <> :transMatchingId")
    void updateStatusByVehicleAvbResourceId(@Param("status") Integer status,
        @Param("vehicleAvbResourceId") Long vehicleAvbResourceItemId,
        @Param("transMatchingId") Long transMatchingId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE TransMatching SET status = :updateStatus, updatedDate = CURRENT_TIMESTAMP " +
        "WHERE cnsLineItemByDateId = :cnsLineItemByDateId AND id != :notEqualId")
    int updateStatusByCnsLineItemByDateIdAndIdNot(@Param("updateStatus") Integer updateStatus,
        @Param("cnsLineItemByDateId") Long cnsLineItemByDateId,
        @Param("notEqualId") Long notEqualId);

    @Modifying
    @Transactional
    @Query(value =
        "UPDATE TransMatching SET status = :updateStatus WHERE vehicleAvbResourceItemId = :vehicleAvbResourceItemId "
            + "AND id != :notEqualId ")
    int updateStatusByVehicleAvbResourceItemIdAndIdNot(@Param("updateStatus") Integer updateStatus,
        @Param("vehicleAvbResourceItemId") Long vehicleAvbResourceItemId,
        @Param("notEqualId") Long notEqualId);

    List<TransMatching> findAllByVehicleDiagramItemTrailerIdIn(List<Long> ids);

    List<TransMatching> getTransMatchingByTransTypeAndCnsLineItemByDateId(Integer transType, Long cnsLineItemByDateId);

    TransMatching getTransMatchingByCnsLineItemByDateIdAndVehicleAvbResourceItemId(Long cnsLineItemByDateId,
        Long vehicleAvbResourceItemId);
}
