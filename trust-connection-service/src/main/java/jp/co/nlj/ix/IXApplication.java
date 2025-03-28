package jp.co.nlj.ix;

import com.next.logistic.config.NljUrlProperties;
import com.next.logistic.config.OpenTelemetryProperties;
import com.next.logistic.mail.config.NextMailProperties;
import com.next.logistic.mail.proxy.EnableAdapterMail;
import com.next.logistic.proxy.EnableAdapterWebOauth2;
import com.next.logistic.security.proxy.EnableNLJSecurity;
import jakarta.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import jp.co.nlj.ix.config.ApplicationProperties;
import jp.co.nlj.ix.config.CRLFLogConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tech.jhipster.config.DefaultProfileUtil;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;

/**
 * <PRE>
 * IXアプリケーション。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@SpringBootApplication
@EnableConfigurationProperties({JHipsterProperties.class, ApplicationProperties.class, OpenTelemetryProperties.class,
    NextMailProperties.class, NljUrlProperties.class})
@EnableAspectJAutoProxy
@EnableAdapterWebOauth2
@EnableNLJSecurity
@EnableAdapterMail
@EnableJpaAuditing
public class IXApplication {

    private static final Logger log = LoggerFactory.getLogger(IXApplication.class);

    private final Environment env;

    public IXApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(IXApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
                activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)
        ) {
            log.error(
                "You have misconfigured your application! It should not run "
                    + "with both the 'dev' and 'prod' profiles at the same time."
            );
        }
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
                activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)
        ) {
            log.error(
                "You have misconfigured your application! It should not "
                    + "run with both the 'dev' and 'cloud' profiles at the same time."
            );
        }

        Locale.setDefault(new Locale("ja", "JP"));
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store"))
            .map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
            .ofNullable(env.getProperty("server.servlet.context-path"))
            .filter(StringUtils::isNotBlank)
            .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            "\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );

        String configServerStatus = env.getProperty("configserver.status");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
        }
        log.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            "\n----------------------------------------------------------\n\t" +
                "Config Server: \t{}\n----------------------------------------------------------",
            configServerStatus
        );
    }
}