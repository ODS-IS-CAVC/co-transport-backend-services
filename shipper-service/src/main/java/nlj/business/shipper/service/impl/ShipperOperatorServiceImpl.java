package nlj.business.shipper.service.impl;

import lombok.RequiredArgsConstructor;
import nlj.business.shipper.domain.ShipperOperator;
import nlj.business.shipper.repository.ShipperOperatorRepository;
import nlj.business.shipper.service.ShipperOperatorService;
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
public class ShipperOperatorServiceImpl implements ShipperOperatorService {

    private final ShipperOperatorRepository shipperOperatorRepository;

    /**
     * 運送会社オペレーターを作成する。
     *
     * @param operatorId オペレーターID
     * @return 運送会社オペレーター
     */
    @Override
    public ShipperOperator createShipperOperator(String operatorId) {
        ShipperOperator shipperOperator = new ShipperOperator();
        shipperOperator.setId(operatorId);

        Long lastOperatorCode = shipperOperatorRepository.getLastOperatorCode();
        Long newOperatorCode = (lastOperatorCode != null) ? lastOperatorCode + 1 : 1;
        shipperOperator.setOperatorCode(newOperatorCode.toString());

        shipperOperatorRepository.save(shipperOperator);
        return shipperOperator;
    }
}
