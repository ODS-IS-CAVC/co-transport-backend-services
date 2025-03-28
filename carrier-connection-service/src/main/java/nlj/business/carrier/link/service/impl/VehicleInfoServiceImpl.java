package nlj.business.carrier.link.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.MessageConstant;
import nlj.business.carrier.link.constant.MessageConstant.System;
import nlj.business.carrier.link.constant.MessageConstant.Validate;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.constant.ParamConstant.DataModelType;
import nlj.business.carrier.link.domain.HazardousMaterialInfo;
import nlj.business.carrier.link.domain.MaxCarryingCapacity;
import nlj.business.carrier.link.domain.VehicleInfo;
import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.commonBody.response.CommonResponseDTO;
import nlj.business.carrier.link.dto.vehicleInfo.HazardousMaterialInfoDTO;
import nlj.business.carrier.link.dto.vehicleInfo.MaxCarryingCapacityDTO;
import nlj.business.carrier.link.dto.vehicleInfo.request.VehicleInfoRequestDTO;
import nlj.business.carrier.link.dto.vehicleInfo.response.VehicleInfoResponseDTO;
import nlj.business.carrier.link.mapper.VehicleInfoMapper;
import nlj.business.carrier.link.repository.HazardousMaterialInfoRepository;
import nlj.business.carrier.link.repository.MaxCarryingCapacityRepository;
import nlj.business.carrier.link.repository.VehicleInfoRepository;
import nlj.business.carrier.link.service.VehicleInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 車両情報サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleInfoServiceImpl implements VehicleInfoService {

    private final VehicleInfoRepository vehicleInfoRepository;
    private final VehicleInfoMapper vehicleInfoMapper;
    private final MaxCarryingCapacityRepository maxCarryingCapacityRepository;
    private final HazardousMaterialInfoRepository hazardousMaterialInfoRepository;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 車両情報を登録
     *
     * @param commonRequestDTO 共通リクエストDTO
     */
    @Override
    @Transactional
    public void registerOrUpdateVehicleInfo(CommonRequestDTO commonRequestDTO) {
        String operationId = userContext.getUser().getCompanyId();
        VehicleInfoRequestDTO vehicleInfoRequestDTO = convertRequestAttribute(commonRequestDTO);
        validateVehicleRequest(vehicleInfoRequestDTO);
        VehicleInfo existVehicleInfo = vehicleInfoRepository
            .findByGiaiAndOperatorId(vehicleInfoRequestDTO.getVehicleInfo().getGiai(), operationId);
        if (Objects.nonNull(existVehicleInfo)) {
            saveVehicle(existVehicleInfo, vehicleInfoRequestDTO, operationId);
        } else {
            saveVehicle(null, vehicleInfoRequestDTO, operationId);
        }
    }

    /**
     * 車両情報を保存
     *
     * @param vehicleInfo           車両情報
     * @param vehicleInfoRequestDTO 車両情報リクエストDTO
     * @param operationId           操作ID
     */
    private void saveVehicle(VehicleInfo vehicleInfo, VehicleInfoRequestDTO vehicleInfoRequestDTO, String operationId) {
        VehicleInfo saveVehicleInfo = vehicleInfoMapper.toVehicleInfo(vehicleInfoRequestDTO.getVehicleInfo(),
            vehicleInfoRequestDTO.getMotasInfo(), vehicleInfoRequestDTO.getVehicleDetails());
        if (Objects.nonNull(vehicleInfo)) {
            saveVehicleInfo.setId(vehicleInfo.getId());
            maxCarryingCapacityRepository.deleteAllByVehicleInfo(vehicleInfo);
            hazardousMaterialInfoRepository.deleteAllByVehicleInfo(vehicleInfo);
        }
        saveVehicleInfo.setOperatorId(operationId);
        VehicleInfo savedVehicleInfo = vehicleInfoRepository.save(saveVehicleInfo);
        if (vehicleInfoRequestDTO.getMaxCarryingCapacity() != null && !vehicleInfoRequestDTO.getMaxCarryingCapacity()
            .isEmpty()) {
            for (MaxCarryingCapacityDTO maxCarryingCapacityDTO : vehicleInfoRequestDTO.getMaxCarryingCapacity()) {
                MaxCarryingCapacity maxCarryingCapacity = vehicleInfoMapper.toMaxCarryingCapacity(
                    maxCarryingCapacityDTO);
                maxCarryingCapacity.setOperatorId(operationId);
                maxCarryingCapacity.setVehicleInfo(savedVehicleInfo);
                maxCarryingCapacityRepository.save(maxCarryingCapacity);
            }
        }
        if (vehicleInfoRequestDTO.getHazardousMaterialInfo() != null
            && !vehicleInfoRequestDTO.getHazardousMaterialInfo().isEmpty()) {
            for (HazardousMaterialInfoDTO hazardousMaterialInfoDTO : vehicleInfoRequestDTO.getHazardousMaterialInfo()) {
                HazardousMaterialInfo hazardousMaterialInfo = vehicleInfoMapper.toHazardousMaterialInfo(
                    hazardousMaterialInfoDTO);
                hazardousMaterialInfo.setOperatorId(operationId);
                hazardousMaterialInfo.setVehicleInfo(savedVehicleInfo);
                hazardousMaterialInfoRepository.save(hazardousMaterialInfo);
            }
        }
    }

    /**
     * 車種に基づいて車両情報を検索
     *
     * @param vehicleType 検索する車種を入力します
     * @return 一致する車両情報のリスト
     */
    @Override
    public CommonResponseDTO searchVehicleByVehicleType(String vehicleType) {
        String operationId = userContext.getUser().getCompanyId();
        if (BaseUtil.isNull(vehicleType)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
        }
        List<VehicleInfo> vehicleInfoList = vehicleInfoRepository.findByVehicleTypeAndOperatorId(vehicleType,
            operationId);
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        responseDTO.setDataModelType(DataModelType.TEST_1);
        List<VehicleInfoResponseDTO> vehicleInfoResponseDTOList = new ArrayList<>();
        if (!vehicleInfoList.isEmpty()) {
            vehicleInfoResponseDTOList = vehicleInfoList.stream()
                .map(this::convertToResponseDTO)
                .toList();

        }
        VehicleInfoResponseDTO vehicleInfoResponseDTO = new VehicleInfoResponseDTO();
        if (!vehicleInfoResponseDTOList.isEmpty()) {
            vehicleInfoResponseDTO = vehicleInfoResponseDTOList.get(0);
        }
        responseDTO.setAttribute(vehicleInfoResponseDTO);
        return responseDTO;
    }

    /**
     * 車両情報を削除
     *
     * @param commonRequestDTO 共通リクエストDTO
     */
    @Override
    public void delete(CommonRequestDTO commonRequestDTO) {
        String operationId = userContext.getUser().getCompanyId();
        VehicleInfoRequestDTO vehicleInfoRequestDTO = convertRequestAttribute(commonRequestDTO);
        VehicleInfo vehicleInfo = vehicleInfoRepository.findByGiaiAndOperatorId(
            vehicleInfoRequestDTO.getVehicleInfo().getGiai(), operationId);
        if (Objects.isNull(vehicleInfo)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.VehicleInfo.VEHICLE_INFO_NOT_FOUND)));
        }
        vehicleInfoRepository.deleteById(vehicleInfo.getId());
    }

    /**
     * 車輛マスタを削除
     *
     * @param giai 削除したいGIAI
     */
    @Override
    @Transactional
    public void deleteVehicle(String giai) {
        String operationId = userContext.getUser().getCompanyId();
        if (BaseUtil.isNull(giai)) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get(MessageConstant.Validate.VALID_NOT_NULL), ParamConstant.GIAI));
        }
        VehicleInfo vehicleInfo = vehicleInfoRepository.findByGiaiAndOperatorId(giai, operationId);
        if (Objects.isNull(vehicleInfo)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.NOT_FOUND,
                    SoaResponsePool.get(MessageConstant.VehicleInfo.VEHICLE_INFO_NOT_FOUND)));
        }
        vehicleInfoRepository.deleteById(vehicleInfo.getId());
    }

    /**
     * 車両情報をレスポンスDTOに変換
     *
     * @param vehicleInfo 車両情報
     * @return 車両情報レスポンスDTO
     */
    private VehicleInfoResponseDTO convertToResponseDTO(VehicleInfo vehicleInfo) {
        VehicleInfoResponseDTO dto = new VehicleInfoResponseDTO();

        // Map basic vehicle info
        dto.setVehicleInfo(vehicleInfoMapper.toVehicleInfoDTO(vehicleInfo));
        dto.setMotasInfo(vehicleInfoMapper.toMotasInfoDTO(vehicleInfo));
        dto.setVehicleDetails(vehicleInfoMapper.toVehicleDetailsDTO(vehicleInfo));

        // Get and map MaxCarryingCapacity data
        List<MaxCarryingCapacity> maxCapacities = maxCarryingCapacityRepository
            .findByVehicleInfoId(vehicleInfo.getId());
        if (!maxCapacities.isEmpty()) {
            List<MaxCarryingCapacityDTO> maxCapacityDTOs = new ArrayList<>();
            for (MaxCarryingCapacity maxCapacity : maxCapacities) {
                maxCapacityDTOs.add(vehicleInfoMapper.toMaxCarryingCapacityDTO(maxCapacity));
            }
            dto.setMaxCarryingCapacity(maxCapacityDTOs);
        }

        // Get and map HazardousMaterialInfo data
        List<HazardousMaterialInfo> hazardousInfos = hazardousMaterialInfoRepository
            .findByVehicleInfoId(vehicleInfo.getId());
        if (!hazardousInfos.isEmpty()) {
            List<HazardousMaterialInfoDTO> hazardousInfoDTOs = new ArrayList<>();
            for (HazardousMaterialInfo hazardousInfo : hazardousInfos) {
                hazardousInfoDTOs.add(vehicleInfoMapper.toHazardousMaterialInfoDTO(hazardousInfo));
            }
            dto.setHazardousMaterialInfo(hazardousInfoDTOs);
        }

        return dto;
    }

    /**
     * <PRE>
     * 車両情報リクエストの検証を行う。<BR> 危険物、冷蔵ユニット、最大積載量の各項目について必須チェックを実施する。<BR>
     * </PRE>
     *
     * @param vehicleInfoRequestDTO 車両情報リクエストDTO
     * @throws NextWebException 入力チェックエラー時
     */
    private void validateVehicleRequest(VehicleInfoRequestDTO vehicleInfoRequestDTO) {
        validateHazardousMaterial(vehicleInfoRequestDTO);
        validateRefrigerationUnit(vehicleInfoRequestDTO);
        validateMaxCarryingCapacity(vehicleInfoRequestDTO);
    }

    /**
     * 危険物の検証.<BR>
     *
     * @param vehicleInfoRequestDTO 車両情報リクエストDTO
     * @throws NextWebException 入力チェックエラー時
     */
    private void validateHazardousMaterial(VehicleInfoRequestDTO vehicleInfoRequestDTO) {
        if (!BaseUtil.isNull(vehicleInfoRequestDTO.getVehicleInfo().getHazardousMaterialVehicleType())
            && ParamConstant.REQUIRED_VALUE
            .equals(vehicleInfoRequestDTO.getVehicleInfo().getHazardousMaterialVehicleType().trim())) {
            if (BaseUtil.isNull(String.valueOf(vehicleInfoRequestDTO.getMotasInfo().getHazardousMaterialVolume()))) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                    Validate.VALID_HAZARDOUS_MATERIAL_VEHICLE_TYPE_VOLUME);
            }
            if (BaseUtil.isNull(
                String.valueOf(vehicleInfoRequestDTO.getMotasInfo().getHazardousMaterialSpecificGravity()))) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                    Validate.VALID_HAZARDOUS_MATERIAL_VEHICLE_TYPE_GRAVITY);
            }
        }
    }

    /**
     * 冷蔵ユニットの検証.<BR>
     *
     * @param vehicleInfoRequestDTO 車両情報リクエストDTO
     * @throws NextWebException 入力チェックエラー時
     */
    private void validateRefrigerationUnit(VehicleInfoRequestDTO vehicleInfoRequestDTO) {
        if (!BaseUtil.isNull(vehicleInfoRequestDTO.getVehicleDetails().getRefrigerationUnit())
            && ParamConstant.REQUIRED_VALUE
            .equals(vehicleInfoRequestDTO.getVehicleDetails().getRefrigerationUnit().trim())) {
            if (BaseUtil.isNull(String.valueOf(vehicleInfoRequestDTO.getVehicleDetails().getTemperatureRangeMin()))) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_REFRIGERATION_UNIT_MIN);
            }
            if (BaseUtil.isNull(String.valueOf(vehicleInfoRequestDTO.getVehicleDetails().getTemperatureRangeMax()))) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_REFRIGERATION_UNIT_MAX);
            }
        }
    }

    /**
     * 最大積載量の検証.<BR>
     *
     * @param vehicleInfoRequestDTO 車両情報リクエストDTO
     * @throws NextWebException 入力チェックエラー時
     */
    private void validateMaxCarryingCapacity(VehicleInfoRequestDTO vehicleInfoRequestDTO) {
        for (MaxCarryingCapacityDTO maxCarryingCapacityDTO : vehicleInfoRequestDTO.getMaxCarryingCapacity()) {
            if (!BaseUtil.isNull(vehicleInfoRequestDTO.getVehicleInfo().getHazardousMaterialVehicleType())
                && ParamConstant.REQUIRED_VALUE
                .equals(vehicleInfoRequestDTO.getVehicleInfo().getHazardousMaterialVehicleType().trim())
                && BaseUtil.isNull(maxCarryingCapacityDTO.getPackageCode())) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                    Validate.VALID_HAZARDOUS_MATERIAL_VEHICLE_TYPE_PACKAGE_CODE);
            }
            if (!BaseUtil.isNull(String.valueOf(maxCarryingCapacityDTO.getMaxLoadQuantity()))
                && BaseUtil.isNull(maxCarryingCapacityDTO.getPackageNameKanji())) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                    Validate.VALID_MAX_LOAD_QUANTITY_PACKAGE_NAME_KANJI);
            }
        }
    }

    /**
     * リクエスト属性を車両情報リクエストDTOに変換
     *
     * @param commonRequestDTO 共通リクエストDTO
     * @return 車両情報リクエストDTO
     * @throws NextWebException 変換エラー時
     */
    private VehicleInfoRequestDTO convertRequestAttribute(CommonRequestDTO commonRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.convertValue(commonRequestDTO.getAttribute(), VehicleInfoRequestDTO.class);
        } catch (Exception e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, System.SYSTEM_ERR_001);
            return null;
        }
    }
}
