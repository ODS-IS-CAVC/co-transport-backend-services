package jp.co.nlj.ttmi.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jp.co.nlj.ttmi.dto.commonBody.request.CommonRequestDTO;
import jp.co.nlj.ttmi.dto.response.ResponseAPI;
import jp.co.nlj.ttmi.dto.trackBySip.request.TrackBySipReqDTO;

/**
 * <PRE>
 * トラックバイシップリクエストサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TrackBySipReqService {

    ResponseAPI updateTrackByShipReq(HttpServletRequest httpServletRequest, @Valid TrackBySipReqDTO trackBySipReqDTO);

    ResponseAPI updateTrackByShipDataChannel(HttpServletRequest httpServletRequest, CommonRequestDTO trackBySipReqDTO);
}

