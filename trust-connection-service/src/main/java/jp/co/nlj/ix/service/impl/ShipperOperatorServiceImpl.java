package jp.co.nlj.ix.service.impl;

import jp.co.nlj.ix.domain.ShipperOperator;
import jp.co.nlj.ix.repository.ShipperOperatorRepository;
import jp.co.nlj.ix.service.ShipperOperatorService;
import lombok.RequiredArgsConstructor;
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
     * 新しい輸送業者を作成する。
     *
     * @param operatorId 輸送業者のID
     * @return 作成された輸送業者
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
