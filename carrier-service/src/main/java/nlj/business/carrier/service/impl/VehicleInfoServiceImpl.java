package nlj.business.carrier.service.impl;

import static nlj.business.carrier.util.BaseUtil.getBigDecimalValue;
import static nlj.business.carrier.util.BaseUtil.getIntegerValue;
import static nlj.business.carrier.util.BaseUtil.getValueByHeader;
import static nlj.business.carrier.util.StringUtil.convertStringToList;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.constant.DataBaseConstant.DATE_TIME_FORMAT;
import nlj.business.carrier.constant.MessageConstant;
import nlj.business.carrier.constant.MessageConstant.System;
import nlj.business.carrier.constant.MessageConstant.Validate;
import nlj.business.carrier.constant.ParamConstant;
import nlj.business.carrier.constant.ParamConstant.CarrierDeleteFlag;
import nlj.business.carrier.domain.VehicleDiagramAllocation;
import nlj.business.carrier.domain.VehicleInfo;
import nlj.business.carrier.domain.VehicleNoAvailable;
import nlj.business.carrier.dto.vehicleInfo.VehicleInfoDTO;
import nlj.business.carrier.dto.vehicleInfo.VehicleInfoImportDTO;
import nlj.business.carrier.dto.vehicleInfo.VehicleNoAvailableDTO;
import nlj.business.carrier.dto.vehicleInfo.request.VehicleInfoImportRequest;
import nlj.business.carrier.dto.vehicleInfo.request.VehicleInfoRegisterRequest;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoListResponse;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoResponse;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoResponseDTO;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleNoAvailableResponseDTO;
import nlj.business.carrier.mapper.VehicleInfoMapper;
import nlj.business.carrier.mapper.VehicleNoAvailableMapper;
import nlj.business.carrier.repository.VehicleDiagramAllocationRepository;
import nlj.business.carrier.repository.VehicleInfoRepository;
import nlj.business.carrier.repository.VehicleNoAvailableRepository;
import nlj.business.carrier.service.GiaiValueComponent;
import nlj.business.carrier.service.VehicleInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 車両情報サービスの実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleInfoServiceImpl implements VehicleInfoService {

    private final VehicleInfoRepository vehicleInfoRepository;
    private final VehicleInfoMapper vehicleInfoMapper;
    private final VehicleNoAvailableMapper vehicleNoAvailableMapper;
    private final VehicleNoAvailableRepository vehicleNoAvailableRepository;
    private final VehicleDiagramAllocationRepository vehicleDiagramAllocationRepository;
    private final S3HelperUtil s3HelperUtil;
    private final GiaiValueComponent giaiValueComponent;
    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 車両情報を登録する。
     *
     * @param request 車両情報登録リクエスト
     * @param files   アップロードするファイルのリスト
     * @return 登録結果
     */
    @Override
    @Transactional
    public VehicleInfoResponse register(VehicleInfoRegisterRequest request, List<MultipartFile> files) {
        VehicleInfoResponse response = new VehicleInfoResponse();
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        if (request == null || request.getVehicleInfo() == null) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        VehicleInfoDTO vehicleInfoDTO = request.getVehicleInfo();
        List<VehicleNoAvailableDTO> vehicleNoAvailableDTOS = request.getVehicleNoAvailable();
        VehicleInfo vehicleInfo = new VehicleInfo();
        if (vehicleInfoDTO != null) {
            vehicleInfo = vehicleInfoMapper.toEntity(vehicleInfoDTO);
            if (vehicleInfo != null) {
                if (files != null) {
                    List<String> fileNameList = uploadS3(files);
                    vehicleInfo.setImages(fileNameList);
                }
                String giai = giaiValueComponent.getGiai(vehicleInfoDTO.getRegistrationAreaCode(),
                    vehicleInfoDTO.getRegistrationGroupNumber(), vehicleInfoDTO.getRegistrationCharacter(),
                    vehicleInfoDTO.getRegistrationNumber1());
                boolean existedInfo = vehicleInfoRepository.existsVehicleInfoByRegistrationAreaCodeAndRegistrationCharacterAndRegistrationGroupNumberAndRegistrationNumber1OrGiai(
                    vehicleInfoDTO.getRegistrationAreaCode(),
                    vehicleInfoDTO.getRegistrationCharacter(),
                    vehicleInfoDTO.getRegistrationGroupNumber(),
                    vehicleInfoDTO.getRegistrationNumber1(),
                    giai);
                if (existedInfo) {
                    NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.VehicleInfo.LICENSE_PLATE_NUMBER_OR_GIAI_EXIST);
                }
                vehicleInfo.setGiai(giai);
                vehicleInfo.setDeleteFlag(Integer.valueOf(CarrierDeleteFlag.NOT_DELETE));
                vehicleInfo.setOperatorId(user.getCompanyId());
                vehicleInfo = vehicleInfoRepository.save(vehicleInfo);

            }
        }
        List<VehicleNoAvailable> vehicleNoAvailables = new ArrayList<>();
        if (vehicleNoAvailableDTOS != null && !vehicleNoAvailableDTOS.isEmpty()) {
            vehicleNoAvailables = vehicleNoAvailableDTOS.stream()
                .filter(dto -> !BaseUtil.isNull(dto.getStartDate()) && !BaseUtil.isNull(dto.getEndDate()))
                .map(vehicleNoAvailableMapper::toEntity)
                .toList();
        }

        if (!vehicleNoAvailables.isEmpty() && vehicleInfo != null && vehicleInfo.getId() != null) {
            for (VehicleNoAvailable v : vehicleNoAvailables) {
                v.setVehicleInfoId(vehicleInfo.getId());
                v.setOperatorId(user.getCompanyId());
                checkStartEndDate(v.getStartDate(), v.getEndDate());
            }
            vehicleNoAvailables = vehicleNoAvailableRepository.saveAll(vehicleNoAvailables);
        }

        response.setVehicleInfo(vehicleInfoMapper.toResponseDto(vehicleInfo));
        List<VehicleNoAvailableResponseDTO> vehicleNoAvailableResponseDTOS = vehicleNoAvailables.stream()
            .map(vehicleNoAvailableMapper::toResponseDto).toList();
        if (!vehicleNoAvailableResponseDTOS.isEmpty()) {
            response.setVehicleNoAvailable(vehicleNoAvailableResponseDTOS);
        } else {
            response.setVehicleNoAvailable(new ArrayList<>());
        }
        return response;
    }

    /**
     * CSVファイルから車両情報を登録する。
     *
     * @param request 車両情報登録リクエスト
     * @param files   アップロードするファイルのリスト
     */
    private void registerByCsv(VehicleInfoRegisterRequest request, List<MultipartFile> files) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        if (request == null || request.getVehicleInfo() == null) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        VehicleInfoDTO vehicleInfoDTO = request.getVehicleInfo();
        List<VehicleNoAvailableDTO> vehicleNoAvailableDTOS = request.getVehicleNoAvailable();
        VehicleInfo vehicleInfo = new VehicleInfo();
        if (vehicleInfoDTO != null) {
            vehicleInfo = vehicleInfoMapper.toEntity(vehicleInfoDTO);
            if (vehicleInfo != null) {
                if (files != null) {
                    List<String> fileNameList = uploadS3(files);
                    vehicleInfo.setImages(fileNameList);
                }
                vehicleInfo.setOperatorId(user.getCompanyId());
                vehicleInfo.setDeleteFlag(Integer.valueOf(CarrierDeleteFlag.NOT_DELETE));
                vehicleInfo = vehicleInfoRepository.save(vehicleInfo);
            }
        }
        List<VehicleNoAvailable> vehicleNoAvailable = new ArrayList<>();
        if (vehicleNoAvailableDTOS != null && !vehicleNoAvailableDTOS.isEmpty()) {
            vehicleNoAvailable = vehicleNoAvailableDTOS.stream()
                .filter(dto -> !BaseUtil.isNull(dto.getStartDate()) && !BaseUtil.isNull(dto.getEndDate()))
                .map(vehicleNoAvailableMapper::toEntity)
                .toList();
        }

        if (!vehicleNoAvailable.isEmpty() && vehicleInfo != null && vehicleInfo.getId() != null) {
            for (VehicleNoAvailable v : vehicleNoAvailable) {
                v.setVehicleInfoId(vehicleInfo.getId());
                v.setOperatorId(user.getCompanyId());
                checkStartEndDate(v.getStartDate(), v.getEndDate());
            }
            vehicleNoAvailableRepository.saveAll(vehicleNoAvailable);
        }
    }

    /**
     * 車両情報を更新する。
     *
     * @param request 車両情報登録リクエスト
     * @param id      更新対象の車両ID
     * @param files   アップロードするファイルのリスト
     */
    @Override
    @Transactional
    public void update(VehicleInfoRegisterRequest request, Long id, List<MultipartFile> files) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        Optional<VehicleInfo> vehicleInfoOptional = vehicleInfoRepository.findById(id);
        vehicleNoAvailableRepository.deleteAllByVehicleInfoId(id);
        if (!vehicleInfoOptional.isPresent()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        VehicleInfoDTO vehicleInfoDTO = request.getVehicleInfo();
        List<VehicleNoAvailableDTO> vehicleNoAvailableDTOS = request.getVehicleNoAvailable();
        VehicleInfo vehicleInfo = new VehicleInfo();
        if (vehicleInfoDTO != null) {
            vehicleInfo = vehicleInfoMapper.toEntity(vehicleInfoDTO);
            if (vehicleInfo != null) {
                if (files != null) {
                    List<String> fileNameList = uploadS3(files);
                    vehicleInfo.setImages(fileNameList);
                }
                vehicleInfo.setId(id);
                vehicleInfo.setOperatorId(user.getCompanyId());
                vehicleInfo.setDeleteFlag(vehicleInfoDTO.getDeleteFlag());
                vehicleInfo = vehicleInfoRepository.save(vehicleInfo);
            }
        }
        List<VehicleNoAvailable> vehicleNoAvailables = new ArrayList<>();
        if (vehicleNoAvailableDTOS != null && !vehicleNoAvailableDTOS.isEmpty()) {
            vehicleNoAvailables = vehicleNoAvailableDTOS.stream()
                .filter(dto -> !BaseUtil.isNull(dto.getStartDate()) && !BaseUtil.isNull(dto.getEndDate()))
                .map(vehicleNoAvailableMapper::toEntity)
                .toList();
        }

        if (!vehicleNoAvailables.isEmpty() && vehicleInfo != null && vehicleInfo.getId() != null) {
            for (VehicleNoAvailable v : vehicleNoAvailables) {
                v.setVehicleInfoId(vehicleInfo.getId());
                v.setOperatorId(user.getCompanyId());
                checkStartEndDate(v.getStartDate(), v.getEndDate());
            }
            vehicleNoAvailableRepository.saveAll(vehicleNoAvailables);
        }
    }

    /**
     * すべての車両情報を取得する。
     *
     * @param type     車両タイプ
     * @param range    温度範囲
     * @param pageable ページ情報
     * @return 車両情報リスト
     */
    @Override
    public VehicleInfoListResponse findAll(String type, String range, Pageable pageable) {
        User user = userContext.getUser();
        VehicleInfoListResponse vehicleInfoListResponse = new VehicleInfoListResponse();
        List<VehicleInfoResponse> responses = new ArrayList<>();
        if (BaseUtil.isNull(user.getCompanyId())) {
            vehicleInfoListResponse.setCurrentPage(Integer.valueOf(ParamConstant.VehicleInfo.DEFAULT_PAGE_NO));
            vehicleInfoListResponse.setTotalItem(0L);
            vehicleInfoListResponse.setTotalPage(0);
            vehicleInfoListResponse.setItemPerPage(Integer.valueOf(ParamConstant.VehicleInfo.LIMIT_DEFAULT));
            return vehicleInfoListResponse;
        }
        List<Integer> vehicleType = convertStringToList(type);
        List<Integer> temperatureRange = convertStringToList(range);
        Page<VehicleInfo> vehicleInfos = vehicleInfoRepository.search(temperatureRange, vehicleType, pageable);
        if (vehicleInfos.isEmpty()) {
            vehicleInfoListResponse.setCurrentPage(Integer.valueOf(ParamConstant.VehicleInfo.DEFAULT_PAGE_NO));
            vehicleInfoListResponse.setTotalItem(0L);
            vehicleInfoListResponse.setTotalPage(0);
            vehicleInfoListResponse.setItemPerPage(Integer.valueOf(ParamConstant.VehicleInfo.LIMIT_DEFAULT));
            return vehicleInfoListResponse;
        }
        vehicleInfoListResponse.setTotalItem(vehicleInfos.getTotalElements());
        vehicleInfoListResponse.setCurrentPage(pageable.getPageNumber() + 1);
        vehicleInfoListResponse.setTotalPage(vehicleInfos.getTotalPages());
        vehicleInfoListResponse.setItemPerPage(pageable.getPageSize());
        List<Long> vehicleInfoIds = vehicleInfos.stream().map(VehicleInfo::getId).toList();
        List<VehicleNoAvailable> vehicleNoAvailables = vehicleNoAvailableRepository.findAllByVehicleInfoIdIn(
            vehicleInfoIds);
        Map<Long, List<VehicleNoAvailable>> vehicleNoAvailableMap = new HashMap<>();
        if (!vehicleNoAvailables.isEmpty()) {
            vehicleNoAvailableMap = vehicleNoAvailables.stream()
                .collect(Collectors.groupingBy(VehicleNoAvailable::getVehicleInfoId));
        }
        for (VehicleInfo vehicleInfo : vehicleInfos) {
            VehicleInfoResponse response = new VehicleInfoResponse();
            VehicleInfoResponseDTO vehicleInfoResponseDTO = vehicleInfoMapper.toResponseDto(vehicleInfo);
            if (vehicleInfoResponseDTO != null) {
                if (vehicleInfo.getImages() != null) {
                    List<String> urlImage = s3HelperUtil.getUrl(vehicleInfo.getImages());
                    vehicleInfoResponseDTO.setImages(urlImage);
                }
                response.setVehicleInfo(vehicleInfoResponseDTO);
            }
            if (vehicleNoAvailableMap.get(vehicleInfo.getId()) != null && !vehicleNoAvailableMap.get(
                vehicleInfo.getId()).isEmpty()) {
                List<VehicleNoAvailableResponseDTO> vehicleNoAvailableResponseDTOS = vehicleNoAvailableMap.get(
                    vehicleInfo.getId()).stream().map(vehicleNoAvailableMapper::toResponseDto).toList();
                if (!vehicleNoAvailableResponseDTOS.isEmpty()) {
                    response.setVehicleNoAvailable(vehicleNoAvailableResponseDTOS);
                } else {
                    response.setVehicleNoAvailable(new ArrayList<>());
                }
            }
            responses.add(response);
        }
        vehicleInfoListResponse.setDataList(responses);

        return vehicleInfoListResponse;
    }

    /**
     * 車両情報の詳細を取得する。
     *
     * @param id 車両ID
     * @return 車両情報レスポンス
     */
    @Override
    public VehicleInfoResponse getDetail(Long id) {
        User user = userContext.getUser();
        VehicleInfoResponse response = new VehicleInfoResponse();
        if (BaseUtil.isNull(user.getCompanyId())) {
            return response;
        }
        VehicleInfo vehicleInfo = vehicleInfoRepository.findByIdAndOperatorId(id, user.getCompanyId());
        if (vehicleInfo == null) {
            return response;
        }
        List<VehicleNoAvailable> vehicleNoAvailables = vehicleNoAvailableRepository.findAllByVehicleInfoIdIn(
            Collections.singletonList(id));
        Map<Long, List<VehicleNoAvailable>> vehicleNoAvailableMap = new HashMap<>();
        if (!vehicleNoAvailables.isEmpty()) {
            vehicleNoAvailableMap = vehicleNoAvailables.stream()
                .collect(Collectors.groupingBy(VehicleNoAvailable::getVehicleInfoId));
        }
        VehicleInfoResponseDTO vehicleInfoResponseDTO = vehicleInfoMapper.toResponseDto(vehicleInfo);
        if (vehicleInfoResponseDTO != null) {
            if (vehicleInfo.getImages() != null) {
                List<String> urlImage = s3HelperUtil.getUrl(vehicleInfo.getImages());
                vehicleInfoResponseDTO.setImages(urlImage);
            }
            response.setVehicleInfo(vehicleInfoResponseDTO);
        }
        if (vehicleNoAvailableMap.get(vehicleInfo.getId()) != null && !vehicleNoAvailableMap.get(vehicleInfo.getId())
            .isEmpty()) {
            List<VehicleNoAvailableResponseDTO> vehicleNoAvailableResponseDTOS = vehicleNoAvailableMap.get(
                vehicleInfo.getId()).stream().map(vehicleNoAvailableMapper::toResponseDto).toList();
            if (!vehicleNoAvailableResponseDTOS.isEmpty()) {
                response.setVehicleNoAvailable(vehicleNoAvailableResponseDTOS);
            } else {
                response.setVehicleNoAvailable(new ArrayList<>());
            }
        }
        return response;
    }

    /**
     * データをインポートする。
     *
     * @param file インポートするファイル
     */
    @Override
    @Transactional
    public void importData(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.endsWith(".csv")) {
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
        VehicleInfoImportRequest vehicleInfoImportRequest = readFile(file);
        VehicleInfoImportDTO vehicleInfoImportDTO = vehicleInfoImportRequest.getVehicleInfoImportDTO();
        if (vehicleInfoImportDTO.getNumberSuccess() <= 0) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        for (VehicleInfoRegisterRequest request : vehicleInfoImportRequest.getVehicleInfoRegisterRequests()) {
            registerByCsv(request, null);
        }
    }

    /**
     * 車両情報を削除する。
     *
     * @param id 削除する車両ID
     */
    @Override
    @Transactional
    public void deleteVehicleInfo(Long id) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId()) || BaseUtil.isNull(String.valueOf(id))) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.SYSTEM_ERR_001))
            );
        }
        List<VehicleDiagramAllocation> allocations = vehicleDiagramAllocationRepository.findAllByVehicleInfoId(id);
        if (allocations == null || allocations.isEmpty()) {
            vehicleInfoRepository.deleteById(id);
            vehicleNoAvailableRepository.deleteAllByVehicleInfoId(id);
        } else {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(System.VEHICLE_INFO_EXIST_IN_ALLOCATION))
            );
        }
    }

    /**
     * ファイルを読み込んで車両情報インポートリクエストを作成する。
     *
     * @param file 読み込むファイル
     * @return 車両情報インポートリクエスト
     */
    private VehicleInfoImportRequest readFile(MultipartFile file) {
        VehicleInfoImportRequest vehicleInfoImportRequest = new VehicleInfoImportRequest();

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
            List<VehicleInfoRegisterRequest> vehicleInfoRegisterRequests = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                rowNum++;
                try {
                    VehicleInfoRegisterRequest vehicleInfoRegisterRequest = parseCSVLine(line, headerMap);
                    vehicleInfoRegisterRequests.add(vehicleInfoRegisterRequest);
                    success++;
                } catch (Exception e) {
                    errors++;
                }
            }
            if (success < 1) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_DATA);
            }
            vehicleInfoImportRequest.setVehicleInfoRegisterRequests(vehicleInfoRegisterRequests);
            VehicleInfoImportDTO vehicleInfoImportDTO = new VehicleInfoImportDTO();
            vehicleInfoImportDTO.setNumberSuccess(success);
            vehicleInfoImportDTO.setNumberFailure(errors);
            vehicleInfoImportRequest.setVehicleInfoImportDTO(vehicleInfoImportDTO);
            return vehicleInfoImportRequest;

        } catch (IOException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_READ);
        }

        return vehicleInfoImportRequest;
    }

    /**
     * ヘッダーの妥当性を検証する。
     *
     * @param headerMap ヘッダーマップ
     */
    private void validateHeaders(Map<String, Integer> headerMap) {
        List<String> requiredHeaders = ParamConstant.VehicleInfo.HEADER_IMPORT;

        List<String> validHeaders = new ArrayList<>();
        for (Field field : VehicleInfoDTO.class.getDeclaredFields()) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                validHeaders.add(jsonProperty.value());
            }
        }
        for (Field field : VehicleInfoDTO.class.getDeclaredFields()) {
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
     * CSV行を解析して車両情報登録リクエストを作成する。
     *
     * @param line      CSV行
     * @param headerMap ヘッダーマップ
     * @return 車両情報登録リクエスト
     */
    private VehicleInfoRegisterRequest parseCSVLine(String line, Map<String, Integer> headerMap) {
        VehicleInfoRegisterRequest request = new VehicleInfoRegisterRequest();
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        try {
            VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO();

            // Parse VehicleInfo data
            vehicleInfoDTO.setRegistrationAreaCode(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.REGISTRATION_AREA_CODE));
            vehicleInfoDTO.setRegistrationGroupNumber(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.REGISTRATION_GROUP_NUMBER));
            vehicleInfoDTO.setRegistrationCharacter(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.REGISTRATION_CHARACTER));
            vehicleInfoDTO.setRegistrationNumber1(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER_1));
            vehicleInfoDTO.setRegistrationNumber2(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.REGISTRATION_NUMBER_2));
            vehicleInfoDTO.setVehicleCode(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.VEHICLE_CODE));
            vehicleInfoDTO.setVehicleName(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.VEHICLE_NAME));
            vehicleInfoDTO.setVehicleType(
                getIntegerValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.VEHICLE_TYPE)));
            vehicleInfoDTO.setTemperatureRange(convertStringToList(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.TEMPERATURE_RANGE)));
            vehicleInfoDTO.setMaxPayload(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.MAX_PAYLOAD)));
            vehicleInfoDTO.setTotalLength(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.TOTAL_LENGTH)));
            vehicleInfoDTO.setTotalWidth(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.TOTAL_WIDTH)));
            vehicleInfoDTO.setTotalHeight(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.TOTAL_HEIGHT)));
            vehicleInfoDTO.setGroundClearance(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.GROUND_CLEARANCE)));
            vehicleInfoDTO.setDoorHeight(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.DOOR_HEIGHT)));
            vehicleInfoDTO.setBodySpecification(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.BODY_SPECIFICATION));
            vehicleInfoDTO.setBodyShape(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.BODY_SHAPE));
            vehicleInfoDTO.setBodyConstruction(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.BODY_CONSTRUCTION));
            vehicleInfoDTO.setStatus(
                getIntegerValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.STATUS)));

            //Parse Vehicle no available data
            VehicleNoAvailableDTO vehicleNoAvailableDTO = new VehicleNoAvailableDTO();
            vehicleNoAvailableDTO.setStartDate(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.NO_AVAIL_START_DATE));
            vehicleNoAvailableDTO.setEndDate(
                getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.NO_AVAIL_END_DATE));
            vehicleNoAvailableDTO.setStatus(
                getIntegerValue(getValueByHeader(fields, headerMap, DataBaseConstant.VehicleInfo.NO_AVAIL_STATUS)));

            request.setVehicleInfo(vehicleInfoDTO);
            request.setVehicleNoAvailable(Collections.singletonList(vehicleNoAvailableDTO));
            return request;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing data: " + e.getMessage());
        }
    }

    /**
     * S3にファイルをアップロードする。
     *
     * @param files アップロードするファイルのリスト
     * @return アップロードされたファイル名のリスト
     */
    private List<String> uploadS3(List<MultipartFile> files) {
        List<String> listFileName = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT.DATE_TIME_FORMAT_HHMM);
        String today = formatter.format(LocalDateTime.now());
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                String originalFileName = file.getOriginalFilename();
                String fileName = today + originalFileName;
                s3HelperUtil.upload(file, fileName);
                listFileName.add(fileName);
            }
        }
        return listFileName;
    }

    /**
     * ファイルをダウンロードする。
     *
     * @param fileName ダウンロードするファイル名
     * @return ファイルのバイト配列
     * @throws IOException 入出力例外
     */
    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        if (BaseUtil.isNull(fileName)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL))
            );
        }
        return s3HelperUtil.download(fileName);
    }

    /**
     * テンプレートファイルをアップロードする。
     *
     * @param file アップロードするファイル
     */
    @Override
    public void uploadTemplateFile(MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            s3HelperUtil.upload(file, fileName);
        }
    }

    /**
     * 開始日と終了日をチェックする。
     *
     * @param startDate 開始日
     * @param endDate   終了日
     */
    private void checkStartEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_DATE_TIME_MAX_MIN),
                    ParamConstant.CARRIER_NO_AVAILABLE_START_DATE, ParamConstant.CARRIER_NO_AVAILABLE_END_DATE)
            );
        }
    }
}
