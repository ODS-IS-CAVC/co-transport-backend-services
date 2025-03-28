package nlj.business.carrier.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.next.logistic.convert.GenericMapToStringConverter;
import jakarta.persistence.Converter;
import java.util.Map;
import nlj.business.carrier.domain.opt.DayTime;

/**
 * <PRE>
 * 曜日コンバーター.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Converter
public class DayWeekConverter extends GenericMapToStringConverter<DayTime> {

    /**
     * <PRE>
     * タイプリファレンスを取得する.<BR>
     * </PRE>
     *
     * @return タイプリファレンス
     */
    @Override
    protected TypeReference<Map<String, DayTime>> getTypeReference() {
        return new TypeReference<>() {
        };
    }
}
