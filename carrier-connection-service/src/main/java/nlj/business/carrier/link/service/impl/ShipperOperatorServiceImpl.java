package nlj.business.carrier.link.service.impl;

import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.domain.ShipperOperator;
import nlj.business.carrier.link.repository.ShipperOperatorRepository;
import nlj.business.carrier.link.service.ShipperOperatorService;
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
public class ShipperOperatorServiceImpl implements ShipperOperatorService {

    private final ShipperOperatorRepository shipperOperatorRepository;

    /**
     * 荷主運送業者コードを作成.<BR>
     *
     * @param operatorId 荷主運送業者ID
     * @return 荷主運送業者
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
