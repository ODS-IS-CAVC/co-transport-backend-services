package nlj.business.carrier.link.service.impl;

import com.next.logistic.authorization.UserContext;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.link.repository.VehicleDiagramMobilityHubRepository;
import nlj.business.carrier.link.service.VehicleDiagramMobilityHubService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 車両ダイアグラムモビリティハブサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleDiagramMobilityHubServiceImpl implements VehicleDiagramMobilityHubService {

    private final VehicleDiagramMobilityHubRepository vehicleDiagramMobilityHubRepository;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 車両ダイアグラムモビリティハブを保存.<BR>
     *
     * @param vehicleDiagramItemId        車両ダイアグラムアイテムID
     * @param vehicleDiagramItemTrailerId 車両ダイアグラムアイテムトレイラーID
     * @param mobilityHubId               モビリティハブID
     * @param sizeClass                   サイズクラス
     * @param validFrom                   有効期間開始日時
     * @param validUntil                  有効期間終了日時
     * @param mhReservationId             モビリティハブ予約ID
     * @param freightId                   荷送ID
     * @param truckId                     トラックID
     * @param type                        タイプ
     * @param vehicleType                 車両タイプ
     * @return 車両ダイアグラムモビリティハブ
     */
    @Override
    public VehicleDiagramMobilityHub saveVehicleDiagramMobilityHub(Long vehicleDiagramItemId,
        Long vehicleDiagramItemTrailerId,
        String mobilityHubId, String sizeClass, LocalDateTime validFrom, LocalDateTime validUntil,
        String mhReservationId, String freightId, String truckId, int type, int vehicleType) {
        String operatorId = userContext.getUser().getCompanyId();
        // check if the validFrom is after the validUntil
        if (validFrom.isAfter(validUntil)) {
            validUntil = validUntil.plusDays(1);
        }
        VehicleDiagramMobilityHub vehicleDiagramMobilityHub = new VehicleDiagramMobilityHub();
        vehicleDiagramMobilityHub.setVehicleDiagramItemId(vehicleDiagramItemId);
        vehicleDiagramMobilityHub.setVehicleDiagramAllocationId(vehicleDiagramItemTrailerId);
        vehicleDiagramMobilityHub.setVehicleDiagramItemTrailerId(vehicleDiagramItemTrailerId);
        vehicleDiagramMobilityHub.setVehicleType(vehicleType);
        vehicleDiagramMobilityHub.setType(type);
        vehicleDiagramMobilityHub.setFreightId(freightId);
        vehicleDiagramMobilityHub.setTruckId(truckId);
        vehicleDiagramMobilityHub.setMobilityHubId(mobilityHubId);
        vehicleDiagramMobilityHub.setReservationStatus(ParamConstant.Status.RESERVATION_STATUS_0);
        vehicleDiagramMobilityHub.setSizeClass(sizeClass);
        vehicleDiagramMobilityHub.setValidFrom(validFrom);
        vehicleDiagramMobilityHub.setValidUntil(validUntil);
        vehicleDiagramMobilityHub.setMhReservationId(mhReservationId);
        vehicleDiagramMobilityHub.setOperatorId(operatorId);
        vehicleDiagramMobilityHub.setStatus(ParamConstant.Status.STATUS_0);
        return vehicleDiagramMobilityHubRepository.save(vehicleDiagramMobilityHub);
    }

}
