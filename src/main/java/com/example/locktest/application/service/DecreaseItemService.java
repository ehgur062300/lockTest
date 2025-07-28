package com.example.locktest.application.service;

import com.example.locktest.application.command.DecreaseItemCommand;
import com.example.locktest.application.port.in.DecreaseItemUseCase;
import com.example.locktest.application.port.out.ItemLockPort;
import com.example.locktest.application.port.out.ItemRepository;
import com.example.locktest.domain.model.Item;

public class DecreaseItemService implements DecreaseItemUseCase {

    private final ItemRepository itemRepository;
    private final ItemLockPort itemLockPort;

    public DecreaseItemService(ItemRepository itemRepository, ItemLockPort itemLockPort) {
        this.itemRepository = itemRepository;
        this.itemLockPort = itemLockPort;
    }

    @Override
    public Long decreaseItem(DecreaseItemCommand command) {
        itemLockPort.lock(command.id());
        try {
            Item item = itemRepository.findById(command.id())
                    .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));
            Item updateItem = item.decrease(command.quantity());
            return itemRepository.update(updateItem);
        } finally {
            itemLockPort.unlock(command.id());
        }
    }
}
