package jp.co.nlj.gateway.configuration;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

/**
 * JacksonConfigurationクラスは、Jacksonの設定を行うためのクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Javaの日時APIをサポートするためのモジュールを提供します。
     *
     * @return JavaTimeModuleのインスタンス
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    /**
     * JDK8の特性をサポートするためのモジュールを提供します。
     *
     * @return Jdk8Moduleのインスタンス
     */
    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /**
     * 制約違反の問題を表現するためのモジュールを提供します。
     *
     * @return ConstraintViolationProblemModuleのインスタンス
     */
    @Bean
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}
