package com.tosin.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {
    @Test
    public void testString(){
//        Jedis jedis = new Jedis("tosin-01", 6379);
        Jedis jedis = RedisUtils.getJedis();
        //添加字符串数据
        jedis.set("name", "tosin");
        //获取字符串数据
        System.out.println(jedis.get("name"));

        //拼接
        jedis.append("name", " is his name.");
        System.out.println(jedis.get("name"));

        //删除键
        jedis.del("name");
        System.out.println(jedis.get("name"));
        //设置多个键值对
        jedis.mset("name", "tom", "age", "23", "qq", "111111");
        //加1操作
        jedis.incr("age");
        System.out.println(jedis.get("name")+"-"+jedis.get("age")+"-"+jedis.get("qq"));


        jedis.disconnect();
    }
}
