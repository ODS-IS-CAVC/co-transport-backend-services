package nlj.business.carrier.link.service.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.link.constant.DataBaseConstant.DATE_TIME_FORMAT;
import nlj.business.carrier.link.constant.MessageConstant.TrspPlanItem;
import nlj.business.carrier.link.constant.MessageConstant.Validate;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.constant.ParamConstant.DataModelType;
import nlj.business.carrier.link.constant.ParamConstant.Status;
import nlj.business.carrier.link.constant.ParamConstant.TransOrderId;
import nlj.business.carrier.link.constant.ParamConstant.YAMATO_DEFAULT;
import nlj.business.carrier.link.domain.CarrierOperator;
import nlj.business.carrier.link.domain.CnsLineItem;
import nlj.business.carrier.link.domain.CnsLineItemByDate;
import nlj.business.carrier.link.domain.ShipCutOffInfo;
import nlj.business.carrier.link.domain.ShipFreeTimeInfo;
import nlj.business.carrier.link.domain.ShipFromPrty;
import nlj.business.carrier.link.domain.ShipFromPrtyRqrm;
import nlj.business.carrier.link.domain.ShipToPrty;
import nlj.business.carrier.link.domain.ShipToPrtyRqrm;
import nlj.business.carrier.link.domain.ShipperOperator;
import nlj.business.carrier.link.domain.TrspPlanLineItem;
import nlj.business.carrier.link.domain.TrspPlanMsgInfo;
import nlj.business.carrier.link.dto.commonBody.response.CommonResponseDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.CnsLineItemDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipCutOffInfoDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipFreeTimeInfoDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipFromPrtyDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipFromPrtyRqrmDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipToPrtyDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.ShipToPrtyRqrmDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspPlanLineItemDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspPlanLineItemDeleteDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemDeleteRequest;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemRegisterRequest;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import nlj.business.carrier.link.dto.trspPlanLineItem.response.TrspPlanLineItemSearchResponse;
import nlj.business.carrier.link.mapper.CnsLineItemMapper;
import nlj.business.carrier.link.mapper.DateTimeMapper;
import nlj.business.carrier.link.mapper.ShipCutOffInfoMapper;
import nlj.business.carrier.link.mapper.ShipFreeTimeInfoMapper;
import nlj.business.carrier.link.mapper.ShipFromPrtyMapper;
import nlj.business.carrier.link.mapper.ShipFromPrtyRqrmMapper;
import nlj.business.carrier.link.mapper.ShipToPrtyMapper;
import nlj.business.carrier.link.mapper.ShipToPrtyRqrmMapper;
import nlj.business.carrier.link.mapper.TrspPlanLineItemDeleteMapper;
import nlj.business.carrier.link.mapper.TrspPlanLineItemMapper;
import nlj.business.carrier.link.mapper.TrspPlanMsgInfoMapper;
import nlj.business.carrier.link.repository.CarrierOperatorRepository;
import nlj.business.carrier.link.repository.CnsLineItemByDateRepository;
import nlj.business.carrier.link.repository.CnsLineItemRepository;
import nlj.business.carrier.link.repository.ShipCutOffInfoRepository;
import nlj.business.carrier.link.repository.ShipFreeTimeInfoRepository;
import nlj.business.carrier.link.repository.ShipFromPrtyRepository;
import nlj.business.carrier.link.repository.ShipFromPrtyRqrmRepository;
import nlj.business.carrier.link.repository.ShipToPrtyRepository;
import nlj.business.carrier.link.repository.ShipToPrtyRqrmRepository;
import nlj.business.carrier.link.repository.ShipperOperatorRepository;
import nlj.business.carrier.link.repository.TrspPlanLineItemRepository;
import nlj.business.carrier.link.repository.TrspPlanMsgInfoRepository;
import nlj.business.carrier.link.service.TrspPlanLineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 輸送計画明細サービス実装.<BR>
 * </PRE>
 *
 * @author Next Logistic
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TrspPlanLineItemServiceImpl implements TrspPlanLineItemService {

    private final TrspPlanLineItemRepository trspPlanLineItemRepository;

    private final CnsLineItemRepository cnsLineItemRepository;

    private final ShipFromPrtyRepository shipFromPrtyRepository;

    private final ShipFromPrtyRqrmRepository shipFromPrtyRqrmRepository;

    private final ShipToPrtyRepository shipToPrtyRepository;
    private final ShipToPrtyRqrmRepository shipToPrtyRqrmRepository;
    private final TrspPlanMsgInfoRepository trspPlanMsgInfoRepository;

    private final TrspPlanMsgInfoMapper trspPlanMsgInfoMapper;
    private final TrspPlanLineItemMapper trspPlanLineItemMapper;
    private final TrspPlanLineItemDeleteMapper trspPlanLineItemDeleteMapper;
    private final CnsLineItemMapper cnsLineItemMapper;
    private final ShipFromPrtyMapper shipFromPrtyMapper;
    private final ShipFromPrtyRqrmMapper shipFromPrtyRqrmMapper;
    private final ShipToPrtyMapper shipToPrtyMapper;
    private final ShipToPrtyRqrmMapper shipToPrtyRqrmMapper;
    private final ShipCutOffInfoMapper shipCutOffInfoMapper;
    private final ShipCutOffInfoRepository shipCutOffInfoRepository;
    private final ShipFreeTimeInfoRepository shipFreeTimeInfoRepository;
    private final ShipFreeTimeInfoMapper shipFreeTimeInfoMapper;
    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;

    private final CarrierOperatorRepository carrierOperatorRepository;

    private final ShipperOperatorRepository shipperOperatorRepository;

    @Resource(name = "userContext")
    private final UserContext userContext;
    private final EntityManager entityManager;

    /**
     * 輸送計画を登録.<BR>
     *
     * @param request   輸送計画リクエスト
     * @param xTracking トラッキングID
     */
    @Override
    @Transactional
    public void registerTrspPlan(TrspPlanLineItemRegisterRequest request, String xTracking) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }

        TrspPlanMsgInfo planMsgInfo = insertPlanMsgInfo(
            trspPlanMsgInfoMapper.toEntity(request.getMsgInfo(), request.getTrspPlan()), xTracking);
        assert planMsgInfo != null;
        Long trspPlanMsgInfoId = planMsgInfo.getId();
        List<CnsLineItem> cnsLineItemList = new ArrayList<>();
        List<ShipFromPrtyRqrm> shipFromPrtyRqrmList = new ArrayList<>();
        List<ShipToPrtyRqrm> shipToPrtyRqrmList = new ArrayList<>();
        List<ShipCutOffInfo> shipCutOffInfoList = new ArrayList<>();
        List<ShipFreeTimeInfo> shipFreeTimeInfoList = new ArrayList<>();
        if (null != request.getTrspPlanLineItem() && !request.getTrspPlanLineItem().isEmpty()) {
            for (TrspPlanLineItemDTO dto : request.getTrspPlanLineItem()) {
                TrspPlanLineItem trspPlanLineItem;
                trspPlanLineItem = trspPlanLineItemMapper.toEntity(dto.getTrspIsr(), dto.getTrspSrvc(),
                    dto.getTrspVehicleTrms(), dto.getDelInfo(), dto.getCns(),
                    dto.getCnsgPrty(), dto.getTrspRqrPrty(), dto.getCneePrty(), dto.getLogsSrvcPrv(), dto.getRoadCarr(),
                    dto.getFretClimToPrty());
                if (BaseUtil.isNull(trspPlanLineItem.getTrspInstructionId())
                    || BaseUtil.isNull(trspPlanLineItem.getServiceNo())
                    || BaseUtil.isNull(trspPlanLineItem.getServiceName())
                    || BaseUtil.isNull(String.valueOf(trspPlanLineItem.getServiceStrtDate()))
                    || BaseUtil.isNull(String.valueOf(trspPlanLineItem.getServiceStrtTime()))
                    || BaseUtil.isNull(String.valueOf(trspPlanLineItem.getFreightRate()))
                    || BaseUtil.isNull(String.valueOf(trspPlanLineItem.getIstdTotlPcksQuan()))
                    || BaseUtil.isNull(trspPlanLineItem.getCnsgPrtyHeadOffId())
                    || BaseUtil.isNull(trspPlanLineItem.getCnsgPrtyBrncOffId())) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
                }
                CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(
                    user.getCompanyId());
                ShipperOperator shipperOperator = shipperOperatorRepository.findByShipperOperatorId(
                    YAMATO_DEFAULT.SHIPPER_ID);
                trspPlanLineItem.setTrspRqrPrtyHeadOffId(carrierOperator.getOperatorCode());
                trspPlanLineItem.setTrspRqrPrtyBrncOffId(carrierOperator.getOperatorCode());
                trspPlanLineItem.setCnsgPrtyHeadOffId(shipperOperator.getOperatorCode());
                trspPlanLineItem.setCnsgPrtyBrncOffId(shipperOperator.getOperatorCode());

                TrspPlanLineItem trspPlanLineItemExist = trspPlanLineItemRepository.findByOperatorIdAndTrspInstructionIdAndServiceNoAndServiceStrtDateAndCnsgPrtyHeadOffIdAndCnsgPrtyBrncOffIdAndTrspRqrPrtyHeadOffIdAndTrspRqrPrtyBrncOffId(
                    user.getCompanyId(), trspPlanLineItem.getTrspInstructionId(),
                    trspPlanLineItem.getServiceNo(), trspPlanLineItem.getServiceStrtDate(),
                    trspPlanLineItem.getCnsgPrtyHeadOffId(), trspPlanLineItem.getCnsgPrtyBrncOffId(),
                    trspPlanLineItem.getTrspRqrPrtyHeadOffId(), trspPlanLineItem.getTrspRqrPrtyBrncOffId()
                );
                if (trspPlanLineItemExist != null) {
                    updateTrspPlan(dto, trspPlanLineItem, trspPlanLineItemExist, user);
                } else {
                    trspPlanLineItem.setOperatorId(user.getCompanyId());
                    trspPlanLineItem.setTrspPlanMsgInfoId(trspPlanMsgInfoId);
                    trspPlanLineItem = trspPlanLineItemRepository.save(trspPlanLineItem);
                    if (null != dto.getCnsLineItem()) {
                        for (CnsLineItemDTO cnsLineItemDTO : dto.getCnsLineItem()) {
                            CnsLineItem cnsLineItem = cnsLineItemMapper.toEntity(cnsLineItemDTO);
                            if (null != cnsLineItem) {
                                cnsLineItem.setOperatorId(user.getCompanyId());
                                cnsLineItem.setTrspPlanLineItem(trspPlanLineItem);
                                Long transOrderId = insertTransOrder(trspPlanLineItem, dto.getShipFromPrty(),
                                    dto.getShipToPrty());
                                cnsLineItemRepository.save(cnsLineItem);
                                if (transOrderId != null) {
                                    insertCnsLineItemByDate(trspPlanLineItem, cnsLineItem, transOrderId,
                                        dto.getShipFromPrty(), dto.getShipToPrty());
                                }
                                cnsLineItemList.add(cnsLineItem);
                            }
                        }
                    }
                    if (null != dto.getShipFromPrty()) {
                        for (ShipFromPrtyDTO shipFromPrtyDTO : dto.getShipFromPrty()) {
                            shipFromPrtyDTO.setShipFromPstlAdrsLineOneTxt(
                                BaseUtil.getGlnDefaultYamato(shipFromPrtyDTO.getShipFromPstlAdrsLineOneTxt(), true));

                            ShipFromPrty shipFromPrty = shipFromPrtyMapper.toEntity(shipFromPrtyDTO);
                            ShipFromPrtyRqrm shipFromPrtyRqrm = shipFromPrtyRqrmMapper.toEntity(
                                shipFromPrtyDTO.getShipFromPrtyRqrm());
                            if (null != shipFromPrty) {
                                shipFromPrty.setOperatorId(user.getCompanyId());
                                shipFromPrty.setTrspPlanLineItem(trspPlanLineItem);
                                shipFromPrty = shipFromPrtyRepository.save(shipFromPrty);
                            }
                            if (null != shipFromPrtyRqrm) {
                                shipFromPrtyRqrm.setOperatorId(user.getCompanyId());
                                assert shipFromPrty != null;
                                shipFromPrtyRqrm.setShipFromPrtyId(shipFromPrty.getId());
                                shipFromPrtyRqrmList.add(shipFromPrtyRqrm);
                            }
                            if (null != shipFromPrtyDTO.getShipCutOffInfo() && !shipFromPrtyDTO.getShipCutOffInfo()
                                .isEmpty()) {
                                assert shipFromPrty != null;
                                Long shipFromId = shipFromPrty.getId();
                                String operatorId = user.getCompanyId();
                                List<ShipCutOffInfo> shipCutOffInfos = shipFromPrtyDTO.getShipCutOffInfo().stream().map(
                                    cutOffDto -> {
                                        ShipCutOffInfo shipCutOffInfo = shipCutOffInfoMapper.toEntity(cutOffDto);
                                        shipCutOffInfo.setShipFromPrtyId(shipFromId);
                                        shipCutOffInfo.setOperatorId(operatorId);
                                        return shipCutOffInfo;
                                    }).toList();
                                shipCutOffInfoList.addAll(shipCutOffInfos);
                            }
                        }
                    }
                    if (null != dto.getShipToPrty()) {
                        for (ShipToPrtyDTO shipToPrtyDTO : dto.getShipToPrty()) {
                            shipToPrtyDTO.setShipToPstlAdrsLineOneTxt(
                                BaseUtil.getGlnDefaultYamato(shipToPrtyDTO.getShipToPstlAdrsLineOneTxt(), false));

                            ShipToPrty shipToPrty = shipToPrtyMapper.toEntity(shipToPrtyDTO);
                            ShipToPrtyRqrm shipToPrtyRqrm = shipToPrtyRqrmMapper.toEntity(
                                shipToPrtyDTO.getShipToPrtyRqrm());
                            if (null != shipToPrty) {
                                shipToPrty.setOperatorId(user.getCompanyId());
                                shipToPrty.setTrspPlanLineItem(trspPlanLineItem);
                                shipToPrty = shipToPrtyRepository.save(shipToPrty);
                            }
                            if (null != shipToPrtyRqrm) {
                                shipToPrtyRqrm.setOperatorId(user.getCompanyId());
                                assert shipToPrty != null;
                                shipToPrtyRqrm.setShipToPrtyId(shipToPrty.getId());
                                shipToPrtyRqrmList.add(shipToPrtyRqrm);
                            }
                            if (null != shipToPrtyDTO.getShipFreeTimeInfo() && !shipToPrtyDTO.getShipFreeTimeInfo()
                                .isEmpty()) {
                                assert shipToPrty != null;
                                Long shipToPrtyId = shipToPrty.getId();
                                String operatorId = user.getCompanyId();
                                List<ShipFreeTimeInfo> shipFreeTimeInfos = shipToPrtyDTO.getShipFreeTimeInfo().stream()
                                    .map(
                                        freeTimeDto -> {
                                            ShipFreeTimeInfo shipFreeTimeInfo = shipFreeTimeInfoMapper.toEntity(
                                                freeTimeDto);
                                            shipFreeTimeInfo.setShipToPrtyId(shipToPrtyId);
                                            shipFreeTimeInfo.setOperatorId(operatorId);
                                            return shipFreeTimeInfo;
                                        }).toList();
                                shipFreeTimeInfoList.addAll(shipFreeTimeInfos);
                            }
                        }
                    }
                }
            }
        }
        if (!shipFromPrtyRqrmList.isEmpty()) {
            shipFromPrtyRqrmRepository.saveAll(shipFromPrtyRqrmList);
        }
        if (!shipToPrtyRqrmList.isEmpty()) {
            shipToPrtyRqrmRepository.saveAll(shipToPrtyRqrmList);
        }
        if (!shipCutOffInfoList.isEmpty()) {
            shipCutOffInfoRepository.saveAll(shipCutOffInfoList);
        }
        if (!shipFreeTimeInfoList.isEmpty()) {
            shipFreeTimeInfoRepository.saveAll(shipFreeTimeInfoList);
        }
    }

    /**
     * 更新輸送計画.<BR>
     *
     * @param dto              輸送計画リクエストDTO
     * @param trspPlanLineItem 輸送計画行項目
     * @param trspExist        既存輸送計画行項目
     * @param user             ユーザー
     */
    @Transactional
    public void updateTrspPlan(TrspPlanLineItemDTO dto, TrspPlanLineItem trspPlanLineItem, TrspPlanLineItem trspExist,
        User user) {

        TrspPlanLineItem itemUpdate = trspPlanLineItem;
        itemUpdate.setId(trspExist.getId());
        itemUpdate.setOperatorId(trspExist.getOperatorId());
        trspPlanLineItem = trspPlanLineItemRepository.save(itemUpdate);
        //Delete recode in 3 table relation
        List<Long> trspPlanLineItemIds = Collections.singletonList(trspPlanLineItem.getId());
        cnsLineItemRepository.deleteAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        List<ShipFromPrty> shipFromPrties = shipFromPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        if (null != shipFromPrties) {
            List<Long> shipFromPrtyIds = shipFromPrties.stream().map(ShipFromPrty::getId).toList();
            shipFromPrtyRepository.deleteAll(shipFromPrties);
            shipFromPrtyRqrmRepository.deleteAllByShipFromPrtyIdIn(shipFromPrtyIds);
            shipCutOffInfoRepository.deleteAllByShipFromPrtyIdIn(shipFromPrtyIds);
        }
        List<ShipToPrty> shipToPrties = shipToPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        if (null != shipToPrties) {
            List<Long> shipToPrtyIds = shipToPrties.stream().map(ShipToPrty::getId).toList();
            shipToPrtyRepository.deleteAll(shipToPrties);
            shipToPrtyRqrmRepository.deleteAllByShipToPrtyIdIn(shipToPrtyIds);
            shipFreeTimeInfoRepository.deleteAllByShipToPrtyIdIn(shipToPrtyIds);
        }

        //update table relation
        List<CnsLineItem> cnsLineItemList = new ArrayList<>();
        List<ShipFromPrtyRqrm> shipFromPrtyRqrmList = new ArrayList<>();
        List<ShipToPrtyRqrm> shipToPrtyRqrmList = new ArrayList<>();
        List<ShipCutOffInfo> shipCutOffInfoList = new ArrayList<>();
        List<ShipFreeTimeInfo> shipFreeTimeInfoList = new ArrayList<>();
        if (null != dto.getCnsLineItem()) {
            for (CnsLineItemDTO cnsLineItemDTO : dto.getCnsLineItem()) {
                CnsLineItem cnsLineItem = cnsLineItemMapper.toEntity(cnsLineItemDTO);
                if (null != cnsLineItem) {
                    cnsLineItem.setOperatorId(user.getCompanyId());
                    cnsLineItem.setTrspPlanLineItem(trspPlanLineItem);
                    Long transOrderId = insertTransOrder(trspPlanLineItem, dto.getShipFromPrty(), dto.getShipToPrty());
                    cnsLineItemRepository.save(cnsLineItem);
                    if (transOrderId != null) {
                        insertCnsLineItemByDate(trspPlanLineItem, cnsLineItem, transOrderId, dto.getShipFromPrty(),
                            dto.getShipToPrty());
                    }
                    cnsLineItemList.add(cnsLineItem);
                }
            }
        }
        if (null != dto.getShipFromPrty()) {
            for (ShipFromPrtyDTO shipFromPrtyDTO : dto.getShipFromPrty()) {
                //Set default value for ship_from_prty.ship_from_pstl_adrs_line_one_txt
                shipFromPrtyDTO.setShipFromPstlAdrsLineOneTxt(
                    BaseUtil.getGlnDefaultYamato(shipFromPrtyDTO.getShipFromPstlAdrsLineOneTxt(), true));

                ShipFromPrty shipFromPrty = shipFromPrtyMapper.toEntity(shipFromPrtyDTO);
                ShipFromPrtyRqrm shipFromPrtyRqrm = shipFromPrtyRqrmMapper.toEntity(
                    shipFromPrtyDTO.getShipFromPrtyRqrm());
                if (null != shipFromPrty) {
                    shipFromPrty.setOperatorId(user.getCompanyId());
                    shipFromPrty.setTrspPlanLineItem(trspPlanLineItem);
                    shipFromPrty = shipFromPrtyRepository.save(shipFromPrty);
                }
                if (null != shipFromPrtyRqrm) {
                    shipFromPrtyRqrm.setOperatorId(user.getCompanyId());
                    assert shipFromPrty != null;
                    shipFromPrtyRqrm.setShipFromPrtyId(shipFromPrty.getId());
                    shipFromPrtyRqrmList.add(shipFromPrtyRqrm);
                }
                if (null != shipFromPrtyDTO.getShipCutOffInfo() && !shipFromPrtyDTO.getShipCutOffInfo().isEmpty()) {
                    assert shipFromPrty != null;
                    Long shipFromId = shipFromPrty.getId();
                    String operatorId = user.getCompanyId();
                    List<ShipCutOffInfo> shipCutOffInfos = shipFromPrtyDTO.getShipCutOffInfo().stream().map(
                        cutOffDto -> {
                            ShipCutOffInfo shipCutOffInfo = shipCutOffInfoMapper.toEntity(cutOffDto);
                            shipCutOffInfo.setShipFromPrtyId(shipFromId);
                            shipCutOffInfo.setOperatorId(operatorId);
                            return shipCutOffInfo;
                        }).toList();
                    shipCutOffInfoList.addAll(shipCutOffInfos);
                }
            }
        }
        if (null != dto.getShipToPrty()) {
            for (ShipToPrtyDTO shipToPrtyDTO : dto.getShipToPrty()) {
                shipToPrtyDTO.setShipToPstlAdrsLineOneTxt(
                    BaseUtil.getGlnDefaultYamato(shipToPrtyDTO.getShipToPstlAdrsLineOneTxt(), false));

                ShipToPrty shipToPrty = shipToPrtyMapper.toEntity(shipToPrtyDTO);
                ShipToPrtyRqrm shipToPrtyRqrm = shipToPrtyRqrmMapper.toEntity(shipToPrtyDTO.getShipToPrtyRqrm());
                if (null != shipToPrty) {
                    shipToPrty.setOperatorId(user.getCompanyId());
                    shipToPrty.setTrspPlanLineItem(trspPlanLineItem);
                    shipToPrty = shipToPrtyRepository.save(shipToPrty);
                }
                if (null != shipToPrtyRqrm) {
                    shipToPrtyRqrm.setOperatorId(user.getCompanyId());
                    assert shipToPrty != null;
                    shipToPrtyRqrm.setShipToPrtyId(shipToPrty.getId());
                    shipToPrtyRqrmList.add(shipToPrtyRqrm);
                }
                if (null != shipToPrtyDTO.getShipFreeTimeInfo() && !shipToPrtyDTO.getShipFreeTimeInfo().isEmpty()) {
                    assert shipToPrty != null;
                    Long shipToPrtyId = shipToPrty.getId();
                    String operatorId = user.getCompanyId();
                    List<ShipFreeTimeInfo> shipFreeTimeInfos = shipToPrtyDTO.getShipFreeTimeInfo().stream().map(
                        freeTimeDto -> {
                            ShipFreeTimeInfo shipFreeTimeInfo = shipFreeTimeInfoMapper.toEntity(freeTimeDto);
                            shipFreeTimeInfo.setShipToPrtyId(shipToPrtyId);
                            shipFreeTimeInfo.setOperatorId(operatorId);
                            return shipFreeTimeInfo;
                        }).toList();
                    shipFreeTimeInfoList.addAll(shipFreeTimeInfos);
                }
            }
        }
        if (!shipFromPrtyRqrmList.isEmpty()) {
            shipFromPrtyRqrmRepository.saveAll(shipFromPrtyRqrmList);
        }
        if (!shipToPrtyRqrmList.isEmpty()) {
            shipToPrtyRqrmRepository.saveAll(shipToPrtyRqrmList);
        }
        if (!shipCutOffInfoList.isEmpty()) {
            shipCutOffInfoRepository.saveAll(shipCutOffInfoList);
        }
        if (!shipFreeTimeInfoList.isEmpty()) {
            shipFreeTimeInfoRepository.saveAll(shipFreeTimeInfoList);
        }
    }

    /**
     * 輸送計画を削除.<BR>
     *
     * @param request   輸送計画リクエストDTO
     * @param xTracking トラッキングID
     */
    @Override
    @Transactional
    public void deleteTrspPlan(TrspPlanLineItemDeleteRequest request, String xTracking) {
        insertPlanMsgInfo(trspPlanMsgInfoMapper.toEntity(request.getMsgInfo(), request.getTrspPlan()), xTracking);
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }
        List<TrspPlanLineItem> trspPlanLineItemRequestList = new ArrayList<>();
        for (TrspPlanLineItemDeleteDTO dto : request.getTrspPlanLineItem()) {
            TrspPlanLineItem trspPlanLineItem = trspPlanLineItemDeleteMapper.toEntity(dto.getTrspIsr(),
                dto.getTrspSrvc(),
                dto.getTrspVehicleTrms(), dto.getDelInfo(),
                dto.getCns(), dto.getCnsgPrty(), dto.getTrspRqrPrty(), dto.getCneePrty(), dto.getLogsSrvcPrv(),
                dto.getRoadCarr(),
                dto.getFretClimToPrty());
            if (BaseUtil.isNull(trspPlanLineItem.getTrspInstructionId())
                || BaseUtil.isNull(trspPlanLineItem.getServiceNo())
                || BaseUtil.isNull(trspPlanLineItem.getServiceName())
                || BaseUtil.isNull(String.valueOf(trspPlanLineItem.getServiceStrtDate()))
                || BaseUtil.isNull(String.valueOf(trspPlanLineItem.getServiceStrtTime()))
                || BaseUtil.isNull(String.valueOf(trspPlanLineItem.getFreightRate()))
                || BaseUtil.isNull(String.valueOf(trspPlanLineItem.getIstdTotlPcksQuan()))
                || BaseUtil.isNull(trspPlanLineItem.getCnsgPrtyHeadOffId())
                || BaseUtil.isNull(trspPlanLineItem.getCnsgPrtyBrncOffId())
                || BaseUtil.isNull(trspPlanLineItem.getTrspRqrPrtyHeadOffId())
                || BaseUtil.isNull(trspPlanLineItem.getTrspRqrPrtyBrncOffId())) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
            }

            CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(user.getCompanyId());
            ShipperOperator shipperOperator = shipperOperatorRepository.findByShipperOperatorId(
                YAMATO_DEFAULT.SHIPPER_ID);
            trspPlanLineItem.setTrspRqrPrtyHeadOffId(carrierOperator.getOperatorCode());
            trspPlanLineItem.setTrspRqrPrtyBrncOffId(carrierOperator.getOperatorCode());
            trspPlanLineItem.setCnsgPrtyHeadOffId(shipperOperator.getOperatorCode());
            trspPlanLineItem.setCnsgPrtyBrncOffId(shipperOperator.getOperatorCode());

            TrspPlanLineItem trspPlanLineItemExist = trspPlanLineItemRepository.findByOperatorIdAndTrspInstructionIdAndServiceNoAndServiceStrtDateAndCnsgPrtyHeadOffIdAndCnsgPrtyBrncOffIdAndTrspRqrPrtyHeadOffIdAndTrspRqrPrtyBrncOffId(
                user.getCompanyId(), trspPlanLineItem.getTrspInstructionId(),
                trspPlanLineItem.getServiceNo(), trspPlanLineItem.getServiceStrtDate(),
                trspPlanLineItem.getCnsgPrtyHeadOffId(), trspPlanLineItem.getCnsgPrtyBrncOffId(),
                trspPlanLineItem.getTrspRqrPrtyHeadOffId(), trspPlanLineItem.getTrspRqrPrtyBrncOffId()
            );
            if (trspPlanLineItemExist == null) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND,
                        SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_NOT_FOUND)));
            }
            if (null != trspPlanLineItem) {
                trspPlanLineItemRequestList.add(trspPlanLineItemExist);
            }
        }

        List<Long> trspPlanLineItemIds = trspPlanLineItemRequestList.stream().map(TrspPlanLineItem::getId).toList();
        List<ShipFromPrty> shipFromPrties = shipFromPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        if (null != shipFromPrties) {
            List<Long> shipFromPrtyIds = shipFromPrties.stream().map(ShipFromPrty::getId).toList();
            shipFromPrtyRqrmRepository.deleteAllByShipFromPrtyIdIn(shipFromPrtyIds);
            shipCutOffInfoRepository.deleteAllByShipFromPrtyIdIn(shipFromPrtyIds);
            shipFromPrtyRepository.deleteAll(shipFromPrties);
        }
        List<ShipToPrty> shipToPrties = shipToPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        if (null != shipToPrties) {
            List<Long> shipToPrtyIds = shipToPrties.stream().map(ShipToPrty::getId).toList();
            shipToPrtyRqrmRepository.deleteAllByShipToPrtyIdIn(shipToPrtyIds);
            shipFreeTimeInfoRepository.deleteAllByShipToPrtyIdIn(shipToPrtyIds);
            shipToPrtyRepository.deleteAll(shipToPrties);
        }
        cnsLineItemRepository.deleteAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        trspPlanLineItemRepository.deleteAll(trspPlanLineItemRequestList);
    }

    /**
     * 輸送計画を削除するメソッド
     *
     * @param trspInstructionId    輸送指示ID
     * @param serviceNo            サービス番号
     * @param serviceStrtDate      サービス開始日
     * @param cnsgPrtyHeadOffId    荷主本社ID
     * @param cnsgPrtyBrncOffId    荷主支店ID
     * @param trspRqrPrtyHeadOffId 輸送要求者本社ID
     * @param trspRqrPrtyBrncOffId 輸送要求者支店ID
     */
    @Override
    @Transactional
    public void deleteTrspPlanYamato(String trspInstructionId, String serviceNo, String serviceStrtDate,
        String cnsgPrtyHeadOffId, String cnsgPrtyBrncOffId, String trspRqrPrtyHeadOffId, String trspRqrPrtyBrncOffId) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }
        if (BaseUtil.isNull(trspInstructionId)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL), ParamConstant.TRSP_INSTRUCTION_ID));
        }
        if (BaseUtil.isNull(serviceNo)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL), ParamConstant.SERVICE_NO));
        }
        if (BaseUtil.isNull(serviceStrtDate)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL), ParamConstant.SERVICE_STRT_DATE));
        }
        try {
            DateTimeMapper.stringToLocalDate(serviceStrtDate);
        } catch (Exception e) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_DATE_FORMAT), ParamConstant.SERVICE_STRT_DATE,
                    DATE_TIME_FORMAT.DATE_FORMAT));
        }
        if (BaseUtil.isNull(cnsgPrtyHeadOffId)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL), ParamConstant.CNSG_PRTY_HEAD_OFF_ID));
        }
        if (BaseUtil.isNull(cnsgPrtyBrncOffId)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL), ParamConstant.CNSG_PRTY_BRNC_OFF_ID));
        }
        if (BaseUtil.isNull(trspRqrPrtyHeadOffId)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL), ParamConstant.TRSP_RQR_PRTY_HEAD_OFF_ID));
        }
        if (BaseUtil.isNull(trspRqrPrtyBrncOffId)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL), ParamConstant.TRSP_RQR_PRTY_BRNC_OFF_ID));
        }
        List<TrspPlanLineItem> trspPlanLineItemRequestList = new ArrayList<>();
        CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(user.getCompanyId());
        ShipperOperator shipperOperator = shipperOperatorRepository.findByShipperOperatorId(YAMATO_DEFAULT.SHIPPER_ID);

        TrspPlanLineItem trspPlanLineItemExist = trspPlanLineItemRepository.findByOperatorIdAndTrspInstructionIdAndServiceNoAndServiceStrtDateAndCnsgPrtyHeadOffIdAndCnsgPrtyBrncOffIdAndTrspRqrPrtyHeadOffIdAndTrspRqrPrtyBrncOffId(
            user.getCompanyId(), trspInstructionId,
            serviceNo, DateTimeMapper.stringToLocalDate(serviceStrtDate),
            shipperOperator.getOperatorCode(), shipperOperator.getOperatorCode(),
            carrierOperator.getOperatorCode(), carrierOperator.getOperatorCode()
        );
        if (trspPlanLineItemExist == null) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_NOT_FOUND)));
        }
        trspPlanLineItemRequestList.add(trspPlanLineItemExist);

        List<Long> trspPlanLineItemIds = new ArrayList<>();
        for (TrspPlanLineItem item : trspPlanLineItemRequestList) {
            trspPlanLineItemIds.add(item.getId());
        }
        List<ShipFromPrty> shipFromPrties = shipFromPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        if (shipFromPrties != null && !shipFromPrties.isEmpty()) {
            List<Long> shipFromPrtyIds = shipFromPrties.stream().map(ShipFromPrty::getId).toList();
            shipFromPrtyRqrmRepository.deleteAllByShipFromPrtyIdIn(shipFromPrtyIds);
            shipCutOffInfoRepository.deleteAllByShipFromPrtyIdIn(shipFromPrtyIds);
            shipFromPrtyRepository.deleteAll(shipFromPrties);
        }
        List<ShipToPrty> shipToPrties = shipToPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        if (shipToPrties != null && !shipToPrties.isEmpty()) {
            List<Long> shipToPrtyIds = shipToPrties.stream().map(ShipToPrty::getId).toList();
            shipToPrtyRqrmRepository.deleteAllByShipToPrtyIdIn(shipToPrtyIds);
            shipFreeTimeInfoRepository.deleteAllByShipToPrtyIdIn(shipToPrtyIds);
            shipToPrtyRepository.deleteAll(shipToPrties);
        }
        cnsLineItemRepository.deleteAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        trspPlanLineItemRepository.deleteAll(trspPlanLineItemRequestList);
    }

    /**
     * 輸送計画を検索.<BR>
     *
     * @param request 輸送計画リクエストDTO
     * @return 輸送計画レスポンスDTO
     */
    @Override
    public CommonResponseDTO searchTrspPlan(TrspPlanLineItemSearchRequest request) {
        List<TrspPlanLineItem> trspPlanLineItemList = trspPlanLineItemRepository.searchTransportPlanItem(request);
        if (null == trspPlanLineItemList) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }
        if (trspPlanLineItemList.isEmpty()) {
            CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
            commonResponseDTO.setDataModelType(DataModelType.TEST_1);
            commonResponseDTO.setAttribute(new TrspPlanLineItemSearchResponse());
            return commonResponseDTO;
        }

        List<Long> trspPlanLineItemIds = trspPlanLineItemList.stream().map(TrspPlanLineItem::getId).toList();
        List<CnsLineItem> cnsLineItemList = cnsLineItemRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        List<ShipFromPrty> shipFromPrties = shipFromPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        List<ShipToPrty> shipToPrties = shipToPrtyRepository.findAllByTrspPlanLineItem_IdIn(trspPlanLineItemIds);
        List<ShipFromPrtyRqrm> shipFromPrtyRqrmList = new ArrayList<>();
        List<ShipToPrtyRqrm> shipToPrtyRqrmList = new ArrayList<>();
        List<ShipCutOffInfo> shipCutOffInfoList = new ArrayList<>();
        List<ShipFreeTimeInfo> shipFreeTimeInfoList = new ArrayList<>();

        if (null != shipFromPrties) {
            List<Long> shipFromPrtyIds = shipFromPrties.stream().map(ShipFromPrty::getId).toList();
            shipFromPrtyRqrmList = shipFromPrtyRqrmRepository.findAllByShipFromPrtyIdIn(shipFromPrtyIds);
            shipCutOffInfoList = shipCutOffInfoRepository.findAllByShipFromPrtyIdIn(shipFromPrtyIds);
        }
        if (null != shipToPrties) {
            List<Long> shipToPrtyIds = shipToPrties.stream().map(ShipToPrty::getId).toList();
            shipToPrtyRqrmList = shipToPrtyRqrmRepository.findAllByShipToPrtyIdIn(shipToPrtyIds);
            shipFreeTimeInfoList = shipFreeTimeInfoRepository.findAllByShipToPrtyIdIn(shipToPrtyIds);
        }
        Map<Long, List<CnsLineItem>> cnsLineItemMap = new HashMap<>();
        Map<Long, List<ShipFromPrty>> shipFromPrtyMap = new HashMap<>();
        Map<Long, List<ShipToPrty>> shipToPrtyMap = new HashMap<>();
        Map<Long, ShipFromPrtyRqrm> shipFromPrtyRqrmMap = new HashMap<>();
        Map<Long, ShipToPrtyRqrm> shipToPrtyRqrmMap = new HashMap<>();
        Map<Long, List<ShipCutOffInfo>> shipCutOffInfoMap = new HashMap<>();
        Map<Long, List<ShipFreeTimeInfo>> shipFreeTimeInfoMap = new HashMap<>();

        if (null != cnsLineItemList) {
            cnsLineItemMap = cnsLineItemList.stream()
                .collect(Collectors.groupingBy(CnsLineItem::getTrspPlanLineItemId));
        }
        if (null != shipFromPrties) {
            shipFromPrtyMap = shipFromPrties.stream()
                .collect(Collectors.groupingBy(ShipFromPrty::getTrspPlanLineItemId));
        }
        if (null != shipToPrties) {
            shipToPrtyMap = shipToPrties.stream().collect(Collectors.groupingBy(ShipToPrty::getTrspPlanLineItemId));
        }
        if (null != shipFromPrtyRqrmList) {
            shipFromPrtyRqrmMap = shipFromPrtyRqrmList.stream()
                .collect(Collectors.toMap(ShipFromPrtyRqrm::getShipFromPrtyId, shipFromPrtyRqrm -> shipFromPrtyRqrm));
        }
        if (null != shipToPrtyRqrmList) {
            shipToPrtyRqrmMap = shipToPrtyRqrmList.stream()
                .collect(Collectors.toMap(ShipToPrtyRqrm::getShipToPrtyId, shipToPrtyRqrm -> shipToPrtyRqrm));
        }
        if (null != shipCutOffInfoList) {
            shipCutOffInfoMap = shipCutOffInfoList.stream()
                .collect(Collectors.groupingBy(ShipCutOffInfo::getShipFromPrtyId));
        }
        if (null != shipFreeTimeInfoList) {
            shipFreeTimeInfoMap = shipFreeTimeInfoList.stream()
                .collect(Collectors.groupingBy(ShipFreeTimeInfo::getShipToPrtyId));
        }
        TrspPlanLineItemSearchResponse response = new TrspPlanLineItemSearchResponse();
        response.setMsgInfo(null);
        response.setTrspPlan(null);
        List<TrspPlanLineItemDTO> trspPlanLineItemDTOList = new ArrayList<>();
        for (TrspPlanLineItem trspPlanLineItem : trspPlanLineItemList) {
            TrspPlanLineItemDTO trspPlanLineItemDTO = new TrspPlanLineItemDTO();
            trspPlanLineItemDTO.setTrspIsr(trspPlanLineItemMapper.toTrspIsrDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setTrspSrvc(trspPlanLineItemMapper.toTrspSrvcDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setTrspVehicleTrms(
                trspPlanLineItemMapper.toTrspVehicleTrmsDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setDelInfo(trspPlanLineItemMapper.toDelInfoDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setCns(trspPlanLineItemMapper.toCnsDTO(trspPlanLineItem));
            if (null != cnsLineItemMap.get(trspPlanLineItem.getId())) {
                List<CnsLineItemDTO> cnsLineItemDTOList = cnsLineItemMap.get(trspPlanLineItem.getId()).stream()
                    .map(cnsLineItemMapper::toDTO).toList();
                trspPlanLineItemDTO.setCnsLineItem(cnsLineItemDTOList);
            }
            trspPlanLineItemDTO.setCnsgPrty(trspPlanLineItemMapper.toCnsgPrtyDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setTrspRqrPrty(trspPlanLineItemMapper.toTrspRqrPrtyDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setCneePrty(trspPlanLineItemMapper.toCneePrtyDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setLogsSrvcPrv(trspPlanLineItemMapper.toLogsSrvcPrvDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setRoadCarr(trspPlanLineItemMapper.toRoadCarrDTO(trspPlanLineItem));
            trspPlanLineItemDTO.setFretClimToPrty(trspPlanLineItemMapper.toFretClimToPrtyDTO(trspPlanLineItem));
            List<ShipFromPrtyDTO> shipFromPrtyDTOList = new ArrayList<>();
            List<ShipToPrtyDTO> shipToPrtyDTOList = new ArrayList<>();
            if (null != shipFromPrtyMap.get(trspPlanLineItem.getId())) {
                for (ShipFromPrty shipFromPrty : shipFromPrtyMap.get(trspPlanLineItem.getId())) {
                    ShipFromPrtyDTO shipFromPrtyDTO;
                    shipFromPrtyDTO = shipFromPrtyMapper.toDTO(shipFromPrty);
                    if (null != shipFromPrtyRqrmMap.get(shipFromPrty.getId())) {
                        ShipFromPrtyRqrmDTO shipFromPrtyRqrmDTO = shipFromPrtyRqrmMapper.toDTO(
                            shipFromPrtyRqrmMap.get(shipFromPrty.getId()));
                        if (null != shipFromPrtyDTO && (null != shipFromPrtyRqrmDTO)) {
                            shipFromPrtyDTO.setShipFromPrtyRqrm(shipFromPrtyRqrmDTO);
                        }
                    }
                    if (null != shipCutOffInfoMap.get(shipFromPrty.getId())) {
                        List<ShipCutOffInfoDTO> listDto = shipCutOffInfoMap.get(shipFromPrty.getId()).stream()
                            .map(shipCutOffInfoMapper::toDTO).toList();
                        if (null != shipFromPrtyDTO) {
                            shipFromPrtyDTO.setShipCutOffInfo(listDto);
                        }
                    }
                    shipFromPrtyDTOList.add(shipFromPrtyDTO);
                }
            }
            if (null != shipToPrtyMap.get(trspPlanLineItem.getId())) {
                for (ShipToPrty shipToPrty : shipToPrtyMap.get(trspPlanLineItem.getId())) {
                    ShipToPrtyDTO shipToPrtyDTO = shipToPrtyMapper.toDTO(shipToPrty);
                    if (null != shipToPrtyRqrmMap.get(shipToPrty.getId())) {
                        ShipToPrtyRqrmDTO shiptoPrtyRqrmDTO = shipToPrtyRqrmMapper.toDTO(
                            shipToPrtyRqrmMap.get(shipToPrty.getId()));
                        if (null != shipToPrtyDTO) {
                            if (null != shiptoPrtyRqrmDTO) {
                                shipToPrtyDTO.setShipToPrtyRqrm(shiptoPrtyRqrmDTO);
                            }
                        }
                    }
                    if (null != shipFreeTimeInfoMap.get(shipToPrty.getId())) {
                        List<ShipFreeTimeInfoDTO> listDto = shipFreeTimeInfoMap.get(shipToPrty.getId()).stream()
                            .map(shipFreeTimeInfoMapper::toDTO).toList();
                        if (null != shipToPrtyDTO) {
                            if (null != listDto) {
                                shipToPrtyDTO.setShipFreeTimeInfo(listDto);
                            }
                        }
                    }
                    shipToPrtyDTOList.add(shipToPrtyDTO);
                }
            }
            trspPlanLineItemDTO.setShipFromPrty(shipFromPrtyDTOList);
            trspPlanLineItemDTO.setShipToPrty(shipToPrtyDTOList);
            trspPlanLineItemDTOList.add(trspPlanLineItemDTO);
        }
        response.setTrspPlanLineItem(trspPlanLineItemDTOList);
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        responseDTO.setDataModelType(DataModelType.TEST_1);
        responseDTO.setAttribute(response);

        return responseDTO;
    }

    /**
     * メッセージを挿入 プラン情報.<BR>
     *
     * @param planMsgInfo プラン情報
     * @param xTracking   トラッキングID
     * @return プラン情報
     */
    private TrspPlanMsgInfo insertPlanMsgInfo(TrspPlanMsgInfo planMsgInfo, String xTracking) {
        if (planMsgInfo == null) {
            return new TrspPlanMsgInfo();
        }
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }
        if (null != planMsgInfo) {
            if (!BaseUtil.isNull(planMsgInfo.getMsgFnStasCd()) && "3".equals(planMsgInfo.getMsgFnStasCd())
                && BaseUtil.isNull(planMsgInfo.getNoteDcptTxt())) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
            }
            planMsgInfo.setOperatorId(user.getCompanyId());
            planMsgInfo.setXTracking(!BaseUtil.isNull(xTracking) ? UUID.fromString(xTracking) : null);
            planMsgInfo = trspPlanMsgInfoRepository.save(planMsgInfo);
        }
        return planMsgInfo;
    }

    /**
     * 輸送計画を登録.<BR>
     *
     * @param trspPlanLineItem 輸送計画行項目
     * @param shipFromPrtyDTO  出発地プロパティDTO
     * @param shipToPrtyDTO    到着地プロパティDTO
     * @return 輸送計画ID
     */
    private Long insertTransOrder(TrspPlanLineItem trspPlanLineItem, List<ShipFromPrtyDTO> shipFromPrtyDTO,
        List<ShipToPrtyDTO> shipToPrtyDTO) {
        User user = userContext.getUser();
        CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(user.getCompanyId());
        ShipperOperator shipperOperator = shipperOperatorRepository.findByShipperOperatorId(YAMATO_DEFAULT.SHIPPER_ID);

        String sqlTransOrder =
            "INSERT INTO t_trans_order (trans_type, status, shipper_operator_id, shipper_operator_code, shipper_operator_name, "
                + "carrier_operator_id, carrier_operator_code, carrier_operator_name, req_cns_line_item_id, cns_line_item_by_date_id, "
                + "vehicle_avb_resource_id, vehicle_avb_resource_item_id, created_user, created_date, arrival_to, departure_from, "
                + "trailer_number, transport_date, service_name) "
                + "VALUES (:transType, :status, :shipperOperatorId, :shipperOperatorCode, :shipperOperatorName, "
                + ":carrierOperatorId, :carrierOperatorCode, :carrierOperatorName, :reqCnsLineItemId, :cnsLineItemByDateId, "
                + ":vehicleAvbResourceId, :vehicleAvbResourceItemId, :createdUser, :createdDate, :arrivalTo, :departureFrom, "
                + ":trailerNumber, :transportDate, :serviceName) RETURNING id";

        try {
            return (Long) entityManager.createNativeQuery(sqlTransOrder)
                .setParameter("transType", TransOrderId.SHIPPER_AND_CARRIER)
                .setParameter("status", Status.ORDER_CONTRACT)
                .setParameter("shipperOperatorId", shipperOperator.getId())
                .setParameter("shipperOperatorCode", shipperOperator.getOperatorCode())
                .setParameter("shipperOperatorName", shipperOperator.getOperatorName())
                .setParameter("carrierOperatorId", carrierOperator.getId())
                .setParameter("carrierOperatorCode", carrierOperator.getOperatorCode())
                .setParameter("carrierOperatorName", carrierOperator.getOperatorName())
                .setParameter("reqCnsLineItemId", 0)
                .setParameter("cnsLineItemByDateId", 0)
                .setParameter("vehicleAvbResourceId", 0)
                .setParameter("vehicleAvbResourceItemId", 0)
                .setParameter("createdUser", userContext.getUser().getUsername())
                .setParameter("createdDate", LocalDateTime.now())
                .setParameter("arrivalTo", parsePostalCode(getShipToPostalCode(shipToPrtyDTO)))
                .setParameter("departureFrom", parsePostalCode(getShipFromPostalCode(shipFromPrtyDTO)))
                .setParameter("trailerNumber", 0)
                .setParameter("transportDate", trspPlanLineItem.getServiceStrtDate())
                .setParameter("serviceName", trspPlanLineItem.getServiceName())
                .getSingleResult();
        } catch (NoResultException e) {
            log.error("Insert t_trans_order error: " + e);
            return null;
        }
    }

    /**
     * 輸送計画行項目を登録.<BR>
     *
     * @param trspPlanLineItem 輸送計画行項目
     * @param cnsLineItem      コンテナ行項目
     * @param transOrderId     輸送計画ID
     * @param shipFromPrtyDTO  出発地プロパティDTO
     * @param shipToPrtyDTO    到着地プロパティDTO
     */
    private void insertCnsLineItemByDate(TrspPlanLineItem trspPlanLineItem, CnsLineItem cnsLineItem, Long transOrderId,
        List<ShipFromPrtyDTO> shipFromPrtyDTO, List<ShipToPrtyDTO> shipToPrtyDTO) {
        User user = userContext.getUser();
        CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(user.getCompanyId());
        CnsLineItemByDate cnsLineItemByDate = new CnsLineItemByDate();
        cnsLineItemByDate.setTransType(TransOrderId.CARRIER_AND_CARRIER);
        cnsLineItemByDate.setTransOrderId(transOrderId);
        cnsLineItemByDate.setCnsLineItemId(cnsLineItem.getId());
        cnsLineItemByDate.setStatus(Status.MARKET);
        cnsLineItemByDate.setOperatorId(carrierOperator.getId());
        cnsLineItemByDate.setOperatorName(carrierOperator.getOperatorName());
        cnsLineItemByDate.setOperatorCode(carrierOperator.getOperatorCode());
        cnsLineItemByDate.setDepartureFrom(parsePostalCode(getShipFromPostalCode(shipFromPrtyDTO)));
        cnsLineItemByDate.setArrivalTo(parsePostalCode(getShipToPostalCode(shipToPrtyDTO)));
        cnsLineItemByDate.setTransportDate(trspPlanLineItem.getServiceStrtDate());
        cnsLineItemByDate.setProposePrice(trspPlanLineItem.getFreightRate());
        cnsLineItemByDate.setProposeDepartureTime(trspPlanLineItem.getServiceStrtTime());
        cnsLineItemByDate.setProposeArrivalTime(trspPlanLineItem.getServiceEndTime());

        cnsLineItemByDate.setPricePerUnit(trspPlanLineItem.getFreightRate());
        cnsLineItemByDate.setCollectionTimeFrom(trspPlanLineItem.getServiceStrtTime());
        cnsLineItemByDate.setCollectionTimeTo(trspPlanLineItem.getServiceEndTime());
        cnsLineItemByDate.setCollectionDate(trspPlanLineItem.getServiceStrtDate());

        cnsLineItemByDate.setTransportCode(cnsLineItem.getLineItemNumId());
        cnsLineItemByDate.setTransportName(cnsLineItem.getItemNameTxt());
        try {
            cnsLineItemByDate.setOuterPackageCode(getNumberDefault(cnsLineItem.getPckeFrmCd()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        cnsLineItemByDate.setWeight(trspPlanLineItem.getIstdTotlWeigMeas());
        cnsLineItemByDate.setIstdTollVolMeas(trspPlanLineItem.getIstdTotlVolMeas());
        cnsLineItemByDate.setVolUntCd(trspPlanLineItem.getVolUntCd());
        cnsLineItemByDate.setNumUntCd(trspPlanLineItem.getNumUntCd());
        cnsLineItemByDate.setWeigUntCd(trspPlanLineItem.getWeigUntCd());
        cnsLineItemByDate.setTransPlanId(trspPlanLineItem.getTrspInstructionId());
        cnsLineItemByDateRepository.save(cnsLineItemByDate);
    }

    /**
     * 出発地郵便番号を取得.<BR>
     *
     * @param shipFromPrtyDTO 出発地プロパティDTO
     * @return 出発地郵便番号
     */
    private String getShipFromPostalCode(List<ShipFromPrtyDTO> shipFromPrtyDTO) {
        if (shipFromPrtyDTO == null
            || shipFromPrtyDTO.isEmpty()) {
            return BaseUtil.getGlnDefaultYamato(null, true);
        }
        ShipFromPrtyDTO shipFrom = shipFromPrtyDTO.get(0);
        return BaseUtil.getGlnDefaultYamato(shipFrom.getShipFromPstlAdrsLineOneTxt(), true);
    }

    /**
     * 到着地郵便番号を取得.<BR>
     *
     * @param shipToPrtyDTO 到着地プロパティDTO
     * @return 到着地郵便番号
     */
    private String getShipToPostalCode(List<ShipToPrtyDTO> shipToPrtyDTO) {
        if (shipToPrtyDTO == null
            || shipToPrtyDTO.isEmpty()) {
            return BaseUtil.getGlnDefaultYamato(null, false);
        }
        ShipToPrtyDTO shipTo = shipToPrtyDTO.get(0);
        return BaseUtil.getGlnDefaultYamato(shipTo.getShipToPstlAdrsLineOneTxt(), false);
    }

    /**
     * 郵便番号をパース.<BR>
     *
     * @param postalCode 郵便番号
     * @return 郵便番号
     */
    private Long parsePostalCode(String postalCode) {
        try {
            return postalCode != null ? Long.valueOf(postalCode) : 0L;
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * デフォルト番号を取得.<BR>
     *
     * @param number 番号
     * @return デフォルト番号
     */
    public int getNumberDefault(String number) {
        List<String> defaultNumber = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        try {
            if (BaseUtil.isNull(number)) {
                return Integer.parseInt(defaultNumber.get(new Random().nextInt(defaultNumber.size())));
            }
            if (defaultNumber.contains(number)) {
                return Integer.parseInt(number);
            }
            return Integer.parseInt(defaultNumber.get(new Random().nextInt(defaultNumber.size())));
        } catch (Exception e) {
            return Integer.parseInt(defaultNumber.get(new Random().nextInt(defaultNumber.size())));
        }
    }
}
