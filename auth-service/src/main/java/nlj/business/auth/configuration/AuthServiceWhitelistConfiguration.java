package nlj.business.auth.configuration;

import com.next.logistic.security.config.AuthWhiteProperties;
import com.next.logistic.security.whitelist.AuthWhitelistProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


/**
 * <PRE>
 * 認証サービスホワイトリスト設定。<BR>
 * </PRE>
 * 
 * @author Next Logistics Japan
 */
@Configuration
public class AuthServiceWhitelistConfiguration implements AuthWhitelistProvider {

    @Autowired
    private AuthWhiteProperties authWhiteProperties;

    @Override
    public String[] authWhitelist() {
        return authWhiteProperties.getApis();
    }
}
