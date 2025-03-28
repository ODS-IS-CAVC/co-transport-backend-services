package nlj.business.carrier.util;

import com.next.logistic.util.BaseUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <PRE>
 * StringUtilクラスは、文字列を操作するためのユーティリティクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class StringUtil {

    /**
     * カンマ区切りの整数文字列をIntegerオブジェクトのリストに変換します。
     *
     * @param input the input string to be converted
     * @return a list of integers parsed from the input string, or an empty list if the input is null
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
