package com.example.locktest.domain.model;

public class Item {
    private final String name;
    private final Long amount;

    public Item(String name, Long amount) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("이름은 필수입니다.");
        if (amount <= 0) throw new IllegalArgumentException("재고는 최소 1개 이상이어야 합니다.");

        this.name = name;
        this.amount = amount;
    }

    public String getName() { return name; }
    public Long getAmount() { return amount; }
}
