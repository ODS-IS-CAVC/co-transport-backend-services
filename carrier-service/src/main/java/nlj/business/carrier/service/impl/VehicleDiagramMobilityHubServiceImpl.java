package nlj.business.carrier.service.impl;

import com.next.logistic.authorization.UserContext;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.repository.VehicleDiagramMobilityHubRepository;
import nlj.business.carrier.service.VehicleDiagramMobilityHubService;
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
     * 車両ダイアグラムモビリティハブを保存する。
     *
     * @param vehicleDiagramItemId        車両ダイアグラムアイテムのID
     * @param vehicleDiagramItemTrailerId 車両ダイアグラムアイテムトレーラーのID
     * @param mobilityHubId               モビリティハブのID
     * @param sizeClass                   サイズクラス
     * @param validFrom                   有効開始日時
     * @param validUntil                  有効終了日時
     * @param mhReservationId             モビリティハブ予約ID
     * @param freightId                   貨物ID
     * @param truckId                     トラックID
     * @param type                        タイプ
     * @param vehicleType                 車両タイプ
     * @param vehicleDiagramAllocationId  車両ダイアグラム割り当てID
     * @return 保存された車両ダイアグラムモビリティハブ
     */
    @Override
    public VehicleDiagramMobilityHub saveVehicleDiagramMobilityHub(Long vehicleDiagramItemId,
        Long vehicleDiagramItemTrailerId, String mobilityHubId, String sizeClass, LocalDateTime validFrom,
        LocalDateTime validUntil, String mhReservationId, String freightId, String truckId, int type, int vehicleType,
        Long vehicleDiagramAllocationId) {
        String operatorId = userContext.getUser().getCompanyId();
        // check if the validFrom is after the validUntil
        if (validFrom.isAfter(validUntil)) {
            validUntil = validUntil.plusDays(1);
        }
        VehicleDiagramMobilityHub vehicleDiagramMobilityHub = new VehicleDiagramMobilityHub();
        vehicleDiagramMobilityHub.setVehicleDiagramItemId(vehicleDiagramItemId);
        vehicleDiagramMobilityHub.setVehicleDiagramAllocationId(vehicleDiagramAllocationId);
        vehicleDiagramMobilityHub.setVehicleDiagramItemTrailerId(vehicleDiagramItemTrailerId);
        vehicleDiagramMobilityHub.setType(type);
        vehicleDiagramMobilityHub.setVehicleType(vehicleType);
        vehicleDiagramMobilityHub.setFreightId(freightId);
        vehicleDiagramMobilityHub.setMobilityHubId(mobilityHubId);
        vehicleDiagramMobilityHub.setReservationStatus(ParamConstant.MobilityHub.RESERVATION_STATUS_0);
        vehicleDiagramMobilityHub.setTruckId(truckId);
        vehicleDiagramMobilityHub.setSizeClass(sizeClass);
        vehicleDiagramMobilityHub.setValidFrom(validFrom);
        vehicleDiagramMobilityHub.setValidUntil(validUntil);
        vehicleDiagramMobilityHub.setMhReservationId(mhReservationId);
        vehicleDiagramMobilityHub.setOperatorId(operatorId);
        vehicleDiagramMobilityHub.setStatus(ParamConstant.MobilityHub.STATUS_0);
        return vehicleDiagramMobilityHubRepository.save(vehicleDiagramMobilityHub);
    }

}
