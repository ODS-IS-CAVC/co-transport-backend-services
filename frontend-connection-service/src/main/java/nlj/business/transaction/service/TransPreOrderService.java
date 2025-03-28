package nlj.business.transaction.service;

import nlj.business.transaction.dto.PageResponseDTO;
import nlj.business.transaction.dto.TransPreOrderDTO;
import nlj.business.transaction.dto.request.TransMatchingRequest;


/**
 * <PRE>
 * 配送マッチング。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransPreOrderService {

    /**
     * トランスプリオーダーを挿入する。
     *
     * @param transMatchingRequest トランスマッチングリクエスト
     * @param status               ステータス
     * @return 挿入されたトランスプリオーダーのID
     */
    Long inserTransPreOrder(TransMatchingRequest transMatchingRequest, Integer status);

    /**
     * シッパーのページネーションされたトランスプリオーダーを取得する。
     *
     * @param pageNo    ページ番号
     * @param pageLimit ページのリミット
     * @return ページネーションされたトランスプリオーダーのレスポンス
     */
    PageResponseDTO<TransPreOrderDTO> getPagedShipper(int pageNo, int pageLimit);

    /**
     * トランスマッチングIDによるトランスプリオーダーを挿入する。
     *
     * @param transMatchingId トランスマッチングID
     * @return 挿入されたトランスプリオーダーのID
     */
    Long insertTransPreOrder(Long transMatchingId);

    /**
     * キャリアのページネーションされたトランスプリオーダーを取得する。
     *
     * @param pageNo    ページ番号
     * @param pageLimit ページのリミット
     * @return ページネーションされたトランスプリオーダーのレスポンス
     */
    PageResponseDTO<TransPreOrderDTO> getPagedCarrier(int pageNo, int pageLimit);
}