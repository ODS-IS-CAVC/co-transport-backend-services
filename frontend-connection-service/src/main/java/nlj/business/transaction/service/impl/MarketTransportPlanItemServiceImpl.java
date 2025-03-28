package nlj.business.transaction.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.dto.MarketTransportPlanItemDTO;
import nlj.business.transaction.mapper.MarketTransportPlanItemMapper;
import nlj.business.transaction.repository.MarketTransportPlanItemRepository;
import nlj.business.transaction.service.MarketTransportPlanItemService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 市場輸送計画アイテムサービスインプル。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class MarketTransportPlanItemServiceImpl implements MarketTransportPlanItemService {

    private final MarketTransportPlanItemRepository marketTransportPlanItemRepository;
    private final MarketTransportPlanItemMapper marketTransportPlanItemMapper;

    /**
     * 販売中の市場輸送計画アイテムを取得する。
     *
     * @return 市場輸送計画アイテムのリスト
     */
    @Override
    public List<MarketTransportPlanItemDTO> findOnSale() {
        return marketTransportPlanItemMapper.mapToMarkeTransportPlanItemDTO(
            marketTransportPlanItemRepository.findByStatus(DataBaseConstant.MarketTransportPlanItem.STATUS_ON_SALE));
    }
}
