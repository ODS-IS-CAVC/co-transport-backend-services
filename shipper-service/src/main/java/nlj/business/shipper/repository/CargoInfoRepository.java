package nlj.business.shipper.repository;

import nlj.business.shipper.domain.CargoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 荷物情報リポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface CargoInfoRepository extends JpaRepository<CargoInfo, Long>, CargoInfoCustomRepository {

    CargoInfo findByIdAndOperatorId(Long id, String uuid);
}

