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
//    @Qualifier("redisDecreaseItemUseCase")
    @Qualifier("dbDecreaseItemUseCase")
//    @Qualifier("javaSyncDecreaseItemUseCase")
    private DecreaseItemUseCase decreaseItemUseCase;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        Item item = new Item(null, "item1", 100L, LocalDateTime.now());
        itemRepository.save(item);
    }

    @Test
    void 동시에_1000개의_요청을_보내면_정확히_1000개_차감되어야_한다() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Long itemId = 1L;

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    // 실패 시 재시도
                    for (int retry = 0; retry < 10; retry++) {
                        try {
                            decreaseItemUseCase.decreaseItem(new DecreaseItemCommand(itemId, 1L));
                            break; // 성공하면 break
                        } catch (Exception e) {
                            // 낙관적 락 충돌 or LockTimeout → 재시도
                            System.out.println(Thread.currentThread().getName() + " - retrying: " + e.getMessage());
                            Thread.sleep(10); // 너무 빠르게 재시도하지 않게 딜레이
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Thread Error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 요청 대기

        Item item = itemRepository.findById(itemId).orElseThrow();
        assertEquals(0L, item.getAmount()); // 재고가 정확히 0이어야 성공
    }

}
