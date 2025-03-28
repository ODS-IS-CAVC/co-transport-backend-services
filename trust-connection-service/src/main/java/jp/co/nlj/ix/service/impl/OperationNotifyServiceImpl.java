package jp.co.nlj.ix.service.impl;

import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import jp.co.nlj.ix.constant.MessageConstant;
import jp.co.nlj.ix.domain.CarInfo;
import jp.co.nlj.ix.domain.CutOffInfo;
import jp.co.nlj.ix.domain.DriverAvailabilityTime;
import jp.co.nlj.ix.domain.DriverInformation;
import jp.co.nlj.ix.domain.FreeTimeInfo;
import jp.co.nlj.ix.domain.TransportAbilityLineItem;
import jp.co.nlj.ix.domain.VehicleAvailabilityResource;
import jp.co.nlj.ix.dto.operationNotify.request.OperationNotifyRequestDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.CarInfoDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.CutOffInfoDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.DriverAvailabilityTimeDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.DriverInformationDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.FreeTimeInfoDTO;
import jp.co.nlj.ix.dto.shipperTrspCapacity.VehicleAvailabilityResourceDTO;
import jp.co.nlj.ix.mapper.CarInfoMapper;
import jp.co.nlj.ix.mapper.CutOffInfoMapper;
import jp.co.nlj.ix.mapper.DriverInformationMapper;
import jp.co.nlj.ix.mapper.FreeTimeInfoMapper;
import jp.co.nlj.ix.mapper.TransportAbilityLineItemMapper;
import jp.co.nlj.ix.mapper.VehicleAvailabilityResourceMapper;
import jp.co.nlj.ix.repository.CarInfoRepository;
import jp.co.nlj.ix.repository.TransportAbilityLineItemRepository;
import jp.co.nlj.ix.service.OperationNotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 操作通知サービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class OperationNotifyServiceImpl implements OperationNotifyService {

    private final TransportAbilityLineItemMapper transportAbilityLineItemMapper;
    private final CarInfoMapper carInfoMapper;
    private final VehicleAvailabilityResourceMapper vehicleAvailabilityResourceMapper;
    private final FreeTimeInfoMapper freeTimeInfoMapper;
    private final CutOffInfoMapper cutOffInfoMapper;
    private final DriverInformationMapper driverInformationMapper;

    private final TransportAbilityLineItemRepository transportAbilityLineItemRepository;
    private final CarInfoRepository carInfoRepository;

    @Resource(name = "userContext")
    private UserContext userContext;

    /**
     * 操作通知の登録または更新を行います。
     *
     * @param requestDTO       操作通知のリクエストデータを含むDTO
     * @param operationIdParam 検証および処理対象の操作IDパラメータ
     */
    @Override
    @Transactional
    public void registerOrUpdateOperationNotify(OperationNotifyRequestDTO requestDTO, String operationIdParam) {
        TransportAbilityLineItem transportAbilityLineItemMapped = transportAbilityLineItemMapper.mapToTransportAbilityLineItem(
            requestDTO.getRoadCarrDTO(), requestDTO.getLogisticsServiceProviderDTO());
        TransportAbilityLineItem transportAbilityLineItemEntity = transportAbilityLineItemRepository.findFirstByTrspCliPrtyHeadOffIdAndTrspCliPrtyBrncOffId(
            requestDTO.getRoadCarrDTO().getTrspCliPrtyHeadOffId(),
            requestDTO.getRoadCarrDTO().getTrspCliPrtyBrncOffId());
        if (transportAbilityLineItemEntity != null) {
            transportAbilityLineItemMapper.updateTransportAbilityLineItem(transportAbilityLineItemEntity,
                requestDTO.getRoadCarrDTO(), requestDTO.getLogisticsServiceProviderDTO());
        } else {
            transportAbilityLineItemEntity = transportAbilityLineItemMapped;
        }
        insertOrUpdateCarInfo(requestDTO.getCarInfoDTOS(), transportAbilityLineItemEntity);
        insertDriverInfor(requestDTO.getDriverInformationDTOS(), transportAbilityLineItemEntity);
        transportAbilityLineItemRepository.save(transportAbilityLineItemEntity);
    }

    /**
     * 車両情報を登録または更新します。
     *
     * @param carInfoDTOS                    車両情報のDTOセット
     * @param transportAbilityLineItemEntity 輸送能力ラインアイテムを表すエンティティ
     */
    private void insertOrUpdateCarInfo(Set<CarInfoDTO> carInfoDTOS,
        TransportAbilityLineItem transportAbilityLineItemEntity) {
        if (carInfoDTOS != null) {
            Set<CarInfo> carInfos = new HashSet<>();
            for (CarInfoDTO carInfoDTO : carInfoDTOS) {
                CarInfo carInfoMapped = carInfoMapper.mapToCarInfo(carInfoDTO);
                CarInfo carInfoEntity = carInfoRepository.findByServiceNoAndServiceStrtDateAndOperatorId(
                    carInfoMapped.getServiceNo(), carInfoMapped.getServiceStrtDate(),
                    transportAbilityLineItemEntity.getOperatorId());
                if (carInfoEntity != null) {
                    carInfoMapper.updateCarInfo(carInfoEntity, carInfoDTO);
                    carInfoEntity.getVehicleAvailabilityResources().clear();
                } else {
                    carInfoEntity = carInfoMapped;
                    carInfoEntity.setOperatorId(transportAbilityLineItemEntity.getOperatorId());
                    carInfoEntity.setTransportAbilityLineItem(transportAbilityLineItemEntity);
                    carInfoEntity.setVehicleAvailabilityResources(new HashSet<>());
                }

                if (carInfoDTO.getVehicleAvailabilityResourceDTOS() != null) {
                    carInfoEntity.getVehicleAvailabilityResources().clear();
                    Set<VehicleAvailabilityResource> vehicleAvailabilityResourceList = new HashSet<>();
                    for (VehicleAvailabilityResourceDTO vehicleAvailabilityResourceDTO : carInfoDTO.getVehicleAvailabilityResourceDTOS()) {
                        VehicleAvailabilityResource vehicleAvailabilityResourceEntity = vehicleAvailabilityResourceMapper.mapToVehicleAvailabilityResource(
                            vehicleAvailabilityResourceDTO);
                        vehicleAvailabilityResourceEntity.setCarInfo(carInfoEntity);
                        vehicleAvailabilityResourceEntity.setOperatorId(transportAbilityLineItemEntity.getOperatorId());

                        if (vehicleAvailabilityResourceDTO.getCutOffInfoDTOS() != null) {
                            Set<CutOffInfo> cutOffInfos = new HashSet<>();
                            for (CutOffInfoDTO cutOffInfoDTO : vehicleAvailabilityResourceDTO.getCutOffInfoDTOS()) {
                                CutOffInfo cutOffInfo = cutOffInfoMapper.mapToCutOffInfo(cutOffInfoDTO);
                                cutOffInfo.setVehicleAvailabilityResource(vehicleAvailabilityResourceEntity);
                                cutOffInfo.setOperatorId(transportAbilityLineItemEntity.getOperatorId());
                                cutOffInfos.add(cutOffInfo);
                            }
                            vehicleAvailabilityResourceEntity.setCutOffInfos(cutOffInfos);
                        }

                        if (vehicleAvailabilityResourceDTO.getFreeTimeInfoDTOS() != null) {
                            Set<FreeTimeInfo> freeTimeInfos = new HashSet<>();
                            for (FreeTimeInfoDTO freeTimeInfoDTO : vehicleAvailabilityResourceDTO.getFreeTimeInfoDTOS()) {
                                FreeTimeInfo freeTimeInfo = freeTimeInfoMapper.mapToFreeTimeInfo(freeTimeInfoDTO);
                                freeTimeInfo.setVehicleAvailabilityResource(vehicleAvailabilityResourceEntity);
                                freeTimeInfo.setOperatorId(transportAbilityLineItemEntity.getOperatorId());
                                freeTimeInfos.add(freeTimeInfo);
                            }
                            vehicleAvailabilityResourceEntity.setFreeTimeInfos(freeTimeInfos);
                        }

                        vehicleAvailabilityResourceList.add(vehicleAvailabilityResourceEntity);
                    }
                    carInfoEntity.getVehicleAvailabilityResources().addAll(vehicleAvailabilityResourceList);
                }
                carInfos.add(carInfoEntity);
            }
            transportAbilityLineItemEntity.getCarInfos().addAll(carInfos);
        }
    }

    /**
     * 荷送人の輸送能力を登録します。
     *
     * @param driverInformationDTOS          荷送人の輸送能力に関するドライバー情報のDTOセット
     * @param transportAbilityLineItemEntity 輸送能力ラインアイテムを表すエンティティ
     */
    private void insertDriverInfor(Set<DriverInformationDTO> driverInformationDTOS,
        TransportAbilityLineItem transportAbilityLineItemEntity) {
        if (driverInformationDTOS != null && !driverInformationDTOS.isEmpty()) {
            if (transportAbilityLineItemEntity.getDriverInformations() != null) {
                transportAbilityLineItemEntity.getDriverInformations().clear();
            } else {
                transportAbilityLineItemEntity.setDriverInformations(new HashSet<>());
            }
            for (DriverInformationDTO driverInformationDTO : driverInformationDTOS) {
                DriverInformation driverInformation = driverInformationMapper.mapToDriverInformation(
                    driverInformationDTO);
                driverInformation.setTransportAbilityLineItem(transportAbilityLineItemEntity);
                driverInformation.setOperatorId(transportAbilityLineItemEntity.getOperatorId());

                if (driverInformationDTO.getDriverAvailabilityTimeDTOS() != null) {
                    Set<DriverAvailabilityTime> driverAvailabilityTimes = new HashSet<>();
                    for (DriverAvailabilityTimeDTO driverAvailabilityTimeDTO : driverInformationDTO.getDriverAvailabilityTimeDTOS()) {
                        DriverAvailabilityTime driverAvailabilityTimeEntity = driverInformationMapper.mapToDriverAvailabilityTime(
                            driverAvailabilityTimeDTO);
                        driverAvailabilityTimeEntity.setDriverInformation(driverInformation);
                        driverAvailabilityTimeEntity.setOperatorId(transportAbilityLineItemEntity.getOperatorId());
                        driverAvailabilityTimes.add(driverAvailabilityTimeEntity);
                    }
                    driverInformation.setDriverAvailabilityTimes(driverAvailabilityTimes);
                }
                transportAbilityLineItemEntity.getDriverInformations().add(driverInformation);
            }
        }
    }
}
