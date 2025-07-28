package com.example.locktest.adapter.out.lock;

import com.example.locktest.application.port.out.ItemLockPort;

public class DecreaseWithOptimisticLockAdapter implements ItemLockPort {

    @Override
    public void lock(Long itemId) { }

    @Override
    public void unlock(Long itemId) { }
}
