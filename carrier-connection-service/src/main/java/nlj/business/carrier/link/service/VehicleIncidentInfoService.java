package nlj.business.carrier.link.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;

/**
 * <PRE>
 * 車両事故情報サービスインターフェース.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleIncidentInfoService {

    /**
     * 車両事故情報を登録.<BR>
     *
     * @param httpServletRequest HTTPサーブレットリクエスト
     * @param commonRequestDTO   共通リクエストDTO
     * @throws JsonProcessingException JSON処理例外
     */
    void registerIncident(HttpServletRequest httpServletRequest, CommonRequestDTO commonRequestDTO)
        throws JsonProcessingException;

    /**
     * 車両事故情報操作を登録.<BR>
     *
     * @param httpServletRequest HTTPサーブレットリクエスト
     * @param commonRequestDTO   共通リクエストDTO
     * @throws JsonProcessingException JSON処理例外
     */
    void registerIncidentOperation(HttpServletRequest httpServletRequest, CommonRequestDTO commonRequestDTO)
        throws JsonProcessingException;
}
