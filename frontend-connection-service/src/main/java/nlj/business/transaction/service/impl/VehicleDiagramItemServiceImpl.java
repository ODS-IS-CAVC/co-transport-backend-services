package nlj.business.transaction.service.impl;

import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.NextWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.domain.trans.MarketVehicleDiagramItemTrailer;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.dto.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.transaction.dto.MarketVehicleDiagramItemTrailerDTO;
import nlj.business.transaction.dto.VehicleDiagramItemDTO;
import nlj.business.transaction.dto.request.MarketVehicleDiagramItemTrailerSearch;
import nlj.business.transaction.mapper.MarketVehicleDiagramItemTrailerMapper;
import nlj.business.transaction.mapper.VehicleDiagramItemMapper;
import nlj.business.transaction.repository.MarketVehicleDiagramItemTrailerCustomRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.repository.VehicleDiagramItemCustomRepository;
import nlj.business.transaction.repository.VehicleDiagramItemRepository;
import nlj.business.transaction.service.VehicleDiagramItemService;
import org.springframework.data.domain.Page;
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
 * 輸送計画項目サービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleDiagramItemServiceImpl implements VehicleDiagramItemService {

    private final VehicleDiagramItemCustomRepository vehicleDiagramItemCustomRepository;
    private final VehicleDiagramItemRepository vehicleDiagramItemRepository;
    private final VehicleDiagramItemMapper vehicleDiagramItemMapper;
    private final MarketVehicleDiagramItemTrailerMapper marketVehicleDiagramItemTrailerMapper;
    private final MarketVehicleDiagramItemTrailerCustomRepository marketVehicleDiagramItemTrailerCustomRepository;
    private final NljAuthProperties authProperties;
    private final NljUrlProperties urlProperties;
    private final RestTemplate restTemplate;
    private final TransOrderRepository transOrderRepository;

    /**
     * 条件に基づいて輸送計画を検索する
     *
     * @param searchRequest 交通計画公開検索
     */
    @Override
    public Page<MarketVehicleDiagramItemTrailerDTO> search(MarketVehicleDiagramItemTrailerSearch searchRequest) {
        Page<MarketVehicleDiagramItemTrailer> paged = marketVehicleDiagramItemTrailerCustomRepository.search(
            searchRequest);
        return paged.map(marketVehicleDiagramItemTrailerMapper::mapToMarketVehicleDiagramItemTrailerDTO);
    }

    /**
     * 販売中の車両ダイアグラム明細を取得する。
     *
     * @return 販売中の車両ダイアグラム明細のリスト
     */
    @Override
    public List<VehicleDiagramItemDTO> findOnSale() {
        return vehicleDiagramItemMapper.mapToVehicleDiagramItemDTO(
            vehicleDiagramItemRepository.findByStatus(DataBaseConstant.VehicleDiagramItem.STATUS_ON_SALE));
    }

    /**
     * 車両ダイアグラム明細のステータスを更新する。
     *
     * @param id 車両ダイアグラム明細のID
     * @return 更新結果
     */
    @Override
    public MarketVehicleDiagramItemTrailer updateStatusOnSale(Long id) {
//        Optional<TransMatching> transMatching = transMatchingRepository.findById(id);
//
//        if(transMatching.isPresent()){
//            Optional<VehicleDiagramItemTrailer> vehicleDiagramItemTrailer = vehicleDiagramItemTrailerRepository.findById(transMatching.get().getVehicleDiagramItemTrailerId());
//            if(vehicleDiagramItemTrailer.isPresent()){
//                vehicleDiagramItemTrailer.get().setStatus(DataBaseConstant.VehicleDiagramItemTrailer.STATUS_ON_SALE);
//                vehicleDiagramItemTrailerRepository.save(vehicleDiagramItemTrailer.get());
//                VehicleDiagramItemTrailerSnapshot vehicleDiagramItemTrailerSnapshot = transMatching.get().getVehicleDiagramItemTrailerSnapshot();
//                MarketVehicleDiagramItemTrailer marketVehicleDiagramItemTrailer = marketVehicleDiagramItemTrailerMapper.mapToMarket(vehicleDiagramItemTrailerSnapshot);
//                marketVehicleDiagramItemTrailer.setStatus(DataBaseConstant.MarketVehicleDiagramItemTrailer.STATUS_ON_SALE);
//                marketVehicleDiagramItemTrailer.setId(null);
//                marketVehicleDiagramItemTrailerRepository.save(marketVehicleDiagramItemTrailer);
//                return marketVehicleDiagramItemTrailer;
//            }
//        }
//
//        throw new NextWebException(
//                new NextAPIError(HttpStatus.NOT_FOUND,
//                        SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_404),
//                        id)
//        );
        return null;
    }

    /**
     * 運送能力の提案
     *
     * @return 運送能力の提案DTO
     */
    @Override
    public VehicleDiagramItemDTO getTransportAbilityProposal() {
        return vehicleDiagramItemMapper.mapVehicleDiagramItemDTO(
            vehicleDiagramItemCustomRepository.getTransportAbilityProposal());
    }

    /**
     * 指定されたトランスオーダーIDに基づいて出発到着時間を取得する。
     *
     * @param transOrderId       トランスオーダーのID
     * @param httpServletRequest HTTPリクエスト
     * @return 出発到着時間DTO
     */
    @Override
    public DiagramItemDepartureArrivalTimeDTO getDepartureArrivalTime(Long transOrderId,
        HttpServletRequest httpServletRequest) {
        TransOrder transOrder = transOrderRepository.findById(transOrderId).orElse(null);
        if (transOrder == null) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, MessageConstant.System.NOT_FOUND, "trans order");
        }
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO = new DiagramItemDepartureArrivalTimeDTO();
        HttpHeaders headers = setHeader();
        String urlPropertiesTime = urlProperties.getDomainCarrier()
            .concat(APIConstant.CarrierDiagramItem.DIAGRAM_ITEM_TIME)
            .concat(transOrder.getVehicleDiagramItemTrailerId().toString());
        return getDiagramItemDepartureArrivalTimeDTO(departureArrivalTimeDTO, headers, urlPropertiesTime);
    }

    /**
     * 指定されたダイアグラムアイテムIDに基づいて出発到着時間を取得する。
     *
     * @param diagramItemId      ダイアグラムアイテムのID
     * @param httpServletRequest HTTPリクエスト
     * @return 出発到着時間DTO
     */
    @Override
    public DiagramItemDepartureArrivalTimeDTO getDepartureArrivalTimeByDiagramItemId(Long diagramItemId,
        HttpServletRequest httpServletRequest) {
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO = new DiagramItemDepartureArrivalTimeDTO();
        HttpHeaders headers = setHeader();
        String urlPropertiesTime = urlProperties.getDomainCarrier()
            .concat(APIConstant.CarrierDiagramItem.DIAGRAM_ITEM_TIME_BY_DIAGRAM_ITEM_ID)
            .concat(diagramItemId.toString());
        return getDiagramItemDepartureArrivalTimeDTO(departureArrivalTimeDTO, headers, urlPropertiesTime);
    }

    /**
     * ダイアグラムアイテムの出発到着時間DTOを取得する。
     *
     * @param departureArrivalTimeDTO 出発到着時間DTO
     * @param headers                 HTTPヘッダー
     * @param urlPropertiesTime       URL
     * @return 出発到着時間DTO
     */
    private DiagramItemDepartureArrivalTimeDTO getDiagramItemDepartureArrivalTimeDTO(
        DiagramItemDepartureArrivalTimeDTO departureArrivalTimeDTO, HttpHeaders headers, String urlPropertiesTime) {
        HttpEntity<DiagramItemDepartureArrivalTimeDTO> requestEntity =
            new HttpEntity<>(null, headers);
        try {
            ResponseEntity<DiagramItemDepartureArrivalTimeDTO> response = restTemplate.exchange(
                urlPropertiesTime,
                HttpMethod.POST,
                requestEntity,
                DiagramItemDepartureArrivalTimeDTO.class
            );
            if (Objects.nonNull(response.getBody())) {
                departureArrivalTimeDTO = response.getBody();
            }
        } catch (Exception e) {
            log.error("ERROR call Carrier Service : {}", e.getMessage());

        }
        return departureArrivalTimeDTO;
    }

    /**
     * HTTPヘッダーを設定する。
     *
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.API_KEY, authProperties.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
