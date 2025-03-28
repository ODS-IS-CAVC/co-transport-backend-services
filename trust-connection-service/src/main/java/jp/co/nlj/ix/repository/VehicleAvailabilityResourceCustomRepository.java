package jp.co.nlj.ix.repository;

import java.time.LocalDate;
import java.util.List;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;

/**
 * <PRE>
 * 車両稼働リソースカスタムリポジトリインターフェース
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleAvailabilityResourceCustomRepository {

    /**
     * 指定された輸送運用日、運用日開始日、サービス番号、オペレーターIDに基づいて車両稼働リソースを取得します。
     *
     * @param trspOpDate            輸送運用日
     * @param trspOpDateTrmStrtDate 運用日開始日
     * @param serviceNo             サービス番号
     * @param operatorId            オペレーターID
     * @return 車両稼働リソースのリスト
     */
    List<VehicleAvailabilityResource> findByTrspOpDate(Long trspOpDate, LocalDate trspOpDateTrmStrtDate,
        String serviceNo, String operatorId);

    /**
     * 指定されたオペレーターID、サービス番号、開始日を基に輸送計画に関連する車両稼働リソースを検索します。
     *
     * @param operatorId オペレーターID
     * @param serviceNo  サービス番号
     * @param startDate  開始日
     * @return 車両稼働リソースのリスト
     */
    List<VehicleAvailabilityResource> searchByTrspPlan(String operatorId, String serviceNo, LocalDate startDate);
}
