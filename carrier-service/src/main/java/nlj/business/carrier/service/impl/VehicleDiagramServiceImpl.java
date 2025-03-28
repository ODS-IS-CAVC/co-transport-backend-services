package nlj.business.carrier.service.impl;

import static com.next.logistic.security.constant.AuthConstant.API_KEY;
import static com.next.logistic.security.constant.AuthConstant.AUTHORIZATION;
import static nlj.business.carrier.constant.MessageConstant.VehicleInfo.ORDER_STATUS_NULL_AND_TOTAL_COUNT_GREATER_0;
import static nlj.business.carrier.constant.MessageConstant.VehicleInfo.TEMPERATURE_RANGE_DRY;
import static nlj.business.carrier.constant.MessageConstant.VehicleInfo.TRAILER;
import static nlj.business.carrier.mapper.DateTimeMapper.localDateTimeToString;
import static nlj.business.carrier.mapper.DateTimeMapper.stringToLocalTime;
import static nlj.business.carrier.util.StringUtil.convertStringToList;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.data.util.ValidateUtil;
import com.next.logistic.security.config.NljAuthProperties;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.constant.MessageConstant;
import nlj.business.carrier.constant.MessageConstant.SemiDymanicInfoMessage;
import nlj.business.carrier.constant.MessageConstant.System;
import nlj.business.carrier.constant.MessageConstant.VehicleDiagramMessage;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramAllocation;
import nlj.business.carrier.domain.VehicleDiagramHead;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import nlj.business.carrier.domain.VehicleInfo;
import nlj.business.carrier.domain.opt.DayTime;
import nlj.business.carrier.dto.vehicleDiagram.request.CarrierVehicleDiagramGetRequest;
import nlj.business.carrier.dto.vehicleDiagram.request.VehicleDiagramDTO;
import nlj.business.carrier.dto.vehicleDiagram.response.CarrierServiceTransMatchingResponse;
import nlj.business.carrier.dto.vehicleDiagram.response.CarrierVehicleDiagramGetResponse;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramListResponse;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.request.VehicleDiagramHeadDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.ResDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.VehicleDiagramStatusResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.request.VehicleDiagramItemDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemByAbilityDetailsDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemByAbilityResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.DayDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramITemTrailerResDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerDTO;
import nlj.business.carrier.mapper.DateTimeMapper;
import nlj.business.carrier.mapper.VehicleDiagramItemMapper;
import nlj.business.carrier.mapper.VehicleDiagramItemTrailerMapper;
import nlj.business.carrier.mapper.VehicleDiagramMapper;
import nlj.business.carrier.mapper.VehicleInfoMapper;
import nlj.business.carrier.repository.TransMatchingCustomRepository;
import nlj.business.carrier.repository.VehicleDiagramAllocationRepository;
import nlj.business.carrier.repository.VehicleDiagramHeadRepository;
import nlj.business.carrier.repository.VehicleDiagramItemCustomRepository;
import nlj.business.carrier.repository.VehicleDiagramItemRepository;
import nlj.business.carrier.repository.VehicleDiagramItemTrailerRepository;
import nlj.business.carrier.repository.VehicleDiagramRepository;
import nlj.business.carrier.repository.VehicleInfoRepository;
import nlj.business.carrier.service.DataTransferYamatoService;
import nlj.business.carrier.service.VehicleDiagramItemService;
import nlj.business.carrier.service.VehicleDiagramService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

/**
 * <PRE>
 * 車両情報サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleDiagramServiceImpl implements VehicleDiagramService {

    @Resource(name = "userContext")
    private final UserContext userContext;

    private final VehicleDiagramRepository vehicleDiagramRepository;
    private final VehicleDiagramMapper vehicleDiagramMapper;
    private final VehicleDiagramItemMapper vehicleDiagramItemMapper;
    private final VehicleDiagramItemService vehicleDiagramItemService;
    private final ValidateUtil validateUtil;
    private final VehicleDiagramAllocationRepository vehicleDiagramAllocationRepository;
    private final VehicleInfoRepository vehicleInfoRepository;
    private final VehicleDiagramItemTrailerRepository vehicleDiagramItemTrailerRepository;
    private final VehicleDiagramItemRepository vehicleDiagramItemRepository;
    private final VehicleInfoMapper vehicleInfoMapper;
    private final VehicleDiagramItemTrailerMapper vehicleDiagramItemTrailerMapper;
    private final VehicleDiagramItemCustomRepository vehicleDiagramItemCustomRepository;
    private final NljUrlProperties nljUrlProperties;
    private final NljAuthProperties authProperties;
    private final VehicleDiagramHeadRepository vehicleDiagramHeadRepository;
    private final TransMatchingCustomRepository transMatchingCustomRepository;
    private final EntityManager entityManager;
    private final DataTransferYamatoService dataTransferYamatoService;

    /**
     * ユーザーによるフィルタを作成する。
     *
     * @param userCompanyId ユーザーの会社ID
     * @return フィルタのSpecification
     */
    public static Specification<VehicleDiagram> filterByUser(String userCompanyId) {
        return (Root<VehicleDiagram> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate userIdPredicate = cb.equal(root.get("operatorId"), userCompanyId);
            return userIdPredicate;
        };
    }

    /**
     * 車両ダイアグラムを追加する。
     *
     * @param vehicleDiagramDTO     車両ダイアグラムのDTO
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッダーのDTO
     * @return 追加結果
     */
    @Override
    public ResDTO addVehicleDiagram(VehicleDiagramDTO vehicleDiagramDTO,
        VehicleDiagramHeadDTO vehicleDiagramHeadDTO) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.SYSTEM_ERR_001);
        }
        VehicleDiagram saveVehicleDiagram = vehicleDiagramMapper.toVehicleDiagram(vehicleDiagramDTO);
        saveVehicleDiagram.setOperatorId(user.getCompanyId());
        VehicleDiagram savedVehicleDiagram = vehicleDiagramRepository.save(saveVehicleDiagram);

        // Generate vehicle diagram item
        return generateVehicleDiagram(user, vehicleDiagramHeadDTO, savedVehicleDiagram);
    }

    /**
     * 車両ダイアグラムを生成する。
     *
     * @param user                  車両ダイアグラムを生成するユーザー
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッダーのDTO
     * @param saveVehicleDiagram    保存する車両ダイアグラム
     * @return 生成結果
     */
    private ResDTO generateVehicleDiagram(User user, VehicleDiagramHeadDTO vehicleDiagramHeadDTO,
        VehicleDiagram saveVehicleDiagram) {
        ResDTO resDTO = new ResDTO();
        VehicleDiagramDTO vehicleDiagramDTO = vehicleDiagramHeadDTO.getVehicleDiagram();
        List<VehicleDiagramItemDTO> vehicleDiagramItemsDTO = new ArrayList<>();
        Map<String, DayTime> dayWeek = vehicleDiagramDTO.getDayWeek();
        LocalDate startDate = stringToLocalDate(vehicleDiagramHeadDTO.getStartDate());
        LocalDate endDate = stringToLocalDate(vehicleDiagramHeadDTO.getEndDate());
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        int errorCode = SemiDymanicInfoMessage.ERROR_CODE_0;
        int errorCodeTmp = SemiDymanicInfoMessage.ERROR_CODE_0;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int dayOfWeek = date.getDayOfWeek().getValue();
            if (dayWeek.containsKey(String.valueOf(dayOfWeek))) {
                DayTime dayTime = dayWeek.get(String.valueOf(dayOfWeek));
                VehicleDiagramItemDTO item = new VehicleDiagramItemDTO();
                item.setOperatorId(user.getCompanyId());
                item.setVehicleDiagramId(saveVehicleDiagram.getId());
                item.setDay(date.format(outputFormatter));
                item.setTripName(vehicleDiagramDTO.getTripName());
                if (dayTime != null) {
                    item.setDepartureTime(String.valueOf(dayTime.getFromTime()));
                    item.setArrivalTime(String.valueOf(dayTime.getToTime()));
                } else {
                    item.setDepartureTime(vehicleDiagramDTO.getDepartureTime());
                    item.setArrivalTime(vehicleDiagramDTO.getArrivalTime());
                }
                item.setPrice(vehicleDiagramDTO.getCommonPrice());
                vehicleDiagramItemsDTO.add(item);
                int checkErrorCode = SemiDymanicInfoMessage.ERROR_CODE_0;
                if (vehicleDiagramHeadDTO.getIsValidate() == null || vehicleDiagramHeadDTO.getIsValidate()) {
                    LocalDate maxDate = LocalDate.now().plusDays(15);
                    if (DateTimeMapper.stringToLocalDate(item.getDay()).isBefore(maxDate)) {
                        checkErrorCode = validateUtil.validateCommon(DateTimeMapper.stringToLocalDate(item.getDay()),
                            DateTimeMapper.stringToLocalTime(item.getDepartureTime()),
                            DateTimeMapper.stringToLocalTime(item.getArrivalTime()));
                    }
                    if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0) {
                        switch (checkErrorCode) {
                            case SemiDymanicInfoMessage.ERROR_CODE_1:
                                errorCode = SemiDymanicInfoMessage.ERROR_CODE_1;
                                break;
                            case SemiDymanicInfoMessage.ERROR_CODE_2:
                                errorCodeTmp = SemiDymanicInfoMessage.ERROR_CODE_2;
                                break;
                            case SemiDymanicInfoMessage.ERROR_CODE_3:
                                errorCodeTmp = SemiDymanicInfoMessage.ERROR_CODE_3;
                                break;
                            case SemiDymanicInfoMessage.ERROR_CODE_4:
                                errorCodeTmp = SemiDymanicInfoMessage.ERROR_CODE_4;
                                break;
                            case SemiDymanicInfoMessage.ERROR_CODE_5:
                                errorCodeTmp = SemiDymanicInfoMessage.ERROR_CODE_5;
                                break;
                            default:
                                resDTO.setCode(SemiDymanicInfoMessage.ERROR_CODE_0);
                                break;
                        }
                    }
                }
            }
        }
        vehicleDiagramItemService.registerVehicleDiagramItems(saveVehicleDiagram, vehicleDiagramItemsDTO);
        if (errorCodeTmp != SemiDymanicInfoMessage.ERROR_CODE_0) {
            resDTO.setCode(errorCodeTmp);
        } else {
            resDTO.setCode(errorCode);
        }
        resDTO.setId(saveVehicleDiagram.getId());
        return resDTO;
    }

    /**
     * 車両ダイアグラムを更新する。
     *
     * @param user                  車両ダイアグラムを更新するユーザー
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッダーのDTO
     * @param saveVehicleDiagram    保存する車両ダイアグラム
     * @param vehicleDiagramHead    車両ダイアグラムヘッダー
     * @return 更新結果
     */
    private ResDTO generateVehicleDiagramUpdate(User user, VehicleDiagramHeadDTO vehicleDiagramHeadDTO,
        VehicleDiagram saveVehicleDiagram, VehicleDiagramHead vehicleDiagramHead) {
        ResDTO resDTO = new ResDTO();
        VehicleDiagramDTO vehicleDiagramDTO = vehicleDiagramHeadDTO.getVehicleDiagram();
        Map<String, DayTime> dayWeek = vehicleDiagramDTO.getDayWeek();
        LocalDate startDate = stringToLocalDate(vehicleDiagramHeadDTO.getStartDate());
        LocalDate endDate = stringToLocalDate(vehicleDiagramHeadDTO.getEndDate());
        LocalDate startDateOld = vehicleDiagramHead.getStartDate();
        LocalDate endDateOld = vehicleDiagramHead.getEndDate();
        resDTO.setCode(SemiDymanicInfoMessage.ERROR_CODE_0);
        List<LocalDate> dateOlds = new ArrayList<>();
        List<LocalDate> dateNews = new ArrayList<>();
        for (LocalDate dateOld = startDateOld; !dateOld.isAfter(endDateOld); dateOld = dateOld.plusDays(1)) {
            dateOlds.add(dateOld);
        }
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dateNews.add(date);
            int dayOfWeek = date.getDayOfWeek().getValue();
            if (dateOlds.contains(date)) { // date exist about startDateOld to endDateOld -> update item
                VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.
                    findVehicleDiagramItemByVehicleDiagramAndDay(saveVehicleDiagram, date);

                if (vehicleDiagramItem != null) {
                    getVehicleDiagramItem(saveVehicleDiagram, vehicleDiagramItem, date, dayWeek, dayOfWeek, user,
                        vehicleDiagramDTO);
                    int checkErrorCode = SemiDymanicInfoMessage.ERROR_CODE_0;
                    if (vehicleDiagramHeadDTO.getIsValidate() == null || vehicleDiagramHeadDTO.getIsValidate()) {
                        LocalDate maxDate = LocalDate.now().plusDays(15);
                        if (vehicleDiagramItem.getDay().isBefore(maxDate)) {
                            checkErrorCode = validateUtil.validateCommon(vehicleDiagramItem.getDay(),
                                vehicleDiagramItem.getDepartureTime(),
                                vehicleDiagramItem.getArrivalTime());
                        }
                    }
                    resDTO = handleErrorCode(checkErrorCode, resDTO);
                    vehicleDiagramItemService.updateVehicleDiagramItem(saveVehicleDiagram, vehicleDiagramItem);
                    List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = vehicleDiagramItemTrailerRepository.
                        findVehicleDiagramItemTrailersByVehicleDiagramItem(vehicleDiagramItem);
                    if (!vehicleDiagramItemTrailers.isEmpty()) {
                        vehicleDiagramItemTrailers.forEach(vehicleDiagramItemTrailer -> {
                            vehicleDiagramItemTrailer.setDepartureTime(vehicleDiagramItem.getDepartureTime());
                            vehicleDiagramItemTrailer.setArrivalTime(vehicleDiagramItem.getArrivalTime());
                            vehicleDiagramItemTrailer.setTripName(saveVehicleDiagram.getTripName());
                            vehicleDiagramItemTrailerRepository.save(vehicleDiagramItemTrailer);
                        });
                    }
                }
            } else { // date not exist about startDateOld to endDateOld -> add item
                VehicleDiagramItemDTO vehicleDiagramItemDTO = getVehicleDiagramItemDTO(saveVehicleDiagram, date,
                    dayWeek, dayOfWeek, user, vehicleDiagramDTO);
                int checkErrorCode = SemiDymanicInfoMessage.ERROR_CODE_0;
                if (vehicleDiagramHeadDTO.getIsValidate() == null || vehicleDiagramHeadDTO.getIsValidate()) {
                    LocalDate maxDate = LocalDate.now().plusDays(15);
                    if (DateTimeMapper.stringToLocalDate(vehicleDiagramItemDTO.getDay()).isBefore(maxDate)) {
                        checkErrorCode = validateUtil.validateCommon(
                            DateTimeMapper.stringToLocalDate(vehicleDiagramItemDTO.getDay()),
                            DateTimeMapper.stringToLocalTime(vehicleDiagramItemDTO.getDepartureTime()),
                            DateTimeMapper.stringToLocalTime(vehicleDiagramItemDTO.getArrivalTime()));
                    }
                }
                resDTO = handleErrorCode(checkErrorCode, resDTO);
                if (vehicleDiagramItemDTO != null) {
                    vehicleDiagramItemService.addVehicleDiagramItem(saveVehicleDiagram, vehicleDiagramItemDTO);
                }
            }
        }
        // remove date in dateOlds when date not in dateNews
        List<LocalDate> removedDates = dateOlds.stream()
            .filter(date -> !dateNews.contains(date))
            .toList();
        removedDates.forEach(removedDate -> {
            List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailersOld = vehicleDiagramItemTrailerRepository.
                findVehicleDiagramItemTrailersByVehicleDiagramAndDay(saveVehicleDiagram, removedDate);
            // check vehicle_diagram_item_trailer in trans_order
            if (!vehicleDiagramItemTrailersOld.isEmpty()) {
                vehicleDiagramItemTrailersOld.forEach(vehicleDiagramItemTrailer -> {
                    String sql = "SELECT id FROM t_trans_order WHERE vehicle_diagram_item_trailer_id = :trailerId";

                    List<Object[]> results = entityManager.createNativeQuery(sql)
                        .setParameter("trailerId", vehicleDiagramItemTrailer.getId())
                        .getResultList();

                    if (!results.isEmpty()) {
                        NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                            MessageConstant.System.VEHICLE_DIAGRAM_ITEM_TRAILER_HAVE_IN_TRANS_ORDER);
                    }
                });
            }
            String sqlUpdateVehicleDiagramItemTrailer = "DELETE FROM c_vehicle_diagram_item_trailer "
                + "WHERE vehicle_diagram_id = :vehicle_diagram_id AND day = :day "
                + "RETURNING id";
            List<Long> deletedIds = entityManager.createNativeQuery(sqlUpdateVehicleDiagramItemTrailer)
                .setParameter("vehicle_diagram_id", saveVehicleDiagram.getId())
                .setParameter("day", removedDate)
                .getResultList();
            deletedIds.forEach(vehicleDiagramTrailerId -> {
                String sqlUpdateVehicleAvbResourceItem = "DELETE FROM vehicle_avb_resource_item WHERE "
                    + "vehicle_diagram_item_trailer_id = :vehicle_diagram_item_trailer_id";
                entityManager.createNativeQuery(sqlUpdateVehicleAvbResourceItem)
                    .setParameter("vehicle_diagram_item_trailer_id", vehicleDiagramTrailerId)
                    .executeUpdate();
                String sqlUpdateTransMatching = "DELETE FROM t_trans_matching WHERE "
                    + "vehicle_diagram_item_trailer_id = :vehicle_diagram_item_trailer_id";
                entityManager.createNativeQuery(sqlUpdateTransMatching)
                    .setParameter("vehicle_diagram_item_trailer_id", vehicleDiagramTrailerId)
                    .executeUpdate();
            });
            String sqlUpdateVehicleDiagramItem = "DELETE FROM c_vehicle_diagram_item WHERE "
                + "vehicle_diagram_id = :vehicle_diagram_id AND day = :day";
            entityManager.createNativeQuery(sqlUpdateVehicleDiagramItem)
                .setParameter("vehicle_diagram_id", saveVehicleDiagram.getId())
                .setParameter("day", removedDate)
                .executeUpdate();
        });
        resDTO.setId(saveVehicleDiagram.getId());
        return resDTO;
    }

    /**
     * エラーコードを処理する。
     *
     * @param checkErrorCode チェックしたエラーコード
     * @param resDTO         結果DTO
     * @return 更新された結果DTO
     */
    private ResDTO handleErrorCode(int checkErrorCode, ResDTO resDTO) {
        if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0
            && checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_1) {
            resDTO.setCode(checkErrorCode);
        } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_1
            && resDTO.getCode() == SemiDymanicInfoMessage.ERROR_CODE_0) {
            resDTO.setCode(checkErrorCode);
        }
        return resDTO;
    }

    /**
     * 車両ダイアグラムアイテムを取得する。
     *
     * @param vehicleDiagram     車両ダイアグラム
     * @param vehicleDiagramItem 車両ダイアグラムアイテム
     * @param date               日付
     * @param dayWeek            曜日マップ
     * @param dayOfWeek          曜日
     * @param user               ユーザー
     * @param vehicleDiagramDTO  車両ダイアグラムのDTO
     * @return 更新された車両ダイアグラムアイテム
     */
    private VehicleDiagramItem getVehicleDiagramItem(VehicleDiagram vehicleDiagram,
        VehicleDiagramItem vehicleDiagramItem,
        LocalDate date, Map<String, DayTime> dayWeek, int dayOfWeek, User user, VehicleDiagramDTO vehicleDiagramDTO) {
        if (dayWeek.containsKey(String.valueOf(dayOfWeek))) {
            DayTime dayTime = dayWeek.get(String.valueOf(dayOfWeek));
            vehicleDiagramItem.setOperatorId(user.getCompanyId());
            vehicleDiagramItem.setVehicleDiagram(vehicleDiagram);
            vehicleDiagramItem.setDay(date);
            vehicleDiagramItem.setTripName(vehicleDiagramDTO.getTripName());
            if (dayTime != null) {
                vehicleDiagramItem.setDepartureTime(stringToLocalTime(dayTime.getFromTime()));
                vehicleDiagramItem.setArrivalTime(stringToLocalTime(dayTime.getToTime()));
            } else {
                vehicleDiagramItem.setDepartureTime(stringToLocalTime(vehicleDiagramDTO.getDepartureTime()));
                vehicleDiagramItem.setArrivalTime(stringToLocalTime(vehicleDiagramDTO.getArrivalTime()));
            }
        }
        return vehicleDiagramItem;
    }

    /**
     * 車両ダイアグラムアイテムDTOを取得する。
     *
     * @param vehicleDiagram    車両ダイアグラム
     * @param date              日付
     * @param dayWeek           曜日マップ
     * @param dayOfWeek         曜日
     * @param user              ユーザー
     * @param vehicleDiagramDTO 車両ダイアグラムのDTO
     * @return 車両ダイアグラムアイテムDTO
     */
    private VehicleDiagramItemDTO getVehicleDiagramItemDTO(VehicleDiagram vehicleDiagram,
        LocalDate date, Map<String, DayTime> dayWeek, int dayOfWeek, User user, VehicleDiagramDTO vehicleDiagramDTO) {
        VehicleDiagramItemDTO item = new VehicleDiagramItemDTO();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (dayWeek.containsKey(String.valueOf(dayOfWeek))) {
            DayTime dayTime = dayWeek.get(String.valueOf(dayOfWeek));
            item.setOperatorId(user.getCompanyId());
            item.setVehicleDiagramId(vehicleDiagram.getId());
            item.setDay(date.format(outputFormatter));
            item.setTripName(vehicleDiagramDTO.getTripName());
            if (dayTime != null) {
                item.setDepartureTime(String.valueOf(dayTime.getFromTime()));
                item.setArrivalTime(String.valueOf(dayTime.getToTime()));
            } else {
                item.setDepartureTime(vehicleDiagramDTO.getDepartureTime());
                item.setArrivalTime(vehicleDiagramDTO.getArrivalTime());
            }
            item.setPrice(vehicleDiagramDTO.getCommonPrice());
            return item;
        }
        return null;
    }

    /**
     * 車両ダイアグラムを更新する。
     *
     * @param vehicleDiagramId      車両ダイアグラムID
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッダーのDTO
     * @return 更新結果
     */
    @Override
    @Transactional
    public VehicleDiagramStatusResponseDTO updateVehicleDiagram(Long vehicleDiagramId,
        VehicleDiagramHeadDTO vehicleDiagramHeadDTO) {
        User user = userContext.getUser();
        VehicleDiagramStatusResponseDTO vehicleDiagramStatusResponseDTO = new VehicleDiagramStatusResponseDTO();
        if (BaseUtil.isNull(user.getCompanyId())) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.SYSTEM_ERR_001);
        }
        VehicleDiagram vehicleDiagram = vehicleDiagramRepository.findVehicleDiagramById(vehicleDiagramId);
        if (vehicleDiagram == null) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, VehicleDiagramMessage.VEHICLE_DIAGRAM_NOT_FOUND);
        }

        LocalTime departureTimeBefore = vehicleDiagram.getDepartureTime();
        LocalTime arrivalTimeBefore = vehicleDiagram.getArrivalTime();

        checkVehicleDiagramItemTrailerInTransOrder(vehicleDiagram, vehicleDiagramHeadDTO);

        VehicleDiagramDTO vehicleDiagramDTO = vehicleDiagramHeadDTO.getVehicleDiagram();
        VehicleDiagram saveVehicleDiagram = vehicleDiagramMapper.toVehicleDiagram(vehicleDiagramDTO);
        vehicleDiagram.setAdjustmentPrice(saveVehicleDiagram.getAdjustmentPrice());
        vehicleDiagram.setTripName(saveVehicleDiagram.getTripName());
        vehicleDiagram.setDepartureTime(saveVehicleDiagram.getDepartureTime());
        vehicleDiagram.setArrivalTime(saveVehicleDiagram.getArrivalTime());
        vehicleDiagram.setArrivalTo(saveVehicleDiagram.getArrivalTo());
        if (saveVehicleDiagram.getCutOffPrice() != null) {
            vehicleDiagram.setCutOffPrice(saveVehicleDiagram.getCutOffPrice());
        }
        vehicleDiagram.setCommonPrice(saveVehicleDiagram.getCommonPrice());
        vehicleDiagram.setDayWeek(saveVehicleDiagram.getDayWeek());
        vehicleDiagram.setDepartureFrom(saveVehicleDiagram.getDepartureFrom());
        vehicleDiagram.setRoundTripType(saveVehicleDiagram.getRoundTripType());
        vehicleDiagram.setStatus(saveVehicleDiagram.getStatus());
        VehicleDiagram updatedVehicleDiagram = vehicleDiagramRepository.save(vehicleDiagram);

        VehicleDiagramHead vehicleDiagramHead = vehicleDiagramHeadRepository.findVehicleDiagramHeadById(vehicleDiagram
            .getVehicleDiagramHead().getId());

        boolean isChangeStartDate = !vehicleDiagramHead.getStartDate().isEqual(
            Objects.requireNonNull(stringToLocalDate(vehicleDiagramHeadDTO.getStartDate())));
        boolean isChangeEndDate = !vehicleDiagramHead.getEndDate().isEqual(
            Objects.requireNonNull(stringToLocalDate(vehicleDiagramHeadDTO.getEndDate())));
        boolean isChangeDayWeeks = isDayWeeksKeyChanged(vehicleDiagram.getDayWeek(),
            vehicleDiagramHeadDTO.getVehicleDiagram().getDayWeek());

        vehicleDiagramStatusResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
        vehicleDiagramStatusResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_0);
        ResDTO resDTO = new ResDTO();
        if (isChangeStartDate || isChangeEndDate || isChangeDayWeeks) {
            // change startDate or endDate or dayweeks -> generate and update VehicleDiagramItem
            resDTO = generateVehicleDiagramUpdate(user, vehicleDiagramHeadDTO, updatedVehicleDiagram,
                vehicleDiagramHead);
        } else {
            // change others info -> update VehicleDiagramItem
            resDTO = updateData(vehicleDiagramHead, updatedVehicleDiagram, vehicleDiagramHeadDTO, departureTimeBefore,
                arrivalTimeBefore);
        }
        if (vehicleDiagramHeadDTO.getIsValidate() == null || vehicleDiagramHeadDTO.getIsValidate()) {
            int checkErrorCode = resDTO.getCode();
            if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_1 && vehicleDiagramStatusResponseDTO.getStatus()
                .equals(SemiDymanicInfoMessage.STATUS_SUCCESS)) {
                vehicleDiagramStatusResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
                vehicleDiagramStatusResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_1);
            } else if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0) {
                vehicleDiagramStatusResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_ERROR);
                if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_2) {
                    vehicleDiagramStatusResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_2);
                } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_3) {
                    vehicleDiagramStatusResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_3);
                } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_4) {
                    vehicleDiagramStatusResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_4);
                } else if (checkErrorCode == SemiDymanicInfoMessage.ERROR_CODE_5) {
                    vehicleDiagramStatusResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_5);
                }
            }
        }
        vehicleDiagramHead.setStartDate(stringToLocalDate(vehicleDiagramHeadDTO.getStartDate()));
        vehicleDiagramHead.setEndDate(stringToLocalDate(vehicleDiagramHeadDTO.getEndDate()));
        vehicleDiagramHeadRepository.save(vehicleDiagramHead);
        if (vehicleDiagramHeadDTO.getIsValidate() == null || vehicleDiagramHeadDTO.getIsValidate()) {
            if (vehicleDiagramStatusResponseDTO.getStatus().equals(SemiDymanicInfoMessage.STATUS_ERROR)) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return vehicleDiagramStatusResponseDTO;
    }

    /**
     * データを更新する。
     *
     * @param vehicleDiagramHead    車両ダイアグラムヘッダー
     * @param vehicleDiagram        車両ダイアグラム
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッダーのDTO
     * @param departureTimeBefore   出発時間（更新前）
     * @param arrivalTimeBefore     到着時間（更新前）
     * @return 更新結果
     */
    private ResDTO updateData(VehicleDiagramHead vehicleDiagramHead, VehicleDiagram vehicleDiagram,
        VehicleDiagramHeadDTO vehicleDiagramHeadDTO, LocalTime departureTimeBefore, LocalTime arrivalTimeBefore) {
        List<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemRepository.findAllByVehicleDiagram(
            vehicleDiagram);
        if (vehicleDiagramItems.isEmpty()) {
            return null;
        }
        boolean isDepartureTimeChange = !Objects.equals(vehicleDiagram.getDepartureTime(), departureTimeBefore);
        boolean isArriveTimeChange = !Objects.equals(vehicleDiagram.getArrivalTime(), arrivalTimeBefore);
        ResDTO resDTO = new ResDTO();
        resDTO.setCode(SemiDymanicInfoMessage.ERROR_CODE_0);
        for (VehicleDiagramItem vehicleDiagramItem : vehicleDiagramItems) {
            vehicleDiagramItem.setDepartureFrom(vehicleDiagram.getDepartureFrom());
            vehicleDiagramItem.setArrivalTo(vehicleDiagram.getArrivalTo());
            vehicleDiagramItem.setDepartureTime(vehicleDiagram.getDepartureTime());
            vehicleDiagramItem.setArrivalTime(vehicleDiagram.getArrivalTime());
            vehicleDiagramItem.setTripName(vehicleDiagram.getTripName());
            vehicleDiagramItem.setOneWayTime(stringToLocalTime(vehicleDiagramHead.getOneWayTime()));
            vehicleDiagramItemRepository.save(vehicleDiagramItem);
            List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = vehicleDiagramItemTrailerRepository.
                findVehicleDiagramItemTrailersByVehicleDiagramItem(vehicleDiagramItem);
            if (!vehicleDiagramItemTrailers.isEmpty()) {
                vehicleDiagramItemTrailers.forEach(vehicleDiagramItemTrailer -> {
                    vehicleDiagramItemTrailer.setDepartureTime(vehicleDiagramItem.getDepartureTime());
                    vehicleDiagramItemTrailer.setArrivalTime(vehicleDiagramItem.getArrivalTime());
                    vehicleDiagramItemTrailer.setTripName(vehicleDiagram.getTripName());
                    vehicleDiagramItemTrailerRepository.save(vehicleDiagramItemTrailer);
                });
                dataTransferYamatoService.transferDataDateTime(vehicleDiagramItem);
            }

            // if time change -> validate weather and call mobility hub
            int checkErrorCode = SemiDymanicInfoMessage.ERROR_CODE_0;
            boolean isValidateWeather =
                (vehicleDiagramHeadDTO.getIsValidate() == null || vehicleDiagramHeadDTO.getIsValidate()) &&
                    (isDepartureTimeChange || isArriveTimeChange) &&
                    (vehicleDiagramItem.getStatus() == null || vehicleDiagramItem.getStatus() == 0
                        || vehicleDiagramItem.getStatus() == 1);
            if (isValidateWeather) {
                LocalDate maxDate = LocalDate.now().plusDays(15);
                if (DateTimeMapper.stringToLocalDate(DateTimeMapper.dateToString(vehicleDiagramItem.getDay()))
                    .isBefore(maxDate)) {
                    if (vehicleDiagramItem.getDay().isBefore(maxDate)) {
                        checkErrorCode = validateUtil.validateCommon(vehicleDiagramItem.getDay(),
                            vehicleDiagramItem.getDepartureTime(),
                            vehicleDiagramItem.getArrivalTime());
                    }
                }
            }
            resDTO = handleErrorCode(checkErrorCode, resDTO);
            if (isDepartureTimeChange || isArriveTimeChange) {
                VehicleDiagramItemByAbilityDetailsDTO vehicleDiagramItemByAbilityDetailsDTO = new VehicleDiagramItemByAbilityDetailsDTO();
                vehicleDiagramItemByAbilityDetailsDTO.setDepartureTime(
                    vehicleDiagramHeadDTO.getVehicleDiagram().getDepartureTime());
                vehicleDiagramItemByAbilityDetailsDTO.setArrivalTime(
                    vehicleDiagramHeadDTO.getVehicleDiagram().getArrivalTime());
                vehicleDiagramItemService.callMobilityHub(vehicleDiagramItem, vehicleDiagramItemByAbilityDetailsDTO);
            }
        }
        return resDTO;
    }

    /**
     * カットオフ価格が変更されたかどうかを確認する。
     *
     * @param oldCutOffPrice 旧カットオフ価格
     * @param newCutOffPrice 新カットオフ価格
     * @return 変更された場合はtrue、そうでなければfalse
     */
    private boolean isCutOffPriceChanged(Map<String, BigDecimal> oldCutOffPrice,
        Map<String, BigDecimal> newCutOffPrice) {
        if (oldCutOffPrice == null && newCutOffPrice == null) {
            return false;
        }
        if (oldCutOffPrice == null || newCutOffPrice == null) {
            return true;
        }
        if (oldCutOffPrice.size() != newCutOffPrice.size()) {
            return true;
        }
        for (Map.Entry<String, BigDecimal> entry : oldCutOffPrice.entrySet()) {
            String key = entry.getKey();
            BigDecimal oldValue = entry.getValue();
            BigDecimal newValue = newCutOffPrice.get(key);
            if (newValue == null || !oldValue.equals(newValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 車両ダイアグラムアイテムトレーラーがトランスオーダーに存在するかを確認する。
     *
     * @param vehicleDiagram        車両ダイアグラム
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッダーのDTO
     */
    private void checkVehicleDiagramItemTrailerInTransOrder(VehicleDiagram vehicleDiagram,
        VehicleDiagramHeadDTO vehicleDiagramHeadDTO) {
        List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = vehicleDiagramItemTrailerRepository.
            findVehicleDiagramItemTrailersByVehicleDiagram(vehicleDiagram);
        VehicleDiagramHead vehicleDiagramHead = vehicleDiagramHeadRepository.findVehicleDiagramHeadById(
            vehicleDiagram.getVehicleDiagramHead().getId());

        if (!vehicleDiagramHead.getStartDate().equals(stringToLocalDate(vehicleDiagramHeadDTO.getStartDate()))
            || !vehicleDiagramHead.getEndDate().equals(stringToLocalDate(vehicleDiagramHeadDTO.getEndDate()))
            || isCutOffPriceChanged(vehicleDiagram.getCutOffPrice(),
            vehicleDiagramHeadDTO.getVehicleDiagram().getCutOffPrice())) {
            vehicleDiagramItemTrailers.forEach(vehicleDiagramItemTrailer -> {
                String sql = "SELECT id FROM t_trans_order WHERE vehicle_diagram_item_trailer_id = :trailerId";

                List<Object[]> results = entityManager.createNativeQuery(sql)
                    .setParameter("trailerId", vehicleDiagramItemTrailer.getId())
                    .getResultList();

                if (!results.isEmpty()) {
                    NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                        MessageConstant.System.VEHICLE_DIAGRAM_ITEM_TRAILER_HAVE_IN_TRANS_ORDER);
                }
            });
        }
    }

    /**
     * 登録された車両ダイアグラムを取得する。
     *
     * @return 車両ダイアグラムのリスト
     */
    @Override
    public List<VehicleDiagramResponseDTO> getRegisterVehicleDiagrams() {
        List<VehicleDiagram> vehicleDiagrams = vehicleDiagramRepository.findAll();
        List<VehicleDiagramResponseDTO> result = new ArrayList<>();

        for (VehicleDiagram diagram : vehicleDiagrams) {
            VehicleDiagramResponseDTO dto = vehicleDiagramMapper.toVehicleDiagramDTO(diagram);
            result.add(dto);
        }

        return result;
    }

    /**
     * ヘッダーIDによる車両ダイアグラムを取得する。
     *
     * @param headId ヘッダーID
     * @return 車両ダイアグラムのリスト
     */
    @Override
    public List<VehicleDiagramResponseDTO> getVehicleDiagramsByHeadId(Long headId) {
        List<VehicleDiagram> vehicleDiagrams = vehicleDiagramRepository.findVehicleDiagramByVehicleDiagramHeadId(
            headId);
        List<VehicleDiagramResponseDTO> result = new ArrayList<>();

        for (VehicleDiagram diagram : vehicleDiagrams) {
            VehicleDiagramResponseDTO dto = vehicleDiagramMapper.toVehicleDiagramDTO(diagram);
            List<VehicleDiagramItemResponseDTO> items = vehicleDiagramItemService.getItemsByDiagramId(diagram.getId());
            dto.setVehicleDiagramItems(items);
            result.add(dto);
        }

        return result;
    }

    /**
     * ヘッダーIDによる車両ダイアグラムを削除する。
     *
     * @param headId ヘッダーID
     */
    @Override
    @Transactional
    public void deleteVehicleDiagramsByHeadId(Long headId) {
        try {
            List<Long> diagramIds = vehicleDiagramRepository.findVehicleDiagramByVehicleDiagramHeadId(headId)
                .stream()
                .map(VehicleDiagram::getId)
                .toList();

            if (!diagramIds.isEmpty()) {
                vehicleDiagramRepository.deleteById(headId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete vehicle diagrams", e);
        }
    }

    /**
     * 車両ダイアグラムをIDで取得する。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @param haveTrailer      トレーラーの有無
     * @return 車両ダイアグラムのDTO
     */
    @Override
    public VehicleDiagramResponseDTO getVehicleDiagramById(Long vehicleDiagramId, boolean haveTrailer) {
        VehicleDiagram vehicleDiagram = vehicleDiagramRepository.findVehicleDiagramById(vehicleDiagramId);
        if (vehicleDiagram == null) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, System.NOT_FOUND, "vehicle_diagram");
        }
        VehicleDiagramHead vehicleDiagramHead = vehicleDiagramHeadRepository.
            findVehicleDiagramHeadById(vehicleDiagram.getVehicleDiagramHead().getId());
        List<VehicleDiagramItemResponseDTO> vehicleDiagramItemResponseDTOs = new ArrayList<>();
        VehicleDiagramResponseDTO vehicleDiagramResponseDTO = vehicleDiagramMapper.toVehicleDiagramDTO(vehicleDiagram);
        vehicleDiagramResponseDTO.setOneWayTime(vehicleDiagramHead.getOneWayTime());
        vehicleDiagramResponseDTO.setCreatedDate(
            vehicleDiagram.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        vehicleDiagramResponseDTO.setStartDate(vehicleDiagram.getVehicleDiagramHead().getStartDate().
            format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        vehicleDiagramResponseDTO.setEndDate(vehicleDiagram.getVehicleDiagramHead().getEndDate().
            format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        if (haveTrailer) {
            if (!vehicleDiagram.getVehicleDiagramItems().isEmpty()) {
                vehicleDiagramItemResponseDTOs.addAll(
                    vehicleDiagram.getVehicleDiagramItems().stream()
                        .map(vehicleDiagramItemMapper::toVehicleDiagramItemDTO)
                        .toList()
                );
            }
            vehicleDiagramResponseDTO.setVehicleDiagramItems(vehicleDiagramItemResponseDTOs);
            List<VehicleDiagramAllocation> vehicleDiagramAllocations = vehicleDiagramAllocationRepository
                .findAllByVehicleDiagramId(vehicleDiagramId);
            if (!vehicleDiagramAllocations.isEmpty()) {
                List<VehicleDiagramAllocationResDTO> vehicleDiagramAllocationResDTOs = new ArrayList<>();
                vehicleDiagramAllocations.forEach(vehicleDiagramAllocation -> {
                    VehicleDiagramAllocationResDTO vehicleDiagramAllocationResDTO = new VehicleDiagramAllocationResDTO();
                    Optional<VehicleInfo> vehicleInfoOptional = vehicleInfoRepository.findById(
                        vehicleDiagramAllocation.getVehicleInfoId());
                    if (!vehicleInfoOptional.isEmpty()) {
                        vehicleDiagramAllocationResDTO.setVehicleInfoResponseDTO(
                            vehicleInfoMapper.toResponseDto(vehicleInfoOptional.get()));
                    }
                    vehicleDiagramAllocationResDTO.setId(vehicleDiagramAllocation.getId());
                    vehicleDiagramAllocationResDTO.setVehicleType(vehicleDiagramAllocation.getVehicleType());
                    vehicleDiagramAllocationResDTO.setDisplayOrder(vehicleDiagramAllocation.getDisplayOrder());
                    vehicleDiagramAllocationResDTO.setAssignType(vehicleDiagramAllocation.getAssignType());
                    vehicleDiagramAllocationResDTOs.add(vehicleDiagramAllocationResDTO);
                });
                vehicleDiagramResponseDTO.setVehicleDiagramAllocations(vehicleDiagramAllocationResDTOs);
            }
            List<VehicleDiagramITemTrailerResDTO> vehicleDiagramITemTrailerResDTOs = new ArrayList<>();

            List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = vehicleDiagramItemTrailerRepository
                .findVehicleDiagramItemTrailerByVehicleDiagram(vehicleDiagram);
            if (!vehicleDiagramItemTrailers.isEmpty()) {
                // Group items by vehicleDiagramAllocationId
                Map<Long, List<VehicleDiagramItemTrailer>> groupedByAllocationId = vehicleDiagramItemTrailers.stream()
                    .collect(Collectors.groupingBy(item -> item.getVehicleDiagramAllocation().getId()));

                groupedByAllocationId.forEach((allocationId, items) -> {
                    VehicleDiagramITemTrailerResDTO vehicleDiagramITemTrailerResDTO = new VehicleDiagramITemTrailerResDTO();
                    vehicleDiagramITemTrailerResDTO.setVehicleDiagramAllocationId(allocationId);

                    // Map all items with same allocationId to trailerDetails
                    List<DayDTO> days = items.stream()
                        .map(item -> {
                            DayDTO detail = new DayDTO();
                            if (item.getDay() != null) {
                                detail.setDay(item.getDay().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                            }
                            detail.setVehicleDiagramItemTrailerId(item.getId());
                            detail.setPrice(item.getPrice());
                            detail.setStatus(item.getStatus());
                            detail.setVehicleDiagramItemId(item.getVehicleDiagramItem().getId());
                            detail.setCutOffPrice(item.getCutOffPrice());
                            return detail;
                        })
                        .toList();
                    if (items.get(0) != null) {
                        vehicleDiagramITemTrailerResDTO.setFreightRateType(items.get(0).getFreightRateType());
                    }
                    vehicleDiagramITemTrailerResDTO.setDays(days);
                    vehicleDiagramITemTrailerResDTOs.add(vehicleDiagramITemTrailerResDTO);
                });
            }
            vehicleDiagramResponseDTO.setVehicleDiagramItemTrailerResDTOS(vehicleDiagramITemTrailerResDTOs);
        }
        vehicleDiagramResponseDTO.setRepeatDay(vehicleDiagram.getVehicleDiagramHead().getRepeatDay());
        vehicleDiagramResponseDTO.setTrailerNumber(vehicleDiagram.getVehicleDiagramHead().getTrailerNumber());
        vehicleDiagramResponseDTO.setIsRoundTrip(vehicleDiagram.getVehicleDiagramHead().getIsRoundTrip());
        vehicleDiagramResponseDTO.setOriginType(vehicleDiagram.getVehicleDiagramHead().getOriginType());
        vehicleDiagramResponseDTO.setImportId(vehicleDiagram.getVehicleDiagramHead().getImportId());
        return vehicleDiagramResponseDTO;
    }

    /**
     * 車両ダイアグラムのリストを取得する。
     *
     * @param pageable      ページ情報
     * @param haveTrailer   トレーラーの有無
     * @param tripName      トリップ名
     * @param dateFrom      開始日
     * @param dateTo        終了日
     * @param departureFrom 出発地
     * @param arrivalTo     到着地
     * @return 車両ダイアグラムのリスト
     */
    @Override
    public VehicleDiagramListResponse getVehicleDiagrams(Pageable pageable, boolean haveTrailer, String tripName,
        String dateFrom, String dateTo, String departureFrom, String arrivalTo) {
        LocalDate startDate = DateTimeMapper.stringToLocalDate(dateFrom);
        LocalDate endDate = DateTimeMapper.stringToLocalDate(dateTo);
        VehicleDiagramListResponse vehicleDiagramListResponse = new VehicleDiagramListResponse();
        User user = userContext.getUser();
        Specification<VehicleDiagram> filters = filterByUser(user.getCompanyId())
            .and(filterByTripName(tripName))
            .and(filterByDateRange(startDate, endDate))
            .and(filterByDepartureAndArrival(departureFrom, arrivalTo));
        Page<VehicleDiagram> vehicleDiagrams = vehicleDiagramRepository.findAll(filters, pageable);
        if (vehicleDiagrams.isEmpty()) {
            vehicleDiagramListResponse.setCurrentPage(Integer.valueOf(ParamConstant.VehicleInfo.DEFAULT_PAGE_NO));
            vehicleDiagramListResponse.setTotalItem(0L);
            vehicleDiagramListResponse.setTotalPage(0);
            vehicleDiagramListResponse.setItemPerPage(Integer.valueOf(ParamConstant.VehicleInfo.LIMIT_DEFAULT));
            return vehicleDiagramListResponse;
        }
        List<VehicleDiagramResponseDTO> vehicleDiagramResponseDTOList = vehicleDiagrams.stream()
            .map(item -> getVehicleDiagramById(item.getId(), haveTrailer))
            .toList();
        long totalItem = vehicleDiagrams.getTotalElements();
        int itemPerPage = pageable.getPageSize();
        int currentPage = pageable.getPageNumber() + 1;
        int totalPage = vehicleDiagrams.getTotalPages();
        vehicleDiagramListResponse.setTotalItem(totalItem);
        vehicleDiagramListResponse.setItemPerPage(itemPerPage);
        vehicleDiagramListResponse.setCurrentPage(currentPage);
        vehicleDiagramListResponse.setTotalPage(totalPage);
        vehicleDiagramListResponse.setDataList(vehicleDiagramResponseDTOList);
        return vehicleDiagramListResponse;
    }

    /**
     * 能力による車両ダイアグラムを取得する。
     *
     * @param abilityId          能力ID
     * @param startDate          開始日
     * @param endDate            終了日
     * @param tripName           トリップ名
     * @param statusList         ステータスリスト
     * @param pageable           ページ情報
     * @param httpServletRequest HTTPリクエスト
     * @return 車両ダイアグラムアイテムのレスポンスDTO
     */
    @Override
    public VehicleDiagramItemByAbilityResponseDTO getVehicleDiagramByAbility(Long abilityId, String startDate,
        String endDate, String tripName, String statusList, Integer temperatureRange, Pageable pageable,
        HttpServletRequest httpServletRequest) {
        VehicleDiagramItemByAbilityResponseDTO responseDTO = new VehicleDiagramItemByAbilityResponseDTO();
        User user = userContext.getUser();
        Page<VehicleDiagramItem> vehicleDiagramItems = vehicleDiagramItemCustomRepository.searchAllItemByDiagramId(
            abilityId,
            user.getCompanyId(), stringToLocalDate(startDate), stringToLocalDate(endDate), tripName,
            convertStringToList(statusList), pageable);

        // Check if the page is empty and set default values
        if (vehicleDiagramItems.isEmpty()) {
            setDefaultResponseValues(responseDTO);
            return responseDTO;
        }

        List<VehicleDiagramItemByAbilityDetailsDTO> itemList = vehicleDiagramItems.stream()
            .map(item -> getVehicleDiagramItemByAbilityDetailsDTO(item.getId(), httpServletRequest))
            .toList();

        // Set response values
        setResponseValues(responseDTO, vehicleDiagramItems, itemList);
        if (temperatureRange != null && temperatureRange == TEMPERATURE_RANGE_DRY && !responseDTO.getDataList()
            .isEmpty()) {
            List<VehicleDiagramItemByAbilityDetailsDTO> filteredList = responseDTO.getDataList().stream()
                .filter(item -> {
                    List<VehicleDiagramAllocationResDTO> allocationTrailer = 
                        Optional.ofNullable(item.getVehicleDiagramAllocations())
                                .orElse(Collections.emptyList())
                                .stream()
                                .filter(alloc -> alloc.getVehicleInfoResponseDTO().getVehicleType().equals(TRAILER))
                                .collect(Collectors.toList());
                    return allocationTrailer.stream().allMatch(allocation ->
                        !allocation.getVehicleInfoResponseDTO().getTemperatureRange().isEmpty()
                        && allocation.getVehicleInfoResponseDTO().getTemperatureRange().get(0) == TEMPERATURE_RANGE_DRY
                        && allocation.getVehicleInfoResponseDTO().getTemperatureRange().size() == 1
                    );
                }).collect(Collectors.toList());
            responseDTO.setDataList(filteredList);
            responseDTO.setTotalItem((long) filteredList.size());
            responseDTO.setTotalPage((int) Math.ceil((double) filteredList.size() / responseDTO.getItemPerPage()));
            responseDTO.setCurrentPage(1);
        }
        if (statusList != null && !statusList.isEmpty() && !responseDTO.getDataList()
            .isEmpty()) {
            List<VehicleDiagramItemByAbilityDetailsDTO> filteredList = responseDTO.getDataList().stream()
                .filter(item -> {
                    List<VehicleDiagramItemTrailerDTO> vehicleDiagramItemTrailerDTOS = Optional
                        .ofNullable(item.getVehicleDiagramItemTrailers())
                        .orElse(Collections.emptyList());
                    boolean hasValidTrailer = vehicleDiagramItemTrailerDTOS.stream().anyMatch(trailer ->
                        convertStringToList(statusList).contains(trailer.getOrderStatus())
                    );
                    if (convertStringToList(statusList).contains(ORDER_STATUS_NULL_AND_TOTAL_COUNT_GREATER_0)) {
                        boolean hasSpecialCondition = vehicleDiagramItemTrailerDTOS.stream().anyMatch(trailer ->
                            trailer.getOrderStatus() == null && trailer.getTotalCount() > 0
                        );
                        return hasValidTrailer || hasSpecialCondition;
                    }
                    return hasValidTrailer;
                }).collect(Collectors.toList());
            responseDTO.setDataList(filteredList);
            responseDTO.setTotalItem((long) filteredList.size());
            responseDTO.setTotalPage((int) Math.ceil((double) filteredList.size() / responseDTO.getItemPerPage()));
            responseDTO.setCurrentPage(1);
        }
        return responseDTO;
    }

    /**
     * デフォルトのレスポンス値を設定する。
     *
     * @param responseDTO レスポンスDTO
     */
    private void setDefaultResponseValues(VehicleDiagramItemByAbilityResponseDTO responseDTO) {
        responseDTO.setCurrentPage(Integer.valueOf(ParamConstant.VehicleInfo.DEFAULT_PAGE_NO));
        responseDTO.setTotalItem(0L);
        responseDTO.setTotalPage(0);
        responseDTO.setItemPerPage(Integer.valueOf(ParamConstant.VehicleInfo.LIMIT_DEFAULT));
    }

    /**
     * レスポンス値を設定する。
     *
     * @param responseDTO         レスポンスDTO
     * @param vehicleDiagramItems 車両ダイアグラムアイテムのページ
     * @param itemList            アイテムリスト
     */
    private void setResponseValues(VehicleDiagramItemByAbilityResponseDTO responseDTO,
        Page<VehicleDiagramItem> vehicleDiagramItems, List<VehicleDiagramItemByAbilityDetailsDTO> itemList) {
        responseDTO.setTotalItem(vehicleDiagramItems.getTotalElements());
        responseDTO.setItemPerPage(vehicleDiagramItems.getSize());
        responseDTO.setCurrentPage(vehicleDiagramItems.getNumber() + 1);
        responseDTO.setTotalPage(vehicleDiagramItems.getTotalPages());
        responseDTO.setDataList(itemList);
    }

    /**
     * 能力による車両ダイアグラムアイテムの詳細DTOを取得する。
     *
     * @param vehicleDiagramItemId 車両ダイアグラムアイテムID
     * @param httpServletRequest   HTTPリクエスト
     * @return 車両ダイアグラムアイテムの詳細DTO
     */
    private VehicleDiagramItemByAbilityDetailsDTO getVehicleDiagramItemByAbilityDetailsDTO(Long vehicleDiagramItemId,
        HttpServletRequest httpServletRequest) {
        String urlTransaction = nljUrlProperties.getDomainTransaction() + "/api/v1/vehicle_trailer_matching";
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemService.findById(vehicleDiagramItemId);
        if (vehicleDiagramItem == null) {
            return new VehicleDiagramItemByAbilityDetailsDTO();
        }
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        List<VehicleDiagramItemTrailer> trailers = vehicleDiagramItemTrailerRepository.findAllByVehicleDiagramItemId(
            vehicleDiagramItemId);
        List<Long> trailerIds = trailers.stream().map(VehicleDiagramItemTrailer::getId).toList();
        RestTemplate restTemplate = new RestTemplate();
        CarrierVehicleDiagramGetRequest request = new CarrierVehicleDiagramGetRequest();
        request.setTrailerIds(trailerIds);
        HttpHeaders headers = setHeader(token);
        HttpEntity<CarrierVehicleDiagramGetRequest> requestEntity = new HttpEntity<>(request, headers);
        List<VehicleDiagramItemTrailerDTO> trailerDTOList = new ArrayList<>();

        List<CarrierServiceTransMatchingResponse> carrierServiceTransMatchingResponses = transMatchingCustomRepository.findAllByVehicleDiagramItemTrailerIdIn(
            trailerIds);
        for (VehicleDiagramItemTrailer trailer : trailers) {
            VehicleDiagramItemTrailerDTO dto = vehicleDiagramItemTrailerMapper.toVehicleDiagramItemTrailerDto(trailer);
            for (CarrierServiceTransMatchingResponse responseItem : carrierServiceTransMatchingResponses) {
                if (dto.getId().equals(responseItem.getVehicleDiagramItemTrailerId())) {
                    dto.setTotalCount(responseItem.getTotalCount());
                    dto.setMatchingStatus(responseItem.getMatchingStatus());
                    dto.setOrderStatus(responseItem.getOrderStatus());
                }
            }
            vehicleDiagramItemService.setOrderStatusAndTotalCountForTrailer(trailer, dto);
            trailerDTOList.add(dto);
        }

        // Collect all keys from cutOffPrice maps
        Set<String> allKeys = new HashSet<>();
        for (VehicleDiagramItemTrailer trailer : trailers) {
            Map<String, BigDecimal> cutOffPrice = trailer.getCutOffPrice();
            if (cutOffPrice != null) {
                allKeys.addAll(cutOffPrice.keySet());
            }
        }

        // Find min and max keys
        String minKey = allKeys.stream().min(String::compareTo).orElse("0");
        String maxKey = allKeys.stream().max(String::compareTo).orElse("0");

        // Convert keys to hours
        int minHours = Integer.parseInt(minKey);
        int maxHours = Integer.parseInt(maxKey);

        // Get departure and arrival times
        LocalTime departureTime = vehicleDiagramItem.getDepartureTime();

        // Calculate start and end times
        LocalTime start = departureTime.minusHours(maxHours);

        // Format the times
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // Adjust pattern as needed
        String formattedStart = start.format(timeFormatter);

        // Set cutoffTime in the desired format
        VehicleDiagramItemByAbilityDetailsDTO detailsDTO = vehicleDiagramItemMapper.toVehicleDiagramItemByAbilityDetailsDto(
            vehicleDiagramItem);
        setVehicleDiagramAllocations(detailsDTO, vehicleDiagramItem);
        setCommonDetails(detailsDTO, vehicleDiagramItem, trailerDTOList);
        if (minHours == 0 && maxHours == 0) {
            detailsDTO.setCutoffTime(null);
        } else {
            detailsDTO.setCutoffTime(formattedStart + " - " + departureTime);
        }
        return detailsDTO;
    }

    /**
     * HTTPヘッダーを設定する。
     *
     * @param token 認証トークン
     * @return 設定されたHTTPヘッダー
     */
    private HttpHeaders setHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY, authProperties.getApiKey());
        headers.set(AUTHORIZATION, token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 車両ダイアグラムの割り当てを設定する。
     *
     * @param detailsDTO         車両ダイアグラムアイテムの詳細DTO
     * @param vehicleDiagramItem 車両ダイアグラムアイテム
     */
    private void setVehicleDiagramAllocations(VehicleDiagramItemByAbilityDetailsDTO detailsDTO,
        VehicleDiagramItem vehicleDiagramItem) {
        List<VehicleDiagramAllocation> allocations = vehicleDiagramAllocationRepository.findAllByVehicleDiagramId(
            vehicleDiagramItem.getVehicleDiagram().getId());
        if (!allocations.isEmpty()) {
            List<VehicleDiagramAllocationResDTO> allocationDTOs = allocations.stream()
                .map(this::mapToAllocationResDTO)
                .toList();
            detailsDTO.setVehicleDiagramAllocations(allocationDTOs);
        }
    }

    /**
     * 割り当てをレスポンスDTOにマッピングする。
     *
     * @param allocation 割り当て
     * @return 割り当てのレスポンスDTO
     */
    private VehicleDiagramAllocationResDTO mapToAllocationResDTO(VehicleDiagramAllocation allocation) {
        VehicleDiagramAllocationResDTO allocationResDTO = new VehicleDiagramAllocationResDTO();
        Optional<VehicleInfo> vehicleInfoOptional = vehicleInfoRepository.findById(allocation.getVehicleInfoId());
        vehicleInfoOptional.ifPresent(
            vehicleInfo -> allocationResDTO.setVehicleInfoResponseDTO(vehicleInfoMapper.toResponseDto(vehicleInfo)));
        allocationResDTO.setVehicleType(allocation.getVehicleType());
        allocationResDTO.setId(allocation.getId());
        allocationResDTO.setDisplayOrder(allocation.getDisplayOrder());
        allocationResDTO.setAssignType(allocation.getAssignType());
        return allocationResDTO;
    }

    /**
     * 共通の詳細を設定する。
     *
     * @param detailsDTO         車両ダイアグラムアイテムの詳細DTO
     * @param vehicleDiagramItem 車両ダイアグラムアイテム
     * @param trailerDTOList     トレーラーDTOリスト
     */
    private void setCommonDetails(VehicleDiagramItemByAbilityDetailsDTO detailsDTO,
        VehicleDiagramItem vehicleDiagramItem, List<VehicleDiagramItemTrailerDTO> trailerDTOList) {
        VehicleDiagram vehicleDiagram = vehicleDiagramItem.getVehicleDiagram();
        detailsDTO.setStartDate(
            vehicleDiagram.getVehicleDiagramHead().getStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        detailsDTO.setEndDate(
            vehicleDiagram.getVehicleDiagramHead().getEndDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        detailsDTO.setRepeatDay(vehicleDiagram.getVehicleDiagramHead().getRepeatDay());
        detailsDTO.setTrailerNumber(vehicleDiagram.getVehicleDiagramHead().getTrailerNumber());
        detailsDTO.setIsRoundTrip(vehicleDiagram.getVehicleDiagramHead().getIsRoundTrip());
        detailsDTO.setOriginType(vehicleDiagram.getVehicleDiagramHead().getOriginType());
        detailsDTO.setImportId(vehicleDiagram.getVehicleDiagramHead().getImportId());
        detailsDTO.setCreatedDate(localDateTimeToString(vehicleDiagramItem.getCreatedDate()));
        detailsDTO.setVehicleDiagramItemTrailers(trailerDTOList);
    }

    /**
     * 文字列を日付に変換する。
     *
     * @param date 日付の文字列
     * @return 変換された日付
     */
    private LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return !BaseUtil.isNull(date) ? LocalDate.parse(date, formatter) : null;
    }

    /**
     * トリップ名によるフィルタを作成する。
     *
     * @param tripName トリップ名
     * @return フィルタのSpecification
     */
    private Specification<VehicleDiagram> filterByTripName(String tripName) {
        return (Root<VehicleDiagram> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (tripName == null || tripName.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(root.get("tripName"), "%" + tripName + "%");
        };
    }

    /**
     * 日付範囲によるフィルタを作成する。
     *
     * @param startDate 開始日
     * @param endDate   終了日
     * @return フィルタのSpecification
     */
    private Specification<VehicleDiagram> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        return (Root<VehicleDiagram> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (startDate == null && endDate == null) {
                return cb.conjunction();
            }
            Join<VehicleDiagram, VehicleDiagramHead> headJoin = root.join("vehicleDiagramHead");
            if (startDate != null && endDate != null) {
                return cb.between(headJoin.get("startDate"), startDate, endDate);
            } else if (startDate != null) {
                return cb.greaterThanOrEqualTo(headJoin.get("startDate"), startDate);
            } else {
                return cb.lessThanOrEqualTo(headJoin.get("endDate"), endDate);
            }
        };
    }

    /**
     * 出発地と到着地によるフィルタを作成する。
     *
     * @param departureFrom 出発地
     * @param arrivalTo     到着地
     * @return フィルタのSpecification
     */
    private Specification<VehicleDiagram> filterByDepartureAndArrival(String departureFrom, String arrivalTo) {
        return (Root<VehicleDiagram> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (departureFrom != null) {
                predicate = cb.and(predicate, cb.equal(root.get("departureFrom"), Long.parseLong(departureFrom)));
            }
            if (arrivalTo != null) {
                predicate = cb.and(predicate, cb.equal(root.get("arrivalTo"), Long.parseLong(arrivalTo)));
            }

            return predicate;
        };
    }

    /**
     * 曜日キーが変更されたかどうかを確認する。
     *
     * @param oldDayWeek 旧曜日マップ
     * @param newDayWeek 新曜日マップ
     * @return 変更された場合はtrue、そうでなければfalse
     */
    private boolean isDayWeeksKeyChanged(Map<String, DayTime> oldDayWeek, Map<String, DayTime> newDayWeek) {
        if (oldDayWeek == null && newDayWeek == null) {
            return false;
        }
        if (oldDayWeek == null || newDayWeek == null) {
            return true;
        }
        return !oldDayWeek.keySet().equals(newDayWeek.keySet());
    }
}
