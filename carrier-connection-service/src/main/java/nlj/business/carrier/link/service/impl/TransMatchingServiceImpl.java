package nlj.business.carrier.link.service.impl;

import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.CnsLineItem;
import nlj.business.carrier.link.domain.CnsLineItemByDate;
import nlj.business.carrier.link.domain.CutOffInfo;
import nlj.business.carrier.link.domain.FreeTimeInfo;
import nlj.business.carrier.link.domain.ReqCnsLineItem;
import nlj.business.carrier.link.domain.ReqShipFromPrtyRqrm;
import nlj.business.carrier.link.domain.ReqShipToPrtyRqrm;
import nlj.business.carrier.link.domain.ReqTrspPlanMsgInfo;
import nlj.business.carrier.link.domain.ShipCutOffInfo;
import nlj.business.carrier.link.domain.ShipFreeTimeInfo;
import nlj.business.carrier.link.domain.ShipFromPrty;
import nlj.business.carrier.link.domain.ShipToPrty;
import nlj.business.carrier.link.domain.TransOrder;
import nlj.business.carrier.link.domain.TransportAbilityLineItem;
import nlj.business.carrier.link.domain.TrspPlanLineItem;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;
import nlj.business.carrier.link.dto.matching.TransMatchingDTO;
import nlj.business.carrier.link.dto.matching.request.CnsLineItemByDateRequest;
import nlj.business.carrier.link.dto.matching.request.MatchingIdRequest;
import nlj.business.carrier.link.dto.matching.request.ShipperOrderIdSaleRequest;
import nlj.business.carrier.link.mapper.CnsLineItemMapper;
import nlj.business.carrier.link.mapper.ShipFromPrtyMapper;
import nlj.business.carrier.link.mapper.ShipFromPrtyRqrmMapper;
import nlj.business.carrier.link.mapper.ShipToPrtyMapper;
import nlj.business.carrier.link.mapper.ShipToPrtyRqrmMapper;
import nlj.business.carrier.link.mapper.TransMatchingMapper;
import nlj.business.carrier.link.mapper.TrspPlanLineItemMapper;
import nlj.business.carrier.link.mapper.TrspPlanMsgInfoMapper;
import nlj.business.carrier.link.repository.CnsLineItemByDateRepository;
import nlj.business.carrier.link.repository.CnsLineItemRepository;
import nlj.business.carrier.link.repository.CutOffInfoRepository;
import nlj.business.carrier.link.repository.FreeTimeInfoRepository;
import nlj.business.carrier.link.repository.ReqCnsLineItemRepository;
import nlj.business.carrier.link.repository.ReqShipFromPrtyRqrmRepository;
import nlj.business.carrier.link.repository.ReqShipToPrtyRqrmRepository;
import nlj.business.carrier.link.repository.ReqTrspPlanMsgInfoRepository;
import nlj.business.carrier.link.repository.ShipCutOffInfoRepository;
import nlj.business.carrier.link.repository.ShipFreeTimeInfoRepository;
import nlj.business.carrier.link.repository.ShipFromPrtyRepository;
import nlj.business.carrier.link.repository.ShipFromPrtyRqrmRepository;
import nlj.business.carrier.link.repository.ShipToPrtyRepository;
import nlj.business.carrier.link.repository.ShipToPrtyRqrmRepository;
import nlj.business.carrier.link.repository.TransMatchingCustomRepository;
import nlj.business.carrier.link.repository.TransOrderRepository;
import nlj.business.carrier.link.repository.TrspPlanLineItemRepository;
import nlj.business.carrier.link.repository.TrspPlanMsgInfoRepository;
import nlj.business.carrier.link.repository.VehicleAvailabilityResourceRepository;
import nlj.business.carrier.link.repository.VehicleAvbResourceItemRepository;
import nlj.business.carrier.link.service.TransMatchingService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 輸送計画マッチングサービス実装クラス.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@AllArgsConstructor
public class TransMatchingServiceImpl implements TransMatchingService {

    private final TransMatchingMapper transMatchingMapper;
    private final TransMatchingCustomRepository transMatchingCustomRepository;
    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;
    private final VehicleAvbResourceItemRepository vehicleAvbResourceItemRepository;
    private final ReqTrspPlanMsgInfoRepository reqTrspPlanMsgInfoRepository;
    private final TrspPlanMsgInfoRepository trspPlanMsgInfoRepository;
    private final TrspPlanMsgInfoMapper trspPlanMsgInfoMapper;
    private final TrspPlanLineItemMapper trspPlanLineItemMapper;
    private final TrspPlanLineItemRepository trspPlanLineItemRepository;
    private final CnsLineItemRepository cnsLineItemRepository;
    private final ShipFromPrtyRepository shipFromPrtyRepository;
    private final ShipFromPrtyMapper shipFromPrtyMapper;
    private final ShipToPrtyRepository shipToPrtyRepository;
    private final ShipFromPrtyRqrmRepository shipFromPrtyRqrmRepository;
    private final ShipToPrtyRqrmRepository shipToPrtyRqrmRepository;
    private final CnsLineItemMapper cnsLineItemMapper;
    private final ShipToPrtyMapper shipToPrtyMapper;
    private final ShipFromPrtyRqrmMapper shipFromPrtyRqrmMapper;
    private final ReqShipFromPrtyRqrmRepository reqShipFromPrtyRqrmRepository;
    private final ReqShipToPrtyRqrmRepository reqShipToPrtyRqrmRepository;
    private final ShipToPrtyRqrmMapper shipToPrtyRqrmMapper;
    private final ReqCnsLineItemRepository reqCnsLineItemRepository;
    private final ShipCutOffInfoRepository shipCutOffInfoRepository;
    private final ShipFreeTimeInfoRepository shipFreeTimeInfoRepository;
    private final VehicleAvailabilityResourceRepository vehicleAvailabilityResourceRepository;
    private final CutOffInfoRepository cutOffInfoRepository;
    private final FreeTimeInfoRepository freeTimeInfoRepository;
    private final TransOrderRepository transOrderRepository;

    /**
     * 時間を減算.<BR>
     *
     * @param time   時間
     * @param offset オフセット
     * @return 時間
     */
    public static LocalTime subtractTime(LocalTime time, BigDecimal offset) {
        int hours = offset.intValue();
        int minutes = offset.subtract(java.math.BigDecimal.valueOf(hours))
            .multiply(BigDecimal.valueOf(60))
            .intValue();
        return time.minusHours(hours).minusMinutes(minutes);
    }

    /**
     * TransportPlanItemSnapshotオブジェクトとVehicleDiagramItemTrailerSnapshotのリストを比較して、Shipperの要求に合ったCarrierを見つけるメソッド。
     *
     * @param cnsLineItemByDateRequest Shipperの要求を表すTransportPlanItemSnapshotオブジェクト
     * @return Shipperの要求に合ったVehicleDiagramItemTrailerSnapshotオブジェクトのリスト
     */
    @Override
    public List<TransMatchingDTO> matchingWithCarrier(CnsLineItemByDateRequest cnsLineItemByDateRequest) {
        Optional<CnsLineItemByDate> cnsLineItemByDate = cnsLineItemByDateRepository.findById(
            Long.parseLong(cnsLineItemByDateRequest.getId()));
        if (cnsLineItemByDate.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.CnsLineItemByDate.CNS_LINE_ITEM_BY_DATE_NOT_FOUND),
                    cnsLineItemByDateRequest.getId())
            );
        }
        return matchingWithCarrier(cnsLineItemByDate.get());
    }

    /**
     * 指定された輸送計画IDを使用してキャリアとの一致処理を行います。
     *
     * @param cnsLineItemByDateRequest 輸送計画IDを含むリクエストデータ
     * @return キャリアとの一致結果を含むTransMatchingDTOのリスト
     */
    @Override
    public List<TransMatchingDTO> matchingWithCarrierByTransportPlan(
        CnsLineItemByDateRequest cnsLineItemByDateRequest) {
        List<CnsLineItemByDate> cnsLineItemByDateList = cnsLineItemByDateRepository.findByTransPlanId(
            cnsLineItemByDateRequest.getId());
        List<TransMatchingDTO> matchingResults = new ArrayList<>();
        for (CnsLineItemByDate cnsLineItemByDate : cnsLineItemByDateList) {
            matchingResults.addAll(matchingWithCarrier(cnsLineItemByDate));
        }
        return matchingResults;
    }

    private List<TransMatchingDTO> matchingWithCarrier(CnsLineItemByDate cnsLineItemByDate) {
        List<Integer> statuses = List.of(ParamConstant.Status.INITIALIZED, ParamConstant.Status.MARKET,
            ParamConstant.Status.AUTOMATIC_MATCHING);
        List<VehicleAvbResourceItem> carrierMatches = transMatchingCustomRepository.carriers(cnsLineItemByDate, null,
            statuses);
        List<TransMatchingDTO> transMatchingDTOS = new ArrayList<>();
        if (carrierMatches != null && !carrierMatches.isEmpty()) {
            for (VehicleAvbResourceItem vehicleAvbResourceItem : carrierMatches) {
                TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItemByDate,
                    vehicleAvbResourceItem);
                transMatchingDTO.setTransType(0);
                transMatchingDTO.setStatus(ParamConstant.Status.MARKET);
                transMatchingDTOS.add(transMatchingDTO);
            }
        } else {
            TransMatchingDTO matchingNG = transMatchingMapper.match(cnsLineItemByDate, new VehicleAvbResourceItem());
            matchingNG.setCarrierOperatorId("");
            matchingNG.setCarrierOperatorCode("");
            matchingNG.setTransType(0);
            matchingNG.setStatus(ParamConstant.Status.INITIALIZED);
            matchingNG.setVehicleAvbResourceItemId(0L);
            matchingNG.setVehicleAvbResourceId(0L);
            transMatchingDTOS.add(matchingNG);
        }
        return transMatchingDTOS;
    }

    /**
     * VehicleDiagramItemTrailerSnapshotオブジェクトとTransportPlanItemSnapshotのリストを比較して、Carrierの要求に合ったShipperを見つけるメソッド。
     *
     * @param id id
     * @return Carrierの要求に合ったTransportPlanItemSnapshotオブジェクトのリスト
     */
    @Override
    public List<TransMatchingDTO> matchingWithShipper(Long id) {
        Optional<VehicleAvbResourceItem> vehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(id);
        if (vehicleAvbResourceItem.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.VehicleAvbResourceItem.VEHICLE_AVB_RESOURCE_ITEM_NOT_FOUND),
                    id));
        }
        Integer transTypeShipper = 0;
        List<Integer> statuses = List.of(ParamConstant.Status.INITIALIZED, ParamConstant.Status.MARKET,
            ParamConstant.Status.AUTOMATIC_MATCHING);
        List<CnsLineItemByDate> shipperMatches = transMatchingCustomRepository.shippers(vehicleAvbResourceItem.get(),
            transTypeShipper, statuses);
        List<TransMatchingDTO> transMatchingDTOS = new ArrayList<>();
        for (CnsLineItemByDate shipper : shipperMatches) {
            TransMatchingDTO transMatchingDTO = transMatchingMapper.match(shipper, vehicleAvbResourceItem.get());
            transMatchingDTO.setTransType(0);
            transMatchingDTOS.add(transMatchingDTO);
        }
        return transMatchingDTOS;
    }

    /**
     * VehicleDiagramItemTrailerSnapshotオブジェクトとTransportPlanItemSnapshotのリストを比較して、Carrierの要求に合ったShipperを見つけるメソッド。
     *
     * @param id id
     * @return Carrierの要求に合ったTransportPlanItemSnapshotオブジェクトのリスト
     */
    @Override
    public List<TransMatchingDTO> matchingWithShipperByVehicleDiagramId(Long id) {
        Integer transTypeCarrier = 0;
        List<Integer> statuses = List.of(ParamConstant.Status.INITIALIZED, ParamConstant.Status.MARKET,
            ParamConstant.Status.AUTOMATIC_MATCHING);
        List<VehicleAvbResourceItem> vehicleAvbResourceItems = vehicleAvbResourceItemRepository.findByVehicleDiagramId(
            id);
        List<TransMatchingDTO> matchingTrailer = new ArrayList<>();
        if (!vehicleAvbResourceItems.isEmpty()) {
            for (VehicleAvbResourceItem vehicleAvbResourceItem : vehicleAvbResourceItems) {
                List<CnsLineItemByDate> cnsLineItemByDates = transMatchingCustomRepository.shippers(
                    vehicleAvbResourceItem, transTypeCarrier, statuses);
                if (!cnsLineItemByDates.isEmpty()) {
                    for (CnsLineItemByDate cnsLineItem : cnsLineItemByDates) {
                        TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItem,
                            vehicleAvbResourceItem);
                        transMatchingDTO.setTransType(transTypeCarrier);
                        transMatchingDTO.setStatus(ParamConstant.Status.MARKET);
                        matchingTrailer.add(transMatchingDTO);
                    }
                }
                break;
            }
            if (matchingTrailer.isEmpty()) {
                TransMatchingDTO matchingNG = transMatchingMapper.match(new CnsLineItemByDate(),
                    vehicleAvbResourceItems.get(0));
                matchingNG.setTransType(transTypeCarrier);
                matchingNG.setStatus(ParamConstant.Status.INITIALIZED);
                matchingNG.setReqCnsLineItemId(0L);
                matchingNG.setCnsLineItemByDateId(0L);
                matchingTrailer.add(matchingNG);
            }
        }
        return matchingTrailer;
    }

    /**
     * キャリア向けのリクエストの登録.<BR>
     *
     * @param cnsLineItemByDateRequest 輸送計画明細リクエスト
     */
    @Override
    @Transactional
    public void insertCarrierOrderIdSale(CnsLineItemByDateRequest cnsLineItemByDateRequest) {
        Optional<CnsLineItemByDate> optionalCnsLineItemByDate = cnsLineItemByDateRepository.findById(
            Long.parseLong(cnsLineItemByDateRequest.getId()));
        CnsLineItemByDate cnsLineItemByDate = optionalCnsLineItemByDate.orElseThrow(() ->
            new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.CnsLineItemByDate.CNS_LINE_ITEM_BY_DATE_NOT_FOUND),
                    cnsLineItemByDateRequest.getId())
            )
        );

        Optional<ReqCnsLineItem> optionalReqCnsLineItem = reqCnsLineItemRepository.findById(
            cnsLineItemByDate.getReqCnsLineItemId());
        ReqCnsLineItem reqCnsLineItem = optionalReqCnsLineItem.orElseThrow(() ->
            new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.ReqCnsLineItem.REQ_CNS_LINE_ITEM_NOT_FOUND),
                    cnsLineItemByDate.getReqCnsLineItemId())
            )
        );

        Optional<ReqTrspPlanMsgInfo> optionalReqTrspPlanMsgInfo = reqTrspPlanMsgInfoRepository.findById(
            reqCnsLineItem.getReqTrspPlanLineItem().getTrspPlanMsgInfoId());
        ReqTrspPlanMsgInfo reqTrspPlanMsgInfo = optionalReqTrspPlanMsgInfo.orElseThrow(() ->
            new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.ReqTrspPlanMsgInfo.REQ_TRSP_PLAN_MSG_INFO_NOT_FOUND),
                    reqCnsLineItem.getReqTrspPlanLineItem().getTrspPlanMsgInfoId())
            )
        );
        trspPlanMsgInfoRepository.save(trspPlanMsgInfoMapper.toTrspPlanMsgInfo(reqTrspPlanMsgInfo));

        TrspPlanLineItem trspPlanLineItem = trspPlanLineItemMapper.toTrspPlanLineItemMapper(
            reqCnsLineItem.getReqTrspPlanLineItem());

        Optional<VehicleAvbResourceItem> optionalVehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(
            Long.parseLong(cnsLineItemByDateRequest.getVehicleAvbResourceItemId()));
        VehicleAvbResourceItem vehicleAvbResourceItem = optionalVehicleAvbResourceItem.orElseThrow(() ->
            new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.VehicleAvbResourceItem.VEHICLE_AVB_RESOURCE_ITEM_NOT_FOUND),
                    cnsLineItemByDateRequest.getVehicleAvbResourceItemId())
            )
        );

        trspPlanLineItem.setServiceNo(vehicleAvbResourceItem.getCarInfo().getServiceNo());
        trspPlanLineItem.setServiceName(vehicleAvbResourceItem.getCarInfo().getServiceName());
        trspPlanLineItem.setServiceStrtDate(vehicleAvbResourceItem.getCarInfo().getServiceStrtDate());
        trspPlanLineItem.setServiceStrtTime(vehicleAvbResourceItem.getCarInfo().getServiceStrtTime());
        trspPlanLineItem.setServiceEndDate(vehicleAvbResourceItem.getCarInfo().getServiceEndDate());
        trspPlanLineItem.setServiceEndTime(vehicleAvbResourceItem.getCarInfo().getServiceEndTime());
        if (reqCnsLineItem.getReqTrspPlanLineItem().getTrspRqrPrtyHeadOffId() == null) {
            trspPlanLineItem.setTrspRqrPrtyHeadOffId(vehicleAvbResourceItem.getOperatorCode());
        }
        if (reqCnsLineItem.getReqTrspPlanLineItem().getTrspRqrPrtyBrncOffId() == null) {
            trspPlanLineItem.setTrspRqrPrtyBrncOffId(vehicleAvbResourceItem.getOperatorCode());
        }
        if (reqCnsLineItem.getReqTrspPlanLineItem().getFreightRate() == null) {
            trspPlanLineItem.setFreightRate(vehicleAvbResourceItem.getCarInfo().getFreightRate());
        }
        TrspPlanLineItem trspPlanLineItemInsert = trspPlanLineItemRepository.save(trspPlanLineItem);

        CnsLineItem cnsLineItem = cnsLineItemMapper.toCnsLineItem(reqCnsLineItem);
        cnsLineItem.setTrspPlanLineItem(trspPlanLineItemInsert);
        CnsLineItem cnsLineItemInsert = cnsLineItemRepository.save(cnsLineItem);

        ShipFromPrty shipFromPrtyInsert = shipFromPrtyRepository.save(
            shipFromPrtyMapper.toShipFromPrty(reqCnsLineItem.getReqTrspPlanLineItem().getReqShipFromPrty()));

        ShipToPrty shipToPrtyInsert = shipToPrtyRepository.save(
            shipToPrtyMapper.toShipToPrty(reqCnsLineItem.getReqTrspPlanLineItem().getReqShipToPrty()));

        ReqShipFromPrtyRqrm reqShipFromPrtyRqrm = reqShipFromPrtyRqrmRepository.findByReqShipFromPrtyId(
            reqCnsLineItem.getReqTrspPlanLineItem().getReqShipFromPrty().getId());
        if (reqShipFromPrtyRqrm != null) {
            shipFromPrtyRqrmRepository.save(shipFromPrtyRqrmMapper.toShipFromPrtyRqrm(reqShipFromPrtyRqrm));
        }

        ReqShipToPrtyRqrm reqShipToPrtyRqrm = reqShipToPrtyRqrmRepository.findByReqShipToPrtyId(
            reqCnsLineItem.getReqTrspPlanLineItem().getReqShipToPrty().getId());
        if (reqShipToPrtyRqrm != null) {
            shipToPrtyRqrmRepository.save(shipToPrtyRqrmMapper.toShipToPrtyRqrm(reqShipToPrtyRqrm));
        }

        List<ShipCutOffInfo> shipCutOffInfosInsert = vehicleAvbResourceItem.getVehicleAvailabilityResource()
            .getCutOffInfos().stream()
            .map(cutOffInfo -> {
                ShipCutOffInfo shipCutOffInfo = new ShipCutOffInfo();
                shipCutOffInfo.setCutOffFee(cutOffInfo.getCutOffFee());
                shipCutOffInfo.setCutOffTime(cutOffInfo.getCutOffTime());
                shipCutOffInfo.setShipFromPrtyId(shipFromPrtyInsert.getId());
                shipCutOffInfo.setOperatorId(shipFromPrtyInsert.getOperatorId());
                return shipCutOffInfo;
            })
            .toList();
        shipCutOffInfoRepository.saveAll(shipCutOffInfosInsert);

        List<ShipFreeTimeInfo> shipFreeTimeInfos = vehicleAvbResourceItem.getVehicleAvailabilityResource()
            .getFreeTimeInfos().stream()
            .map(freeTimeInfo -> {
                ShipFreeTimeInfo shipFreeTimeInfo = new ShipFreeTimeInfo();
                shipFreeTimeInfo.setFreeTimeFee(freeTimeInfo.getFreeTimeFee());
                shipFreeTimeInfo.setFreeTime(freeTimeInfo.getFreeTime());
                shipFreeTimeInfo.setShipToPrtyId(shipToPrtyInsert.getId());
                shipFreeTimeInfo.setOperatorId(shipToPrtyInsert.getOperatorId());
                return shipFreeTimeInfo;
            })
            .toList();
        shipFreeTimeInfoRepository.saveAll(shipFreeTimeInfos);

        Optional<TransOrder> transOrder = transOrderRepository.findById(
            Long.parseLong(cnsLineItemByDateRequest.getOrderId()));
        CnsLineItemByDate cnsLineItemByDateInsert = new CnsLineItemByDate();
        BeanUtils.copyProperties(cnsLineItemByDate, cnsLineItemByDateInsert);
        cnsLineItemByDateInsert.setCreatedDate(LocalDateTime.now());
        cnsLineItemByDateInsert.setUpdatedDate(LocalDateTime.now());

        boolean hasShipperNegotiationData = false;
        if (transOrder.get().getNegotiationData() != null
            && transOrder.get().getNegotiationData().getShipper() != null) {
            hasShipperNegotiationData = true;
        }

        if (hasShipperNegotiationData
            && transOrder.get().getNegotiationData().getShipper().getCollectionTimeFrom() != null) {
            cnsLineItemByDateInsert.setCollectionTimeFrom(
                transOrder.get().getNegotiationData().getShipper().getCollectionTimeFrom());
        } else if (transOrder.get().getRequestCollectionTimeFrom() != null) {
            cnsLineItemByDateInsert.setCollectionTimeFrom(transOrder.get().getRequestCollectionTimeFrom());
        }

        if (hasShipperNegotiationData
            && transOrder.get().getNegotiationData().getShipper().getCollectionTimeTo() != null) {
            cnsLineItemByDateInsert.setCollectionTimeTo(
                transOrder.get().getNegotiationData().getShipper().getCollectionTimeTo());
        } else if (transOrder.get().getRequestCollectionTimeTo() != null) {
            cnsLineItemByDateInsert.setCollectionTimeTo(transOrder.get().getRequestCollectionTimeTo());
        }

        if (hasShipperNegotiationData
            && transOrder.get().getNegotiationData().getShipper().getDepartureDate() != null) {
            cnsLineItemByDateInsert.setCollectionDate(
                transOrder.get().getNegotiationData().getShipper().getDepartureDate());
        } else if (transOrder.get().getRequestSnapshot().getCollectionDate() != null) {
            cnsLineItemByDateInsert.setCollectionDate(transOrder.get().getRequestSnapshot().getCollectionDate());
        }

        if (hasShipperNegotiationData && transOrder.get().getNegotiationData().getShipper().getPrice() != null) {
            cnsLineItemByDateInsert.setPricePerUnit(transOrder.get().getNegotiationData().getShipper().getPrice());
        } else if (transOrder.get().getRequestPrice() != null) {
            cnsLineItemByDateInsert.setPricePerUnit(transOrder.get().getRequestPrice());
        }

        cnsLineItemByDateInsert.setId(null);
        cnsLineItemByDateInsert.setOperatorId(vehicleAvbResourceItem.getOperatorId());
        cnsLineItemByDateInsert.setOperatorCode(vehicleAvbResourceItem.getOperatorCode());
        cnsLineItemByDateInsert.setOperatorName(vehicleAvbResourceItem.getOperatorName());
        cnsLineItemByDateInsert.setTransType(ParamConstant.TransOrderId.CARRIER_AND_CARRIER);
        cnsLineItemByDateInsert.setTransOrderId(Long.parseLong(cnsLineItemByDateRequest.getOrderId()));
        cnsLineItemByDateInsert.setCnsLineItemId(cnsLineItemInsert.getId());
        cnsLineItemByDateInsert.setStatus(ParamConstant.Status.MARKET);

        BigDecimal width = cnsLineItemByDate.getTotalWidth();
        BigDecimal length = cnsLineItemByDate.getTotalLength();
        BigDecimal height = cnsLineItemByDate.getTotalHeight();
        if (width != null && length != null && height != null) {
            cnsLineItemByDateInsert.setIstdTollVolMeas(
                width.multiply(length).multiply(height)
            );
        } else {
            cnsLineItemByDateInsert.setIstdTollVolMeas(BigDecimal.ZERO);
        }
        cnsLineItemByDateRepository.save(cnsLineItemByDateInsert);
    }

    /**
     * キャリア向けのリクエストの登録.<BR>
     *
     * @param cnsLineItemByDateRequest 輸送計画明細リクエスト
     */
    @Override
    @Transactional
    public void carrierOrderIdCancel(CnsLineItemByDateRequest cnsLineItemByDateRequest) {
        CnsLineItemByDate cnsLineItemByDate = cnsLineItemByDateRepository.findById(
            Long.parseLong(cnsLineItemByDateRequest.getId())).get();
        Optional<CnsLineItem> optionalCnsLineItem = cnsLineItemRepository.findById(
            cnsLineItemByDate.getCnsLineItemId());
        CnsLineItem cnsLineItem = optionalCnsLineItem.orElseThrow(() ->
            new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.APIResponses.NOT_FOUND_CODE),
                    cnsLineItemByDate.getReqCnsLineItemId())
            )
        );
        trspPlanLineItemRepository.deleteById(cnsLineItem.getTrspPlanLineItemId());
        cnsLineItemByDateRepository.delete(cnsLineItemByDate);
    }

    /**
     * キャリア間マッチングの実行.<BR>
     *
     * @param request マッチングIDリクエスト
     * @return マッチング結果
     */
    @Override
    public List<TransMatchingDTO> carrierMatchingWithCarrier(MatchingIdRequest request) {
        List<TransMatchingDTO> matchingPackage = matchingCarrierPackage(request);
        List<TransMatchingDTO> matchingTrailer = matchingCarrierTrailer(request);
        List<TransMatchingDTO> matchingResult = new ArrayList<>();
        if (!matchingTrailer.isEmpty()) {
            matchingResult.addAll(matchingTrailer);
        }
        if (!matchingPackage.isEmpty()) {
            matchingResult.addAll(matchingPackage);
        }
        return matchingResult;
    }

    private List<TransMatchingDTO> matchingCarrierPackage(MatchingIdRequest request) {
        Long transOrderId = Long.parseLong(request.getId());
        Integer transTypeCarrier = 1;
        List<Integer> statuses = List.of(ParamConstant.Status.MARKET, ParamConstant.Status.AUTOMATIC_MATCHING);
        List<VehicleAvbResourceItem> listVehicleAvbItem = vehicleAvbResourceItemRepository.findByTransTypeAndTransOrderIdAndStatusIn(
            transTypeCarrier, transOrderId, statuses);
        List<TransMatchingDTO> matchingPackage = new ArrayList<>();
        for (VehicleAvbResourceItem vehicleAvbResourceItem : listVehicleAvbItem) {
            List<CnsLineItemByDate> listMatchedResult = transMatchingCustomRepository.matchingCarrierPackage(
                vehicleAvbResourceItem, transTypeCarrier, statuses);
            if (listMatchedResult != null && !listMatchedResult.isEmpty()) {
                for (CnsLineItemByDate cnsLineItemByDate : listMatchedResult) {
                    TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItemByDate,
                        vehicleAvbResourceItem);
                    transMatchingDTO.setTransType(transTypeCarrier);
                    transMatchingDTO.setStatus(ParamConstant.Status.MARKET);
                    matchingPackage.add(transMatchingDTO);
                }
                break;
            }
        }
        if (matchingPackage.isEmpty() && !listVehicleAvbItem.isEmpty()) {
            TransMatchingDTO matchingNG = transMatchingMapper.match(new CnsLineItemByDate(), listVehicleAvbItem.get(0));
            matchingNG.setVehicleAvbResourceItemId(0L);
            matchingNG.setTransType(transTypeCarrier);
            matchingNG.setStatus(ParamConstant.Status.INITIALIZED);
            matchingNG.setCnsLineItemByDateId(0L);
            matchingPackage.add(matchingNG);
        }
        return matchingPackage;
    }

    private List<TransMatchingDTO> matchingCarrierTrailer(MatchingIdRequest request) {
        Long transOrderId = Long.parseLong(request.getId());
        Integer transTypeCarrier = 1;
        List<Integer> statuses = List.of(ParamConstant.Status.MARKET, ParamConstant.Status.AUTOMATIC_MATCHING);
        List<CnsLineItemByDate> listCnsItemByDate = cnsLineItemByDateRepository.findByTransTypeAndTransOrderIdAndStatusIn(
            transTypeCarrier, transOrderId, statuses);
        List<TransMatchingDTO> matchingTrailer = new ArrayList<>();
        for (CnsLineItemByDate cnsLineItemByDate : listCnsItemByDate) {
            List<VehicleAvbResourceItem> listMatchedResult = transMatchingCustomRepository.matchingCarrierTrailer(
                cnsLineItemByDate, transTypeCarrier, statuses);
            if (listMatchedResult != null && !listMatchedResult.isEmpty()) {
                for (VehicleAvbResourceItem vehicleAvbResourceItem : listMatchedResult) {
                    TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItemByDate,
                        vehicleAvbResourceItem);
                    transMatchingDTO.setTransType(transTypeCarrier);
                    transMatchingDTO.setStatus(ParamConstant.Status.MARKET);
                    transMatchingDTO.setShipperOperatorCode(null);
                    transMatchingDTO.setShipperOperatorId(null);
                    transMatchingDTO.setCarrierOperatorCode(cnsLineItemByDate.getOperatorCode());
                    transMatchingDTO.setCarrierOperatorId(cnsLineItemByDate.getOperatorId());
                    transMatchingDTO.setCarrier2OperatorCode(vehicleAvbResourceItem.getOperatorCode());
                    transMatchingDTO.setCarrier2OperatorId(vehicleAvbResourceItem.getOperatorId());
                    matchingTrailer.add(transMatchingDTO);
                }
                break;
            }
        }

        if (matchingTrailer.isEmpty() && !listCnsItemByDate.isEmpty()) {
            CnsLineItemByDate firstItemByDate = listCnsItemByDate.get(0);
            TransMatchingDTO matchingNG = transMatchingMapper.match(firstItemByDate, new VehicleAvbResourceItem());
            matchingNG.setCarrierOperatorCode(firstItemByDate.getOperatorCode());
            matchingNG.setCarrierOperatorId(firstItemByDate.getOperatorId());
            matchingNG.setShipperOperatorCode(null);
            matchingNG.setShipperOperatorId(null);
            matchingNG.setTransType(transTypeCarrier);
            matchingNG.setStatus(ParamConstant.Status.INITIALIZED);
            matchingNG.setVehicleAvbResourceItemId(0L);
            matchingNG.setVehicleAvbResourceId(0L);
            matchingTrailer.add(matchingNG);
        }
        return matchingTrailer;
    }

    /**
     * マッチングの実行 (case from api) shipper-carrier
     */
    @Override
    public List<TransMatchingDTO> reqCnsLineItemMatching(Long reqCnsLineItemId) {
        List<Integer> statuses = List.of(ParamConstant.Status.INITIALIZED, ParamConstant.Status.MARKET,
            ParamConstant.Status.AUTOMATIC_MATCHING);
        List<CnsLineItemByDate> cnsLineItemByDates = cnsLineItemByDateRepository.findByReqCnsLineItemId(
            reqCnsLineItemId);
        Integer transTypeCarrier = 0;
        List<TransMatchingDTO> matchingList = new ArrayList<>();
        for (CnsLineItemByDate cnsLineItemByDate : cnsLineItemByDates) {
            List<VehicleAvbResourceItem> listMatchedResult = transMatchingCustomRepository.carriers(cnsLineItemByDate,
                transTypeCarrier, statuses);
            if (listMatchedResult != null && !listMatchedResult.isEmpty()) {
                for (VehicleAvbResourceItem vehicleAvbResourceItem : listMatchedResult) {
                    TransMatchingDTO transMatchingDTO = transMatchingMapper.matchCarrierWithCarrier(cnsLineItemByDate,
                        vehicleAvbResourceItem);
                    transMatchingDTO.setTransType(transTypeCarrier);
                    transMatchingDTO.setStatus(ParamConstant.Status.MARKET);
                    matchingList.add(transMatchingDTO);
                }
            } else {
                TransMatchingDTO matchingNG = transMatchingMapper.matchCarrierWithCarrier(cnsLineItemByDate,
                    new VehicleAvbResourceItem());
                matchingNG.setCarrierOperatorId("");
                matchingNG.setCarrierOperatorCode("");
                matchingNG.setTransType(transTypeCarrier);
                matchingNG.setStatus(ParamConstant.Status.INITIALIZED);
                matchingNG.setVehicleAvbResourceItemId(0L);
                matchingNG.setVehicleAvbResourceId(0L);
                matchingList.add(matchingNG);
            }
        }
        return matchingList;
    }

    /**
     * マッチングの実行 (case from api) carrier-shipper
     */
    @Override
    public List<TransMatchingDTO> vehicleAvbResourceMatching(Long vehicleAvbResourceId) {
        Integer transTypeCarrier = 0;
        List<Integer> statuses = List.of(ParamConstant.Status.INITIALIZED, ParamConstant.Status.MARKET,
            ParamConstant.Status.AUTOMATIC_MATCHING);
        List<VehicleAvbResourceItem> vehicleAvbResourceItems = vehicleAvbResourceItemRepository.findByVehicleAvailabilityResourceId(
            vehicleAvbResourceId);
        List<TransMatchingDTO> matchingTrailer = new ArrayList<>();
        if (!vehicleAvbResourceItems.isEmpty()) {
            for (VehicleAvbResourceItem vehicleAvbResourceItem : vehicleAvbResourceItems) {
                List<CnsLineItemByDate> cnsLineItemByDates = transMatchingCustomRepository.shippers(
                    vehicleAvbResourceItem, transTypeCarrier, statuses);
                for (CnsLineItemByDate cnsLineItem : cnsLineItemByDates) {
                    TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItem, vehicleAvbResourceItem);
                    transMatchingDTO.setTransType(transTypeCarrier);
                    transMatchingDTO.setStatus(ParamConstant.Status.MARKET);
                    matchingTrailer.add(transMatchingDTO);
                }
                if (!cnsLineItemByDates.isEmpty()) {
                    break;
                }
            }
            if (matchingTrailer.isEmpty()) {
                TransMatchingDTO matchingNG = transMatchingMapper.match(new CnsLineItemByDate(),
                    vehicleAvbResourceItems.get(0));
                matchingNG.setTransType(transTypeCarrier);
                matchingNG.setStatus(ParamConstant.Status.INITIALIZED);
                matchingNG.setReqCnsLineItemId(0L);
                matchingNG.setCnsLineItemByDateId(0L);
                matchingTrailer.add(matchingNG);
            }
        }
        return matchingTrailer;
    }

    /**
     * マッチングの実行 (case from api) carrier-carrier
     */
    @Override
    public List<TransMatchingDTO> cnsLineItemMatching(Long cnsLineItemId) {
        List<Integer> statuses = List.of(ParamConstant.Status.INITIALIZED, ParamConstant.Status.MARKET,
            ParamConstant.Status.AUTOMATIC_MATCHING);
        List<CnsLineItemByDate> cnsLineItemByDates = cnsLineItemByDateRepository.findByCnsLineItemId(cnsLineItemId);
        Integer transTypeCarrier = 0;
        List<TransMatchingDTO> matchingList = new ArrayList<>();
        for (CnsLineItemByDate cnsLineItemByDate : cnsLineItemByDates) {
            List<VehicleAvbResourceItem> listMatchedResult = transMatchingCustomRepository.carriers(cnsLineItemByDate,
                transTypeCarrier, statuses);
            if (listMatchedResult != null && !listMatchedResult.isEmpty()) {
                for (VehicleAvbResourceItem vehicleAvbResourceItem : listMatchedResult) {
                    TransMatchingDTO transMatchingDTO = transMatchingMapper.match(cnsLineItemByDate,
                        vehicleAvbResourceItem);
                    transMatchingDTO.setTransType(transTypeCarrier);
                    transMatchingDTO.setStatus(ParamConstant.Status.MARKET);
                    matchingList.add(transMatchingDTO);
                }
            } else {
                TransMatchingDTO matchingNG = transMatchingMapper.match(cnsLineItemByDate,
                    new VehicleAvbResourceItem());
                matchingNG.setCarrierOperatorId("");
                matchingNG.setCarrierOperatorCode("");
                matchingNG.setTransType(transTypeCarrier);
                matchingNG.setStatus(ParamConstant.Status.INITIALIZED);
                matchingNG.setVehicleAvbResourceItemId(0L);
                matchingNG.setVehicleAvbResourceId(0L);
                matchingList.add(matchingNG);
            }
        }
        return matchingList;
    }

    /**
     * NLJ フロントエンド画面用に、リスト内のリセール可能な契約を確認できるようにするため.<BR>
     *
     * @param shipperOrderIdSaleRequest 荷主注文ID売却リクエスト
     * @return 車両情報
     */
    @Override
    public List<VehicleAvbResourceItem> insertShipperOrderIdSale(ShipperOrderIdSaleRequest shipperOrderIdSaleRequest) {
        Optional<VehicleAvailabilityResource> optionalVehicleAvailabilityResource = vehicleAvailabilityResourceRepository.findById(
            Long.parseLong(shipperOrderIdSaleRequest.getVehicleAvbResourceId()));
        VehicleAvailabilityResource vehicleAvailabilityResourceById = optionalVehicleAvailabilityResource.orElseThrow(
            () ->
                new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND,
                        SoaResponsePool.get(MessageConstant.VehicleAvbResource.VEHICLE_AVB_RESOURCE_NOT_FOUND),
                        shipperOrderIdSaleRequest.getVehicleAvbResourceId())
                )
        );
        List<VehicleAvbResourceItem> vehicleAvbResourceItemInsert = new ArrayList<>();
        List<CarInfo> carInfos = transMatchingCustomRepository.findCarInfoByGiai(
            vehicleAvailabilityResourceById.getCarInfo());
        for (CarInfo carInfo : carInfos) {
            List<VehicleAvailabilityResource> vehicleAvailabilityResources = vehicleAvailabilityResourceRepository.findByCarInfo(
                carInfo);
            for (VehicleAvailabilityResource vehicleAvailabilityResource : vehicleAvailabilityResources) {
                List<CutOffInfo> cutOffInfoInsert = cutOffInfoRepository.findCutOffInfoByVehicleAvailabilityResource(
                    vehicleAvailabilityResource);
                List<FreeTimeInfo> freeTimeInfosInsert = freeTimeInfoRepository.findCutOffInfoByVehicleAvailabilityResource(
                    vehicleAvailabilityResource);
                vehicleAvbResourceItemInsert.addAll(processVehicleAvbResourceItems(
                    carInfo.getTransportAbilityLineItem(),
                    carInfo,
                    vehicleAvailabilityResource,
                    cutOffInfoInsert,
                    freeTimeInfosInsert)
                );
            }
        }
        return vehicleAvbResourceItemInsert;
    }

    /**
     * 車両情報を作成.<BR>
     *
     * @param transportAbilityLineItem    輸送能力行項目
     * @param carInfo                     車両情報
     * @param vehicleAvailabilityResource 車両情報
     * @param cutOffInfos                 切断情報
     * @param freeTimeInfos               自由時間情報
     * @return 車両情報
     */
    public List<VehicleAvbResourceItem> processVehicleAvbResourceItems(
        TransportAbilityLineItem transportAbilityLineItem, CarInfo carInfo,
        VehicleAvailabilityResource vehicleAvailabilityResource,
        List<CutOffInfo> cutOffInfos, List<FreeTimeInfo> freeTimeInfos) {
        Set<VehicleAvbResourceItem> vehicleAvbResourceItems = new HashSet<>();
        if (!cutOffInfos.isEmpty() && !freeTimeInfos.isEmpty()) {
            for (int j = 0; j < cutOffInfos.size(); j++) {
                for (int k = 0; k < freeTimeInfos.size(); k++) {
                    CutOffInfo cutOffInfo = cutOffInfos.get(j);
                    FreeTimeInfo freeTimeInfo = freeTimeInfos.get(k);
                    BigDecimal price = calculatePrice(carInfo, cutOffInfo, freeTimeInfo);
                    VehicleAvbResourceItem vehicleAvbResourceItem = createVehicleAvbResourceItem(
                        transportAbilityLineItem, carInfo, vehicleAvailabilityResource,
                        cutOffInfo, freeTimeInfo, price, j);
                    vehicleAvbResourceItems.add(vehicleAvbResourceItem);
                }
            }
        } else if (!cutOffInfos.isEmpty()) {
            for (int j = 0; j < cutOffInfos.size(); j++) {
                CutOffInfo cutOffInfo = cutOffInfos.get(j);
                BigDecimal price = calculatePrice(carInfo, cutOffInfo, null);
                VehicleAvbResourceItem vehicleAvbResourceItem = createVehicleAvbResourceItem(transportAbilityLineItem,
                    carInfo, vehicleAvailabilityResource,
                    cutOffInfo, null, price, j);
                vehicleAvbResourceItems.add(vehicleAvbResourceItem);
            }
        } else if (!freeTimeInfos.isEmpty()) {
            for (int j = 0; j < freeTimeInfos.size(); j++) {
                FreeTimeInfo freeTimeInfo = freeTimeInfos.get(j);
                BigDecimal price = calculatePrice(carInfo, null, freeTimeInfo);
                VehicleAvbResourceItem vehicleAvbResourceItem = createVehicleAvbResourceItem(transportAbilityLineItem,
                    carInfo, vehicleAvailabilityResource,
                    null, freeTimeInfo, price, j);
                vehicleAvbResourceItems.add(vehicleAvbResourceItem);
            }
        }
        return vehicleAvbResourceItemRepository.saveAll(vehicleAvbResourceItems);
    }

    /**
     * 価格を計算.<BR>
     *
     * @param carInfo      車両情報
     * @param cutOffInfo   切断情報
     * @param freeTimeInfo 自由時間情報
     * @return 価格
     */
    public BigDecimal calculatePrice(CarInfo carInfo, CutOffInfo cutOffInfo, FreeTimeInfo freeTimeInfo) {
        BigDecimal price = carInfo.getFreightRate();
        if (cutOffInfo != null) {
            price = price.subtract(cutOffInfo.getCutOffFee());
        }
        if (freeTimeInfo != null) {
            price = price.subtract(freeTimeInfo.getFreeTimeFee());
        }
        return price;
    }

    /**
     * 車両情報を作成.<BR>
     *
     * @param transportAbilityLineItem    輸送能力行項目
     * @param carInfo                     車両情報
     * @param vehicleAvailabilityResource 車両情報
     * @param cutOffInfo                  切断情報
     * @param freeTimeInfo                自由時間情報
     * @param price                       価格
     * @param index                       インデックス
     * @return 車両情報
     */
    public VehicleAvbResourceItem createVehicleAvbResourceItem(TransportAbilityLineItem transportAbilityLineItem,
        CarInfo carInfo, VehicleAvailabilityResource vehicleAvailabilityResource,
        CutOffInfo cutOffInfo, FreeTimeInfo freeTimeInfo, BigDecimal price, int index) {
        VehicleAvbResourceItem vehicleAvbResourceItem = new VehicleAvbResourceItem();
        vehicleAvbResourceItem.setOperatorId(transportAbilityLineItem.getOperatorId());
        vehicleAvbResourceItem.setOperatorCode(transportAbilityLineItem.getTrspCliPrtyHeadOffId());
        vehicleAvbResourceItem.setPrice(price);
        vehicleAvbResourceItem.setCarInfo(carInfo);
        vehicleAvbResourceItem.setVehicleAvailabilityResource(vehicleAvailabilityResource);
        vehicleAvbResourceItem.setDepartureFrom(
            Long.parseLong(vehicleAvailabilityResource.getTrspOpStrtAreaCtyJisCd()));
        vehicleAvbResourceItem.setArrivalTo(Long.parseLong(vehicleAvailabilityResource.getTrspOpEndAreaCtyJisCd()));
        vehicleAvbResourceItem.setTripName(carInfo.getServiceName());
        if (cutOffInfo != null) {
            vehicleAvbResourceItem.setDepartureTimeMin(
                subtractTime(vehicleAvailabilityResource.getTrspOpPlanDateTrmStrtTime(), cutOffInfo.getCutOffTime()));
            if (index > 0) {
                vehicleAvbResourceItem.setDepartureTimeMax(
                    subtractTime(carInfo.getServiceStrtTime(), cutOffInfo.getCutOffTime()));
            } else {
                vehicleAvbResourceItem.setDepartureTimeMax(
                    subtractTime(carInfo.getServiceStrtTime(), new BigDecimal("0")));
            }
            vehicleAvbResourceItem.setCutOffInfoId(cutOffInfo.getId());
            vehicleAvbResourceItem.setCutOffTime(cutOffInfo.getCutOffTime());
            vehicleAvbResourceItem.setCutOffFee(cutOffInfo.getCutOffFee());
        }

        if (freeTimeInfo != null) {
            vehicleAvbResourceItem.setDepartureTimeMin(
                subtractTime(vehicleAvailabilityResource.getTrspOpPlanDateTrmStrtTime(), freeTimeInfo.getFreeTime()));
            if (index > 0) {
                vehicleAvbResourceItem.setDepartureTimeMax(
                    subtractTime(carInfo.getServiceStrtTime(), freeTimeInfo.getFreeTime()));
            } else {
                vehicleAvbResourceItem.setDepartureTimeMax(
                    subtractTime(carInfo.getServiceStrtTime(), new BigDecimal("0")));
            }
            vehicleAvbResourceItem.setFreeTimeInfoId(freeTimeInfo.getId());
            vehicleAvbResourceItem.setFreeTimeFee(freeTimeInfo.getFreeTime());
            vehicleAvbResourceItem.setFreeTimeFee(freeTimeInfo.getFreeTimeFee());
        }

        vehicleAvbResourceItem.setArrivalTime(carInfo.getServiceEndTime());
        vehicleAvbResourceItem.setDay(carInfo.getServiceStrtDate());
        vehicleAvbResourceItem.setTotalHeight(carInfo.getCarHghtMeas());
        vehicleAvbResourceItem.setTotalWidth(carInfo.getCarWidMeas());
        vehicleAvbResourceItem.setTotalLength(carInfo.getCarLnghMeas());
        vehicleAvbResourceItem.setGiai(carInfo.getGiai());

        if (carInfo.getTractorIdcr() != null) {
            vehicleAvbResourceItem.setVehicleType(Integer.parseInt(carInfo.getTractorIdcr()));
        }
        vehicleAvbResourceItem.setStatus(ParamConstant.Status.INITIALIZED);
        return vehicleAvbResourceItem;
    }
}
