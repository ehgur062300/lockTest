package com.example.locktest.infrastructure.config;

import com.example.locktest.adapter.out.persistence.JpaItemRepository;
import com.example.locktest.adapter.out.persistence.SpringDataItemRepository;
import com.example.locktest.application.port.in.CreateItemUseCase;
import com.example.locktest.application.port.in.UpdateItemUseCase;
import com.example.locktest.application.port.out.ItemRepository;
import com.example.locktest.application.service.CreateItemService;
import com.example.locktest.application.service.UpdateItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeenConfig {

    // CRUD service
    @Bean
    public ItemRepository itemRepository(SpringDataItemRepository springDataItemRepository) {
        return new JpaItemRepository(springDataItemRepository);
    }

    @Bean
    public CreateItemUseCase createItemUseCase(ItemRepository itemRepository) {
        return new CreateItemService(itemRepository);
    }

    @Bean
    public UpdateItemUseCase updateItemUseCase(ItemRepository itemRepository) {
        return new UpdateItemService(itemRepository);
    }
}
