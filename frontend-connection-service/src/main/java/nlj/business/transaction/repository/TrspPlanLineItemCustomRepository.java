package nlj.business.transaction.repository;

import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送計画品目のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TrspPlanLineItemCustomRepository {

    String findInstructionIdByCnsLineItemId(Long cnsLineItemId);
}
