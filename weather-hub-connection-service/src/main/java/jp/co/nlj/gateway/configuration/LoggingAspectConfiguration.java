package jp.co.nlj.gateway.configuration;

import jp.co.nlj.gateway.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * LoggingAspectConfigurationクラスは、ログインターセプターを設定するためのクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    /**
     * ログインターセプターをBeanとして定義し、環境変数を渡す。
     *
     * @param env 環境変数
     * @return LoggingAspectのインスタンス
     */
    @Bean
    @Profile("dev")
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
