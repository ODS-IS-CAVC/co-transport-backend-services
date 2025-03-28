package nlj.business.carrier.service;

import nlj.business.carrier.dto.semiDynamicInfo.request.SemiDynamicInfoRequestDTO;
import nlj.business.carrier.dto.semiDynamicInfo.request.ValidateSemiDynamicInfoRequestDTO;
import nlj.business.carrier.dto.semiDynamicInfo.response.SemiDynamicInfoResponseDTO;
import nlj.business.carrier.dto.semiDynamicInfo.response.ValidateSemiDynamicInfoResponseDTO;

/**
 * <PRE>
 * セミダイナミック情報サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface SemiDynamicInfoService {

    /**
     * 現在のセミダイナミック情報を取得する。
     *
     * @param semiDynamicInfoRequestDTO セミダイナミック情報リクエストDTO
     * @return 現在のセミダイナミック情報レスポンスDTO
     */
    SemiDynamicInfoResponseDTO getCurrentSemiDynamicInfo(SemiDynamicInfoRequestDTO semiDynamicInfoRequestDTO);

    /**
     * 開始前のセミダイナミック情報を取得する。
     *
     * @param semiDynamicInfoRequestDTO セミダイナミック情報リクエストDTO
     * @return 開始前のセミダイナミック情報レスポンスDTO
     */
    SemiDynamicInfoResponseDTO getBeforeStartSemiDynamicInfo(SemiDynamicInfoRequestDTO semiDynamicInfoRequestDTO);

    /**
     * セミダイナミック情報を検証する。
     *
     * @param validateSemiDynamicInfoRequestDTO 検証リクエストDTO
     * @return 検証結果レスポンスDTO
     */
    ValidateSemiDynamicInfoResponseDTO validateSemiDynamicInfo(
        ValidateSemiDynamicInfoRequestDTO validateSemiDynamicInfoRequestDTO);
}
