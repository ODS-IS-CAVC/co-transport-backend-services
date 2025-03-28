package nlj.business.transaction.repository;

/**
 * <PRE>
 * 車両利用可能リソースのカスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleAvbResourceCustomRepository {

    Long getCarInfoId(Long vehicleAvbResourceId);
}
