package nlj.business.transaction.service.impl;

import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.constant.Prefectures;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.CutOffInfo;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.domain.carrier.VehicleDiagramItemTrailer;
import nlj.business.transaction.domain.shipper.TransportPlanItem;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.dto.TransMatchingDTO;
import nlj.business.transaction.dto.VehicleAvbResourceItemDTO;
import nlj.business.transaction.dto.VehicleAvbResourceItemDTOResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilityDetailResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilitySaleHeadResponse;
import nlj.business.transaction.dto.matching.TransMatchingDTOResponse;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.matching.TrspPlanIdDTOResponse;
import nlj.business.transaction.dto.matching.request.MatchingIdRequest;
import nlj.business.transaction.dto.request.CarrierVehicleDiagramGetRequest;
import nlj.business.transaction.dto.request.CnsLineItemByDateRequest;
import nlj.business.transaction.dto.request.ShipperOrderIdSaleRequest;
import nlj.business.transaction.dto.request.TransportPlanMatchingRequest;
import nlj.business.transaction.dto.response.CarrierLinkMatchingResponseDTO;
import nlj.business.transaction.dto.response.CarrierVehicleDiagramGetResponse;
import nlj.business.transaction.mapper.TransMatchingMapper;
import nlj.business.transaction.mapper.VehicleDiagramItemTrailerMapper;
import nlj.business.transaction.repository.CnsLineItemByDateRepository;
import nlj.business.transaction.repository.CutOffInfoRepository;
import nlj.business.transaction.repository.TransMatchingCustomRepository;
import nlj.business.transaction.repository.TransMatchingRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.repository.TransportPlanItemRepository;
import nlj.business.transaction.repository.VehicleAvbResourceItemCustomRepository;
import nlj.business.transaction.repository.VehicleAvbResourceItemRepository;
import nlj.business.transaction.repository.VehicleDiagramItemTrailerRepository;
import nlj.business.transaction.service.TransMatchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 配送マッチングを実行するクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TransMatchingServiceImpl implements TransMatchingService {

    private final TransMatchingCustomRepository transMatchingCustomRepository;
    private final TransMatchingRepository transMatchingRepository;
    private final TransportPlanItemRepository transportPlanItemRepository;
    private final VehicleDiagramItemTrailerRepository vehicleDiagramItemTrailerRepository;
    private final TransMatchingMapper transMatchingMapper;
    private final VehicleDiagramItemTrailerMapper vehicleDiagramItemTrailerMapper;
    private final RestTemplate restTemplate;
    private final TransOrderRepository transOrderRepository;
    private final VehicleAvbResourceItemCustomRepository vehicleAvbResourceItemCustomRepository;
    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;
    private final VehicleAvbResourceItemRepository vehicleAvbResourceItemRepository;
    private final CutOffInfoRepository cutOffInfoRepository;
    private static final Logger log = LoggerFactory.getLogger(TransMatchingServiceImpl.class);
    @Resource(name = "userContext")
    private final UserContext userContext;

    @Value("${next.url.carrier-link-insert-carrier-order-id-sale}")
    public String INSERT_CARRIER_ORDER_ID_SALE_ENDPOINT;

    @Value("${next.url.carrier-link-insert-carrier-order-id-sale-cancel}")
    public String CANCEL_CARRIER_ORDER_ID_SALE_ENDPOINT;

    @Value("${next.url.carrier-link-insert-shipper-order-id-sale}")
    public String INSERT_SHIPPER_ORDER_ID_SALE_ENDPOINT;

    /**
     * マッチングが必要な輸送能力一覧
     *
     * @param offset 結果の開始位置 - ページネーションに使用。スキップする結果の数
     * @param limit  結果の最大件数 - 1ページあたりに取得する結果の数
     * @return マッチングが必要な輸送能力一覧
     */
    @Override
    public Page<TransMatchingDTOResponse> getTransPlanMatching(String freeWord, List<Integer> temperatureRange,
        int offset, int limit) {
        String companyId = userContext.getUser().getCompanyId();
        freeWord = normalizeFreeword(freeWord);
        return transMatchingCustomRepository.getTransPlanMatching(companyId, freeWord, temperatureRange, offset, limit);
    }

    /**
     * マッチングが必要な輸送能力一覧
     *
     * @param page  結果の開始位置 - ページネーションに使用。スキップする結果の数
     * @param limit 結果の最大件数 - 1ページあたりに取得する結果の数
     * @return マッチングが必要な輸送能力一覧
     */
    @Override
    public Page<TransMatchingHeadResponse> getMatchingByTrailer(String transType, String freeWord,
        List<Integer> temperatureRange, int page, int limit, Integer isEmergency) {
        String companyId = userContext.getUser().getCompanyId();
        freeWord = normalizeFreeword(freeWord);
        return transMatchingCustomRepository.getTransMatchingByTrailer(transType, companyId, freeWord, temperatureRange,
            page - 1, limit, isEmergency);
    }

    /**
     * キャリアによるトレーラーのマッチングを取得します。
     *
     * @param orderId 輸送計画のID
     * @param page    ページ番号
     * @param limit   1ページあたりの最大件数
     * @return マッチング結果
     */
    @Override
    public Page<TransMatchingHeadResponse> getMatchingCarrierByTrailer(Long orderId, int page, int limit) {
        return transMatchingCustomRepository.getTransCarrierMatchingByTrailer(orderId, page - 1, limit);
    }

    /**
     * 輸送能力の販売を取得します。
     *
     * @param transType        輸送タイプ
     * @param temperatureRange 温度範囲
     * @param page             ページ番号
     * @param limit            1ページあたりの最大件数
     * @return 輸送能力の販売結果
     */
    @Override
    public Page<TransMatchingAbilitySaleHeadResponse> getTransportAbilitySale(String transType,
        List<Integer> temperatureRange, int page, int limit) {
        String companyId = userContext.getUser().getCompanyId();
        return transMatchingCustomRepository.getTransportAbilitySale(transType, temperatureRange, companyId, page - 1,
            limit);
    }

    /**
     * トレーラーによる輸送マッチングを取得します。
     *
     * @param request トレーラーのリクエストデータ
     * @return 輸送マッチングのレスポンス
     */
    @Override
    public CarrierVehicleDiagramGetResponse getTransportMatchingByTrailer(CarrierVehicleDiagramGetRequest request) {
        CarrierVehicleDiagramGetResponse response = new CarrierVehicleDiagramGetResponse();
        List<TransMatching> transMatchings = transMatchingRepository.findAllByVehicleDiagramItemTrailerIdIn(
            request.getTrailerIds());
        if (transMatchings == null || transMatchings.isEmpty()) {
            return response;
        }
        Map<Long, Long> diagramItemTrailerMatching = transMatchings.stream()
            .collect(Collectors.groupingBy(TransMatching::getVehicleDiagramItemTrailerId, Collectors.counting()));
        response.setDiagramItemTrailerMatching(diagramItemTrailerMatching);
        return response;
    }

    /**
     * 輸送計画IDに基づいてマッチングが必要な計画一覧を取得します。
     *
     * @param cnsLineItemByDateId CNSラインアイテムの日付ID
     * @return マッチングが必要な計画一覧
     */
    @Override
    public List<TrspPlanIdDTOResponse> getTransPlanMatchingById(Long cnsLineItemByDateId) {
        return transMatchingCustomRepository.getTransPlanMatchingById(cnsLineItemByDateId);
    }

    /**
     * トレーラーIDに基づいてマッチングが必要な輸送能力の詳細を取得します。
     *
     * @param vehicleAvbResourceId 車両の可用リソースID
     * @param transType            輸送タイプ
     * @return 輸送能力の詳細
     */
    @Override
    public TransMatchingAbilityDetailResponse getTransMatchingByTrailerId(Long vehicleAvbResourceId, String transType) {
        return transMatchingCustomRepository.getTransMatchingByTrailerId(vehicleAvbResourceId, transType);
    }

    /**
     * 輸送計画IDですべての輸送計画項目を一致させる
     *
     * @param transportPlanId 輸送計画ID
     */
    @Override
    @Transactional
    public List<TransMatching> matchByTransportPlanId(Long transportPlanId, HttpServletRequest httpServletRequest) {
        List<CnsLineItemByDate> cnsLineItemByDateList = cnsLineItemByDateRepository.findByTransPlanId(
            transportPlanId.toString());
        List<TransMatching> matchingResults = new ArrayList<>();
        for (CnsLineItemByDate cnsLineItemByDate : cnsLineItemByDateList) {
            matchingResults.addAll(shipperMatchingCarrier(cnsLineItemByDate));
        }
        return matchingResults;
    }

    /**
     * 輸送計画IDを使用して、すべての輸送計画項目をキャリアと自動的に一致させます。
     *
     * @param requestBody        輸送計画のリクエストデータを含むオブジェクト
     * @param apiEndpoint        呼び出すAPIエンドポイントのURL
     * @param httpServletRequest クライアントから送信されたHTTPリクエスト情報
     * @return 一致した輸送計画項目のリスト
     */
    private List<TransMatchingDTO> autoMatchingWithCarrier(TransportPlanMatchingRequest requestBody, String apiEndpoint,
        HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<TransportPlanMatchingRequest> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<CarrierLinkMatchingResponseDTO> response = restTemplate.exchange(apiEndpoint, HttpMethod.POST,
            requestEntity, CarrierLinkMatchingResponseDTO.class);
        if (response.getBody() == null) {
            return List.of();
        } else {
            return response.getBody().getData();
        }
    }

    /**
     * IDで輸送計画項目を検索し、一致させる
     *
     * @param cnsLineItemId 輸送計画項目ID
     */
    @Override
    @Transactional
    public List<TransMatching> matchByCnsLineItemId(Long cnsLineItemId, HttpServletRequest httpServletRequest) {
        Optional<CnsLineItemByDate> cnsLineItemByDate = cnsLineItemByDateRepository.findById(cnsLineItemId);
        List<TransMatching> matchingResults = shipperMatchingCarrier(cnsLineItemByDate.get());
        return matchingResults;
    }

    /**
     * 車両可用リソースアイテムIDでマッチングを実行します。
     *
     * @param vehicleAvbResourceItemId 車両可用リソースアイテムID
     * @param httpServletRequest       HTTPリクエスト
     * @return マッチング結果
     */
    @Override
    public List<TransMatching> matchByVehicleAvbResourceItemId(Long vehicleAvbResourceItemId,
        HttpServletRequest httpServletRequest) {
        Optional<VehicleAvbResourceItem> vehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(
            vehicleAvbResourceItemId);
        List<TransMatching> transMatchings = new ArrayList<>();
        List<Long> cnsLineItemByDateIds = new ArrayList<>();
        transMatchings.addAll(carrierMatchingShipper(vehicleAvbResourceItem.get(), cnsLineItemByDateIds));
        return transMatchings;
    }

    /**
     * 輸送能力マッチングを実行します。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @return マッチング結果
     */
    @Override
    @Transactional
    public List<TransMatching> executeMatching(Long vehicleDiagramId, HttpServletRequest httpServletRequest) {
        List<VehicleAvbResourceItem> vehicleAvbResourceItems = vehicleAvbResourceItemRepository.findByVehicleDiagramId(
            vehicleDiagramId);
        List<TransMatching> matchingTrailer = new ArrayList<>();
        List<Long> cnsLineItemByDateIds = new ArrayList<>();
        if (!vehicleAvbResourceItems.isEmpty()) {
            for (VehicleAvbResourceItem vehicleAvbResourceItem : vehicleAvbResourceItems) {
                matchingTrailer.addAll(carrierMatchingShipper(vehicleAvbResourceItem, cnsLineItemByDateIds));
            }
        }
        return matchingTrailer;
    }

    /**
     * 輸送アイテムの能力マッチングを実行します
     *
     * @param vehicleDiagramItemTrailerId 車両ダイアグラムアイテムトレーラーID
     * @return 輸送アイテムの能力マッチングを実行します
     */
    @Override
    @Transactional
    public List<TransMatching> executeMatchingItem(Long vehicleDiagramItemTrailerId) {
        List<TransMatching> transMatchings = new ArrayList<>();
        Optional<VehicleDiagramItemTrailer> vehicleDiagramItemTrailerOptional = vehicleDiagramItemTrailerRepository.findById(
            vehicleDiagramItemTrailerId);
        if (vehicleDiagramItemTrailerOptional.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.Validate.VEHICLE_DIAGRAM_ITEM_TRAILER_404),
                    vehicleDiagramItemTrailerId)
            );
        }
        VehicleDiagramItemTrailer vehicleDiagramItemTrailer = vehicleDiagramItemTrailerOptional.get();
        List<TransportPlanItem> transportPlanItemList = this.transportPlanItemRepository.searchMatching(
            vehicleDiagramItemTrailer.getVehicleDiagram().getDepartureFrom()
            , vehicleDiagramItemTrailer.getVehicleDiagram().getArrivalTo(),
            vehicleDiagramItemTrailer.getVehicleDiagramAllocation().getVehicleInfo().getMaxPayload(),
            vehicleDiagramItemTrailer.getVehicleDiagramAllocation().getVehicleInfo().getTotalLength(),
            vehicleDiagramItemTrailer.getVehicleDiagramAllocation().getVehicleInfo().getTotalWidth(),
            vehicleDiagramItemTrailer.getVehicleDiagramAllocation().getVehicleInfo().getTotalHeight(),
            vehicleDiagramItemTrailer.getVehicleDiagram().getDepartureTime(),
            vehicleDiagramItemTrailer.getVehicleDiagramItem().getPrice());
        for (TransportPlanItem item : transportPlanItemList) {
            TransMatching transMatching = transMatchingMapper.match(item,
                vehicleDiagramItemTrailerMapper.mapToSnapshot(vehicleDiagramItemTrailer));
            transMatchings.add(transMatching);
        }

        return transMatchingRepository.saveAll(transMatchings);
    }

    /**
     * 輸送マッチングを挿入します。
     *
     * @param transMatchingDTOS 輸送マッチングのDTOリスト
     * @return 保存された輸送マッチングのリスト
     */
    @Override
    public List<TransMatching> inserTransMatchings(List<TransMatchingDTO> transMatchingDTOS) {
        List<TransMatching> transMatchings = transMatchingMapper.toMapperTransMatching(transMatchingDTOS);
        return transMatchingRepository.saveAll(transMatchings);
    }

    /**
     * キャリア向けのリクエストの登録
     */
    @Override
    public void insertCarrierOrderIdSale(Long orderId, HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrder = transOrderRepository.findById(orderId);
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), orderId)
            );
        }
        CnsLineItemByDate checkCnsLineItemByDate = cnsLineItemByDateRepository.checkCarrieOnSale(orderId);
        if (checkCnsLineItemByDate == null ||
            (transOrder.get().getCnsLineItemByDateId() == 0
                && transOrder.get().getVehicleAvbResourceId() == 0
                && transOrder.get().getVehicleAvbResourceItemId() == 0
            )
        ) {
            CnsLineItemByDateRequest cnsLineItemByDateRequest = new CnsLineItemByDateRequest(
                transOrder.get().getCnsLineItemByDateId().toString(),
                orderId.toString(),
                transOrder.get().getVehicleAvbResourceItemId().toString()
            );
            HttpHeaders headers = new HttpHeaders();
            headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
            HttpEntity<CnsLineItemByDateRequest> requestEntity = new HttpEntity<>(cnsLineItemByDateRequest, headers);
            try {
                restTemplate.exchange(INSERT_CARRIER_ORDER_ID_SALE_ENDPOINT, HttpMethod.POST, requestEntity,
                    Object.class);
            } catch (Exception ex) {
                log.info("ERROR: Call to " + INSERT_CARRIER_ORDER_ID_SALE_ENDPOINT
                    + " failed. Data is not sync to carrier link.");
                throw ex;
            }
        }
    }

    /**
     * キャリアの注文IDをキャンセルします。
     *
     * @param orderId            注文ID
     * @param httpServletRequest HTTPリクエスト
     */
    @Override
    @Transactional
    public void carrierOrderIdCancel(Long orderId, HttpServletRequest httpServletRequest) {
        Optional<CnsLineItemByDate> optionalCnsLineItemByDate = cnsLineItemByDateRepository.findByTransOrderId(orderId);
        CnsLineItemByDate cnsLineItemByDate = optionalCnsLineItemByDate.orElseThrow(() ->
            new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.System.NOT_FOUND), orderId)
            )
        );
        CnsLineItemByDateRequest cnsLineItemByDateRequest = new CnsLineItemByDateRequest(
            cnsLineItemByDate.getId().toString(), orderId.toString(), "0");
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<CnsLineItemByDateRequest> requestEntity = new HttpEntity<>(cnsLineItemByDateRequest, headers);
        try {
            restTemplate.exchange(CANCEL_CARRIER_ORDER_ID_SALE_ENDPOINT, HttpMethod.POST, requestEntity, Object.class);
            Optional<TransOrder> transOrder = transOrderRepository.findById(cnsLineItemByDate.getTransOrderId());
            if (!transOrder.isEmpty()) {
                vehicleAvbResourceItemRepository.updateStatusById(transOrder.get().getVehicleAvbResourceItemId(),
                    DataBaseConstant.Status.MARKET);
                transOrderRepository.delete(transOrder.get());
            }
            List<TransMatching> transMatchings = transMatchingRepository.getTransMatchingByTransTypeAndCnsLineItemByDateId(
                1, cnsLineItemByDate.getId());
            transMatchingRepository.deleteAll(transMatchings);

        } catch (Exception ex) {
            log.info("ERROR: Call to " + CANCEL_CARRIER_ORDER_ID_SALE_ENDPOINT
                + " failed. Data is not sync to carrier link.");
            throw ex;
        }

    }

    /**
     * キャリア間マッチングの実行
     */
    @Override
    @Transactional
    public List<Long> insertCarrierOrderIdMatching(Long orderId, HttpServletRequest httpServletRequest) {
        return matchCarrierWithCarrierPackage(orderId).stream().map(TransMatching::getId).distinct().toList();
    }

    /**
     * キャリアとキャリアパッケージのマッチングを実行します。
     *
     * @param orderId 注文ID
     * @return マッチング結果
     */
    private List<TransMatching> matchCarrierWithCarrierPackage(Long orderId) {
        List<CnsLineItemByDate> lineItemByDateList = cnsLineItemByDateRepository.findByTransTypeAndTransOrderIdAndStatusIn(
            DataBaseConstant.TransMatching.TransType.CARRIER_REQUEST, orderId,
            List.of(DataBaseConstant.CnsLineItemByDate.Status.MARKET,
                DataBaseConstant.CnsLineItemByDate.Status.AUTOMATIC_MATCHING));
        if (lineItemByDateList.isEmpty()) {
//            throw new NextWebException(
//                    new NextAPIError(HttpStatus.NOT_FOUND,
//                            SoaResponsePool.get(MessageConstant.Validate.ORDER_ID_PACKAGE_NOT_FOUND),
//                            orderId)
//            );
            return new ArrayList<>();
        }
        List<TransMatching> listMatching = new ArrayList<>();
        for (CnsLineItemByDate cnsLineItemByDate : lineItemByDateList) {
            listMatching.addAll(matchCarrierWithCarrierPackage(cnsLineItemByDate));
        }
        return listMatching;
    }

    /**
     * キャリアとキャリアパッケージのマッチングを実行します。
     *
     * @param cnsLineItemByDate CNSラインアイテムの日付
     * @return マッチング結果
     */
    @Override
    public List<TransMatching> matchCarrierWithCarrierPackage(CnsLineItemByDate cnsLineItemByDate) {
        List<VehicleAvbResourceItem> matchedRecordList = vehicleAvbResourceItemCustomRepository.matchingCarrierTrailer(
            cnsLineItemByDate);
        List<TransMatching> results = new ArrayList<>();
        List<Long> vehicleAvbResourceIds = new ArrayList<>();
        //delete old matching record
        transMatchingRepository.deleteByCnsLineItemByDateId(cnsLineItemByDate.getId());
        if (!matchedRecordList.isEmpty()) {
            cnsLineItemByDateRepository.updateStatusById(cnsLineItemByDate.getId(),
                DataBaseConstant.Status.AUTOMATIC_MATCHING);
            for (VehicleAvbResourceItem vehicleAvbResourceItem : matchedRecordList) {
                if (vehicleAvbResourceIds.contains(vehicleAvbResourceItem.getVehicleAvbResourceId())) {
                    continue;
                }
                vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(
                    vehicleAvbResourceItem.getVehicleAvbResourceId(), DataBaseConstant.Status.AUTOMATIC_MATCHING);
                transMatchingRepository.deleteByVehicleAvbResourceIdAndStatus(
                    vehicleAvbResourceItem.getVehicleAvbResourceId(), DataBaseConstant.Status.INITIALIZED);
                Optional<TransMatching> exitsTransMatching = transMatchingRepository.findFirstByMatchingKey(
                    vehicleAvbResourceItem.getVehicleAvbResourceId(),
                    vehicleAvbResourceItem.getId(),
                    cnsLineItemByDate.getId());
                TransMatching transMatching = null;
                TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItemByDate,
                    vehicleAvbResourceItem);
                if (exitsTransMatching.isPresent()) {
                    transMatching = exitsTransMatching.get();
                    transMatchingMapper.updateTransMatching(transMatching, transMatchingDTO);
                } else {
                    transMatching = transMatchingMapper.toMapperTransMatching(transMatchingDTO);
                    transMatching.setMatchingType(DataBaseConstant.TransMatching.MatchingType.CARRIER_MATCH_CARRIER);
                }
                transMatching.setStatus(DataBaseConstant.TranMatchingStatus.MATCHING_OK);
                transMatching.setTransportDate(vehicleAvbResourceItem.getDay());
                transMatching.setTransType(DataBaseConstant.TransMatching.TransType.CARRIER_REQUEST);
                transMatching.setFreeWord(insertFreeWordTransMatching(cnsLineItemByDate, vehicleAvbResourceItem));
                results.add(transMatchingRepository.save(transMatching));
                vehicleAvbResourceIds.add(vehicleAvbResourceItem.getVehicleAvbResourceId());
            }
        } else {
            cnsLineItemByDateRepository.updateStatusById(cnsLineItemByDate.getId(), DataBaseConstant.Status.MARKET);
            TransMatching transMatchingNG = transMatchingMapper.toMapperTransMatching(
                transMatchingMapper.match(cnsLineItemByDate, new VehicleAvbResourceItem()));
            transMatchingNG.setCarrierOperatorCode(cnsLineItemByDate.getOperatorCode());
            transMatchingNG.setCarrierOperatorId(cnsLineItemByDate.getOperatorId());
            transMatchingNG.setShipperOperatorCode(null);
            transMatchingNG.setShipperOperatorId(null);
            transMatchingNG.setTransType(DataBaseConstant.TransMatching.TransType.CARRIER_REQUEST);
            transMatchingNG.setStatus(DataBaseConstant.TranMatchingStatus.MATCHING_NOT_GOOD);
            transMatchingNG.setVehicleAvbResourceItemId(0L);
            transMatchingNG.setVehicleAvbResourceId(0L);
            transMatchingNG.setMatchingType(DataBaseConstant.TransMatching.MatchingType.CARRIER_MATCH_CARRIER);
            transMatchingNG.setFreeWord(insertFreeWordTransMatching(cnsLineItemByDate, new VehicleAvbResourceItem()));
            results.add(transMatchingRepository.save(transMatchingNG));
        }
        return results;
    }

    /**
     * マッチングの実行 (case from api)
     */
    @Override
    @Transactional
    public List<TransMatching> reqCnsLineItemMatching(MatchingIdRequest matchingIdRequest,
        HttpServletRequest httpServletRequest) {
        List<CnsLineItemByDate> cnsLineItemByDates = cnsLineItemByDateRepository.findByReqCnsLineItemId(
            Long.parseLong(matchingIdRequest.getId()));
        List<TransMatching> matchingList = new ArrayList<>();
        for (CnsLineItemByDate cnsLineItemByDate : cnsLineItemByDates) {
            matchingList.addAll(shipperMatchingCarrier(cnsLineItemByDate));
        }
        return matchingList;
    }

    /**
     * マッチングの実行 (case from api)
     */
    @Override
    @Transactional
    public List<TransMatching> vehicleAvbResourceMatching(MatchingIdRequest matchingIdRequest,
        HttpServletRequest httpServletRequest) {
        List<VehicleAvbResourceItem> vehicleAvbResourceItems = vehicleAvbResourceItemRepository.findByVehicleAvbResourceId(
            Long.parseLong(matchingIdRequest.getId()));
        List<TransMatching> matchingTrailer = new ArrayList<>();
        List<Long> cnsLineItemByDateIds = new ArrayList<>();
        if (!vehicleAvbResourceItems.isEmpty()) {
            for (VehicleAvbResourceItem vehicleAvbResourceItem : vehicleAvbResourceItems) {
                matchingTrailer.addAll(carrierMatchingShipper(vehicleAvbResourceItem, cnsLineItemByDateIds));
            }
        }
        return matchingTrailer;
    }

    /**
     * マッチングの実行 (case from api)
     */
    @Override
    @Transactional
    public List<TransMatching> cnsLineItemMatching(MatchingIdRequest matchingIdRequest,
        HttpServletRequest httpServletRequest) {
        List<CnsLineItemByDate> cnsLineItemByDates = cnsLineItemByDateRepository.findByCnsLineItemId(
            Long.parseLong(matchingIdRequest.getId()));
        List<TransMatching> matchingList = new ArrayList<>();
        for (CnsLineItemByDate cnsLineItemByDate : cnsLineItemByDates) {
            matchingList.addAll(shipperMatchingCarrier(cnsLineItemByDate));
        }
        return matchingList;
    }

    /**
     * NLJ フロントエンド画面用に、リスト内のリセール可能な契約を確認できるようにするため。 param: orderId from t_trans_order
     */
    @Override
    public List<VehicleAvbResourceItemDTO> insertShipperOrderIdSale(Long orderId,
        HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrder = transOrderRepository.findById(orderId);
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), orderId)
            );
        }
        ShipperOrderIdSaleRequest shipperOrderIdSaleRequest = new ShipperOrderIdSaleRequest(
            transOrder.get().getVehicleAvbResourceId().toString()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<ShipperOrderIdSaleRequest> requestEntity = new HttpEntity<>(shipperOrderIdSaleRequest, headers);
        ResponseEntity<VehicleAvbResourceItemDTOResponse> response =
            restTemplate.exchange(INSERT_SHIPPER_ORDER_ID_SALE_ENDPOINT, HttpMethod.POST, requestEntity,
                VehicleAvbResourceItemDTOResponse.class);
        List<VehicleAvbResourceItemDTO> list = new ArrayList<>();
        if (response.getBody() != null) {
            list = response.getBody().getData();
        }
        return list;
    }

    /**
     * キャリアとシッパーのマッチングを実行します。
     *
     * @param vehicleAvbResourceItem 車両可用リソースアイテム
     * @param cnsLineItemByDateIds   CNSラインアイテムの日付IDリスト
     * @return マッチング結果
     */
    @Override
    public List<TransMatching> carrierMatchingShipper(VehicleAvbResourceItem vehicleAvbResourceItem,
        List<Long> cnsLineItemByDateIds) {
        Integer transTypeCarrier = 0;
        List<Integer> statuses = List.of(ParamConstant.Status.INITIALIZED, ParamConstant.Status.MARKET,
            ParamConstant.Status.AUTOMATIC_MATCHING);
        List<CnsLineItemByDate> cnsLineItemByDates = transMatchingCustomRepository.shippers(vehicleAvbResourceItem,
            transTypeCarrier, statuses, cnsLineItemByDateIds);
        List<TransMatching> listMatching = new ArrayList<>();
        transMatchingRepository.deleteByVehicleAvbResourceItemId(vehicleAvbResourceItem.getId());
        if (!cnsLineItemByDates.isEmpty()) {
            for (CnsLineItemByDate cnsLineItem : cnsLineItemByDates) {
                cnsLineItemByDateRepository.updateStatusById(cnsLineItem.getId(),
                    ParamConstant.Status.AUTOMATIC_MATCHING);
                transMatchingRepository.deleteByCnsLineItemByDateIdAndStatus(cnsLineItem.getId(),
                    DataBaseConstant.Status.INITIALIZED);
                Optional<TransMatching> exitsTransMatching = transMatchingRepository.findFirstByMatchingKey(
                    vehicleAvbResourceItem.getVehicleAvbResourceId(),
                    vehicleAvbResourceItem.getId(),
                    cnsLineItem.getId());
                TransMatching transMatching = null;
                TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItem, vehicleAvbResourceItem);
                if (exitsTransMatching.isPresent()) {
                    transMatching = exitsTransMatching.get();
                    transMatchingMapper.updateTransMatching(transMatching, transMatchingDTO);
                } else {
                    transMatching = transMatchingMapper.toMapperTransMatching(transMatchingDTO);
                    transMatching.setMatchingType(DataBaseConstant.TransMatching.MatchingType.CARRIER_MATCH_SHIPPER);
                }
                transMatching.setStatus(DataBaseConstant.TranMatchingStatus.MATCHING_OK);
                transMatching.setTransportDate(vehicleAvbResourceItem.getDay());
                transMatching.setTransType(DataBaseConstant.TransMatching.TransType.SHIPPER_REQUEST);
                transMatching.setFreeWord(insertFreeWordTransMatching(cnsLineItem, vehicleAvbResourceItem));
                listMatching.add(transMatchingRepository.save(transMatching));
                cnsLineItemByDateIds.add(cnsLineItem.getId());
            }
        } else {
            TransMatching transMatchingNG = transMatchingMapper.toMapperTransMatching(
                transMatchingMapper.match(new CnsLineItemByDate(), vehicleAvbResourceItem));
            transMatchingNG.setCarrierOperatorCode("");
            transMatchingNG.setCarrierOperatorId("");
            transMatchingNG.setShipperOperatorCode(vehicleAvbResourceItem.getOperatorCode());
            transMatchingNG.setShipperOperatorId(vehicleAvbResourceItem.getOperatorId());
            transMatchingNG.setTransType(DataBaseConstant.TransMatching.TransType.SHIPPER_REQUEST);
            transMatchingNG.setStatus(DataBaseConstant.TranMatchingStatus.MATCHING_NOT_GOOD);
            transMatchingNG.setCnsLineItemByDateId(0L);
            transMatchingNG.setReqCnsLineItemId(0L);
            transMatchingNG.setMatchingType(DataBaseConstant.TransMatching.MatchingType.CARRIER_MATCH_SHIPPER);
            transMatchingNG.setFreeWord(insertFreeWordTransMatching(new CnsLineItemByDate(), vehicleAvbResourceItem));
            listMatching.add(transMatchingRepository.save(transMatchingNG));
        }
        return listMatching;
    }

    /**
     * カットオフ情報を取得します。
     *
     * @param vehicleAvbResoureId 車両可用リソースID
     * @return カットオフ情報のリスト
     */
    @Override
    public List<CutOffInfo> getCufOffInfo(Long vehicleAvbResoureId) {
        return cutOffInfoRepository.findByVehicleAvbResourceId(vehicleAvbResoureId);
    }

    /**
     * シッパーとキャリアのマッチングを実行します。
     *
     * @param cnsLineItemByDate CNSラインアイテムの日付
     * @return マッチング結果
     */
    @Override
    public List<TransMatching> shipperMatchingCarrier(CnsLineItemByDate cnsLineItemByDate) {
        Integer transTypeCarrier = 0;
        List<Integer> statuses = List.of(ParamConstant.Status.INITIALIZED, ParamConstant.Status.MARKET,
            ParamConstant.Status.AUTOMATIC_MATCHING);
        List<TransMatching> listMatching = new ArrayList<>();
        List<Long> vehicleAvbResourceIds = new ArrayList<>();
        List<VehicleAvbResourceItem> vehicleAvbResourceItems = transMatchingCustomRepository.carriers(cnsLineItemByDate,
            transTypeCarrier, statuses);
        transMatchingRepository.deleteByCnsLineItemByDateId(cnsLineItemByDate.getId());
        if (!vehicleAvbResourceItems.isEmpty()) {
            //Update status cns_line_item_by_date = 2: matching tu dong
            cnsLineItemByDateRepository.updateStatusById(cnsLineItemByDate.getId(),
                DataBaseConstant.Status.AUTOMATIC_MATCHING);

            for (VehicleAvbResourceItem vehicleAvbResourceItem : vehicleAvbResourceItems) {
                if (vehicleAvbResourceIds.contains(vehicleAvbResourceItem.getVehicleAvbResourceId())) {
                    continue;
                }
                vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(
                    vehicleAvbResourceItem.getVehicleAvbResourceId(), DataBaseConstant.Status.AUTOMATIC_MATCHING);
                transMatchingRepository.deleteByVehicleAvbResourceIdAndStatus(
                    vehicleAvbResourceItem.getVehicleAvbResourceId(), DataBaseConstant.Status.INITIALIZED);
                Optional<TransMatching> exitsTransMatching = transMatchingRepository.findFirstByMatchingKey(
                    vehicleAvbResourceItem.getVehicleAvbResourceId(),
                    vehicleAvbResourceItem.getId(),
                    cnsLineItemByDate.getId());
                TransMatching transMatching = null;
                TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItemByDate,
                    vehicleAvbResourceItem);
                if (exitsTransMatching.isPresent()) {
                    transMatching = exitsTransMatching.get();
                    transMatchingMapper.updateTransMatching(transMatching, transMatchingDTO);
                } else {
                    transMatching = transMatchingMapper.toMapperTransMatching(transMatchingDTO);
                    transMatching.setMatchingType(DataBaseConstant.TransMatching.MatchingType.SHIPPER_MATCH_CARRIER);
                }
                transMatching.setStatus(DataBaseConstant.TranMatchingStatus.MATCHING_OK);
                transMatching.setTransportDate(vehicleAvbResourceItem.getDay());
                transMatching.setTransType(DataBaseConstant.TransMatching.TransType.SHIPPER_REQUEST);
                transMatching.setFreeWord(insertFreeWordTransMatching(cnsLineItemByDate, vehicleAvbResourceItem));
                listMatching.add(transMatchingRepository.save(transMatching));
                vehicleAvbResourceIds.add(vehicleAvbResourceItem.getVehicleAvbResourceId());
            }
        } else {
            //Update status cns_line_item_by_date = 1: thi truong
            cnsLineItemByDateRepository.updateStatusById(cnsLineItemByDate.getId(), DataBaseConstant.Status.MARKET);

            TransMatching transMatchingNG = transMatchingMapper.toMapperTransMatching(
                transMatchingMapper.match(cnsLineItemByDate, new VehicleAvbResourceItem()));
            transMatchingNG.setCarrierOperatorCode(cnsLineItemByDate.getOperatorCode());
            transMatchingNG.setCarrierOperatorId(cnsLineItemByDate.getOperatorId());
            transMatchingNG.setShipperOperatorCode(null);
            transMatchingNG.setShipperOperatorId(null);
            transMatchingNG.setTransType(DataBaseConstant.TransMatching.TransType.SHIPPER_REQUEST);
            transMatchingNG.setStatus(DataBaseConstant.TranMatchingStatus.MATCHING_NOT_GOOD);
            transMatchingNG.setVehicleAvbResourceItemId(0L);
            transMatchingNG.setVehicleAvbResourceId(0L);
            transMatchingNG.setMatchingType(DataBaseConstant.TransMatching.MatchingType.SHIPPER_MATCH_CARRIER);
            transMatchingNG.setFreeWord(insertFreeWordTransMatching(cnsLineItemByDate, new VehicleAvbResourceItem()));
            listMatching.add(transMatchingRepository.save(transMatchingNG));
        }
        return listMatching;
    }

    /**
     * フリーワードを挿入します。
     *
     * @param cnsLineItemByDate      CNSラインアイテムの日付
     * @param vehicleAvbResourceItem 車両可用リソースアイテム
     * @return フリーワード
     */
    @Override
    public String insertFreeWordTransMatching(CnsLineItemByDate cnsLineItemByDate,
        VehicleAvbResourceItem vehicleAvbResourceItem) {
        StringBuilder result = new StringBuilder();

        String fromText = null;
        if (cnsLineItemByDate.getDepartureFrom() != null) {
            fromText = Prefectures.data.get(cnsLineItemByDate.getDepartureFrom());
        } else if (vehicleAvbResourceItem.getDepartureFrom() != null) {
            fromText = Prefectures.data.get(vehicleAvbResourceItem.getDepartureFrom());
        }

        String arrText = null;
        if (cnsLineItemByDate.getArrivalTo() != null) {
            arrText = Prefectures.data.get(cnsLineItemByDate.getArrivalTo());
        } else if (vehicleAvbResourceItem.getArrivalTo() != null) {
            arrText = Prefectures.data.get(vehicleAvbResourceItem.getArrivalTo());
        }

        Consumer<Object> appendIfNotNullOrEmpty = value -> {
            if (value != null) {
                String strValue = value.toString();
                if (!strValue.isEmpty()) {
                    result.append(strValue);
                }
            }
        };

        appendIfNotNullOrEmpty.accept(cnsLineItemByDate.getTransportName());
        appendIfNotNullOrEmpty.accept(fromText);
        appendIfNotNullOrEmpty.accept(arrText);
        appendIfNotNullOrEmpty.accept(cnsLineItemByDate.getPricePerUnit());
        appendIfNotNullOrEmpty.accept(vehicleAvbResourceItem.getPrice());
        appendIfNotNullOrEmpty.accept(cnsLineItemByDate.getOperatorName());
        appendIfNotNullOrEmpty.accept(vehicleAvbResourceItem.getOperatorName());
        appendIfNotNullOrEmpty.accept(vehicleAvbResourceItem.getTripName());
        appendIfNotNullOrEmpty.accept(vehicleAvbResourceItem.getVehicleName());

        return result.toString();
    }

    /**
     * フリーワードを正規化します。
     *
     * @param freeword フリーワード
     * @return 正規化されたフリーワード
     */
    @Override
    public String normalizeFreeword(String freeword) {
        if (freeword == null) {
            return "";
        }
        return freeword.replaceAll("(?<=\\d),(?=\\d)", "");
    }
}
