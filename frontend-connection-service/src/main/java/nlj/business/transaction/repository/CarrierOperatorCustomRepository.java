package nlj.business.transaction.repository;

import java.util.List;

/**
 * <PRE>
 * 運送業者のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CarrierOperatorCustomRepository {

    List<String> getCarrierOperatorMails(List<String> operatorIds);

    String getCarrierOperatorMail(String operatorId);
}
