package nlj.business.transaction.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.dto.ProposeAbilityDTO;
import nlj.business.transaction.dto.ix.IXShipperOperations;
import nlj.business.transaction.dto.ix.response.IXShipperOperationsResponse;
import nlj.business.transaction.dto.request.TransportAbilityProposalRequest;
import nlj.business.transaction.dto.request.TransportAbilityPublicResponseDTO;
import nlj.business.transaction.dto.request.TransportAbilityPublicSearch;
import nlj.business.transaction.dto.response.TransportAbilityPublicIXResponseDTO;
import nlj.business.transaction.dto.shipperTrspCapacity.request.ShipperTransportCapacitySearchDTO;
import nlj.business.transaction.dto.shipperTrspCapacity.response.AttributeDTO;
import nlj.business.transaction.dto.shipperTrspCapacity.response.CarInfoDTO;
import nlj.business.transaction.dto.shipperTrspCapacity.response.TransportAbilityLineItemDTO;
import nlj.business.transaction.mapper.VehicleAvbResourceItemMapper;
import nlj.business.transaction.repository.CnsLineItemByDateCustomRepository;
import nlj.business.transaction.repository.VehicleAvbResourceItemCustomRepository;
import nlj.business.transaction.repository.VehicleAvbResourceItemRepository;
import nlj.business.transaction.service.VehicleAvbResourceItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * 車両アビリティリソースアイテムサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleAvbResourceItemServiceImpl implements VehicleAvbResourceItemService {

    private final VehicleAvbResourceItemCustomRepository vehicleAvbResourceItemCustomRepository;
    private final CnsLineItemByDateCustomRepository cnsLineItemByDateCustomRepository;
    private final RestTemplate restTemplate;
    @Resource(name = "userContext")
    private final UserContext userContext;
    private final VehicleAvbResourceItemMapper vehicleAvbResourceItemMapper;
    private final VehicleAvbResourceItemRepository vehicleAvbResourceItemRepository;

    @Value("${next.url.ix-service-shipper-operations-plans}")
    private String SHIPPER_OPERATIONS_PLANS_ENDPOINT;

    @Value("${next.url.cologi-ix-service-shipper-operations-plans}")
    private String IX_SHIPPER_OPERATIONS_PLANS_ENDPOINT;
    private static final Logger logger = LoggerFactory.getLogger(VehicleAvbResourceItemServiceImpl.class);

    /**
     * 輸送能力をページングして取得します。
     *
     * @param searchRequest 検索リクエスト
     * @return ページングされた輸送能力のDTO
     */
    @Override
    public Page<TransportAbilityPublicResponseDTO> getPagedTransportAbility(
        TransportAbilityPublicSearch searchRequest) {
        return vehicleAvbResourceItemCustomRepository.getPagedTransportAbility(searchRequest);
    }

    /**
     * IXサービスの輸送能力をページングして取得します。
     *
     * @param searchRequest      検索リクエスト
     * @param httpServletRequest HTTPサーブレットリクエスト
     * @return ページングされた輸送能力のDTO
     */
    @Override
    public Page<TransportAbilityPublicIXResponseDTO> getPagedTransportAbilityIX(
        TransportAbilityPublicSearch searchRequest, HttpServletRequest httpServletRequest) {
        User user = userContext.getUser();
        ShipperTransportCapacitySearchDTO shipperTransportCapacitySearchDTO = searchRequest.toShipperTransportCapacitySearchDTO();
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<ShipperTransportCapacitySearchDTO> requestEntity = new HttpEntity<>(
            shipperTransportCapacitySearchDTO, headers);

        String url = null;
        String apiEndPoint = IX_SHIPPER_OPERATIONS_PLANS_ENDPOINT;
        Class ixResponseClass = IXShipperOperationsResponse.class;
        Boolean isNotIX = searchRequest.getIsNotIX() != null && searchRequest.getIsNotIX();
        isNotIX = isNotIX || user.isDebug();
        if (isNotIX) {
            apiEndPoint = SHIPPER_OPERATIONS_PLANS_ENDPOINT;
            ixResponseClass = AttributeDTO.class;
        }
        UriComponentsBuilder builder;
        try {
            builder = UriComponentsBuilder.fromHttpUrl(apiEndPoint)
                .queryParam(ParamConstant.TRSP_STRT_TXT,
                    shipperTransportCapacitySearchDTO.getTrspOpStrtAreaLineOneTxt())
                .queryParam(ParamConstant.TRSP_END_TXT, shipperTransportCapacitySearchDTO.getTrspOpEndAreaLineOneTxt())
                .queryParam(ParamConstant.MAX_TRSP_STRT_DATE,
                    shipperTransportCapacitySearchDTO.getMaxTrspOpDateTrmStrtDate())
                .queryParam(ParamConstant.MIN_TRSP_STRT_DATE,
                    shipperTransportCapacitySearchDTO.getMinTrspOpDateTrmStrtDate())
                //.queryParam(ParamConstant.MAX_TRSP_END_DATE, shipperTransportCapacitySearchDTO.getMaxTrspOpDateTrmEndDate())
                //.queryParam(ParamConstant.MIN_TRSP_END_DATE, shipperTransportCapacitySearchDTO.getMinTrspOpDateTrmEndDate())
                .queryParam(ParamConstant.MAX_TRSP_STRT_TIME,
                    shipperTransportCapacitySearchDTO.getMaxTrspOpPlanDateTrmStrtTime())
                .queryParam(ParamConstant.MIN_TRSP_STRT_TIME,
                    shipperTransportCapacitySearchDTO.getMinTrspOpPlanDateTrmStrtTime())
                //.queryParam(ParamConstant.MAX_TRSP_END_TIME, shipperTransportCapacitySearchDTO.getMaxTrspOpPlanDateTrmEndTime())
                //.queryParam(ParamConstant.MIN_TRSP_END_TIME, shipperTransportCapacitySearchDTO.getMinTrspOpPlanDateTrmEndTime())
                .queryParam(ParamConstant.ADVANCED_CONDITIONS,
                    new ObjectMapper().writeValueAsString(shipperTransportCapacitySearchDTO.getAdvancedSearch()))
                .queryParam("o", searchRequest.getPage())//ix 020 param
                .queryParam("n", searchRequest.getLimit());

            if (searchRequest.getCid() != null) {
                builder.queryParam("cid", searchRequest.getCid());
            }

            url = builder.build().encode().toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        logger.info("DEBUG: IX API-020 url: " + url);
        logger.info("DEBUG: IX API-020 body: " + BaseUtil.makeString(shipperTransportCapacitySearchDTO));
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ixResponseClass);
        logger.info("DEBUG: IX API-020 Successfully called for response: {}", response);

        List<TransportAbilityPublicIXResponseDTO> result = new ArrayList<>();
        List<Map<String, String>> listParam = new ArrayList<>();
        if (response.getBody() instanceof AttributeDTO str) {
            listParam.addAll(getParam(str.getTransportAbilityLineItemDTOS(), null));
        } else if (response.getBody() instanceof IXShipperOperationsResponse str) {
            for (IXShipperOperations shipperOperations : str.getShipperOperationsList()) {
                listParam.addAll(
                    getParam(shipperOperations.getTransportAbilityLineItemDTOS(), shipperOperations.getCarInfoDTOS()));
            }
        }
        if (listParam.isEmpty()) {
            return new PageImpl<>(List.of());
        }

        Page<TransportAbilityPublicResponseDTO> tempPage = vehicleAvbResourceItemCustomRepository.getTransportAbility(
            searchRequest, listParam);
        tempPage.getContent().forEach(page -> {
            TransportAbilityPublicIXResponseDTO ix = vehicleAvbResourceItemMapper.map(page);
            ix.setProposeTrspPlan(cnsLineItemByDateCustomRepository.findByTransportAbilityPublic(page));
            result.add(ix);
        });
        return new PageImpl<>(result, PageRequest.of(tempPage.getNumber(), tempPage.getSize()),
            tempPage.getTotalElements());
    }

    /**
     * 輸送能力のパラメータを取得します。
     *
     * @param trsplineItemDTOList 輸送能力ラインアイテムのDTOリスト
     * @param carInfoDTOS         車両情報のDTOリスト
     * @return パラメータのリスト
     */
    private List<Map<String, String>> getParam(List<TransportAbilityLineItemDTO> trsplineItemDTOList,
        List<CarInfoDTO> carInfoDTOS) {
        List<Map<String, String>> listParam = new ArrayList<>();
        if (trsplineItemDTOList != null) {
            for (TransportAbilityLineItemDTO transportAbilityLineItemDTO : trsplineItemDTOList) {
                if (transportAbilityLineItemDTO.getCarInfoDTOS() != null) {
                    for (CarInfoDTO carInfoDTO : transportAbilityLineItemDTO.getCarInfoDTOS()) {
                        Map<String, String> param = new HashMap<>();
                        param.put("serviceNo", carInfoDTO.getServiceNo());
                        param.put("serviceStrtDate", carInfoDTO.getServiceStrtDate());
                        param.put("freightRate", carInfoDTO.getFreightRate());
                        listParam.add(param);
                    }
                }
            }
        }

        if (carInfoDTOS != null) {
            for (CarInfoDTO carInfoDTO : carInfoDTOS) {
                Map<String, String> param = new HashMap<>();
                param.put("serviceNo", carInfoDTO.getServiceNo());
                param.put("serviceStrtDate", carInfoDTO.getServiceStrtDate());
                param.put("freightRate", carInfoDTO.getFreightRate());
                listParam.add(param);
            }
        }
        return listParam;
    }

    /**
     * ID に基づいて車両アビリティリソースアイテムを取得します。
     *
     * @param id 車両アビリティリソースアイテムのID
     * @return 車両アビリティリソースアイテム
     */
    @Override
    public VehicleAvbResourceItem findById(Long id) {
        Optional<VehicleAvbResourceItem> vehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(id);
        if (vehicleAvbResourceItem.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        } else {
            return vehicleAvbResourceItem.get();
        }
    }

    /**
     * 輸送能力の提案アイテムを取得します。
     *
     * @param request 輸送能力提案リクエスト
     * @return 輸送能力の提案アイテムのページ
     */
    @Override
    public Page<ProposeAbilityDTO> getProposalItem(TransportAbilityProposalRequest request) {
        return vehicleAvbResourceItemCustomRepository.getProposalItem(request);
    }
}
