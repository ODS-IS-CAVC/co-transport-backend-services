package nlj.business.carrier.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.next.logistic.convert.GenericMapToStringConverter;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <PRE>
 * 切り捨て価格コンバーター.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class CutOffPriceConverter extends GenericMapToStringConverter<BigDecimal> {

    /**
     * <PRE>
     * タイプリファレンスを取得する.<BR>
     * </PRE>
     *
     * @return タイプリファレンス
     */
    @Override
    protected TypeReference<Map<String, BigDecimal>> getTypeReference() {
        return new TypeReference<>() {
        };
    }
}
