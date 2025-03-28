package nlj.business.carrier.link.mapper;

import com.next.logistic.util.BaseUtil;
import java.lang.reflect.Field;
import java.util.Objects;
import nlj.business.carrier.link.constant.MapperConstants;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * ベースマッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface BaseMapper {

    /**
     * <PRE>
     * 数値を整数に変換する.<BR>
     * </PRE>
     *
     * @param number 数値
     * @return 整数
     */
    @Named(MapperConstants.NUMBER_TO_INTEGER)
    static Integer numberToInter(Number number) {
        if (Objects.isNull(number)) {
            return null;
        }
        return number.intValue();
    }

    /**
     * <PRE>
     * 文字列の前後の空白を削除する.<BR>
     * </PRE>
     *
     * @param val 文字列
     * @return 削除後の文字列
     */
    @Named(MapperConstants.TRIM_VALUE)
    static String trimValue(String val) {
        if (BaseUtil.isNull(val)) {
            return null;
        }
        return val.trim();
    }

    /**
     * <PRE>
     * マッピング前に文字列の前後の空白を削除する.<BR>
     * </PRE>
     *
     * @param source ソース
     */
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