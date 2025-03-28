package nlj.business.transaction.service.impl;

import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant.VehicleDiagramItemTrailerStatus;
import nlj.business.transaction.dto.VehicleDiagramItemTrailerDTO;
import nlj.business.transaction.mapper.VehicleDiagramItemTrailerMapper;
import nlj.business.transaction.repository.VehicleDiagramItemTrailerRepository;
import nlj.business.transaction.service.VehicleDiagramItemTrailerService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 車両ダイアグラムアイテムトレーラーサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleDiagramItemTrailerServiceImpl implements VehicleDiagramItemTrailerService {

    private final VehicleDiagramItemTrailerRepository vehicleDiagramItemTrailerRepository;
    private final VehicleDiagramItemTrailerMapper vehicleDiagramItemTrailerMapper;

    /**
     * 輸送能力の提案アイテムを取得する。
     *
     * @return 輸送能力の提案アイテム
     */
    @Override
    public VehicleDiagramItemTrailerDTO getTransportAbilityProposal() {
        return vehicleDiagramItemTrailerMapper.mapToDTO(
            vehicleDiagramItemTrailerRepository.findFirstByStatusOrderByIdAsc(
                VehicleDiagramItemTrailerStatus.ON_SALES));
    }
}
