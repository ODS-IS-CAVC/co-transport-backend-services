package jp.co.nlj.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * <PRE>
 * TokenCacheServiceインターフェースは、トークンキャッシュサービスを定義するためのインターフェースです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TokenCacheService {

    /**
     * キャッシュされたトークンを取得するメソッド。
     *
     * @return キャッシュされたトークン
     */
    String getCacheToken() throws JsonProcessingException;
}
