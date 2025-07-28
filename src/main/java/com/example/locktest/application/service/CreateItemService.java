package com.example.locktest.application.service;

import com.example.locktest.application.command.CreateItemCommand;
import com.example.locktest.application.port.in.CreateItemUseCase;
import com.example.locktest.application.port.out.ItemRepository;
import com.example.locktest.domain.model.Item;

import java.time.LocalDateTime;

public class CreateItemService implements CreateItemUseCase {

    private final ItemRepository itemRepository;

    public CreateItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Long createItem(CreateItemCommand command) {
        Item item = new Item(null, command.name(), command.amount(), LocalDateTime.now());
        item.validate();
        return itemRepository.save(item);
    }
}
