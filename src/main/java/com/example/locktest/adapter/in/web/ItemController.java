package com.example.locktest.adapter.in.web;

import com.example.locktest.application.command.CreateItemCommand;
import com.example.locktest.application.command.DecreaseItemCommand;
import com.example.locktest.application.command.UpdateItemCommand;
import com.example.locktest.application.port.in.CreateItemUseCase;
import com.example.locktest.application.port.in.DecreaseItemUseCase;
import com.example.locktest.application.port.in.UpdateItemUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/items")
public class ItemController {

    private final CreateItemUseCase createItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final DecreaseItemUseCase syncDecreaseItemUseCase;
    private final DecreaseItemUseCase redisDecreaseItemUseCase;
    private final DecreaseItemUseCase optimisticDecreaseItemUseCase;

    public ItemController(CreateItemUseCase createItemUseCase,
                          UpdateItemUseCase updateItemUseCase,
                          @Qualifier("javaSyncDecreaseItemUseCase") DecreaseItemUseCase sync,
                          @Qualifier("dbDecreaseItemUseCase") DecreaseItemUseCase optimistic,
                          @Qualifier("redisDecreaseItemUseCase") DecreaseItemUseCase redis) {
        this.createItemUseCase = createItemUseCase;
        this.updateItemUseCase = updateItemUseCase;
        this.syncDecreaseItemUseCase = sync;
        this.optimisticDecreaseItemUseCase = optimistic;
        this.redisDecreaseItemUseCase = redis;
    }

    @PostMapping
    public ResponseEntity<Long> createItem(@RequestBody CreateItemCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createItemUseCase.createItem(command));
    }

    @PutMapping
    public ResponseEntity<Long> updateItem(@RequestBody UpdateItemCommand command) {
        return ResponseEntity.ok().body(updateItemUseCase.updateItem(command));
    }

    @PostMapping("/decrease/sync")
    public ResponseEntity<Long> decreaseItemWithSync(@RequestBody DecreaseItemCommand command) {
        return ResponseEntity.ok(syncDecreaseItemUseCase.decreaseItem(command));
    }

    @PostMapping("/decrease/optimistic")
    public ResponseEntity<Long> decreaseItemWithOptimistic(@RequestBody DecreaseItemCommand command) {
        return ResponseEntity.ok(optimisticDecreaseItemUseCase.decreaseItem(command));
    }

    @PostMapping("/decrease/redis")
    public ResponseEntity<Long> decreaseItemWithRedis(@RequestBody DecreaseItemCommand command) {
        return ResponseEntity.ok(redisDecreaseItemUseCase.decreaseItem(command));
    }

}
