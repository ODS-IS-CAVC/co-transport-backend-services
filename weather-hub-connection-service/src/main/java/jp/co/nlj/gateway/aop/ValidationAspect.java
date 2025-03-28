package jp.co.nlj.gateway.aop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import jp.co.nlj.gateway.aop.proxy.ValidateField;
import jp.co.nlj.gateway.constant.DataBaseConstant;
import jp.co.nlj.gateway.constant.MessageConstant.Validate;
import jp.co.nlj.gateway.constant.ParamConstant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * ValidationAspectクラスは、メソッド引数のバリデーションを実行するためのクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Aspect
@Component
@Order(1)
public class ValidationAspect {

    /**
     * メソッド引数のバリデーションを実行する。
     *
     * @param joinPoint メソッドの結合点
     * @throws IllegalAccessException アクセス権限の例外
     */
    @Before("execution(* jp.co.nlj.gateway..*(..))")
    public void validateMethodArguments(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg != null) {
                validateFields(arg);
            }
        }
    }

    /**
     * オブジェクトのフィールドをバリデーションする。
     *
     * @param obj バリデーション対象のオブジェクト
     * @throws IllegalAccessException アクセス権限の例外
     */
    private void validateFields(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ValidateField.class)) {
                ValidateField annotation = field.getAnnotation(ValidateField.class);
                field.setAccessible(true);
                Object value = field.get(obj);
                String fieldName = getJsonPropertyValue(field);
                validateValue(fieldName, value, annotation, obj);
            }
        }
    }

    /**
     * フィールドの値をバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     * @param obj        バリデーション対象のオブジェクト
     * @throws IllegalAccessException アクセス権限の例外
     */
    private void validateValue(String fieldName, Object value, ValidateField annotation, Object obj)
        throws IllegalAccessException {
        validateNotNull(fieldName, value, annotation);
        validateStringLength(fieldName, value, annotation);
        validateNumericPrecisionAndScale(fieldName, value, annotation);
        validatePositiveInteger(fieldName, value, annotation);
        validateDateFormat(fieldName, value, annotation, obj);
        validateTimeFormat(fieldName, value, annotation);
        validateEndTime(fieldName, annotation, obj, value);
        validateHalfWidth(fieldName, value, annotation);
        validateDateCompareField(fieldName, annotation, value);
        validateCorrectionCode(fieldName, value, annotation, obj);
        validateContainsSpecialCharacter(fieldName, value, annotation);
        validateInputIsArrayOrBoolean(fieldName, value, annotation);
        validateHourFormat(fieldName, value, annotation);
        validateVehicleType(fieldName, value, annotation);
        validateVanBodyType(fieldName, value, annotation);
        validateTractorType(fieldName, value, annotation);
        validatePackingCode(fieldName, value, annotation);
    }

    /**
     * フィールドがnullでないことをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
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
     * 文字列の長さをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateStringLength(String fieldName, Object value, ValidateField annotation) {
        if (value instanceof String str) {
            if (str.length() < annotation.minLength()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_MIN_LENGTH),
                        fieldName, annotation.minLength())
                );
            }
            if (str.length() > annotation.maxLength()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_MAX_LENGTH),
                        fieldName, annotation.maxLength())
                );
            }
        }
    }

    /**
     * 数値の精度とスケールをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateNumericPrecisionAndScale(String fieldName, Object value, ValidateField annotation) {
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
            if (bigDecimal.compareTo(BigDecimal.ZERO) <= 0 && annotation.positiveNumber()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_POSITIVE_NUMBER),
                        fieldName, annotation.positiveNumber())
                );
            }
            if (bigDecimal.precision() > annotation.precision()) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_PRECISION),
                        fieldName, annotation.precision())
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
     * 正の整数であることをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validatePositiveInteger(String fieldName, Object value, ValidateField annotation) {
        if (value instanceof Integer integer && integer <= 0 && annotation.positiveNumber()) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_POSITIVE_NUMBER),
                    fieldName, annotation.positiveNumber())
            );
        }
        if (value instanceof String str && annotation.positiveText()) {
            try {
                int number = Integer.parseInt(str);
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
     * 日付のフォーマットをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     * @param obj        バリデーション対象のオブジェクト
     */
    private void validateDateFormat(String fieldName, Object value, ValidateField annotation, Object obj) {
        if (value instanceof String dateStr && !annotation.dateFormat().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(annotation.dateFormat());
                sdf.setLenient(false);
                Date currentDate = sdf.parse(dateStr);
                validateEndDate(fieldName, annotation, obj, sdf, currentDate);
            } catch (ParseException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                        fieldName, annotation.dateFormat())
                );
            }
        }
    }

    /**
     * 終了日付をバリデーションする。
     *
     * @param fieldName   フィールド名
     * @param annotation  バリデーションアノテーション
     * @param obj         バリデーション対象のオブジェクト
     * @param sdf         日付フォーマッタ
     * @param currentDate 現在の日付
     */
    private void validateEndDate(String fieldName, ValidateField annotation, Object obj, SimpleDateFormat sdf,
        Date currentDate) {
        if (!annotation.endDateField().isEmpty()) {
            Field endDateField = null;
            try {
                endDateField = obj.getClass().getDeclaredField(annotation.endDateField());
                endDateField.setAccessible(true);
                String endDateStr = (String) endDateField.get(obj);

                if (endDateStr != null) {
                    Date endDate = sdf.parse(endDateStr);
                    if (currentDate.after(endDate)) {
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
            } catch (ParseException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                        getJsonPropertyValue(endDateField), annotation.dateFormat()));
            }
        }
    }

    /**
     * 開始時刻と終了時刻をバリデーションする。
     *
     * @param annotation バリデーションアノテーション
     * @param obj        バリデーション対象のオブジェクト
     * @throws NoSuchFieldException   フィールドが存在しない場合の例外
     * @throws IllegalAccessException アクセス権限の例外
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
     * 時刻のフォーマットをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateTimeFormat(String fieldName, Object value, ValidateField annotation) {
        if (value instanceof String timeStr && !annotation.timeFormat().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(annotation.timeFormat());
                LocalTime.parse(timeStr, formatter);
            } catch (Exception e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                        fieldName, annotation.timeFormat())
                );
            }
        }
    }

    /**
     * 終了時刻をバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param annotation バリデーションアノテーション
     * @param obj        バリデーション対象のオブジェクト
     * @param value      フィールドの値
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
     * 時刻を比較する。
     *
     * @param startTime       開始時刻
     * @param endTime         終了時刻
     * @param startTimeFormat 開始時刻のフォーマット
     * @param endTimeFormat   終了時刻のフォーマット
     * @return 開始時刻が終了時刻より後であるか
     */
    private boolean compareTime(String startTime, String endTime, String startTimeFormat, String endTimeFormat) {
        LocalTime starLocalTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern(startTimeFormat));
        LocalTime endLocalTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern(endTimeFormat));
        return starLocalTime.isAfter(endLocalTime);
    }

    /**
     * JSONプロパティの値を取得する。
     *
     * @param field フィールド
     * @return JSONプロパティの値
     */
    private String getJsonPropertyValue(Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if (jsonProperty != null && !jsonProperty.value().isEmpty()) {
            return jsonProperty.value();
        }
        return field.getName();
    }

    /**
     * 半角文字をバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateHalfWidth(String fieldName, Object value, ValidateField annotation) {
        if (value instanceof String str && annotation.numberContainSpecialChar()) {
            String regex = "^[0-9()\\-+]+$";
            if (!str.matches(regex)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_HALF_WIDTH),
                        fieldName));
            }
        }
    }

    /**
     * 特殊文字を含むかどうかをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateContainsSpecialCharacter(String fieldName, Object value, ValidateField annotation) {
        if (annotation.textContainSpecialChar() && value instanceof String str) {
            String containsLetterPattern = ".*[a-zA-Z].*";
            String containsSpecialCharPattern = ".*[^a-zA-Z0-9].*";
            if (str.matches(containsLetterPattern) || str.matches(containsSpecialCharPattern)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_CONTAIN_SPECIAL_CHARACTER),
                        fieldName));
            }
        }
    }

    /**
     * 入力が配列またはブール値であるかをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateInputIsArrayOrBoolean(String fieldName, Object value, ValidateField annotation) {
        if (annotation.inputIsArrOrBoolean() && value instanceof String str) {
            String arrayPattern = "\\[\\d+(,\\d+)*\\]";
            if (str.matches(arrayPattern) || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_ARRAY_OR_BOOLEAN),
                        fieldName));
            }
        }
    }

    /**
     * 日付の比較フィールドをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param annotation バリデーションアノテーション
     * @param value      フィールドの値
     */
    private void validateDateCompareField(String fieldName, ValidateField annotation, Object value) {
        if (!annotation.dateCompareField().isEmpty() && value instanceof String str) {
            try {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
                LocalDate inputDate = LocalDate.parse(str, formatter);
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
     * 修正コードをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     * @param obj        バリデーション対象のオブジェクト
     */
    private void validateCorrectionCode(String fieldName, Object value, ValidateField annotation, Object obj) {
        if (annotation.correctionCode() && value instanceof String str) {
            if (!ParamConstant.CORRECTION_CODE.contains(str)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_CORRECTION_CODE),
                        fieldName));
            }

            if (str.equals(ParamConstant.CorrectionCode.CANCEL) && !annotation.correctionReasonField().isEmpty()) {
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
     * 時間フォーマットをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateHourFormat(String fieldName, Object value, ValidateField annotation) {
        if (annotation.hourFormat() && value instanceof String str) {
            String regex = "^(0[0-9]|1[0-9]|2[0-3])$";
            if (!str.matches(regex)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_HOUR_FORMAT),
                        fieldName));
            }
        }
    }

    /**
     * 車両タイプをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateVehicleType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.vehicleType() && value instanceof String str) {
            if (!ParamConstant.VEHICLE_TYPE.contains(str)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_VEHICLE_TYPE),
                        fieldName));
            }
        }
    }

    /**
     * バンボディタイプをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateVanBodyType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.vanBodyType() && value instanceof String str) {
            if (!ParamConstant.FLATBED_VAN_BODY_TYPE.contains(str)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_VAN_BODY_TYPE),
                        fieldName));
            }
        }
    }

    /**
     * トラクタータイプをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     */
    private void validateTractorType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.tractorType() && value instanceof String str) {
            if (!ParamConstant.TRACTOR.contains(str.trim())) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_TRACTOR_TYPE),
                        fieldName));
            }
        }
    }

    /**
     * Packingコードをバリデーションする。
     *
     * @param fieldName  フィールド名
     * @param value      フィールドの値
     * @param annotation バリデーションアノテーション
     * @throws IllegalAccessException アクセス権限の例外
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
}
