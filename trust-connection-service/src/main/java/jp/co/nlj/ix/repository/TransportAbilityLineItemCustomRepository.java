package jp.co.nlj.ix.repository;

import jp.co.nlj.ix.domain.VehicleAvbResourceItem;
import jp.co.nlj.ix.dto.shipperTrspCapacity.ShipperTransportCapacitySearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送能力。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface TransportAbilityLineItemCustomRepository {

    /**
     * 運送能力を検索する。
     *
     * @param request 運送能力検索条件
     * @param page    ページ番号
     * @param limit   ページサイズ
     * @return 運送能力のページ
     */
    Page<VehicleAvbResourceItem> searchTransportPlanItem(ShipperTransportCapacitySearchDTO request, int page,
        int limit);
}
