package nlj.business.carrier.link.repository;

import nlj.business.carrier.link.domain.ShipperOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * シッパーコーポレートリポジトリインターフェース.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipperOperatorRepository extends JpaRepository<ShipperOperator, String> {

    /**
     * シッパーコーポレートIDで検索.<BR>
     *
     * @param id シッパーコーポレートID
     * @return シッパーコーポレート
     */
    ShipperOperator getShipperOperatorById(String id);

    /**
     * シッパーコーポレートコードの最大値を取得.<BR>
     *
     * @return シッパーコーポレートコードの最大値
     */
    @Query("SELECT MAX(operatorCode) FROM ShipperOperator")
    Long getLastOperatorCode();

    /**
     * シッパーコーポレートコードで検索.<BR>
     *
     * @param operatorCode シッパーコーポレートコード
     * @return シッパーコーポレート
     */
    @Query("SELECT co FROM ShipperOperator co WHERE co.operatorCode = :operatorCode ORDER BY co.id ASC LIMIT 1")
    ShipperOperator findFirstByOperatorCodeOrderByIdAsc(@Param("operatorCode") String operatorCode);

    /**
     * シッパーコーポレートIDで検索.<BR>
     *
     * @param shipperOperatorId シッパーコーポレートID
     * @return シッパーコーポレート
     */
    @Query("SELECT s FROM ShipperOperator s WHERE s.id = :shipperOperatorId")
    ShipperOperator findByShipperOperatorId(@Param("shipperOperatorId") String shipperOperatorId);
} 