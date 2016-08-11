package com.limiter.open.tokenbucket.config.impl;

import com.limiter.open.tokenbucket.config.ConfigCenter;
import com.limiter.open.tokenbucket.domain.TokenBucketConfig;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhao
 */
public class LocalConfigCenterImpl implements ConfigCenter {

    private static ConcurrentHashMap<String, TokenBucketConfig> configMap = new ConcurrentHashMap<>();

    @Override
    public TokenBucketConfig getConfig(String tokenBucketKey) {
        return configMap.get(tokenBucketKey);
    }

    @Override
    public boolean register(TokenBucketConfig tokenBucketConfig) {
        configMap.putIfAbsent(tokenBucketConfig.getTokenBucketKey(), tokenBucketConfig);
        return true;
    }

    @Override
    public boolean clear() {
        configMap.clear();
        return true;
    }

}
