package nlj.business.carrier.link.repository.impl;

import com.next.logistic.authorization.UserContext;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.domain.CarrierVehicleInfo;
import nlj.business.carrier.link.repository.VehicleInfoCarrierCustomRepository;
import org.springframework.stereotype.Repository;

/**
 * 車両情報カスタムリポジトリ実装.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleInfoCarrierCustomRepositoryImpl implements VehicleInfoCarrierCustomRepository {

    private final EntityManager entityManager;
    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * トラックID取得.<BR>
     *
     * @param vehicleId 車両ID
     * @return トラックID
     */
    @Override
    public String getTruckId(Long vehicleId) {
        CarrierVehicleInfo vehicleInfo = entityManager.find(CarrierVehicleInfo.class, vehicleId);
        if (vehicleInfo == null) {
            return null;
        } else {
            return vehicleInfo.getRegistrationAreaCode() + " " + vehicleInfo.getRegistrationGroupNumber() + " "
                + vehicleInfo.getRegistrationCharacter() + " " + vehicleInfo.getRegistrationNumber1();
        }
    }


}
