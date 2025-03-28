package nlj.business.transaction.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.trans.TransMarket;
import nlj.business.transaction.dto.TransMarketDTO;
import nlj.business.transaction.mapper.TransMarketMapper;
import nlj.business.transaction.repository.TransMarketRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.service.BatchService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * バッチ処理を実行するサービスクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final TransMarketMapper transMarketMapper;
    private final TransOrderRepository transOrderRepository;
    private final TransMarketRepository transMarketRepository;

    /**
     * 指定された日付の市場価格の統計を計算します。。
     *
     * @return 計算された市場価格統計のリスト（TransMarket)
     */
    @Override
    public TransMarket calculateMarketPriceStatistics() {
        List<Integer> listStatus = Arrays.asList(
            DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT,
            DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT,
            160, 161, 170);
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
        System.out.println("calculateMarketPriceStatistics: " + localDateTime);
        TransMarketDTO transMarketDTO = new TransMarketDTO();

        transMarketDTO.setDay(String.valueOf(localDateTime.getDayOfMonth()));

        String formattedMonth = localDateTime.getYear() + String.format("%02d", localDateTime.getMonthValue());
        transMarketDTO.setMonth(formattedMonth);

        transMarketDTO.setTotalTransNumber(transOrderRepository.countByDate(localDateTime, listStatus));
        transMarketDTO.setTotalTrailerNumber(transOrderRepository.sumTrailerNumberByDate(localDateTime, listStatus));
        transMarketDTO.setTotalAvailableTrailerNumber(
            transOrderRepository.countTotalAvailableTrailerNumber(localDateTime, listStatus));
        transMarketDTO.setLowPrice(transOrderRepository.minPriceByDate(localDateTime, listStatus));
        transMarketDTO.setHighPrice(transOrderRepository.maxPriceByDate(localDateTime, listStatus));
        transMarketDTO.setMedianPrice(transOrderRepository.medianPrice(localDateTime, listStatus));

        List<Integer> listCarrierStatus = List.of(
            DataBaseConstant.TransOrderStatus.CARRIER_REQUEST,
            DataBaseConstant.TransOrderStatus.CARRIER_RE_REQUEST
        );
        this.setProposalData(transMarketDTO, localDateTime, listCarrierStatus);

        List<Integer> listShipperStatus = List.of(
            DataBaseConstant.TransOrderStatus.SHIPPER_REQUEST,
            DataBaseConstant.TransOrderStatus.SHIPPER_RE_REQUEST
        );
        this.setRequestData(transMarketDTO, localDateTime, listShipperStatus);

        TransMarket transMarketResult = transMarketRepository.save(transMarketMapper.mapToTransMarket(transMarketDTO));
        return transMarketResult;
    }

    /**
     * 提案データを設定します。
     *
     * @param dto      提案データを格納するDTO
     * @param dateTime 日付
     * @param statuses ステータスのリスト
     */
    private void setProposalData(TransMarketDTO dto, LocalDateTime dateTime, List<Integer> statuses) {
        dto.setTotalProposalNumber(transOrderRepository.countByDateAndStatus(dateTime, statuses));
        dto.setTotalTrailerProposalNumber(transOrderRepository.sumTrailerNumberByDateAndStatus(dateTime, statuses));
        dto.setLowProposalPrice(transOrderRepository.minPriceByDateAndStatus(dateTime, statuses));
        dto.setHighProposalPrice(transOrderRepository.maxPriceByDateAndStatus(dateTime, statuses));
        dto.setAverageProposalPrice(transOrderRepository.avgPriceByDateAndStatus(dateTime, statuses));
    }

    /**
     * リクエストデータを設定します。
     *
     * @param dto      リクエストデータを格納するDTO
     * @param dateTime 日付
     * @param statuses ステータスのリスト
     */
    private void setRequestData(TransMarketDTO dto, LocalDateTime dateTime, List<Integer> statuses) {
        dto.setTotalRequestNumber(transOrderRepository.countByDateAndStatus(dateTime, statuses));
        dto.setTotalTrailerRequestNumber(transOrderRepository.sumTrailerNumberByDateAndStatus(dateTime, statuses));
        dto.setLowRequestPrice(transOrderRepository.minPriceByDateAndStatus(dateTime, statuses));
        dto.setHighRequestPrice(transOrderRepository.maxPriceByDateAndStatus(dateTime, statuses));
        dto.setAverageRequestPrice(transOrderRepository.avgPriceByDateAndStatus(dateTime, statuses));
    }
}
