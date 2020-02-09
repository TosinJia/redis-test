package com.tosin.redis;

import redis.clients.jedis.JedisPubSub;

public class MyListener extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        System.out.println("onMessage channel " + channel + ",message is " + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        super.onPMessage(pattern, channel, message);
        System.out.println("onPMessage pattern " + pattern + ",channel is " + channel + ",message is " + message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        super.onSubscribe(channel, subscribedChannels);
    }
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        super.onUnsubscribe(channel, subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        super.onPUnsubscribe(pattern, subscribedChannels);
    }
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        super.onPSubscribe(pattern, subscribedChannels);
    }
}
