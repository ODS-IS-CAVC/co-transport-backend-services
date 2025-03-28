package nlj.business.transaction.repository;

import java.time.LocalDate;
import java.util.List;
import nlj.business.transaction.domain.yamato.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <PRE>
 * 車両情報のリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {

    List<CarInfo> findCarInfosByServiceNoAndServiceStrtDateAndTractorIdcr(String vehicleDiagramId,
        LocalDate serviceStartDate, String tractorIdcr);
}