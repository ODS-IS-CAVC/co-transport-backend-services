package nlj.business.shipper.repository;

import java.util.List;
import nlj.business.shipper.domain.CargoInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <PRE>
 * 荷物情報カスタムリポジトリインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CargoInfoCustomRepository {

    Page<CargoInfo> search(List<Integer> status, Pageable pageable);
}
