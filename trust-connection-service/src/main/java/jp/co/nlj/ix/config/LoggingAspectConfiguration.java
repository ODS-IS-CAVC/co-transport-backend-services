package jp.co.nlj.ix.config;

import jp.co.nlj.ix.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * <PRE>
 * LoggingAspectConfigurationクラスは、ログの設定を行うためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    /**
     * LoggingAspectをBeanとして定義し、ログの設定を行う。
     *
     * @param env Environment
     * @return LoggingAspectのインスタンス
     */
    @Bean
    @Profile("dev")
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
