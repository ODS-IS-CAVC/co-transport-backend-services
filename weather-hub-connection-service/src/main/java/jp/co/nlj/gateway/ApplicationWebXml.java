package jp.co.nlj.gateway;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tech.jhipster.config.DefaultProfileUtil;

/**
 * This is a helper Java class that provides an alternative to creating a {@code web.xml}. This will be invoked only
 * when the application is deployed to a Servlet container like Tomcat, JBoss etc.
 *
 * @author Next Logistics Japan
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // set a default to use when no profile is configured.
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(GatewayApplication.class);
    }
}
