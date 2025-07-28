package com.example.locktest.application.command;

public record UpdateItemCommand(Long id, String name, Long amount) {
}
