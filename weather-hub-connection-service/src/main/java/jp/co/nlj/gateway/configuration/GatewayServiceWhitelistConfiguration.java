package jp.co.nlj.gateway.configuration;

import com.next.logistic.security.config.AuthWhiteProperties;
import com.next.logistic.security.whitelist.AuthWhitelistProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * GatewayServiceWhitelistConfigurationクラスは、Gatewayサービスのホワイトリストを設定するためのクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
public class GatewayServiceWhitelistConfiguration implements AuthWhitelistProvider {

    @Autowired
    private AuthWhiteProperties authWhiteProperties;

    /**
     * 認証ホワイトリストを取得します。
     *
     * @return 認証ホワイトリスト
     */
    @Override
    public String[] authWhitelist() {
        return authWhiteProperties.getApis();
    }
}
