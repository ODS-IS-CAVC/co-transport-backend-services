package jp.co.nlj.gateway.service;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.gateway.dto.trackBySip.request.TrackBySipRequestDTO;
import jp.co.nlj.gateway.dto.trackBySip.response.RestTemplateApiResponse;

/**
 * <PRE>
 * TrackBySipServiceインターフェースは、トラックバイSIPサービスを定義するためのインターフェースです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TrackBySipService {

    /**
     * トラックバイSIPを作成するメソッド。
     *
     * @param httpServletRequest   HTTPリクエスト
     * @param trackBySipRequestDTO トラックバイSIPリクエストDTO
     * @return RESTテンプレートAPIレスポンス
     */
    RestTemplateApiResponse createTrackBySIP(HttpServletRequest httpServletRequest,
        TrackBySipRequestDTO trackBySipRequestDTO);

    /**
     * トラックバイSIPを更新するメソッド。
     *
     * @param httpServletRequest   HTTPリクエスト
     * @param trackBySipRequestDTO トラックバイSIPリクエストDTO
     * @return RESTテンプレートAPIレスポンス
     */
    RestTemplateApiResponse updateTrackBySIP(HttpServletRequest httpServletRequest,
        TrackBySipRequestDTO trackBySipRequestDTO);
}
