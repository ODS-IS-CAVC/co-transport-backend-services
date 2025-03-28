package nlj.business.carrier.link.repository;

import java.time.LocalDate;
import java.util.List;
import nlj.business.carrier.link.domain.TransOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 荷届先リポジトリ
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransOrderRepository extends JpaRepository<TransOrder, Long> {

    /**
     * 輸送計画マッチングカスタムリポジトリ.<BR>
     *
     * @param trspInstructionId 輸送計画マッチングカスタムリポジトリ
     * @param transportDate     輸送計画マッチングカスタムリポジトリ
     * @return 輸送計画マッチングカスタムリポジトリ
     */
    List<TransOrder> findByTrspInstructionIdAndTransportDate(String trspInstructionId, LocalDate transportDate);

}
