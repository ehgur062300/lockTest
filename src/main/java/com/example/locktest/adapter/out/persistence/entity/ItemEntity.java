package com.example.locktest.adapter.out.persistence.entity;

import com.example.locktest.domain.model.Item;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Table(name = "items")
@NoArgsConstructor
@Entity
public class ItemEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private LocalDateTime creatAt;

    @Builder
    public ItemEntity(String name, Long amount, LocalDateTime creatAt) {
        this.name = name;
        this.amount = amount;
        this.creatAt = creatAt;
    }

    public void update(Item item) {
        this.name = item.getName();
        this.amount = item.getAmount();
    }

    public Item toDomain() {
        return new Item(id, name, amount, creatAt);
    }
}

