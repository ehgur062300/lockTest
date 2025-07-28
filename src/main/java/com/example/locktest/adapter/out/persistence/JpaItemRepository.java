package com.example.locktest.adapter.out.persistence;

import com.example.locktest.adapter.out.persistence.entity.ItemEntity;
import com.example.locktest.application.port.out.ItemRepository;
import com.example.locktest.domain.model.Item;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class JpaItemRepository implements ItemRepository {

    private final SpringDataItemRepository springDataItemRepository;

    public JpaItemRepository(SpringDataItemRepository springDataItemRepository) {
        this.springDataItemRepository = springDataItemRepository;
    }

    @Transactional
    @Override
    public Long save(Item item) {
        ItemEntity entity = ItemEntity.builder()
                .name(item.getName())
                .amount(item.getAmount())
                .creatAt(LocalDateTime.now())
                .build();
        ItemEntity saved = springDataItemRepository.save(entity);
        return saved.getId();
    }

    @Transactional
    @Override
    public Long update(Item newItem) {
        ItemEntity entity = springDataItemRepository.findById(newItem.getId()).orElseThrow(
                () -> new RuntimeException("Item not found")
        );
        entity.update(newItem);
        return entity.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Item> findById(Long id) {
        return springDataItemRepository.findById(id)
                .map(ItemEntity::toDomain);

    }
}
