package jp.co.nlj.ttmi;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tech.jhipster.config.DefaultProfileUtil;

/**
 * <PRE>
 * アプリケーションWebXML。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // set a default to use when no profile is configured.
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(TTMIApplication.class);
    }
}

