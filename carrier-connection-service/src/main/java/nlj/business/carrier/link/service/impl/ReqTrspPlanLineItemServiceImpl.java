package nlj.business.carrier.link.service.impl;

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
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.DataBaseConstant;
import nlj.business.carrier.link.constant.MessageConstant.TrspPlanItem;
import nlj.business.carrier.link.constant.ParamConstant.DataModelType;
import nlj.business.carrier.link.domain.CnsLineItemByDate;
import nlj.business.carrier.link.domain.ReqCnsLineItem;
import nlj.business.carrier.link.domain.ReqShipFromPrty;
import nlj.business.carrier.link.domain.ReqShipFromPrtyRqrm;
import nlj.business.carrier.link.domain.ReqShipToPrty;
import nlj.business.carrier.link.domain.ReqShipToPrtyRqrm;
import nlj.business.carrier.link.domain.ReqTrspPlanLineItem;
import nlj.business.carrier.link.domain.ReqTrspPlanMsgInfo;
import nlj.business.carrier.link.domain.ShipperOperator;
import nlj.business.carrier.link.dto.commonBody.response.CommonResponseDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqCnsLineItemDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqShipFromPrtyDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqShipFromPrtyRqrmDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqShipToPrtyDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqShipToPrtyRqrmDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqTrspPlanLineItemDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqTrspPlanLineItemDeleteDTO;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemDeleteRequest;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemRegisterRequest;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.request.ReqTrspPlanLineItemSearchRequest;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.response.ReqTrspPlanLineItemSearchResponse;
import nlj.business.carrier.link.mapper.ReqCnsLineItemMapper;
import nlj.business.carrier.link.mapper.ReqShipFromPrtyMapper;
import nlj.business.carrier.link.mapper.ReqShipFromPrtyRqrmMapper;
import nlj.business.carrier.link.mapper.ReqShipToPrtyMapper;
import nlj.business.carrier.link.mapper.ReqShipToPrtyRqrmMapper;
import nlj.business.carrier.link.mapper.ReqTrspPlanLineItemDeleteMapper;
import nlj.business.carrier.link.mapper.ReqTrspPlanLineItemMapper;
import nlj.business.carrier.link.mapper.ReqTrspPlanMsgInfoMapper;
import nlj.business.carrier.link.repository.CnsLineItemByDateRepository;
import nlj.business.carrier.link.repository.ReqCnsLineItemRepository;
import nlj.business.carrier.link.repository.ReqShipFromPrtyRepository;
import nlj.business.carrier.link.repository.ReqShipFromPrtyRqrmRepository;
import nlj.business.carrier.link.repository.ReqShipToPrtyRepository;
import nlj.business.carrier.link.repository.ReqShipToPrtyRqrmRepository;
import nlj.business.carrier.link.repository.ReqTrspPlanLineItemRepository;
import nlj.business.carrier.link.repository.ReqTrspPlanMsgInfoRepository;
import nlj.business.carrier.link.repository.ShipperOperatorRepository;
import nlj.business.carrier.link.service.ReqTrspPlanLineItemService;
import nlj.business.carrier.link.service.ShipperOperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 要求輸送計画明細サービス実装クラス.<BR>
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
    private final ReqTrspPlanMsgInfoRepository reqTrspPlanMsgInfoRepository;

    private final ReqTrspPlanMsgInfoMapper reqTrspPlanMsgInfoMapper;
    private final ReqCnsLineItemMapper reqCnsLineItemMapper;
    private final ReqShipFromPrtyMapper reqShipFromPrtyMapper;
    private final ReqShipFromPrtyRqrmMapper shipFromPrtyRqrmMapper;
    private final ReqShipToPrtyMapper reqShipToPrtyMapper;
    private final ReqShipToPrtyRqrmMapper reqshipToPrtyRqrmMapper;
    private final ReqTrspPlanLineItemMapper reqTrspPlanLineItemMapper;
    private final ReqTrspPlanLineItemDeleteMapper reqTrspPlanLineItemDeleteMapper;
    private final ReqTrspPlanLineItemRepository reqTrspPlanLineItemRepository;
    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;
    private final ShipperOperatorRepository shipperOperatorRepository;
    private final ShipperOperatorService shipperOperatorService;


    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 輸送計画を登録する.<BR>
     *
     * @param request   要求輸送計画明細登録リクエスト
     * @param xTracking Xトラッキング
     */
    @Override
    @Transactional
    public void registerTrspPlan(ReqTrspPlanLineItemRegisterRequest request, String xTracking) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }
        String operatorId = userContext.getUser().getCompanyId();

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
                    user.getCompanyId(), reqTrspPlanLineItem.getTrspInstructionId(),
                    reqTrspPlanLineItem.getCnsgPrtyHeadOffId(), reqTrspPlanLineItem.getCnsgPrtyBrncOffId()
                );
                if (reqTrspPlanLineItemExist != null) {
                    updateTrspPlan(dto, reqTrspPlanLineItem, reqTrspPlanLineItemExist, user);
                } else {
                    reqTrspPlanLineItem.setOperatorId(user.getCompanyId());
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
                                cnsLineItem.setOperatorId(user.getCompanyId());
                                cnsLineItem.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                                cnsLineItemList.add(cnsLineItem);

                                reqCnsLineItemRepository.save(cnsLineItem);
                                LocalDate fromDate = stringToLocalDate(dto.getTrspSrvc().getDsedCllFromDate());
                                LocalDate toDate = stringToLocalDate(dto.getTrspSrvc().getDsedCllToDate());
                                for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
                                    CnsLineItemByDate cnsLineItemByDate = new CnsLineItemByDate();
                                    cnsLineItemByDate.setReqCnsLineItemId(cnsLineItem.getId());
                                    cnsLineItemByDate.setTransPlanId(reqTrspPlanLineItem.getTrspInstructionId());
                                    cnsLineItemByDate.setOperatorId(user.getCompanyId());
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
                            shipFromPrty.setOperatorId(user.getCompanyId());
                            shipFromPrty.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                            shipFromPrty = reqShipFromPrtyRepository.save(shipFromPrty);
                        }
                        if (null != shipFromPrtyRqrm) {
                            shipFromPrtyRqrm.setOperatorId(user.getCompanyId());
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
                            shipToPrty.setOperatorId(user.getCompanyId());
                            shipToPrty.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                            shipToPrty = reqShipToPrtyRepository.save(shipToPrty);
                        }
                        if (null != shipToPrtyRqrm) {
                            shipToPrtyRqrm.setOperatorId(user.getCompanyId());
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

    private LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return !BaseUtil.isNull(date) ? LocalDate.parse(date, formatter) : null;
    }

    /**
     * 更新輸送計画.<BR>
     *
     * @param dto                 要求輸送計画明細DTO
     * @param reqTrspPlanLineItem 要求輸送計画明細
     * @param trspExist           既存の要求輸送計画明細
     * @param user                ユーザー
     */
    @Transactional
    public void updateTrspPlan(ReqTrspPlanLineItemDTO dto, ReqTrspPlanLineItem reqTrspPlanLineItem,
        ReqTrspPlanLineItem trspExist, User user) {

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
                    cnsLineItem.setOperatorId(user.getCompanyId());
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
                shipFromPrty.setOperatorId(user.getCompanyId());
                shipFromPrty.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                shipFromPrty = reqShipFromPrtyRepository.save(shipFromPrty);
            }
            if (null != shipFromPrtyRqrm && shipFromPrty != null) {
                if (reqShipFromPrtyRqrm != null) {
                    shipFromPrtyRqrm.setId(reqShipFromPrtyRqrm.getId());
                }
                shipFromPrtyRqrm.setOperatorId(user.getCompanyId());
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
                shipToPrtyReq.setOperatorId(user.getCompanyId());
                shipToPrtyReq.setReqTrspPlanLineItem(reqTrspPlanLineItem);
                shipToPrtyReq = reqShipToPrtyRepository.save(shipToPrtyReq);
            }
            if (null != reqShipToPrtyRqrmReq && null != shipToPrtyReq) {
                reqShipToPrtyRqrmReq.setOperatorId(user.getCompanyId());
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
     * 輸送計画を削除.<BR>
     *
     * @param request   要求輸送計画明細削除リクエスト
     * @param xTracking Xトラッキング
     */
    @Override
    @Transactional
    public void deleteTrspPlan(ReqTrspPlanLineItemDeleteRequest request, String xTracking) {
        insertPlanMsgInfo(reqTrspPlanMsgInfoMapper.toEntity(request.getMsgInfo()), xTracking);
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
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
                user.getCompanyId(), trspPlanLineItemReq.getTrspInstructionId(),
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
     * 輸送計画を検索.<BR>
     *
     * @param request 要求輸送計画明細検索リクエスト
     * @return 要求輸送計画明細検索レスポンス
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
            commonResponseDTO.setAttribute(new ReqTrspPlanLineItemSearchResponse());
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
        response.setMsgInfo(null);
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
     * メッセージを挿入 プラン情報.<BR>
     *
     * @param planMsgInfo 要求輸送計画メッセージ情報
     * @param xTracking   Xトラッキング
     * @return 要求輸送計画メッセージ情報
     */
    private ReqTrspPlanMsgInfo insertPlanMsgInfo(ReqTrspPlanMsgInfo planMsgInfo, String xTracking) {
        if (planMsgInfo == null) {
            return new ReqTrspPlanMsgInfo();
        }
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
        }
        if (null != planMsgInfo) {
            if (!BaseUtil.isNull(planMsgInfo.getMsgFnStasCd()) && planMsgInfo.getMsgFnStasCd().equals("3")
                && BaseUtil.isNull(planMsgInfo.getNoteDcptTxt())) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(TrspPlanItem.TRSP_PLAN_ITEM_ERR_001)));
            }
            planMsgInfo.setOperatorId(user.getCompanyId());
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
