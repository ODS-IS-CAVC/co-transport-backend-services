package jp.co.nlj.ix.service.impl;

import com.next.logistic.data.config.CommonDataConfigProperties;
import jp.co.nlj.ix.constant.APIConstant.Reserve;
import jp.co.nlj.ix.dto.reserve.request.ReserveRequestDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveProposeNotifyDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveProposeResponseDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserveResponseDTO;
import jp.co.nlj.ix.dto.reserve.response.ReserverProposeReplyDTO;
import jp.co.nlj.ix.service.GateWayReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 予約サービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GateWayReserveServiceImpl implements GateWayReserveService {

    @Autowired
    private CommonDataConfigProperties commonDataConfigProperties;

    /**
     * 予約を挿入する。
     *
     * @param reserveRequestDTO 予約リクエストDTO
     * @param paramUrl          パラメータURL
     * @return 予約レスポンスDTO
     */
    @Override
    public ReserveResponseDTO insertReserve(ReserveRequestDTO reserveRequestDTO, String paramUrl) {
        String url = commonDataConfigProperties.getDomainIX() + Reserve.PREFIX + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ReserveRequestDTO> entity = new HttpEntity<>(reserveRequestDTO);
        try {
            log.info("Start call insert reserve call IX External: " + reserveRequestDTO);
            ResponseEntity<ReserveResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, ReserveResponseDTO.class);
            log.info("End call insert reserve success IX External: " + response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Call IX insert reserve External ERROR: " + e.getMessage());
        }
        return null;
    }

    /**
     * 予約提案を更新する。
     *
     * @param operationId       操作ID
     * @param proposeId         提案ID
     * @param reserveRequestDTO 予約リクエストDTO
     * @param paramUrl          パラメータURL
     * @return 予約提案レスポンスDTO
     */
    @Override
    public ReserveProposeResponseDTO updateReservePropose(String operationId, String proposeId,
        ReserveRequestDTO reserveRequestDTO, String paramUrl) {
        String url =
            commonDataConfigProperties.getDomainIX() + Reserve.PREFIX + "/" + operationId + Reserve.PROPOSE + proposeId
                + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ReserveRequestDTO> entity = new HttpEntity<>(reserveRequestDTO);
        try {
            log.info("Start call update reserve propose IX External: " + reserveRequestDTO);
            ResponseEntity<ReserveProposeResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, ReserveProposeResponseDTO.class);
            log.info("End call update reserve propose success IX External: " + response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Call update reserve propose IX External ERROR: " + e.getMessage());
        }
        return null;
    }

    /**
     * 予約提案通知を挿入する。
     *
     * @param operationId       操作ID
     * @param proposeId         提案ID
     * @param reserveRequestDTO 予約リクエストDTO
     * @param paramUrl          パラメータURL
     * @return 予約提案通知DTO
     */
    @Override
    public ReserveProposeNotifyDTO insertReserveProposeNotify(String operationId, String proposeId,
        ReserveRequestDTO reserveRequestDTO, String paramUrl) {
        String url =
            commonDataConfigProperties.getDomainIX() + Reserve.PREFIX + "/" + operationId + Reserve.PROPOSE + proposeId
                + Reserve.NOTIFY + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ReserveRequestDTO> entity = new HttpEntity<>(reserveRequestDTO);
        try {
            log.info("Start call insert reserve propose notify IX External: " + reserveRequestDTO);
            ResponseEntity<ReserveProposeNotifyDTO> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, ReserveProposeNotifyDTO.class);
            log.info("End call update reserve propose notify success IX External: " + response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Call update reserve propose notify IX External ERROR: " + e.getMessage());
        }
        return null;
    }

    /**
     * 予約提案返信を挿入する。
     *
     * @param operationId       操作ID
     * @param proposeId         提案ID
     * @param reserveRequestDTO 予約リクエストDTO
     * @param paramUrl          パラメータURL
     * @return 予約提案返信DTO
     */
    @Override
    public ReserverProposeReplyDTO insertReserveProposeReply(String operationId, String proposeId,
        ReserveRequestDTO reserveRequestDTO, String paramUrl) {
        String url =
            commonDataConfigProperties.getDomainIX() + Reserve.PREFIX + "/" + operationId + Reserve.PROPOSE + proposeId
                + Reserve.REPLY + paramUrl;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ReserveRequestDTO> entity = new HttpEntity<>(reserveRequestDTO);
        try {
            log.info("Start call insert reserve propose reply IX External: " + reserveRequestDTO);
            ResponseEntity<ReserverProposeReplyDTO> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, ReserverProposeReplyDTO.class);
            log.info("End call update reserve propose reply success IX External: " + response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Call update reserve propose reply IX External ERROR: " + e.getMessage());
        }
        return null;
    }
}
