package nlj.business.carrier.service.impl;

import lombok.RequiredArgsConstructor;
import nlj.business.carrier.domain.CarrierOperator;
import nlj.business.carrier.repository.CarrierRepository;
import nlj.business.carrier.service.CarrierOperatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 輸送計画アイテムサービス実装クラス
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CarrierOperatorServiceImpl implements CarrierOperatorService {

    private final CarrierRepository carrierRepository;

    /**
     * 運行者を作成する。
     *
     * @param operatorId 運行者ID
     * @return 運行者
     */
    @Override
    public CarrierOperator createCarrierOperator(String operatorId) {
        CarrierOperator carrierOperator = new CarrierOperator();
        carrierOperator.setId(operatorId);

        Long lastOperatorCode = carrierRepository.getLastOperatorCode();
        Long newOperatorCode = (lastOperatorCode != null) ? lastOperatorCode + 1 : 1;
        carrierOperator.setOperatorCode(newOperatorCode.toString());

        carrierRepository.save(carrierOperator);
        return carrierOperator;
    }
}
