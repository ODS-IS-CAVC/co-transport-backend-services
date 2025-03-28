package nlj.business.transaction.ulti;

import java.sql.Time;
import java.util.Calendar;

/**
 * <PRE>
 * SQL時間のユーティリティクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class SqlTimeUlti {

    /**
     * <PRE>
     * Timeを整数配列に変換するメソッド。<BR>
     * </PRE>
     *
     * @param time Time
     * @return 整数配列
     */
    public static int[] convertToIntegerArray(Time time) {
        if (time == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new int[]{hour, minute};
    }
}
