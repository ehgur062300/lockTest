package com.example.locktest.adapter.in.web;

import com.example.locktest.application.command.CreateItemCommand;
import com.example.locktest.application.command.DecreaseItemCommand;
import com.example.locktest.application.command.UpdateItemCommand;
import com.example.locktest.application.port.in.CreateItemUseCase;
import com.example.locktest.application.port.in.DecreaseItemUseCase;
import com.example.locktest.application.port.in.UpdateItemUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/items")
public class ItemController {

    private final CreateItemUseCase createItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final DecreaseItemUseCase decreaseItemUseCase;

    public ItemController(CreateItemUseCase createItemUseCase,
                          UpdateItemUseCase updateItemUseCase,
                          DecreaseItemUseCase decreaseItemUseCase) {
        this.createItemUseCase = createItemUseCase;
        this.updateItemUseCase = updateItemUseCase;
        this.decreaseItemUseCase = decreaseItemUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> createItem(@RequestBody CreateItemCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createItemUseCase.createItem(command));
    }

    @PutMapping
    public ResponseEntity<Long> updateItem(@RequestBody UpdateItemCommand command) {
        return ResponseEntity.ok().body(updateItemUseCase.updateItem(command));
    }

    @PostMapping("/decrease")
    public ResponseEntity<Long> decreaseItem(@RequestBody DecreaseItemCommand command) {
        return ResponseEntity.ok().body(decreaseItemUseCase.decreaseItem(command));
    }

}
