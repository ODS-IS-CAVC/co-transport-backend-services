package jp.co.nlj.verify.configuration;

import jp.co.nlj.verify.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * <PRE>
 * ログアスペクト設定クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile("dev")
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
