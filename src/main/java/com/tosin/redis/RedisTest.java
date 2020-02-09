package com.tosin.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

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

    /**
     * 事务
     */
    @Test
    public void testTransaction(){
        Jedis jedis = new Jedis("tosin-01", 6379);

        jedis.set("tom", "1000");
        jedis.set("mike", "1000");
        Transaction transaction = null;
        try {
            //开启事务
            transaction = jedis.multi();

            transaction.decrBy("tom", 100);
            transaction.incrBy("mike", 100);
            //提交事务
            transaction.exec();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            transaction.discard();
        }

        jedis.disconnect();
    }

    /**
     * 锁
     */
    @Test
    public void testLock(){
        Jedis jedis = new Jedis("tosin-01", 6379);

        jedis.set("tom", "1000");
        jedis.set("ticket", "1");
        //对ticket加锁，如果在事务执行过程中，该值有变化，则抛出异常
        jedis.watch("ticket");
        Transaction transaction = null;
        try {
            //开启事务
            transaction = jedis.multi();

            transaction.decr("ticket");
            Thread.sleep(5000);
            transaction.decrBy("tom", 100);
            //提交事务
            transaction.exec();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            transaction.discard();
        }

        jedis.disconnect();
    }

    /**
     * 使用Java程序实现消息的发布与订阅，需要继承JedisPubSub类
     */
    @Test
    public void testMessage(){
        Jedis jedis = new Jedis("tosin-01", 6379);
//        jedis.subscribe(new MyListener(), "channel");
        jedis.psubscribe(new MyListener(), "channel*");
    }

}
