package nlj.business.transaction.service.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.mail.util.NextMailUtil;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.transaction.constant.APIConstant;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.MessageConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.TransMatching;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.dto.CarrierNegotiation;
import nlj.business.transaction.dto.NegotiationData;
import nlj.business.transaction.dto.PageResponseDTO;
import nlj.business.transaction.dto.ShipperNegotiation;
import nlj.business.transaction.dto.ShipperTransactionDTO;
import nlj.business.transaction.dto.TransOrderDTO;
import nlj.business.transaction.dto.TransactionCarrierDTO;
import nlj.business.transaction.dto.TransactionDetailResponseDTO;
import nlj.business.transaction.dto.ix.response.IXOperationRequestPropose;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.request.CarrierOperationApprovalRequestDTO;
import nlj.business.transaction.dto.request.CarrierOperatorPlansRequest;
import nlj.business.transaction.dto.request.CarrierTransportProposalIXRequest;
import nlj.business.transaction.dto.request.CarrierTransportProposalRequest;
import nlj.business.transaction.dto.request.CarrierTransportProposalSearch;
import nlj.business.transaction.dto.request.OrderEmergencyUpdateRequest;
import nlj.business.transaction.dto.request.SendMailRequest;
import nlj.business.transaction.dto.request.TransMatchingRequest;
import nlj.business.transaction.dto.request.TransactionCarrier2Request;
import nlj.business.transaction.dto.request.TransactionCarrierFERequest;
import nlj.business.transaction.dto.request.TransactionCarrierRequest;
import nlj.business.transaction.dto.request.TransactionShipperApprovalIXRequest;
import nlj.business.transaction.dto.request.TransactionShipperApprovalRequest;
import nlj.business.transaction.dto.request.TransactionShipperSearch;
import nlj.business.transaction.dto.request.TransactionShipperUpdateIXRequest;
import nlj.business.transaction.dto.request.TransactionShipperUpdateRequest;
import nlj.business.transaction.dto.request.TrspPlanLineItemResponseDTO;
import nlj.business.transaction.dto.response.CarrierOperatorPlansInsertResponse;
import nlj.business.transaction.dto.response.CarrierProposalItemResponseDTO;
import nlj.business.transaction.dto.response.CarrierTransportProposalIXResponseDTO;
import nlj.business.transaction.dto.transferStatus.request.TransferStatusRequest;
import nlj.business.transaction.dto.trip.VehicleDiagramMobilityHubResponseDTO;
import nlj.business.transaction.dto.trip.cnsLineItem.response.CnsLineItemResponseDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.CneePrtyDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.CnsDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.CnsLineItemDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.CnsgPrtyDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.RoadCarrDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.ShipFromPrtyDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.ShipToPrtyDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.TrspIsrDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.TrspPlanLineItemRequest;
import nlj.business.transaction.dto.trspPlanLineItem.ix.TrspRqrPrtyDTO;
import nlj.business.transaction.dto.trspPlanLineItem.ix.TrspSrvcDTO;
import nlj.business.transaction.mapper.DateTimeMapper;
import nlj.business.transaction.mapper.NegotiationDataMapper;
import nlj.business.transaction.mapper.TransOrderMapper;
import nlj.business.transaction.mapper.TransactionDetailResponseMapper;
import nlj.business.transaction.repository.CarrierOperatorCustomRepository;
import nlj.business.transaction.repository.CnsLineItemByDateRepository;
import nlj.business.transaction.repository.ReqTrspPlanLineItemCustomRepository;
import nlj.business.transaction.repository.ShipperOperatorCustomRepository;
import nlj.business.transaction.repository.TransMatchingCustomRepository;
import nlj.business.transaction.repository.TransMatchingRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import nlj.business.transaction.repository.TrspPlanLineItemCustomRepository;
import nlj.business.transaction.repository.VehicleAvbResourceItemRepository;
import nlj.business.transaction.repository.VehicleDiagramMobilityHubCustomRepository;
import nlj.business.transaction.service.CnsLineItemByDateService;
import nlj.business.transaction.service.TransMatchingService;
import nlj.business.transaction.service.TransOrderService;
import nlj.business.transaction.service.TransferStatusService;
import nlj.business.transaction.service.VehicleAvbResourceItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <PRE>
 * トランザクションオーダーサービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransOrderServiceImpl implements TransOrderService {

    private final TransOrderRepository transOrderRepository;
    private final TransMatchingRepository transMatchingRepository;
    private final RestTemplate restTemplate;
    private final TransOrderMapper transOrderMapper;
    private final TransMatchingCustomRepository transMatchingCustomRepository;
    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;
    private final VehicleAvbResourceItemRepository vehicleAvbResourceItemRepository;
    private final ReqTrspPlanLineItemCustomRepository reqTrspPlanLineItemCustomRepository;
    private final TrspPlanLineItemCustomRepository trspPlanLineItemCustomRepository;
    private final TransferStatusService transferStatusService;

    private final VehicleAvbResourceItemService vehicleAvbResourceItemService;
    private final CnsLineItemByDateService cnsLineItemByDateService;

    private final NegotiationDataMapper negotiationDataMapper;
    private final TransactionDetailResponseMapper transactionDetailResponseMapper;
    private final EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(TransOrderServiceImpl.class);

    private final VehicleDiagramMobilityHubCustomRepository vehicleDiagramMobilityHubCustomRepository;
    private final CarrierOperatorCustomRepository carrierOperatorCustomRepository;
    private final ShipperOperatorCustomRepository shipperOperatorCustomRepository;
    private final NextMailUtil nextMailUtil;
    @Value("${next.url.ix-service-carrier-transaction-shipper}")
    public String CARRIER_TRANSACTION_SHIPPER_ENDPOINT;
    @Value("${next.url.ix-service-shipper-operations-plans-propose}")
    public String SHIPPER_OPERATIONS_PLANS_PROPOSE_ENDPOINT;
    @Value("${next.url.ix-service-carrier-transaction-shipper-id-approval}")
    public String CARRIER_TRANSACTION_SHIPPER_ID_APPROVAL_ENDPOINT;
    @Value("${next.url.ix-service-carrier-transaction-shipper-id}")
    public String CARRIER_TRANSACTION_SHIPPER_ID_ENDPOINT;
    @Value("${next.url.ix-service-shipper-operations-plans-propose-reply}")
    public String SHIPPER_OPERATIONS_PLANS_PROPOSE_RELY_ENDPOINT;
    @Value("${next.url.ix-service-shipper-operations-plans-propose-update}")
    public String SHIPPER_OPERATIONS_PLANS_PROPOSE_UPDATE_ENDPOINT;
    @Value("${next.url.cologi-ix-service-operation-request-new}")
    public String IX_SERVICE_OPERATION_REQUEST_ENDPOINT;
    @Value("${next.url.cologi-ix-service-shipper-operations-plans-propose-reply}")
    public String IX_SERVICE_RESERVE_PROPOSE_REPLY_ENDPOINT;
    @Value("${next.url.cologi-ix-service-shipper-operations-plans-propose-update}")
    public String IX_SERVICE_RESERVE_PROPOSE_UPDATE_ENDPOINT;
    @Value("${next.url.cologi-ix-service-shipper-operations-plans-propose}")
    public String IX_SERVICE_RESERVE_PROPOSE_ENDPOINT;
    @Resource(name = "userContext")
    private UserContext userContext;
    @Value("${next.url.cologi-ix-service-operation-request-handover}")
    private String API_URL;
    @Autowired
    private TransMatchingService transMatchingService;

    /**
     * シッパーのトランザクションを挿入する。
     *
     * @param transMatchingRequest トランザクションマッチングリクエスト
     * @param httpServletRequest   HTTPリクエスト
     * @return 挿入されたトランザクションのID
     */
    @Override
    @Transactional
    public BigInteger insertShipperTransaction(TransMatchingRequest transMatchingRequest,
        HttpServletRequest httpServletRequest) {
        Long transMatchingId = Long.parseLong(transMatchingRequest.getId());
        Optional<TransMatching> transMatchingDB = transMatchingRepository.findById(transMatchingId);
        if (transMatchingDB.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_404),
                    transMatchingId));
        }
        TransOrder transOrder = transOrderMapper.toTransOrder(transMatchingDB.get());
        transOrder.setStatus(DataBaseConstant.TransOrderStatus.SHIPPER_REQUEST);
        transOrder.setTransType(DataBaseConstant.TransOrder.TransType.SHIPPER_REQUEST);
        transOrder.setRequestPrice(transMatchingDB.get().getRequestSnapshot().getPricePerUnit());
        transOrder.setRequestCollectionTimeFrom(transMatchingDB.get().getRequestSnapshot().getCollectionTimeFrom());
        transOrder.setRequestCollectionTimeTo(transMatchingDB.get().getRequestSnapshot().getCollectionTimeTo());
        transOrder.setTransMatchingId(transMatchingDB.get().getId());
        transOrder.setTrspInstructionId(
            reqTrspPlanLineItemCustomRepository.findInstructionIdByReqCnsLineItemId(
                transMatchingDB.get().getReqCnsLineItemId()));

        //Insert Negotiation data
        if (transMatchingRequest.getNegotiationDTORequest() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
            NegotiationData negotiationData = new NegotiationData();
            ShipperNegotiation shipperNegotiation = new ShipperNegotiation();
            shipperNegotiation.setDepartureDate(
                LocalDate.parse(transMatchingRequest.getNegotiationDTORequest().getDepartureDate(), formatter));
            shipperNegotiation.setArrivalDate(
                LocalDate.parse(transMatchingRequest.getNegotiationDTORequest().getArrivalDate(), formatter));
            shipperNegotiation.setCutOffTime(transMatchingRequest.getNegotiationDTORequest().getCutOffTime());
            shipperNegotiation.setCutOffFee(transMatchingRequest.getNegotiationDTORequest().getCutOffFee());
            shipperNegotiation.setPrice(transMatchingRequest.getNegotiationDTORequest().getPrice());
            negotiationData.setShipper(shipperNegotiation);
            transOrder.setNegotiationData(negotiationData);
        }
        Optional<VehicleAvbResourceItem> vehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(
            transMatchingDB.get().getVehicleAvbResourceItemId());
        if (vehicleAvbResourceItem.isPresent()) {
            transOrder.setArrivalTime(vehicleAvbResourceItem.get().getArrivalTime());
            transOrder.setDepartureTime(vehicleAvbResourceItem.get().getDepartureTime());
            transOrder.setVehicleDiagramItemId(vehicleAvbResourceItem.get().getVehicleDiagramItemId());
        }
        Optional<CnsLineItemByDate> cnsLineItemByDate = cnsLineItemByDateRepository.findById(
            transMatchingDB.get().getReqCnsLineItemId());
        cnsLineItemByDate.ifPresent(lineItemByDate -> lineItemByDate.setStatus(
            DataBaseConstant.CnsLineItemByDate.Status.AWAITING_CONFIRMATION));
        //call to IX-021
//        if(transMatchingRequest.getIsNotIX() == null || !transMatchingRequest.getIsNotIX()) {
//            insertShipperOperationReserveIX(transMatchingRequest.getShipperCode(), transMatchingRequest.getCarrierCode(), transMatchingRequest.getCid(), new CarrierTransportProposalIXRequest());
//        }
        updateStatusAfterPropose(transMatchingDB.get().getId(), transMatchingDB.get().getCnsLineItemByDateId(),
            transMatchingDB.get().getVehicleAvbResourceId());

        TransOrder transOrderInsert = transOrderRepository.save(transOrder);
        this.updateStatusTransaction(transOrderInsert.getCnsLineItemByDateId(), transOrder.getVehicleAvbResourceId(),
            transOrderInsert.getStatus());
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrderInsert.getVehicleDiagramItemTrailerId(), transOrderInsert.getStatus(),
            transOrderInsert.getTransportPlanItemId(), transOrderInsert.getStatus());
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);

        return BigInteger.valueOf(transOrderInsert.getId());
    }

    //call to IX-021

    /**
     * シッパーのオペレーション予約を挿入する。
     *
     * @param shipperCode シッパーコード
     * @param carrierCode キャリアコード
     * @param cid         CID
     * @param requestBody リクエストボディ
     * @return 提案ID
     */
    private BigInteger insertShipperOperationReserveIX(String shipperCode, String carrierCode, String cid,
        CarrierTransportProposalIXRequest requestBody) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CarrierTransportProposalIXRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(
                IX_SERVICE_RESERVE_PROPOSE_ENDPOINT)
            .queryParam(ParamConstant.SHIPPER_CID, BaseUtil.getShipperCid(shipperCode))
            .queryParam(ParamConstant.CARRIER_CID, BaseUtil.getCarrierCid(carrierCode))
            .queryParam(ParamConstant.CID, cid);
        logger.info("DEBUG: IX API-021 url: " + uriComponentsBuilder.toUriString());
        logger.info("DEBUG: IX API-021 body: " + BaseUtil.makeString(requestBody));
        ResponseEntity<CarrierTransportProposalIXResponseDTO> response = restTemplate.exchange(
            uriComponentsBuilder.build().encode().toString(), HttpMethod.POST, requestEntity,
            CarrierTransportProposalIXResponseDTO.class);
        logger.info("DEBUG: IX API-021 Successfully called IX 021 for response: {}", response);
        if (response != null && response.getBody().getReserveDTO() != null) {
            BigInteger proposeId = response.getBody().getReserveDTO().getProposeId();
            return proposeId;
        }
        return null;
    }

    /**
     * キャリアのトランザクションを挿入する。
     *
     * @param transMatchingRequest トランザクションマッチングリクエスト
     * @param httpServletRequest   HTTPリクエスト
     * @return 挿入されたトランザクションのID
     */
    @Override
    @Transactional
    public Long insertCarrierTransaction(TransMatchingRequest transMatchingRequest,
        HttpServletRequest httpServletRequest) {
        Long transMatchingId = Long.parseLong(transMatchingRequest.getId());
        Optional<TransMatching> transMatchingDB = transMatchingRepository.findById(transMatchingId);
        if (transMatchingDB.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_404),
                    transMatchingId));
        }

        //Insert trans_order
        TransOrder transOrder = transOrderMapper.toTransOrder(transMatchingDB.get());
        transOrder.setStatus(DataBaseConstant.TransOrderStatus.CARRIER_REQUEST);
        transOrder.setTransMatchingId(transMatchingId);
        if (transMatchingDB.get().getRequestSnapshot().getTransType() == 1) {
            transOrder.setParentOrderId(transMatchingDB.get().getRequestSnapshot().getTransOrderId());
            Optional<TransOrder> transOrderFindById = transOrderRepository.findById(
                transMatchingDB.get().getRequestSnapshot().getTransOrderId());
            if (transOrderFindById.isEmpty()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                        transMatchingDB.get().getRequestSnapshot().getTransOrderId()));
            }
            transOrder.setShipperOperatorId(transOrderFindById.get().getShipperOperatorId());
            transOrder.setShipperOperatorCode(transOrderFindById.get().getShipperOperatorCode());
            transOrder.setShipperOperatorName(transOrderFindById.get().getShipperOperatorName());
            transOrder.setTransType(DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST);
        } else {
            transOrder.setTransType(DataBaseConstant.TransOrder.TransType.SHIPPER_REQUEST);
        }
        transOrder.setRequestPrice(transMatchingDB.get().getRequestSnapshot().getPricePerUnit());
        transOrder.setRequestCollectionTimeFrom(transMatchingDB.get().getRequestSnapshot().getCollectionTimeFrom());
        transOrder.setRequestCollectionTimeTo(transMatchingDB.get().getRequestSnapshot().getCollectionTimeTo());
        transOrder.setTrspInstructionId(
            reqTrspPlanLineItemCustomRepository.findInstructionIdByReqCnsLineItemId(
                transMatchingDB.get().getReqCnsLineItemId()));

        //Insert Negotiation data
        if (transMatchingRequest.getNegotiationDTORequest() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM);
            NegotiationData negotiationData = new NegotiationData();
            CarrierNegotiation carrierNegotiation = new CarrierNegotiation();
            carrierNegotiation.setDepartureTime(
                LocalTime.parse(transMatchingRequest.getNegotiationDTORequest().getCollectionTimeFrom(), formatter));
            carrierNegotiation.setArrivalTime(
                LocalTime.parse(transMatchingRequest.getNegotiationDTORequest().getCollectionTimeTo(), formatter));
            carrierNegotiation.setCutOffTime(transMatchingRequest.getNegotiationDTORequest().getCutOffTime());
            carrierNegotiation.setCutOffFee(transMatchingRequest.getNegotiationDTORequest().getCutOffFee());
            carrierNegotiation.setPrice(transMatchingRequest.getNegotiationDTORequest().getPrice());
            negotiationData.setCarrier(carrierNegotiation);
            transOrder.setNegotiationData(negotiationData);
        }

        Optional<VehicleAvbResourceItem> vehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(
            transMatchingDB.get().getVehicleAvbResourceItemId());
        if (vehicleAvbResourceItem.isPresent()) {
            transOrder.setArrivalTime(vehicleAvbResourceItem.get().getArrivalTime());
            transOrder.setDepartureTime(vehicleAvbResourceItem.get().getDepartureTime());
            transOrder.setVehicleDiagramItemId(vehicleAvbResourceItem.get().getVehicleDiagramItemId());
        }
        Long idSave = transOrderRepository.save(transOrder).getId();
        this.updateStatusTransaction(transOrder.getCnsLineItemByDateId(), transOrder.getVehicleAvbResourceId(),
            transOrder.getStatus());
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(), transOrder.getTransportPlanItemId(),
            transOrder.getStatus());
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        updateStatusAfterPropose(transMatchingDB.get().getId(), transMatchingDB.get().getCnsLineItemByDateId(),
            transMatchingDB.get().getVehicleAvbResourceId());
        return idSave;
    }

    /**
     * シッパーのトランザクションをページングで取得する。
     *
     * @param searchRequest トランザクションシッパー検索リクエスト
     * @return ページングされたトランザクションのページレスポンス
     */
    @Override
    public PageResponseDTO<ShipperTransactionDTO> getPagedShipperTransaction(TransactionShipperSearch searchRequest) {
        Page<ShipperTransactionDTO> transOrderPage = transOrderRepository.getPagedShipperTransaction(searchRequest);
        return new PageResponseDTO<>(transOrderPage);
    }

    /**
     * キャリアの輸送提案を作成する。
     *
     * @param request            キャリア輸送提案リクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 作成されたトランザクションのID
     */
    @Override
    @Transactional
    public Long createCarrierTransportProposal(CarrierTransportProposalRequest request,
        HttpServletRequest httpServletRequest) {
        Long transMatchingId = Long.parseLong(request.getId());
        Optional<TransMatching> transMatchingDB = transMatchingRepository.findById(transMatchingId);
        if (transMatchingDB.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.TRANS_MATCHING_404),
                    transMatchingId));
        }

        String userOperatorId = userContext.getUser().getCompanyId();
        Integer orderUpdateStatus = null;
        if (userOperatorId.equals(transMatchingDB.get().getCarrierOperatorId())) {
            orderUpdateStatus = DataBaseConstant.TransOrderStatus.CARRIER1_REQUEST;
            if (transMatchingDB.get().getCnsLineItemByDateId() != null) {
                cnsLineItemByDateRepository.updateStatusById(
                    transMatchingDB.get().getCnsLineItemByDateId(),
                    DataBaseConstant.CnsLineItemByDate.Status.AWAITING_CONFIRMATION);

                transMatchingRepository.updateStatusByCnsLineItemByDateIdAndIdNot(
                    DataBaseConstant.TranMatchingStatus.IMPOSSIBLE_PICK_UP,
                    transMatchingDB.get().getCnsLineItemByDateId(),
                    transMatchingDB.get().getId()
                );
            }
        } else if (transMatchingDB.get().getCarrier2OperatorId() != null && userOperatorId.equals(
            transMatchingDB.get().getCarrier2OperatorId())) {
            orderUpdateStatus = DataBaseConstant.TransOrderStatus.CARRIER2_PROPOSAL;
            if (transMatchingDB.get().getVehicleAvbResourceItemId() != null) {
                vehicleAvbResourceItemRepository.updateStatusById(
                    transMatchingDB.get().getVehicleAvbResourceItemId(),
                    DataBaseConstant.VehicleAvbResourceItem.Status.AWAITING_CONFIRMATION);

                transMatchingRepository.updateStatusByVehicleAvbResourceItemIdAndIdNot(
                    DataBaseConstant.TranMatchingStatus.IMPOSSIBLE_PICK_UP,
                    transMatchingDB.get().getVehicleAvbResourceItemId(),
                    transMatchingDB.get().getId()
                );
            }
        } else {
            //throw exception if operator not match DB record
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(MessageConstant.Validate.OPERATOR_ID_NOT_MATCH_RECORD)));
        }

        TransOrder transOrder = transOrderMapper.toTransOrder(transMatchingDB.get());
        transOrder.setStatus(orderUpdateStatus);
        transOrder.setTransMatchingId(transMatchingDB.get().getId());
        transOrder.setTransType(DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST);
        transOrder.setProposePrice(transMatchingDB.get().getProposeSnapshot().getPrice());
        transOrder.setProposeDepartureTime(transMatchingDB.get().getProposeSnapshot().getDepartureTimeMin());
        transOrder.setProposeArrivalTime(transMatchingDB.get().getProposeSnapshot().getArrivalTime());
        transOrder.setTrspInstructionId(
            trspPlanLineItemCustomRepository.findInstructionIdByCnsLineItemId(
                transMatchingDB.get().getCnsLineItemId()
            )
        );
        Optional<VehicleAvbResourceItem> vehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(
            transMatchingDB.get().getVehicleAvbResourceItemId());
        if (vehicleAvbResourceItem.isPresent()) {
            transOrder.setArrivalTime(vehicleAvbResourceItem.get().getArrivalTime());
            transOrder.setDepartureTime(vehicleAvbResourceItem.get().getDepartureTime());
            transOrder.setVehicleDiagramItemId(vehicleAvbResourceItem.get().getVehicleDiagramItemId());
        }
        transOrder = transOrderRepository.save(transOrder);
        transMatchingDB.get().setStatus(DataBaseConstant.TranMatchingStatus.PICKED_UP);
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(), transOrder.getTransportPlanItemId(),
            transOrder.getStatus());
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return transOrder.getId();
    }

    /**
     * トランザクションのステータスを更新する。
     *
     * @param id                 トランザクションID
     * @param httpServletRequest HTTPリクエスト
     */
    @Override
    public void updateStatusTransaction(Long id, HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrderOptional = transOrderRepository.findById(id);
        String operatorId = userContext.getUser().getCompanyId();
        if (transOrderOptional.isPresent()) {
            TransOrder transOrder = transOrderOptional.get();
            if (transOrder.getCarrierOperatorId().equals(operatorId)) {
                transOrder.setStatus(DataBaseConstant.TransOrderStatus.CARRIER1_RE_REQUEST);
            } else if (transOrder.getCarrier2OperatorId().equals(operatorId)) {
                transOrder.setStatus(DataBaseConstant.TransOrderStatus.CARRIER2_RE_PROPOSAL);
            } else {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                        id));
            }

            transOrderRepository.save(transOrder);
            this.updateStatusTransaction(transOrder.getCnsLineItemByDateId(), transOrder.getVehicleAvbResourceId(),
                DataBaseConstant.VehicleAvbResourceItem.Status.AWAITING_CONFIRMATION);
            TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(),
                transOrder.getTransportPlanItemId(), transOrder.getStatus());
            transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);

        } else {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        }

    }

    /**
     * キャリアのトランザクションを承認する。
     *
     * @param id                 トランザクションID
     * @param approval           承認フラグ
     * @param httpServletRequest HTTPリクエスト
     */
    @Override
    public void updateStatusTransactionCarrierApproval(Long id, boolean approval,
        HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrderOptional = transOrderRepository.findById(id);
        String operatorId = userContext.getUser().getCompanyId();
        Integer statusItem;
        if (transOrderOptional.isPresent()) {
            TransOrder transOrder = transOrderOptional.get();
            if (transOrder.getCarrierOperatorId().equals(operatorId)) {
                transOrder.setStatus(approval ? DataBaseConstant.TransOrderStatus.CARRIER1_APPROVE
                    : DataBaseConstant.TransOrderStatus.CARRIER1_REJECT);
            } else if (transOrder.getCarrier2OperatorId().equals(operatorId)) {
                transOrder.setStatus(approval ? DataBaseConstant.TransOrderStatus.CARRIER2_APPROVE
                    : DataBaseConstant.TransOrderStatus.CARRIER2_REJECT);
            } else {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                        id));
            }
            if (approval) {
                try {
                    TrspPlanLineItemRequest trspPlan = convertRequestDemo(transOrder);
                    List<String> departureSpaceList = new ArrayList<>();
                    List<String> arrivalSpaceList = new ArrayList<>();
                    String trailerGiais = "";
                    Long vehicleAvbResourceId = transOrder.getVehicleAvbResourceId();
                    if (null != vehicleAvbResourceId) {
                        trailerGiais = vehicleAvbResourceItemRepository.findByVehicleAvbResourceId(vehicleAvbResourceId)
                            .stream()
                            .map(VehicleAvbResourceItem::getGiai)
                            .collect(Collectors.joining(","));
                        List<VehicleDiagramMobilityHubResponseDTO> vehicleDiagramList = vehicleDiagramMobilityHubCustomRepository.findAllByOperationIdAndVehicleType(
                            vehicleAvbResourceId, 2
                        );
                        if (!BaseUtil.isEmpty(vehicleDiagramList, null, 1)) {
                            departureSpaceList = vehicleDiagramList.stream()
                                .filter(vehicleDiagramMobilityHubResponseDTO ->
                                    Objects.nonNull(vehicleDiagramMobilityHubResponseDTO.getType())
                                        && 0 == vehicleDiagramMobilityHubResponseDTO.getType())
                                .map(VehicleDiagramMobilityHubResponseDTO::getSlotId).collect(Collectors.toList());
                            arrivalSpaceList = vehicleDiagramList.stream()
                                .filter(vehicleDiagramMobilityHubResponseDTO ->
                                    Objects.nonNull(vehicleDiagramMobilityHubResponseDTO.getType())
                                        && 1 == vehicleDiagramMobilityHubResponseDTO.getType())
                                .map(VehicleDiagramMobilityHubResponseDTO::getSlotId).collect(Collectors.toList());
                        }

                    }
                    ResponseEntity<String> response = sendOperationRequest(vehicleAvbResourceId.toString(),
                        transOrder.getId().toString(), transOrder.getCarrierOperatorCode(),
                        transOrder.getCarrier2OperatorCode(),
                        transOrder.getDepartureFrom().toString(), departureSpaceList,
                        transOrder.getArrivalTo().toString(), arrivalSpaceList, trailerGiais, trspPlan);
                } catch (Throwable throwable) {
                    logger.error("ERROR call approve transOrder: " + throwable.getMessage());
                }
            }
            statusItem = approval ? DataBaseConstant.VehicleAvbResourceItem.Status.AWAITING_CONFIRMATION
                : DataBaseConstant.VehicleAvbResourceItem.Status.CARRIER_CANCEL;
            transOrderRepository.save(transOrder);
            this.updateStatusTransaction(transOrder.getCnsLineItemByDateId(), transOrder.getVehicleAvbResourceId(),
                statusItem);
            TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(),
                transOrder.getTransportPlanItemId(), transOrder.getStatus());
            transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);

        } else {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        }
    }

    /**
     * トランザクションのトレイラIDを検索する。
     *
     * @param transOrder トランザクション
     * @return トランザクションのトレイラID
     */
    private TrspPlanLineItemResponseDTO getTrspPlanLineItemInfo(TransOrder transOrder) {
        TrspPlanLineItemResponseDTO trspPlanLineItemResponseDTO = new TrspPlanLineItemResponseDTO();
        CnsLineItemResponseDTO cnsLineItemResponseDTO = new CnsLineItemResponseDTO();
        cnsLineItemResponseDTO = getCnsLineItem(transOrder);
        trspPlanLineItemResponseDTO = getTrspPlanLineItem(cnsLineItemResponseDTO);
        return trspPlanLineItemResponseDTO;
    }

    /**
     * コンテナIDを検索する。
     *
     * @param transOrder トランザクション
     * @return コンテナID
     */
    private CnsLineItemResponseDTO getCnsLineItem(TransOrder transOrder) {

        if (transOrder == null || transOrder.getCnsLineItemId() == null) {
            return null;
        }
        Long cnsLineItemId = transOrder.getCnsLineItemId();
        String cnsLineItemSql = "SELECT " +
            "line_item_num_id, " +
            "sev_ord_num_id, " +
            "cnsg_crg_item_num_id, " +
            "buy_assi_item_cd, " +
            "sell_assi_item_cd, " +
            "wrhs_assi_item_cd, " +
            "item_name_txt, " +
            "gods_idcs_in_ots_pcke_name_txt, " +
            "num_of_istd_untl_quan, " +
            "num_of_istd_quan, " +
            "sev_num_unt_cd, " +
            "istd_pcke_weig_meas, " +
            "sev_weig_unt_cd, " +
            "istd_pcke_vol_meas, " +
            "sev_vol_unt_cd, " +
            "istd_quan_meas, " +
            "cnte_num_unt_cd, " +
            "dcpv_trpn_pckg_txt, " +
            "pcke_frm_cd, " +
            "pcke_frm_name_cd, " +
            "crg_hnd_trms_spcl_isrs_txt, " +
            "glb_retb_asse_id, " +
            "totl_rti_quan_quan, " +
            "chrg_of_pcke_ctrl_num_unt_amnt, " +
            "trsp_plan_line_item_id " +
            "FROM cns_line_item WHERE id = ?";

        List resultList = entityManager.createNativeQuery(cnsLineItemSql)
            .setParameter(1, cnsLineItemId)
            .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        Object[] result = (Object[]) resultList.get(0);

        return CnsLineItemResponseDTO.fromResult(result);
    }

    /**
     * トランザクションのトレイラIDを検索する。
     *
     * @param cnsLineItemResponseDTO コンテナID
     * @return トランザクションのトレイラID
     */
    private TrspPlanLineItemResponseDTO getTrspPlanLineItem(
        CnsLineItemResponseDTO cnsLineItemResponseDTO) {
        if (cnsLineItemResponseDTO == null || cnsLineItemResponseDTO.getTrspPlanLineItemId() == null) {
            return null;
        }
        String trspPlanLineItemSql = "SELECT " +
            "dsed_cll_from_date, " +
            "dsed_cll_to_date, " +
            "dsed_cll_from_time, " +
            "dsed_cll_to_time, " +
            "dsed_cll_time_trms_srvc_rqrm_cd, " +
            "aped_arr_from_date, " +
            "aped_arr_to_date, " +
            "cnsg_prty_head_off_id " +
            "FROM trsp_plan_line_item WHERE id = ?";

        List resultList = entityManager.createNativeQuery(trspPlanLineItemSql)
            .setParameter(1, cnsLineItemResponseDTO.getTrspPlanLineItemId())
            .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        Object[] result = (Object[]) resultList.get(0);
        return TrspPlanLineItemResponseDTO.fromResult(result);
    }

    /**
     * トランザクションのリクエストを変換する。
     *
     * @param transOrder トランザクション
     * @return トランザクションリクエスト
     */
    public TrspPlanLineItemRequest convertRequestDemo(TransOrder transOrder) {
        TrspPlanLineItemResponseDTO trspPlanLineItemResponseDTO = new TrspPlanLineItemResponseDTO();
        trspPlanLineItemResponseDTO = getTrspPlanLineItemInfo(transOrder);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        TrspIsrDTO trspIsr = new TrspIsrDTO();
        trspIsr.setTrspInstructionId(transOrder.getTrspInstructionId());
        String dateToSend = transOrder.getTransportDate().format(formatter);

        TrspSrvcDTO trspSrvc = new TrspSrvcDTO();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        trspIsr.setTrspInstructionDateSubmDttm(dateToSend);
        if (trspPlanLineItemResponseDTO.getDsedCllFromDate() != null) {
            trspSrvc.setDsedCllFromDate(dateToSend);
        }
        if (trspPlanLineItemResponseDTO.getDsedCllToDate() != null) {
            trspSrvc.setDsedCllToDate(dateToSend);
        }
        if (trspPlanLineItemResponseDTO.getDsedCllFromTime() != null) {
            trspSrvc.setDsedCllFromTime(
                trspPlanLineItemResponseDTO.getDsedCllFromTime().toLocalTime().format(timeFormatter));
        }
        if (trspPlanLineItemResponseDTO.getDsedCllToTime() != null) {
            trspSrvc.setDsedCllToTime(
                trspPlanLineItemResponseDTO.getDsedCllToTime().toLocalTime().format(timeFormatter));
        }
        trspSrvc.setDsedCllTimeTrmsSrvcRqrmCd("01");
        if (trspPlanLineItemResponseDTO.getApedArrFromDate() != null) {
            trspSrvc.setApedArrFromDate(dateToSend);
        }
        if (trspPlanLineItemResponseDTO.getApedArrToDate() != null) {
            trspSrvc.setApedArrToDate(dateToSend);
        }
        trspSrvc.setApedArrFromTimePrfmDttm("1000");

        trspSrvc.setServiceNo("12345678901234567890");
        trspSrvc.setServiceName(transOrder.getServiceName());
        trspSrvc.setServiceStrtDate(dateToSend);
        trspSrvc.setServiceStrtTime("18:00");
        trspSrvc.setServiceEndDate(dateToSend);
        trspSrvc.setServiceEndTime("22:00");
        trspSrvc.setFreightRate(BigDecimal.valueOf(150000));

        CnsDTO cns = new CnsDTO();
        cns.setIstdTotlPcksQuan(BigDecimal.valueOf(10));
        cns.setIstdTotlWeigMeas(BigDecimal.valueOf(99999999999.999));
        cns.setIstdTotlVolMeas(BigDecimal.valueOf(99999999.999));
        cns.setIstdTotlUntlQuan(BigDecimal.valueOf(1));

        CnsLineItemDTO cnsLineItem = new CnsLineItemDTO();
        cnsLineItem.setLineItemNumId("1234567890");
        cnsLineItem.setNumOfIstdUntlQuan(BigDecimal.valueOf(1));
        cnsLineItem.setNumOfIstdQuan(BigDecimal.valueOf(1));
        cnsLineItem.setIstdPckeWeigMeas(BigDecimal.valueOf(999999999.999));
        cnsLineItem.setIstdPckeVolMeas(BigDecimal.valueOf(9999999.9999));
        cnsLineItem.setIstdQuanMeas(BigDecimal.valueOf(99999999999.9999));
        cnsLineItem.setTotlRtiQuanQuan(BigDecimal.valueOf(1));
        cnsLineItem.setChrgOfPckeCtrlNumUntAmnt(BigDecimal.valueOf(1));

        CnsgPrtyDTO cnsgPrty = new CnsgPrtyDTO();
        cnsgPrty.setCnsgPrtyHeadOffId(trspPlanLineItemResponseDTO.getCnsgPrtyHeadOffId());
        cnsgPrty.setCnsgPrtyBrncOffId("01");

        TrspRqrPrtyDTO trspRqrPrty = new TrspRqrPrtyDTO();
        trspRqrPrty.setTrspRqrPrtyHeadOffId(BaseUtil.getShipperCid(transOrder.getShipperOperatorCode()));

        CneePrtyDTO cneePrty = new CneePrtyDTO();
        cneePrty.setCneePrtyHeadOffId("993000001");

        RoadCarrDTO roadCarr = new RoadCarrDTO();
        roadCarr.setTrspCliPrtyHeadOffId(BaseUtil.getCarrierCid(transOrder.getCarrierOperatorCode()));
        roadCarr.setTrspCliPrtyBrncOffId("01");

        ShipFromPrtyDTO shipFromPrty = new ShipFromPrtyDTO();
        shipFromPrty.setGlnPrtyId(BaseUtil.getGlnStart(null));

        ShipToPrtyDTO shipToPrty = new ShipToPrtyDTO();
        shipToPrty.setGlnPrtyId(BaseUtil.getGlnEnd(null));

        TrspPlanLineItemRequest trspPlan = new TrspPlanLineItemRequest();
        trspPlan.setTrspIsr(trspIsr);
        trspPlan.setTrspSrvc(trspSrvc);
        trspPlan.setCns(cns);
        trspPlan.setCnsLineItem(List.of(cnsLineItem));
        trspPlan.setCnsgPrty(cnsgPrty);
        trspPlan.setTrspRqrPrty(trspRqrPrty);
        trspPlan.setCneePrty(cneePrty);
        trspPlan.setRoadCarr(roadCarr);
        trspPlan.setShipFromPrty(List.of(shipFromPrty));
        trspPlan.setShipToPrty(List.of(shipToPrty));
        return trspPlan;
    }

    /**
     * オペレーションリクエストを送信する。
     *
     * @param operationId          オペレーションID
     * @param proposeId            提案ID
     * @param fromCid              出発CID
     * @param toCid                到着CID
     * @param departureMh          出発MH
     * @param departureMhSpaceList 出発MHスペースリスト
     * @param arrivalMh            到着MH
     * @param arrivalMhSpaceList   到着MHスペースリスト
     * @param trailerGiais         トレイラギアリスト
     * @param requestDto           リクエストDTO
     * @return レスポンスエンティティ
     */
    public ResponseEntity<String> sendOperationRequest(
        String operationId, String proposeId,
        String fromCid, String toCid,
        String departureMh, List<String> departureMhSpaceList,
        String arrivalMh, List<String> arrivalMhSpaceList,
        String trailerGiais, TrspPlanLineItemRequest requestDto) {

        Map<String, Object> paramRequests = new HashMap<>();
        paramRequests.put("from_cid", BaseUtil.getCarrierCid(fromCid));
        paramRequests.put("to_cid", BaseUtil.getCarrierCid(toCid));
        paramRequests.put("departure_mh", BaseUtil.getGlnStart(departureMh));
        paramRequests.put("departure_mh_space_list", BaseUtil.getMhSpaceList(departureMhSpaceList, true));
        paramRequests.put("arrival_mh_space_list", BaseUtil.getMhSpaceList(arrivalMhSpaceList, false));
        paramRequests.put("arrival_mh", BaseUtil.getGlnStart(arrivalMh));
        paramRequests.put("trailer_giais", BaseUtil.getTrailerGiaList("")); //TODO: trailer

        UriComponentsBuilder builder = BaseUtil.convertURL(
            API_URL + operationId + "/propose/" + proposeId + "/handover_info",
            paramRequests);

        requestDto.getCneePrty().setCneePrtyHeadOffId(requestDto.getTrspRqrPrty().getTrspRqrPrtyHeadOffId());

        String endTime = requestDto.getTrspSrvc().getServiceStrtTime().replace(":", "");
        requestDto.getTrspSrvc().setApedArrFromDate(requestDto.getTrspSrvc().getServiceStrtDate());
        requestDto.getTrspSrvc().setApedArrFromTimePrfmDttm(endTime);
        String dateStr = requestDto.getTrspSrvc().getServiceStrtDate() + " " + endTime;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
        LocalDateTime time = LocalDateTime.parse(dateStr, inputFormatter).plusHours(2);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

        requestDto.getTrspSrvc().setApedArrToDate(time.format(dateFormatter));
        requestDto.getTrspSrvc().setApedArrToTimePrfmDttm(time.format(timeFormatter));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TrspPlanLineItemRequest> requestEntity = new HttpEntity<>(requestDto, headers);
        String url = builder.toUriString();
        log.info("DEBUG: IX API-034 url: " + url);
        log.info("DEBUG: IX API-034 body: " + BaseUtil.makeString(requestDto));
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            log.info("DEBUG: IX API-034 Successfully called IX 034 for operationId: {}, response: {}", operationId,
                response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while calling API: " + e.getMessage());
        }

        return response;
    }

    /**
     * キャリアのトランザクションを支払いキャンセルする。
     *
     * @param id                 トランザクションID
     * @param httpServletRequest HTTPリクエスト
     */
    @Override
    public void updateStatusTransactionCarrierPayment(Long id, HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrderOptional = transOrderRepository.findById(id);
        String operatorId = userContext.getUser().getCompanyId();
        if (transOrderOptional.isPresent()) {
            TransOrder transOrder = transOrderOptional.get();
            if (transOrder.getCarrierOperatorId().equals(operatorId)) {
                transOrder.setStatus(DataBaseConstant.TransOrderStatus.CARRIER1_CANCEL);
            } else if (transOrder.getCarrier2OperatorId().equals(operatorId)) {
                transOrder.setStatus(DataBaseConstant.TransOrderStatus.CARRIER2_CANCEL);
            } else {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                        id));
            }

            transOrderRepository.save(transOrder);
            this.updateStatusTransaction(transOrder.getCnsLineItemByDateId(), transOrder.getVehicleAvbResourceId(),
                DataBaseConstant.VehicleAvbResourceItem.Status.CARRIER_CANCEL);
            TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(),
                transOrder.getTransportPlanItemId(), transOrder.getStatus());
            transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);

        } else {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        }
    }

    /**
     * キャリアのトランザクションを契約する。
     *
     * @param id                 トランザクションID
     * @param approval           承認フラグ
     * @param httpServletRequest HTTPリクエスト
     */
    @Override
    public void updateStatusTransactionCarrierContract(Long id, boolean approval,
        HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrderOptional = transOrderRepository.findById(id);

        if (transOrderOptional.isPresent()) {
            TransOrder transOrder = transOrderOptional.get();
            transOrder.setStatus(approval ? DataBaseConstant.TransOrderStatus.MAKE_CONTRACT
                : DataBaseConstant.TransOrderStatus.DECLINE_CONTRACT);

            Integer statusItem = approval ? DataBaseConstant.VehicleAvbResourceItem.Status.CONTRACT
                : DataBaseConstant.VehicleAvbResourceItem.Status.CARRIER_CANCEL;
            transOrderRepository.save(transOrder);
            this.updateStatusTransaction(transOrder.getCnsLineItemByDateId(), transOrder.getVehicleAvbResourceId(),
                statusItem);
            TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(),
                transOrder.getTransportPlanItemId(), transOrder.getStatus());
            transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);

        } else {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        }
    }

    /**
     * キャリアのトランザクションを輸送する。
     *
     * @param id                 トランザクションID
     * @param requestStatus      リクエストステータス
     * @param httpServletRequest HTTPリクエスト
     */
    @Override
    public void updateStatusTransactionCarrierTransport(Long id, String requestStatus,
        HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrderOptional = transOrderRepository.findById(id);

        if (transOrderOptional.isPresent()) {
            TransOrder transOrder = transOrderOptional.get();
            if (requestStatus.equals("start") || requestStatus.equals("complete")) {
                Integer status =
                    requestStatus.equals("start") ? DataBaseConstant.VehicleAvbResourceItem.Status.TRANSPORT
                        : DataBaseConstant.VehicleAvbResourceItem.Status.COMPLETED;
                Integer transOrderStatus =
                    requestStatus.equals("start") ? DataBaseConstant.TransOrderStatus.START_TRANSPORT
                        : DataBaseConstant.TransOrderStatus.COMPLETE_TRANSPORT;
                transOrder.setStatus(transOrderStatus);
                transOrderRepository.save(transOrder);
                this.updateStatusTransaction(transOrder.getCnsLineItemByDateId(), transOrder.getVehicleAvbResourceId(),
                    status);
                TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                    transOrder.getVehicleDiagramItemTrailerId(), transOrderStatus, transOrder.getTransportPlanItemId(),
                    transOrderStatus);
                transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
            }


        } else {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        }
    }

    /**
     * キャリアのトランザクションをページングで取得する。
     *
     * @param searchRequest トランザクションキャリア検索リクエスト
     * @return ページングされたトランザクションのページレスポンス
     */
    @Override
    public PageResponseDTO<TransOrderDTO> getPagedCarrierProposal(CarrierTransportProposalSearch searchRequest) {
        List<Integer> listStatus = switch (searchRequest.getStatus()) {
            case ParamConstant.TransOrderSearchStatus.PROPOSE -> List.of(
                DataBaseConstant.TransOrderStatus.CARRIER1_REQUEST,
                DataBaseConstant.TransOrderStatus.CARRIER1_RE_REQUEST,
                DataBaseConstant.TransOrderStatus.CARRIER2_PROPOSAL,
                DataBaseConstant.TransOrderStatus.CARRIER2_RE_PROPOSAL);
            case ParamConstant.TransOrderSearchStatus.APPROVAL -> List.of(
                DataBaseConstant.TransOrderStatus.CARRIER1_APPROVE,
                DataBaseConstant.TransOrderStatus.CARRIER2_APPROVE);
            default -> Collections.emptyList();
        };
        Pageable pageable = PageRequest.of(Integer.parseInt(searchRequest.getPage()) - 1,
            Integer.parseInt(searchRequest.getLimit()));
        Page<TransOrder> transOrderPage = transOrderRepository.getPagedByStatusAndTransType(listStatus,
            DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST, pageable);
        Page<TransOrderDTO> transOrderDTOPage = transOrderPage.map(transOrderMapper::toDTO);
        return new PageResponseDTO<>(transOrderDTOPage);
    }

    /**
     * キャリアの提案アイテムを取得する。
     *
     * @param transOrderId トランザクションID
     * @return キャリア提案アイテムレスポンスDTO
     */
    @Override
    public CarrierProposalItemResponseDTO getCarrierProposalItem(Long transOrderId) {
        return transOrderRepository.getCarrierProposalItem(transOrderId);
    }

    /**
     * トランザクションの詳細を取得する。
     *
     * @param id トランザクションID
     * @return トランザクション詳細レスポンスDTO
     */
    @Override
    public TransactionDetailResponseDTO getDetailTransaction(String id) {
        Long parsedId;
        try {
            parsedId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new NextWebException(new NextAPIError(HttpStatus.FORBIDDEN,
                SoaResponsePool.get(MessageConstant.Validate.VALID_POSITIVE_NUMBER), id));
        }
        TransactionCarrierDTO childOrder = transOrderRepository.getDetailTransaction(parsedId);
        TransactionDetailResponseDTO transactionDetailResponseDTO = transactionDetailResponseMapper.mapToTransactionDetailResponse(
            childOrder);
        if (childOrder != null && childOrder.getTransType() == DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST) {
            TransactionCarrierDTO parentOrder = transOrderRepository.getDetailTransaction(
                childOrder.getParentOrderId());
            transactionDetailResponseDTO.setParentOrder(parentOrder);
        }
        return transactionDetailResponseDTO;
    }

    /**
     * シッパーのトランザクションを挿入する。
     *
     * @param isNotIX            非IXフラグ
     * @param request            キャリアプランリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 挿入されたトランザクションのID
     */
    @Override
    public CarrierOperatorPlansInsertResponse carrierTransactionShipper(boolean isNotIX,
        CarrierOperatorPlansRequest request, HttpServletRequest httpServletRequest) {
        User user = userContext.getUser();

        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<CarrierOperatorPlansRequest> requestEntity = new HttpEntity<>(request, headers);
        isNotIX = isNotIX || user.isDebug();
        Optional<CnsLineItemByDate> cnsLineItemByDate = cnsLineItemByDateRepository.findById(
            Long.parseLong(request.getTrspPlanId()));
        if (cnsLineItemByDate.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                    request.getTrspPlanId()));
        }
        Optional<VehicleAvbResourceItem> vehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(
            Long.parseLong(request.getOperationId()));
        if (vehicleAvbResourceItem.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                    request.getOperationId()));
        }
        request.setTrspOpTrailerId(String.valueOf(vehicleAvbResourceItem.get().getVehicleDiagramItemTrailerId()));
        CarrierOperatorPlansInsertResponse carrierOperatorPlansInsertResponse = null;
        if (isNotIX) {
            ResponseEntity<CarrierOperatorPlansInsertResponse> response = restTemplate.exchange(
                CARRIER_TRANSACTION_SHIPPER_ENDPOINT, HttpMethod.POST, requestEntity,
                CarrierOperatorPlansInsertResponse.class);
            carrierOperatorPlansInsertResponse = response.getBody();
        } else {
            String url = IX_SERVICE_OPERATION_REQUEST_ENDPOINT
                + "propose?"
                + "from_cid=" + BaseUtil.getCarrierCid(cnsLineItemByDate.get().getOperatorCode())
                + "&to_cid=" + BaseUtil.getCarrierCid(vehicleAvbResourceItem.get().getOperatorCode());
            logger.info("DEBUG: IX API-031 url: " + url);
            logger.info("DEBUG: IX API-031 body: " + BaseUtil.makeString(request));
            ResponseEntity<IXOperationRequestPropose> response = restTemplate.exchange(url, HttpMethod.POST,
                requestEntity, IXOperationRequestPropose.class);
            logger.info("DEBUG: IX API-031 Successfully called IX 031 for response: {}", response);
            carrierOperatorPlansInsertResponse.setProposeId(response.getBody().getIxOperationRequest().getProposeId());
        }

        //Update status and insert free_word
        Optional<TransOrder> transOrder = transOrderRepository.findById(
            Long.parseLong(carrierOperatorPlansInsertResponse.getProposeId()));
        if (transOrder.isPresent()) {
            if (userContext.getUser().getCompanyId().equals(cnsLineItemByDate.get().getOperatorId())) {
                transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.CARRIER1_REQUEST);
            } else if (userContext.getUser().getCompanyId().equals(vehicleAvbResourceItem.get().getOperatorId())) {
                transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.CARRIER2_PROPOSAL);
            }
            transOrder.get().setArrivalTime(vehicleAvbResourceItem.get().getArrivalTime());
            transOrder.get().setDepartureTime(vehicleAvbResourceItem.get().getDepartureTime());
            transOrder.get().setVehicleDiagramItemId(vehicleAvbResourceItem.get().getVehicleDiagramItemId());

            if (transOrder.get().getParentOrderId() != null) {
                Optional<TransOrder> parentOrder = transOrderRepository.findById(transOrder.get().getParentOrderId());
                parentOrder.ifPresent(order -> transOrder.get().setFreeWord(
                    transMatchingService.insertFreeWordTransMatching(cnsLineItemByDate.get(),
                        vehicleAvbResourceItem.get()) + order.getServiceName()));
            } else {
                transOrder.get().setFreeWord(transMatchingService.insertFreeWordTransMatching(cnsLineItemByDate.get(),
                    vehicleAvbResourceItem.get()));
            }

            if (request.getNegotiation() != null) {
                transOrder.get()
                    .setNegotiationData(negotiationDataMapper.mapNegotiationDataCarrier(request.getNegotiation()));
            }
            if (Objects.equals(cnsLineItemByDate.get().getIsEmergency(), DataBaseConstant.IS_EMERGENCY)) {
                transOrder.get().setIsEmergency(DataBaseConstant.IS_EMERGENCY);
            }
            TransOrder transOrderInsert = transOrderRepository.save(transOrder.get());
            this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
                transOrder.get().getVehicleAvbResourceId(), transOrderInsert.getStatus());
            TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                transOrderInsert.getVehicleDiagramItemTrailerId(), transOrderInsert.getStatus(),
                transOrderInsert.getTransportPlanItemId(), transOrderInsert.getStatus());
            transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
            TransMatching transMatching = transMatchingRepository.getTransMatchingByCnsLineItemByDateIdAndVehicleAvbResourceItemId(
                cnsLineItemByDate.get().getId(), vehicleAvbResourceItem.get().getId());
            if (transMatching != null) {
                updateStatusAfterPropose(transMatching.getId(), transMatching.getCnsLineItemByDateId(),
                    transMatching.getVehicleAvbResourceId());
            }
        }

        return carrierOperatorPlansInsertResponse;
    }

    /**
     * キャリアの輸送提案をIXに作成する。
     *
     * @param requestData        トランザクションキャリアリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 作成された提案のID
     */
    @Override
    @Transactional
    public BigInteger createCarrierTransportProposalIX(TransactionCarrierRequest requestData,
        HttpServletRequest httpServletRequest) {
        User user = userContext.getUser();
        Optional<VehicleAvbResourceItem> vehicleAvbResourceItem = vehicleAvbResourceItemRepository.findById(
            Long.parseLong(requestData.getVehicleAvbResourceItemId()));
        if (vehicleAvbResourceItem.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                    requestData.getVehicleAvbResourceItemId()));
        }
        CarrierTransportProposalIXRequest ixRequest = new CarrierTransportProposalIXRequest();
        ixRequest.setTrspPlanId(requestData.getCnsLineItemByDateId());
        ixRequest.setOperationId(requestData.getVehicleAvbResourceItemId());
        ixRequest.setServiceNo(requestData.getServiceNo());
        ixRequest.setTrspOpDateTrmStrtDate(requestData.getDepartureDate());
        ixRequest.setGiaiNumber(requestData.getGiai());
        ixRequest.setTrspOpPlanDateTrmStrtTime(requestData.getDepartureTime());
        ixRequest.setReqAvbFromTimeOfCllTime(requestData.getCollectionTimeFrom());
        ixRequest.setReqAvbToTimeOfCllTime(requestData.getCollectionTimeTo());
        ixRequest.setTrspOpTrailerId(vehicleAvbResourceItem.get().getVehicleDiagramItemTrailerId().toString());
        if (!BaseUtil.isNull(requestData.getPrice())) {
            ixRequest.setReqFreightRate(new BigDecimal(requestData.getPrice()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<CarrierTransportProposalIXRequest> requestEntity = new HttpEntity<>(ixRequest, headers);
        Long orderId = null;
        //call to IX-021
        Boolean isNotIX = !(requestData.getIsNotIX() == null || !requestData.getIsNotIX());
        isNotIX = isNotIX || user.isDebug();
        if (isNotIX) {
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(
                SHIPPER_OPERATIONS_PLANS_PROPOSE_ENDPOINT);
            ResponseEntity<CarrierTransportProposalIXResponseDTO> response = restTemplate.exchange(
                uriComponentsBuilder.build().encode().toString(), HttpMethod.POST, requestEntity,
                CarrierTransportProposalIXResponseDTO.class);
            if (response.getBody() != null && response.getBody().getProposeId() != null) {
                orderId = response.getBody().getProposeId().longValue();
            }
        } else {
            orderId = insertShipperOperationReserveIX(requestData.getShipperCode(), requestData.getCarrierCode(),
                requestData.getCid(), ixRequest).longValue();
        }

        if (orderId != null) {
            Optional<TransOrder> transOrder = transOrderRepository.findById(orderId);
            if (transOrder.isPresent()) {
                if (transOrder.get().getTransType() == 1) {
                    if (Objects.equals(transOrder.get().getCarrierOperatorId(), userContext.getUser().getCompanyId())) {
                        transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.CARRIER1_REQUEST);
                    } else if (Objects.equals(transOrder.get().getCarrier2OperatorId(),
                        userContext.getUser().getCompanyId())) {
                        transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.CARRIER2_PROPOSAL);
                    }
                } else {
                    if (userContext.getUser().isShipper()) {
                        transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.SHIPPER_REQUEST);
                    } else {
                        transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.CARRIER_REQUEST);
                    }
                }
                transOrder.get().setArrivalTime(vehicleAvbResourceItem.get().getArrivalTime());
                transOrder.get().setDepartureTime(vehicleAvbResourceItem.get().getDepartureTime());
                transOrder.get().setVehicleDiagramItemId(vehicleAvbResourceItem.get().getVehicleDiagramItemId());
                Optional<CnsLineItemByDate> cnsLineItemByDate = cnsLineItemByDateRepository.findById(
                    transOrder.get().getCnsLineItemByDateId());
                if (transOrder.get().getParentOrderId() != null) {
                    Optional<TransOrder> parentOrder = transOrderRepository.findById(
                        transOrder.get().getParentOrderId());
                    parentOrder.ifPresent(order -> transOrder.get().setFreeWord(
                        transMatchingService.insertFreeWordTransMatching(cnsLineItemByDate.get(),
                            vehicleAvbResourceItem.get()) + order.getServiceName()));
                } else {
                    transOrder.get().setFreeWord(
                        transMatchingService.insertFreeWordTransMatching(cnsLineItemByDate.get(),
                            vehicleAvbResourceItem.get()));
                }
                transOrder.get()
                    .setNegotiationData(negotiationDataMapper.mapNegotiationData(requestData.getNegotiationDTO()));
                if (Objects.equals(cnsLineItemByDate.get().getIsEmergency(), DataBaseConstant.IS_EMERGENCY)) {
                    transOrder.get().setIsEmergency(DataBaseConstant.IS_EMERGENCY);
                }
                transOrderRepository.save(transOrder.get());
            }

            //Update status after order
            if (transOrder.get().getTransMatchingId() != null) {
                updateStatusAfterPropose(transOrder.get().getTransMatchingId(),
                    transOrder.get().getCnsLineItemByDateId(), transOrder.get().getVehicleAvbResourceId());
            }
            TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                transOrder.get().getVehicleDiagramItemTrailerId(), transOrder.get().getStatus(),
                transOrder.get().getTransportPlanItemId(), transOrder.get().getStatus());
            transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        }

        return orderId != null ? BigInteger.valueOf(orderId) : null;
    }

    /**
     * キャリアのトランザクションを承認する。
     *
     * @param isNotIX            非IXフラグ
     * @param id                 トランザクションID
     * @param request            キャリア操作承認リクエストDTO
     * @param httpServletRequest HTTPリクエスト
     * @return 承認された提案のID
     */
    @Override
    public CarrierOperatorPlansInsertResponse carrierTransactionShipperIdApproval(boolean isNotIX, String id,
        CarrierOperationApprovalRequestDTO request, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        Optional<TransOrder> transOrder = transOrderRepository.findById(Long.parseLong(id));
        request.setTrspPlanId(transOrder.get().getTrspInstructionId());

        HttpEntity<CarrierOperationApprovalRequestDTO> requestEntity = new HttpEntity<>(request, headers);
        if (isNotIX || userContext.getUser().isDebug()) {
            String finalUrl = CARRIER_TRANSACTION_SHIPPER_ID_APPROVAL_ENDPOINT.replace("{operation_id}",
                    request.getOperationId())
                .replace("{propose_id}", id);
            ResponseEntity<CarrierOperationApprovalRequestDTO> response = restTemplate.exchange(finalUrl,
                HttpMethod.POST, requestEntity, CarrierOperationApprovalRequestDTO.class);
            CarrierOperatorPlansInsertResponse carrierOperatorPlansInsertResponse = new CarrierOperatorPlansInsertResponse();
            carrierOperatorPlansInsertResponse.setProposeId(response.getBody().getProposeId());
            return carrierOperatorPlansInsertResponse;
        } else {
            if (transOrder.isEmpty()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                        id));
            }
            String url = IX_SERVICE_OPERATION_REQUEST_ENDPOINT + request.getOperationId() + "/propose/" + id + "/reply"
                + "?from_cid=" + BaseUtil.getCarrierCid(transOrder.get().getCarrierOperatorCode())
                + "&to_cid=" + BaseUtil.getCarrierCid(transOrder.get().getCarrier2OperatorCode());
            logger.info("DEBUG: IX API-033 url: " + url);
            logger.info("DEBUG: IX API-033 body: " + BaseUtil.makeString(request));
            ResponseEntity<IXOperationRequestPropose> response = restTemplate.exchange(url, HttpMethod.POST,
                requestEntity, IXOperationRequestPropose.class);
            logger.info("DEBUG: IX API-033 Successfully called IX 033 for response: {}", response);
            CarrierOperatorPlansInsertResponse carrierOperatorPlansInsertResponse = new CarrierOperatorPlansInsertResponse();
            if (response.getBody().isResult()) {
                carrierOperatorPlansInsertResponse.setProposeId(id);
            }
            return carrierOperatorPlansInsertResponse;
        }
    }

    /**
     * キャリアのトランザクションを挿入する。
     *
     * @param isNotIX            非IXフラグ
     * @param id                 トランザクションID
     * @param request            キャリアプランリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 挿入されたトランザクションのID
     */
    @Override
    public CarrierOperatorPlansInsertResponse carrierTransactionShipperId(boolean isNotIX, String id,
        CarrierOperatorPlansRequest request, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<CarrierOperatorPlansRequest> requestEntity = new HttpEntity<>(request, headers);
        CarrierOperatorPlansInsertResponse carrierOperatorPlansInsertResponse = new CarrierOperatorPlansInsertResponse();
        if (isNotIX || userContext.getUser().isDebug()) {
            String finalUrl = CARRIER_TRANSACTION_SHIPPER_ID_ENDPOINT.replace("{operation_id}",
                    request.getOperationId())
                .replace("{propose_id}", id);
            ResponseEntity<CarrierOperatorPlansRequest> response = restTemplate.exchange(finalUrl, HttpMethod.PUT,
                requestEntity, CarrierOperatorPlansRequest.class);
            carrierOperatorPlansInsertResponse.setProposeId(response.getBody().getProposeId());
        } else {
            Optional<TransOrder> transOrder = transOrderRepository.findById(Long.parseLong(id));
            if (transOrder.isEmpty()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                        id));
            }
            String url = IX_SERVICE_OPERATION_REQUEST_ENDPOINT + request.getOperationId() + "/propose/" + id
                + "?from_cid=" + BaseUtil.getCarrierCid(transOrder.get().getCarrierOperatorCode())
                + "&to_cid=" + BaseUtil.getCarrierCid(transOrder.get().getCarrier2OperatorCode());
            log.info("DEBUG: IX API-032 url: " + url);
            log.info("DEBUG: IX API-032 body: " + BaseUtil.makeString(requestEntity));
            ResponseEntity<IXOperationRequestPropose> response = restTemplate.exchange(url, HttpMethod.PUT,
                requestEntity, IXOperationRequestPropose.class);
            logger.info("DEBUG: IX API-032 Successfully called IX 032 for response: {}", response);
            if (response.getBody().isResult()) {
                if (userContext.getUser().getCompanyId().equals(transOrder.get().getCarrier2OperatorId())) {
                    transOrderRepository.updateStatusById(Long.parseLong(id), 221);
                } else if (userContext.getUser().getCompanyId().equals(transOrder.get().getCarrierOperatorId())) {
                    transOrderRepository.updateStatusById(Long.parseLong(id), 211);
                }
                this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
                    transOrder.get().getVehicleAvbResourceId(), transOrder.get().getStatus());
                TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                    transOrder.get().getVehicleDiagramItemTrailerId(), transOrder.get().getStatus(),
                    transOrder.get().getTransportPlanItemId(), transOrder.get().getStatus());
                transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
                carrierOperatorPlansInsertResponse.setProposeId(id);
            }
        }

        Optional<TransOrder> transOrder = transOrderRepository.findById(Long.parseLong(id));
        if (transOrder.isPresent()) {
            String userCompanyId = userContext.getUser().getCompanyId();
            if (Objects.equals(transOrder.get().getCarrier2OperatorId(), userCompanyId)) {
                transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.CARRIER2_RE_PROPOSAL);
            } else if (Objects.equals(transOrder.get().getCarrierOperatorId(), userCompanyId)) {
                transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.CARRIER1_RE_REQUEST);
            }
            transOrderRepository.save(transOrder.get());
            this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
                transOrder.get().getVehicleAvbResourceId(), transOrder.get().getStatus());
            TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
                transOrder.get().getVehicleDiagramItemTrailerId(), transOrder.get().getStatus(),
                transOrder.get().getTransportPlanItemId(), transOrder.get().getStatus());
            transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        }
        return carrierOperatorPlansInsertResponse;
    }

    /**
     * シッパーの提案を承認する。
     *
     * @param requestData        トランザクションシッパー承認リクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 承認された提案のID
     */
    @Override
    @Transactional
    public Long approveShipperProposalIX(TransactionShipperApprovalRequest requestData,
        HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrder = transOrderRepository.findById(Long.parseLong(requestData.getPathId()));
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.Validate.TRANS_ORDER_NOT_FOUND),
                    requestData.getPathId())
            );
        }

        TransactionShipperApprovalIXRequest ixRequest = new TransactionShipperApprovalIXRequest();
        ixRequest.setApproval(requestData.getApproval());
        ixRequest.setOperationId(requestData.getVehicleAvbResourceId());

        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        HttpEntity<TransactionShipperApprovalIXRequest> requestEntity = new HttpEntity<>(ixRequest, headers);

        UriComponentsBuilder uriComponentsBuilder;
        if ((requestData.getIsNotIX() != null && requestData.getIsNotIX()) ||
            (userContext.getUser() != null && userContext.getUser().isDebug())) {
            uriComponentsBuilder = UriComponentsBuilder.fromUriString(SHIPPER_OPERATIONS_PLANS_PROPOSE_RELY_ENDPOINT);
        } else {
            String finalUrl = String.format(IX_SERVICE_RESERVE_PROPOSE_REPLY_ENDPOINT,
                requestData.getVehicleAvbResourceId(), requestData.getPathId());
            uriComponentsBuilder = UriComponentsBuilder.fromUriString(finalUrl)
                .queryParam(ParamConstant.SHIPPER_CID,
                    BaseUtil.getShipperCid(transOrder.get().getShipperOperatorCode()))
                .queryParam(ParamConstant.CARRIER_CID,
                    BaseUtil.getCarrierCid(transOrder.get().getCarrierOperatorCode()));
        }
        //Replaces {operation_id} and {propose_id} dynamically
        String apiEndpoint = uriComponentsBuilder.buildAndExpand(requestData.getVehicleAvbResourceId(),
            requestData.getPathId()).encode().toString();
        logger.info("DEBUG: IX API-024 url: " + uriComponentsBuilder.toUriString());
        logger.info("DEBUG: IX API-024 body: " + BaseUtil.makeString(ixRequest));
        Object response = restTemplate.exchange(apiEndpoint, HttpMethod.POST, requestEntity, Object.class);
        logger.info("DEBUG: IX API-024 Successfully called IX 022 for response: {}", response);

        Integer vehicleItemUpdateStatus = DataBaseConstant.VehicleAvbResourceItem.Status.AWAITING_CONFIRMATION;
        Integer cnsDateUpdateStatus = DataBaseConstant.CnsLineItemByDate.Status.AWAITING_CONFIRMATION;
        Integer orderUpdateStatus = DataBaseConstant.TransOrderStatus.CARRIER_APPROVE;
        if (Boolean.FALSE.equals(requestData.getApproval())) {
            vehicleItemUpdateStatus = DataBaseConstant.VehicleAvbResourceItem.Status.CARRIER_CANCEL;
            cnsDateUpdateStatus = DataBaseConstant.CnsLineItemByDate.Status.CARRIER_CANCEL;
            orderUpdateStatus = DataBaseConstant.TransOrderStatus.CARRIER_APPROVE_REJECT;
        }

        transOrder.get().setStatus(orderUpdateStatus);
        transOrderRepository.save(transOrder.get());

        if (transOrder.get().getVehicleAvbResourceItemId() != null) {
            vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(
                transOrder.get().getVehicleAvbResourceId(), vehicleItemUpdateStatus);
        }

        if (transOrder.get().getCnsLineItemByDateId() != null) {
            cnsLineItemByDateRepository.updateStatusById(transOrder.get().getCnsLineItemByDateId(),
                cnsDateUpdateStatus);
        }
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.get().getVehicleDiagramItemTrailerId(), transOrder.get().getStatus(),
            transOrder.get().getTransportPlanItemId(), transOrder.get().getStatus());
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);

        return Long.parseLong(requestData.getPathId());
    }

    /**
     * シッパーの提案を更新する。
     *
     * @param requestData        トランザクションシッパー更新リクエスト
     * @param paramId            トランザクションID
     * @param httpServletRequest HTTPリクエスト
     * @return 更新されたトランザクションのID
     */
    @Override
    public Long updateShipperProposalIX(TransactionShipperUpdateRequest requestData, String paramId,
        HttpServletRequest httpServletRequest) {
        Long transOrderId;
        try {
            transOrderId = Long.parseLong(paramId);
        } catch (NumberFormatException e) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(MessageConstant.Validate.VALID_NUMBER),
                    ParamConstant.Transaction.ID)
            );
        }
        Optional<TransOrder> transOrder = transOrderRepository.findById(transOrderId);
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.Validate.TRANS_ORDER_NOT_FOUND),
                    transOrderId)
            );
        }

        TransactionShipperUpdateIXRequest ixRequest = new TransactionShipperUpdateIXRequest();
        ixRequest.setTrspPlanId(requestData.getCnsLineItemByDateId());
        ixRequest.setOperationId(requestData.getVehicleAvbResourceItemId());
        ixRequest.setServiceNo(requestData.getServiceNo());
        ixRequest.setTrspOpDateTrmStrtDate(requestData.getDepartureDate());
        ixRequest.setGiaiNumber(requestData.getGiai());
        ixRequest.setTrspOpPlanDateTrmStrtTime(requestData.getDepartureTime());
        ixRequest.setReqAvbFromTimeOfCllTime(requestData.getCollectionTimeFrom());
        ixRequest.setReqAvbToTimeOfCllTime(requestData.getCollectionTimeTo());
        if (!BaseUtil.isNull(requestData.getPrice())) {
            ixRequest.setReqFreightRate(new BigDecimal(requestData.getPrice()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(APIConstant.AUTHORIZATION, httpServletRequest.getHeader(APIConstant.AUTHORIZATION));
        ixRequest.setGiaiNumber(BaseUtil.getGiai(""));
        HttpEntity<TransactionShipperUpdateIXRequest> requestEntity = new HttpEntity<>(ixRequest, headers);
        UriComponentsBuilder uriComponentsBuilder;
        if (requestData.getIsNotIX() != null && requestData.getIsNotIX()) {
            uriComponentsBuilder = UriComponentsBuilder.fromUriString(SHIPPER_OPERATIONS_PLANS_PROPOSE_UPDATE_ENDPOINT);
        } else {
            uriComponentsBuilder = UriComponentsBuilder.fromUriString(IX_SERVICE_RESERVE_PROPOSE_UPDATE_ENDPOINT)
                .queryParam(ParamConstant.SHIPPER_CID,
                    BaseUtil.getShipperCid(transOrder.get().getShipperOperatorCode()))
                .queryParam(ParamConstant.CARRIER_CID,
                    BaseUtil.getCarrierCid(transOrder.get().getCarrierOperatorCode()));
        }

        logger.info("DEBUG: IX API-023 url: " + uriComponentsBuilder.toUriString());
        logger.info("DEBUG: IX API-023 body: " + BaseUtil.makeString(ixRequest));
        String apiEndpoint = uriComponentsBuilder.buildAndExpand(requestData.getVehicleAvbResourceItemId(), paramId)
            .encode().toString();
        Object response = restTemplate.exchange(apiEndpoint, HttpMethod.PUT, requestEntity, Object.class);
        logger.info("DEBUG: IX API-023 response: " + response);

        transOrder = transOrderRepository.findById(transOrderId);
        transOrder.get().setStatus(DataBaseConstant.TransOrderStatus.SHIPPER_RE_REQUEST);
        transOrder.get().setUpdatedDate(LocalDateTime.now());
        transOrderRepository.save(transOrder.get());
        this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
            transOrder.get().getVehicleAvbResourceId(), transOrder.get().getStatus());
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.get().getVehicleDiagramItemTrailerId(), transOrder.get().getStatus(),
            transOrder.get().getTransportPlanItemId(), transOrder.get().getStatus());
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return Long.parseLong(paramId);
    }

    /**
     * トランザクションのステータスを更新する。
     *
     * @param id                 トランザクションID
     * @param status             ステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新されたトランザクションのID
     */
    @Override
    public Long updateOrderStatus(Long id, Integer status, HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrder = transOrderRepository.findById(id);
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        }
        if (status == DataBaseConstant.TransOrderStatus.CANCEL
            || status == DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT) {
            Integer statusOrder = status == DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT
                ? DataBaseConstant.VehicleAvbResourceItem.Status.PAYMENT
                : DataBaseConstant.VehicleAvbResourceItem.Status.CARRIER_CANCEL;
            this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
                transOrder.get().getVehicleAvbResourceId(), statusOrder);
        }
        transOrder.get().setStatus(status);
        transOrderRepository.save(transOrder.get());
        this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
            transOrder.get().getVehicleAvbResourceItemId(), transOrder.get().getStatus());
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.get().getVehicleDiagramItemTrailerId(), status, transOrder.get().getTransportPlanItemId(),
            status);
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return transOrder.get().getId();
    }

    /**
     * トランザクションの承認ステータスを更新する。
     *
     * @param id                 トランザクションID
     * @param approval           承認ステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新されたトランザクションのID
     */
    @Override
    public Long updateOrderApprovalStatus(Long id, boolean approval, HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrder = transOrderRepository.findById(id);
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        }
        Integer transOrderStatus = approval ? DataBaseConstant.TransOrderStatus.SHIPPER_APPROVE
            : DataBaseConstant.TransOrderStatus.SHIPPER_REJECT_PROPOSE;
        Integer status = approval ? DataBaseConstant.VehicleAvbResourceItem.Status.AWAITING_CONFIRMATION
            : DataBaseConstant.VehicleAvbResourceItem.Status.SHIPPER_CANCEL;
        transOrder.get().setStatus(transOrderStatus);
        transOrderRepository.save(transOrder.get());
        this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
            transOrder.get().getVehicleAvbResourceId(), status);
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.get().getVehicleDiagramItemTrailerId(), transOrderStatus,
            transOrder.get().getTransportPlanItemId(), transOrderStatus);
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return transOrder.get().getId();

    }

    /**
     * トランザクションの契約ステータスを更新する。
     *
     * @param id                 トランザクションID
     * @param approval           契約ステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新されたトランザクションのID
     */
    @Override
    public Long updateOrderContractStatus(Long id, boolean approval, HttpServletRequest httpServletRequest) {
        Optional<TransOrder> transOrder = transOrderRepository.findById(id);
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND), id));
        }
        Integer transOrderStatus = approval ? DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_CONTRACT
            : DataBaseConstant.TransOrderStatus.SHIPPER_REJECT_CONTRACT;
        Integer status = approval ? DataBaseConstant.VehicleAvbResourceItem.Status.CONTRACT
            : DataBaseConstant.VehicleAvbResourceItem.Status.SHIPPER_CANCEL;
        transOrder.get().setStatus(transOrderStatus);
        transOrderRepository.save(transOrder.get());
        this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
            transOrder.get().getVehicleAvbResourceId(), status);
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.get().getVehicleDiagramItemTrailerId(), status, transOrder.get().getTransportPlanItemId(),
            status);
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return transOrder.get().getId();

    }

    /**
     * シッパーのトランザクションステータスを更新する。
     *
     * @param id                 トランザクションID
     * @param status             ステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新されたトランザクションのID
     */
    @Override
    public Long updateShipperOrderStatus(String id, Integer status, HttpServletRequest httpServletRequest) {
        Long parsedId;
        try {
            parsedId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new NextWebException(new NextAPIError(HttpStatus.FORBIDDEN,
                SoaResponsePool.get(MessageConstant.Validate.VALID_POSITIVE_NUMBER), id));
        }
        Optional<TransOrder> transOrder = transOrderRepository.findById(parsedId);
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                    parsedId));
        }
        if (status == DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT
            || status == DataBaseConstant.TransOrderStatus.SHIPPER_CANCEL) {
            Integer statusOrder = status == DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT
                ? DataBaseConstant.VehicleAvbResourceItem.Status.PAYMENT
                : DataBaseConstant.VehicleAvbResourceItem.Status.SHIPPER_CANCEL;
            this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
                transOrder.get().getVehicleAvbResourceId(), statusOrder);
        }
        transOrder.get().setStatus(status);
        transOrderRepository.save(transOrder.get());
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.get().getVehicleDiagramItemTrailerId(), status, transOrder.get().getTransportPlanItemId(),
            status);
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return transOrder.get().getId();
    }

    /**
     * シッパーの契約ステータスを更新する。
     *
     * @param id                 トランザクションID
     * @param approval           契約ステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新されたトランザクションのID
     */
    @Override
    public Long updateShipperContractStatus(String id, boolean approval, HttpServletRequest httpServletRequest) {
        Long parsedId;
        try {
            parsedId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new NextWebException(new NextAPIError(HttpStatus.FORBIDDEN,
                SoaResponsePool.get(MessageConstant.Validate.VALID_POSITIVE_NUMBER), id));
        }
        Optional<TransOrder> transOrder = transOrderRepository.findById(parsedId);
        if (transOrder.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                    parsedId));
        }
        Integer transOrderStatus = approval ? DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_CONTRACT
            : DataBaseConstant.TransOrderStatus.SHIPPER_REJECT_CONTRACT;
        Integer statusContract = approval ? DataBaseConstant.VehicleAvbResourceItem.Status.CONTRACT
            : DataBaseConstant.VehicleAvbResourceItem.Status.SHIPPER_CANCEL;
        transOrder.get().setStatus(transOrderStatus);
        transOrderRepository.save(transOrder.get());
        this.updateStatusTransaction(transOrder.get().getCnsLineItemByDateId(),
            transOrder.get().getVehicleAvbResourceId(), statusContract);
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.get().getVehicleDiagramItemTrailerId(), transOrderStatus,
            transOrder.get().getTransportPlanItemId(), transOrderStatus);
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return transOrder.get().getId();
    }

    /**
     * トランザクションの検索を行う。
     *
     * @param transType        トランザクションタイプ
     * @param advanceStatus    進捗ステータス
     * @param status           ステータス
     * @param freeWord         フリーワード
     * @param temperatureRange 温度範囲
     * @param page             ページ
     * @param limit            制限
     * @param isEmergency      緊急ステータス
     * @return トランザクションのページ
     */
    @Override
    public Page<TransMatchingHeadResponse> getTransactionByTrailer(String transType, List<String> advanceStatus,
        String status, String freeWord, List<Integer> temperatureRange, int page, int limit, Integer isEmergency) {
        String companyId = userContext.getUser().getCompanyId();
        freeWord = transMatchingService.normalizeFreeword(freeWord);
        return transMatchingCustomRepository.getTransactionByTrailer(transType, advanceStatus, companyId, status,
            freeWord, temperatureRange, page - 1, limit, isEmergency);
    }

    private void updateStatusTransaction(Long shipperId, Long carrierId, Integer status) {
        Optional<CnsLineItemByDate> cnsLineItemByDate = cnsLineItemByDateRepository.findById(shipperId);
        if (cnsLineItemByDate.isPresent()) {
            cnsLineItemByDate.get().setStatus(status);
            cnsLineItemByDateRepository.save(cnsLineItemByDate.get());
        }

        vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(carrierId, status);
    }

    /**
     * トランザクションの作成を行う。
     *
     * @param requestData        トランザクションリクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return 作成されたトランザクションのID
     */
    @Override
    @Transactional
    public Long createCarrierTransportProposalFE(TransactionCarrierFERequest requestData,
        HttpServletRequest httpServletRequest) {
        requestData.validateTransTypeField();
        VehicleAvbResourceItem vehicleAvbResourceItem = vehicleAvbResourceItemService.findById(
            Long.parseLong(requestData.getVehicleAvbResourceItemId()));
        CnsLineItemByDate cnsLineItemByDate = cnsLineItemByDateService.findById(
            Long.parseLong(requestData.getCnsLineItemByDateId()));
        Integer orderStatus;
        Integer transType;
        Long parentOrderId = null;

        if (requestData.getTransType().equals(ParamConstant.TransType.CARRIER_SHIPPER.toString())) {
            orderStatus = DataBaseConstant.TransOrderStatus.CARRIER_PROPOSAL;
            transType = DataBaseConstant.TransOrder.TransType.SHIPPER_REQUEST;
        } else {
            orderStatus = DataBaseConstant.TransOrderStatus.CARRIER2_PROPOSAL;
            transType = DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST;
            parentOrderId = cnsLineItemByDate.getTransOrderId();
        }
        TransOrder transOrder = transOrderMapper.toTransOrder(cnsLineItemByDate, vehicleAvbResourceItem);
        transOrder.setTransType(transType);
        transOrder.setStatus(orderStatus);
        transOrder.setParentOrderId(parentOrderId);
        transOrder.setReqCnsLineItemId(
            requestData.getReqCnsLineItemId() != null ? Long.parseLong(requestData.getReqCnsLineItemId()) : null);
        transOrder.setCnsLineItemId(
            requestData.getCnsLineItemId() != null ? Long.parseLong(requestData.getCnsLineItemId()) : null);
        transOrder.setCnsLineItemByDateId(Long.parseLong(requestData.getCnsLineItemByDateId()));
        transOrder.setVehicleAvbResourceId(Long.parseLong(requestData.getVehicleAvbResourceId()));
        transOrder.setVehicleAvbResourceItemId(Long.parseLong(requestData.getVehicleAvbResourceItemId()));
        transOrder.setNegotiationData(negotiationDataMapper.mapNegotiationDataCarrier(requestData.getNegotiationDTO()));
        if (!BaseUtil.isNull(requestData.getPrice())) {
            transOrder.setProposePrice(new BigDecimal(requestData.getPrice().trim()));
        }
        if (!BaseUtil.isNull(requestData.getDepartureTime())) {
            transOrder.setProposeDepartureTime(
                DateTimeMapper.stringToLocalTime(requestData.getDepartureTime())
            );
        }
        if (!BaseUtil.isNull(requestData.getArrivalTime())) {
            transOrder.setProposeArrivalTime(
                DateTimeMapper.stringToLocalTime(requestData.getArrivalTime())
            );
        }
        transOrder.setFreeWord(
            transMatchingService.insertFreeWordTransMatching(cnsLineItemByDate, vehicleAvbResourceItem));
        TransMatching transMatching = transMatchingRepository.getTransMatchingByCnsLineItemByDateIdAndVehicleAvbResourceItemId(
            cnsLineItemByDate.getId(), vehicleAvbResourceItem.getId());
        if (transMatching != null) {
            updateStatusAfterPropose(transMatching.getId(), cnsLineItemByDate.getId(), vehicleAvbResourceItem.getId());
        } else {
            updateStatusAfterPropose(0L, cnsLineItemByDate.getId(), vehicleAvbResourceItem.getId());
        }
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(), transOrder.getTransportPlanItemId(),
            transOrder.getStatus());
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return transOrderRepository.save(transOrder).getId();
    }

    /**
     * キャリア2のトランザクションの作成を行う。
     *
     * @param requestData トランザクションリクエスト
     * @return 作成されたトランザクションのID
     */
    @Override
    public Long createCarrier2TransportProposal(TransactionCarrier2Request requestData) {
        VehicleAvbResourceItem vehicleAvbResourceItem = vehicleAvbResourceItemService.findById(
            Long.parseLong(requestData.getVehicleAvbResourceItemId()));
        CnsLineItemByDate cnsLineItemByDate = cnsLineItemByDateService.findById(
            Long.parseLong(requestData.getCnsLineItemByDateId()));
        TransOrder transOrder = transOrderMapper.toTransOrder(cnsLineItemByDate, vehicleAvbResourceItem);
        transOrder.setTransType(DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST);
        transOrder.setStatus(DataBaseConstant.TransOrderStatus.CARRIER2_PROPOSAL);
        transOrder.setParentOrderId(cnsLineItemByDate.getTransOrderId());
        transOrder.setCnsLineItemId(
            requestData.getCnsLineItemId() != null ? Long.parseLong(requestData.getCnsLineItemId()) : null);
        transOrder.setCnsLineItemByDateId(Long.parseLong(requestData.getCnsLineItemByDateId()));
        transOrder.setVehicleAvbResourceId(Long.parseLong(requestData.getVehicleAvbResourceId()));
        transOrder.setVehicleAvbResourceItemId(Long.parseLong(requestData.getVehicleAvbResourceItemId()));
        transOrder.setNegotiationData(negotiationDataMapper.mapNegotiationData(requestData.getNegotiationDTO()));
        vehicleAvbResourceItem.setStatus(DataBaseConstant.VehicleAvbResourceItem.Status.AWAITING_CONFIRMATION);
        cnsLineItemByDate.setStatus(DataBaseConstant.CnsLineItemByDate.Status.AWAITING_CONFIRMATION);
        transOrder.setFreeWord(
            transMatchingService.insertFreeWordTransMatching(cnsLineItemByDate, vehicleAvbResourceItem));
        vehicleAvbResourceItemRepository.save(vehicleAvbResourceItem);
        cnsLineItemByDateRepository.save(cnsLineItemByDate);
        return transOrderRepository.save(transOrder).getId();
    }

    /**
     * トランザクションのIDを検索する。
     *
     * @param orderIdString トランザクションID
     * @return 検索されたトランザクション
     */
    private TransOrder findByIdString(String orderIdString) {
        if (BaseUtil.isNull(orderIdString)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(MessageConstant.Validate.VALID_NOT_NULL),
                    ParamConstant.Transaction.ID));
        }

        Long transOrderId;
        try {
            transOrderId = Long.parseLong(orderIdString);
        } catch (NumberFormatException e) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(MessageConstant.Validate.VALID_NUMBER),
                    orderIdString));
        }

        Optional<TransOrder> transOrderOptional = transOrderRepository.findById(transOrderId);
        if (transOrderOptional.isEmpty()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                    transOrderId));
        }
        return transOrderOptional.get();
    }

    /**
     * キャリアのトランザクションのステータスを更新する。
     *
     * @param orderIdString      トランザクションID
     * @param updateOrderStatus  ステータス
     * @param httpServletRequest HTTPリクエスト
     * @return 更新されたトランザクションのID
     */
    @Override
    @Transactional
    public Long updateCarrierTransportDecision(String orderIdString, Integer updateOrderStatus,
        HttpServletRequest httpServletRequest) {
        TransOrder transOrder = this.findByIdString(orderIdString);
        transOrder.setStatus(updateOrderStatus);
        transOrder.setUpdatedDate(LocalDateTime.now());
        if (transOrder.getVehicleAvbResourceItemId() != null) {
            vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(transOrder.getVehicleAvbResourceId(),
                DataBaseConstant.VehicleAvbResourceItem.Status.TRANSPORT);
        }

        if (transOrder.getCnsLineItemByDateId() != null) {
            cnsLineItemByDateRepository.updateStatusById(transOrder.getCnsLineItemByDateId(),
                DataBaseConstant.CnsLineItemByDate.Status.TRANSPORT);
        }

        transOrderRepository.save(transOrder);

        if (updateOrderStatus.equals(DataBaseConstant.TransOrderStatus.READY_TO_TRANSPORT)) {
            if (transOrder.getParentOrderId() != null) {
                Optional<TransOrder> parentOrder = transOrderRepository.findById(transOrder.getParentOrderId());
                if (parentOrder.isPresent()) {
                    parentOrder.get().setStatus(DataBaseConstant.TransOrderStatus.TRANSPORT_DECISION);
                    parentOrder.get().setUpdatedDate(LocalDateTime.now());
                    transOrderRepository.save(parentOrder.get());
                }
            } else {
                Optional<CnsLineItemByDate> parentByDate = cnsLineItemByDateRepository.findById(
                    transOrder.getCnsLineItemByDateId());
                if (parentByDate.isPresent() && parentByDate.get().getTransOrderId() != null) {
                    Optional<TransOrder> parentOrder = transOrderRepository.findById(
                        parentByDate.get().getTransOrderId());
                    if (parentOrder.isPresent()) {
                        parentOrder.get().setStatus(DataBaseConstant.TransOrderStatus.TRANSPORT_DECISION);
                        parentOrder.get().setUpdatedDate(LocalDateTime.now());
                        transOrderRepository.save(parentOrder.get());
                    }
                }
            }
        }

        //Update sibling trailer
        //List<TransOrder> sibilingTransOrders = transOrderRepository.findByVehicleDiagramItemIdAndVehicleAvbResourceIdIsNot(transOrder.getVehicleDiagramItemId(), transOrder.getVehicleAvbResourceId());
        List<TransOrder> sibilingTransOrders = transOrderRepository.findByVehicleDiagramItemIdAndIdIsNot(
            transOrder.getVehicleDiagramItemId(), transOrder.getId());
        if (sibilingTransOrders != null) {
            for (TransOrder sibilingTransOrder : sibilingTransOrders) {
                if (Objects.equals(sibilingTransOrder.getTransType(),
                    DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST)) {
                    transOrderRepository.updateStatusById(sibilingTransOrder.getId(),
                        DataBaseConstant.TransOrderStatus.READY_TO_TRANSPORT);
                } else {
                    transOrderRepository.updateStatusById(sibilingTransOrder.getId(),
                        DataBaseConstant.TransOrderStatus.TRANSPORT_DECISION);
                }
                vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(
                    sibilingTransOrder.getVehicleAvbResourceId(),
                    DataBaseConstant.VehicleAvbResourceItem.Status.TRANSPORT);
                cnsLineItemByDateRepository.updateStatusById(sibilingTransOrder.getCnsLineItemByDateId(),
                    DataBaseConstant.CnsLineItemByDate.Status.TRANSPORT);
                if (sibilingTransOrder.getParentOrderId() != null) {
                    Optional<TransOrder> parentOrder = transOrderRepository.findById(
                        sibilingTransOrder.getParentOrderId());
                    if (parentOrder.isPresent()) {
                        if (Objects.equals(parentOrder.get().getTransType(),
                            DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST)) {
                            parentOrder.get().setStatus(DataBaseConstant.TransOrderStatus.READY_TO_TRANSPORT);
                            parentOrder.get().setUpdatedDate(LocalDateTime.now());
                        } else {
                            parentOrder.get().setStatus(DataBaseConstant.TransOrderStatus.TRANSPORT_DECISION);
                            parentOrder.get().setUpdatedDate(LocalDateTime.now());
                        }
                        transOrderRepository.save(parentOrder.get());
                    }
                } else {
                    Optional<CnsLineItemByDate> parentByDate = cnsLineItemByDateRepository.findById(
                        sibilingTransOrder.getCnsLineItemByDateId());
                    if (parentByDate.isPresent() && parentByDate.get().getTransOrderId() != null) {
                        Optional<TransOrder> parentOrder = transOrderRepository.findById(
                            parentByDate.get().getTransOrderId());
                        if (parentOrder.isPresent()) {
                            if (Objects.equals(parentOrder.get().getTransType(),
                                DataBaseConstant.TransOrder.TransType.CARRIER_REQUEST)) {
                                parentOrder.get().setStatus(DataBaseConstant.TransOrderStatus.READY_TO_TRANSPORT);
                                parentOrder.get().setUpdatedDate(LocalDateTime.now());
                            } else {
                                parentOrder.get().setStatus(DataBaseConstant.TransOrderStatus.TRANSPORT_DECISION);
                                parentOrder.get().setUpdatedDate(LocalDateTime.now());
                            }
                            transOrderRepository.save(parentOrder.get());
                        }
                    }
                }
            }
        }

        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(), transOrder.getTransportPlanItemId(),
            transOrder.getStatus());
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        log.info(" END DEBUG: ATH-316:");
        return transOrder.getId();
    }

    /**
     * キャリアの再提案を行う。
     *
     * @param orderIdString      トランザクションID
     * @param httpServletRequest HTTPリクエスト
     * @return 更新されたトランザクションのID
     */
    @Override
    @Transactional
    public Long updateCarrierRePropose(String orderIdString, HttpServletRequest httpServletRequest) {
        TransOrder transOrder = this.findByIdString(orderIdString);
        transOrder.setStatus(DataBaseConstant.TransOrderStatus.CARRIER_RE_PROPOSAL);
        transOrderRepository.save(transOrder);
        if (transOrder.getVehicleAvbResourceItemId() != null) {
            vehicleAvbResourceItemRepository.updateStatusById(transOrder.getVehicleAvbResourceItemId(),
                DataBaseConstant.VehicleAvbResourceItem.Status.AWAITING_CONFIRMATION);
        }

        if (transOrder.getCnsLineItemByDateId() != null) {
            cnsLineItemByDateRepository.updateStatusById(transOrder.getCnsLineItemByDateId(),
                DataBaseConstant.CnsLineItemByDate.Status.AWAITING_CONFIRMATION);
        }
        this.updateStatusTransaction(transOrder.getCnsLineItemByDateId(), transOrder.getVehicleAvbResourceId(),
            transOrder.getStatus());
        TransferStatusRequest transferStatusRequest = new TransferStatusRequest(
            transOrder.getVehicleDiagramItemTrailerId(), transOrder.getStatus(), transOrder.getTransportPlanItemId(),
            transOrder.getStatus());
        transferStatusService.updateStatus(transferStatusRequest, httpServletRequest);
        return transOrder.getId();
    }

    /**
     * トランザクションのアイテムトレイラIDを検索する。
     *
     * @param ids トランザクションIDのリスト
     * @return トランザクションの存在状態
     */
    @Override
    public String findAllByItemTrailerIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return "Not Exist";
        }
        List<TransOrder> transOrders = transOrderRepository.findAllByVehicleDiagramItemTrailerIdIn(ids);
        if (transOrders == null || transOrders.isEmpty()) {
            return "Not Exist";
        }

        return "Exist";
    }

    /**
     * トランザクションの緊急ステータスを更新する。
     *
     * @param requestData トランザクション緊急更新リクエスト
     */
    @Override
    @Transactional
    public void updateOrderEmergency(OrderEmergencyUpdateRequest requestData) {
        for (Long orderId : requestData.getListId()) {
            TransOrder transOrder = transOrderRepository.findById(orderId).orElseThrow(
                () -> new NextWebException(
                    new NextAPIError(HttpStatus.NOT_FOUND, SoaResponsePool.get(MessageConstant.Validate.NOT_FOUND),
                        orderId)));
            if (requestData.isRemove()) {
                transOrder.setIsEmergency(DataBaseConstant.IS_NOT_EMERGENCY);
            } else {
                transOrder.setIsEmergency(DataBaseConstant.IS_EMERGENCY);
            }
            transOrderRepository.save(transOrder);
        }
    }

    /**
     * トランザクションのメール通知を送信する。
     *
     * @param sendMailRequest メール送信リクエスト
     */
    @Override
    public void sendIXMail(SendMailRequest sendMailRequest) {
        List<TransOrder> transOrders;
        if (!BaseUtil.isNull(String.valueOf(sendMailRequest.getCnsLineItemByDateId()))) {
            transOrders = transOrderRepository.findDistinctByCnsLineItemByDateId(
                sendMailRequest.getCnsLineItemByDateId());
            if (!transOrders.isEmpty()) {
                transOrders.forEach(transOrder -> {
                    String carrierMail = carrierOperatorCustomRepository.getCarrierOperatorMail(
                        transOrder.getCarrierOperatorId());
                    if (!BaseUtil.isNull(carrierMail)) {
                        sendEmailNotification(transOrder.getVehicleDiagramItemId(), carrierMail,
                            transOrder.getTransportDate());
                    }
                });
            }
        }
        if (!BaseUtil.isNull(String.valueOf(sendMailRequest.getVehicleAvbResourceItemId()))) {
            transOrders = transOrderRepository.findDistinctByVehicleAvbResourceItemId(
                sendMailRequest.getVehicleAvbResourceItemId());
            if (!transOrders.isEmpty()) {
                transOrders.forEach(transOrder -> {
                    String shipperMail = shipperOperatorCustomRepository.getShipperOperatorMail(
                        transOrder.getShipperOperatorId());
                    if (!BaseUtil.isNull(shipperMail)) {
                        sendEmailNotification(transOrder.getVehicleDiagramItemId(), shipperMail,
                            transOrder.getTransportDate());
                    }
                });
            }
        }
    }

    /**
     * トランザクションのステータスを更新する。
     *
     * @param transMatchingId      トランザクションID
     * @param cnsLineItemByDateId  コンテナID
     * @param vehicleAvbResourceId 車両ID
     */
    private void updateStatusAfterPropose(Long transMatchingId, Long cnsLineItemByDateId, Long vehicleAvbResourceId) {
        transMatchingRepository.updateStatusById(DataBaseConstant.TranMatchingStatus.PICKED_UP, transMatchingId);

        //Update trans_matching by vehicle_avb_resource_id
        transMatchingRepository.updateStatusByVehicleAvbResourceId(
            DataBaseConstant.TranMatchingStatus.IMPOSSIBLE_PICK_UP, vehicleAvbResourceId, transMatchingId);
        transMatchingRepository.updateStatusByCnsLineItemByDateIdAndIdNot(
            DataBaseConstant.TranMatchingStatus.IMPOSSIBLE_PICK_UP, cnsLineItemByDateId, transMatchingId);

        //Update vehicle_avb_resource_item and cns_line_item_by_date
        vehicleAvbResourceItemRepository.updateStatusByVehicleAvbResourceId(vehicleAvbResourceId,
            DataBaseConstant.VehicleAvbResourceItem.Status.AWAITING_CONFIRMATION);
        cnsLineItemByDateRepository.updateStatusById(cnsLineItemByDateId,
            DataBaseConstant.CnsLineItemByDate.Status.AWAITING_CONFIRMATION);
    }

    /**
     * トランザクションのメール通知を送信する。
     *
     * @param tripId        トランザクションID
     * @param mail          メールアドレス
     * @param transportDate 運行日
     * @return メール送信結果
     */
    private String sendEmailNotification(Long tripId, String mail, LocalDate transportDate) {
        boolean emailSent = nextMailUtil.sendMail(mail,
            "便" + tripId + " 運行日 " + transportDate + " は運行決定されました。",
            "便" + tripId + " 運行日 " + transportDate + " は運行決定されました。",
            "mail-template");
        log.info("{} to send mail to : {}", emailSent ? "Success" : "Failed", mail);
        return emailSent ? "Success" : "Failed";
    }
}
