package com.example.locktest.adapter.out.lock;

import com.example.locktest.application.port.out.ItemLockPort;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLock implements ItemLockPort {

    private final RedissonClient redissonClient;

    public RedisLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void lock(Long itemId) {
        RLock lock = redissonClient.getLock("item_lock_" + itemId);
        try {
            lock.lock(5, TimeUnit.SECONDS);
        } catch(Exception e) {
            throw new IllegalStateException("lock fail", e);
        }
    }

    @Override
    public void unlock(Long itemId) {
        RLock lock = redissonClient.getLock("item_lock_" + itemId);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
