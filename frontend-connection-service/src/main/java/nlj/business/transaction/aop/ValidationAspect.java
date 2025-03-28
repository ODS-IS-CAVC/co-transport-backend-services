package nlj.business.transaction.aop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.constant.MessageConstant.Validate;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.dto.request.CommonPagingSearch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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
     * メソッド引数のバリデーション。<BR>
     *
     * @param joinPoint ジョインポイント
     * @throws IllegalAccessException アクセス例外
     */
    @Before("execution(* nlj.business.transaction..*(..))")
    public void validateMethodArguments(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg != null) {
                validateFields(arg);
            }
        }
    }

    /**
     * フィールドのバリデーション。<BR>
     *
     * @param obj オブジェクト
     * @throws IllegalAccessException アクセス例外
     */
    private void validateFields(Object obj) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredFields()));
        if (obj.getClass().getSuperclass().equals(CommonPagingSearch.class)) {
            fields.addAll(Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields()));
        }
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
        validateNotEqualField(fieldName, value, annotation, obj);
        validateTransOrderSearchStatus(fieldName, value, annotation);
        validateTypeIntegerArray(fieldName, value, annotation);
        validateAllowValue(fieldName, value, annotation);
        validateTransType(fieldName, value, annotation);
        validateTransTypeShipperField(fieldName, value, annotation, obj);
        validateGreaterOrEqualZero(fieldName, value, annotation);
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
     * 文字列の長さバリデーション
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
     * 数値の精度とスケールバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateNumericPrecisionAndScale(String fieldName, Object value, ValidateField annotation) {
        if (annotation.precision() > 0 && value != null) {
            BigDecimal bigDecimal = null;
            try {
                bigDecimal = new BigDecimal(String.valueOf(value));
            } catch (NumberFormatException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_NUMBER),
                        fieldName)
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
     * 正の整数バリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validatePositiveInteger(String fieldName, Object value, ValidateField annotation) {
        if (value instanceof Integer integer && integer <= 0 && annotation.positiveNumber()) {
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
     * 日付バリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateDateFormat(String fieldName, Object value, ValidateField annotation, Object obj) {
        if (value instanceof String dateStr && !annotation.dateFormat().isEmpty() && !BaseUtil.isNull(dateStr)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(annotation.dateFormat());
                sdf.setLenient(false);
                Date currentDate = sdf.parse(dateStr);
                validateEndDate(fieldName, annotation, obj, sdf, currentDate);
            } catch (ParseException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST, SoaResponsePool.get(Validate.VALID_DATE_FORMAT), fieldName,
                        annotation.dateFormat())
                );
            }
        }
    }

    /**
     * 終了日バリデーション
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

                if (!BaseUtil.isNull(endDateStr)) {
                    Date endDate = sdf.parse(endDateStr);
                    if (currentDate.after(endDate)) {
                        throw new NextWebException(
                            new NextAPIError(
                                HttpStatus.BAD_REQUEST, SoaResponsePool.get(Validate.VALID_START_DATE_AFTER_END_DATE),
                                fieldName));
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
     * 時間バリデーション
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
     * JSONプロパティ値を取得
     *
     * @param field フィールド
     * @return JSONプロパティ値
     */
    private String getJsonPropertyValue(Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if (jsonProperty != null && !jsonProperty.value().isEmpty()) {
            return jsonProperty.value();
        }
        return field.getName();
    }

    /**
     * 等しくないフィールドバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateNotEqualField(String fieldName, Object value, ValidateField annotation, Object obj) {
        if (!BaseUtil.isNull(annotation.notEqualField()) && value instanceof String fieldValue) {
            try {
                Field comparedField = obj.getClass().getDeclaredField(annotation.notEqualField());
                comparedField.setAccessible(true);
                Object comparedObject = comparedField.get(obj);
                if (comparedObject instanceof String comparedValue && !BaseUtil.isNull(comparedValue)
                    && fieldValue.equalsIgnoreCase(comparedValue)) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_NOT_EQUAL_FIELD),
                            fieldName, getJsonPropertyValue(comparedField))
                    );
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 転送注文検索ステータスバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @throws IllegalAccessException アクセス例外
     */
    private void validateTransOrderSearchStatus(String fieldName, Object value, ValidateField annotation)
        throws IllegalAccessException {
        if (annotation.transOrderSearchStatus() && value instanceof String fieldValue && !BaseUtil.isNull(fieldValue)
            && !ParamConstant.TransOrderSearchStatus.getListStatus().contains(fieldValue)) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_SHIPPER_TRANS_SEARCH_STATUS),
                    fieldName, BaseUtil.listToString(ParamConstant.TransOrderSearchStatus.getListStatus(), ","))
            );
        }
    }

    /**
     * 整数配列バリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateTypeIntegerArray(String fieldName, Object value, ValidateField annotation) {
        if (annotation.typeIntegerArray() && value instanceof String fieldValue && !BaseUtil.isNull(fieldValue)) {
            try {
                fieldValue = fieldValue.replaceAll("[{}]", "").trim();
                String[] strArray = fieldValue.split(",");
                for (int i = 0; i < strArray.length; i++) {
                    Integer.parseInt(strArray[i].trim());
                }
            } catch (Exception e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_INTEGER_ARRAY),
                        fieldName)
                );
            }
        }
    }

    /**
     * 許可値バリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateAllowValue(String fieldName, Object value, ValidateField annotation) {
        if (value instanceof String valueStr && !BaseUtil.isNull(valueStr)
            && annotation.allowValue().length > 0) {
            List<String> allowValues = Arrays.stream(annotation.allowValue()).toList();
            if (!allowValues.contains(valueStr)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_ALLOW_VALUE),
                        fieldName, BaseUtil.listToString(allowValues, ",")));
            }
        }
    }

    /**
     * 転送タイプバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    private void validateTransType(String fieldName, Object value, ValidateField annotation) {
        if (annotation.transType() && !BaseUtil.isNull(value.toString())) {
            List<Integer> transType = ParamConstant.TransType.getListStatus();
            boolean isValid = false;

            try {
                Integer inputTransType = Integer.parseInt(value.toString());
                if (transType.contains(inputTransType)) {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                //do nothing
            }

            if (!isValid) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_TRANS_TYPE),
                        fieldName, BaseUtil.listToString(transType.stream().map(String::valueOf).toList(), ","))

                );
            }
        }
    }

    /**
     * 転送タイプのシッパー必須フィールドバリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     * @param obj        オブジェクト
     */
    private void validateTransTypeShipperField(String fieldName, Object value, ValidateField annotation, Object obj) {
        if (annotation.transType() && annotation.transTypeShipperRequiredFields().length > 0
            && !BaseUtil.isNull(value.toString())) {
            String inputValue = value.toString();
            boolean isShipperType = inputValue.equals(ParamConstant.TransType.CARRIER_SHIPPER.toString());

            if (isShipperType) {
                for (String requiredFieldName : annotation.transTypeShipperRequiredFields()) {
                    try {
                        Field field = obj.getClass().getDeclaredField(requiredFieldName);
                        field.setAccessible(true);
                        String fieldValue = (String) field.get(obj);
                        if (BaseUtil.isNull(fieldValue)) {
                            throw new NextWebException(
                                new NextAPIError(HttpStatus.BAD_REQUEST,
                                    SoaResponsePool.get(Validate.VALID_NOT_NULL),
                                    getJsonPropertyValue(field))
                            );
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 0以上バリデーション
     *
     * @param fieldName  フィールド名
     * @param value      値
     * @param annotation アノテーション
     */
    public void validateGreaterOrEqualZero(String fieldName, Object value, ValidateField annotation) {
        if (value != null && annotation.greaterThanOrEqualZero()) {
            try {
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(value).trim());
                if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
                    throw new NextWebException(
                        new NextAPIError(HttpStatus.BAD_REQUEST,
                            SoaResponsePool.get(Validate.VALID_POSITIVE_NUMBER),
                            fieldName, annotation.positiveNumber())
                    );
                }
            } catch (NumberFormatException e) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_NUMBER),
                        fieldName)
                );
            }
        }
    }
}
