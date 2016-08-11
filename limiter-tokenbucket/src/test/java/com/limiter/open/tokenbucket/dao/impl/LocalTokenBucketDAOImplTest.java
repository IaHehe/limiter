package com.limiter.open.tokenbucket.dao.impl;

import com.limiter.open.tokenbucket.core.TokenBucketDAO;
import com.limiter.open.tokenbucket.domain.TokenBucket;
import com.limiter.tokenbucket.ObjectFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wuhao
 */
public class LocalTokenBucketDAOImplTest {

    private static String tokenBucketKey;

    private static TokenBucket tokenBucket;

    private static TokenBucketDAO localTokenBucketDAO;

    @BeforeClass
    public static void beforeClass() {
        tokenBucketKey = "test";
        localTokenBucketDAO = ObjectFactory.getTokenBucketDAOInstance();

        tokenBucket = new TokenBucket() {
            @Override
            public String getTokenBucketKey() {
                return tokenBucketKey;
            }

            @Override
            public int getCapacity() {
                return 120;
            }

            @Override
            public int getTokenNum() {
                return 1;
            }

            @Override
            public long getLastRefillTimePoint() {
                return 1000;
            }

            @Override
            public void setLastRefillTimePoint(long time) {

            }

            @Override
            public long getAddTimeWithMillisecond() {
                return 1000;
            }

            @Override
            public int getAddNum() {
                return 1000;
            }

            @Override
            public long getAddPeriod() {
                return 1000;
            }

            @Override
            public void filledToken(int num) {

            }

            @Override
            public void reduceToken(int num) {

            }
        };

    }

    @Test
    public void testGetTokenBucket() {
        boolean result = localTokenBucketDAO.saveTokenBucket(tokenBucket);
        assertTrue(result);

        TokenBucketDAO tokenBucketDAO = ObjectFactory.getTokenBucketDAOInstance();
        TokenBucket tokenBucketResult = tokenBucketDAO.getTokenBucket(tokenBucketKey);
        assertEquals(tokenBucket.getAddNum(), tokenBucketResult.getAddNum());
        assertEquals(tokenBucket.getAddPeriod(), tokenBucketResult.getAddPeriod());
        assertEquals(tokenBucket.getAddTimeWithMillisecond(), tokenBucketResult.getAddTimeWithMillisecond());
        assertEquals(tokenBucket.getCapacity(), tokenBucketResult.getCapacity());
        assertEquals(tokenBucket.getLastRefillTimePoint(), tokenBucketResult.getLastRefillTimePoint());
        assertEquals(tokenBucket.getTokenBucketKey(), tokenBucketResult.getTokenBucketKey());
        assertEquals(tokenBucket.getTokenNum(), tokenBucketResult.getTokenNum());
    }

}
