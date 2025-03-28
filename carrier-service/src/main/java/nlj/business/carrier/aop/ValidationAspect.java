package nlj.business.carrier.aop;

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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import nlj.business.carrier.aop.proxy.ValidateField;
import nlj.business.carrier.constant.DataBaseConstant;
import nlj.business.carrier.constant.MessageConstant.Validate;
import nlj.business.carrier.constant.ParamConstant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * バリデーションアスペクトクラス.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Aspect
@Component
@Order(1)
public class ValidationAspect {

    /**
     * メソッド引数のバリデーション.<BR>
     *
     * @param joinPoint 結合ポイント
     * @throws IllegalAccessException 不正アクセス例外
     */
    @Before("execution(* nlj.business.carrier..*(..))")
    public void validateMethodArguments(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg != null) {
                validateFields(arg);
            }
        }
    }

    /**
     * フィールドのバリデーション.<BR>
     *
     * @param obj オブジェクト
     * @throws IllegalAccessException 不正アクセス例外
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
     * フィールドのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateValue(String fieldName, Object value, ValidateField annotation, Object obj) {
        validateNotNull(fieldName, value, annotation);
        validateStringLength(fieldName, value, annotation);
        validateNumericPrecisionAndScale(fieldName, value, annotation);
        validatePositiveInteger(fieldName, value, annotation);
        validateDateFormat(fieldName, value, annotation, obj);
        validateTimeFormat(fieldName, value, annotation);
        validateHalfWidth(fieldName, value, annotation);
        validateDateCompareField(fieldName, annotation, value);
        validateCorrectionCode(fieldName, value, annotation);
        validateContainsSpecialCharacter(fieldName, value, annotation);
        validateInputIsArrayOrBoolean(fieldName, value, annotation);
        validateHourFormat(fieldName, value, annotation);
        validateVehicleType(fieldName, value, annotation);
        validateVanBodyType(fieldName, value, annotation);
        validateRepeatDay(fieldName, value, annotation);
        validateRoundTrip(fieldName, value, annotation);
        validateRoundTripDirection(fieldName, value, annotation);
        validateOriginType(fieldName, value, annotation);
        validateStatusDiagram(fieldName, value, annotation);
        validateAssignType(fieldName, value, annotation);
        validateCarrierVehicleType(fieldName, value, annotation);
        validateCarrierStatus(fieldName, value, annotation);
        validateArrayInput(fieldName, value, annotation);
        validateCarrierDeleteFlag(fieldName, value, annotation);
        validateAllowedEnumValues(fieldName, value, annotation);
    }

    /**
     * フィールドのバリデーション.<BR>
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
     * 文字列の長さのバリデーション.<BR>
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
     * 数値の精度とスケールのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateNumericPrecisionAndScale(String fieldName, Object value, ValidateField annotation) {
        if (annotation.precision() > 0 && value != null) {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
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
     * 正の整数のバリデーション.<BR>
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
     * 日付のバリデーション.<BR>
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
     * 終了日のバリデーション.<BR>
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
            try {
                Field endDateField = obj.getClass().getDeclaredField(annotation.endDateField());
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
                        validateEndTime(annotation, obj);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle exception or log error
            } catch (ParseException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                        fieldName, annotation.dateFormat()));
            }
        }
    }

    /**
     * 終了時間のバリデーション.<BR>
     *
     * @param annotation アノテーション
     * @param obj        オブジェクト
     * @throws NoSuchFieldException   存在しないフィールド例外
     * @throws IllegalAccessException 不正アクセス例外
     */
    private void validateEndTime(ValidateField annotation, Object obj)
        throws NoSuchFieldException, IllegalAccessException {
        if (!BaseUtil.isNull(annotation.startTimeField()) && !BaseUtil.isNull(annotation.endTimeField())) {
            Field startTimeField = obj.getClass().getDeclaredField(annotation.startTimeField());
            Field endTimeField = obj.getClass().getDeclaredField(annotation.endTimeField());
            startTimeField.setAccessible(true);
            endTimeField.setAccessible(true);

            String startTimeValue = (String) startTimeField.get(obj);
            String endTimeValue = (String) endTimeField.get(obj);

            validateTimeFormat(getJsonPropertyValue(startTimeField), startTimeValue,
                startTimeField.getAnnotation(ValidateField.class));
            validateTimeFormat(getJsonPropertyValue(endTimeField), endTimeValue,
                endTimeField.getAnnotation(ValidateField.class));

            LocalTime starLocalTime = LocalTime.parse(startTimeValue, DateTimeFormatter.ofPattern(
                startTimeField.getAnnotation(ValidateField.class).timeFormat()
            ));
            LocalTime endLocalTime = LocalTime.parse(endTimeValue, DateTimeFormatter.ofPattern(
                endTimeField.getAnnotation(ValidateField.class).timeFormat()
            ));

            if (starLocalTime.isAfter(endLocalTime) || starLocalTime.equals(endLocalTime)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_START_TIME_AFTER_END_TIME),
                        getJsonPropertyValue(startTimeField), getJsonPropertyValue(endTimeField)));
            }
        }
    }

    /**
     * 時間フォーマットのバリデーション.<BR>
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
     * JSONプロパティの値を取得.<BR>
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
     * 半角のバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
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
     * 特殊文字を含むバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
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
     * 配列またはブール値のバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
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
     * 日付比較フィールドのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param annotation アノテーション
     * @param value      値
     */
    private void validateDateCompareField(String fieldName, ValidateField annotation, Object value) {
        if (!annotation.dateCompareField().isEmpty() && value instanceof String str && !((String) value).isEmpty()) {
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
     * 修正コードのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateCorrectionCode(String fieldName, Object value, ValidateField annotation) {
        if (annotation.correctionCode() && value instanceof String str) {
            if (!ParamConstant.CORRECTION_CODE.contains(str)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_CORRECTION_CODE),
                        fieldName));
            }
        }
    }

    /**
     * 時間フォーマットのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
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
     * 車両タイプのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateVehicleType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.vehicleType() && !ParamConstant.VEHICLE_TYPE.contains(String.valueOf(value))) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_VEHICLE_TYPE),
                    fieldName));
        }
    }

    /**
     * バンボディタイプのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
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
     * 繰り返し日のバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateRepeatDay(String fieldName, Object value, ValidateField annotation) {
        if (annotation.repeatDay()) {
            if (value instanceof Integer || value instanceof Number) {
                int numValue = ((Number) value).intValue();
                if (!ParamConstant.REPEAT_DAY.contains(String.valueOf(numValue))) {
                    NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_REPEAT_DAY, fieldName);
                }
            }
        }
    }

    /**
     * 往復のバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateRoundTrip(String fieldName, Object value, ValidateField annotation) {
        if (annotation.roundTrip()) {
            if (value instanceof Integer || value instanceof Number) {
                int numValue = ((Number) value).intValue();
                if (!ParamConstant.ROUND_TRIP.contains(String.valueOf(numValue))) {
                    NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_IS_ROUND_TRIP, fieldName);
                }
            }
        }
    }

    /**
     * 原点タイプのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateOriginType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.originType()) {
            if (value instanceof Integer || value instanceof Number) {
                int numValue = ((Number) value).intValue();
                if (!ParamConstant.ORIGIN_TYPE.contains(String.valueOf(numValue))) {
                    NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_ORIGIN_TYPE, fieldName);
                }
            }
        }
    }

    /**
     * ステータスダイアグラムのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateStatusDiagram(String fieldName, Object value, ValidateField annotation) {
        if (annotation.statusVehicleDiagram() && (!BaseUtil.isNull(String.valueOf(value)))
            && (!ParamConstant.STATUS_VEHICLE_DIAGRAM.contains(String.valueOf(value)))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_STATUS_DIAGRAM, fieldName);
        }
    }

    /**
     * キャリア車両タイプのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateCarrierVehicleType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.carrierVehicleType() && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.CARRIER_VEHICLE_TYPE.contains(String.valueOf(value))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_CARRIER_VEHICLE_TYPE, fieldName);
        }
    }

    /**
     * キャリアステータスのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateCarrierStatus(String fieldName, Object value, ValidateField annotation) {
        if (annotation.carrierStatus() && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.CARRIER_STATUS.contains(String.valueOf(value))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_ROUND_TRIP_DIRECTION, fieldName);
        }
    }

    /**
     * キャリア削除フラグのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateCarrierDeleteFlag(String fieldName, Object value, ValidateField annotation) {
        if (annotation.carrierDeleteFlag() && !BaseUtil.isNull(String.valueOf(value))
            && !ParamConstant.CARRIER_DELETE_FLAG.contains(String.valueOf(value))) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_DELETE_FLAG_NOT_FOUND, fieldName);
        }
    }

    /**
     * 往復方向のバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateRoundTripDirection(String fieldName, Object value, ValidateField annotation) {
        if (annotation.roundTripDirection()) {
            if (value instanceof Integer || value instanceof Number) {
                int numValue = ((Number) value).intValue();
                if (!ParamConstant.ROUND_TRIP_DIRECTION.contains(String.valueOf(numValue))) {
                    NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_ROUND_TRIP_DIRECTION,
                        fieldName);
                }
            }
        }
    }

    /**
     * 割り当てタイプのバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateAssignType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.assignType() && !ParamConstant.ASSIGN_TYPE.contains(String.valueOf(value))) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_ASSIGN_TYPE),
                    fieldName));
        }
    }

    /**
     * 配列入力のバリデーション.<BR>
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateArrayInput(String fieldName, Object value, ValidateField annotation) {
        if (annotation.arrayInput() && !Objects.isNull(value)) {
            if (value instanceof List<?> list) {
                for (Object element : list) {
                    if (!(element instanceof Integer)) {
                        NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_ARRAY_INTEGER,
                            fieldName);
                    }
                }
                return;
            }
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_ARRAY_INTEGER, fieldName);
        }
    }

    /**
     * 許可された列挙値のバリデーション.<BR>
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
                        Arrays.asList(annotation.allowedEnumValues())));
            }
        }
    }
}
