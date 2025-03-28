package nlj.business.transaction.repository;

import java.util.List;

/**
 * <PRE>
 * 運送業者のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface ShipperOperatorCustomRepository {

    List<String> getShipperOperatorMails(List<String> operatorIds);

    String getShipperOperatorMail(String operatorId);
}
