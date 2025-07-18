package com.example.locktest.adapter.out.persistence;

import com.example.locktest.adapter.out.persistence.entity.ItemEntity;
import com.example.locktest.application.port.out.ItemRepository;
import com.example.locktest.domain.model.Item;

public class JpaItemRepository implements ItemRepository {

    private final SpringDataItemRepository springDataItemRepository;

    public JpaItemRepository(SpringDataItemRepository springDataItemRepository) {
        this.springDataItemRepository = springDataItemRepository;
    }

    @Override
    public Long save(Item item) {
        ItemEntity entity = ItemEntity.builder()
                .id(item.getId())
                .name(item.getName())
                .amount(item.getAmount())
                .creatAt(item.getCreateAt())
                .build();
        return springDataItemRepository.save(entity).getId();
    }
}
