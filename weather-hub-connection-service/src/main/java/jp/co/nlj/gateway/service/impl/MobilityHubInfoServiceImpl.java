package jp.co.nlj.gateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.DateUtils;
import com.next.logistic.util.ObjectMapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import jp.co.nlj.gateway.constant.APIConstant;
import jp.co.nlj.gateway.dto.reservation.Attribute;
import jp.co.nlj.gateway.dto.reservation.Statuses;
import jp.co.nlj.gateway.dto.reservation.request.ReservationRequestDTO;
import jp.co.nlj.gateway.dto.reservation.request.StatusesRequestDTO;
import jp.co.nlj.gateway.dto.reservation.response.ReservationResponseDTO;
import jp.co.nlj.gateway.service.MobilityHubInfoService;
import jp.co.nlj.gateway.service.TokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * MobilityHubInfoServiceImplクラスは、モビリティハブ情報サービス実装クラスを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Slf4j
@Service
public class MobilityHubInfoServiceImpl implements MobilityHubInfoService {

    private static final ObjectMapper objectMapper = ObjectMapperUtil.getInstance();
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NljUrlProperties urlProperties;
    @Autowired
    private TokenCacheService tokenCacheService;

    @Override
    public ReservationResponseDTO reserveMobilityHub(HttpServletRequest httpServletRequest,
        ReservationRequestDTO request) {
        if (urlProperties.isMobilityHubMockData()) {
            return mockApiResponse(request);
        } else {
            try {
                for (StatusesRequestDTO status : request.getAttribute().getStatuses()) {
                    String dataBeforeEncode = objectMapper.writeValueAsString(
                        status.getRequestObject());
                    String dataAfterEncode = Base64.getEncoder()
                        .encodeToString(dataBeforeEncode.getBytes(StandardCharsets.UTF_8));
                    status.setValue(dataAfterEncode);
                    status.setValidFrom(DateUtils.formatDateTime(status.getValidFrom()));
                    status.setValidUntil(DateUtils.adjustDateIfNeeded(status.getValidFrom(), status.getValidUntil()));
                    status.setRequestObject(null);
                }
                HttpHeaders headers = setHeader();
                HttpEntity<ReservationRequestDTO> entity = new HttpEntity<>(request, headers);

                ResponseEntity<String> apiResponse = restTemplate.exchange(
                    urlProperties.getMobilityHub(), HttpMethod.PUT, entity, String.class);
                log.info("MobilityHub Response: {}", apiResponse.getBody());
                if (apiResponse.getStatusCode().is2xxSuccessful() && apiResponse.getBody() != null) {

                    ReservationResponseDTO reservationResponseDTO = objectMapper.readValue(apiResponse.getBody(),
                        ReservationResponseDTO.class);
                    return reservationResponseDTO;
                }
            } catch (Exception e) {
                log.error("MobilityHubInfoServiceImpl reserveMobilityHub ERROR: {}", e.getMessage());
                throw new NextWebException(new NextAPIError(HttpStatus.INTERNAL_SERVER_ERROR,
                    SoaResponsePool.get("soa.code.resources_err_001")));
            }

        }
        return null;
    }

    @Override
    public void cancelMobilityHubReservation(HttpServletRequest httpServletRequest, String reservationId) {
        if (!urlProperties.isMobilityHubMockData()) {
            String cancelUrl = urlProperties.getMobilityHub() + "?keyFilter=" + reservationId;

            HttpHeaders headers = setHeader();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> dmpResponse = restTemplate.exchange(cancelUrl, HttpMethod.DELETE, entity,
                String.class);

            if (dmpResponse.getStatusCode().is5xxServerError()) {
                throw new NextWebException(new NextAPIError(HttpStatus.INTERNAL_SERVER_ERROR,
                    SoaResponsePool.get("soa.code.resources_err_001")));
            }
        }
    }

    private ReservationResponseDTO mockApiResponse(ReservationRequestDTO requestDTO) {
        String dataBeforeEncode;
        try {
            dataBeforeEncode = objectMapper.writeValueAsString(
                requestDTO.getAttribute().getStatuses().get(0).getRequestObject());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String dataAfterEncode = Base64.getEncoder()
            .encodeToString(dataBeforeEncode.getBytes(StandardCharsets.UTF_8));
        requestDTO.getAttribute().getStatuses().get(0).setValue(dataAfterEncode);

        List<Statuses> statuses = new ArrayList<>();
        statuses.add(new Statuses(UUID.randomUUID().toString(), dataAfterEncode,
            "2025-04-22T02:00:00+0900", "2025-04-22T03:30:00+0900"));

        return new ReservationResponseDTO("test1", new Attribute("mobilityhub", statuses));
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String accessToken = APIConstant.BEARER;
        try {
            accessToken = accessToken + tokenCacheService.getCacheToken();
        } catch (Exception e) {
            log.error("MobilityHubInfoServiceImpl Get accessToken ERROR: {}", e.getMessage());
        }

        headers.set(APIConstant.API_KEY, urlProperties.getApiKey());
        headers.set(APIConstant.AUTHORIZATION, accessToken);
        return headers;
    }
}
