package jp.co.nlj.verify.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <PRE>
 * アプリケーションプロパティクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
