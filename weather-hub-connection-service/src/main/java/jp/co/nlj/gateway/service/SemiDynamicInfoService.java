package jp.co.nlj.gateway.service;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.nlj.gateway.dto.semiDynamicInfo.request.SemiDynamicInfoRequestDTO;
import jp.co.nlj.gateway.dto.semiDynamicInfo.response.SemiDynamicInfoResponseDTO;

/**
 * <PRE>
 * SemiDynamicInfoServiceインターフェースは、セミダイナミック情報サービスを定義するためのインターフェースです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface SemiDynamicInfoService {

    /**
     * セミダイナミック情報を取得するメソッド。
     *
     * @param semiDynamicInfoRequestDTO セミダイナミック情報リクエストDTO
     * @param httpServletRequest        HTTPリクエスト
     * @return セミダイナミック情報レスポンスDTO
     */
    SemiDynamicInfoResponseDTO getSemiDynamicInfo(SemiDynamicInfoRequestDTO semiDynamicInfoRequestDTO,
        HttpServletRequest httpServletRequest);
}
