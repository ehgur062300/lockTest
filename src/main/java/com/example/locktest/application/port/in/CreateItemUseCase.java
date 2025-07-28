package com.example.locktest.application.port.in;

import com.example.locktest.application.command.CreateItemCommand;

public interface CreateItemUseCase {
    Long createItem(CreateItemCommand command);
}
