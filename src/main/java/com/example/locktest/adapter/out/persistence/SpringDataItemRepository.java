package com.example.locktest.adapter.out.persistence;

import com.example.locktest.adapter.out.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataItemRepository extends JpaRepository<ItemEntity, Long> {
}
