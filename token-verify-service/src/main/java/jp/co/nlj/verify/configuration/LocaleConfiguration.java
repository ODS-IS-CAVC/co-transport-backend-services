package jp.co.nlj.verify.configuration;

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
 * ロケール設定クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

    /**
     * ロケールリゾルバを取得する。<BR>
     *
     * @return ロケールリゾルバ
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY");
        cookieLocaleResolver.setDefaultLocale(Locale.ROOT);
        return cookieLocaleResolver;
    }

    /**
     * ロケール変更インターセプタを追加する。<BR>
     *
     * @param registry インターセプタレジストリ
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        registry.addInterceptor(localeChangeInterceptor);
    }
}
