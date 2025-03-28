package jp.co.nlj.ix.repository;

import java.util.Optional;
import jp.co.nlj.ix.domain.carrier.CarrierOperator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <PRE>
 * 車両情報リポジトリ
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CarrierOperatorRepository extends JpaRepository<CarrierOperator, Long> {

    /**
     * 指定されたIDに基づいてキャリアオペレーターを検索します。
     *
     * @param id キャリアオペレーターのID
     * @return オプショナルなキャリアオペレーター
     */
    Optional<CarrierOperator> findById(String id);

    /**
     * 指定されたオペレーターコードに基づいてキャリアオペレーターを検索します。
     *
     * @param operatorCode キャリアオペレーターのコード
     * @return キャリアオペレーター
     */
    CarrierOperator findByOperatorCode(String operatorCode);
}
