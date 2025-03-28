package nlj.business.transaction;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tech.jhipster.config.DefaultProfileUtil;

/**
 * <PRE>
 * アプリケーションWeb XMLクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // set a default to use when no profile is configured.
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(TransactionApplication.class);
    }
}
