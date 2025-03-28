package jp.co.nlj.ix.repository;

import jp.co.nlj.ix.domain.ShipperOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * シッパーコーポレートリポジトリインターフェース。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface ShipperOperatorRepository extends JpaRepository<ShipperOperator, Long> {

    /**
     * 指定されたIDに基づいてシッパーオペレーターを取得する。
     *
     * @param id シッパーオペレーターのID
     * @return シッパーオペレーター
     */
    ShipperOperator getShipperOperatorById(String id);

    /**
     * シッパーオペレーターの最大オペレーターコードを取得する。
     *
     * @return 最大オペレーターコード
     */
    @Query("SELECT MAX(operatorCode) FROM ShipperOperator")
    Long getLastOperatorCode();
} 