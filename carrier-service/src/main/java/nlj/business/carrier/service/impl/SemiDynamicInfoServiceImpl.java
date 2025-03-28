package nlj.business.carrier.service.impl;

import com.next.logistic.data.util.CreateTrackBySipUtil;
import com.next.logistic.data.util.ValidateUtil;
import com.next.logistic.mail.util.NextMailUtil;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.constant.MessageConstant.SemiDymanicInfoMessage;
import nlj.business.carrier.constant.MessageConstant.Validate;
import nlj.business.carrier.constant.MessageConstant.VehicleDiagramMessage;
import nlj.business.carrier.domain.CarrierOperator;
import nlj.business.carrier.domain.LocationMaster;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.domain.VehicleDiagramItemOperationTracking;
import nlj.business.carrier.domain.VehicleDiagramItemTracking;
import nlj.business.carrier.dto.semiDynamicInfo.request.SemiDynamicInfoRequestDTO;
import nlj.business.carrier.dto.semiDynamicInfo.request.ValidateSemiDynamicInfoRequestDTO;
import nlj.business.carrier.dto.semiDynamicInfo.response.SemiDynamicInfoResponseDTO;
import nlj.business.carrier.dto.semiDynamicInfo.response.ValidateSemiDynamicInfoResponseDTO;
import nlj.business.carrier.mapper.VehicleDiagramItemOperationTrackingMapper;
import nlj.business.carrier.repository.CarrierRepository;
import nlj.business.carrier.repository.LocationMasterRepository;
import nlj.business.carrier.repository.VehicleDiagramItemOperationTrackingRepository;
import nlj.business.carrier.repository.VehicleDiagramItemRepository;
import nlj.business.carrier.repository.VehicleDiagramItemTrackingRepository;
import nlj.business.carrier.service.SemiDynamicInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * セミダイナミック情報サービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SemiDynamicInfoServiceImpl implements SemiDynamicInfoService {

    private final ValidateUtil validateUtil;
    private final CreateTrackBySipUtil createTrackBySipUtil;
    private final VehicleDiagramItemTrackingRepository vehicleDiagramItemTrackingRepository;
    private final VehicleDiagramItemOperationTrackingRepository vehicleDiagramItemOperationTrackingRepository;
    private final VehicleDiagramItemOperationTrackingMapper vehicleDiagramItemOperationTrackingMapper;
    private final VehicleDiagramItemRepository vehicleDiagramItemRepository;
    private final LocationMasterRepository locationMasterRepository;
    private final NextMailUtil nextMailUtil;
    private final CarrierRepository carrierRepository;

    /**
     * 現在のセミダイナミック情報取得
     *
     * @param semiDynamicInfoRequestDTO
     * @return セミダイナミック情報
     */
    @Override
    @Transactional
    public SemiDynamicInfoResponseDTO getCurrentSemiDynamicInfo(SemiDynamicInfoRequestDTO semiDynamicInfoRequestDTO) {
        SemiDynamicInfoResponseDTO semiDynamicInfoResponseDTO = new SemiDynamicInfoResponseDTO();
        Long vehicleDiagramItemId = null;
        try {
            vehicleDiagramItemId = Long.parseLong(semiDynamicInfoRequestDTO.getVehicleDiagramItemId());
        } catch (NumberFormatException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_NUMBER_FORMAT,
                "vehicle_diagram_item_id");
        }
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(
            vehicleDiagramItemId);
        if (vehicleDiagramItem == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                VehicleDiagramMessage.VEHICLE_DIAGRAM_ITEM_NOT_FOUND);
        }
        if (BaseUtil.isNull(semiDynamicInfoRequestDTO.getStatus()) || (
            !semiDynamicInfoRequestDTO.getStatus().equals(SemiDymanicInfoMessage.START_STATUS)
                && !semiDynamicInfoRequestDTO.getStatus().equals(SemiDymanicInfoMessage.END_STATUS))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                VehicleDiagramMessage.STATUS_NOT_VALID);
        }
        LocationMaster locationMaster;
        if (semiDynamicInfoRequestDTO.getStatus().equals(SemiDymanicInfoMessage.START_STATUS)) {
            locationMaster = locationMasterRepository.findLocationMasterByCode(
                String.valueOf(vehicleDiagramItem.getDepartureFrom()));
        } else {
            locationMaster = locationMasterRepository.findLocationMasterByCode(
                String.valueOf(vehicleDiagramItem.getArrivalTo()));
        }
        if (locationMaster == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                VehicleDiagramMessage.LOCATION_NOT_FOUND);
        }
        LocalDate now = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        insertStartEndVehicleDiagramItemTracking(vehicleDiagramItemId, now, nowTime,
            semiDynamicInfoRequestDTO.getStatus(), locationMaster.getName());
        int checkErrorCode = SemiDymanicInfoMessage.ERROR_CODE_0;
        if (semiDynamicInfoRequestDTO.getStatus().equals(SemiDymanicInfoMessage.START_STATUS)) {
            nowTime = LocalTime.now();
            checkErrorCode = validateUtil.validateCommon(now, nowTime, nowTime);
            if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_0) {
                createTrackBySipUtil.createTrackBySip(false);
            } else {
                CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(
                    vehicleDiagramItem.getOperatorId());
                if (!BaseUtil.isNull(carrierOperator.getEmail())) {
                    nextMailUtil.sendMail(carrierOperator.getEmail(),
                        "Vehicle item: " + vehicleDiagramItem.getId() + "-" + validateUtil.getErrorMessage(
                            checkErrorCode),
                        "", "mail-template");
                }
                insertVehicleDiagramItemTracking(vehicleDiagramItemId, now, nowTime, checkErrorCode);
            }
        }
        semiDynamicInfoResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
        semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_1);
        if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_1) {
            semiDynamicInfoResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
            semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_1);
        } else if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0) {
            semiDynamicInfoResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_ERROR);
            if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_2) {
                semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_2);
            } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_3) {
                semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_3);
            } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_4) {
                semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_4);
            } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_5) {
                semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_5);
            }
        }
        return semiDynamicInfoResponseDTO;
    }

    /**
     * 開始前のセミダイナミック情報取得
     *
     * @param semiDynamicInfoRequestDTO
     * @return セミダイナミック情報
     */
    @Override
    @Transactional
    public SemiDynamicInfoResponseDTO getBeforeStartSemiDynamicInfo(
        SemiDynamicInfoRequestDTO semiDynamicInfoRequestDTO) {
        SemiDynamicInfoResponseDTO semiDynamicInfoResponseDTO = new SemiDynamicInfoResponseDTO();
        Long vehicleDiagramItemId = null;
        try {
            vehicleDiagramItemId = Long.parseLong(semiDynamicInfoRequestDTO.getVehicleDiagramItemId());
        } catch (NumberFormatException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_NUMBER_FORMAT,
                "vehicle_diagram_item_id");
        }
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(
            vehicleDiagramItemId);
        if (vehicleDiagramItem == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                VehicleDiagramMessage.VEHICLE_DIAGRAM_ITEM_NOT_FOUND);
        }
        LocalDate now = LocalDate.now();
        LocalTime nowTime = LocalTime.now().minusHours(1);
        int checkErrorCode = validateUtil.validateCommon(now, nowTime, nowTime);
        if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_0) {
            createTrackBySipUtil.createTrackBySip(true);
        } else {
            CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(
                vehicleDiagramItem.getOperatorId());
            if (!BaseUtil.isNull(carrierOperator.getEmail())) {
                nextMailUtil.sendMail(carrierOperator.getEmail(),
                    "Vehicle item: " + vehicleDiagramItem.getId() + "-" + validateUtil.getErrorMessage(checkErrorCode),
                    "", "mail-template");
            }
            insertVehicleDiagramItemTracking(vehicleDiagramItemId, now, nowTime, checkErrorCode);
        }

        semiDynamicInfoResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
        semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_1);
        if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_1) {
            semiDynamicInfoResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
            semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_1);
        } else if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0) {
            semiDynamicInfoResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_ERROR);
            if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_2) {
                semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_2);
            } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_3) {
                semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_3);
            } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_4) {
                semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_4);
            } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_5) {
                semiDynamicInfoResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_5);
            }
        }

        return semiDynamicInfoResponseDTO;
    }

    /**
     * 車両ダイアグラムアイテムトラッキングを挿入する
     *
     * @param vehicleDiagramItemId
     * @param now
     * @param nowTime
     * @param checkErrorCode
     */
    private void insertVehicleDiagramItemTracking(Long vehicleDiagramItemId, LocalDate now, LocalTime nowTime,
        int checkErrorCode) {
        VehicleDiagramItemTracking vehicleDiagramItemTracking = new VehicleDiagramItemTracking();
        vehicleDiagramItemTracking.setVehicleDiagramItemId(vehicleDiagramItemId);
        vehicleDiagramItemTracking.setOperationDate(now);
        vehicleDiagramItemTracking.setOperationTime(nowTime);
        vehicleDiagramItemTracking.setLabel(SemiDymanicInfoMessage.LABEL_DELAY);
        vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_1);
        vehicleDiagramItemTracking.setMessage(validateUtil.getErrorMessage(checkErrorCode));
        vehicleDiagramItemTrackingRepository.save(vehicleDiagramItemTracking);

        try {
            VehicleDiagramItemOperationTracking vehicleDiagramItemOperationTracking = vehicleDiagramItemOperationTrackingMapper.toVehicleDiagramItemOperationTracking(
                vehicleDiagramItemTracking);
            vehicleDiagramItemOperationTrackingRepository.save(vehicleDiagramItemOperationTracking);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * セミダイナミック情報を検証する。
     *
     * @param validateSemiDynamicInfoRequestDTO セミダイナミック情報検証リクエストDTO
     * @return セミダイナミック情報検証レスポンスDTO
     */
    @Override
    public ValidateSemiDynamicInfoResponseDTO validateSemiDynamicInfo(
        ValidateSemiDynamicInfoRequestDTO validateSemiDynamicInfoRequestDTO) {
        ValidateSemiDynamicInfoResponseDTO validateSemiDynamicInfoResponseDTO = new ValidateSemiDynamicInfoResponseDTO();
        Long vehicleDiagramItemId = null;
        try {
            vehicleDiagramItemId = Long.parseLong(validateSemiDynamicInfoRequestDTO.getVehicleDiagramItemId());
        } catch (NumberFormatException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_NUMBER_FORMAT,
                "vehicle_diagram_item_id");
        }
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(
            vehicleDiagramItemId);
        if (vehicleDiagramItem == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                VehicleDiagramMessage.VEHICLE_DIAGRAM_ITEM_NOT_FOUND);
        }
        LocalDate now = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        int checkErrorCode = validateUtil.validateCommon(vehicleDiagramItem.getDay(),
            vehicleDiagramItem.getDepartureTime(), vehicleDiagramItem.getArrivalTime());
        if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0
            && checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_1) {
            CarrierOperator carrierOperator = carrierRepository.findCarrierOperatorById(
                vehicleDiagramItem.getOperatorId());
            if (carrierOperator != null && !BaseUtil.isNull(carrierOperator.getEmail())) {
                nextMailUtil.sendMail(carrierOperator.getEmail(),
                    "Vehicle item: " + vehicleDiagramItem.getId() + "-" + validateUtil.getErrorMessage(checkErrorCode),
                    "", "mail-template");
            }
        }
        if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0) {
            VehicleDiagramItemTracking vehicleDiagramItemTracking = new VehicleDiagramItemTracking();
            vehicleDiagramItemTracking.setVehicleDiagramItemId(vehicleDiagramItemId);
            vehicleDiagramItemTracking.setOperationDate(now);
            vehicleDiagramItemTracking.setOperationTime(nowTime);
            vehicleDiagramItemTracking.setLabel(SemiDymanicInfoMessage.LABEL_DELAY);
            vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_1);
            vehicleDiagramItemTracking.setMessage(validateUtil.getErrorMessage(checkErrorCode));
            vehicleDiagramItemTrackingRepository.save(vehicleDiagramItemTracking);

            try {
                VehicleDiagramItemOperationTracking vehicleDiagramItemOperationTracking = vehicleDiagramItemOperationTrackingMapper.toVehicleDiagramItemOperationTracking(
                    vehicleDiagramItemTracking);
                vehicleDiagramItemOperationTrackingRepository.save(vehicleDiagramItemOperationTracking);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        String status = "";
        String message = "";
        if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_0) {
            status = SemiDymanicInfoMessage.STATUS_SUCCESS;
        } else {
            status = SemiDymanicInfoMessage.STATUS_ERROR;
        }
        if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0) {
            message = validateUtil.getErrorMessage(checkErrorCode);
            validateSemiDynamicInfoResponseDTO.setMessage(message);
        }
        validateSemiDynamicInfoResponseDTO.setStatus(status);
        return validateSemiDynamicInfoResponseDTO;
    }

    /**
     * 車両ダイアグラムアイテムトラッキングを挿入する
     *
     * @param vehicleDiagramItemId
     * @param now
     * @param nowTime
     * @param status
     * @param locationName
     */
    private void insertStartEndVehicleDiagramItemTracking(Long vehicleDiagramItemId, LocalDate now, LocalTime nowTime,
        String status, String locationName) {
        VehicleDiagramItemTracking vehicleDiagramItemTracking = new VehicleDiagramItemTracking();
        vehicleDiagramItemTracking.setVehicleDiagramItemId(vehicleDiagramItemId);
        vehicleDiagramItemTracking.setOperationDate(now);
        vehicleDiagramItemTracking.setOperationTime(nowTime);
        vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_0);
        if (status.equals(SemiDymanicInfoMessage.START_STATUS)) {
            vehicleDiagramItemTracking.setMessage(
                validateUtil.getMessageInfo(SemiDymanicInfoMessage.ERROR_CODE_25, locationName));
        } else if (status.equals(SemiDymanicInfoMessage.END_STATUS)) {
            vehicleDiagramItemTracking.setStatus(SemiDymanicInfoMessage.STATUS_CODE_2);
            vehicleDiagramItemTracking.setMessage(
                validateUtil.getErrorMessage(SemiDymanicInfoMessage.ERROR_CODE_26));
        }
        vehicleDiagramItemTrackingRepository.save(vehicleDiagramItemTracking);

        try {
            VehicleDiagramItemOperationTracking vehicleDiagramItemOperationTracking = vehicleDiagramItemOperationTrackingMapper.toVehicleDiagramItemOperationTracking(
                vehicleDiagramItemTracking);
            vehicleDiagramItemOperationTrackingRepository.save(vehicleDiagramItemOperationTracking);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

