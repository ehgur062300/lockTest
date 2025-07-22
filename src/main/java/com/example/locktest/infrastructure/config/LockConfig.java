package com.example.locktest.infrastructure.config;

import com.example.locktest.adapter.out.lock.DecreaseWithOptimisticLockAdapter;
import com.example.locktest.adapter.out.lock.DecreaseWithRedisLockAdapter;
import com.example.locktest.adapter.out.lock.DecreaseWithSynchronizedLockAdapter;
import com.example.locktest.application.port.in.DecreaseItemUseCase;
import com.example.locktest.application.port.out.ItemLockPort;
import com.example.locktest.application.port.out.ItemRepository;
import com.example.locktest.application.service.DecreaseItemService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfig {

    // redis
    @Bean("redisItemLockPort")
    public ItemLockPort redisItemLockPort(RedissonClient redissonClient) {
        return new DecreaseWithRedisLockAdapter(redissonClient);
    }

    @Bean("redisDecreaseItemUseCase")
    public DecreaseItemUseCase redisDecreaseItemUseCase(ItemRepository itemRepository,
                                                        @Qualifier("redisItemLockPort") ItemLockPort lockPort
    ) {
        return new DecreaseItemService(itemRepository, lockPort);
    }

    // db optimistic
    @Bean("databaseItemLockPort")
    public ItemLockPort databaseItemLockPort() {
        return new DecreaseWithOptimisticLockAdapter();
    }

    @Bean("dbDecreaseItemUseCase")
    public DecreaseItemUseCase dbDecreaseItemUseCase(ItemRepository itemRepository,
                                                     @Qualifier("databaseItemLockPort") ItemLockPort lockPort) {
        return new DecreaseItemService(itemRepository, lockPort);
    }

    // sync
    @Bean("javaSyncItemLockPort")
    public ItemLockPort javaSyncItemLockPort() {
        return new DecreaseWithSynchronizedLockAdapter();
    }

    @Bean("javaSyncDecreaseItemUseCase")
    public DecreaseItemUseCase syncDecreaseItemUseCase(ItemRepository itemRepository,
                                                       @Qualifier("javaSyncItemLockPort") ItemLockPort lockPort) {
        return new DecreaseItemService(itemRepository, lockPort);
    }
}
