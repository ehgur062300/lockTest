package com.example.locktest.domain.model;

import java.time.LocalDateTime;

public class Item {
    private final Long id;
    private String name;
    private Long amount;
    private final LocalDateTime createAt;

    public Item(Long id, String name, Long amount, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.createAt = createAt;
    }

    public void validate() {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("이름은 필수입니다.");
        if (amount <= 0) throw new IllegalArgumentException("재고는 최소 1개 이상이어야 합니다.");
    }

    public void update(String name, Long amount) {
        this.name = name;
        this.amount = amount;
    }

    public void decrease(Long quantity) {
        if (amount < quantity) throw new IllegalArgumentException("재고 수량이 부족합니다.");
        if (quantity < 1) throw new IllegalArgumentException("요청 값의 크기는 0보다 커야합니다.");
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getAmount() { return amount; }
    public LocalDateTime getCreateAt() { return createAt; }
}
