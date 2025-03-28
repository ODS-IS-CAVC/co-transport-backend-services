package jp.co.nlj.verify.configuration;

import com.next.logistic.security.config.AuthWhiteProperties;
import com.next.logistic.security.whitelist.AuthWhitelistProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * <PRE>
 * ホワイトリスト設定クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
public class VerifyServiceWhitelistConfiguration implements AuthWhitelistProvider {

    @Autowired
    private AuthWhiteProperties authWhiteProperties;

    @Override
    public String[] authWhitelist() {
        return authWhiteProperties.getApis();
    }
}
