package jp.co.nlj.ttmi.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * <PRE>
 * トークンデータチャンネルキャッシュサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TokenDataChannelCacheService {

    String getCacheToken() throws JsonProcessingException;
}

