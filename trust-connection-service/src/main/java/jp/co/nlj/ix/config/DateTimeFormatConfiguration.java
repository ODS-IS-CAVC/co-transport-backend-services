package jp.co.nlj.ix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <PRE>
 * DateTimeFormatConfigurationクラスは、日付のフォーマットをISO形式に設定するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
public class DateTimeFormatConfiguration implements WebMvcConfigurer {

    /**
     * 日付のフォーマットをISO形式に設定します。
     *
     * @param registry FormatterRegistry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }
}
