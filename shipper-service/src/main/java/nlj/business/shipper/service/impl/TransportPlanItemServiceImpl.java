package nlj.business.shipper.service.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.constant.MessageConstant.SystemMessage;
import nlj.business.shipper.constant.ParamConstant;
import nlj.business.shipper.domain.ShipperOperator;
import nlj.business.shipper.domain.TransportPlanItem;
import nlj.business.shipper.dto.TransportPlanItemDTO;
import nlj.business.shipper.dto.request.PlanItemUpdateStatusRequestDTO;
import nlj.business.shipper.dto.response.PlanItemUpdateStatusResponse;
import nlj.business.shipper.dto.transportPlanItem.response.TransportPlanItemPagingResponse;
import nlj.business.shipper.mapper.TransportPlanMapper;
import nlj.business.shipper.repository.ShipperOperatorRepository;
import nlj.business.shipper.repository.TransportPlanItemCustomRepository;
import nlj.business.shipper.repository.TransportPlanItemRepository;
import nlj.business.shipper.service.TransportPlanItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 輸送計画アイテムサービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TransportPlanItemServiceImpl implements TransportPlanItemService {

    private final TransportPlanItemRepository transportPlanItemRepository;
    private final TransportPlanMapper transportPlanMapper;
    private final ShipperOperatorRepository shipperOperatorRepository;
    private final TransportPlanItemCustomRepository transportPlanItemCustomRepository;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * IDによって輸送計画アイテムを取得する。
     *
     * @param id 輸送計画アイテムID
     * @return 輸送計画アイテムDTO
     */
    @Override
    public TransportPlanItemDTO getTransportPlanItemById(Long id) {
        TransportPlanItemDTO transportPlanItemDTO = new TransportPlanItemDTO();

        TransportPlanItem transportPlanItem = transportPlanItemRepository.getTransportPlanItemById(id);
        if (transportPlanItem == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, SystemMessage.SYSTEM_ERR_001);
        }

        transportPlanItemDTO = transportPlanMapper.toTransportPlanItemDTO(transportPlanItem);
        return transportPlanItemDTO;
    }

    /**
     * IDによって輸送計画アイテムを更新する。
     *
     * @param id                   輸送計画アイテムID
     * @param transportPlanItemDTO 輸送計画アイテムDTO
     */
    @Override
    @Transactional
    public void updateTransportPlanItem(Long id, TransportPlanItemDTO transportPlanItemDTO) {

        TransportPlanItem existingTransportPlanItem = transportPlanItemRepository.getTransportPlanItemById(id);

        if (existingTransportPlanItem == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, SystemMessage.SYSTEM_ERR_001);
        }

        TransportPlanItem updateTransportPlanItem = transportPlanMapper.toTransportPlanItemEntity(
            transportPlanItemDTO);
        updateTransportPlanItem.setId(id);
        transportPlanItemRepository.save(updateTransportPlanItem);
    }

    /**
     * 輸送計画アイテムを検索する。
     *
     * @param transportName 輸送計画名
     * @param pageable      ページング情報
     * @return 輸送計画アイテムページングレスポンス
     */
    @Override
    public TransportPlanItemPagingResponse searchTrspPlanItem(String transportName, Pageable pageable) {
        User user = userContext.getUser();
        TransportPlanItemPagingResponse transportPlanItemPagingResponse = new TransportPlanItemPagingResponse();
        ShipperOperator shipperOperator = shipperOperatorRepository.findById(user.getCompanyId()).orElse(null);
        if (shipperOperator == null) {
            transportPlanItemPagingResponse.setCurrentPage(Integer.valueOf(ParamConstant.DEFAULT_PAGE_NO));
            transportPlanItemPagingResponse.setTotalItem(0L);
            transportPlanItemPagingResponse.setTotalPage(0);
            transportPlanItemPagingResponse.setItemPerPage(Integer.valueOf(ParamConstant.LIMIT_DEFAULT));
            return transportPlanItemPagingResponse;
        }
        Page<TransportPlanItem> transportPlanItemPage = transportPlanItemCustomRepository.searchTrspPlanItem(
            user.getCompanyId(), transportName, pageable);
        if (transportPlanItemPage.isEmpty()) {
            transportPlanItemPagingResponse.setCurrentPage(Integer.valueOf(ParamConstant.DEFAULT_PAGE_NO));
            transportPlanItemPagingResponse.setTotalItem(0L);
            transportPlanItemPagingResponse.setTotalPage(0);
            transportPlanItemPagingResponse.setItemPerPage(Integer.valueOf(ParamConstant.LIMIT_DEFAULT));
            return transportPlanItemPagingResponse;
        }
        transportPlanItemPagingResponse.setTotalItem(transportPlanItemPage.getTotalElements());
        transportPlanItemPagingResponse.setCurrentPage(pageable.getPageNumber() + 1);
        transportPlanItemPagingResponse.setTotalPage(transportPlanItemPage.getTotalPages());
        transportPlanItemPagingResponse.setItemPerPage(pageable.getPageSize());
        List<TransportPlanItem> transportPlanList = transportPlanItemPage.getContent();
        List<TransportPlanItemDTO> responseDTOs = new ArrayList<>();

        for (TransportPlanItem transportPlanItem : transportPlanList) {
            TransportPlanItemDTO responseDTO = transportPlanMapper.toTransportPlanItemDTO(transportPlanItem);
            responseDTO.setCreatedDate(
                transportPlanItem.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            responseDTO.setCompanyName(shipperOperator.getOperatorName());
            responseDTO.setId(transportPlanItem.getId());
            responseDTO.setTrspPlanId(transportPlanItem.getTransportPlan().getId());
            responseDTOs.add(responseDTO);
        }
        transportPlanItemPagingResponse.setDataList(responseDTOs);
        return transportPlanItemPagingResponse;
    }

    /**
     * 輸送計画アイテムのステータスを更新する。
     *
     * @param dto 更新情報
     * @return 更新結果
     */
    @Override
    public PlanItemUpdateStatusResponse updateStatus(PlanItemUpdateStatusRequestDTO dto) {
        PlanItemUpdateStatusResponse response = new PlanItemUpdateStatusResponse();
        if (BaseUtil.isNull(String.valueOf(dto.getId())) || BaseUtil.isNull(String.valueOf(dto.getStatus()))) {
            response.setStatus("Fail");
            return response;
        }
        TransportPlanItem transportPlanItem = transportPlanItemRepository.findById(dto.getId()).orElse(null);
        if (transportPlanItem == null) {
            response.setStatus("Fail");
            return response;
        }
        transportPlanItem.setStatus(dto.getStatus());
        transportPlanItemRepository.save(transportPlanItem);
        response.setStatus("Success");
        return response;
    }
} 