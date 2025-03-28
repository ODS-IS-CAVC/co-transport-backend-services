package nlj.business.shipper.service.impl;

import static nlj.business.shipper.util.BaseUtil.getBigDecimalValue;
import static nlj.business.shipper.util.BaseUtil.getIntegerValue;
import static nlj.business.shipper.util.BaseUtil.getLongValue;
import static nlj.business.shipper.util.BaseUtil.getStringValue;
import static nlj.business.shipper.util.BaseUtil.getStringValueTime;
import static nlj.business.shipper.util.BaseUtil.getValueByHeader;
import static nlj.business.shipper.util.BaseUtil.uploadS3;
import static nlj.business.shipper.util.StringUtil.convertStringToList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.s3.util.S3HelperUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.constant.MessageConstant.Validate;
import nlj.business.shipper.constant.ParamConstant;
import nlj.business.shipper.domain.ShipperOperator;
import nlj.business.shipper.domain.TransportPlan;
import nlj.business.shipper.domain.TransportPlanImport;
import nlj.business.shipper.domain.TransportPlanItem;
import nlj.business.shipper.dto.TransportPlanDTO;
import nlj.business.shipper.dto.TransportPlanImportDTO;
import nlj.business.shipper.dto.TransportPlanItemDTO;
import nlj.business.shipper.dto.request.TransportPlanFileRequestDTO;
import nlj.business.shipper.dto.request.TransportPlanImportRequestDTO;
import nlj.business.shipper.dto.request.TransportPlanRequestDTO;
import nlj.business.shipper.mapper.TransportPlanMapper;
import nlj.business.shipper.repository.ShipperOperatorRepository;
import nlj.business.shipper.repository.TransportPlanImportRepository;
import nlj.business.shipper.repository.TransportPlanItemRepository;
import nlj.business.shipper.repository.TransportPlanRepository;
import nlj.business.shipper.service.ShipperOperatorService;
import nlj.business.shipper.service.TransportPlanImportService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 輸送計画インポートサービス実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TransportPlanImportServiceImpl implements TransportPlanImportService {

    private final ShipperOperatorService shipperOperatorService;
    private final TransportPlanImportRepository transportPlanImportRepository;
    private final ShipperOperatorRepository shipperOperatorRepository;
    private final TransportPlanMapper transportPlanMapper;
    private final TransportPlanRepository transportPlanRepository;
    private final TransportPlanItemRepository transportPlanItemRepository;
    private final S3HelperUtil s3HelperUtil;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 輸送計画を一括作成する。
     *
     * @param transportPlanFileRequestDTO 輸送計画ファイルリクエストDTO
     * @return 輸送計画インポートDTO
     */
    @Override
    @Transactional
    public TransportPlanImportDTO createTransportPlanBulk(TransportPlanFileRequestDTO transportPlanFileRequestDTO) {
        TransportPlanImportRequestDTO transportPlanImportRequestDTO = readFile(transportPlanFileRequestDTO.getFile());
        TransportPlanImportDTO transportPlanImportDTO = transportPlanImportRequestDTO.getTransportPlanImportDTO();
        List<String> fileNameList = uploadS3(Collections.singletonList(transportPlanFileRequestDTO.getFile()),
            s3HelperUtil);
        transportPlanImportDTO.setFilePath(fileNameList.get(0));
        TransportPlanImport transportPlanImport = transportPlanMapper.toTransportPlanImportEntity(
            transportPlanImportDTO);
        String operatorId = userContext.getUser().getCompanyId();

        ShipperOperator shipperOperator = shipperOperatorRepository.getShipperOperatorById(operatorId);
        if (shipperOperator == null) {
            shipperOperator = shipperOperatorService.createShipperOperator(operatorId);
        }
        transportPlanImport.setShipperOperator(shipperOperator);
        transportPlanImportRepository.save(transportPlanImport);
        for (TransportPlanRequestDTO transportPlanRequestDTO : transportPlanImportRequestDTO.getTransportPlanRequestDTOList()) {
            saveTransportPlan(transportPlanRequestDTO, transportPlanImport, shipperOperator);
        }
        return transportPlanImportDTO;
    }

    /**
     * ファイルを読み込む。
     *
     * @param file ファイル
     * @return 輸送計画インポートリクエストDTO
     */
    private TransportPlanImportRequestDTO readFile(MultipartFile file) {
        TransportPlanImportRequestDTO transportPlanImportRequestDTO = new TransportPlanImportRequestDTO();

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
                headerMap.put(headers[i].trim(), i);
            }

            validateHeaders(headerMap);

            String line;
            int rowNum = 1;
            List<TransportPlanRequestDTO> transportPlanList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                rowNum++;
                try {
                    TransportPlanRequestDTO transportPlanDTO = parseCSVLine(line, headerMap, rowNum);
                    transportPlanList.add(transportPlanDTO);
                    success++;
                } catch (Exception e) {
                    errors++;
                }
            }
            if (success < 1) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_DATA);
            }
            transportPlanImportRequestDTO.setTransportPlanRequestDTOList(transportPlanList);
            TransportPlanImportDTO transportPlanImportDTO = new TransportPlanImportDTO();
            transportPlanImportDTO.setNumberSuccess(success);
            transportPlanImportDTO.setNumberFailure(errors);
            transportPlanImportRequestDTO.setTransportPlanImportDTO(transportPlanImportDTO);
            return transportPlanImportRequestDTO;

        } catch (IOException e) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_READ);
        }

        return transportPlanImportRequestDTO;
    }

    /**
     * ヘッダーを検証する。
     *
     * @param headerMap ヘッダーマップ
     */
    private void validateHeaders(Map<String, Integer> headerMap) {
        List<String> requiredHeaders = Arrays.asList(
            "transport_code",
            "transport_name",
            "item_transport_code",
            "item_transport_name"
        );

        List<String> validHeaders = new ArrayList<>();
        for (Field field : TransportPlanDTO.class.getDeclaredFields()) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                validHeaders.add(jsonProperty.value());
            }
        }
        for (Field field : TransportPlanItemDTO.class.getDeclaredFields()) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                validHeaders.add("item_" + jsonProperty.value());
            }
        }

        List<String> missingRequiredHeaders = requiredHeaders.stream()
            .filter(header -> !headerMap.containsKey(header))
            .collect(Collectors.toList());

        if (!missingRequiredHeaders.isEmpty()) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_MISSING_REQUIRED_COLUMNS,
                String.valueOf(missingRequiredHeaders));
        }

        List<String> invalidHeaders = headerMap.keySet().stream()
            .filter(header -> !validHeaders.contains(header))
            .collect(Collectors.toList());

        if (!invalidHeaders.isEmpty()) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_FILE_DATA,
                "Invalid column names: " + invalidHeaders);
        }
    }

    /**
     * 行を解析する。
     *
     * @param line      行
     * @param headerMap ヘッダーマップ
     * @param rowNum    行番号
     * @return 輸送計画リクエストDTO
     */
    private TransportPlanRequestDTO parseCSVLine(String line, Map<String, Integer> headerMap, int rowNum) {
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        try {
            TransportPlanRequestDTO requestDTO = new TransportPlanRequestDTO();

            // Parse TransportPlan data
            TransportPlanDTO planDTO = new TransportPlanDTO();
            planDTO.setTransportCode(getValueByHeader(fields, headerMap, "transport_code"));
            planDTO.setTransportName(getValueByHeader(fields, headerMap, "transport_name"));
            planDTO.setDepartureFrom(getLongValue(getValueByHeader(fields, headerMap, "departure_from")));
            planDTO.setArrivalTo(getLongValue(getValueByHeader(fields, headerMap, "arrival_to")));
            planDTO.setCollectionDateFrom(getStringValue(getValueByHeader(fields, headerMap, "collection_date_from")));
            planDTO.setCollectionDateTo(getStringValue(getValueByHeader(fields, headerMap, "collection_date_to")));
            planDTO.setCollectionTimeFrom(
                getStringValueTime(getValueByHeader(fields, headerMap, "collection_time_from")));
            planDTO.setCollectionTimeTo(getStringValueTime(getValueByHeader(fields, headerMap, "collection_time_to")));
            planDTO.setTrailerNumber(getIntegerValue(getValueByHeader(fields, headerMap, "trailer_number")));
            planDTO.setRepeatDay(getIntegerValue(getValueByHeader(fields, headerMap, "repeat_day")));
            planDTO.setDayWeek(convertStringToList(getValueByHeader(fields, headerMap, "day_week")));
            planDTO.setPricePerUnit(getLongValue(getValueByHeader(fields, headerMap, "price_per_unit")));
            planDTO.setVehicleCondition(convertStringToList(getValueByHeader(fields, headerMap, "vehicle_condition")));
            planDTO.setOuterPackageCode(getLongValue(getValueByHeader(fields, headerMap, "outer_package_code")));
            planDTO.setTotalLength(getBigDecimalValue(getValueByHeader(fields, headerMap, "total_length")));
            planDTO.setTotalWidth(getBigDecimalValue(getValueByHeader(fields, headerMap, "total_width")));
            planDTO.setTotalHeight(getBigDecimalValue(getValueByHeader(fields, headerMap, "total_height")));
            planDTO.setTotalWeight(getBigDecimalValue(getValueByHeader(fields, headerMap, "total_weight")));
            planDTO.setSpecialInstructions(getStringValue(getValueByHeader(fields, headerMap, "special_instructions")));
            planDTO.setOriginType(getIntegerValue(getValueByHeader(fields, headerMap, "origin_type")));
            try {
                planDTO.setIsPrivate(Boolean.parseBoolean(getValueByHeader(fields, headerMap, "is_private")));
            } catch (Exception e) {
                planDTO.setIsPrivate(null);
            }
            planDTO.setStatus(Integer.valueOf(ParamConstant.TransportPlanStatus.INIT));
            // Parse TransportPlanItem data
            TransportPlanItemDTO itemDTO = new TransportPlanItemDTO();
            itemDTO.setTransportCode(getValueByHeader(fields, headerMap, "item_transport_code"));
            itemDTO.setTransportName(getValueByHeader(fields, headerMap, "item_transport_name"));
            itemDTO.setCollectionDate(getStringValue(getValueByHeader(fields, headerMap, "item_collection_date")));
            itemDTO.setCollectionTimeFrom(
                getStringValueTime(getValueByHeader(fields, headerMap, "item_collection_time_from")));
            itemDTO.setCollectionTimeTo(
                getStringValueTime(getValueByHeader(fields, headerMap, "item_collection_time_to")));
            itemDTO.setDepartureFrom(getLongValue(getValueByHeader(fields, headerMap, "item_departure_from")));
            itemDTO.setArrivalTo(getLongValue(getValueByHeader(fields, headerMap, "item_arrival_to")));
            itemDTO.setTrailerNumber(getIntegerValue(getValueByHeader(fields, headerMap, "item_trailer_number")));
            itemDTO.setPricePerUnit(getBigDecimalValue(getValueByHeader(fields, headerMap, "item_price_per_unit")));
            itemDTO.setTempRange(
                convertStringToList(getValueByHeader(fields, headerMap, "item_temp_range")));
            itemDTO.setOuterPackageCode(
                getIntegerValue(getValueByHeader(fields, headerMap, "item_outer_package_code")));
            itemDTO.setTotalLength(getBigDecimalValue(getValueByHeader(fields, headerMap, "item_total_length")));
            itemDTO.setTotalWidth(getBigDecimalValue(getValueByHeader(fields, headerMap, "item_total_width")));
            itemDTO.setTotalHeight(getBigDecimalValue(getValueByHeader(fields, headerMap, "item_total_height")));
            itemDTO.setWeight(getBigDecimalValue(getValueByHeader(fields, headerMap, "item_weight")));
            itemDTO.setSpecialInstructions(
                getStringValue(getValueByHeader(fields, headerMap, "item_special_instructions")));
            itemDTO.setStatus(Integer.valueOf(ParamConstant.TransportPlanStatus.INIT));
            try {
                itemDTO.setIsPrivate(Boolean.parseBoolean(getValueByHeader(fields, headerMap, "item_is_private")));
            } catch (Exception e) {
                itemDTO.setIsPrivate(null);
            }
            requestDTO.setTransportPlanDTO(planDTO);
            requestDTO.setTransportPlanItemDTOList(Collections.singletonList(itemDTO));

            return requestDTO;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing data: " + e.getMessage());
        }
    }

    /**
     * 輸送計画を保存する。
     *
     * @param transportPlanRequestDTO 輸送計画リクエストDTO
     * @param transportPlanImport     輸送計画インポート
     * @param shipperOperator         船主オペレータ
     */
    private void saveTransportPlan(TransportPlanRequestDTO transportPlanRequestDTO,
        TransportPlanImport transportPlanImport, ShipperOperator shipperOperator) {
        TransportPlan transportPlan = transportPlanMapper.toTransportPlanEntity(
            transportPlanRequestDTO.getTransportPlanDTO());
        transportPlan.setTransportPlanImport(transportPlanImport);
        transportPlan.setShipperOperator(shipperOperator);
        transportPlanRepository.save(transportPlan);

        for (TransportPlanItemDTO transportPlanItemDTO : transportPlanRequestDTO.getTransportPlanItemDTOList()) {
            TransportPlanItem transportPlanItem = transportPlanMapper.toTransportPlanItemEntity(transportPlanItemDTO);
            transportPlanItem.setTransportPlan(transportPlan);
            transportPlanItem.setShipperOperator(shipperOperator);
            transportPlanItemRepository.save(transportPlanItem);
        }
    }
}