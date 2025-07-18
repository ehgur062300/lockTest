package com.example.locktest.adapter.in.web;

import com.example.locktest.application.command.CreateItemCommand;
import com.example.locktest.application.port.in.CreateItemUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/items")
public class ItemController {

    private final CreateItemUseCase createItemUseCase;

    public ItemController(CreateItemUseCase createItemUseCase) {
        this.createItemUseCase = createItemUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createItem(@RequestBody CreateItemCommand command) {
        return createItemUseCase.createItem(command);
    }

}
