package com.example.locktest.application.port.out;

import com.example.locktest.domain.model.Item;

import java.util.Optional;

public interface ItemRepository {
    Long save(Item item);
    Long update(Item newItem);
    Optional<Item> findById(Long id);
}
