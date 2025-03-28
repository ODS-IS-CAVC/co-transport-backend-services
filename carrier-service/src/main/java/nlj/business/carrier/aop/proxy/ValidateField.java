package nlj.business.carrier.aop.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <PRE>
 * フィールドのバリデーションアノテーション.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateField {

    boolean notNull() default false;

    int minLength() default 0;

    int maxLength() default Integer.MAX_VALUE;

    int precision() default 0;

    int scale() default Integer.MAX_VALUE;

    boolean positiveNumber() default true;

    String dateFormat() default "";

    String endDateField() default "";

    String timeFormat() default "";

    String startTimeField() default "";

    String endTimeField() default "";

    boolean textHalfWidth() default false;

    String dateCompareField() default "";

    boolean correctionCode() default false;

    boolean textContainSpecialChar() default false;

    boolean inputIsArrOrBoolean() default true;

    boolean textFullWidth() default false;

    boolean hourFormat() default false;

    boolean vehicleType() default false;

    boolean vanBodyType() default false;

    boolean textContainHyphen() default false;

    boolean numberContainSpecialChar() default false;

    boolean textFullWidthAndKanji() default false;

    boolean positiveText() default false;

    boolean repeatDay() default false;

    boolean roundTrip() default false;

    boolean originType() default false;

    boolean statusVehicleDiagram() default false;

    boolean roundTripDirection() default false;

    boolean assignType() default false;

    boolean carrierVehicleType() default false;

    boolean carrierStatus() default false;

    boolean carrierDeleteFlag() default false;

    boolean arrayInput() default false;

    String[] allowedEnumValues() default {};
}
