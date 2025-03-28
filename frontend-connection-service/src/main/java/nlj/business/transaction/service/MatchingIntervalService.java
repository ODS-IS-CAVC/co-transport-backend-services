package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import nlj.business.transaction.dto.request.MatchingIntervalRequest;
import nlj.business.transaction.dto.response.MatchingIntervalResponse;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * マッチング間隔サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface MatchingIntervalService {

    /**
     * マッチング間隔に基づいてマッチングを行う。
     *
     * @param requestData        マッチング間隔リクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return マッチング間隔レスポンス
     */
    MatchingIntervalResponse matchingByInterval(MatchingIntervalRequest requestData,
        HttpServletRequest httpServletRequest);
}
