package nlj.business.shipper.util;

import com.next.logistic.util.BaseUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <PRE>
 * 文字列ユーティリティクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class StringUtil {

    /**
     * 文字列を整数リストに変換する。
     *
     * @param input 入力文字列
     * @return 整数リスト
     */
    public static List<Integer> convertStringToList(String input) {
        if (BaseUtil.isNull(input)) {
            return new ArrayList<>();
        }
        if (input.contains("\"")) {
            input = input.replace("\"", "");
        }
        String[] split = input.split(",");
        return Arrays.stream(split)
            .map(Integer::parseInt)
            .toList();
    }
}
