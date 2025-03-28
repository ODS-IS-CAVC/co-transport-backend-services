package jp.co.nlj.ttmi.aop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jp.co.nlj.ttmi.aop.proxy.ValidateField;
import jp.co.nlj.ttmi.constant.DataBaseConstant.DATE_TIME_FORMAT;
import jp.co.nlj.ttmi.constant.MessageConstant;
import jp.co.nlj.ttmi.constant.MessageConstant.Validate;
import jp.co.nlj.ttmi.constant.ParamConstant;
import jp.co.nlj.ttmi.constant.ParamConstant.CharacterSign;
import jp.co.nlj.ttmi.constant.ParamConstant.CorrectionCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * バリデーションアスペクトクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Aspect
@Component
@Order(1)
public class ValidationAspect {

    /**
     * メソッド引数のバリデーション
     *
     * @param joinPoint ジョインポイント
     * @throws IllegalAccessException アクセス例外
     */
    @Before("execution(* jp.co.nlj.ttmi..*(..))")
    public void validateMethodArguments(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg != null) {
                validateFields(arg);
            }
        }
    }

    /**
     * フィールドのバリデーション
     *
     * @param obj オブジェクト
     * @throws IllegalAccessException アクセス例外
     */
    private void validateFields(Object obj) throws IllegalAccessException {
        validateFields(obj, new HashSet<>());
    }

    /**
     * フィールドのバリデーション
     *
     * @param obj            オブジェクト
     * @param visitedObjects 訪問済みオブジェクト
     * @throws IllegalAccessException アクセス例外
     */
    private void validateFields(Object obj, Set<Object> visitedObjects) throws IllegalAccessException {
        if (obj == null || visitedObjects.contains(obj)) {
            return;
        }
        visitedObjects.add(obj);

        if (obj instanceof Collection<?>) {
            for (Object item : (Collection<?>) obj) {
                if (item != null) {
                    validateFields(item, visitedObjects);
                }
            }
            return;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!isPojo(field.getType())) {
                continue; // Skip non-POJO fields
            }

            field.setAccessible(true);
            Object fieldValue = field.get(obj);

            if (field.isAnnotationPresent(ValidateField.class)) {
                ValidateField annotation = field.getAnnotation(ValidateField.class);
                String fieldName = getJsonPropertyValue(field);
                validateValue(fieldName, fieldValue, annotation, obj);
            }

            if (fieldValue != null) {
                if (fieldValue instanceof Collection<?>) {
                    for (Object item : (Collection<?>) fieldValue) {
                        if (item != null) {
                            validateFields(item, visitedObjects);
                        }
                    }
                } else if (isPojo(field.getType())) {
                    validateFields(fieldValue, visitedObjects);
                }
            }
        }
    }

    /**
     * 値のバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     * @throws IllegalAccessException アクセス例外
     */
    private void validateValue(String fieldName, Object value, ValidateField annotation, Object obj)
        throws IllegalAccessException {
        validateNotNull(fieldName, value, annotation);
        validateStringLength(fieldName, value, annotation);
        validateInputIsBoolean(fieldName, value, annotation);
        validateNumericPrecisionAndScale(fieldName, value, annotation);
        validatePositiveInteger(fieldName, value, annotation);
        validateDateFormat(fieldName, value, annotation, obj);
        validateTimeFormat(fieldName, value, annotation);
        validateHalfWidth(fieldName, value, annotation);
        validateDateCompareField(fieldName, annotation, value);
        validateCorrectionCode(fieldName, value, annotation, obj);
        validateContainsSpecialCharacter(fieldName, value, annotation);
        validateHourFormat(fieldName, value, annotation);
        validateVehicleType(fieldName, value, annotation);
        validateVanBodyType(fieldName, value, annotation);
        validateTractorType(fieldName, value, annotation);
        validatePackingCode(fieldName, value, annotation);
        validateInputContainOnlyNumber(fieldName, value, annotation);
        validateIntegerNumber(fieldName, value, annotation);
        validateMinValue(fieldName, value, annotation);
        validateMaxValue(fieldName, value, annotation);
        validateHazardousMaterialVehicleType(fieldName, value, annotation);
        validatePowerGate(fieldName, value, annotation);
        validateCraneEquipped(fieldName, value, annotation);
        validateUpperTempField(fieldName, value, annotation, obj);
        validateLength(fieldName, value, annotation);
        validatePackageCode(fieldName, value, annotation);
        validateMinField(fieldName, value, annotation, obj);
        validateWingDoor(fieldName, value, annotation);
        validateRefrigerationUnit(fieldName, value, annotation);
        validateDimensionCode(fieldName, value, annotation);
        validateRegistrationNumber(fieldName, value, annotation);
        validateMsgFnStasCdType(fieldName, value, annotation);
        validateTrspNormalAbnormal(fieldName, value, annotation);
        validateIncidentCategory(fieldName, value, annotation);
    }

    /**
     * 長さを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateLength(String fieldName, Object value, ValidateField annotation) {
        if (!BaseUtil.isNull(String.valueOf(value)) && !BaseUtil.isNull(annotation.length())) {
            int lengthField = Integer.parseInt(annotation.length());
            if (value.toString().trim().length() != lengthField) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_LENGTH_FIELD),
                        fieldName, annotation.length())
                );
            }
        }
    }

    /**
     * メッセージ種別コードを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateMsgFnStasCdType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.msgFnStasCdType()) {
            if (value instanceof Integer || value instanceof Number) {
                int numValue = ((Number) value).intValue();
                if (!ParamConstant.MSG_FN_STAS_CD_TYPE.contains(String.valueOf(numValue))) {
                    NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.MSG_FN_STAS_CD_TYPE, fieldName);
                }
            }
        }
    }

    /**
     * 輸送状況コードを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateTrspNormalAbnormal(String fieldName, Object value, ValidateField annotation) {
        if (annotation.trspNormalAbnormal()
            && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.TRSP_NORMAL_ABNORMAL.contains(String.valueOf(value.toString()))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.TRSP_NORMAL_ABNORMAL, fieldName);
        }
    }

    /**
     * フィールドがnullでないか検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateNotNull(String fieldName, Object value, ValidateField annotation) {
        if (annotation.notNull() && BaseUtil.isNull(String.valueOf(value))) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NOT_NULL),
                    fieldName)
            );
        }
    }

    /**
     * 文字列の長さを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateStringLength(String fieldName, Object value, ValidateField annotation) {
        if (!BaseUtil.isNull(String.valueOf(value))) {
            if (value.toString().trim().length() < annotation.minLength()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_MIN_LENGTH),
                        fieldName, annotation.minLength())
                );
            }
            if (value.toString().trim().length() > annotation.maxLength()) {
                if (value instanceof BigDecimal bigDecimal) {
                    String digitsOnly = removeBigDecimalSign(bigDecimal);
                    if (digitsOnly.length() > annotation.maxLength()) {
                        throw new NextWebException(
                            new NextAPIError(HttpStatus.BAD_REQUEST,
                                SoaResponsePool.get(Validate.VALID_MAX_LENGTH),
                                fieldName, annotation.maxLength())
                        );
                    }
                }
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_MAX_LENGTH),
                        fieldName, annotation.maxLength())
                );
            }
        }
    }

    /**
     * 数値の精度とスケールを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateNumericPrecisionAndScale(String fieldName, Object value, ValidateField annotation) {
        if (value instanceof String str && BaseUtil.isNull(str)) {
            return;
        }
        if (annotation.precision() > 0 && value != null) {
            BigDecimal bigDecimal;
            try {
                bigDecimal = new BigDecimal(String.valueOf(value));
            } catch (NumberFormatException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_NUMERIC),
                        fieldName, annotation.positiveNumber())
                );
            }
            if (bigDecimal.compareTo(BigDecimal.ZERO) < 0 && annotation.positiveNumber()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_POSITIVE_NUMBER),
                        fieldName, annotation.positiveNumber())
                );
            }
            int integerPartLength = annotation.precision() - annotation.scale();
            BigDecimal decimalStripZeroes = bigDecimal.stripTrailingZeros();
            if ((decimalStripZeroes.precision() - decimalStripZeroes.scale()) > integerPartLength) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_PRECISION),
                        fieldName, integerPartLength)
                );
            }
            if (bigDecimal.scale() > annotation.scale()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_SCALE),
                        fieldName, annotation.scale() == 0 ? "This parameter is an integer"
                        : "Scale length must be less than " + annotation.scale())
                );
            }
        }
    }

    /**
     * 正の整数を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validatePositiveInteger(String fieldName, Object value, ValidateField annotation) {
        if (value instanceof Integer integer && integer < 0 && annotation.positiveNumber()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_POSITIVE_NUMBER),
                    fieldName, annotation.positiveNumber())
            );
        }
        if (!BaseUtil.isNull(String.valueOf(value)) && annotation.positiveText()) {
            try {
                int number = Integer.parseInt(value.toString());
                if (number < 0) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_POSITIVE_NUMBER),
                            fieldName, annotation.positiveNumber())
                    );
                }
            } catch (NumberFormatException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_POSITIVE_NUMBER),
                        fieldName, annotation.positiveNumber())
                );
            }

        }
    }

    /**
     * 日付形式を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateDateFormat(String fieldName, Object value, ValidateField annotation, Object obj) {
        if (!annotation.dateFormat().isEmpty() && !BaseUtil.isNull(String.valueOf(value))) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT.DATE_FORMAT);
                LocalDate date = LocalDate.parse(value.toString(), formatter);
                if (!value.toString().equals(date.format(formatter))) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                            fieldName, annotation.dateFormat())
                    );
                }
                validateEndDate(fieldName, annotation, obj, formatter, date);
            } catch (DateTimeParseException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                        fieldName, annotation.dateFormat())
                );
            }
        }
    }

    /**
     * 終了日を検証する。<BR>
     *
     * @param fieldName   フィールド名
     * @param annotation  アノテーション
     * @param obj         オブジェクト
     * @param dtf         日付フォーマッタ
     * @param currentDate 現在日付
     */
    private void validateEndDate(String fieldName, ValidateField annotation, Object obj, DateTimeFormatter dtf,
        LocalDate currentDate) {
        if (!annotation.endDateField().isEmpty()) {
            Field endDateField = null;
            try {
                endDateField = obj.getClass().getDeclaredField(annotation.endDateField());
                endDateField.setAccessible(true);
                String endDateStr = (String) endDateField.get(obj);

                if (endDateStr != null && !endDateStr.isEmpty()) {
                    LocalDate endDate = LocalDate.parse(endDateStr, dtf);
                    if (currentDate.isAfter(endDate)) {
                        throw new NextWebException(
                            new NextAPIError(
                                HttpStatus.BAD_REQUEST, SoaResponsePool.get(Validate.VALID_START_DATE_AFTER_END_DATE),
                                fieldName));
                    } else if (currentDate.equals(endDate)) {
                        validateStartTimeAndEndTime(annotation, obj);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle exception or log error
            } catch (DateTimeParseException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                        getJsonPropertyValue(endDateField), annotation.dateFormat()));
            }
        }
    }

    /**
     * 開始時間と終了時間を検証する。<BR>
     *
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateStartTimeAndEndTime(ValidateField annotation, Object obj)
        throws NoSuchFieldException, IllegalAccessException {
        if (!BaseUtil.isNull(annotation.startTimeField()) && !BaseUtil.isNull(annotation.endTimeField())) {
            Field startTimeField = obj.getClass().getDeclaredField(annotation.startTimeField());
            Field endTimeField = obj.getClass().getDeclaredField(annotation.endTimeField());
            startTimeField.setAccessible(true);
            endTimeField.setAccessible(true);

            String startTimeValue = (String) startTimeField.get(obj);
            String endTimeValue = (String) endTimeField.get(obj);
            if (BaseUtil.isNull(startTimeValue) || BaseUtil.isNull(endTimeValue)) {
                return;
            }

            validateTimeFormat(getJsonPropertyValue(startTimeField), startTimeValue,
                startTimeField.getAnnotation(ValidateField.class));
            validateTimeFormat(getJsonPropertyValue(endTimeField), endTimeValue,
                endTimeField.getAnnotation(ValidateField.class));
            boolean isStartGreater = compareTime(startTimeValue, endTimeValue,
                startTimeField.getAnnotation(ValidateField.class).timeFormat(),
                endTimeField.getAnnotation(ValidateField.class).timeFormat());
            if (isStartGreater) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_START_TIME_AFTER_END_TIME),
                        getJsonPropertyValue(startTimeField), getJsonPropertyValue(endTimeField)));
            }
        }
    }

    /**
     * 時間形式を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateTimeFormat(String fieldName, Object value, ValidateField annotation) {
        if (!BaseUtil.isNull(String.valueOf(value)) && !annotation.timeFormat().isEmpty()) {
            try {
                if (value.toString().length() >= ParamConstant.MIN_LENGTH_OF_TIME
                    && ParamConstant.VALIDATE_TIME_ERROR.equals(value.toString().substring(0, 2))) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_TIME_FORMAT),
                            fieldName, annotation.timeFormat())
                    );
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(annotation.timeFormat());
                LocalTime.parse(value.toString(), formatter);
            } catch (Exception e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_TIME_FORMAT),
                        fieldName, annotation.timeFormat())
                );
            }
        }
    }

    /**
     * 終了時間を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param annotation アノテーション
     * @param obj        オブジェクト
     * @param value      値
     */
    private void validateEndTime(String fieldName, ValidateField annotation, Object obj, Object value) {
        try {
            if (!annotation.timeFormat().isEmpty() && !annotation.endTimeField().isEmpty()) {
                Field endTimeField = obj.getClass().getDeclaredField(annotation.endTimeField());
                endTimeField.setAccessible(true);
                String endTimeValue = (String) endTimeField.get(obj);
                if (BaseUtil.isNull(endTimeValue) || BaseUtil.isNull((String) value)) {
                    return;
                }
                validateTimeFormat(getJsonPropertyValue(endTimeField), endTimeValue,
                    endTimeField.getAnnotation(ValidateField.class));

                boolean isStartGreater = compareTime(value.toString(), endTimeValue, annotation.timeFormat(),
                    endTimeField.getAnnotation(ValidateField.class).timeFormat());
                if (isStartGreater) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_START_TIME_AFTER_END_TIME),
                            fieldName, getJsonPropertyValue(endTimeField)));
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {

        }
    }

    /**
     * 時間を比較する。<BR>
     *
     * @param startTime       開始時間
     * @param endTime         終了時間
     * @param startTimeFormat 開始時間フォーマット
     * @param endTimeFormat   終了時間フォーマット
     * @return 比較結果
     */
    private boolean compareTime(String startTime, String endTime, String startTimeFormat, String endTimeFormat) {
        LocalTime starLocalTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern(startTimeFormat));
        LocalTime endLocalTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern(endTimeFormat));
        return starLocalTime.isAfter(endLocalTime) || starLocalTime.equals(endLocalTime);
    }

    /**
     * プロパティ名を取得する。<BR>
     *
     * @param field フィールド
     * @return プロパティ名
     */
    private String getJsonPropertyValue(Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if (jsonProperty != null && !jsonProperty.value().isEmpty()) {
            return jsonProperty.value();
        }
        return field.getName();
    }

    /**
     * 半角文字を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateHalfWidth(String fieldName, Object value, ValidateField annotation) {
        if (!BaseUtil.isNull(String.valueOf(value)) && annotation.numberContainSpecialChar()) {
            String regex = "^[0-9()\\-+]+$";
            if (!value.toString().matches(regex)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_PHONE_NUMBER),
                        fieldName));
            }
        }
    }

    /**
     * 全角文字を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateFullWidth(String fieldName, Object value, ValidateField annotation) {
        if (!BaseUtil.isNull(String.valueOf(value)) && annotation.textFullWidth()) {
            String fullwidthPattern = ParamConstant.REGEX_FULL_WIDTH;
            if (!value.toString().matches(fullwidthPattern)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_FULL_WIDTH),
                        fieldName));
            }
        }
        if (!BaseUtil.isNull(String.valueOf(value)) && annotation.textFullWidthAndKanji()) {
            String regex = "^[a-zA-Z\uFF21-\uFF3A\uFF41-\uFF5A\u4E00-\u9FFF\uFF10-\uFF19]+$";
            if (!value.toString().matches(regex)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_FULL_WIDTH),
                        fieldName));
            }
        }
    }

    /**
     * 特殊文字を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateContainsSpecialCharacter(String fieldName, Object value, ValidateField annotation) {
        if (annotation.textContainSpecialChar() && !BaseUtil.isNull(String.valueOf(value))) {
            String containsSpecialCharPattern = ".*[^a-zA-Z0-9].*";
            if (value.toString().matches(containsSpecialCharPattern)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_CONTAIN_SPECIAL_CHARACTER),
                        fieldName));
            }
        }
    }

    /**
     * 入力が配列またはブール値か検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateInputIsBoolean(String fieldName, Object value, ValidateField annotation) {
        if (annotation.inputIsArrOrBoolean() && !BaseUtil.isNull(String.valueOf(value))) {
            String arrayPattern = "\\[\\d+(,\\d+)*\\]";
            if (value.toString().matches(arrayPattern) || value.toString().equalsIgnoreCase("true") || value.toString()
                .equalsIgnoreCase("false")) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_BOOLEAN),
                        fieldName));
            }
        }
    }

    /**
     * 日付比較フィールドを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param annotation アノテーション
     * @param value      値
     */
    private void validateDateCompareField(String fieldName, ValidateField annotation, Object value) {
        if (!annotation.dateCompareField().isEmpty() && !BaseUtil.isNull(String.valueOf(value))) {
            try {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    DATE_TIME_FORMAT.DATE_FORMAT);
                LocalDate inputDate = LocalDate.parse(value.toString(), formatter);
                LocalDate currentDate = LocalDate.now();

                if (currentDate.isAfter(inputDate)) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_DATE_BEFORE_CURRENT_DATE),
                            fieldName, annotation.dateFormat()));
                }
            } catch (DateTimeParseException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                        fieldName, annotation.dateFormat()));
            }
        }
    }

    /**
     * 修正コードを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateCorrectionCode(String fieldName, Object value, ValidateField annotation, Object obj) {
        if (annotation.correctionCode() && !BaseUtil.isNull(String.valueOf(value))) {
            if (!ParamConstant.CORRECTION_CODE.contains(value.toString())) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_CORRECTION_CODE),
                        fieldName));
            }

            if (CorrectionCode.CANCEL.equals(value.toString()) && !annotation.correctionReasonField().isEmpty()) {
                try {
                    Field correctionReasonField = obj.getClass().getDeclaredField(annotation.correctionReasonField());
                    correctionReasonField.setAccessible(true);
                    String reasonValue = (String) correctionReasonField.get(obj);
                    if (BaseUtil.isNull(reasonValue)) {
                        throw new NextWebException(
                            new NextAPIError(HttpStatus.BAD_REQUEST,
                                SoaResponsePool.get(Validate.VALID_CORRECTION_CODE_CANCEL_REASON),
                                getJsonPropertyValue(correctionReasonField)));
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            }
        }
    }

    /**
     * 時間フォーマットを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateHourFormat(String fieldName, Object value, ValidateField annotation) {
        if (annotation.hourFormat() && !BaseUtil.isNull(String.valueOf(value))) {
            String str = value.toString();
            if (value.toString().length() == 1) {
                str = "0" + str;
            }
            String regex = "^(0[0-9]|1[0-2])$";
            if (!str.matches(regex)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_HOUR_FORMAT),
                        fieldName));
            }
        }
    }

    /**
     * 入力が数値のみか検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateInputContainOnlyNumber(String fieldName, Object value, ValidateField annotation) {
        if (annotation.onlyNumber() && !BaseUtil.isNull(String.valueOf(value))) {
            String pattern = "^\\d+$";
            if (!value.toString().matches(pattern)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_CONTAIN_SPECIAL_CHARACTER),
                        fieldName));
            }
        }
    }

    /**
     * 輸送状況コードを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateVehicleType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.vehicleType() && !BaseUtil.isNull(String.valueOf(value))
            && (!ParamConstant.VEHICLE_TYPE.contains(String.valueOf(value)))) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_VEHICLE_TYPE),
                    fieldName));

        }
    }

    /**
     * フラットベッド車体タイプを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateVanBodyType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.vanBodyType() && !BaseUtil.isNull(String.valueOf(value))
            && (!ParamConstant.FLATBED_VAN_BODY_TYPE.contains(String.valueOf(value)))) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_VAN_BODY_TYPE),
                    fieldName));
        }
    }

    /**
     * トラクタタイプを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateTractorType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.tractorType()
            && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.TRACTOR.contains(value.toString().trim())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_TRACTOR_TYPE),
                    fieldName));
        }

    }

    /**
     * 梱包コードを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validatePackingCode(String fieldName, Object value, ValidateField annotation)
        throws IllegalAccessException {
        if (annotation.packingCode() && value instanceof String pckCode) {
            List<String> packingCodeList = Arrays.asList(ParamConstant.getPackingCode());
            if (!packingCodeList.contains(pckCode.trim())) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_PACKING_CODE),
                        fieldName, BaseUtil.listToString(packingCodeList, ",")));
            }
        }
    }

    /**
     * 整数形式を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateIntegerNumber(String fieldName, Object value, ValidateField annotation) {
        if (annotation.integerFormat() && !BaseUtil.isNull(String.valueOf(value))) {
            BigDecimal decimal = new BigDecimal(value.toString());
            if (decimal.scale() > 0) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_INTEGER_FORMAT),
                        fieldName));
            }
        }
    }

    /**
     * 最小値を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateMinValue(String fieldName, Object value, ValidateField annotation) {
        if (!BaseUtil.isNull(String.valueOf(value))
            && value instanceof Number number
            && number.floatValue() < annotation.minValue()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_MIN_VALUE),
                    fieldName, annotation.minValue()));
        }
    }

    /**
     * 最大値を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateMaxValue(String fieldName, Object value, ValidateField annotation) {
        if (!BaseUtil.isNull(String.valueOf(value))
            && value instanceof Number number
            && number.floatValue() > annotation.maxValue()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_MAX_VALUE),
                    fieldName, annotation.maxValue()));
        }
    }

    /**
     * 危険物輸送車種を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateHazardousMaterialVehicleType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.hazardousMaterialVehicleType()
            && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.HAZARDOUS_MATERIAL_VEHICLE_TYPE.contains(String.valueOf(value).trim())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_HAZARDOUS_MATERIAL_VEHICLE_TYPE),
                    fieldName));
        }
    }

    /**
     * パワーゲートを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validatePowerGate(String fieldName, Object value, ValidateField annotation) {
        if (annotation.powerGate()
            && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.POWER_GATE_TYPE.contains(String.valueOf(value).trim())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_POWER_GATE_TYPE),
                    fieldName));
        }
    }

    /**
     * クレーン装備を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateCraneEquipped(String fieldName, Object value, ValidateField annotation) {
        if (annotation.craneEquipped()
            && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.CRANE_EQUIPPED_TYPE.contains(String.valueOf(value).trim())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_CRANE_EQUIPPED_TYPE),
                    fieldName));
        }
    }

    /**
     * 上昇温度フィールドを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateUpperTempField(String fieldName, Object value, ValidateField annotation, Object obj)
        throws IllegalAccessException {
        if (value != null && !annotation.upperTemperatureField().isEmpty()) {
            try {
                Field upperTempField = obj.getClass().getDeclaredField(annotation.upperTemperatureField());
                upperTempField.setAccessible(true);
                Object uppTemplObj = upperTempField.get(obj);
                BigDecimal upperTempValue = getBigDecimalValue(uppTemplObj, getJsonPropertyValue(upperTempField));
                BigDecimal lowerTempValue = getBigDecimalValue(value, fieldName);

                if (upperTempValue == null || lowerTempValue == null) {
                    return;
                }

                if (lowerTempValue.compareTo(upperTempValue) > 0) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_MAX_LWR_TMP),
                            fieldName, getJsonPropertyValue(upperTempField)));
                }
            } catch (NoSuchFieldException e) {
                // Do nothing
            }
        }
    }

    /**
     * 翼ドアを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateWingDoor(String fieldName, Object value, ValidateField annotation) {
        if (annotation.wingDoor()
            && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.WING_DOOR_TYPE.contains(String.valueOf(value).trim())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_WING_DOOR_TYPE),
                    fieldName));
        }
    }

    /**
     * 冷凍装置を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateRefrigerationUnit(String fieldName, Object value, ValidateField annotation) {
        if (annotation.refrigerationUnit()
            && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.REFRIGERATION_UNIT_TYPE.contains(String.valueOf(value).trim())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_REFRIGERATION_UNIT_TYPE),
                    fieldName));
        }
    }

    /**
     * 寸法コードを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateDimensionCode(String fieldName, Object value, ValidateField annotation) {
        if (annotation.dimensionCode()
            && !BaseUtil.isNull(String.valueOf(value))) {
            if (value.toString().length() > ParamConstant.DIMENSION_PACKAGE_LENGTH) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_MAX_LENGTH),
                        fieldName, ParamConstant.DIMENSION_PACKAGE_LENGTH));
            }
            if (!value.toString().trim().matches(ParamConstant.REGEX_DIMENSION_CODE)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_PARAM),
                        fieldName));
            }

        }
    }

    /**
     * BigDecimal値を取得する。<BR>
     *
     * @param value     値
     * @param fieldName フィールド名
     * @return BigDecimal値
     */
    private BigDecimal getBigDecimalValue(Object value, String fieldName) {
        if (value == null || (value instanceof String str && BaseUtil.isNull(str.trim()))) {
            return null;
        }

        try {
            return new BigDecimal(String.valueOf(value));
        } catch (NumberFormatException e) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_NUMERIC),
                    fieldName));
        }
    }

    /**
     * 登録番号を検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateRegistrationNumber(String fieldName, Object value, ValidateField annotation) {
        if (annotation.registrationNumber() && !value.toString().matches(ParamConstant.REGEX_REGISTRATION_NUMBER)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_PARAM),
                    fieldName));
        }
    }

    /**
     * BigDecimalの符号を削除する。<BR>
     *
     * @param bigDecimal BigDecimal
     * @return 符号削除後の文字列
     */
    private String removeBigDecimalSign(BigDecimal bigDecimal) {
        String plainValue = bigDecimal.toPlainString();
        return plainValue.replace(CharacterSign.DOT, "").replace(CharacterSign.NEGATIVE_SIGN, "");
    }

    /**
     * パッケージコードを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validatePackageCode(String fieldName, Object value, ValidateField annotation) {
        if (annotation.packageCode()
            && !BaseUtil.isNull(String.valueOf(value))) {
            if (value.toString().length() > ParamConstant.DIMENSION_PACKAGE_LENGTH) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_MAX_LENGTH),
                        fieldName, ParamConstant.DIMENSION_PACKAGE_LENGTH));
            }
            if (!value.toString().trim().matches(ParamConstant.REGEX_PACKAGE_CODE)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_PARAM),
                        fieldName));
            }
        }
    }

    /**
     * 最小フィールドを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateMinField(String fieldName, Object value, ValidateField annotation, Object obj) {
        if (!BaseUtil.isNull(annotation.minField()) && !BaseUtil.isNull(String.valueOf(value))) {
            try {
                Field minField = obj.getClass().getDeclaredField(annotation.minField());
                minField.setAccessible(true);
                BigDecimal minFieldValue = (BigDecimal) minField.get(obj);
                if (!BaseUtil.isNull(String.valueOf(minFieldValue))
                    && value instanceof BigDecimal bigDecimal
                    && bigDecimal.compareTo(minFieldValue) < 0) {
                    throw new NextWebException(
                        new NextAPIError(
                            HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_MIN_MAX_VALUE),
                            fieldName,
                            getJsonPropertyValue(minField)));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(MessageConstant.System.SYSTEM_ERR_001),
                        fieldName));
            }
        }
    }

    /**
     * POJOかどうかを検証する。<BR>
     *
     * @param clazz クラス
     * @return POJOかどうか
     */
    private boolean isPojo(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        // Check if it's a system class or common type that should be skipped
        return !(clazz.isPrimitive()
            || clazz.equals(String.class)
            || clazz.equals(Boolean.class)
            || clazz.equals(Character.class)
            || Number.class.isAssignableFrom(clazz)
            || clazz.isEnum()
            || clazz.isArray()
            || Collection.class.isAssignableFrom(clazz)
            || Map.class.isAssignableFrom(clazz)
            || clazz.getPackageName().startsWith("java.")
            || clazz.getPackageName().startsWith("javax.")
            || clazz.getPackageName().startsWith("sun.")
            || clazz.getPackageName().startsWith("com.sun.")
            || clazz.getPackageName().startsWith("org.w3c.")
            || clazz.getPackageName().startsWith("org.xml."));
    }

    /**
     * 事故カテゴリを検証する。<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateIncidentCategory(String fieldName, Object value, ValidateField annotation) {
        if (annotation.incidentCategory() && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.INCIDENT_CATEGORY.contains(value.toString())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_INCIDENT_CATEGORY),
                    fieldName));
        }

        if (annotation.incidentType() && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.INCIDENT_TYPE.contains(value.toString())) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_INCIDENT_TYPE),
                    fieldName));
        }
    }

}
