package com.example.locktest.adapter.out.persistence.entity;

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
    public ItemEntity(Long id, String name, Long amount, LocalDateTime creatAt) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.creatAt = creatAt;
    }
}

