package nlj.business.transaction.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.dto.TransMarketDTO;
import nlj.business.transaction.mapper.TransMarketMapper;
import nlj.business.transaction.repository.TransMarketCustomRepository;
import nlj.business.transaction.service.TransMarketService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 取引市場サービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TransMarketServiceImpl implements TransMarketService {

    private final TransMarketCustomRepository transMarketCustomRepository;
    private final TransMarketMapper transMarketMapper;

    /**
     * 指定された月の市場輸送計画を検索する。
     *
     * @param month 月
     * @return 市場輸送計画のリスト
     */
    @Override
    public List<TransMarketDTO> search(String month) {
        return transMarketMapper.mapToTransMarketDTO(transMarketCustomRepository.search(month));
    }
}
