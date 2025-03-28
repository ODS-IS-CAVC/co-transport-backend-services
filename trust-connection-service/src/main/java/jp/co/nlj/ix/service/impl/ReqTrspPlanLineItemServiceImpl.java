package jp.co.nlj.ix.service.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.constant.MapperConstants.TrspPlanItem;
import jp.co.nlj.ix.constant.ParamConstant.DataModelType;
import jp.co.nlj.ix.domain.CnsLineItemByDate;
import jp.co.nlj.ix.domain.ReqCnsLineItem;
import jp.co.nlj.ix.domain.ReqShipFromPrty;
import jp.co.nlj.ix.domain.ReqShipFromPrtyRqrm;
import jp.co.nlj.ix.domain.ReqShipToPrty;
import jp.co.nlj.ix.domain.ReqShipToPrtyRqrm;
import jp.co.nlj.ix.domain.ReqTrspPlanLineItem;
import jp.co.nlj.ix.domain.ReqTrspPlanMsgInfo;
import jp.co.nlj.ix.domain.ShipperOperator;
import jp.co.nlj.ix.dto.commonBody.response.CommonResponseDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqCnsLineItemDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqShipFromPrtyDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqShipFromPrtyRqrmDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqShipToPrtyDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqShipToPrtyRqrmDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspPlanLineItemDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspPlanLineItemDeleteDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemDeleteRequest;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemRegisterRequest;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemSearchRequest;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.response.ReqTrspPlanLineItemSearchResponse;
import jp.co.nlj.ix.dto.trspPlanLineItem.MsgInfoDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.response.TrspPlanLineItemSearchResponse;
import jp.co.nlj.ix.mapper.ReqCnsLineItemMapper;
import jp.co.nlj.ix.mapper.ReqShipFromPrtyMapper;
import jp.co.nlj.ix.mapper.ReqShipFromPrtyRqrmMapper;
import jp.co.nlj.ix.mapper.ReqShipToPrtyMapper;
import jp.co.nlj.ix.mapper.ReqShipToPrtyRqrmMapper;
import jp.co.nlj.ix.mapper.ReqTrspPlanLineItemDeleteMapper;
import jp.co.nlj.ix.mapper.ReqTrspPlanLineItemMapper;
import jp.co.nlj.ix.mapper.ReqTrspPlanMsgInfoMapper;
import jp.co.nlj.ix.repository.CnsLineItemByDateRepository;
import jp.co.nlj.ix.repository.ReqCnsLineItemRepository;
import jp.co.nlj.ix.repository.ReqShipFromPrtyRepository;
import jp.co.nlj.ix.repository.ReqShipFromPrtyRqrmRepository;
import jp.co.nlj.ix.repository.ReqShipToPrtyRepository;
import jp.co.nlj.ix.repository.ReqShipToPrtyRqrmRepository;
import jp.co.nlj.ix.repository.ReqTrspPlanLineItemRepository;
import jp.co.nlj.ix.repository.ReqTrspPlanMsgInfoRepository;
import jp.co.nlj.ix.repository.ShipperOperatorRepository;
import jp.co.nlj.ix.service.ReqTrspPlanLineItemService;
import jp.co.nlj.ix.service.ShipperOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 要求輸送計画明細サービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistic
 */
@Service
@RequiredArgsConstructor
public class ReqTrspPlanLineItemServiceImpl implements ReqTrspPlanLineItemService {


    private final ReqCnsLineItemRepository reqCnsLineItemRepository;

    private final ReqShipFromPrtyRepository reqShipFromPrtyRepository;

    private final ReqShipFromPrtyRqrmRepository reqShipFromPrtyRqrmRepository;

    private final ReqShipToPrtyRepository reqShipToPrtyRepository;
    private final ReqShipToPrtyRqrmRepository reqShipToPrtyRqrmRepository;
    private final ReqCnsLineItemMapper reqCnsLineItemMapper;
    private final ReqShipFromPrtyMapper reqShipFromPrtyMapper;
    private final ReqShipFromPrtyRqrmMapper shipFromPrtyRqrmMapper;
    private final ReqShipToPrtyMapper reqShipToPrtyMapper;
    private final ReqShipToPrtyRqrmMapper reqshipToPrtyRqrmMapper;
    private final ReqTrspPlanLineItemMapper reqTrspPlanLineItemMapper;
    private final ReqTrspPlanLineItemRepository reqTrspPlanLineItemRepository;

    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;

    private final ShipperOperatorRepository shipperOperatorRepository;
    private final ShipperOperatorService shipperOperatorService;

    private final ReqTrspPlanMsgInfoRepository reqTrspPlanMsgInfoRepository;

    private final ReqTrspPlanMsgInfoMapper reqTrspPlanMsgInfoMapper;

    private final ReqTrspPlanLineItemDeleteMapper reqTrspPlanLineItemDeleteMapper;

    @Resource(name = "userContext")
    private final UserContext userContext;


    /**
     * 輸送計画を登録する
     *
     * @author Next Logistic
     */
    @Override
    @Transactional
    public void registerTrspPlan(ReqTrspPlanLineItemRegisterRequest request, String xTracking) {
        User user = userContext.getUser();
        String operatorId = APIConstant.TRSP_PLAN_COMPANY_ID_DEFAULT_FOR_IX;
        if (!BaseUtil.isNull(user.getCompanyId())) {
            operatorId = userContext.getUser().getCompanyId();
        }

        ShipperOperator shipperOperator = shipperOperatorRepository.getShipperOperatorById(operatorId);
        if (shipperOperator == null) {
            shipperOperator = shipperOperatorService.createShipperOperator(operatorId);
        }
        ReqTrspPlanMsgInfo planMsgInfo = insertPlanMsgInfo(reqTrspPlanMsgInfoMapper.toEntity(request.getMsgInfo()),
            xTracking);
        assert planMsgInfo != null;
        Long trspPlanMsgInfoId = planMsgInfo.getId();
        List<ReqCnsLineItem> cnsLineItemList = new ArrayList<>();
        if (null != request.getReqTrspPlanLineItem() && !request.getReqTrspPlanLineItem().isEmpty()) {
            for (ReqTrspPlanLineItemDTO dto : request.getReqTrspPlanLineItem()) {
                ReqTrspPlanLineItem reqTrspPlanLineItem;
                reqTrspPlanLineItem = reqTrspPlanLineItemMapper.toEntity(dto.getTrspIsr(), dto.getTrspSrvc(),
                    dto.getTrspVehicleTrms(), dto.getDelInfo(), dto.getCns(),
                    dto.getCnsgPrty(), dto.getTrspRqrPrty(), dto.getCneePrty(), dto.getLogsSrvcPrv(), dto.getRoadCarr(),
                    dto.getFretClimToPrty());
                if (BaseUtil.isNull(reqTrspPlanLineItem.getTrspInstructionId())
                    || BaseUtil.isNull(String.valueOf(reqTrspPlanLineItem.getIstdTotlPcksQuan()))
                    || BaseUtil.isNull(reqTrspPlanLineItem.getCnsgPrtyHeadOffId())
                    || BaseUtil.isNull(reqTrspPlanLineItem.getCnsgPrtyBrncOffId())) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
                }
                ReqTrspPlanLineItem reqTrspPlanLineItemExist = reqTrspPlanLineItemRepository.findByOperatorIdAndTrspInstructionIdAndCnsgPrtyHeadOffIdAndCnsgPrtyBrncOffId(
                    operatorId, reqTrspPlanLineItem.getTrspInstructionId(),
                    reqTrspPlanLineItem.getCnsgPrtyHeadOffId(), reqTrspPlanLineItem.getCnsgPrtyBrncOffId()
                );
                if (reqTrspPlanLineItemExist != null) {
                    updateTrspPlan(dto, reqTrspPlanLineItem, reqTrspPlanLineItemExist, user);
                } else {
                    reqTrspPlanLineItem.setOperatorId(operatorId);
                    reqTrspPlanLineItem.setCneePrtyHeadOffId(shipperOperator.getOperatorCode());
                    reqTrspPlanLineItem.setCneePrtyBrncOffId(shipperOperator.getOperatorCode());
                    reqTrspPlanLineItem.setCnsgPrtyNameTxt(shipperOperator.getOperatorName());
                    reqTrspPlanLineItem.setCnsgSctSpedOrgNameTxt(shipperOperator.getManagerName());
                    reqTrspPlanLineItem.setCnsgTelCmmCmpNumTxt(shipperOperator.getPhoneNumber());
                    reqTrspPlanLineItem.setCnsgPstlAdrsLineOneTxt(shipperOperator.getAddress1() + " " +
                        shipperOperator.getAddress2());
                    reqTrspPlanLineItem.setCnsgPstcCd(shipperOperator.getPostalCode());
                    reqTrspPlanLineItem.setTrspPlanMsgInfoId(trspPlanMsgInfoId);
                    reqTrspPlanLineItem = reqTrspPlanLineItemRepository.save(reqTrspPlanLineItem);
                    if (null != dto.getCnsLineItem()) {
                        for (ReqCnsLineItemDTO cnsLineItemDTO : dto.getCnsLineItem()) {
                            ReqCnsLineItem cnsLineItem = reqCnsLineItemMapper.toEntity(cnsLineItemDTO);
                            if (null != cnsLineItem) {
                                cnsLineItem.setOperatorId(operatorId);
                                cnsLineItem.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                                cnsLineItemList.add(cnsLineItem);

                                reqCnsLineItemRepository.save(cnsLineItem);
                                LocalDate fromDate = stringToLocalDate(dto.getTrspSrvc().getDsedCllFromDate());
                                LocalDate toDate = stringToLocalDate(dto.getTrspSrvc().getDsedCllToDate());
                                for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
                                    CnsLineItemByDate cnsLineItemByDate = new CnsLineItemByDate();
                                    cnsLineItemByDate.setReqCnsLineItemId(cnsLineItem.getId());
                                    cnsLineItemByDate.setTransPlanId(reqTrspPlanLineItem.getTrspInstructionId());
                                    cnsLineItemByDate.setOperatorId(operatorId);
                                    cnsLineItemByDate.setOperatorCode(shipperOperator.getOperatorCode());
                                    String shipToPstlAdrsLineOneTxt = dto.getShipToPrty().getShipToPstlAdrsLineOneTxt();
                                    if (!BaseUtil.isNull(shipToPstlAdrsLineOneTxt)) {
                                        cnsLineItemByDate.setArrivalTo(
                                            Long.valueOf(shipToPstlAdrsLineOneTxt));
                                    }
                                    cnsLineItemByDate.setCollectionDate(date);
                                    cnsLineItemByDate.setCollectionTimeFrom(reqTrspPlanLineItem.getDsedCllFromTime());
                                    cnsLineItemByDate.setCollectionTimeTo(reqTrspPlanLineItem.getDsedCllToTime());
                                    cnsLineItemByDate.setPricePerUnit(dto.getTrspSrvc().getFreightRate());
                                    String shipFromPstlAdrsLineOneTxt = dto.getShipFromPrty()
                                        .getShipFromPstlAdrsLineOneTxt();
                                    if (!BaseUtil.isNull(shipFromPstlAdrsLineOneTxt)) {
                                        cnsLineItemByDate.setDepartureFrom(
                                            Long.valueOf(shipFromPstlAdrsLineOneTxt));
                                    }
                                    if (!BaseUtil.isNull(cnsLineItemDTO.getPckeFrmCd())) {
                                        cnsLineItemByDate.setOuterPackageCode(
                                            Integer.valueOf(cnsLineItemDTO.getPckeFrmCd()));
                                    }
                                    cnsLineItemByDate.setTrailerNumber(dto.getCns().getIstdTotlPcksQuan().intValue());
                                    cnsLineItemByDate.setTrailerNumberRest(0);
                                    cnsLineItemByDate.setTransportCode(dto.getTrspIsr().getTrspInstructionId());
                                    cnsLineItemByDate.setTransportName(cnsLineItemDTO.getItemNameTxt());
                                    //cnsLineItemByDate.setVehicleCondition(List.of(new Integer[]{}));
                                    cnsLineItemByDate.setPricePerUnit(dto.getTrspSrvc().getFreightRate());
                                    cnsLineItemByDate.setTransType(0);
                                    cnsLineItemByDate.setStatus(0);
                                    cnsLineItemByDateRepository.save(cnsLineItemByDate);
                                }
                            }
                        }
                    }
                    if (null != dto.getShipFromPrty()) {

                        ReqShipFromPrty shipFromPrty = reqShipFromPrtyMapper.toEntity(dto.getShipFromPrty());
                        ReqShipFromPrtyRqrm shipFromPrtyRqrm = shipFromPrtyRqrmMapper.toEntity(
                            dto.getShipFromPrty().getShipFromPrtyRqrm());
                        if (null != shipFromPrty) {
                            shipFromPrty.setOperatorId(operatorId);
                            shipFromPrty.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                            shipFromPrty = reqShipFromPrtyRepository.save(shipFromPrty);
                        }
                        if (null != shipFromPrtyRqrm) {
                            shipFromPrtyRqrm.setOperatorId(operatorId);
                            assert shipFromPrty != null;
                            shipFromPrtyRqrm.setReqShipFromPrtyId(shipFromPrty.getId());
                            reqShipFromPrtyRqrmRepository.save(shipFromPrtyRqrm);
                        }
                    }
                    if (null != dto.getShipToPrty()) {
                        ReqShipToPrty shipToPrty = reqShipToPrtyMapper.toEntity(dto.getShipToPrty());
                        ReqShipToPrtyRqrm shipToPrtyRqrm = reqshipToPrtyRqrmMapper.toEntity(
                            dto.getShipToPrty().getShipToPrtyRqrm());
                        if (null != shipToPrty) {
                            shipToPrty.setOperatorId(operatorId);
                            shipToPrty.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                            shipToPrty = reqShipToPrtyRepository.save(shipToPrty);
                        }
                        if (null != shipToPrtyRqrm) {
                            shipToPrtyRqrm.setOperatorId(operatorId);
                            assert shipToPrty != null;
                            shipToPrtyRqrm.setReqShipToPrtyId(shipToPrty.getId());
                            reqShipToPrtyRqrmRepository.save(shipToPrtyRqrm);
                        }
                    }
                }
            }
        }
//        if (!cnsLineItemList.isEmpty()) {
//            reqCnsLineItemRepository.saveAll(cnsLineItemList);
//        }
    }

    /**
     * 文字列を日付に変換する
     *
     * @param date 変換する日付の文字列
     * @return 変換された日付
     */
    private LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return !BaseUtil.isNull(date) ? LocalDate.parse(date, formatter) : null;
    }

    /**
     * 更新輸送計画
     *
     * @param dto                 更新する輸送計画のDTO
     * @param reqTrspPlanLineItem 更新する輸送計画のエンティティ
     * @param trspExist           既存の輸送計画のエンティティ
     * @param user                ユーザー情報
     */
    @Transactional
    public void updateTrspPlan(ReqTrspPlanLineItemDTO dto, ReqTrspPlanLineItem reqTrspPlanLineItem,
        ReqTrspPlanLineItem trspExist, User user) {

        String operatorId = APIConstant.TRSP_PLAN_COMPANY_ID_DEFAULT_FOR_IX;
        if (!BaseUtil.isNull(user.getCompanyId())) {
            operatorId = userContext.getUser().getCompanyId();
        }

        ReqTrspPlanLineItem itemUpdate = reqTrspPlanLineItem;
        itemUpdate.setId(trspExist.getId());
        itemUpdate.setOperatorId(trspExist.getOperatorId());
        reqTrspPlanLineItem = reqTrspPlanLineItemRepository.save(itemUpdate);
        //Delete recode in 3 table relation
        reqCnsLineItemRepository.deleteAllByReqTrspPlanLineItem_IdIn(
            Collections.singletonList(reqTrspPlanLineItem.getId()));

        //update table relation
        List<ReqCnsLineItem> cnsLineItemList = new ArrayList<>();
        ReqShipFromPrtyRqrm reqShipFromPrtyRqrm = new ReqShipFromPrtyRqrm();
        ReqShipFromPrty reqShipFromPrty = new ReqShipFromPrty();

        if (null != dto.getCnsLineItem()) {
            for (ReqCnsLineItemDTO cnsLineItemDTO : dto.getCnsLineItem()) {
                ReqCnsLineItem cnsLineItem = reqCnsLineItemMapper.toEntity(cnsLineItemDTO);
                if (null != cnsLineItem) {
                    cnsLineItem.setOperatorId(operatorId);
                    cnsLineItem.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                    cnsLineItemList.add(cnsLineItem);
                }
            }
        }
        if (null != dto.getShipFromPrty()) {
            ReqShipFromPrty shipFromPrty = reqShipFromPrtyMapper.toEntity(dto.getShipFromPrty());
            ReqShipFromPrtyRqrm shipFromPrtyRqrm = shipFromPrtyRqrmMapper.toEntity(
                dto.getShipFromPrty().getShipFromPrtyRqrm());

            reqShipFromPrty = reqShipFromPrtyRepository.findByReqTrspPlanLineItem_Id(reqTrspPlanLineItem.getId());
            if (reqShipFromPrty != null) {
                reqShipFromPrtyRqrm = reqShipFromPrtyRqrmRepository.findByReqShipFromPrtyId(reqShipFromPrty.getId());
                shipFromPrty.setId(reqShipFromPrty.getId());
                shipFromPrty.setReqTrspPlanLineItem(reqTrspPlanLineItem);
            }

            if (null != shipFromPrty) {
                shipFromPrty.setOperatorId(operatorId);
                shipFromPrty.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                shipFromPrty = reqShipFromPrtyRepository.save(shipFromPrty);
            }
            if (null != shipFromPrtyRqrm && shipFromPrty != null) {
                if (reqShipFromPrtyRqrm != null) {
                    shipFromPrtyRqrm.setId(reqShipFromPrtyRqrm.getId());
                }
                shipFromPrtyRqrm.setOperatorId(operatorId);
                shipFromPrtyRqrm.setReqShipFromPrtyId(shipFromPrty.getId());
                reqShipFromPrtyRqrmRepository.save(shipFromPrtyRqrm);
            }
        }
        ReqShipToPrtyRqrm reqShipToPrtyRqrm = new ReqShipToPrtyRqrm();
        ReqShipToPrty reqShipToPrty = new ReqShipToPrty();
        if (null != dto.getShipToPrty()) {
            reqShipToPrty = reqShipToPrtyRepository.findByReqTrspPlanLineItem_Id(reqTrspPlanLineItem.getId());
            ReqShipToPrty shipToPrtyReq = reqShipToPrtyMapper.toEntity(dto.getShipToPrty());
            ReqShipToPrtyRqrm reqShipToPrtyRqrmReq = reqshipToPrtyRqrmMapper.toEntity(
                dto.getShipToPrty().getShipToPrtyRqrm());
            if (reqShipToPrty != null) {
                shipToPrtyReq.setId(reqShipToPrty.getId());
                shipToPrtyReq.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                reqShipToPrtyRqrm = reqShipToPrtyRqrmRepository.findByReqShipToPrtyId(reqShipToPrty.getId());
            }
            if (null != shipToPrtyReq) {
                shipToPrtyReq.setOperatorId(operatorId);
                shipToPrtyReq.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                shipToPrtyReq = reqShipToPrtyRepository.save(shipToPrtyReq);
            }
            if (null != reqShipToPrtyRqrmReq && null != shipToPrtyReq) {
                reqShipToPrtyRqrmReq.setOperatorId(operatorId);
                if (reqShipToPrtyRqrm != null) {
                    reqShipToPrtyRqrmReq.setId(reqShipToPrtyRqrm.getId());
                }
                reqShipToPrtyRqrmReq.setReqShipToPrtyId(shipToPrtyReq.getId());
                reqShipToPrtyRqrmRepository.save(reqShipToPrtyRqrmReq);
            }
        }
        if (!cnsLineItemList.isEmpty()) {
            reqCnsLineItemRepository.saveAll(cnsLineItemList);
        }
    }

    /**
     * 輸送計画を削除
     *
     * @param request   削除する輸送計画のリクエスト
     * @param xTracking トラッキング情報
     */
    @Override
    @Transactional
    public void deleteTrspPlan(ReqTrspPlanLineItemDeleteRequest request, String xTracking) {
        insertPlanMsgInfo(reqTrspPlanMsgInfoMapper.toEntity(request.getMsgInfo()), xTracking);
        User user = userContext.getUser();
        String operatorId = APIConstant.TRSP_PLAN_COMPANY_ID_DEFAULT_FOR_IX;
        if (!BaseUtil.isNull(user.getCompanyId())) {
            operatorId = userContext.getUser().getCompanyId();
        }
        List<ReqTrspPlanLineItem> reqTrspPlanLineItems = new ArrayList<>();
        for (ReqTrspPlanLineItemDeleteDTO dto : request.getReqTrspPlanLineItem()) {

            ReqTrspPlanLineItem trspPlanLineItemReq = reqTrspPlanLineItemDeleteMapper.toEntity(dto.getTrspIsr(),
                dto.getTrspSrvc(),
                dto.getTrspVehicleTrms(), dto.getDelInfo(),
                dto.getCns(), dto.getCnsgPrty(), dto.getTrspRqrPrty(), dto.getCneePrty(), dto.getLogsSrvcPrv(),
                dto.getRoadCarr(),
                dto.getFretClimToPrty());
            if (BaseUtil.isNull(trspPlanLineItemReq.getTrspInstructionId())
                || BaseUtil.isNull(String.valueOf(trspPlanLineItemReq.getIstdTotlPcksQuan()))
                || BaseUtil.isNull(trspPlanLineItemReq.getCnsgPrtyHeadOffId())
                || BaseUtil.isNull(trspPlanLineItemReq.getCnsgPrtyBrncOffId())) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
            }

            ReqTrspPlanLineItem reqTrspPlanLineItemExist = reqTrspPlanLineItemRepository.findByOperatorIdAndTrspInstructionIdAndCnsgPrtyHeadOffIdAndCnsgPrtyBrncOffId(
                operatorId, trspPlanLineItemReq.getTrspInstructionId(),
                trspPlanLineItemReq.getCnsgPrtyHeadOffId(), trspPlanLineItemReq.getCnsgPrtyBrncOffId()
            );
            if (reqTrspPlanLineItemExist == null) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND,
                        SoaResponsePool.get(TrspPlanItem.REQ_TRSP_PLAN_ITEM_NOT_FOUND)));
            }
            if (null != trspPlanLineItemReq) {
                reqTrspPlanLineItems.add(reqTrspPlanLineItemExist);
            }
        }
        List<Long> reqTrspPlanId = reqTrspPlanLineItems.stream()
            .map(ReqTrspPlanLineItem::getId).toList();
        //Query in DB, get list  TrspPlanLineItem
        List<ReqShipFromPrty> reqShipFromPrties = reqShipFromPrtyRepository.findAllByReqTrspPlanLineItem_IdIn(
            reqTrspPlanId);
        if (null != reqShipFromPrties) {
            List<Long> ids = reqShipFromPrties.stream().map(ReqShipFromPrty::getId).toList();
            reqShipFromPrtyRqrmRepository.deleteAllByReqShipFromPrtyIdIn(ids);
            reqShipFromPrtyRepository.deleteAll(reqShipFromPrties);
        }
        List<ReqShipToPrty> shipToPrties = reqShipToPrtyRepository.findAllByReqTrspPlanLineItem_IdIn(reqTrspPlanId);
        if (null != shipToPrties) {
            List<Long> ids = shipToPrties.stream().map(ReqShipToPrty::getId).toList();
            reqShipToPrtyRqrmRepository.deleteAllByReqShipToPrtyIdIn(ids);
            reqShipToPrtyRepository.deleteAll(shipToPrties);
        }
        reqCnsLineItemRepository.deleteAllByReqTrspPlanLineItem_IdIn(reqTrspPlanId);
        reqTrspPlanLineItemRepository.deleteAll(reqTrspPlanLineItems);
    }

    /**
     * 輸送計画を検索
     *
     * @param request 検索リクエスト
     * @return 検索結果のDTO
     */
    @Override
    public CommonResponseDTO searchTrspPlan(ReqTrspPlanLineItemSearchRequest request) {
        List<ReqTrspPlanLineItem> reqTrspPlanLineItems = reqTrspPlanLineItemRepository.searchTransportPlanItem(request);
        if (null == reqTrspPlanLineItems) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }
        if (reqTrspPlanLineItems.isEmpty()) {
            CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
            commonResponseDTO.setDataModelType(DataModelType.TEST_1);
            commonResponseDTO.setAttribute(new TrspPlanLineItemSearchResponse());
            return commonResponseDTO;
        }

        List<Long> reqTrspPlanLineItemIds = reqTrspPlanLineItems.stream().map(ReqTrspPlanLineItem::getId).toList();
        List<ReqCnsLineItem> reqCnsLineItems = reqCnsLineItemRepository.findAllByReqTrspPlanLineItem_IdIn(
            reqTrspPlanLineItemIds);
        List<ReqShipFromPrty> reqShipFromPrties = reqShipFromPrtyRepository.findAllByReqTrspPlanLineItem_IdIn(
            reqTrspPlanLineItemIds);
        List<ReqShipToPrty> reqShipToPrties = reqShipToPrtyRepository.findAllByReqTrspPlanLineItem_IdIn(
            reqTrspPlanLineItemIds);
        List<ReqShipFromPrtyRqrm> reqShipFromPrtyRqrms = new ArrayList<>();
        List<ReqShipToPrtyRqrm> reqShipToPrtyRqrms = new ArrayList<>();

        if (null != reqShipFromPrties) {
            List<Long> shipFromPrtyIds = reqShipFromPrties.stream().map(ReqShipFromPrty::getId).toList();
            reqShipFromPrtyRqrms = reqShipFromPrtyRqrmRepository.findAllByReqShipFromPrtyIdIn(shipFromPrtyIds);
        }
        if (null != reqShipToPrties) {
            List<Long> shipToPrtyIds = reqShipToPrties.stream().map(ReqShipToPrty::getId).toList();
            reqShipToPrtyRqrms = reqShipToPrtyRqrmRepository.findAllByReqShipToPrtyIdIn(shipToPrtyIds);
        }
        Map<Long, List<ReqCnsLineItem>> reqCnsLineItemMap = new HashMap<>();
        Map<Long, ReqShipFromPrty> reqShipFromPrtyMap = new HashMap<>();
        Map<Long, ReqShipToPrty> reqShipToPrtyMap = new HashMap<>();
        Map<Long, ReqShipFromPrtyRqrm> reqShipFromPrtyRqrmMap = new HashMap<>();
        Map<Long, ReqShipToPrtyRqrm> reqShipToPrtyRqrmMap = new HashMap<>();

        if (null != reqCnsLineItems) {
            reqCnsLineItemMap = reqCnsLineItems.stream()
                .collect(Collectors.groupingBy(ReqCnsLineItem::getreqTrspPlanLineItemId));
        }
        if (null != reqShipFromPrties) {
            reqShipFromPrtyMap = reqShipFromPrties.stream().collect(
                Collectors.toMap(ReqShipFromPrty::getReqTrspPlanLineItemId, reqShipFromPrty -> reqShipFromPrty));
        }
        if (null != reqShipToPrties) {
            reqShipToPrtyMap = reqShipToPrties.stream()
                .collect(Collectors.toMap(ReqShipToPrty::getReqTrspPlanLineItemId, reqShipToPrty -> reqShipToPrty));
        }
        if (null != reqShipFromPrtyRqrms) {
            reqShipFromPrtyRqrmMap = reqShipFromPrtyRqrms.stream()
                .collect(
                    Collectors.toMap(ReqShipFromPrtyRqrm::getReqShipFromPrtyId, shipFromPrtyRqrm -> shipFromPrtyRqrm));
        }
        if (null != reqShipToPrtyRqrms) {
            reqShipToPrtyRqrmMap = reqShipToPrtyRqrms.stream()
                .collect(Collectors.toMap(ReqShipToPrtyRqrm::getReqShipToPrtyId, shipToPrtyRqrm -> shipToPrtyRqrm));
        }

        ReqTrspPlanLineItemSearchResponse response = new ReqTrspPlanLineItemSearchResponse();
        response.setMsgInfo(new MsgInfoDTO());
        List<ReqTrspPlanLineItemDTO> trspPlanLineItemDTOList = new ArrayList<>();
        for (ReqTrspPlanLineItem reqTrspPlanLineItem : reqTrspPlanLineItems) {
            ReqTrspPlanLineItemDTO trspPlanLineItemDTO = new ReqTrspPlanLineItemDTO();
            trspPlanLineItemDTO.setTrspIsr(reqTrspPlanLineItemMapper.toTrspIsrDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setTrspSrvc(reqTrspPlanLineItemMapper.toTrspSrvcDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setTrspVehicleTrms(
                reqTrspPlanLineItemMapper.toTrspVehicleTrmsDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setDelInfo(reqTrspPlanLineItemMapper.toDelInfoDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setCns(reqTrspPlanLineItemMapper.toCnsDTO(reqTrspPlanLineItem));
            if (null != reqCnsLineItemMap.get(reqTrspPlanLineItem.getId())) {
                List<ReqCnsLineItemDTO> cnsLineItemDTOList = reqCnsLineItemMap.get(reqTrspPlanLineItem.getId()).stream()
                    .map(reqCnsLineItemMapper::toDTO).toList();
                trspPlanLineItemDTO.setCnsLineItem(cnsLineItemDTOList);
            }
            trspPlanLineItemDTO.setCnsgPrty(reqTrspPlanLineItemMapper.toCnsgPrtyDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setTrspRqrPrty(reqTrspPlanLineItemMapper.toTrspRqrPrtyDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setCneePrty(reqTrspPlanLineItemMapper.toCneePrtyDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setLogsSrvcPrv(reqTrspPlanLineItemMapper.toLogsSrvcPrvDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setRoadCarr(reqTrspPlanLineItemMapper.toRoadCarrDTO(reqTrspPlanLineItem));
            trspPlanLineItemDTO.setFretClimToPrty(reqTrspPlanLineItemMapper.toFretClimToPrtyDTO(reqTrspPlanLineItem));
            if (null != reqShipFromPrtyMap.get(reqTrspPlanLineItem.getId())) {
                ReqShipFromPrty reqShipFromPrty = reqShipFromPrtyMap.get(reqTrspPlanLineItem.getId());
                ReqShipFromPrtyDTO reqShipFromPrtyDTO;
                reqShipFromPrtyDTO = reqShipFromPrtyMapper.toDTO(reqShipFromPrtyMap.get(reqTrspPlanLineItem.getId()));
                if (null != reqShipFromPrtyRqrmMap.get(reqShipFromPrty.getId())) {
                    ReqShipFromPrtyRqrmDTO shipFromPrtyRqrmDTO = shipFromPrtyRqrmMapper.toDTO(
                        reqShipFromPrtyRqrmMap.get(reqShipFromPrty.getId()));
                    if (null != reqShipFromPrtyDTO && (null != shipFromPrtyRqrmDTO)) {
                        reqShipFromPrtyDTO.setShipFromPrtyRqrm(shipFromPrtyRqrmDTO);
                    }
                }
                trspPlanLineItemDTO.setShipFromPrty(reqShipFromPrtyDTO);
            }

            if (null != reqShipToPrtyMap.get(reqTrspPlanLineItem.getId())) {

                ReqShipToPrty reqShipToPrty = reqShipToPrtyMap.get(reqTrspPlanLineItem.getId());
                ReqShipToPrtyDTO shipToPrtyDTO = reqShipToPrtyMapper.toDTO(reqShipToPrty);
                if (null != reqShipToPrtyRqrmMap.get(reqShipToPrty.getId())) {
                    ReqShipToPrtyRqrmDTO shiptoPrtyRqrmDTO = reqshipToPrtyRqrmMapper.toDTO(
                        reqShipToPrtyRqrmMap.get(reqShipToPrty.getId()));
                    if (null != shipToPrtyDTO) {
                        if (null != shiptoPrtyRqrmDTO) {
                            shipToPrtyDTO.setShipToPrtyRqrm(shiptoPrtyRqrmDTO);
                        }
                    }
                }
                trspPlanLineItemDTO.setShipToPrty(shipToPrtyDTO);

            }

            trspPlanLineItemDTOList.add(trspPlanLineItemDTO);
        }
        response.setReqTrspPlanLineItem(trspPlanLineItemDTOList);
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        responseDTO.setDataModelType(DataModelType.TEST_1);
        responseDTO.setAttribute(response);

        return responseDTO;
    }

    /**
     * メッセージを挿入 プラン情報
     *
     * @param planMsgInfo 挿入するプラン情報
     * @param xTracking   トラッキング情報
     * @return 挿入されたプラン情報
     */
    private ReqTrspPlanMsgInfo insertPlanMsgInfo(ReqTrspPlanMsgInfo planMsgInfo, String xTracking) {
        if (planMsgInfo == null) {
            return new ReqTrspPlanMsgInfo();
        }
        User user = userContext.getUser();
        String operatorId = APIConstant.TRSP_PLAN_COMPANY_ID_DEFAULT_FOR_IX;
        if (!BaseUtil.isNull(user.getCompanyId())) {
            operatorId = userContext.getUser().getCompanyId();
        }
        if (null != planMsgInfo) {
            if (!BaseUtil.isNull(planMsgInfo.getMsgFnStasCd()) && planMsgInfo.getMsgFnStasCd().equals("3")
                && BaseUtil.isNull(planMsgInfo.getNoteDcptTxt())) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
            }
            planMsgInfo.setOperatorId(operatorId);
            planMsgInfo.setXTracking(!BaseUtil.isNull(xTracking) ? UUID.fromString(xTracking) : null);
            /*ReqTrspPlanMsgInfo checkExistplanMsgInfo = reqTrspPlanMsgInfoRepository.findByMsgId(planMsgInfo.getMsgId());
            if (null != checkExistplanMsgInfo) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
            }*/
            planMsgInfo = reqTrspPlanMsgInfoRepository.save(planMsgInfo);
        }
        return planMsgInfo;
    }

}
