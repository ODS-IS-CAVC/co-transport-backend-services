package jp.co.nlj.ttmi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <PRE>
 * OAuth2設定クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Configuration
@ConfigurationProperties(prefix = "next.ttmi")
@Data
public class OAuth2TTMIConfig {

    private String tokenUrl;
    private String clientId;
    private String clientSecret;
    private String tenantId;
}
