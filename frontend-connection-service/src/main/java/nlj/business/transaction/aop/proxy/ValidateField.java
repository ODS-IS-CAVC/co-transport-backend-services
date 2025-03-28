package nlj.business.transaction.aop.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <PRE>
 * フィールドのバリデーションアノテーション。<BR>
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

    String notEqualField() default "";

    boolean transOrderSearchStatus() default false;

    boolean typeIntegerArray() default false;

    boolean positiveText() default false;

    boolean transType() default false;

    String[] transTypeShipperRequiredFields() default {};

    String[] allowValue() default {};

    boolean greaterThanOrEqualZero() default false;
}
