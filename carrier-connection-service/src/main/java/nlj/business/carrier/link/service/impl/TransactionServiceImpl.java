package nlj.business.carrier.link.service.impl;

import java.util.Optional;
import lombok.AllArgsConstructor;
import nlj.business.carrier.link.domain.CnsLineItemByDate;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;
import nlj.business.carrier.link.repository.CnsLineItemByDateRepository;
import nlj.business.carrier.link.repository.VehicleAvbResourceItemRepository;
import nlj.business.carrier.link.service.TransactionService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * トランザクションサービス実装クラス.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CnsLineItemByDateRepository cnsLineItemByDateRepository;
    private final VehicleAvbResourceItemRepository vehicleAvbResourceItemRepository;

    /**
     * ステータスを更新.<BR>
     *
     * @param shipperId 荷主ID
     * @param carrierId 運送業者ID
     * @param status    ステータス
     */
    @Override
    public void updateStatusTransaction(Long shipperId, Long carrierId, Integer status) {
        Optional<CnsLineItemByDate> cnsLineItemByDateOptional = cnsLineItemByDateRepository.findById(shipperId);
        if (cnsLineItemByDateOptional.isPresent()) {
            CnsLineItemByDate cnsLineItemByDate = cnsLineItemByDateOptional.get();
            cnsLineItemByDate.setStatus(status);
            cnsLineItemByDateRepository.save(cnsLineItemByDate);
        }
        Optional<VehicleAvbResourceItem> vehicleAvbResourceItemOptional = vehicleAvbResourceItemRepository.findById(
            carrierId);
        if (vehicleAvbResourceItemOptional.isPresent()) {
            VehicleAvbResourceItem vehicleAvbResourceItem = vehicleAvbResourceItemOptional.get();
            vehicleAvbResourceItem.setStatus(status);
            vehicleAvbResourceItemRepository.save(vehicleAvbResourceItem);
        }
    }
}
