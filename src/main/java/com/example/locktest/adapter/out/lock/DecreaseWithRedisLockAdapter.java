package com.example.locktest.adapter.out.lock;

import com.example.locktest.application.port.out.ItemLockPort;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class DecreaseWithRedisLockAdapter implements ItemLockPort {

    private final RedissonClient redissonClient;

    public DecreaseWithRedisLockAdapter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void lock(Long itemId) {
        RLock lock = redissonClient.getLock("item_lock_" + itemId);
        try {
            System.out.println(Thread.currentThread().getName() + " - try to lock: " + itemId);
            lock.lock(5, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + " - lock acquired: " + itemId);
        } catch (Exception e) {
            throw new IllegalStateException("lock fail", e);
        }
    }

    @Override
    public void unlock(Long itemId) {
        RLock lock = redissonClient.getLock("item_lock_" + itemId);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " - unlock: " + itemId);
        }
    }

}
