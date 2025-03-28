package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.reserve.request.ReserveRequestDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveProposeNotifyDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveProposeResponseDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveResponseDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserverProposeReplyDTO;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 稼働通知サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface GateWayReserveService {

    /**
     * 予約を挿入する。
     *
     * @param reserveRequestDTO 予約リクエストのDTO
     * @param paramUrl          パラメータURL
     * @return 予約結果
     */
    ReserveResponseDTO insertReserve(ReserveRequestDTO reserveRequestDTO, String paramUrl);

    /**
     * 予約提案を更新する。
     *
     * @param operationId       操作ID
     * @param proposeId         提案ID
     * @param reserveRequestDTO 予約リクエストのDTO
     * @param paramUrl          パラメータURL
     * @return 更新された予約提案の結果
     */
    ReserveProposeResponseDTO updateReservePropose(String operationId, String proposeId,
        ReserveRequestDTO reserveRequestDTO, String paramUrl);

    /**
     * 予約提案通知を挿入する。
     *
     * @param operationId       操作ID
     * @param proposeId         提案ID
     * @param reserveRequestDTO 予約リクエストのDTO
     * @param paramUrl          パラメータURL
     * @return 予約提案通知の結果
     */
    ReserveProposeNotifyDTO insertReserveProposeNotify(String operationId, String proposeId,
        ReserveRequestDTO reserveRequestDTO, String paramUrl);

    /**
     * 予約提案に対する返信を挿入する。
     *
     * @param operationId       操作ID
     * @param proposeId         提案ID
     * @param reserveRequestDTO 予約リクエストのDTO
     * @param paramUrl          パラメータURL
     * @return 予約提案返信の結果
     */
    ReserverProposeReplyDTO insertReserveProposeReply(String operationId, String proposeId,
        ReserveRequestDTO reserveRequestDTO, String paramUrl);
}
