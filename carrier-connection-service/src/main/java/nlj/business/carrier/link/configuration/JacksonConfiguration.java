package nlj.business.carrier.link.configuration;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class JacksonConfiguration {

    /**
     * Support for Java date and time API.
     *
     * @return the corresponding Jackson module.
     * @author Next Logistics Japan
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    /**
     * Support for Java 8 date and time API.
     *
     * @return the corresponding Jackson module.
     * @author Next Logistics Japan
     */
    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /*
     * Support for Hibernate types in Jackson.
     *
     * @return the corresponding Jackson module.
     * @author Next Logistics Japan
     */
    @Bean
    public Hibernate6Module hibernate6Module() {
        return new Hibernate6Module();
    }

    /*
     * Module for serialization/deserialization of RFC7807 Problem.
     */
//    @Bean
//    public ProblemModule problemModule() {
//        return new ProblemModule();
//    }

    /*
     * Module for serialization/deserialization of ConstraintViolationProblem.
     *
     * @return the corresponding Jackson module.
     * @author Next Logistics Japan
     */
    @Bean
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}

