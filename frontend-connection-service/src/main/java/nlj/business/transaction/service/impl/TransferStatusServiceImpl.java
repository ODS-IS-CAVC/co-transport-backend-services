package nlj.business.transaction.service.impl;

import static com.next.logistic.security.constant.AuthConstant.API_KEY;
import static com.next.logistic.security.constant.AuthConstant.AUTHORIZATION;
import static nlj.business.transaction.constant.TransportPlanItemMappingStatus.getMappedTransportStatusValue;
import static nlj.business.transaction.constant.VehicleDiagramItemTrailerMappingStatus.getMappedTrailerStatusValue;

import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.security.config.NljAuthProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.dto.transferStatus.request.ItemTrailerUpdateStatusRequestDTO;
import nlj.business.transaction.dto.transferStatus.request.PlanItemUpdateStatusRequestDTO;
import nlj.business.transaction.dto.transferStatus.request.TransferStatusRequest;
import nlj.business.transaction.dto.transferStatus.response.ItemTrailerUpdateStatusResponse;
import nlj.business.transaction.dto.transferStatus.response.PlanItemUpdateStatusResponse;
import nlj.business.transaction.dto.transferStatus.response.TransferStatusResponse;
import nlj.business.transaction.service.TransferStatusService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 転送ステータスサービスインプル。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TransferStatusServiceImpl implements TransferStatusService {

    private final NljUrlProperties nljUrlProperties;
    private final NljAuthProperties authProperties;

    /**
     * ステータスを更新する。
     *
     * @param request            ステータス更新リクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 更新結果
     */
    @Override
    public TransferStatusResponse updateStatus(TransferStatusRequest request, HttpServletRequest httpServletRequest) {
        TransferStatusResponse response = new TransferStatusResponse();

        String urlCarrier = nljUrlProperties.getDomainCarrier() + "/api/v1/item-trailer/status";
        String urlShipper = nljUrlProperties.getDomainShipper() + "/api/v1/transport-plan-item/status";
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        RestTemplate restTemplate = new RestTemplate();
        // Build item trailer request
        ItemTrailerUpdateStatusRequestDTO itemTrailerRequest = new ItemTrailerUpdateStatusRequestDTO();
        itemTrailerRequest.setId(request.getVehicleDiagramItemTrailerId());
        itemTrailerRequest.setStatus(getMappedTrailerStatusValue(request.getVehicleDiagramItemTrailerStatus()));
        // Build transport plan item request
        PlanItemUpdateStatusRequestDTO planItemRequest = new PlanItemUpdateStatusRequestDTO();
        planItemRequest.setId(request.getTransportPlanItemId());
        planItemRequest.setStatus(getMappedTransportStatusValue(request.getTransportPlanItemStatus()));

        HttpHeaders headers = setHeader(token);
        HttpEntity<ItemTrailerUpdateStatusRequestDTO> trailerRequest = new HttpEntity<>(itemTrailerRequest, headers);
        HttpEntity<PlanItemUpdateStatusRequestDTO> planItemUpdateRequest = new HttpEntity<>(planItemRequest, headers);
        ResponseEntity<ItemTrailerUpdateStatusResponse> carrierResponse = restTemplate.exchange(urlCarrier,
            HttpMethod.PUT, trailerRequest,
            ItemTrailerUpdateStatusResponse.class);
        ResponseEntity<PlanItemUpdateStatusResponse> shipperResponse = restTemplate.exchange(urlShipper, HttpMethod.PUT,
            planItemUpdateRequest,
            PlanItemUpdateStatusResponse.class);

        if (carrierResponse.getBody() != null && carrierResponse.getBody().getStatus() != null) {
            response.setTrailerUpdateStatus(carrierResponse.getBody().getStatus());
        }
        if (shipperResponse.getBody() != null && shipperResponse.getBody().getStatus() != null) {
            response.setPlanItemUpdateStatus(shipperResponse.getBody().getStatus());
        }

        return response;
    }

    /**
     * HTTPヘッダーを設定する。
     *
     * @param token 認証トークン
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY, authProperties.getApiKey());
        headers.set(AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
