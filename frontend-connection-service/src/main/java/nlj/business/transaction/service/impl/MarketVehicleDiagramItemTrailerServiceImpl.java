package nlj.business.transaction.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.dto.MarketVehicleDiagramItemTrailerDTO;
import nlj.business.transaction.mapper.MarketVehicleDiagramItemTrailerMapper;
import nlj.business.transaction.repository.MarketVehicleDiagramItemTrailerRepository;
import nlj.business.transaction.service.MarketVehicleDiagramItemTrailerService;
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
public class MarketVehicleDiagramItemTrailerServiceImpl implements MarketVehicleDiagramItemTrailerService {

    private final MarketVehicleDiagramItemTrailerRepository marketVehicleDiagramItemTrailerRepository;
    private final MarketVehicleDiagramItemTrailerMapper marketVehicleDiagramItemTrailerMapper;

    /**
     * 販売中の市場輸送計画アイテムを取得する。
     *
     * @return 市場輸送計画アイテムのリスト
     */
    @Override
    public List<MarketVehicleDiagramItemTrailerDTO> findOnSale() {
        return marketVehicleDiagramItemTrailerMapper.map(marketVehicleDiagramItemTrailerRepository.findByStatus(
            DataBaseConstant.MarketVehicleDiagramItemTrailer.STATUS_ON_SALE));
    }
}
