package nlj.business.shipper.aop.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <PRE>
 * フィールド検証アノテーション。<BR>
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

    boolean positiveText() default false;

    String dateFormat() default "";

    String endDateField() default "";

    String timeFormat() default "";

    String startTimeField() default "";

    String endTimeField() default "";

    String[] allowedEnumValues() default {};

    String fileType() default "";

    int maxFileSize() default 0;

    boolean cargoStatus() default false;
}
