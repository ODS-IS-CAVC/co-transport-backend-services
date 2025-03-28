package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import nlj.business.transaction.dto.transferStatus.request.TransferStatusRequest;
import nlj.business.transaction.dto.transferStatus.response.TransferStatusResponse;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 転送ステータスサービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface TransferStatusService {

    /**
     * 転送ステータスを更新する。
     *
     * @param request            転送ステータスリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 転送ステータスレスポンス
     */
    TransferStatusResponse updateStatus(TransferStatusRequest request, HttpServletRequest httpServletRequest);

}
