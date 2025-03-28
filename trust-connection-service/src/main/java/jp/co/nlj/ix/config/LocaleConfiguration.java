package jp.co.nlj.ix.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * <PRE>
 * LocaleConfigurationクラスは、ロケールの設定を行うためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

    /**
     * CookieLocaleResolverをBeanとして定義し、ロケールの解決を行う。
     *
     * @return LocaleResolverのインスタンス
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY");
        cookieLocaleResolver.setDefaultLocale(Locale.ROOT);
        return cookieLocaleResolver;
    }

    /**
     * インターセプターを追加し、リクエストパラメータでロケールを変更できるようにする。
     *
     * @param registry インターセプターのレジストリ
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        registry.addInterceptor(localeChangeInterceptor);
    }
}
