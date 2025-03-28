package jp.co.nlj.ix.repository;

import java.time.LocalDate;
import jp.co.nlj.ix.domain.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <PRE>
 * 車両情報リポジトリ
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {

    /**
     * 指定されたサービス番号、サービス開始日、およびオペレーターIDで車両情報を検索します。
     *
     * @param serviceNo       サービス番号
     * @param serviceStrtDate サービス開始日
     * @param operatorId      オペレーターID
     * @return 一致する車両情報、存在しない場合はnull
     */
    CarInfo findByServiceNoAndServiceStrtDateAndOperatorId(String serviceNo, LocalDate serviceStrtDate,
        String operatorId);
}

