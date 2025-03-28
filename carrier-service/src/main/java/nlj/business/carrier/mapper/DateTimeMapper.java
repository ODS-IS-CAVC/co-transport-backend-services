package nlj.business.carrier.mapper;

import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.DateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import nlj.business.carrier.constant.DataBaseConstant;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * 日付時刻マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface DateTimeMapper {

    @Named("stringToLocalDate")
    static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return !BaseUtil.isNull(date) ? LocalDate.parse(date, formatter) : null;
    }

    @Named("stringHyphenToLocalDate")
    static LocalDate stringHyphenToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT_HYPHEN);
        return !BaseUtil.isNull(date) ? LocalDate.parse(date, formatter) : null;
    }

    @Named("dateToString")
    static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return date != null ? date.format(formatter) : null;
    }

    @Named("stringToLocalTime")
    static LocalTime stringToLocalTime(String time) {
        return !BaseUtil.isNull(time) ? DateUtils.parseTime(time) : null;
    }

    @Named("timeToString")
    static String timeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS);
        return time != null ? time.format(formatter) : null;
    }

    @Named("timeToStringHHMM")
    static String timeToStringHHMM(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM);
        return time != null ? time.format(formatter) : null;
    }

    @Named("localDateTimeToStringHHMM")
    static String localDateTimeToStringHHMM(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            DataBaseConstant.DATE_TIME_FORMAT.LOCAL_DATE_TIME_FORMAT);
        return time != null ? time.format(formatter) : null;
    }

    @Named("stringTolocalDateTime")
    static LocalDateTime stringToLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            DataBaseConstant.DATE_TIME_FORMAT.LOCAL_DATE_TIME_FORMAT);
        return !BaseUtil.isNull(time) ? LocalDateTime.parse(time, formatter) : null;
    }

    @Named("localDateTimeToString")
    static String localDateTimeToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return date != null ? date.format(formatter) : null;
    }

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

    @Named("localDateTimeToJapaneseFormat")
    static String localDateTimeToJapaneseFormatHHMM(LocalTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                DataBaseConstant.DATE_TIME_FORMAT.DATE_TIME_FORMAT_JAPAN_HHMM)
            .withLocale(Locale.JAPANESE);
        return dateTime.format(formatter);
    }
}
