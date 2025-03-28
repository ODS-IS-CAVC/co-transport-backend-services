package jp.co.nlj.verify.configuration;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

/**
 * <PRE>
 * Jackson設定クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Java日付時刻APIのサポート。<BR>
     *
     * @return 対応するJacksonモジュール。
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    /**
     * Java 8のサポート。<BR>
     *
     * @return 対応するJacksonモジュール。
     */
    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /**
     * ConstraintViolationProblemのシリアライゼーション/デシリアライゼーションのモジュール。<BR>
     *
     * @return 対応するJacksonモジュール。
     */
    @Bean
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}
