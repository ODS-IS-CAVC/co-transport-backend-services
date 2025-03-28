package nlj.business.transaction.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.trans.MarketTransportPlanItem;
import nlj.business.transaction.dto.MarketTransportPlanItemDTO;
import nlj.business.transaction.dto.TransportPlanItemDTO;
import nlj.business.transaction.dto.request.MarketTransportPlanItemSearch;
import nlj.business.transaction.mapper.MarketTransportPlanItemMapper;
import nlj.business.transaction.mapper.TransportPlanItemMapper;
import nlj.business.transaction.repository.MarketTransportPlanItemCustomRepository;
import nlj.business.transaction.repository.TransportPlanItemRepository;
import nlj.business.transaction.service.TransportPlanItemService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 輸送計画項目サービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TransportPlanItemServiceImpl implements TransportPlanItemService {

    private final TransportPlanItemRepository transportPlanItemRepository;
    private final TransportPlanItemMapper transportPlanItemMapper;
    private final MarketTransportPlanItemMapper marketTransportPlanItemMapper;
    private final MarketTransportPlanItemCustomRepository marketTransportPlanItemCustomRepository;

    /**
     * 条件に基づいて輸送計画を検索する
     *
     * @param searchRequest 交通計画公開検索
     */
    @Override
    public Page<MarketTransportPlanItemDTO> search(MarketTransportPlanItemSearch searchRequest) {
        Page<MarketTransportPlanItem> paged = marketTransportPlanItemCustomRepository.search(searchRequest);
        return paged.map(marketTransportPlanItemMapper::mapToMarketTransportPlanItemDTO);
    }

    /**
     * 販売中の輸送計画を検索する。
     *
     * @return 販売中の輸送計画のリスト
     */
    @Override
    @Transactional
    public List<TransportPlanItemDTO> findOnSale() {
        return transportPlanItemMapper.mapToTransportPlanItemDTO(
            transportPlanItemRepository.findByStatus(DataBaseConstant.TransportPlanItem.STATUS_ON_SALE));
    }

    /**
     * 輸送計画のステータスを更新する。
     *
     * @param id 輸送計画ID
     * @return 更新された輸送計画
     */
    @Override
    @Transactional
    public MarketTransportPlanItem updateStatusOnSale(Long id) {
//        Optional<TransMatching> transMatching = transMatchingRepository.findById(id);
//
//        if(transMatching.isPresent()){
//            Optional<TransportPlanItem> transportPlanItem = transportPlanItemRepository.findById(transMatching.get().getTransportPlanItemId());
//            if(transportPlanItem.isPresent()){
//                transportPlanItem.get().setStatus(DataBaseConstant.TransportPlanItem.STATUS_ON_SALE);
//                transportPlanItemRepository.save(transportPlanItem.get());
//                TransportPlanItemSnapshot transportPlanItemSnapshot = transMatching.get().getTransportPlanItemSnapshot();
//                MarketTransportPlanItem item = marketTransportPlanItemMapper.mapToMarket(transportPlanItemSnapshot);
//                item.setStatus(DataBaseConstant.MarketTransportPlanItem.STATUS_ON_SALE);
//                    item.setId(null);
//                    marketTransportPlanItemRepository.save(item);
//                    return item;
//
//
//            }
//        }
//
//        throw new NextWebException(
//                new NextAPIError(HttpStatus.NOT_FOUND,
//                        SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_404),
//                        id)
//        );
        return null;
    }

    /**
     * 輸送計画の提案
     *
     * @return 輸送計画の提案
     */
    @Override
    public TransportPlanItemDTO getTransportPlanProposal() {
        return transportPlanItemMapper.mapToTransportPlanItemDTO(
            transportPlanItemRepository.getTransportPlanItemProposal());
    }
}
