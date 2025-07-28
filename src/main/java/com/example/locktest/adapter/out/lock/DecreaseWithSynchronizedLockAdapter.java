package com.example.locktest.adapter.out.lock;

import com.example.locktest.application.port.out.ItemLockPort;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DecreaseWithSynchronizedLockAdapter implements ItemLockPort {

    // 아이템별로 락 오브젝트를 관리하는 맵
    private final Map<Long, Object> locks = new ConcurrentHashMap<>();

    @Override
    public void lock(Long itemId) {
        Object lock = locks.computeIfAbsent(itemId, k -> new Object());
        synchronized (lock) {
            // synchronized로 락 획득 → 이후 작업은 UseCase에서 수행됨
        }
    }

    @Override
    public void unlock(Long itemId) {
        // synchronized는 자동 해제되므로 별도 unlock 필요 없음
        // 하지만 인터페이스 맞추기 위해 dummy 구현 제공
    }
}
