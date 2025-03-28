package nlj.business.carrier.repository;

import nlj.business.carrier.domain.CarrierOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両図面割り当てリポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface CarrierRepository extends JpaRepository<CarrierOperator, String> {

    /**
     * 車両運送業者をIDで検索する。<BR>
     *
     * @param id 車両運送業者ID
     * @return 車両運送業者
     */
    CarrierOperator findCarrierOperatorById(String id);

    /**
     * 車両運送業者コードの最大値を取得する。<BR>
     *
     * @return 車両運送業者コードの最大値
     */
    @Query("SELECT MAX(operatorCode) FROM CarrierOperator ")
    Long getLastOperatorCode();
}
