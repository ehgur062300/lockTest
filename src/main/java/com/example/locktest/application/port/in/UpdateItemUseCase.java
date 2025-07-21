package com.example.locktest.application.port.in;

import com.example.locktest.application.command.UpdateItemCommand;

public interface UpdateItemUseCase {
    Long updateItem(UpdateItemCommand command);
}
