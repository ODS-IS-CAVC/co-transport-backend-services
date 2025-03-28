package nlj.business.shipper.service.impl;

import static nlj.business.shipper.util.BaseUtil.getBigDecimalValue;
import static nlj.business.shipper.util.BaseUtil.getIntegerValue;
import static nlj.business.shipper.util.BaseUtil.getValueByHeader;
import static nlj.business.shipper.util.BaseUtil.uploadS3;
import static nlj.business.shipper.util.StringUtil.convertStringToList;

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
import jakarta.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlj.business.shipper.constant.DataBaseConstant;
import nlj.business.shipper.constant.MessageConstant.Validate;
import nlj.business.shipper.constant.ParamConstant;
import nlj.business.shipper.domain.CargoInfo;
import nlj.business.shipper.domain.CargoInfoImport;
import nlj.business.shipper.domain.ShipperOperator;
import nlj.business.shipper.domain.TransportPlanCargoInfo;
import nlj.business.shipper.domain.TransportPlanItem;
import nlj.business.shipper.dto.cargoInfo.CargoInfoDTO;
import nlj.business.shipper.dto.cargoInfo.CargoInfoResponseDTO;
import nlj.business.shipper.dto.cargoInfo.request.CargoInfoRequest;
import nlj.business.shipper.dto.cargoInfo.response.CargoInfoSearchResponse;
import nlj.business.shipper.dto.cargoInfoImport.CargoInfoImportDTO;
import nlj.business.shipper.dto.cargoInfoImport.Request.CargoInfoImportRequest;
import nlj.business.shipper.mapper.CargoInfoImportMapper;
import nlj.business.shipper.mapper.CargoInfoMapper;
import nlj.business.shipper.repository.CargoInfoImportRepository;
import nlj.business.shipper.repository.CargoInfoRepository;
import nlj.business.shipper.repository.ShipperOperatorRepository;
import nlj.business.shipper.repository.TransportPlanCargoInfoRepository;
import nlj.business.shipper.repository.TransportPlanItemRepository;
import nlj.business.shipper.service.CargoInfoService;
import nlj.business.shipper.service.TransportPlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 荷物情報サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CargoInfoServiceImpl implements CargoInfoService {

    @Resource(name = "userContext")
    private final UserContext userContext;
    private final CargoInfoMapper cargoInfoMapper;
    private final CargoInfoRepository cargoInfoRepository;
    private final CargoInfoImportMapper cargoInfoImportMapper;
    private final CargoInfoImportRepository cargoInfoImportRepository;
    private final S3HelperUtil s3HelperUtil;
    private final TransportPlanCargoInfoRepository transportPlanCargoInfoRepository;
    private final TransportPlanItemRepository transportPlanItemRepository;
    private final EntityManager entityManager;
    private final TransportPlanService transportPlanService;
    private final ShipperOperatorRepository shipperOperatorRepository;

    /**
     * 荷物情報を作成する
     *
     * @param request  荷物情報リクエスト
     * @param importId インポートID
     * @return 荷物情報レスポンス
     */
    @Override
    @Transactional
    public CargoInfoResponseDTO createCargoInfo(CargoInfoRequest request, Long importId) {
        User user = userContext.getUser();
        CargoInfoResponseDTO response = new CargoInfoResponseDTO();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        if (request.getCargoInfoDTO() == null) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        CargoInfo cargoInfo = cargoInfoMapper.toEntity(request.getCargoInfoDTO());
        if (cargoInfo != null) {
            cargoInfo.setOperatorId(user.getCompanyId());
            if (importId != null) {
                cargoInfo.setImportId(importId);
            }
            cargoInfo = cargoInfoRepository.save(cargoInfo);
        }

        response = cargoInfoMapper.toDTO(cargoInfo);
        return response;
    }

    /**
     * CSVファイルから荷物情報を作成する
     *
     * @param request  荷物情報リクエスト
     * @param importId インポートID
     */
    private void createCargoInfoByCsv(CargoInfoRequest request, Long importId) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        if (request.getCargoInfoDTO() == null) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        CargoInfo cargoInfo = cargoInfoMapper.toEntity(request.getCargoInfoDTO());
        if (cargoInfo != null) {
            cargoInfo.setOperatorId(user.getCompanyId());
            if (importId != null) {
                cargoInfo.setImportId(importId);
            }
            cargoInfoRepository.save(cargoInfo);
        }

    }

    /**
     * 荷物情報を更新する
     *
     * @param request 荷物情報リクエスト
     * @param id      荷物情報ID
     */
    @Override
    @Transactional
    public void updateCargoInfo(CargoInfoRequest request, Long id) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        if (request.getCargoInfoDTO() == null) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        CargoInfo car = cargoInfoRepository.findByIdAndOperatorId(id, user.getCompanyId());
        if (car == null) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        updateTransferData(request, car);
        CargoInfo cargoInfo = cargoInfoMapper.toEntity(request.getCargoInfoDTO());
        if (cargoInfo != null) {
            cargoInfo.setId(car.getId());
            cargoInfo.setOperatorId(user.getCompanyId());
            cargoInfoRepository.save(cargoInfo);
        }

    }

    /**
     * 輸送計画アイテムのデータを更新する
     *
     * @param cargoInfoRequest 荷物情報リクエスト
     * @param cargoInfo        荷物情報
     */
    private void updateTransferData(CargoInfoRequest cargoInfoRequest, CargoInfo cargoInfo) {
        BigDecimal totalLengthUpdate = Optional.ofNullable(cargoInfoRequest.getCargoInfoDTO().getTotalLength())
            .map(length -> length.setScale(3, RoundingMode.HALF_UP)).orElse(BigDecimal.ZERO);
        BigDecimal totalWidthUpdate = Optional.ofNullable(cargoInfoRequest.getCargoInfoDTO().getTotalWidth())
            .map(width -> width.setScale(3, RoundingMode.HALF_UP)).orElse(BigDecimal.ZERO);
        BigDecimal totalHeightUpdate = Optional.ofNullable(cargoInfoRequest.getCargoInfoDTO().getTotalHeight())
            .map(height -> height.setScale(3, RoundingMode.HALF_UP)).orElse(BigDecimal.ZERO);
        BigDecimal weightUpdate = Optional.ofNullable(cargoInfoRequest.getCargoInfoDTO().getWeight())
            .map(weight -> weight.setScale(3, RoundingMode.HALF_UP)).orElse(BigDecimal.ZERO);

        BigDecimal totalLength = (cargoInfo.getTotalLength() != null) ? cargoInfo.getTotalLength() : BigDecimal.ZERO;
        BigDecimal totalWidth = (cargoInfo.getTotalWidth() != null) ? cargoInfo.getTotalWidth() : BigDecimal.ZERO;
        BigDecimal totalHeight = (cargoInfo.getTotalHeight() != null) ? cargoInfo.getTotalHeight() : BigDecimal.ZERO;
        BigDecimal weight = (cargoInfo.getWeight() != null) ? cargoInfo.getWeight() : BigDecimal.ZERO;

        boolean isChangeTotalLength = totalLength.compareTo(totalLengthUpdate) != 0;
        boolean isChangeTotalWidth = totalWidth.compareTo(totalWidthUpdate) != 0;
        boolean isChangeTotalHeight = totalHeight.compareTo(totalHeightUpdate) != 0;
        boolean isChangeWeight = weight.compareTo(weightUpdate) != 0;
        if (!(isChangeTotalHeight || isChangeTotalWidth || isChangeTotalLength || isChangeWeight)) {
            return;
        }
        List<TransportPlanItem> transportPlanItems = transportPlanItemRepository.findAllByCargoInfoId(
            cargoInfo.getId());
        if (transportPlanItems.isEmpty()) {
            return;
        }

        List<TransportPlanItem> transportPlanItemUpdate = transportPlanItems.stream()
            .filter(item -> item.getStatus() < 3)
            .collect(Collectors.toList());

        if (transportPlanItemUpdate.isEmpty()) {
            log.info(
                "Transport plan item have status not equals 0,1,2. Don't update transport_plan_item and cns_line_item_by_date");
            return;
        }
        for (TransportPlanItem transportPlanItem : transportPlanItemUpdate) {
            transportPlanItem.setTotalLength(cargoInfoRequest.getCargoInfoDTO().getTotalLength());
            transportPlanItem.setTotalWidth(cargoInfoRequest.getCargoInfoDTO().getTotalWidth());
            transportPlanItem.setTotalHeight(cargoInfoRequest.getCargoInfoDTO().getTotalHeight());
            transportPlanItem.setWeight(cargoInfoRequest.getCargoInfoDTO().getWeight());
            // update table cns_line_item_by_date
            String cnsLineItemByDateSql =
                "UPDATE cns_line_item_by_date SET updated_date = CURRENT_TIMESTAMP, total_height = :totalHeight, "
                    + "total_length = :totalLength, total_width = :totalWidth, weight = :weight WHERE transport_plan_item_id = :transportPlanItemId";
            entityManager.createNativeQuery(cnsLineItemByDateSql)
                .setParameter("transportPlanItemId", transportPlanItem.getId())
                .setParameter("totalHeight", totalHeightUpdate)
                .setParameter("totalLength", totalLengthUpdate)
                .setParameter("totalWidth", totalWidthUpdate)
                .setParameter("weight", weightUpdate)
                .executeUpdate();

            String sqlMatching = "DELETE FROM t_trans_matching WHERE transport_plan_item_id = :itemId";
            entityManager.createNativeQuery(sqlMatching)
                .setParameter("itemId", transportPlanItem.getId())
                .executeUpdate();
        }
        // update table trsp_plan_item
        transportPlanItemRepository.saveAll(transportPlanItemUpdate);

    }

    /**
     * 荷物情報を取得する
     *
     * @param id 荷物情報ID
     * @return 荷物情報レスポンス
     */
    @Override
    public CargoInfoResponseDTO getDetailCargoInfo(Long id) {
        CargoInfoResponseDTO response = new CargoInfoResponseDTO();
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        CargoInfo car = cargoInfoRepository.findByIdAndOperatorId(id, user.getCompanyId());
        if (car == null) {
            return response;
        }
        TransportPlanCargoInfo transportPlan = transportPlanCargoInfoRepository.findFirstByCargoInfoIdOrderByIdDesc(id);
        response = cargoInfoMapper.toDTO(car);
        response.setTransportPlanId(transportPlan == null ? null : transportPlan.getTransportPlanId());
        return response;
    }

    /**
     * 荷物情報を検索する
     *
     * @param status   荷物情報ステータス
     * @param pageable ページネーション
     * @return 荷物情報検索レスポンス
     */
    @Override
    public CargoInfoSearchResponse search(String status, Pageable pageable) {
        CargoInfoSearchResponse response = new CargoInfoSearchResponse();
        List<Integer> listStatus = new ArrayList<>();
        if (status != null) {
            listStatus = convertStringToList(status);
        }
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        Page<CargoInfo> cargoInfoPage = cargoInfoRepository.search(listStatus, pageable);
        if (cargoInfoPage.isEmpty()) {
            response.setCurrentPage(Integer.valueOf(ParamConstant.CargoInfo.DEFAULT_PAGE_NO));
            response.setTotalItem(0L);
            response.setTotalPages(0);
            response.setItemPerPage(Integer.valueOf(ParamConstant.CargoInfo.LIMIT_DEFAULT));
            return response;
        }
        response.setTotalItem(cargoInfoPage.getTotalElements());
        response.setCurrentPage(pageable.getPageNumber() + 1);
        response.setTotalPages(cargoInfoPage.getTotalPages());
        response.setItemPerPage(pageable.getPageSize());
        List<CargoInfoResponseDTO> cargoInfoDTOS = cargoInfoPage.stream()
            .map(cargoInfoMapper::toDTO)
            .toList();

        List<Long> cargoInfoIds = cargoInfoDTOS.stream()
            .map(CargoInfoResponseDTO::getId)
            .collect(Collectors.toList());

        List<TransportPlanCargoInfo> transportPlans = transportPlanCargoInfoRepository.findByCargoInfoIdInOrderByIdDesc(
            cargoInfoIds);

        Map<Long, Long> cargoIdToTransportPlanId = transportPlans.stream()
            .collect(Collectors.toMap(
                TransportPlanCargoInfo::getCargoInfoId,
                TransportPlanCargoInfo::getTransportPlanId,
                (existing, replacement) -> existing
            ));

        cargoInfoDTOS.forEach(cargoInfo ->
            cargoInfo.setTransportPlanId(cargoIdToTransportPlanId.getOrDefault(cargoInfo.getId(), null))
        );
        ShipperOperator shipperOperator = shipperOperatorRepository.getShipperOperatorById(user.getCompanyId());
        if (shipperOperator != null) {
            response.setCompanyName(shipperOperator.getOperatorName());
        }
        response.setDataList(cargoInfoDTOS);
        return response;
    }

    /**
     * CSVファイルから荷物情報をインポートする
     *
     * @param file CSVファイル
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
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        CargoInfoImportRequest request = readFile(file);
        CargoInfoImportDTO cargoInfoImportDTO = request.getCargoInfoImportDTO();
        if (cargoInfoImportDTO.getNumberSuccess() <= 0) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }
        CargoInfoImport cargoInfoImport = cargoInfoImportMapper.toEntity(cargoInfoImportDTO);
        cargoInfoImport.setOperatorId(user.getCompanyId());
        List<String> listFileName = uploadS3(Collections.singletonList(file), s3HelperUtil);
        cargoInfoImport.setFilePath(listFileName.get(0));
        cargoInfoImport = cargoInfoImportRepository.save(cargoInfoImport);

        for (CargoInfoRequest cargoInfoRequest : request.getCargoInfoRequestList()) {
            createCargoInfoByCsv(cargoInfoRequest, cargoInfoImport.getId());
        }
    }

    /**
     * 荷物情報を削除する
     *
     * @param id 荷物情報ID
     */
    @Override
    @Transactional
    public void deleteCargoInfo(Long id) {
        if (BaseUtil.isNull(String.valueOf(id))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_NOT_NULL);
        }
        cargoInfoRepository.deleteById(id);
    }

    /**
     * CSVファイルを読み込む
     *
     * @param file CSVファイル
     * @return 荷物情報インポートリクエスト
     */
    private CargoInfoImportRequest readFile(MultipartFile file) {
        CargoInfoImportRequest cargoInfoImportRequest = new CargoInfoImportRequest();

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
                headerMap.put(headers[i].trim().replace(ParamConstant.CargoInfo.BOM, ""), i);
            }

            validateHeaders(headerMap);

            String line;
            int rowNum = 1;
            List<CargoInfoRequest> cargoInfoRequests = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                rowNum++;
                try {
                    CargoInfoRequest cargoInfoRequest = parseCSVLine(line, headerMap, rowNum);
                    cargoInfoRequests.add(cargoInfoRequest);
                    success++;
                } catch (Exception e) {
                    errors++;
                }
            }
            if (success < 1) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_DATA);
            }
            cargoInfoImportRequest.setCargoInfoRequestList(cargoInfoRequests);
            CargoInfoImportDTO cargoInfoImportDTO = new CargoInfoImportDTO();
            cargoInfoImportDTO.setNumberSuccess(success);
            cargoInfoImportDTO.setNumberFailure(errors);
            cargoInfoImportRequest.setCargoInfoImportDTO(cargoInfoImportDTO);
            return cargoInfoImportRequest;

        } catch (IOException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_READ);
        }

        return cargoInfoImportRequest;
    }

    /**
     * ヘッダーを検証する
     *
     * @param headerMap ヘッダーマップ
     */
    private void validateHeaders(Map<String, Integer> headerMap) {
        List<String> requiredHeaders = ParamConstant.CargoInfo.HEADER_IMPORT;

        List<String> validHeaders = new ArrayList<>();
        for (Field field : CargoInfoDTO.class.getDeclaredFields()) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                validHeaders.add(jsonProperty.value());
            }
        }
        for (Field field : CargoInfoDTO.class.getDeclaredFields()) {
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
     * CSV行を解析する
     *
     * @param line      CSV行
     * @param headerMap ヘッダーマップ
     * @param rowNum    行番号
     * @return 荷物情報リクエスト
     */
    private CargoInfoRequest parseCSVLine(String line, Map<String, Integer> headerMap, int rowNum) {
        CargoInfoRequest request = new CargoInfoRequest();
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        try {
            CargoInfoDTO cargoInfoDTO = new CargoInfoDTO();

            // Parse CargoInfo data
            cargoInfoDTO.setCargoName(getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.CARGO_NAME));
            cargoInfoDTO.setOuterPackageCode(
                getIntegerValue(getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.OUTER_PACKAGE_CODE)));
            cargoInfoDTO.setTotalLength(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.TOTAL_LENGTH)));
            cargoInfoDTO.setTotalWidth(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.TOTAL_WIDTH)));
            cargoInfoDTO.setTotalHeight(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.TOTAL_HEIGHT)));
            cargoInfoDTO.setTempRange(
                convertStringToList(getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.TEMP_RANGE)));
            cargoInfoDTO.setSpecialInstructions(
                getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.SPECIAL_INSTRUCTIONS));
            cargoInfoDTO.setWeight(
                getBigDecimalValue(getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.WEIGHT)));
            cargoInfoDTO.setStatus(
                getIntegerValue(getValueByHeader(fields, headerMap, DataBaseConstant.CargoInfo.STATUS)));
            // Require cargo name and outer package code not null
            if (BaseUtil.isNull(cargoInfoDTO.getCargoName()) || BaseUtil.isNull(
                String.valueOf(cargoInfoDTO.getOuterPackageCode()))) {
                throw new IllegalArgumentException("Missing information on line " + rowNum);
            }
            request.setCargoInfoDTO(cargoInfoDTO);
            return request;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing data: " + e.getMessage());
        }
    }

    /**
     * ファイルをダウンロードする
     *
     * @param fileName ファイル名
     * @return ファイルバイト配列
     * @throws IOException ファイル読み込みエラー
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
     * テンプレートファイルをアップロードする
     *
     * @param file ファイル
     */
    @Override
    public void uploadTemplateFile(MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            s3HelperUtil.upload(file, fileName);
        }
    }

}
