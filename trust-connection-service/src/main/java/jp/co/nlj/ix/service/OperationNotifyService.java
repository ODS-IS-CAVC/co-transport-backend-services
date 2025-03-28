package jp.co.nlj.ix.service;

import jp.co.nlj.ix.dto.operationNotify.request.OperationNotifyRequestDTO;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 稼働通知サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface OperationNotifyService {

    /**
     * 稼働通知を登録または更新する。
     *
     * @param requestDTO       稼働通知リクエストDTO
     * @param operationIdParam 操作IDパラメータ
     */
    void registerOrUpdateOperationNotify(OperationNotifyRequestDTO requestDTO, String operationIdParam);
}
