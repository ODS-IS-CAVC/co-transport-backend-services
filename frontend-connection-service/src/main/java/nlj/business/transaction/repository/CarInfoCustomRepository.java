package nlj.business.transaction.repository;

/**
 * <PRE>
 * 車両情報のカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CarInfoCustomRepository {

    String getCarPlateNumber(Long carInfoId);
}

