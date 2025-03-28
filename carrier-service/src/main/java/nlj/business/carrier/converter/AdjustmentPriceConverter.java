package nlj.business.carrier.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.next.logistic.convert.GenericMapToStringConverter;
import jakarta.persistence.Converter;
import java.util.Map;
import nlj.business.carrier.domain.opt.DayAdjustment;

/**
 * <PRE>
 * 調整価格コンバーター.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Converter
public class AdjustmentPriceConverter extends GenericMapToStringConverter<DayAdjustment> {

    /**
     * <PRE>
     * タイプリファレンスを取得する.<BR>
     * </PRE>
     *
     * @return タイプリファレンス
     */
    @Override
    protected TypeReference<Map<String, DayAdjustment>> getTypeReference() {
        return new TypeReference<>() {
        };
    }
}
