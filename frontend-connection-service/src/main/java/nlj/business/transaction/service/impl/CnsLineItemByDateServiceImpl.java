package nlj.business.transaction.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.dto.CnsLineItemByDateDTO;
import nlj.business.transaction.dto.CnsLineItemByDateResponseDTO;
import nlj.business.transaction.dto.request.CarrierListOrderSearch;
import nlj.business.transaction.dto.request.CarrierTransportPlanRequest;
import nlj.business.transaction.dto.request.CommonPagingSearch;
import nlj.business.transaction.dto.request.TransportPlanPublicSearch;
import nlj.business.transaction.dto.trspPlanLineItem.TrspPlanItemSearchRequestDTO;
import nlj.business.transaction.dto.trspPlanLineItem.TrspPlanLineItemDTO;
import nlj.business.transaction.dto.trspPlanLineItem.TrspSrvcDTO;
import nlj.business.transaction.dto.trspPlanLineItem.response.OperationRequestSearchResponse;
import nlj.business.transaction.dto.trspPlanLineItem.response.TrspPlanLineItemSearchResponse;
import nlj.business.transaction.repository.CnsLineItemByDateCustomRepository;
import nlj.business.transaction.repository.CnsLineItemByDateRepository;
import nlj.business.transaction.service.CnsLineItemByDateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <PRE>
 * 運送計画明細項目サービス実装クラス<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CnsLineItemByDateServiceImpl implements CnsLineItemByDateService {

    private final CnsLineItemByDateCustomRepository cnsLineItemByDateCustomRepository;
    private final RestTemplate restTemplate;
    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;

    @Value("${next.url.ix-service-carrier-operations-plans}")
    private String CARRIER_OPERATIONS_PLANS_ENDPOINT;

    @Value("${next.url.cologi-ix-service-operation-request}")
    private String CARRIER_OPERATION_REQUEST;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 運送計画のページ付きリストを取得する。
     *
     * @param searchRequest 検索リクエスト
     * @return ページ付き運送計画リスト
     */
    @Override
    public Page<CnsLineItemByDateResponseDTO> getPagedTransportPlan(TransportPlanPublicSearch searchRequest) {
        return cnsLineItemByDateCustomRepository.getPagedTransportPlan(searchRequest);
    }

    /**
     * 公開運送計画のページ付きリストを取得する。
     *
     * @param request            リクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return ページ付き運送計画DTOリスト
     */
    @Override
    public Page<CnsLineItemByDateDTO> getPagedTransportPlanPublic(CarrierTransportPlanRequest request,
        HttpServletRequest httpServletRequest) {
        User user = userContext.getUser();

        TrspPlanItemSearchRequestDTO trspPlanItemSearchRequestDTO = request.toTrspPlanLineItemRequestDTO();
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        TrspPlanLineItemSearchResponse jsonResponse = new TrspPlanLineItemSearchResponse();

        String url;
        Boolean isNotIX = request.getIsNotIX();
        isNotIX = isNotIX || user.isDebug();

        try {
            if (isNotIX) {
                ResponseEntity<TrspPlanLineItemSearchResponse> response;
                url = UriComponentsBuilder.fromHttpUrl(CARRIER_OPERATIONS_PLANS_ENDPOINT)
                    .queryParam(ParamConstant.TRSP_STRT_TXT, request.getDepId())
                    .queryParam(ParamConstant.TRSP_END_TXT, request.getArrId())
                    .queryParam(ParamConstant.MAX_TRSP_STRT_DATE,
                        trspPlanItemSearchRequestDTO.getMaxTrspOpDateTrmStrtDate())
                    .queryParam(ParamConstant.MIN_TRSP_STRT_DATE,
                        trspPlanItemSearchRequestDTO.getMinTrspOpDateTrmStrtDate())
                    .queryParam(ParamConstant.MAX_TRSP_STRT_TIME,
                        trspPlanItemSearchRequestDTO.getMaxTrspOpPlanDateTrmStrtTime())
                    .queryParam(ParamConstant.MIN_TRSP_STRT_TIME,
                        trspPlanItemSearchRequestDTO.getMinTrspOpPlanDateTrmStrtTime())
                    .queryParam(ParamConstant.ADVANCED_CONDITIONS,
                        new ObjectMapper().writeValueAsString(trspPlanItemSearchRequestDTO.getAdvancedSearch()))
                    .build()
                    .encode()
                    .toString();
                response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    TrspPlanLineItemSearchResponse.class);
                jsonResponse = response.getBody();

            } else {
                ResponseEntity<OperationRequestSearchResponse> response;
                url = UriComponentsBuilder.fromHttpUrl(CARRIER_OPERATION_REQUEST)
                    .queryParam(ParamConstant.TRSP_STRT_TXT, request.getDepId())
                    .queryParam(ParamConstant.TRSP_END_TXT, request.getArrId())
                    .queryParam(ParamConstant.MAX_TRSP_STRT_DATE,
                        trspPlanItemSearchRequestDTO.getMaxTrspOpDateTrmStrtDate())
                    .queryParam(ParamConstant.MIN_TRSP_STRT_DATE,
                        trspPlanItemSearchRequestDTO.getMinTrspOpDateTrmStrtDate())
                    .queryParam(ParamConstant.MAX_TRSP_STRT_TIME,
                        trspPlanItemSearchRequestDTO.getMaxTrspOpPlanDateTrmStrtTime())
                    .queryParam(ParamConstant.MIN_TRSP_STRT_TIME,
                        trspPlanItemSearchRequestDTO.getMinTrspOpPlanDateTrmStrtTime())
                    .queryParam(ParamConstant.ADVANCED_CONDITIONS,
                        new ObjectMapper().writeValueAsString(trspPlanItemSearchRequestDTO.getAdvancedSearch()))
                    .build()
                    .encode()
                    .toString();

                log.info("DEBUG: IX API-030 url: " + url);
                response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    OperationRequestSearchResponse.class);
                log.info("DEBUG: IX API-030 Successfully called IX 030, response: {}", response);
                jsonResponse.setTrspPlanLineItem(
                    response.getBody().getOperationRequestResponses().getTrspPlanLineItems());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Map<String, String>> serviceDetails = new ArrayList<>();

        if (jsonResponse.getTrspPlanLineItem() != null) {
            if (jsonResponse.getTrspPlanLineItem().size() > 0) {
                List<TrspPlanLineItemDTO> trspPlanLineItems = jsonResponse.getTrspPlanLineItem();

                for (TrspPlanLineItemDTO item : trspPlanLineItems) {
                    TrspSrvcDTO trspSrvc = item.getTrspSrvc();
                    if (trspSrvc != null) {
                        String serviceNoValue = trspSrvc.getServiceNo();
                        String serviceStrtDateValue = trspSrvc.getServiceStrtDate();

                        if (serviceNoValue != null && serviceStrtDateValue != null) {
                            // Create a map to store the serviceNo and serviceStrtDate as key-value pairs
                            Map<String, String> serviceMap = new HashMap<>();
                            serviceMap.put("serviceNo", serviceNoValue);
                            serviceMap.put("serviceStrtDate", serviceStrtDateValue);

                            // Add the map to the list
                            serviceDetails.add(serviceMap);
                        }
                    }
                }

                return cnsLineItemByDateCustomRepository.getPagedTransportPlanPublic(serviceDetails, request.getPage(),
                    request.getLimit(), request);
            } else {
                return Page.empty();
            }
        } else {
            return Page.empty();
        }

    }

    /**
     * 温度範囲に基づいて運送計画のページ付きリストを取得する。
     *
     * @param temperatureRange 温度範囲
     * @param request          ページングリクエスト
     * @return ページ付き運送計画リスト
     */
    @Override
    public Page<CnsLineItemByDate> getPagedTransportPlanSale(List<Integer> temperatureRange,
        CommonPagingSearch request) {
        String companyId = userContext.getUser().getCompanyId();
        String tempRangeString = null;
        Pageable pageable = PageRequest.of(Integer.parseInt(request.getPage()) - 1,
            Integer.parseInt(request.getLimit()));
        if (temperatureRange != null) {
            tempRangeString =
                "{" + temperatureRange.stream().map(String::valueOf).collect(Collectors.joining(",")) + "}";
        }
        return cnsLineItemByDateRepository.findByStatusInAndTransTypeAndOperatorId(companyId, tempRangeString,
            pageable);
    }

    /**
     * 販売中のキャリアオーダーのページ付きリストを取得する。
     *
     * @param searchRequest 検索リクエスト
     * @return ページ付きキャリアオーダーDTOリスト
     */
    @Override
    public Page<CnsLineItemByDateDTO> getPagedListCarrierOrderOnSale(CarrierListOrderSearch searchRequest) {
        return cnsLineItemByDateCustomRepository.getPagedListCarrierOrderOnSale(searchRequest);
    }

    /**
     * IDに基づいて運送計画明細を取得する。
     *
     * @param id 運送計画明細のID
     * @return 運送計画明細
     */
    @Override
    public CnsLineItemByDate findById(Long id) {
        Optional<CnsLineItemByDate> cnsLineItemByDateOption = cnsLineItemByDateRepository.findById(id);
        if (cnsLineItemByDateOption.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        } else {
            return cnsLineItemByDateOption.get();
        }
    }
}
