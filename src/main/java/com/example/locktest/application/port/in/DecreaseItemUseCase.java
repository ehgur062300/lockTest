package com.example.locktest.application.port.in;

import com.example.locktest.application.command.DecreaseItemCommand;

public interface DecreaseItemUseCase {
    Long decreaseItem(DecreaseItemCommand command);
}
