package com.example.locktest;

import com.example.locktest.application.command.DecreaseItemCommand;
import com.example.locktest.application.port.in.DecreaseItemUseCase;
import com.example.locktest.application.port.out.ItemRepository;
import com.example.locktest.domain.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class ItemConcurrencyTest {

    @Autowired
    @Qualifier("redisDecreaseItemUseCase")
//    @Qualifier("dbDecreaseItemUseCase")
//    @Qualifier("javaSyncDecreaseItemUseCase")
    private DecreaseItemUseCase decreaseItemUseCase;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        Item item = new Item(null, "item1", 1000L, LocalDateTime.now());
        itemRepository.save(item);
    }

    @Test
    void 동시에_100개의_요청을_보내면_정확히_100개_차감되어야_한다() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Long itemId = 1L;

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    decreaseItemUseCase.decreaseItem(new DecreaseItemCommand(itemId, 1L));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 요청 대기

        Item item = itemRepository.findById(itemId).orElseThrow();
        assertEquals(0L, item.getAmount()); // 100개가 정확히 줄었는지 확인
    }
}
