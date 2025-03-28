package jp.co.nlj.ix.config;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

/**
 * <PRE>
 * JacksonConfigurationクラスは、Jacksonの設定を行うためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Javaの日時APIをサポートするためのモジュールを提供します。
     *
     * @return Java日時APIに対応するJacksonモジュール。
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    /**
     * JDK8の特性をサポートするためのモジュールを提供します。
     *
     * @return JDK8に対応するJacksonモジュール。
     */
    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /**
     * 制約違反の問題を表現するためのモジュールを提供します。
     *
     * @return 制約違反問題に対応するJacksonモジュール。
     */
    @Bean
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}
