package nlj.business.carrier.link.repository;

/**
 * <PRE>
 * 車両情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */

public interface VehicleInfoCarrierCustomRepository {

    /**
     * 車両IDからトラックIDを取得.<BR>
     *
     * @param vehicleId 車両ID
     * @return トラックID
     */
    String getTruckId(Long vehicleId);
}
