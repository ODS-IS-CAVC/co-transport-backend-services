package nlj.business.carrier.link.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.MessageConstant.System;
import nlj.business.carrier.link.constant.MessageConstant.TrspAbilityLineItemMsg;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.trspAbilityLineItem.request.TrspAbilityLineItemRequestDTO;
import nlj.business.carrier.link.dto.trspAbilityLineItem.response.TrspAbilityLineItemResponseDTO;
import nlj.business.carrier.link.service.TrspAbilityLineItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 輸送能力行項目サービス実装.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TrspAbilityLineItemServiceImpl implements TrspAbilityLineItemService {

    private final Logger logger = LoggerFactory.getLogger(TrspAbilityLineItemServiceImpl.class);

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 輸送能力行項目を登録.<BR>
     *
     * @param commonRequestDTO 共通リクエストDTO
     * @param remoteIp         リモートIPアドレス
     * @return 輸送能力行項目レスポンスDTO
     */
    @Override
    public TrspAbilityLineItemResponseDTO registerTrspAbilityLineItem(CommonRequestDTO commonRequestDTO,
        String remoteIp) {
        TrspAbilityLineItemRequestDTO requestDTO = convertRequestAttribute(commonRequestDTO);
        logger.info("Operator Id: {} - Remote ip : {} - Request Body : {}",
            userContext.getUser().getCompanyId(),
            remoteIp,
            requestDTO);
        TrspAbilityLineItemResponseDTO responseDTO = new TrspAbilityLineItemResponseDTO();
        responseDTO.setMessage(TrspAbilityLineItemMsg.REGISTRATION_SUCCESS);
        return responseDTO;
    }

    /**
     * リクエスト属性を変換.<BR>
     *
     * @param commonRequestDTO 共通リクエストDTO
     * @return 輸送能力行項目リクエストDTO
     */
    private TrspAbilityLineItemRequestDTO convertRequestAttribute(CommonRequestDTO commonRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.convertValue(commonRequestDTO.getAttribute(), TrspAbilityLineItemRequestDTO.class);
        } catch (Exception e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
            return null;
        }
    }
}
