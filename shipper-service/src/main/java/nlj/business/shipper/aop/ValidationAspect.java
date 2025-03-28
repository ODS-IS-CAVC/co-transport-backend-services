package nlj.business.shipper.aop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import nlj.business.shipper.aop.proxy.ValidateField;
import nlj.business.shipper.constant.MessageConstant.Validate;
import nlj.business.shipper.constant.ParamConstant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * バリデーションアスペクト。<BR>
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
    @Before("execution(* nlj.business.shipper..*(..))")
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
        validateNumericPrecisionAndScale(fieldName, value, annotation);
        validatePositiveInteger(fieldName, value, annotation);
        validateDateFormat(fieldName, value, annotation, obj);
        validateTimeFormat(fieldName, value, annotation);
        validateEndTime(fieldName, annotation, obj, value);
        validateAllowedEnumValues(fieldName, value, annotation);
        validateFileType(fieldName, value, annotation);
    }

    /**
     * 値がnullでないかバリデーション
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
     * 文字列の長さをバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
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
     * 数値の精度とスケールをバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
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
            if (bigDecimal.compareTo(BigDecimal.ZERO) < 0 && annotation.positiveNumber()) {
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
     * 正の整数をバリデーション
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
     * 日付形式をバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
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
     * 終了日をバリデーション
     *
     * @param fieldName   フィールド名
     * @param annotation  アノテーション
     * @param obj         オブジェクト
     * @param sdf         日付フォーマット
     * @param currentDate 現在日付
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
     * 開始時間と終了時間をバリデーション
     *
     * @param annotation アノテーション
     * @param obj        オブジェクト
     * @throws NoSuchFieldException   フィールドが存在しない例外
     * @throws IllegalAccessException アクセス例外
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
     * 時間形式をバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
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
     * 終了時間をバリデーション
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
     * 時間比較
     *
     * @param startTime       開始時間
     * @param endTime         終了時間
     * @param startTimeFormat 開始時間フォーマット
     * @param endTimeFormat   終了時間フォーマット
     * @return 開始時間が終了時間より後かどうか
     */
    private boolean compareTime(String startTime, String endTime, String startTimeFormat, String endTimeFormat) {
        LocalTime starLocalTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern(startTimeFormat));
        LocalTime endLocalTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern(endTimeFormat));
        return starLocalTime.isAfter(endLocalTime);
    }

    /**
     * プロパティ値を取得
     *
     * @param field フィールド
     * @return プロパティ値
     */
    private String getJsonPropertyValue(Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if (jsonProperty != null && !jsonProperty.value().isEmpty()) {
            return jsonProperty.value();
        }
        return field.getName();
    }

    /**
     * 許可された列挙値をバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateAllowedEnumValues(String fieldName, Object value, ValidateField annotation) {
        if (annotation.allowedEnumValues().length > 0 && value instanceof String str) {
            if (!Arrays.asList(annotation.allowedEnumValues()).contains(str)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(Validate.VALID_ENUM_VALUE), fieldName,
                        value));
            }
        }
    }

    /**
     * ファイルタイプをバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateFileType(String fieldName, Object value, ValidateField annotation) {
        if (!annotation.fileType().isEmpty() && value instanceof MultipartFile file) {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals(annotation.fileType())) {
                throw new NextWebException(
                    new NextAPIError(
                        HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_FILE_TYPE),
                        file.getOriginalFilename(),
                        annotation.fileType()
                    )
                );
            }
            if (file.getSize() > annotation.maxFileSize()) {
                throw new NextWebException(
                    new NextAPIError(
                        HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_FILE_SIZE),
                        file.getOriginalFilename(),
                        annotation.maxFileSize() / (1024 * 1024) + "MB"
                    )
                );
            }
        }
    }

    /**
     * 荷物ステータスをバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateCargoStatus(String fieldName, Object value, ValidateField annotation) {
        if (annotation.cargoStatus() && !ParamConstant.CARGO_STATUS.contains(String.valueOf(value))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_CARGO_STATUS, fieldName);
        }
    }
}
