package com.example.locktest.application.port.out;

import com.example.locktest.domain.model.Item;

public interface ItemRepository {
    Long save(Item item);
}
