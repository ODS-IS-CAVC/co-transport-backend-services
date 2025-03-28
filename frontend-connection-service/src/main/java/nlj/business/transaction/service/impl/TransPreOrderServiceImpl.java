package nlj.business.transaction.service.impl;


import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.TransPreOrder;
import nlj.business.transaction.dto.PageResponseDTO;
import nlj.business.transaction.dto.TransPreOrderDTO;
import nlj.business.transaction.dto.request.TransMatchingRequest;
import nlj.business.transaction.mapper.TransPreOrderMapper;
import nlj.business.transaction.repository.TransMatchingRepository;
import nlj.business.transaction.repository.TransPreOrderRepository;
import nlj.business.transaction.service.TransPreOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


/**
 * <PRE>
 * 配送マッチングを実行するクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TransPreOrderServiceImpl implements TransPreOrderService {

    private final TransMatchingRepository transMatchingRepository;
    private final TransPreOrderMapper transPreOrderMapper;
    private final TransPreOrderRepository transPreOrderRepository;

    /**
     * ID で一致するトランスポートを取得し、トランスポートの事前順序に新しいレコードを挿入し、ステータスを 11 に設定します。
     *
     * @param transMatchingRequest トランスマッチングリクエスト
     * @param status               新しく挿入されたレコードのステータス
     */
    @Override
    public Long inserTransPreOrder(TransMatchingRequest transMatchingRequest, Integer status) {
        Long transMatchingId = Long.parseLong(transMatchingRequest.getId());
        Optional<TransMatching> transMatchingDB = transMatchingRepository.findById(transMatchingId);
        if (transMatchingDB.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_404),
                    transMatchingId));
        }
        Optional<TransPreOrder> transPreOrderDB = transPreOrderRepository.findByTransMatchingId(transMatchingId);
        if (transPreOrderDB.isPresent()) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_PRE_ORDER_EXIST), transMatchingId));
        }
        TransMatching transMatching = transMatchingDB.get();
        TransPreOrder transPreOrder = transPreOrderMapper.map(transMatching);
        transPreOrder.setTransMatching(transMatching);
        transPreOrder.setStatus(status);
        return transPreOrderRepository.save(transPreOrder).getId();
    }

    /**
     * 輸送予約レコードのステータスがリクエストまたは承認されているページを取得します。
     *
     * @param pageNo    ページ番号、1から始まる
     * @param pageLimit ページ上のレコード数
     */
    @Override
    public PageResponseDTO<TransPreOrderDTO> getPagedShipper(int pageNo, int pageLimit) {
        List<Integer> listSearchStatus = List.of(DataBaseConstant.TransPreOrderStatus.SHIPPER_REQUEST,
            DataBaseConstant.TransPreOrderStatus.SHIPPER_APPROVED);
        return getPagedTransPreOrder(listSearchStatus, pageNo, pageLimit);
    }

    /**
     * ID で一致するトランスポートを取得し、トランスポートの事前順序に新しいレコードを挿入し、ステータスを 21 に設定します。
     *
     * @param transMatchingId ユーザーID（trans_matching_id）
     * @return 結果として新しく作成されたTransPreOrderのIDを返す
     */
    @Override
    public Long insertTransPreOrder(Long transMatchingId) {

        // Check if transMatchingId exists in the table TransPreOrder
        boolean transMatchingExists = transPreOrderRepository.existsByTransMatchingId(transMatchingId);
        if (transMatchingExists) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_PRE_ORDER_EXIST), transMatchingId));
        }
        // Check if transMatchingId exists in the TransMatching table
        TransMatching transMatching = transMatchingRepository.findById(transMatchingId).orElseThrow(
            () -> new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_404),
                    transMatchingId)));
        // Map data and save DB
        TransPreOrder transPreOrder = transPreOrderMapper.match(transMatching);
        transPreOrder.setStatus(DataBaseConstant.TransPreOrderStatus.CARRIER_PROPOSAL);
        TransPreOrder savedTransPreOrder = transPreOrderRepository.save(transPreOrder);
        return savedTransPreOrder.getId();
    }

    /**
     * キャリア提案またはキャリア承認ステータスの輸送予約レコードを取得するページ。
     *
     * @param pageNo    ページ番号、1から始まる
     * @param pageLimit ページ上のレコード数
     */
    @Override
    public PageResponseDTO<TransPreOrderDTO> getPagedCarrier(int pageNo, int pageLimit) {
        List<Integer> listSearchStatus = List.of(DataBaseConstant.TransPreOrderStatus.CARRIER_PROPOSAL,
            DataBaseConstant.TransPreOrderStatus.CARRIER_APPROVED);
        return getPagedTransPreOrder(listSearchStatus, pageNo, pageLimit);
    }

    /**
     * 指定されたステータスの輸送予約レコードをページングして取得します。
     *
     * @param listSearchStatus 検索するステータスのリスト
     * @param pageNo           ページ番号、1から始まる
     * @param pageLimit        ページ上のレコード数
     * @return ページングされた輸送予約レコードのDTO
     */
    private PageResponseDTO<TransPreOrderDTO> getPagedTransPreOrder(List<Integer> listSearchStatus, int pageNo,
        int pageLimit) {
        Sort sort = Sort.by(Sort.Order.by("createdDate")).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageLimit, sort);
        Page<TransPreOrder> transPreOrderPage = transPreOrderRepository.getPaged(listSearchStatus, pageable);
        Page<TransPreOrderDTO> transPreOrderDTOPage = transPreOrderPage.map(
            transPreOrder -> transPreOrderMapper.toDTO(transPreOrder));
        return new PageResponseDTO<>(transPreOrderDTOPage);
    }
}