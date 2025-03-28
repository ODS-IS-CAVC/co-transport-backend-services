package nlj.business.shipper.mapper;

import com.next.logistic.util.BaseUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import nlj.business.shipper.constant.DataBaseConstant;
import nlj.business.shipper.constant.DataBaseConstant.DATE_TIME_FORMAT;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <PRE>
 * 日付時間マッパーインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface DateTimeMapper {

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return !BaseUtil.isNull(date) ? LocalDate.parse(date, formatter) : null;
    }

    @Named("dateToString")
    static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        return date != null ? date.format(formatter) : null;
    }

    @Named("stringToLocalTime")
    static LocalTime stringToLocalTime(String time) {
        //if time is in HHmm format, then append 00 as default second
        if (!BaseUtil.isNull(time) && time.length() == 4) {
            time += "00";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS);
        return !BaseUtil.isNull(time) ? LocalTime.parse(time, formatter) : null;
    }

    @Named("timeToString")
    static String timeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS);
        return time != null ? time.format(formatter) : null;
    }

    @Named("localDateTimeToLocalDate")
    static String toLocalDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT.DATE_FORMAT_JP);
        return localDateTime != null ? localDateTime.format(formatter) : null;
    }

    @Named("timeToStringHHMM")
    static String timeToStringHHMM(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMM);
        return time != null ? time.format(formatter) : null;
    }
}
