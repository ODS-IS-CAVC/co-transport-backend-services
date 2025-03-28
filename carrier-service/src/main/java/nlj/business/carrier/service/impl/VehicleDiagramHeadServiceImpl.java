package nlj.business.carrier.service.impl;

import static nlj.business.carrier.util.BaseUtil.getBigDecimalValue;
import static nlj.business.carrier.util.BaseUtil.getBooleanValue;
import static nlj.business.carrier.util.BaseUtil.getIntegerValue;
import static nlj.business.carrier.util.BaseUtil.getLongValue;
import static nlj.business.carrier.util.BaseUtil.getValueByHeader;
import static nlj.business.carrier.util.BaseUtil.uploadS3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.s3.util.S3HelperUtil;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.MessageConstant;
import nlj.business.carrier.constant.MessageConstant.SemiDymanicInfoMessage;
import nlj.business.carrier.constant.MessageConstant.System;
import nlj.business.carrier.constant.MessageConstant.Validate;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.constant.ParamConstant.RoundTripType;
import nlj.business.carrier.domain.VehicleDiagramHead;
import nlj.business.carrier.domain.VehicleDiagramImport;
import nlj.business.carrier.domain.opt.DayAdjustment;
import nlj.business.carrier.domain.opt.DayTime;
import nlj.business.carrier.dto.vehicleDiagram.request.VehicleDiagramDTO;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.request.VehicleDiagramHeadDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.ResDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.VehicleDiagramHeadResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.VehicleDiagramStatusResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramImport.VehicleDiagramImportDTO;
import nlj.business.carrier.dto.vehicleDiagramImport.VehicleDiagramImportRequest;
import nlj.business.carrier.dto.vehicleDiagramImport.VehicleDiagramImportResponse;
import nlj.business.carrier.mapper.VehicleDiagramHeadMapper;
import nlj.business.carrier.mapper.VehicleDiagramImportMapper;
import nlj.business.carrier.repository.VehicleDiagramHeadRepository;
import nlj.business.carrier.repository.VehicleDiagramImportRepository;
import nlj.business.carrier.repository.VehicleDiagramRepository;
import nlj.business.carrier.service.VehicleDiagramHeadService;
import nlj.business.carrier.service.VehicleDiagramService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 車両ダイアグラムヘッダサービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleDiagramHeadServiceImpl implements VehicleDiagramHeadService {

    @Resource(name = "userContext")
    private final UserContext userContext;

    private final VehicleDiagramHeadRepository vehicleDiagramHeadRepository;
    private final VehicleDiagramHeadMapper vehicleDiagramHeadMapper;
    private final VehicleDiagramService vehicleDiagramService;
    private final VehicleDiagramRepository vehicleDiagramRepository;
    private final VehicleDiagramImportRepository vehicleDiagramImportRepository;
    private final S3HelperUtil s3HelperUtil;
    private final VehicleDiagramImportMapper vehicleDiagramImportMapper;

    @Override
    @Transactional
    public VehicleDiagramStatusResponseDTO registerVehicleDiagramHead(VehicleDiagramHeadDTO vehicleDiagramHeadDTO) {
        User user = userContext.getUser();
        VehicleDiagramStatusResponseDTO vehicleDiagramStatusResponseDTO = new VehicleDiagramStatusResponseDTO();
        if (BaseUtil.isNull(user.getCompanyId())) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.SYSTEM_ERR_001);
        }
        VehicleDiagramHead vehicleDiagramHead = vehicleDiagramHeadMapper.toVehicleDiagramHead(vehicleDiagramHeadDTO);
        vehicleDiagramHead.setOperatorId(user.getCompanyId());
        VehicleDiagramHead savedVehicleDiagramHead = vehicleDiagramHeadRepository.save(vehicleDiagramHead);
        VehicleDiagramDTO vehicleDiagramDTO = vehicleDiagramHeadDTO.getVehicleDiagram();
        List<VehicleDiagramDTO> vehicleDiagramDTOList = new ArrayList<>();
        vehicleDiagramDTOList.add(vehicleDiagramDTO);
        if (vehicleDiagramHeadDTO.getIsRoundTrip()) {
            Gson gson = new Gson();
            VehicleDiagramDTO vehicleDiagramDTO1 = gson.fromJson(gson.toJson(vehicleDiagramDTO),
                VehicleDiagramDTO.class);
            if (vehicleDiagramDTO.getRoundTripType().equals(RoundTripType.OUTBOUND_TRIP)) {
                vehicleDiagramDTO1.setRoundTripType(RoundTripType.INBOUND_TRIP);
            } else {
                vehicleDiagramDTO1.setRoundTripType(RoundTripType.OUTBOUND_TRIP);
            }
            vehicleDiagramDTO1.setDepartureFrom(vehicleDiagramDTO.getArrivalTo());
            vehicleDiagramDTO1.setArrivalTo(vehicleDiagramDTO.getDepartureFrom());
            vehicleDiagramDTOList.add(vehicleDiagramDTO1);
        }
        int checkErrorCode = SemiDymanicInfoMessage.ERROR_CODE_0;
        vehicleDiagramStatusResponseDTO.setStatus(SemiDymanicInfoMessage.STATUS_SUCCESS);
        vehicleDiagramStatusResponseDTO.setMessage(SemiDymanicInfoMessage.MESSAGE_ERROR_0);
        List<Long> vehicleDiagramIds = new ArrayList<>();
        for (VehicleDiagramDTO itemDTO : vehicleDiagramDTOList) {
            itemDTO.setDiagramHeadId(savedVehicleDiagramHead.getId());
            ResDTO resDTO = vehicleDiagramService.addVehicleDiagram(itemDTO, vehicleDiagramHeadDTO);
            vehicleDiagramIds.add(resDTO.getId());
            if (vehicleDiagramHeadDTO.getIsValidate() == null || vehicleDiagramHeadDTO.getIsValidate()) {
                checkErrorCode = resDTO.getCode();
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
        }
        vehicleDiagramStatusResponseDTO.setIds(vehicleDiagramIds);

        if (vehicleDiagramHeadDTO.getIsValidate() == null || vehicleDiagramHeadDTO.getIsValidate()) {
            if (checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_0
                && checkErrorCode != SemiDymanicInfoMessage.ERROR_CODE_1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

        return vehicleDiagramStatusResponseDTO;
    }

    /**
     * 登録済みの車両ダイアグラムヘッダを取得する。
     *
     * @return 車両ダイアグラムヘッダのリスト
     */
    @Override
    public List<VehicleDiagramHeadResponseDTO> getRegisterVehicleDiagramHeads() {
        List<VehicleDiagramHead> vehicleDiagramHeads = vehicleDiagramHeadRepository.findAll();

        return vehicleDiagramHeads.stream()
            .map(head -> {
                VehicleDiagramHeadResponseDTO responseDTO = vehicleDiagramHeadMapper.toVehicleDiagramHeadDTO(head);
                List<VehicleDiagramResponseDTO> vehicleDiagrams = vehicleDiagramService.getVehicleDiagramsByHeadId(
                    head.getId());
                responseDTO.setVehicleDiagrams(vehicleDiagrams);
                return responseDTO;
            })
            .toList();
    }

    /**
     * 指定されたIDの車両ダイアグラムヘッダを取得する。
     *
     * @param id 車両ダイアグラムヘッダID
     * @return 車両ダイアグラムヘッダのDTO
     */
    @Override
    public VehicleDiagramHeadResponseDTO getVehicleDiagramHeadById(Long id) {
        VehicleDiagramHead vehicleDiagramHead = vehicleDiagramHeadRepository.findById(id).orElse(null);
        VehicleDiagramHeadResponseDTO responseDTO = vehicleDiagramHeadMapper.toVehicleDiagramHeadDTO(
            vehicleDiagramHead);

        List<VehicleDiagramResponseDTO> vehicleDiagrams = vehicleDiagramService.getVehicleDiagramsByHeadId(id);

        responseDTO.setVehicleDiagrams(vehicleDiagrams);
        return responseDTO;
    }

    /**
     * データをインポートする。
     *
     * @param file ファイル
     */
    @Override
    @Transactional
    public void importData(MultipartFile file) {
        String fileNameCheckValid = file.getOriginalFilename();
        if (fileNameCheckValid != null && !fileNameCheckValid.endsWith(".csv")) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(Validate.VALID_FILE_IMPORT))
            );
        }
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        VehicleDiagramImportResponse response = readFile(file);
        VehicleDiagramImportDTO vehicleDiagramImportDTO = response.getVehicleDiagramImportDTO();
        if (vehicleDiagramImportDTO.getNumberSuccess() <= 0) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        VehicleDiagramImport vehicleDiagramImport = vehicleDiagramImportMapper.toEntity(vehicleDiagramImportDTO);
        String fileName = uploadS3(Collections.singletonList(file), s3HelperUtil).get(0);
        vehicleDiagramImport.setFilePath(fileName);
        vehicleDiagramImport = vehicleDiagramImportRepository.save(vehicleDiagramImport);
        for (VehicleDiagramHeadDTO dto : response.getVehicleDiagramHeadDTOS()) {
            dto.setImportId(vehicleDiagramImport.getId());
            registerVehicleDiagramHead(dto);
        }
    }

    /**
     * ファイルを読み込む。
     *
     * @param file ファイル
     * @return 車両ダイアグラムインポートレスポンス
     */
    private VehicleDiagramImportResponse readFile(MultipartFile file) {
        VehicleDiagramImportResponse vehicleDiagramImportResponse = new VehicleDiagramImportResponse();

        int errors = 0;
        int success = 0;

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String headerLine = reader.readLine();
            if (headerLine == null) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_DATA);
            }

            String[] headers = headerLine.split(",");
            Map<String, Integer> headerMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                headerMap.put(headers[i].trim().replace(ParamConstant.VehicleInfo.BOM, ""), i);
            }

            validateHeaders(headerMap);

            String line;
            int rowNum = 1;
            List<VehicleDiagramHeadDTO> vehicleDiagramHeadDTOS = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                rowNum++;
                try {
                    VehicleDiagramHeadDTO vehicleDiagramHeadDTO = parseCSVLine(line, headerMap);
                    vehicleDiagramHeadDTOS.add(vehicleDiagramHeadDTO);
                    success++;
                } catch (Exception e) {
                    errors++;
                }
            }
            if (success < 1) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_DATA);
            }
            vehicleDiagramImportResponse.setVehicleDiagramHeadDTOS(vehicleDiagramHeadDTOS);
            VehicleDiagramImportDTO vehicleDiagramImportDTO = new VehicleDiagramImportDTO();
            vehicleDiagramImportDTO.setNumberSuccess(success);
            vehicleDiagramImportDTO.setNumberFailure(errors);
            vehicleDiagramImportResponse.setVehicleDiagramImportDTO(vehicleDiagramImportDTO);
            return vehicleDiagramImportResponse;

        } catch (IOException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_READ);
        }

        return vehicleDiagramImportResponse;
    }

    /**
     * ヘッダーを検証する。
     *
     * @param headerMap ヘッダーマップ
     */
    private void validateHeaders(Map<String, Integer> headerMap) {
        List<String> requiredHeaders = ParamConstant.VehicleDiagramImport.HEADER_DIAGRAM_IMPORT;

        List<String> validHeaders = new ArrayList<>();
        for (Field field : VehicleDiagramImportRequest.class.getDeclaredFields()) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                validHeaders.add(jsonProperty.value());
            }
        }
        for (Field field : VehicleDiagramImportRequest.class.getDeclaredFields()) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                validHeaders.add("item_" + jsonProperty.value());
            }
        }

        List<String> missingRequiredHeaders = requiredHeaders.stream()
            .filter(header -> !headerMap.containsKey(header.trim()))
            .toList();

        if (!missingRequiredHeaders.isEmpty()) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_MISSING_REQUIRED_COLUMNS,
                String.valueOf(missingRequiredHeaders));
        }

        List<String> invalidHeaders = headerMap.keySet().stream()
            .filter(header -> !validHeaders.contains(header))
            .toList();

        if (!invalidHeaders.isEmpty()) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_DATA,
                "Invalid column names: " + invalidHeaders);
        }
    }

    /**
     * CSV行を解析する。
     *
     * @param line      CSV行
     * @param headerMap ヘッダーマップ
     * @return 車両ダイアグラムヘッダDTO
     */
    private VehicleDiagramHeadDTO parseCSVLine(String line, Map<String, Integer> headerMap) {
        VehicleDiagramHeadDTO request = new VehicleDiagramHeadDTO();
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            // Parse vehicle diagram head data
            request.setDepartureFrom(getLongValue(getValueByHeader(fields, headerMap, "departure_from")));
            request.setArrivalTo(getLongValue(getValueByHeader(fields, headerMap, "arrival_to")));
            request.setOneWayTime(getValueByHeader(fields, headerMap, "one_way_time"));
            request.setStartDate(getValueByHeader(fields, headerMap, "start_date"));
            request.setEndDate(getValueByHeader(fields, headerMap, "end_date"));
            request.setRepeatDay(getIntegerValue(getValueByHeader(fields, headerMap, "repeat_day")));
            request.setTrailerNumber(getIntegerValue(getValueByHeader(fields, headerMap, "trailer_number")));
            request.setIsRoundTrip(getBooleanValue(getValueByHeader(fields, headerMap, "is_round_trip")));
            request.setOriginType(getIntegerValue(getValueByHeader(fields, headerMap, "origin_type")));
            request.setStatus(getIntegerValue(getValueByHeader(fields, headerMap, "status")));

            //Parse Vehicle no available data
            VehicleDiagramDTO vehicleDiagramDTO = new VehicleDiagramDTO();
            vehicleDiagramDTO.setRoundTripType(getIntegerValue(getValueByHeader(fields, headerMap, "round_trip_type")));
            vehicleDiagramDTO.setTripName(getValueByHeader(fields, headerMap, "trip_name"));
            vehicleDiagramDTO.setDepartureFrom(
                getLongValue(getValueByHeader(fields, headerMap, "diagram_departure_from")));
            vehicleDiagramDTO.setArrivalTo(getLongValue(getValueByHeader(fields, headerMap, "diagram_arrival_to")));
            vehicleDiagramDTO.setDepartureTime(getValueByHeader(fields, headerMap, "departure_time"));
            vehicleDiagramDTO.setArrivalTime(getValueByHeader(fields, headerMap, "arrival_time"));

            String dayWeekJson = getValueByHeader(fields, headerMap, "day_week");
            String adjustmentPriceJson = getValueByHeader(fields, headerMap, "adjustment_price");
            String cutOffPriceJson = getValueByHeader(fields, headerMap, "cut_off_price");
            Map<String, DayTime> dayWeek = null;
            if (!BaseUtil.isNull(dayWeekJson)) {
                dayWeek = objectMapper.readValue(dayWeekJson.replace("\"\"\"", ""),
                    new TypeReference<Map<String, DayTime>>() {
                    });
            }

            Map<String, DayAdjustment> adjustmentMap = null;
            if (!BaseUtil.isNull(adjustmentPriceJson)) {
                adjustmentMap = objectMapper.readValue(adjustmentPriceJson.replace("\"\"\"", ""),
                    new TypeReference<Map<String, DayAdjustment>>() {
                    });
            }
            Map<String, BigDecimal> cutOffPrice = null;
            if (!BaseUtil.isNull(cutOffPriceJson)) {
                cutOffPrice = objectMapper.readValue(cutOffPriceJson.replace("\"\"\"", ""),
                    new TypeReference<Map<String, BigDecimal>>() {
                    });
            }
            vehicleDiagramDTO.setDayWeek(dayWeek);
            vehicleDiagramDTO.setAdjustmentPrice(adjustmentMap);
            vehicleDiagramDTO.setCutOffPrice(cutOffPrice);
            vehicleDiagramDTO.setCommonPrice(getBigDecimalValue(getValueByHeader(fields, headerMap, "common_price")));
            vehicleDiagramDTO.setStatus(getIntegerValue(getValueByHeader(fields, headerMap, "diagram_status")));

            request.setVehicleDiagram(vehicleDiagramDTO);
            return request;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing data: " + e.getMessage());
        }
    }
} 