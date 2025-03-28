package jp.co.nlj.ix.service.impl;

import com.next.logistic.authorization.UserContext;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jp.co.nlj.ix.domain.CnsLineItem;
import jp.co.nlj.ix.domain.ShipCutOffInfo;
import jp.co.nlj.ix.domain.ShipFreeTimeInfo;
import jp.co.nlj.ix.domain.ShipFromPrty;
import jp.co.nlj.ix.domain.ShipFromPrtyRqrm;
import jp.co.nlj.ix.domain.ShipToPrty;
import jp.co.nlj.ix.domain.ShipToPrtyRqrm;
import jp.co.nlj.ix.domain.TrspPlanLineItem;
import jp.co.nlj.ix.dto.trspPlanLineItem.CnsLineItemDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipFromPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipToPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.TrspPlanLineItemRequest;
import jp.co.nlj.ix.mapper.CnsLineItemMapper;
import jp.co.nlj.ix.mapper.ShipCutOffInfoMapper;
import jp.co.nlj.ix.mapper.ShipFreeTimeInfoMapper;
import jp.co.nlj.ix.mapper.ShipFromPrtyMapper;
import jp.co.nlj.ix.mapper.ShipFromPrtyRqrmMapper;
import jp.co.nlj.ix.mapper.ShipToPrtyMapper;
import jp.co.nlj.ix.mapper.ShipToPrtyRqrmMapper;
import jp.co.nlj.ix.mapper.TrspPlanLineItemMapper;
import jp.co.nlj.ix.repository.CnsLineItemRepository;
import jp.co.nlj.ix.repository.ShipCutOffInfoRepository;
import jp.co.nlj.ix.repository.ShipFreeTimeInfoRepository;
import jp.co.nlj.ix.repository.ShipFromPrtyRepository;
import jp.co.nlj.ix.repository.ShipFromPrtyRqrmRepository;
import jp.co.nlj.ix.repository.ShipToPrtyRepository;
import jp.co.nlj.ix.repository.ShipToPrtyRqrmRepository;
import jp.co.nlj.ix.repository.TrspPlanLineItemRepository;
import jp.co.nlj.ix.service.TrspPlanLineItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrspPlanLineItemServiceImpl implements TrspPlanLineItemService {

    private final TrspPlanLineItemRepository trspPlanLineItemRepository;

    private final CnsLineItemRepository cnsLineItemRepository;

    private final ShipFromPrtyRepository shipFromPrtyRepository;

    private final ShipFromPrtyRqrmRepository shipFromPrtyRqrmRepository;

    private final ShipToPrtyRepository shipToPrtyRepository;
    private final ShipToPrtyRqrmRepository shipToPrtyRqrmRepository;
    private final CnsLineItemMapper cnsLineItemMapper;
    private final ShipFromPrtyMapper shipFromPrtyMapper;
    private final ShipFromPrtyRqrmMapper shipFromPrtyRqrmMapper;
    private final ShipToPrtyMapper shipToPrtyMapper;
    private final ShipToPrtyRqrmMapper shipToPrtyRqrmMapper;
    private final ShipCutOffInfoMapper shipCutOffInfoMapper;
    private final ShipCutOffInfoRepository shipCutOffInfoRepository;
    private final ShipFreeTimeInfoRepository shipFreeTimeInfoRepository;
    private final ShipFreeTimeInfoMapper shipFreeTimeInfoMapper;
    private final TrspPlanLineItemMapper trspPlanLineItemMapper;
    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 輸送計画を更新する。
     *
     * @param request       輸送計画のリクエスト
     * @param instructionId 指示ID
     */
    @Transactional
    public void updateTrspPlan(TrspPlanLineItemRequest request, String instructionId) {
        TrspPlanLineItem itemUpdate = trspPlanLineItemMapper.toEntity(request.getTrspIsr(), request.getTrspSrvc(),
            request.getTrspVehicleTrms(), request.getDelInfo(), request.getCns(),
            request.getCnsgPrty(), request.getTrspRqrPrty(), request.getCneePrty(),
            request.getLogsSrvcPrv(), request.getRoadCarr(),
            request.getFretClimToPrty());
        TrspPlanLineItem trspExist = trspPlanLineItemRepository.findByTrspInstructionId(instructionId);
        itemUpdate.setId(trspExist.getId());
        itemUpdate.setOperatorId(trspExist.getOperatorId());
        TrspPlanLineItem trspPlanLineItem = trspPlanLineItemRepository.save(itemUpdate);
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
        if (null != request.getCnsLineItem()) {
            for (CnsLineItemDTO cnsLineItemDTO : request.getCnsLineItem()) {
                CnsLineItem cnsLineItem = cnsLineItemMapper.toEntity(cnsLineItemDTO);
                if (null != cnsLineItem) {
                    cnsLineItem.setTrspPlanLineItem(trspPlanLineItem);
                    cnsLineItemList.add(cnsLineItem);
                }
            }
        }
        if (null != request.getShipFromPrty()) {
            for (ShipFromPrtyDTO shipFromPrtyDTO : request.getShipFromPrty()) {
                ShipFromPrty shipFromPrty = shipFromPrtyMapper.toEntity(shipFromPrtyDTO);
                ShipFromPrtyRqrm shipFromPrtyRqrm = shipFromPrtyRqrmMapper.toEntity(
                    shipFromPrtyDTO.getShipFromPrtyRqrm());
                if (null != shipFromPrty) {
                    shipFromPrty.setTrspPlanLineItem(trspPlanLineItem);
                    shipFromPrty = shipFromPrtyRepository.save(shipFromPrty);
                }
                if (null != shipFromPrtyRqrm) {
                    assert shipFromPrty != null;
                    shipFromPrtyRqrm.setShipFromPrtyId(shipFromPrty.getId());
                    shipFromPrtyRqrmList.add(shipFromPrtyRqrm);
                }
                if (null != shipFromPrtyDTO.getShipCutOffInfo() && !shipFromPrtyDTO.getShipCutOffInfo().isEmpty()) {
                    assert shipFromPrty != null;
                    Long shipFromId = shipFromPrty.getId();
                    List<ShipCutOffInfo> shipCutOffInfos = shipFromPrtyDTO.getShipCutOffInfo().stream().map(
                        cutOffDto -> {
                            ShipCutOffInfo shipCutOffInfo = shipCutOffInfoMapper.toEntity(cutOffDto);
                            shipCutOffInfo.setShipFromPrtyId(shipFromId);
                            return shipCutOffInfo;
                        }).toList();
                    shipCutOffInfoList.addAll(shipCutOffInfos);
                }
            }
        }
        if (null != request.getShipToPrty()) {
            for (ShipToPrtyDTO shipToPrtyDTO : request.getShipToPrty()) {
                ShipToPrty shipToPrty = shipToPrtyMapper.toEntity(shipToPrtyDTO);
                ShipToPrtyRqrm shipToPrtyRqrm = shipToPrtyRqrmMapper.toEntity(shipToPrtyDTO.getShipToPrtyRqrm());
                if (null != shipToPrty) {
                    //shipToPrty.setOperatorId(user.getCompanyId());
                    shipToPrty.setTrspPlanLineItem(trspPlanLineItem);
                    shipToPrty = shipToPrtyRepository.save(shipToPrty);
                }
                if (null != shipToPrtyRqrm) {
                    //shipToPrtyRqrm.setOperatorId(user.getCompanyId());
                    assert shipToPrty != null;
                    shipToPrtyRqrm.setShipToPrtyId(shipToPrty.getId());
                    shipToPrtyRqrmList.add(shipToPrtyRqrm);
                }
                if (null != shipToPrtyDTO.getShipFreeTimeInfo() && !shipToPrtyDTO.getShipFreeTimeInfo().isEmpty()) {
                    assert shipToPrty != null;
                    Long shipToPrtyId = shipToPrty.getId();
                    //String operatorId = user.getCompanyId();
                    List<ShipFreeTimeInfo> shipFreeTimeInfos = shipToPrtyDTO.getShipFreeTimeInfo().stream().map(
                        freeTimeDto -> {
                            ShipFreeTimeInfo shipFreeTimeInfo = shipFreeTimeInfoMapper.toEntity(freeTimeDto);
                            shipFreeTimeInfo.setShipToPrtyId(shipToPrtyId);
                            //shipFreeTimeInfo.setOperatorId(operatorId);
                            return shipFreeTimeInfo;
                        }).toList();
                    shipFreeTimeInfoList.addAll(shipFreeTimeInfos);
                }
            }
        }
        if (!cnsLineItemList.isEmpty()) {
            cnsLineItemRepository.saveAll(cnsLineItemList);
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

}
