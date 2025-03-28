package nlj.business.transaction.repository;

/**
 * <PRE>
 * 場所マスタのカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface LocationMasterCustomRepository {

    String getLocationNameByCode(String locationCode);
}

