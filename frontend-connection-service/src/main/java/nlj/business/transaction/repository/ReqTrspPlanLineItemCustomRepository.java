package nlj.business.transaction.repository;

import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 要求運送計画品目のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ReqTrspPlanLineItemCustomRepository {

    String findInstructionIdByReqCnsLineItemId(Long reqCnsLineItemId);
}
