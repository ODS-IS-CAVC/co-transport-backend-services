package nlj.business.transaction.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.dto.request.MatchingIntervalRequest;
import nlj.business.transaction.dto.response.MatchingIntervalResponse;
import nlj.business.transaction.repository.CnsLineItemByDateRepository;
import nlj.business.transaction.repository.TransMatchingRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.repository.VehicleAvbResourceItemRepository;
import nlj.business.transaction.service.MatchingIntervalService;
import nlj.business.transaction.service.TransMatchingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * マッチング間隔サービスインプル。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class MatchingIntervalServiceImpl implements MatchingIntervalService {

    private final TransMatchingService transMatchingService;

    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;
    private final VehicleAvbResourceItemRepository vehicleAvbResourceItemRepository;
    private final TransMatchingRepository transMatchingRepository;
    private final TransOrderRepository transOrderRepository;

    @Override
    @Transactional
    /**
     * 指定された間隔でマッチングを行う。
     *
     * @param requestData マッチングリクエストデータ
     * @param httpServletRequest HTTPサーブレットリクエスト
     * @return マッチング結果
     */
    public MatchingIntervalResponse matchingByInterval(MatchingIntervalRequest requestData,
        HttpServletRequest httpServletRequest) {
        LocalDateTime lastInsertTime = LocalDateTime.now().minusMinutes(Long.parseLong(requestData.getInterval()));
        switch (requestData.getTarget()) {
            case ParamConstant.MatchingInterval.SHIPPER:
                return matchingShipperWithCarrier(lastInsertTime);
            case ParamConstant.MatchingInterval.CARRIER:
                return matchingCarrierWithShipper(lastInsertTime);
            case ParamConstant.MatchingInterval.CARRIER2:
                return matchCarrierAndCarrier(lastInsertTime);
            case ParamConstant.MatchingInterval.CARRIER2_EMERGENCY:
                return matchCarrierEmergency(httpServletRequest);
        }
        return new MatchingIntervalResponse();
    }

    @Transactional
    /**
     * 緊急のキャリアオーダーをマッチングする。
     *
     * @param httpServletRequest HTTPサーブレットリクエスト
     * @return マッチング結果
     */
    private MatchingIntervalResponse matchCarrierEmergency(HttpServletRequest httpServletRequest) {
        List<Long> listOrderId = transOrderRepository.findIdByEmergencyOnSale(DataBaseConstant.IS_EMERGENCY);
        for (Long orderId : listOrderId) {
            transMatchingService.insertCarrierOrderIdSale(orderId, httpServletRequest);
        }
        return new MatchingIntervalResponse();
    }

    /**
     * キャリア同士のマッチングを行う。
     *
     * @param lastInsertTime 最後の挿入時間
     * @return マッチング結果
     */
    private MatchingIntervalResponse matchCarrierAndCarrier(LocalDateTime lastInsertTime) {
        MatchingIntervalResponse response = new MatchingIntervalResponse();
        List<CnsLineItemByDate> lineItemByDateList = cnsLineItemByDateRepository.findAllByTransTypeAndStatusAndLastCreated(
            DataBaseConstant.CnsLineItemByDate.TransType.CARRIER_REQUEST,
            List.of(
                DataBaseConstant.CnsLineItemByDate.Status.INITIALIZED,
                DataBaseConstant.CnsLineItemByDate.Status.MARKET,
                DataBaseConstant.CnsLineItemByDate.Status.AUTOMATIC_MATCHING
            ),
            lastInsertTime
        );
        for (CnsLineItemByDate cnsLineItemByDate : lineItemByDateList) {
            List<TransMatching> transMatchings = transMatchingService.matchCarrierWithCarrierPackage(cnsLineItemByDate);
            Integer parentEmergencyStatus = cnsLineItemByDateRepository.findParentOrderEmergencyStatus(
                cnsLineItemByDate.getId());
            for (TransMatching transMatching : transMatchings) {
                if (Objects.equals(parentEmergencyStatus, 1)) {
                    transMatching.setIsEmergency(1);
                    transMatchingRepository.save(transMatching);
                }
                if (transMatching.getStatus().equals(DataBaseConstant.TranMatchingStatus.MATCHING_OK)) {
                    response.setMatchingOK(response.getMatchingOK() + 1);
                } else {
                    response.setMatchingNG(response.getMatchingNG() + 1);
                }
            }
        }
        return response;
    }

    /**
     * キャリアとシッパーのマッチングを行う。
     *
     * @param lastInsertTime 最後の挿入時間
     * @return マッチング結果
     */
    private MatchingIntervalResponse matchingCarrierWithShipper(LocalDateTime lastInsertTime) {
        MatchingIntervalResponse response = new MatchingIntervalResponse();
        List<VehicleAvbResourceItem> vehicleAvbResourceItemList = vehicleAvbResourceItemRepository.findAllByStatusAndLastCreated(
            List.of(
                DataBaseConstant.CnsLineItemByDate.Status.INITIALIZED,
                DataBaseConstant.CnsLineItemByDate.Status.MARKET,
                DataBaseConstant.CnsLineItemByDate.Status.AUTOMATIC_MATCHING
            ),
            lastInsertTime
        );
        List<Long> listId = new ArrayList<>();
        Long prevResourceId = null;
        for (VehicleAvbResourceItem vehicleAvbResourceItem : vehicleAvbResourceItemList) {
            Long currentResourceId = vehicleAvbResourceItem.getVehicleAvbResourceId();
            if (!currentResourceId.equals(prevResourceId) && prevResourceId != null) {
                if (!listId.isEmpty()) {
                    vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(prevResourceId,
                        DataBaseConstant.Status.AUTOMATIC_MATCHING);
                } else {
                    vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(prevResourceId,
                        DataBaseConstant.Status.MARKET);
                }
                listId = new ArrayList<>();
            }
            List<TransMatching> transMatchingList = transMatchingService.carrierMatchingShipper(vehicleAvbResourceItem,
                listId);
            for (TransMatching transMatching : transMatchingList) {
                if (transMatching.getStatus().equals(DataBaseConstant.TranMatchingStatus.MATCHING_OK)) {
                    response.setMatchingOK(response.getMatchingOK() + 1);
                } else {
                    response.setMatchingNG(response.getMatchingNG() + 1);
                }
            }
            prevResourceId = currentResourceId;
        }
        return response;
    }

    /**
     * シッパーとキャリアのマッチングを行う。
     *
     * @param lastInsertTime 最後の挿入時間
     * @return マッチング結果
     */
    private MatchingIntervalResponse matchingShipperWithCarrier(LocalDateTime lastInsertTime) {
        MatchingIntervalResponse response = new MatchingIntervalResponse();
        List<CnsLineItemByDate> lineItemByDateList = cnsLineItemByDateRepository.findAllByTransTypeAndStatusAndLastCreated(
            DataBaseConstant.CnsLineItemByDate.TransType.SHIPPER_REQUEST,
            List.of(
                DataBaseConstant.CnsLineItemByDate.Status.INITIALIZED,
                DataBaseConstant.CnsLineItemByDate.Status.MARKET,
                DataBaseConstant.CnsLineItemByDate.Status.AUTOMATIC_MATCHING
            ),
            lastInsertTime
        );
        for (CnsLineItemByDate cnsLineItemByDate : lineItemByDateList) {
            List<TransMatching> transMatchingList = transMatchingService.shipperMatchingCarrier(cnsLineItemByDate);
            for (TransMatching transMatching : transMatchingList) {
                if (transMatching.getStatus().equals(DataBaseConstant.TranMatchingStatus.MATCHING_OK)) {
                    response.setMatchingOK(response.getMatchingOK() + 1);
                } else {
                    response.setMatchingNG(response.getMatchingNG() + 1);
                }
            }
        }
        return response;
    }
}
