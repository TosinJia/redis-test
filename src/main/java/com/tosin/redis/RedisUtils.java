package com.tosin.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    private static JedisPool jedisPool = null;

    static{
        /**
         * 高版本中官方废弃没有setMaxActive和setMaxWait属性，用以下两个属性替换
         * maxActive  ==>  maxTotal
         * maxWait    ==>  maxWaitMillis
         */
        JedisPoolConfig config = new JedisPoolConfig();
        //可用连接实例的最大数目，默认值为8；如果赋值为-1，则表示不限制
        config.setMaxTotal(1024);
        //控制一个pool最多有多少个状态为idle（空闲的）的jedis实例，默认值也是8
        config.setMaxIdle(200);
        //等待可用连接的最大时间，单位毫秒；默认值是-1，表示永不超时
        config.setMaxWaitMillis(100000);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, "tosin-01", 6379);
    }

    public synchronized static Jedis getJedis(){
        try {
            if(jedisPool != null) return jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void returnResource(final Jedis jedis){
        if(jedis != null) jedisPool.returnResource(jedis);
    }
}
