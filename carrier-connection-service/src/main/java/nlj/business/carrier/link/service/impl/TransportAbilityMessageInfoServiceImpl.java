package nlj.business.carrier.link.service.impl;

import com.next.logistic.authorization.UserContext;
import com.next.logistic.data.dto.response.ReservationResponseDTO;
import com.next.logistic.data.util.MobilityHubUtil;
import com.next.logistic.data.util.ValidateUtil;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.DataBaseConstant.DATE_TIME_FORMAT;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.MessageConstant.SemiDynamicInfo;
import nlj.business.carrier.link.constant.MessageConstant.System;
import nlj.business.carrier.link.constant.MessageConstant.Validate;
import nlj.business.carrier.link.constant.MessageConstant.ValidateCommonMessage;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.CarrierOperator;
import nlj.business.carrier.link.domain.CutOffInfo;
import nlj.business.carrier.link.domain.DriverAvailabilityTime;
import nlj.business.carrier.link.domain.DriverInformation;
import nlj.business.carrier.link.domain.FreeTimeInfo;
import nlj.business.carrier.link.domain.LocationMaster;
import nlj.business.carrier.link.domain.TransportAbilityLineItem;
import nlj.business.carrier.link.domain.TransportAbilityMessageInfo;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;
import nlj.business.carrier.link.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.link.dto.shipperTrspCapacity.CarInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.CutOffInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.DriverAvailabilityTimeDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.DriverInformationDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.FreeTimeInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.MessageInfoDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.ShipperTransportCapacityRegisterDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.TransportAbilityLineItemDTO;
import nlj.business.carrier.link.dto.shipperTrspCapacity.VehicleAvailabilityResourceDTO;
import nlj.business.carrier.link.mapper.CutOffInfoMapper;
import nlj.business.carrier.link.mapper.DateTimeMapper;
import nlj.business.carrier.link.mapper.FreeTimeInfoMapper;
import nlj.business.carrier.link.mapper.TransportAbilityMessageInfoMapper;
import nlj.business.carrier.link.repository.CarInfoRepository;
import nlj.business.carrier.link.repository.CarrierOperatorRepository;
import nlj.business.carrier.link.repository.CutOffInfoRepository;
import nlj.business.carrier.link.repository.LocationMasterRepository;
import nlj.business.carrier.link.repository.TransportAbilityLineItemRepository;
import nlj.business.carrier.link.repository.TransportAbilityMessageInfoRepository;
import nlj.business.carrier.link.repository.VehicleAvbResourceItemRepository;
import nlj.business.carrier.link.repository.VehicleDiagramMobilityHubCustomRepository;
import nlj.business.carrier.link.repository.VehicleDiagramMobilityHubRepository;
import nlj.business.carrier.link.service.TransportAbilityMessageInfoService;
import nlj.business.carrier.link.service.VehicleDiagramMobilityHubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * トランスポート機能メッセージ情報サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class TransportAbilityMessageInfoServiceImpl implements TransportAbilityMessageInfoService {

    private final Logger logger = LoggerFactory.getLogger(TransportAbilityMessageInfoServiceImpl.class);

    private final TransportAbilityMessageInfoRepository transportAbilityMessageInfoRepository;

    private final TransportAbilityMessageInfoMapper transportAbilityMessageInfoMapper;

    private final TransportAbilityLineItemRepository transportAbilityLineItemRepository;

    private final VehicleDiagramMobilityHubRepository vehicleDiagramMobilityHubRepository;

    private final CarInfoRepository carInfoRepository;

    private final LocationMasterRepository locationMasterRepository;

    private final VehicleAvbResourceItemRepository vehicleAvbResourceItemRepository;

    private final VehicleDiagramMobilityHubService vehicleDiagramMobilityHubService;

    private final VehicleDiagramMobilityHubCustomRepository vehicleDiagramMobilityHubCustomRepository;

    private final CarrierOperatorRepository carrierOperatorRepository;

    private final CutOffInfoRepository cutOffInfoRepository;
    private final CutOffInfoMapper cutOffInfoMapper;
    private final FreeTimeInfoMapper freeTimeInfoMapper;
    private final MobilityHubUtil mobilityHubUtil;
    private final ValidateUtil validateUtil;
    @Resource(name = "userContext")
    private UserContext userContext;

    /**
     * 荷送人の輸送能力を登録.<BR>
     *
     * @param dto 荷送人輸送能力レジスタ DTO
     */
    @Override
    @Transactional
    public void registerOrUpdateTransportInfor(ShipperTransportCapacityRegisterDTO dto, String xTracking) {
        String operatorId = userContext.getUser().getCompanyId();

        UUID uXTracking = null;
        if (!BaseUtil.isNull(xTracking)) {
            try {
                uXTracking = UUID.fromString(xTracking);
            } catch (Exception e) {
                /*throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(
                    MessageConstant.Validate.VALID_XTRACKING_FORMAT)));*/
            }
        }

        MessageInfoDTO messageInfoDTO = dto.getAttributeDTO().getMessageInfoDTO();
        TransportAbilityMessageInfo transportAbilityMessageInfo = transportAbilityMessageInfoMapper.mapToTransportAbilityMessageInfo(
            messageInfoDTO);
        if (transportAbilityMessageInfo != null) {
            transportAbilityMessageInfo.setOperatorId(operatorId);
            transportAbilityMessageInfo.setXTracking(uXTracking);
            transportAbilityMessageInfoRepository.save(transportAbilityMessageInfo);
        }

        Set<TransportAbilityLineItem> lineItems = new HashSet<>();
        for (TransportAbilityLineItemDTO transportAbilityLineItemDTO : dto.getAttributeDTO()
            .getTransportAbilityLineItemDTOS()) {
            TransportAbilityLineItem transportAbilityLineItemMapped = transportAbilityMessageInfoMapper.mapToTransportAbilityLineItem(
                transportAbilityLineItemDTO, transportAbilityLineItemDTO.getRoadCarrDTO(),
                transportAbilityLineItemDTO.getLogisticsServiceProviderDTO());
            CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(operatorId);
            String operatorCode = carrierOperator.getOperatorCode();
            TransportAbilityLineItem transportAbilityLineItemEntity = transportAbilityLineItemRepository.findFirstByTrspCliPrtyHeadOffIdAndTrspCliPrtyBrncOffIdAndOperatorId(
                operatorCode, operatorCode, operatorId);
            if (transportAbilityLineItemEntity != null) {
                transportAbilityMessageInfoMapper.updateTransportAbilityLineItem(transportAbilityLineItemEntity,
                    transportAbilityLineItemDTO, transportAbilityLineItemDTO.getRoadCarrDTO(),
                    transportAbilityLineItemDTO.getLogisticsServiceProviderDTO());
            } else {
                transportAbilityLineItemEntity = transportAbilityLineItemMapped;
                transportAbilityLineItemEntity.setTransportAbilityMessageInfo(transportAbilityMessageInfo);
                transportAbilityLineItemEntity.setOperatorId(operatorId);
            }
            transportAbilityLineItemEntity.setTrspCliPrtyHeadOffId(operatorCode);
            transportAbilityLineItemEntity.setTrspCliPrtyBrncOffId(operatorCode);

            transportAbilityLineItemRepository.save(transportAbilityLineItemEntity);

            insertOrUpdateCarInfo(transportAbilityLineItemDTO.getCarInfoDTOS(), transportAbilityLineItemEntity);

            insertDriverInfor(transportAbilityLineItemDTO.getDriverInformationDTOS(), transportAbilityLineItemEntity);

            lineItems.add(transportAbilityLineItemEntity);
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
                DriverInformation driverInformation = transportAbilityMessageInfoMapper.mapToDriverInformation(
                    driverInformationDTO);
                driverInformation.setTransportAbilityLineItem(transportAbilityLineItemEntity);
                driverInformation.setOperatorId(transportAbilityLineItemEntity.getOperatorId());

                if (driverInformationDTO.getDriverAvailabilityTimeDTOS() != null) {
                    Set<DriverAvailabilityTime> driverAvailabilityTimes = new HashSet<>();
                    for (DriverAvailabilityTimeDTO driverAvailabilityTimeDTO : driverInformationDTO.getDriverAvailabilityTimeDTOS()) {
                        DriverAvailabilityTime driverAvailabilityTimeEntity = transportAbilityMessageInfoMapper.mapToDriverAvailabilityTime(
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

    /**
     * 車両情報を登録または更新.<BR>
     *
     * @param carInfoDTOS                    車両情報のDTOセット
     * @param transportAbilityLineItemEntity 輸送能力ラインアイテムを表すエンティティ
     */
    private void insertOrUpdateCarInfo(Set<CarInfoDTO> carInfoDTOS,
        TransportAbilityLineItem transportAbilityLineItemEntity) {
        LocalDate maxDate = LocalDate.now().plusDays(15);
        if (carInfoDTOS != null) {
            Set<CarInfo> carInfos = new HashSet<>();
            for (CarInfoDTO carInfoDTO : carInfoDTOS) {
                CarInfo carInfoMapped = transportAbilityMessageInfoMapper.mapToCarInfo(carInfoDTO);
                CarInfo carInfoEntity = carInfoRepository.findFirstByServiceNoAndServiceStrtDateAndOperatorIdAndGiai(
                    carInfoMapped.getServiceNo(), carInfoMapped.getServiceStrtDate(),
                    transportAbilityLineItemEntity.getOperatorId(), carInfoMapped.getGiai());
                if (carInfoEntity != null) {
                    transportAbilityMessageInfoMapper.updateCarInfo(carInfoEntity, carInfoDTO);
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
                        VehicleAvailabilityResource vehicleAvailabilityResourceEntity = transportAbilityMessageInfoMapper.mapToVehicleAvailabilityResource(
                            vehicleAvailabilityResourceDTO);
                        vehicleAvailabilityResourceEntity.setCarInfo(carInfoEntity);
                        vehicleAvailabilityResourceEntity.setOperatorId(transportAbilityLineItemEntity.getOperatorId());
                        vehicleAvailabilityResourceEntity.setTrspOpStrtAreaLineOneTxt(BaseUtil.getGlnDefaultYamato(
                            vehicleAvailabilityResourceEntity.getTrspOpStrtAreaLineOneTxt(), true));
                        vehicleAvailabilityResourceEntity.setTrspOpEndAreaLineOneTxt(
                            BaseUtil.getGlnDefaultYamato(vehicleAvailabilityResourceEntity.getTrspOpEndAreaLineOneTxt(),
                                false));

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
                if (!carInfoEntity.getServiceStrtDate().isBefore(LocalDate.now()) && carInfoEntity.getServiceStrtDate()
                    .isBefore(maxDate)) {
                    int checkErrorCode = validateUtil.validateCommon(carInfoEntity.getServiceStrtDate(),
                        carInfoEntity.getServiceStrtTime(), carInfoEntity.getServiceEndTime());
                    if (checkErrorCode != SemiDynamicInfo.ERROR_CODE_0
                        && checkErrorCode != SemiDynamicInfo.ERROR_CODE_1) {
                        switch (checkErrorCode) {
                            case SemiDynamicInfo.ERROR_CODE_2:
                                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                                    ValidateCommonMessage.MESSAGE_ERROR_2);
                                break;
                            case SemiDynamicInfo.ERROR_CODE_3:
                                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                                    ValidateCommonMessage.MESSAGE_ERROR_3);
                                break;
                            case SemiDynamicInfo.ERROR_CODE_4:
                                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                                    ValidateCommonMessage.MESSAGE_ERROR_4);
                                break;
                            case SemiDynamicInfo.ERROR_CODE_5:
                                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                                    ValidateCommonMessage.MESSAGE_ERROR_5);
                                break;
                            default:
                                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
                                break;
                        }
                    }
                }
                carInfos.add(carInfoEntity);
            }
            List<CarInfo> listCarInfoInsert = carInfoRepository.saveAll(carInfos);

            //Insert vehicle avb resource
            for (CarInfo carInfo : listCarInfoInsert) {
                Set<VehicleAvailabilityResource> resourceSet = carInfo.getVehicleAvailabilityResources();
                if (resourceSet == null || resourceSet.isEmpty()) {
                    continue;
                }
                List<VehicleAvailabilityResource> resources = new ArrayList<>(resourceSet);
                List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubTractors = new ArrayList<>();
                for (VehicleAvailabilityResource resource : resources) {
                    Set<CutOffInfo> cutOffInfosSet = resource.getCutOffInfos();
                    Set<FreeTimeInfo> freeTimeInfosSet = resource.getFreeTimeInfos();
                    List<CutOffInfo> cutOffInfos =
                        cutOffInfosSet != null ? new ArrayList<>(cutOffInfosSet) : Collections.emptyList();
                    List<FreeTimeInfo> freeTimeInfos =
                        freeTimeInfosSet != null ? new ArrayList<>(freeTimeInfosSet) : Collections.emptyList();

                    processVehicleAvbResourceItems(transportAbilityLineItemEntity, carInfo, resource, cutOffInfos,
                        freeTimeInfos);
                    insertOrUpdateMobilityHub(resource, carInfo, vehicleDiagramMobilityHubTractors);
                }
            }
        }
    }

    /**
     * 荷送業者の輸送能力を削除.<BR>
     *
     * @param dto 荷送人輸送能力レジスタ DTO
     */
    @Override
    @Transactional
    public void deleteTransportInfor(ShipperTransportCapacityRegisterDTO dto) {
        String operatorId = userContext.getUser().getCompanyId();

        dto.getAttributeDTO().getTransportAbilityLineItemDTOS().forEach(lineItem -> {
            processCarInfo(lineItem);
            processDriverInfo(lineItem);
            handleTransportLineItem(operatorId);
        });
    }

    private void processCarInfo(TransportAbilityLineItemDTO lineItem) {
        lineItem.getCarInfoDTOS().forEach(carInfo -> {
            transportAbilityMessageInfoMapper.mapToCarInfo(carInfo);
            carInfo.getVehicleAvailabilityResourceDTOS().forEach(resource -> {
                transportAbilityMessageInfoMapper.mapToVehicleAvailabilityResource(resource);
                if (resource.getCutOffInfoDTOS() != null) {
                    resource.getCutOffInfoDTOS().forEach(cutOffInfoMapper::mapToCutOffInfo);
                }
                if (resource.getFreeTimeInfoDTOS() != null) {
                    resource.getFreeTimeInfoDTOS().forEach(freeTimeInfoMapper::mapToFreeTimeInfo);
                }
            });
        });
    }

    private void processDriverInfo(TransportAbilityLineItemDTO lineItem) {
        lineItem.getDriverInformationDTOS().forEach(driverInfo -> {
            transportAbilityMessageInfoMapper.mapToDriverInformation(driverInfo);
            if (driverInfo.getDriverAvailabilityTimeDTOS() != null) {
                driverInfo.getDriverAvailabilityTimeDTOS()
                    .forEach(transportAbilityMessageInfoMapper::mapToDriverAvailabilityTime);
            }
        });
    }

    private void handleTransportLineItem(String operatorId) {
        CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(operatorId);
        String operatorCode = carrierOperator.getOperatorCode();
        TransportAbilityLineItem transportLineEntity = transportAbilityLineItemRepository
            .findFirstByTrspCliPrtyHeadOffIdAndTrspCliPrtyBrncOffIdAndOperatorId(
                operatorCode,
                operatorCode,
                operatorId
            );

        if (transportLineEntity == null) {
            throw new NextWebException(new NextAPIError(HttpStatus.NOT_FOUND,
                SoaResponsePool.get(MessageConstant.TrspAbilityLineItem.RSP_ABILITY_LINE_ITEM_NOT_EXIST)));
        }
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubs = new ArrayList<>();
        if (transportLineEntity.getCarInfos() != null && !transportLineEntity.getCarInfos().isEmpty()) {
            for (CarInfo carInfo : transportLineEntity.getCarInfos()) {
                vehicleDiagramMobilityHubs = vehicleDiagramMobilityHubCustomRepository.findByVehicleDiagramItemId(
                    carInfo.getId());
                if (vehicleDiagramMobilityHubs != null && !vehicleDiagramMobilityHubs.isEmpty()) {
                    vehicleDiagramMobilityHubs.forEach(mobilityHub -> {
                        mobilityHubUtil.deleteMobilityHub(mobilityHub.getMhReservationId());
                    });
                    vehicleDiagramMobilityHubRepository.deleteAll(vehicleDiagramMobilityHubs);
                }
            }
        }

        transportAbilityLineItemRepository.delete(transportLineEntity);
    }

    /**
     * 荷送業者の輸送能力を削除
     *
     * @param trspCliPrtyHeadOffId 荷送業者の頭拠点ID
     * @param trspCliPrtyBrncOffId 荷送業者の支拠点ID
     * @param serviceNo            サービス番号
     * @param serviceStrtDate      サービス開始日
     */
    @Override
    @Transactional
    public void deleteTransportInforYamato(String trspCliPrtyHeadOffId, String trspCliPrtyBrncOffId, String serviceNo,
        String serviceStrtDate) {
        String operatorId = userContext.getUser().getCompanyId();
        if (BaseUtil.isNull(trspCliPrtyHeadOffId)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(MessageConstant.Validate.VALID_NOT_NULL), ParamConstant.TRSP_CLI_PRTY_HEAD_OFF_ID));
        }
        if (BaseUtil.isNull(trspCliPrtyBrncOffId)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(MessageConstant.Validate.VALID_NOT_NULL), ParamConstant.TRSP_CLI_PRTY_BRNC_OFF_ID));
        }
        if (BaseUtil.isNull(serviceNo)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(MessageConstant.Validate.VALID_NOT_NULL), ParamConstant.SERVICE_NO));
        }
        if (BaseUtil.isNull(serviceStrtDate)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(MessageConstant.Validate.VALID_NOT_NULL), ParamConstant.SERVICE_STRT_DATE));
        }
        try {
            DateTimeMapper.stringToLocalDate(serviceStrtDate);
        } catch (Exception e) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_DATE_FORMAT), ParamConstant.SERVICE_STRT_DATE,
                    DATE_TIME_FORMAT.DATE_FORMAT));
        }
        CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(operatorId);
        String operatorCode = carrierOperator.getOperatorCode();
        List<TransportAbilityLineItem> transportLineEntities = transportAbilityLineItemRepository
            .findAllByTrspCliPrtyHeadOffIdAndTrspCliPrtyBrncOffIdAndOperatorId(
                operatorCode,
                operatorCode,
                operatorId
            );

        if (transportLineEntities == null || transportLineEntities.isEmpty()) {
            throw new NextWebException(new NextAPIError(HttpStatus.NOT_FOUND,
                SoaResponsePool.get(MessageConstant.TrspAbilityLineItem.RSP_ABILITY_LINE_ITEM_NOT_EXIST)));
        }
        TransportAbilityLineItem transportLineEntity = transportLineEntities.stream()
            .filter(item -> item.getCarInfos().stream()
                .anyMatch(carInfo -> carInfo.getServiceNo().equals(serviceNo) &&
                    carInfo.getOperatorId().equals(operatorId) &&
                    carInfo.getServiceStrtDate().equals(DateTimeMapper.stringToLocalDate(serviceStrtDate))))
            .findFirst()
            .orElse(null);

        if (transportLineEntity == null) {
            throw new NextWebException(new NextAPIError(HttpStatus.NOT_FOUND,
                SoaResponsePool.get(MessageConstant.TrspAbilityLineItem.RSP_ABILITY_LINE_ITEM_NOT_EXIST)));
        }

        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubs = new ArrayList<>();
        if (transportLineEntity.getCarInfos() != null && !transportLineEntity.getCarInfos().isEmpty()) {
            for (CarInfo carInfo : transportLineEntity.getCarInfos()) {
                vehicleDiagramMobilityHubs = vehicleDiagramMobilityHubCustomRepository.findByVehicleDiagramItemId(
                    carInfo.getId());
                if (vehicleDiagramMobilityHubs != null && !vehicleDiagramMobilityHubs.isEmpty()) {
                    vehicleDiagramMobilityHubs.forEach(mobilityHub -> {
                        mobilityHubUtil.deleteMobilityHub(mobilityHub.getMhReservationId());
                    });
                    vehicleDiagramMobilityHubRepository.deleteAll(vehicleDiagramMobilityHubs);
                }
            }
        }

        transportAbilityLineItemRepository.delete(transportLineEntity);
    }

    public static LocalTime subtractTime(LocalTime time, BigDecimal offset) {
        int hours = offset.intValue();
        int minutes = offset.subtract(BigDecimal.valueOf(hours))
            .multiply(BigDecimal.valueOf(60))
            .intValue();
        return time.minusHours(hours).minusMinutes(minutes);
    }

    //Function Insert vehicle_avb_resource
    private void processVehicleAvbResourceItems(TransportAbilityLineItem transportAbilityLineItem, CarInfo carInfo,
        VehicleAvailabilityResource vehicleAvailabilityResource,
        List<CutOffInfo> cutOffInfos, List<FreeTimeInfo> freeTimeInfos) {
        Set<VehicleAvbResourceItem> vehicleAvbResourceItems = new HashSet<>();
        if (!cutOffInfos.isEmpty() && !freeTimeInfos.isEmpty()) {
            for (int j = 0; j < cutOffInfos.size(); j++) {
                for (int k = 0; k < freeTimeInfos.size(); k++) {
                    CutOffInfo cutOffInfo = cutOffInfos.get(j);
                    BigDecimal preTime = new BigDecimal(0);
                    if (j > 0) {
                        preTime = cutOffInfos.get(j - 1).getCutOffTime();
                    }
                    FreeTimeInfo freeTimeInfo = freeTimeInfos.get(k);
                    BigDecimal price = calculatePrice(carInfo, cutOffInfo, freeTimeInfo);
                    VehicleAvbResourceItem vehicleAvbResourceItem = createVehicleAvbResourceItem(
                        transportAbilityLineItem, carInfo, vehicleAvailabilityResource,
                        cutOffInfo, freeTimeInfo, price, preTime);
                    vehicleAvbResourceItems.add(vehicleAvbResourceItem);
                }
            }
        } else if (!cutOffInfos.isEmpty()) {
            for (int j = 0; j < cutOffInfos.size(); j++) {
                CutOffInfo cutOffInfo = cutOffInfos.get(j);
                BigDecimal preTime = new BigDecimal(0);
                if (j > 0) {
                    preTime = cutOffInfos.get(j - 1).getCutOffTime();
                }
                BigDecimal price = calculatePrice(carInfo, cutOffInfo, null);
                VehicleAvbResourceItem vehicleAvbResourceItem = createVehicleAvbResourceItem(transportAbilityLineItem,
                    carInfo, vehicleAvailabilityResource,
                    cutOffInfo, null, price, preTime);
                vehicleAvbResourceItems.add(vehicleAvbResourceItem);
            }
        } else if (!freeTimeInfos.isEmpty()) {
            for (int j = 0; j < freeTimeInfos.size(); j++) {
                FreeTimeInfo freeTimeInfo = freeTimeInfos.get(j);
                BigDecimal preTime = new BigDecimal(0);
                if (j > 0) {
                    preTime = cutOffInfos.get(j - 1).getCutOffTime();
                }
                BigDecimal price = calculatePrice(carInfo, null, freeTimeInfo);
                VehicleAvbResourceItem vehicleAvbResourceItem = createVehicleAvbResourceItem(transportAbilityLineItem,
                    carInfo, vehicleAvailabilityResource,
                    null, freeTimeInfo, price, preTime);
                vehicleAvbResourceItems.add(vehicleAvbResourceItem);
            }
        }

        vehicleAvbResourceItemRepository.saveAll(vehicleAvbResourceItems);
    }

    //Calculate price
    private BigDecimal calculatePrice(CarInfo carInfo, CutOffInfo cutOffInfo, FreeTimeInfo freeTimeInfo) {
        BigDecimal price = carInfo.getFreightRate();
        BigDecimal cutOffFee = BigDecimal.valueOf(0);
        BigDecimal freeTimeFee = BigDecimal.valueOf(0);
        if (cutOffInfo != null && !BaseUtil.isNull(String.valueOf(cutOffInfo.getCutOffFee()))) {
            cutOffFee = cutOffInfo.getCutOffFee();
        }
        if (freeTimeInfo != null && !BaseUtil.isNull(String.valueOf(freeTimeInfo.getFreeTimeFee()))) {
            freeTimeFee = freeTimeInfo.getFreeTimeFee();
        }
        if (cutOffInfo != null) {
            price = price.subtract(cutOffFee);
        }
        if (freeTimeInfo != null) {
            price = price.subtract(freeTimeFee);
        }
        return price;
    }

    //Function create vehicle_avb_resource
    private VehicleAvbResourceItem createVehicleAvbResourceItem(TransportAbilityLineItem transportAbilityLineItem,
        CarInfo carInfo, VehicleAvailabilityResource vehicleAvailabilityResource,
        CutOffInfo cutOffInfo, FreeTimeInfo freeTimeInfo, BigDecimal price, BigDecimal preTime) {
        CarrierOperator carrierOperator = carrierOperatorRepository.findByCarrierOperatorId(
            transportAbilityLineItem.getOperatorId());
        VehicleAvbResourceItem vehicleAvbResourceItem = new VehicleAvbResourceItem();
        vehicleAvbResourceItem.setOperatorId(transportAbilityLineItem.getOperatorId());

        vehicleAvbResourceItem.setPrice(price);
        vehicleAvbResourceItem.setCarInfo(carInfo);
        vehicleAvbResourceItem.setVehicleAvailabilityResource(vehicleAvailabilityResource);
        vehicleAvbResourceItem.setDepartureFrom(
            Long.parseLong(
                BaseUtil.getGlnDefaultYamato(vehicleAvailabilityResource.getTrspOpStrtAreaLineOneTxt(), true)));
        vehicleAvbResourceItem.setArrivalTo(Long.parseLong(
            BaseUtil.getGlnDefaultYamato(vehicleAvailabilityResource.getTrspOpEndAreaLineOneTxt(), false)));

        vehicleAvbResourceItem.setTripName(carInfo.getServiceName());

        vehicleAvbResourceItem.setMaxPayload(carInfo.getCarWeigMeas());
        if (carrierOperator != null) {
            vehicleAvbResourceItem.setOperatorCode(carrierOperator.getOperatorCode());
            vehicleAvbResourceItem.setOperatorName(carrierOperator.getOperatorName());
        } else {
            vehicleAvbResourceItem.setOperatorCode("");
            vehicleAvbResourceItem.setOperatorName("");
        }

        if (cutOffInfo != null) {
            if (BaseUtil.isNull(String.valueOf(cutOffInfo.getCutOffTime()))) {
                vehicleAvbResourceItem.setDepartureTimeMin(
                    subtractTime(carInfo.getServiceStrtTime(), BigDecimal.valueOf(0)));
            } else {
                vehicleAvbResourceItem.setDepartureTimeMin(
                    subtractTime(carInfo.getServiceStrtTime(), cutOffInfo.getCutOffTime()));
            }
            vehicleAvbResourceItem.setDepartureTimeMax(subtractTime(carInfo.getServiceStrtTime(), preTime));

            vehicleAvbResourceItem.setCutOffInfoId(cutOffInfo.getId());
            vehicleAvbResourceItem.setCutOffTime(cutOffInfo.getCutOffTime());
            vehicleAvbResourceItem.setCutOffFee(cutOffInfo.getCutOffFee());
        }

        if (freeTimeInfo != null) {
            vehicleAvbResourceItem.setDepartureTimeMin(
                subtractTime(carInfo.getServiceStrtTime(), freeTimeInfo.getFreeTime()));
            vehicleAvbResourceItem.setDepartureTimeMax(subtractTime(carInfo.getServiceStrtTime(), preTime));
            vehicleAvbResourceItem.setFreeTimeInfoId(freeTimeInfo.getId());
            vehicleAvbResourceItem.setFreeTimeFee(freeTimeInfo.getFreeTime());
            vehicleAvbResourceItem.setFreeTimeFee(freeTimeInfo.getFreeTimeFee());
        }

        vehicleAvbResourceItem.setArrivalTime(carInfo.getServiceEndTime());
        vehicleAvbResourceItem.setDay(carInfo.getServiceStrtDate());
        vehicleAvbResourceItem.setTotalHeight(carInfo.getCarHghtMeas());
        vehicleAvbResourceItem.setTotalWidth(carInfo.getCarWidMeas());
        vehicleAvbResourceItem.setTotalLength(carInfo.getCarLnghMeas());
        vehicleAvbResourceItem.setGiai(carInfo.getGiai());

        if (carInfo.getTractorIdcr() != null) {
            vehicleAvbResourceItem.setVehicleType(Integer.parseInt(carInfo.getTractorIdcr()));
        }
        vehicleAvbResourceItem.setStatus(ParamConstant.Status.INITIALIZED);
        return vehicleAvbResourceItem;
    }

    private void insertOrUpdateMobilityHub(VehicleAvailabilityResource vehicleAvailabilityResource, CarInfo carInfo,
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubTractors) {
        if (BaseUtil.isNull(carInfo.getTractorIdcr()) || carInfo.getTractorIdcr().equals("1")) {
            return;
        }
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubs = vehicleDiagramMobilityHubCustomRepository.findByVehicleDiagramItemId(
            vehicleAvailabilityResource.getCarInfo().getId());
        if (!vehicleDiagramMobilityHubs.isEmpty()) {
            vehicleDiagramMobilityHubs.forEach(mobilityHub -> {
                mobilityHubUtil.deleteMobilityHub(mobilityHub.getMhReservationId());
            });
            vehicleDiagramMobilityHubRepository.deleteAll(vehicleDiagramMobilityHubs);
        }
        // trsp_op_strt_area_cty_jis_cd
        callMobilityHub(vehicleAvailabilityResource, carInfo, ParamConstant.MobilityHub.TYPE_0,
            vehicleDiagramMobilityHubTractors);
        // trsp_op_end_area_cty_jis_cd
        callMobilityHub(vehicleAvailabilityResource, carInfo, ParamConstant.MobilityHub.TYPE_1,
            vehicleDiagramMobilityHubTractors);
    }

    private void callMobilityHub(VehicleAvailabilityResource vehicleAvailabilityResource, CarInfo carInfo, int type,
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubTractors) {
        LocationMaster locationMaster = null;
        String mobilityHubId = null;
        VehicleDiagramMobilityHub vehicleDiagramMobilityHub;
        VehicleDiagramMobilityHub vehicleDiagramMobilityHubTractor = null;
        boolean isExist = false;
        if (type == ParamConstant.MobilityHub.TYPE_0) {
            mobilityHubId = BaseUtil.getGlnFrom(vehicleAvailabilityResource.getTrspOpStrtAreaLineOneTxt());
            // get cut off info
            List<CutOffInfo> cutOffInfos = cutOffInfoRepository.findCutOffInfoByVehicleAvailabilityResource(
                vehicleAvailabilityResource);
            BigDecimal cutOffTime = BigDecimal.ZERO;
            if (!cutOffInfos.isEmpty()) {
                cutOffTime = cutOffInfos.stream()
                    .map(CutOffInfo::getCutOffTime)
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            }
            isExist = isExist(vehicleDiagramMobilityHubTractors, mobilityHubId, carInfo.getGiai(),
                vehicleAvailabilityResource.getCarInfo().getId(), type);
            // insert or update tractor
            if (!isExist) {
                vehicleDiagramMobilityHubTractor = vehicleDiagramMobilityHubService.saveVehicleDiagramMobilityHub(
                    vehicleAvailabilityResource.getCarInfo().getId(),
                    vehicleAvailabilityResource.getId(), mobilityHubId, carInfo.getCarClsOfSizeCd(),
                    carInfo.getServiceStrtDate().atTime(carInfo.getServiceStrtTime()).minusHours(cutOffTime.intValue()),
                    carInfo.getServiceStrtDate().atTime(carInfo.getServiceStrtTime()).plusMinutes(15), null,
                    carInfo.getServiceNo(),
                    carInfo.getCarLicensePltNumId(), type, ParamConstant.MobilityHub.VEHICLE_TYPE_TRACTOR);
                vehicleDiagramMobilityHubTractors.add(vehicleDiagramMobilityHubTractor);
            }
            // insert or update trailer
            vehicleDiagramMobilityHub = vehicleDiagramMobilityHubService.saveVehicleDiagramMobilityHub(
                vehicleAvailabilityResource.getCarInfo().getId(),
                vehicleAvailabilityResource.getId(), mobilityHubId, carInfo.getCarClsOfSizeCd(),
                carInfo.getServiceStrtDate().atTime(carInfo.getServiceStrtTime()).minusHours(cutOffTime.intValue()),
                carInfo.getServiceStrtDate().atTime(carInfo.getServiceStrtTime()).plusMinutes(15), null,
                carInfo.getServiceNo(),
                carInfo.getTrailerLicensePltNumId(), type, ParamConstant.MobilityHub.VEHICLE_TYPE_TRAILER);
        } else {
            mobilityHubId = BaseUtil.getGlnTo(vehicleAvailabilityResource.getTrspOpEndAreaLineOneTxt());
            isExist = isExist(vehicleDiagramMobilityHubTractors, mobilityHubId, carInfo.getGiai(),
                vehicleAvailabilityResource.getCarInfo().getId(), type);
            // insert or update tractor
            if (!isExist) {
                vehicleDiagramMobilityHubTractor = vehicleDiagramMobilityHubService.saveVehicleDiagramMobilityHub(
                    vehicleAvailabilityResource.getCarInfo().getId(),
                    vehicleAvailabilityResource.getId(), mobilityHubId, carInfo.getCarClsOfSizeCd(),
                    carInfo.getServiceEndDate().atTime(carInfo.getServiceEndTime()).minusMinutes(15),
                    carInfo.getServiceEndDate().atTime(carInfo.getServiceEndTime()).plusHours(3), null,
                    carInfo.getServiceNo(), carInfo.getCarLicensePltNumId(), type,
                    ParamConstant.MobilityHub.VEHICLE_TYPE_TRACTOR);
                vehicleDiagramMobilityHubTractors.add(vehicleDiagramMobilityHubTractor);
            }
            // insert or update trailer
            vehicleDiagramMobilityHub = vehicleDiagramMobilityHubService.saveVehicleDiagramMobilityHub(
                vehicleAvailabilityResource.getCarInfo().getId(),
                vehicleAvailabilityResource.getId(), mobilityHubId, carInfo.getCarClsOfSizeCd(),
                carInfo.getServiceEndDate().atTime(carInfo.getServiceEndTime()).minusMinutes(15),
                carInfo.getServiceEndDate().atTime(carInfo.getServiceEndTime()).plusHours(3), null,
                carInfo.getServiceNo(), carInfo.getTrailerLicensePltNumId(), type,
                ParamConstant.MobilityHub.VEHICLE_TYPE_TRAILER);
        }
        if (vehicleDiagramMobilityHub == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                MessageConstant.VehicleDiagramMobilityHub.ERROR_INSERT_UPDATE_VEHICLE_DIAGRAM_MOBILITY_HUB);
        }
        List<String> mobilityHubIds = new ArrayList<>();
        if (vehicleDiagramMobilityHubTractor != null) {
            mobilityHubIds.add(vehicleDiagramMobilityHubTractor.getMobilityHubId());
        }
        mobilityHubIds.add(vehicleDiagramMobilityHub.getMobilityHubId());
        List<String> freightIds = new ArrayList<>();
        if (vehicleDiagramMobilityHubTractor != null) {
            freightIds.add(vehicleDiagramMobilityHubTractor.getFreightId());
        }
        freightIds.add(vehicleDiagramMobilityHub.getFreightId());
        List<String> truckIds = new ArrayList<>();
        if (vehicleDiagramMobilityHubTractor != null) {
            truckIds.add(vehicleDiagramMobilityHubTractor.getTruckId());
        }
        truckIds.add(vehicleDiagramMobilityHub.getTruckId());
        List<String> sizeClassIds = new ArrayList<>();
        if (vehicleDiagramMobilityHubTractor != null) {
            sizeClassIds.add(vehicleDiagramMobilityHubTractor.getSizeClass());
        }
        sizeClassIds.add(vehicleDiagramMobilityHub.getSizeClass());
        List<LocalDateTime> validFroms = new ArrayList<>();
        if (vehicleDiagramMobilityHubTractor != null) {
            validFroms.add(vehicleDiagramMobilityHubTractor.getValidFrom());
        }
        validFroms.add(vehicleDiagramMobilityHub.getValidFrom());
        List<LocalDateTime> validUntil = new ArrayList<>();
        if (vehicleDiagramMobilityHubTractor != null) {
            validUntil.add(vehicleDiagramMobilityHubTractor.getValidUntil());
        }
        validUntil.add(vehicleDiagramMobilityHub.getValidUntil());

        ReservationResponseDTO responseDTO = mobilityHubUtil.updateMobilityHub(mobilityHubIds, freightIds, truckIds,
            sizeClassIds, validFroms, validUntil);

        if (responseDTO != null) {
            // update tractor
            String slotIdTractor = null;
            String reservationId = responseDTO.getAttribute().getStatuses().get(0).getKey();
            if (vehicleDiagramMobilityHubTractor != null) {
                try {
                    slotIdTractor = reservationId.split(ParamConstant.MobilityHub.SLOT_ID_PREFIX)[1];
                } catch (Exception e) {
                    logger.error("Error splitting reservationId: {}", reservationId);
                }
                vehicleDiagramMobilityHubTractor.setMhReservationId(reservationId);
                vehicleDiagramMobilityHubTractor.setReservationStatus(ParamConstant.Status.RESERVATION_STATUS_2);
                vehicleDiagramMobilityHubTractor.setSlotId(slotIdTractor);
                reservationId = responseDTO.getAttribute().getStatuses().get(1).getKey();
            }
            // update trailer
            String slotIdTrailer = null;
            try {
                slotIdTrailer = reservationId.split(ParamConstant.MobilityHub.SLOT_ID_PREFIX)[1];
            } catch (Exception e) {
                logger.error("Error splitting reservationId: {}", reservationId);
            }
            vehicleDiagramMobilityHub.setSlotId(slotIdTrailer);
            vehicleDiagramMobilityHub.setMhReservationId(reservationId);
            vehicleDiagramMobilityHub.setReservationStatus(ParamConstant.Status.RESERVATION_STATUS_2);
        } else {
            vehicleDiagramMobilityHub.setReservationStatus(ParamConstant.Status.RESERVATION_STATUS_1);
            if (vehicleDiagramMobilityHubTractor != null) {
                vehicleDiagramMobilityHubTractor.setReservationStatus(ParamConstant.Status.RESERVATION_STATUS_1);
            }
        }
        vehicleDiagramMobilityHubRepository.save(vehicleDiagramMobilityHub);
        if (vehicleDiagramMobilityHubTractor != null) {
            vehicleDiagramMobilityHubRepository.save(vehicleDiagramMobilityHubTractor);
        }
    }

    private boolean isExist(List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubs, String mobilityHubId,
        String truckId, Long vehicleDiagramItemId, int type) {
        if (vehicleDiagramMobilityHubs.isEmpty()) {
            return false;
        }
        return vehicleDiagramMobilityHubs.stream().filter(
                item -> item.getMobilityHubId().equals(mobilityHubId) && item.getTruckId().equals(truckId)
                    && item.getVehicleDiagramItemId().equals(vehicleDiagramItemId) && item.getType().equals(type))
            .findFirst().orElse(null) != null;
    }
}