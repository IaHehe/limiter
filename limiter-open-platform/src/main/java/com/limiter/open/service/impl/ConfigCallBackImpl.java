package com.limiter.open.service.impl;

import com.limiter.open.common.utils.TokenBucketKeyUtils;
import com.limiter.open.config.RuleDao;
import com.limiter.open.config.domain.*;
import com.limiter.open.tokenbucket.domain.TokenBucketConfig;
import com.limiter.open.tokenbucket.config.ConfigCenter;
import com.limiter.open.tokenbucket.config.ConfigCallBack;

public class ConfigCallBackImpl implements ConfigCallBack {

    private RuleDao ruleDao;

    private ConfigCenter configCenter;

    public void setRuleDao(RuleDao ruleDao) {
        this.ruleDao = ruleDao;
    }

    public void setConfigCenter(ConfigCenter configCenter) {
        this.configCenter = configCenter;
    }

    @Override
    public void callBack(String tokenBucketKey) {
        String appkey = TokenBucketKeyUtils.getAppkey(tokenBucketKey);
        String method = TokenBucketKeyUtils.getMethod(tokenBucketKey);

        Config config = ruleDao.selectConfig();

        DetailConfig detailConfig = config.getDetailConfig(appkey, method);
        if (null != detailConfig) {
            register(detailConfig, appkey, method);
            return;
        }
        AppkeyConfig appkeyConfig = config.getAppkeyConfig(appkey);
        if (null != appkeyConfig) {
            register(appkeyConfig, appkey, method);
            return;
        }

        MethodConfig methodConfig = config.getMethodConfig(method);
        if (null != methodConfig) {
            register(methodConfig, appkey, method);
            return;
        }

        BaseConfigInfo baseConfigInfo = config.getGlobalConfig();
        if (null != baseConfigInfo) {
            register(baseConfigInfo, appkey, method);
        }
    }

    private void register(final BaseConfigInfo config, final String appkey, final String method) {
        configCenter.register(new TokenBucketConfig() {

            @Override
            public String getTokenBucketKey() {
                return TokenBucketKeyUtils.generateTokenBucketKey(appkey, method);
            }

            @Override
            public int getCapacity() {
                return config.getCapacity();
            }

            @Override
            public int getAddNum() {
                return config.getAddNum();
            }

            @Override
            public long getAddTimeWithMillisecond() {
                return config.getAddTimeWithMillisecond();
            }

            @Override
            public long getAddPeriod() {
                return config.getAddPeriod();
            }
        });
    }

}
