package nlj.business.shipper.configuration;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
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
     * Java日付と時間APIのサポート
     *
     * @return 対応するJacksonモジュール
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    /**
     * JDK8のサポート
     *
     * @return 対応するJacksonモジュール
     */
    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /**
     * HibernateのタイプをJacksonでサポート
     *
     * @return 対応するJacksonモジュール
     */
    @Bean
    public Hibernate6Module hibernate6Module() {
        return new Hibernate6Module();
    }

    /**
     * ConstraintViolationProblemのシリアライゼーション/デシリアライゼーションのモジュール
     *
     * @return 対応するJacksonモジュール
     */
    @Bean
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}

