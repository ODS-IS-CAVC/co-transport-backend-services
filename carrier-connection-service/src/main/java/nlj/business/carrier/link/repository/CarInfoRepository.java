package nlj.business.carrier.link.repository;

import java.time.LocalDate;
import nlj.business.carrier.link.domain.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車の情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {

    /**
     * サービス番号 (serviceNo) で最初の車両情報を取得します
     *
     * @param serviceNo 検索するサービス番号を入力します
     * @return 車の情報
     */
    CarInfo findFirstByServiceNo(String serviceNo);

    /**
     * 指定されたサービス番号とサービス開始日で車両情報を検索します。
     *
     * @param serviceNo       サービス番号
     * @param serviceStrtDate サービス開始日
     * @return 一致する車両情報、存在しない場合はnull
     */
    CarInfo findByServiceNoAndServiceStrtDate(String serviceNo, LocalDate serviceStrtDate);

    /**
     * 指定されたサービス番号、サービス開始日、およびオペレーターIDで車両情報を検索します。
     *
     * @param serviceNo       サービス番号
     * @param serviceStrtDate サービス開始日
     * @param operatorId      オペレーターID
     * @return 一致する車両情報、存在しない場合はnull
     */
    CarInfo findFirstByServiceNoAndServiceStrtDateAndOperatorIdAndGiai(String serviceNo, LocalDate serviceStrtDate,
        String operatorId, String giai);
}
