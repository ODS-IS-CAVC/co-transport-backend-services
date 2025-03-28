package nlj.business.carrier.link.mapper;

import com.next.logistic.util.BaseUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import nlj.business.carrier.link.constant.DataBaseConstant;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * 日付時間マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface DateTimeMapper {

    /**
     * <PRE>
     * 文字列をLocalDateに変換する.<BR>
     * </PRE>
     *
     * @param date 文字列
     * @return LocalDate
     */
    @Named("stringToLocalDate")
    static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return !BaseUtil.isNull(date) ? LocalDate.parse(date, formatter) : null;
    }

    /**
     * <PRE>
     * LocalDateを文字列に変換する.<BR>
     * </PRE>
     *
     * @param date LocalDate
     * @return 文字列
     */
    @Named("dateToString")
    static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return date != null ? date.format(formatter) : null;
    }

    /**
     * <PRE>
     * 文字列をLocalTimeに変換する.<BR>
     * </PRE>
     *
     * @param time 文字列
     * @return LocalTime
     */
    @Named("stringToLocalTime")
    static LocalTime stringToLocalTime(String time) {
        //if time is in HHmm format, then append 00 as default second
        if (!BaseUtil.isNull(time) && time.length() == 4) {
            time += "00";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS);
        return !BaseUtil.isNull(time) ? LocalTime.parse(time, formatter) : null;
    }

    /**
     * <PRE>
     * LocalTimeを文字列に変換する.<BR>
     * </PRE>
     *
     * @param time LocalTime
     * @return 文字列
     */
    @Named("timeToString")
    static String timeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM);
        return time != null ? time.format(formatter) : null;
    }

    /**
     * <PRE>
     * LocalDateTimeを日本語形式の文字列に変換する.<BR>
     * </PRE>
     *
     * @param dateTime LocalDateTime
     * @return 文字列
     */
    @Named("localDateTimeToJapaneseFormat")
    static String localDateTimeToJapaneseFormat(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                DataBaseConstant.DATE_TIME_FORMAT.DATE_TIME_FORMAT_JAPAN)
            .withLocale(Locale.JAPANESE);
        return dateTime.format(formatter);
    }
}
