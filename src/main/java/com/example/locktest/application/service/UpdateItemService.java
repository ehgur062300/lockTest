package com.example.locktest.application.service;

import com.example.locktest.application.command.UpdateItemCommand;
import com.example.locktest.application.port.in.UpdateItemUseCase;
import com.example.locktest.application.port.out.ItemRepository;
import com.example.locktest.domain.model.Item;

public class UpdateItemService implements UpdateItemUseCase {

    private final ItemRepository itemRepository;

    public UpdateItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Long updateItem(UpdateItemCommand command) {
        Item item = itemRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.update(command.name(), command.amount());
        return itemRepository.update(item);
    }
}
