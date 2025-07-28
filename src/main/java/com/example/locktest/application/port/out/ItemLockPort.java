package com.example.locktest.application.port.out;

public interface ItemLockPort {
    void lock(Long itemId);
    void unlock(Long itemId);
}
