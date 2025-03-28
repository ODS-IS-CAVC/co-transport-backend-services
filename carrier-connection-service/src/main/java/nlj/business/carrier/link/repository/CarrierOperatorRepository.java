package nlj.business.carrier.link.repository;

import nlj.business.carrier.link.domain.CarrierOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 運送業者オペレーターリポジトリ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface CarrierOperatorRepository extends JpaRepository<CarrierOperator, String> {

    /**
     * 運送業者オペレーターIDで検索.<BR>
     *
     * @param carrierOperatorId 運送業者オペレーターID
     * @return 運送業者オペレーター
     */
    @Query("SELECT c FROM CarrierOperator c WHERE c.id = :carrierOperatorId")
    CarrierOperator findByCarrierOperatorId(@Param("carrierOperatorId") String carrierOperatorId);

    /**
     * 運送業者オペレーターコードで検索.<BR>
     *
     * @param operatorCode 運送業者オペレーターコード
     * @return 運送業者オペレーター
     */
    @Query("SELECT co FROM CarrierOperator co WHERE co.operatorCode = :operatorCode ORDER BY co.id ASC LIMIT 1")
    CarrierOperator findFirstByOperatorCodeOrderByIdAsc(@Param("operatorCode") String operatorCode);
}
