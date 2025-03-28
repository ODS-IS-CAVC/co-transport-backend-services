package jp.co.nlj.ix.mapper;

import com.next.logistic.util.BaseUtil;
import java.lang.reflect.Field;
import java.util.Objects;
import jp.co.nlj.ix.constant.MapperConstants;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * BaseMapperインターフェース ベースマッパーインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface BaseMapper {

    @Named(MapperConstants.TRIM_VALUE)
    static String trimValue(String val) {
        if (BaseUtil.isNull(val)) {
            return null;
        }
        return val.trim();
    }

    @Named(MapperConstants.NUMBER_TO_INTEGER)
    static Integer numberToInter(Number number) {
        if (Objects.isNull(number)) {
            return null;
        }
        return number.intValue();
    }

    @BeforeMapping
    default <T> void trimBeforeMapping(T source) {
        if (source == null) {
            return;
        }
        try {
            for (Field field : source.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType().equals(String.class)) {
                    String value = (String) field.get(source);
                    if (value != null) {
                        String trimmedValue = value.trim();
                        field.set(source, trimmedValue.isEmpty() ? null : trimmedValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}